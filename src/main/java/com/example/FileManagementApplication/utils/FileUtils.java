package com.example.FileManagementApplication.utils;

import com.example.FileManagementApplication.models.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
  public static final Path ROOT_STORAGE_LOCATION = Paths.get("userFiles/");
  public static final String DEFAULT_FILENAME = "Unnamed_File";

  public static String getFilenameToStore(final MultipartFile file) {
    final String originalFilename = file.getOriginalFilename();
    return (originalFilename != null && !originalFilename.isEmpty()) ? originalFilename : DEFAULT_FILENAME;
  }

  public static Path getPathFromFileInfo(final FileInfo fileInfo) {
    final String filename = fileInfo.getId().toString() + "-" + fileInfo.getFilename();
    return ROOT_STORAGE_LOCATION.resolve(Paths.get(filename)).normalize().toAbsolutePath();
  }
}
