package com.openpayd.exchanger.controller;

import com.openpayd.exchanger.entity.Conversion;
import com.openpayd.exchanger.http.*;
import com.openpayd.exchanger.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping(path="/api/v1")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @GetMapping(value = "/rate")
    public RateResponse getConversionRate(@Valid RateRequest request) {
        BigDecimal result = exchangeService.getExchangeRate(request.getFrom(), request.getTo());
        return new RateResponse(result);
    }

    @GetMapping(value = "/convert")
    public ConversionResponse convert(@Valid ConversionRequest request) {
        Conversion result = exchangeService.convert(request.getFrom(), request.getTo(), request.getAmount());
        return new ConversionResponse(result);
    }

    @GetMapping(value = "/conversionList")
    public ConversionListResponse getConversionList(@Valid ConversionListRequest request) {
        Page<Conversion> result = exchangeService.getConversions(request.getId(), request.getDate(), request.getPageable());
        return new ConversionListResponse(result);
    }
}
