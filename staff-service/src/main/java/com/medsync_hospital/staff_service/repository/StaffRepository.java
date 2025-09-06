package com.medsync_hospital.staff_service.repository;

import com.medsync_hospital.staff_service.entity.Staff;
import com.medsync_hospital.staff_service.enums.StaffRole;
import com.medsync_hospital.staff_service.enums.StaffStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByPhone(String phone);
    List<Staff> findAllByRole(StaffRole role);
    List<Staff> findAllByStatus(StaffStatus status);
    boolean existsByEmail(String email);
} 