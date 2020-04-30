package com.openpayd.exchanger.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ConversionRateService {
    public Map<String, BigDecimal> getRates(List<String> symbols);
}
