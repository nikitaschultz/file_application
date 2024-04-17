package com.example.FileManagementApplication.components;

import com.example.FileManagementApplication.enums.FileSource;
import com.example.FileManagementApplication.models.Device;
import com.example.FileManagementApplication.models.FileInfo;
import com.example.FileManagementApplication.repositories.DeviceRepository;
import com.example.FileManagementApplication.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements ApplicationRunner {

  @Autowired
  private FileRepository fileRepository;

  @Autowired
  private DeviceRepository deviceRepository;

  @Override
  public void run(final ApplicationArguments args) throws Exception {
    final Device device1 = Device.builder().id(1L).model("A123K").build();
    deviceRepository.save(device1);
    final Device device2 = Device.builder().id(2L).model("B100F").build();
    deviceRepository.save(device2);

    final FileInfo file1 = FileInfo.builder().id(1L).filename("test1.txt").uploaded(LocalDateTime.now()).source(FileSource.OFFICER).device(device1).build();
    fileRepository.save(file1);
    final FileInfo file2 = FileInfo.builder().id(2L).filename("test2.txt").uploaded(LocalDateTime.now()).source(FileSource.PUBLIC).build();
    fileRepository.save(file2);
    final FileInfo file3 = FileInfo.builder().id(3L).filename("test3.txt").uploaded(LocalDateTime.now()).source(FileSource.OFFICER).device(device2).build();
    fileRepository.save(file3);
  }

}
