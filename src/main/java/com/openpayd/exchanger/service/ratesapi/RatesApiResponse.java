package com.openpayd.exchanger.service.ratesapi;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class RatesApiResponse {
    private String base;
    private Map<String, BigDecimal> rates;
    private String date;
}
