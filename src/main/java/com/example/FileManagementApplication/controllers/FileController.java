package com.example.FileManagementApplication.controllers;

import com.example.FileManagementApplication.models.FileInfo;
import com.example.FileManagementApplication.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("files")
public class FileController {

  @Autowired
  private FileService fileService;

  @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<FileInfo> uploadFile(final @RequestPart FileInfo fileInfo, final @RequestPart MultipartFile file) {
    final FileInfo storedFileInfo = fileService.storeFile(fileInfo, file);
    return ResponseEntity.ok(storedFileInfo);
  }

  @GetMapping(value = "")
  public ResponseEntity<List<FileInfo>> getFileInfoList() {
    final List<FileInfo> fileInfoList = fileService.getAllFileInfos();
    return ResponseEntity.ok(fileInfoList);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Resource> getFileById(final @PathVariable Long id) {
    final Resource foundFile = fileService.getFileById(id);
    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + foundFile.getFilename() + "\"").body(foundFile);
  }

}
