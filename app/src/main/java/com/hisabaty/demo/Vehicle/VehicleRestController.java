package com.hisabaty.demo.Vehicle;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schools/{schoolId}/vehicles")
@Validated
public class VehicleRestController {

    private final VehicleService vehicleService;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @PostMapping
    public ResponseEntity<Vehicle_Response_DTO> addVehicle(@Valid @RequestBody Vehicle_Request_DTO vehicle,
                                                           @Positive @PathVariable Long schoolId) {
        Vehicle created = vehicleService.createVehicle(vehicle, schoolId);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.convertToVehicleResponseDTO(created));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Vehicle_Response_DTO>> getVehiclesByFilter(@RequestBody Vehicle_Request_DTO request,
                                                                          @Positive @PathVariable Long schoolId,
                                                                          Pageable pg) {
        Page<Vehicle_Response_DTO> vehicles = vehicleService.getVehicleDynamically(request, schoolId, pg);
        return ResponseEntity.ok(vehicles);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    @GetMapping
    public ResponseEntity<Page<Vehicle_Response_DTO>> getVehiclesBySchool(@Positive @PathVariable Long schoolId,
                                                                          Pageable pg) {
        Page<Vehicle_Response_DTO> vehicles = vehicleService.getVehiclesBySchool(schoolId, pg);
        return ResponseEntity.ok(vehicles);
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle_Response_DTO> getVehicleById(@Positive @PathVariable("vehicleId") Long vehicleId,
                                                               Pageable pg) {
        Vehicle_Response_DTO vehicle = vehicleService.getVehicleById(vehicleId, pg);
        return ResponseEntity.ok(vehicle);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<Vehicle_Response_DTO> updateVehicle(@PathVariable("vehicleId") @Positive Long vehicleId,
                                                              @Valid @RequestBody Vehicle_Request_DTO request) {
        Vehicle vehicle = vehicleService.updateVehicle(vehicleId, request);
        return ResponseEntity.ok(vehicleService.convertToVehicleResponseDTO(vehicle));
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<Map<String, String>> deleteVehicle(@PathVariable @Positive Long vehicleId) {
        Boolean success = vehicleService.deleteVehicle(vehicleId);
        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Vehicle not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Vehicle deleted successfully"));
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    @PatchMapping("/{vehicleId}")
    public ResponseEntity<Vehicle_Response_DTO> patchVehicle(@PathVariable @Positive Long vehicleId,
                                                             @Valid @RequestBody Vehicle_Patch_Request_DTO request) {
        Vehicle vehicle = vehicleService.patchVehicle(vehicleId, request);
        return ResponseEntity.ok(vehicleService.convertToVehicleResponseDTO(vehicle));
    }

    /* ************************************************************************** */
    /*                              UTILS                                         */
    /* ************************************************************************** */

    public Pageable getpages(int pageNumber, int pageSize) {
        if (pageNumber < 0 || pageSize < 0) {
            throw new IllegalArgumentException("Invalid page number or page size value!");
        }
        return PageRequest.of(pageNumber, pageSize);
    }
}
