package com.hisabaty.demo.Instructor;

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
@RequestMapping("/api/schools/{schoolId}/instructors")
@Validated
public class InstructorRestController {

    private final InstructorService instructorService;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    @PostMapping
    public ResponseEntity<Instructor_Response_DTO> addInstructor(@Valid @RequestBody Instructor_Request_DTO instructor,
                                                                 @Positive @PathVariable Long schoolId) {
        Instructor inst = instructorService.createInstructor(instructor, schoolId);
        return ResponseEntity.status(HttpStatus.CREATED).body(instructorService.convertToInstructorResponseDTO(inst));
    }

    @PostMapping("/filter")
    public ResponseEntity<Page<Instructor_Response_DTO>> getInstructorsByFilter(@Valid @RequestBody Instructor_Request_DTO request,
                                                                                @Positive @PathVariable Long schoolId,
                                                                                Pageable pg) {
        Page<Instructor_Response_DTO> instructors = instructorService.getInstructorDynamically(request, schoolId, pg);
        return ResponseEntity.ok(instructors);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    @GetMapping
    public ResponseEntity<Page<Instructor_Response_DTO>> getInstructorsBySchool(@Positive @PathVariable Long schoolId,
                                                                                Pageable pg) {
        Page<Instructor_Response_DTO> instructors = instructorService.getInstructorsBySchool(schoolId, pg);
        return ResponseEntity.ok(instructors);
    }

    @GetMapping("/{instructorId}")
    public ResponseEntity<Instructor_Response_DTO> getInstructorById(@Positive @PathVariable("instructorId") Long instructorId,
                                                                     Pageable pg) {
        Instructor_Response_DTO inst = instructorService.getInstructorById(instructorId, pg);
        return ResponseEntity.ok(inst);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    @PutMapping("/update/{instructorId}")
    public ResponseEntity<Instructor_Response_DTO> updateInstructor(@PathVariable("instructorId") @Positive Long instructorId,
                                                                    @Valid @RequestBody Instructor_Request_DTO request) {
        Instructor inst = instructorService.updateInstructor(instructorId, request);
        return ResponseEntity.ok(instructorService.convertToInstructorResponseDTO(inst));
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    @DeleteMapping("/delete/{instructorId}")
    public ResponseEntity<Map<String, String>> deleteInstructor(@PathVariable @Positive Long instructorId) {
        Boolean success = instructorService.deleteInstructor(instructorId);
        if (!success) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Instructor not found"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Instructor deleted successfully"));
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    @PatchMapping("/{instructorId}")
    public ResponseEntity<Instructor_Response_DTO> patchInstructor(@PathVariable @Positive Long instructorId,
                                                                   @Valid @RequestBody Instructor_Patch_Request_DTO request) {
        Instructor inst = instructorService.patchInstructor(instructorId, request);
        return ResponseEntity.ok(instructorService.convertToInstructorResponseDTO(inst));
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
