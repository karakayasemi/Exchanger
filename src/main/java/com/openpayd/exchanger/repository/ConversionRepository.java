package com.openpayd.exchanger.repository;

import com.openpayd.exchanger.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface ConversionRepository extends JpaRepository<Conversion, Long> {
    Page<Conversion> findById(Long Id, Pageable page);
    Page<Conversion> findByDate(Date date, Pageable page);
}
