package com.example.FileManagementApplication.models;

import com.example.FileManagementApplication.enums.FileSource;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@Entity
@Table(name = "file_infos")
@NoArgsConstructor
public class FileInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String filename;
  private LocalDateTime uploaded;
  @Enumerated(EnumType.STRING)
  private FileSource source;
  @ManyToOne
  @JoinColumn(name = "device_id")
  private Device device;

}
