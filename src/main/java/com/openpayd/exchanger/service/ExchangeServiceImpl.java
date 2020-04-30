package com.openpayd.exchanger.service;

import com.openpayd.exchanger.entity.Conversion;
import com.openpayd.exchanger.repository.ConversionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ExchangeServiceImpl implements ExchangeService {

    protected ConversionRateService rateService;
    protected ConversionRepository repository;
    final static int scale = 5;

    @Autowired
    public ExchangeServiceImpl(ConversionRateService rateService, ConversionRepository repository) {
        this.rateService = rateService;
        this.repository = repository;
    }

    @Override
    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        List<String> list = new ArrayList<>(Arrays.asList(fromCurrency, toCurrency));
        Map<String, BigDecimal> rates = rateService.getRates(list);
        return rates.get(fromCurrency).divide(rates.get(toCurrency), scale, RoundingMode.DOWN);
    }

    @Override
    public Conversion convert(String fromCurrency, String toCurrency, BigDecimal fromAmount) {
        List<String> list = new ArrayList<>(Arrays.asList(fromCurrency, toCurrency));
        Map<String, BigDecimal> rates = rateService.getRates(list);
        BigDecimal toAmount = rates.get(fromCurrency).divide(rates.get(toCurrency), scale, RoundingMode.DOWN)
                .multiply(fromAmount);
        Conversion conversion = new Conversion(fromCurrency, toCurrency, fromAmount, toAmount);
        repository.save(conversion);
        return conversion;
    }

    @Override
    public Page<Conversion> getConversions(Long id, Date date, Pageable pageable) throws IllegalArgumentException {
        if (id != null) {
            return repository.findById(id, pageable);
        }

        if (date != null) {
            return repository.findByDate(date, pageable);
        }

        throw new IllegalArgumentException("Either Id or Date parameters should be supplied.");
    }
}
