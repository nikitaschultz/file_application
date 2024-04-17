package com.example.FileManagementApplication.controllers;

import com.example.FileManagementApplication.exceptions.FileNotFoundException;
import com.example.FileManagementApplication.models.FileInfo;
import com.example.FileManagementApplication.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

  private PodamFactory podamFactory;
  private FileInfo fileInfo;

  @InjectMocks
  private FileController fileController;

  @Mock
  private FileService mockFileService;

  @BeforeEach
  void before() {
    podamFactory = new PodamFactoryImpl();
    fileInfo = podamFactory.manufacturePojo(FileInfo.class);
  }

  @Test
  public void happyPath_getFileInfoList() {
    final List<FileInfo> fileInfoList = new ArrayList<>();
    fileInfoList.add(fileInfo);

    when(mockFileService.getAllFileInfos()).thenReturn(fileInfoList);

    final ResponseEntity<List<FileInfo>> response = fileController.getFileInfoList();
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), fileInfoList);

    verify(mockFileService, times(1)).getAllFileInfos();
    verifyNoMoreInteractions(mockFileService);
  }

  @Test
  public void happyPath_getFileById() throws Exception {
    final Resource file = new UrlResource("file://userFiles/1-test1.txt");
    final Long id = 1L;

    when(mockFileService.getFileById(id)).thenReturn(file);

    final ResponseEntity<Resource> response = fileController.getFileById(id);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), file);

    verify(mockFileService, times(1)).getFileById(id);
    verifyNoMoreInteractions(mockFileService);
  }

  @Test
  public void sadPath_getFileById_fileNotFound() {
    final Long id = 1L;

    when(mockFileService.getFileById(id)).thenThrow(FileNotFoundException.class);

    assertThrows(FileNotFoundException.class, () -> fileController.getFileById(id));

    verify(mockFileService, times(1)).getFileById(id);
    verifyNoMoreInteractions(mockFileService);
  }

  @Test
  public void happyPath_uploadFile() {
    final MultipartFile file = podamFactory.manufacturePojoWithFullData(MultipartFile.class);

    when(mockFileService.storeFile(fileInfo, file)).thenReturn(fileInfo);

    final ResponseEntity<FileInfo> response = fileController.uploadFile(fileInfo, file);
    assertEquals(response.getStatusCode(), HttpStatus.OK);
    assertEquals(response.getBody(), fileInfo);

    verify(mockFileService, times(1)).storeFile(fileInfo, file);
    verifyNoMoreInteractions(mockFileService);
  }
}
