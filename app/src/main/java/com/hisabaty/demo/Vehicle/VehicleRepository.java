package com.hisabaty.demo.Vehicle;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

    Optional<Page<Vehicle>> findBySchoolId(Long schoolId, Pageable pageable);

    Optional<Vehicle> findByPlateNumberIgnoreCase(String plateNumber);

    @Override
    Page<Vehicle> findAll(Specification<Vehicle> spec, Pageable pageable);

    boolean existsByPlateNumberIgnoreCase(String plateNumber);
}
