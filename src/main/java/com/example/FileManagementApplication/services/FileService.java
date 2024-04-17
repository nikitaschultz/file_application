package com.example.FileManagementApplication.services;

import com.example.FileManagementApplication.exceptions.FileException;
import com.example.FileManagementApplication.exceptions.FileNotFoundException;
import com.example.FileManagementApplication.models.FileInfo;
import com.example.FileManagementApplication.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.FileManagementApplication.utils.FileUtils.getFilenameToStore;
import static com.example.FileManagementApplication.utils.FileUtils.getPathFromFileInfo;

@Service
public class FileService {

  @Autowired
  private FileRepository fileRepository;

  public final FileInfo storeFile(final FileInfo fileInfo, final MultipartFile file) {
    if (file.isEmpty()) {
      throw new FileException("Unable to store empty file.");
    }

    try {
      final String filenameToStore = getFilenameToStore(file);
      fileInfo.setFilename(filenameToStore);
      fileInfo.setUploaded(LocalDateTime.now());
      fileRepository.save(fileInfo);
    } catch (final Exception e) {
      throw new FileException("Failed to store file info.", e);
    }

    try (final InputStream inputStream = file.getInputStream()) {
      final Path destination = getPathFromFileInfo(fileInfo);
      Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
      return fileInfo;
    } catch (final Exception e) {
      fileRepository.delete(fileInfo);
      throw new FileException("Failed to store file.", e);
    }
  }

  public final List<FileInfo> getAllFileInfos() {
    return fileRepository.findAll();
  }

  public final Resource getFileById(final Long id) {
    if (id == null) {
      throw new FileException("No id provided.");
    }

    final FileInfo fileInfo = fileRepository.findById(id).orElseThrow(() -> new FileNotFoundException("Unable to locate file with provided id."));

    try {
      final Path filePath = getPathFromFileInfo(fileInfo);
      final Resource file = UrlResource.from(filePath.toUri());
      if (file.exists() && file.isReadable()) {
        return file;
      } else {
        throw new FileException("Could not read file.");
      }
    } catch (final Exception e) {
      throw new FileException("Failed to retrieve file.", e);
    }
  }

}

