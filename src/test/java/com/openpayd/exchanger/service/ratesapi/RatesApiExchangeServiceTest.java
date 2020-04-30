package com.openpayd.exchanger.service.ratesapi;

import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RatesApiExchangeServiceTest {

    private RatesApiExchangeService ratesApiService;
    private RestTemplate restTemplate;

    @Test
    void testGetRatesSuccess() {
        List<String> symbols = new ArrayList<>();
        symbols.add("TRY");
        symbols.add("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", BigDecimal.valueOf(0.13));
        rates.put("USD", BigDecimal.valueOf(0.85));

        RatesApiResponse response = mock(RatesApiResponse.class);
        when(response.getRates()).thenReturn(rates);
        restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.eq(RatesApiResponse.class))).thenReturn(response);
        RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        ratesApiService = new RatesApiExchangeService(restTemplateBuilder);

        assertEquals(rates, ratesApiService.getRates(symbols));
    }

    @Test
    void testGetRatesThrowsException() {
        List<String> arr = new ArrayList<>();
        arr.add("EUR");
        arr.add("USD");

        restTemplate = mock(RestTemplate.class);
        when(restTemplate.getForObject(anyString(), ArgumentMatchers.eq(RatesApiResponse.class))).thenReturn(null);
        RestTemplateBuilder restTemplateBuilder = mock(RestTemplateBuilder.class);
        when(restTemplateBuilder.build()).thenReturn(restTemplate);
        ratesApiService = new RatesApiExchangeService(restTemplateBuilder);

        assertThrows(InvalidRequestStateException.class, () -> ratesApiService.getRates(arr));
    }
}