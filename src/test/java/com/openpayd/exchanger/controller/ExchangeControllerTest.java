package com.openpayd.exchanger.controller;

import com.openpayd.exchanger.entity.Conversion;
import com.openpayd.exchanger.http.*;
import com.openpayd.exchanger.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeControllerTest {

    private ExchangeController controller;
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        exchangeService = mock(ExchangeService.class);
        this.controller = new ExchangeController(exchangeService);
    }

    @Test
    void testGetConversionRate() {
        RateRequest request = new RateRequest("USD", "TRY");
        when(exchangeService.getExchangeRate("USD", "TRY")).thenReturn(BigDecimal.valueOf(0.14222));

        assertEquals(controller.getConversionRate(request), new RateResponse(BigDecimal.valueOf(0.14222)));
        verify(exchangeService, times(1)).getExchangeRate("USD", "TRY");
    }

    @Test
    void testConvert() {
        ConversionRequest request = new ConversionRequest("USD", "TRY", BigDecimal.valueOf(10));
        Conversion conversion = new Conversion(
                "USD",
                "TRY",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1.4220)
        );
        when(exchangeService.convert("USD", "TRY", BigDecimal.valueOf(10))).thenReturn(conversion);

        assertEquals(controller.convert(request), new ConversionResponse(conversion));
        verify(exchangeService, times(1)).convert("USD", "TRY", BigDecimal.valueOf(10));
    }

    @Test
    void testGetConversionList() {
        Pageable pageable = mock(Pageable.class);
        Date date = mock(Date.class);
        ConversionListRequest request = new ConversionListRequest(date, (long) 1, pageable);
        when(exchangeService.getConversions((long) 1, date, pageable)).thenReturn(Page.empty());

        assertEquals(controller.getConversionList(request), new ConversionListResponse(Page.empty()));
        verify(exchangeService, times(1)).getConversions((long) 1, date, pageable);
    }
}