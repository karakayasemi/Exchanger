package com.openpayd.exchanger.repository;

import com.openpayd.exchanger.entity.Conversion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ConversionRepositoryTest {

    @Autowired
    private ConversionRepository conversionRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void testFindById() {
        Conversion conversion = new Conversion(
                "USD",
                "TRY",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(6.97)
        );
        Conversion finalConversion  = conversionRepository.save(conversion);
        List<Conversion> resultsPage =  conversionRepository.
                findById(conversion.getId(), PageRequest.of(0, 5)).getContent();
        assertThat(resultsPage).isNotEmpty();
        assertEquals(1, resultsPage.size());
        assertThat(resultsPage).extracting("id").allMatch((id) -> Objects.equals(id, finalConversion.getId()));
    }

    @Test
    void testFindByDate() {
        Date date = new Date();
        List<Conversion> conversionList = new ArrayList<>();

        // save 4 conversion
        for (int i = 0; i<4; i++) {
            Conversion conversion = new Conversion(
                    "USD",
                    "TRY",
                    BigDecimal.valueOf(10),
                    BigDecimal.valueOf(6.97)
            );
            conversionList.add(conversion);
        }
        conversionRepository.saveAll(conversionList);

        List<Conversion> resultsPage = conversionRepository.
                findByDate(date, PageRequest.of(0, 3)).getContent();
        assertThat(resultsPage).isNotEmpty();
        assertEquals(3, resultsPage.size());
        resultsPage = conversionRepository.
                findByDate(date, PageRequest.of(1, 3)).getContent();
        assertThat(resultsPage).isNotEmpty();
        assertEquals(1, resultsPage.size());
    }
}