package com.openpayd.exchanger.service;

import com.openpayd.exchanger.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;

public interface ExchangeService {
    BigDecimal getExchangeRate(String from, String to);
    Conversion convert(String from, String to, BigDecimal amount);
    Page<Conversion> getConversions(Long id, Date date, Pageable pageable);
}
