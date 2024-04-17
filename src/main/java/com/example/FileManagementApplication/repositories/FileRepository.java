package com.example.FileManagementApplication.repositories;

import com.example.FileManagementApplication.models.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileInfo, Long> {
}
