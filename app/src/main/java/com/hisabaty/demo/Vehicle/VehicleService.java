package com.hisabaty.demo.Vehicle;

import com.hisabaty.demo.School.School;
import com.hisabaty.demo.School.SchoolNotFound;
import com.hisabaty.demo.School.SchoolRepository;
import com.hisabaty.demo.ValueAlreadyExist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepo;
    private final SchoolRepository schoolRepo;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @Transactional
    public Vehicle createVehicle(Vehicle_Request_DTO request, Long schoolId) {
        if (request == null || schoolId == null || schoolId < 0) {
            throw new IllegalArgumentException("Invalid vehicle or school id!");
        }

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new SchoolNotFound("School with ID " + schoolId + " not found. Please create it first."));

        if (vehicleRepo.existsByPlateNumberIgnoreCase(request.getPlateNumber())) {
            throw new ValueAlreadyExist("Error: Vehicle [Plate : " + request.getPlateNumber() + " ] is already registered!");
        }

        if (school.getLicenseAvailable() != null && !school.getLicenseAvailable().contains(request.getTypeOfLicense())) {
            throw new IllegalArgumentException("Error: School does not offer this type of license!");
        }

        Vehicle vehicle = new Vehicle();
        vehicle.setSchool(school);
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setPlateNumber(request.getPlateNumber());
        vehicle.setTypeOfLicense(request.getTypeOfLicense());
        vehicle.setYear(request.getYear());
        vehicle.setStatus(request.getStatus() != null ? request.getStatus() : VehicleStatus.AVAILABLE);
        if (request.getMileage() != null) {
            vehicle.setMileage(request.getMileage());
        }
        vehicle.setInsuranceExpiry(request.getInsuranceExpiry());
        vehicle.setTechnicalVisitExpiry(request.getTechnicalVisitExpiry());

        school.setVehicleCount(school.getVehicleCount() != null ? school.getVehicleCount() + 1 : 1);
        schoolRepo.save(school);

        return vehicleRepo.save(vehicle);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    public Page<Vehicle_Response_DTO> getVehiclesBySchool(Long schoolId, Pageable pageable) {
        Optional<Page<Vehicle>> vehicles = vehicleRepo.findBySchoolId(schoolId, pageable);
        if (vehicles.isEmpty()) {
            throw new VehicleNotFound("No vehicles found for the given school id!");
        }
        return vehicles.get().map(this::convertToVehicleResponseDTO);
    }

    public Vehicle_Response_DTO getVehicleById(Long id, Pageable pageable) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new VehicleNotFound("Vehicle [id : " + id + "] is not found!"));
        return convertToVehicleResponseDTO(vehicle);
    }

    public Page<Vehicle_Response_DTO> getVehicleDynamically(Vehicle_Request_DTO req, Long schoolId, Pageable pg) {
        if (req == null) {
            req = new Vehicle_Request_DTO();
        }
        Specification<Vehicle> spec = VehicleSpecification.searchVehicle(req, schoolId);
        return vehicleRepo.findAll(spec, pg).map(this::convertToVehicleResponseDTO);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    public Vehicle updateVehicle(Long id, Vehicle_Request_DTO req) {
        Vehicle vehicle = vehicleRepo.findById(id)
                .orElseThrow(() -> new VehicleNotFound(id));

        if (StringUtils.hasText(req.getPlateNumber()) && !req.getPlateNumber().equalsIgnoreCase(vehicle.getPlateNumber())) {
            Optional<Vehicle> exist = vehicleRepo.findByPlateNumberIgnoreCase(req.getPlateNumber());
            if (exist.isPresent() && !exist.get().getId().equals(id)) {
                throw new ValueAlreadyExist("Error: A vehicle with plate [" + req.getPlateNumber() + "] already exists.");
            }
        }

        if (StringUtils.hasText(req.getTypeOfLicense())
                && vehicle.getSchool().getLicenseAvailable() != null
                && !vehicle.getSchool().getLicenseAvailable().contains(req.getTypeOfLicense())) {
            throw new IllegalArgumentException("Error: School does not offer this type of license!");
        }

        vehicle = setterVehicles(vehicle, req);
        return vehicleRepo.save(vehicle);
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    public Boolean deleteVehicle(Long id) {
        if (id == null || id < 0) {
            return false;
        }

        Optional<Vehicle> vehicle = vehicleRepo.findById(id);
        if (vehicle.isEmpty()) {
            return false;
        }

        vehicleRepo.delete(vehicle.get());
        return true;
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    public Vehicle patchVehicle(Long vehicleId, Vehicle_Patch_Request_DTO request) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFound(vehicleId));

        if (StringUtils.hasText(request.getPlateNumber()) && !request.getPlateNumber().equalsIgnoreCase(vehicle.getPlateNumber())) {
            Optional<Vehicle> exist = vehicleRepo.findByPlateNumberIgnoreCase(request.getPlateNumber());
            if (exist.isPresent() && !exist.get().getId().equals(vehicleId)) {
                throw new ValueAlreadyExist("Error: A vehicle with plate [" + request.getPlateNumber() + "] already exists.");
            }
        }

        if (StringUtils.hasText(request.getTypeOfLicense())
                && vehicle.getSchool().getLicenseAvailable() != null
                && !vehicle.getSchool().getLicenseAvailable().contains(request.getTypeOfLicense())) {
            throw new IllegalArgumentException("Error: School does not offer this type of license!");
        }

        vehicle = setterVehiclesPatch(vehicle, request);
        return vehicleRepo.save(vehicle);
    }

    /* ************************************************************************** */
    /*                              CONVERT & UTILS                               */
    /* ************************************************************************** */

    public Vehicle_Response_DTO convertToVehicleResponseDTO(Vehicle vehicle) {
        return Vehicle_Response_DTO.toVehicleResponseDTO(vehicle);
    }

    public Vehicle setterVehicles(Vehicle vehicle, Vehicle_Request_DTO request) {
        if (request == null) {
            return vehicle;
        }
        if (vehicle == null) {
            return null;
        }

        if (StringUtils.hasText(request.getBrand())) {
            vehicle.setBrand(request.getBrand());
        }
        if (StringUtils.hasText(request.getModel())) {
            vehicle.setModel(request.getModel());
        }
        if (StringUtils.hasText(request.getPlateNumber())) {
            vehicle.setPlateNumber(request.getPlateNumber());
        }
        if (StringUtils.hasText(request.getTypeOfLicense())) {
            vehicle.setTypeOfLicense(request.getTypeOfLicense());
        }
        if (request.getYear() != null) {
            vehicle.setYear(request.getYear());
        }
        if (request.getStatus() != null) {
            vehicle.setStatus(request.getStatus());
        }
        if (request.getMileage() != null) {
            vehicle.setMileage(request.getMileage());
        }
        if (request.getInsuranceExpiry() != null) {
            vehicle.setInsuranceExpiry(request.getInsuranceExpiry());
        }
        if (request.getTechnicalVisitExpiry() != null) {
            vehicle.setTechnicalVisitExpiry(request.getTechnicalVisitExpiry());
        }

        return vehicle;
    }

    public Vehicle setterVehiclesPatch(Vehicle vehicle, Vehicle_Patch_Request_DTO request) {
        if (request == null) {
            return vehicle;
        }
        if (vehicle == null) {
            return null;
        }

        if (StringUtils.hasText(request.getBrand())) {
            vehicle.setBrand(request.getBrand());
        }
        if (StringUtils.hasText(request.getModel())) {
            vehicle.setModel(request.getModel());
        }
        if (StringUtils.hasText(request.getPlateNumber())) {
            vehicle.setPlateNumber(request.getPlateNumber());
        }
        if (StringUtils.hasText(request.getTypeOfLicense())) {
            vehicle.setTypeOfLicense(request.getTypeOfLicense());
        }
        if (request.getYear() != null) {
            vehicle.setYear(request.getYear());
        }
        if (request.getStatus() != null) {
            vehicle.setStatus(request.getStatus());
        }
        if (request.getMileage() != null) {
            vehicle.setMileage(request.getMileage());
        }
        if (request.getInsuranceExpiry() != null) {
            vehicle.setInsuranceExpiry(request.getInsuranceExpiry());
        }
        if (request.getTechnicalVisitExpiry() != null) {
            vehicle.setTechnicalVisitExpiry(request.getTechnicalVisitExpiry());
        }

        return vehicle;
    }
}
