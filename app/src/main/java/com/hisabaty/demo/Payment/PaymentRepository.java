package com.hisabaty.demo.Payment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Optional<Page<Payment>> findBySchoolId(Long schoolId, Pageable pageable);

    Optional<Page<Payment>> findByStudentId(Long studentId, Pageable pageable);

    @Override
    Page<Payment> findAll(Specification<Payment> spec, Pageable pageable);

    boolean existsByReceiptNumberIgnoreCase(String receiptNumber);

    Optional<Payment> findByReceiptNumberIgnoreCase(String receiptNumber);
}
