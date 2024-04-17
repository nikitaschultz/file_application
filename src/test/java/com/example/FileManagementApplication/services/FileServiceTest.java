package com.example.FileManagementApplication.services;

import com.example.FileManagementApplication.exceptions.FileException;
import com.example.FileManagementApplication.exceptions.FileNotFoundException;
import com.example.FileManagementApplication.models.FileInfo;
import com.example.FileManagementApplication.repositories.FileRepository;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.mock.web.MockMultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.FileManagementApplication.utils.FileUtils.ROOT_STORAGE_LOCATION;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileServiceTest {

  private PodamFactory podamFactory;
  private MockMultipartFile mockMultipartFile;
  private FileInfo fileInfo;

  @InjectMocks
  private FileService fileService;

  @Mock
  private FileRepository mockFileRepository;

  @BeforeEach
  void before() {
    podamFactory = new PodamFactoryImpl();
    fileInfo = podamFactory.manufacturePojoWithFullData(FileInfo.class);
  }

  @Test
  public void happyPath_getFileInfoList() {
    final List<FileInfo> fileInfoList = new ArrayList<>();
    fileInfoList.add(fileInfo);

    when(mockFileRepository.findAll()).thenReturn(fileInfoList);

    final List<FileInfo> response = fileService.getAllFileInfos();
    assertIterableEquals(fileInfoList, response);

    verify(mockFileRepository, times(1)).findAll();
    verifyNoMoreInteractions(mockFileRepository);
  }

  @Test
  public void happyPath_getFileById() {
    final Long id = 1L;
    final String filename = "test1.txt";
    fileInfo.setId(id);
    fileInfo.setFilename(filename);
    when(mockFileRepository.findById(id)).thenReturn(Optional.of(fileInfo));

    final Resource actualResponse = fileService.getFileById(id);
    final Path expectedPath = ROOT_STORAGE_LOCATION.resolve("1-test1.txt");
    final Resource expectedResource = UrlResource.from(expectedPath.toUri());

    assertEquals(actualResponse, expectedResource);

    verify(mockFileRepository, times(1)).findById(id);
    verifyNoMoreInteractions(mockFileRepository);
  }

  @Test
  public void sadPath_getFileById_noIdProvided() {
    final Exception exception = assertThrows(FileException.class, () -> fileService.getFileById(null));
    final String expectedMessage = "No id provided.";
    assertEquals(expectedMessage, exception.getMessage());

    verifyNoInteractions(mockFileRepository);
  }

  @Test
  public void sadPath_getFileById_fileNotFound() {
    final Long id = 1L;

    when(mockFileRepository.findById(id)).thenReturn(Optional.empty());

    final Exception exception = assertThrows(FileNotFoundException.class, () -> fileService.getFileById(id));
    final String expectedMessage = "Unable to locate file with provided id.";
    assertEquals(expectedMessage, exception.getMessage());

    verify(mockFileRepository, times(1)).findById(id);
    verifyNoMoreInteractions(mockFileRepository);
  }

  @Test
  public void sadPath_uploadFile_fileIsEmpty() {
    mockMultipartFile = new MockMultipartFile("file", "test.txt", "text/plain", ByteArrayBuilder.NO_BYTES);

    final Exception exception = assertThrows(FileException.class, () -> fileService.storeFile(fileInfo, mockMultipartFile));
    final String expectedMessage = "Unable to store empty file.";
    assertEquals(expectedMessage, exception.getMessage());
  }
}
