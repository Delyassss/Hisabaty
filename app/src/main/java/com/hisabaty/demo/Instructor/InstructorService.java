package com.hisabaty.demo.Instructor;

import com.hisabaty.demo.School.School;
import com.hisabaty.demo.School.SchoolNotFound;
import com.hisabaty.demo.School.SchoolRepository;
import com.hisabaty.demo.ValueAlreadyExist;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InstructorService {

    private final InstructorRepository instructorRepo;
    private final SchoolRepository schoolRepo;

    /* ************************************************************************** */
    /*                              POST REQUESTS                                 */
    /* ************************************************************************** */

    public Instructor createInstructor(Instructor_Request_DTO instructor, Long schoolId) {
        if (instructor == null || schoolId == null || schoolId < 0) {
            throw new IllegalArgumentException("Invalid instructor or school id!");
        }

        School school = schoolRepo.findById(schoolId)
                .orElseThrow(() -> new SchoolNotFound("School with ID " + schoolId + " not found. Please create it first."));

        if (instructorRepo.existsByCinIgnoreCase(instructor.getCin())) {
            throw new ValueAlreadyExist("Error: Instructor [Cin : " + instructor.getCin() + " ] is already registered!");
        }

        if (school.getLicenseAvailable() != null && !school.getLicenseAvailable().contains(instructor.getTypeOfLicense())) {
            throw new IllegalArgumentException("Error: School does not offer this type of license!");
        }

        Instructor newInstructor = new Instructor();
        newInstructor.setName(instructor.getName());
        newInstructor.setCin(instructor.getCin());
        newInstructor.setPhone(instructor.getPhone());
        newInstructor.setEmail(instructor.getEmail());
        newInstructor.setCapNumber(instructor.getCapNumber());
        newInstructor.setTypeOfLicense(instructor.getTypeOfLicense());
        newInstructor.setSchool(school);

        if (instructor.getSpeciality() != null) {
            newInstructor.setSpeciality(instructor.getSpeciality());
        } else {
            newInstructor.setSpeciality(InstructorSpeciality.BOTH);
        }

        if (instructor.getStatus() != null) {
            newInstructor.setStatus(instructor.getStatus());
        } else {
            newInstructor.setStatus(InstructorStatus.ACTIVE);
        }

        if (instructor.getSalary() != null) {
            newInstructor.setSalary(instructor.getSalary());
        }

        if (instructor.getHourlyRate() != null) {
            newInstructor.setHourlyRate(instructor.getHourlyRate());
        }

        if (instructor.getHireDate() != null) {
            newInstructor.setHireDate(instructor.getHireDate());
        }

        school.setInstructorCount(school.getInstructorCount() != null ? school.getInstructorCount() + 1 : 1);

        return instructorRepo.save(newInstructor);
    }

    /* ************************************************************************** */
    /*                              GET REQUESTS                                  */
    /* ************************************************************************** */

    public Page<Instructor_Response_DTO> getInstructorsBySchool(Long schoolId, Pageable pageable) {
        Optional<Page<Instructor>> instructors = instructorRepo.findBySchoolId(schoolId, pageable);
        if (instructors.isEmpty()) {
            throw new InstructorNotFound("No instructors found for the given school id!");
        }

        return instructors.get().map(this::convertToInstructorResponseDTO);
    }

    public Instructor_Response_DTO getInstructorById(Long id, Pageable pageable) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new InstructorNotFound("Instructor [id : " + id + "] is not found!"));
        return convertToInstructorResponseDTO(instructor);
    }

    public Page<Instructor_Response_DTO> getInstructorDynamically(Instructor_Request_DTO req, Long schoolId, Pageable pg) {
        if (req == null) {
            throw new IllegalArgumentException("Invalid instructor search criteria!");
        }

        Specification<Instructor> spec = InstructorSpecification.searchInstructor(req, schoolId);
        return instructorRepo.findAll(spec, pg).map(this::convertToInstructorResponseDTO);
    }

    /* ************************************************************************** */
    /*                              PUT REQUESTS                                 */
    /* ************************************************************************** */

    public Instructor updateInstructor(Long id, Instructor_Request_DTO req) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new InstructorNotFound(id));

        if (StringUtils.hasText(req.getCin()) && !req.getCin().equalsIgnoreCase(instructor.getCin())) {
            Optional<Instructor> exist = instructorRepo.findByCin(req.getCin());
            if (exist.isPresent() && !exist.get().getId().equals(id)) {
                throw new ValueAlreadyExist("Error: An instructor with CIN [" + req.getCin() + "] already exists.");
            }
        }

        instructor = setterInstructors(instructor, req);
        return instructorRepo.save(instructor);
    }

    /* ************************************************************************** */
    /*                              DELETE REQUESTS                               */
    /* ************************************************************************** */

    public Boolean deleteInstructor(Long id) {
        if (id == null || id < 0) {
            return false;
        }

        Optional<Instructor> instructor = instructorRepo.findById(id);
        if (instructor.isEmpty()) {
            return false;
        }

        instructorRepo.delete(instructor.get());
        return true;
    }

    /* ************************************************************************** */
    /*                              PATCH REQUESTS                               */
    /* ************************************************************************** */

    public Instructor patchInstructor(Long instructorId, Instructor_Patch_Request_DTO request) {
        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> new InstructorNotFound(instructorId));

        if (StringUtils.hasText(request.getCin()) && !request.getCin().equalsIgnoreCase(instructor.getCin())) {
            Optional<Instructor> exist = instructorRepo.findByCin(request.getCin());
            if (exist.isPresent() && !exist.get().getId().equals(instructorId)) {
                throw new ValueAlreadyExist("Error: An instructor with CIN [" + request.getCin() + "] already exists.");
            }
        }

        instructor = setterInstructorsPatch(instructor, request);
        return instructorRepo.save(instructor);
    }

    /* ************************************************************************** */
    /*                              CONVERT & UTILS                               */
    /* ************************************************************************** */

    public Instructor_Response_DTO convertToInstructorResponseDTO(Instructor instructor) {
        return Instructor_Response_DTO.toInstructorResponseDTO(instructor);
    }

    public Page<Instructor_Response_DTO> toInstructorResponseDTO(Page<Instructor> instructors) {
        return Instructor_Response_DTO.toInstructorResponseDTO_All(instructors);
    }

    public Instructor_Request_DTO convertToInstructorRequestDTO(Instructor instructor) {
        if (instructor == null) {
            return null;
        }
        Instructor_Request_DTO dto = new Instructor_Request_DTO();
        if (instructor.getSchool() != null) {
            dto.setSchoolId(instructor.getSchool().getId());
        }
        dto.setName(instructor.getName());
        dto.setCin(instructor.getCin());
        dto.setPhone(instructor.getPhone());
        dto.setEmail(instructor.getEmail());
        dto.setCapNumber(instructor.getCapNumber());
        dto.setTypeOfLicense(instructor.getTypeOfLicense());
        dto.setSpeciality(instructor.getSpeciality());
        dto.setStatus(instructor.getStatus());
        dto.setSalary(instructor.getSalary());
        dto.setHourlyRate(instructor.getHourlyRate());
        dto.setHireDate(instructor.getHireDate());
        dto.setCreatedAt(instructor.getCreatedAt());
        return dto;
    }

    public Instructor setterInstructors(Instructor instructor, Instructor_Request_DTO request) {
        if (request == null) {
            return instructor;
        }
        if (instructor == null) {
            return null;
        }

        if (StringUtils.hasText(request.getName())) {
            instructor.setName(request.getName());
        }
        if (StringUtils.hasText(request.getCin())) {
            instructor.setCin(request.getCin());
        }
        if (StringUtils.hasText(request.getEmail())) {
            instructor.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone())) {
            instructor.setPhone(request.getPhone());
        }
        if (StringUtils.hasText(request.getCapNumber())) {
            instructor.setCapNumber(request.getCapNumber());
        }
        if (StringUtils.hasText(request.getTypeOfLicense())) {
            instructor.setTypeOfLicense(request.getTypeOfLicense());
        }
        if (request.getSpeciality() != null) {
            instructor.setSpeciality(request.getSpeciality());
        }
        if (request.getStatus() != null) {
            instructor.setStatus(request.getStatus());
        }
        if (request.getSalary() != null) {
            instructor.setSalary(request.getSalary());
        }
        if (request.getHourlyRate() != null) {
            instructor.setHourlyRate(request.getHourlyRate());
        }
        if (request.getHireDate() != null) {
            instructor.setHireDate(request.getHireDate());
        }

        return instructor;
    }

    public Instructor setterInstructorsPatch(Instructor instructor, Instructor_Patch_Request_DTO request) {
        if (request == null) {
            return instructor;
        }
        if (instructor == null) {
            return null;
        }

        if (StringUtils.hasText(request.getName())) {
            instructor.setName(request.getName());
        }
        if (StringUtils.hasText(request.getCin())) {
            instructor.setCin(request.getCin());
        }
        if (StringUtils.hasText(request.getEmail())) {
            instructor.setEmail(request.getEmail());
        }
        if (StringUtils.hasText(request.getPhone())) {
            instructor.setPhone(request.getPhone());
        }
        if (StringUtils.hasText(request.getCapNumber())) {
            instructor.setCapNumber(request.getCapNumber());
        }
        if (StringUtils.hasText(request.getTypeOfLicense())) {
            instructor.setTypeOfLicense(request.getTypeOfLicense());
        }
        if (request.getSpeciality() != null) {
            instructor.setSpeciality(request.getSpeciality());
        }
        if (request.getStatus() != null) {
            instructor.setStatus(request.getStatus());
        }
        if (request.getSalary() != null) {
            instructor.setSalary(request.getSalary());
        }
        if (request.getHourlyRate() != null) {
            instructor.setHourlyRate(request.getHourlyRate());
        }
        if (request.getHireDate() != null) {
            instructor.setHireDate(request.getHireDate());
        }

        return instructor;
    }
}
