package com.example.FileManagementApplication.utils;

import com.example.FileManagementApplication.models.FileInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;

import static com.example.FileManagementApplication.utils.FileUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileUtilsTest {

  private FileInfo fileInfo;

  @BeforeEach
  public void before() {
    fileInfo = FileInfo.builder().id(12L).filename("testFile.jpg").build();
  }

  @Test
  public void happyPath_getFilenameToStore_isNamedFile() {
    final String expected = "test.txt";
    final MockMultipartFile file = new MockMultipartFile("file", expected, "text/plain", "test file".getBytes());
    final String actual = getFilenameToStore(file);

    assertEquals(expected, actual);
  }

  @Test
  public void happyPath_getFilenameToStore_isUnnamedFile() {
    final MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", "test file".getBytes());
    final String actual = getFilenameToStore(file);

    assertEquals(DEFAULT_FILENAME, actual);
  }

  @Test
  public void happyPath_getPathFromFileInfo() {
    final Path actual = getPathFromFileInfo(fileInfo);
    final Path expected = ROOT_STORAGE_LOCATION.resolve(Paths.get("12-testFile.jpg")).normalize().toAbsolutePath();
    assertEquals(expected, actual);
  }

}
