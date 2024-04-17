package com.example.FileManagementApplication.repositories;

import com.example.FileManagementApplication.models.Device;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceRepository extends JpaRepository<Device, Long> {
}
