package com.openpayd.exchanger.service;

import com.openpayd.exchanger.entity.Conversion;
import com.openpayd.exchanger.repository.ConversionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExchangeServiceImplTest {

    private ConversionRateService rateService;
    private ConversionRepository repository;
    private ExchangeServiceImpl exchangeService;

    @BeforeEach
    void setUp() {
        rateService = mock(ConversionRateService.class);
        repository = mock(ConversionRepository.class);
        exchangeService = new ExchangeServiceImpl(rateService, repository);
    }

    @Test
    void testGetExchangeRate() {
        List<String> symbols = new ArrayList<>();
        symbols.add("TRY");
        symbols.add("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", BigDecimal.valueOf(0.13));
        rates.put("USD", BigDecimal.valueOf(0.85));

        when(rateService.getRates(symbols)).thenReturn(rates);
        assertEquals(BigDecimal.valueOf(0.15294), exchangeService.getExchangeRate("TRY", "USD"));
    }

    @Test
    void testConvert() {
        List<String> symbols = new ArrayList<>();
        symbols.add("TRY");
        symbols.add("USD");
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("TRY", BigDecimal.valueOf(0.13));
        rates.put("USD", BigDecimal.valueOf(0.85));
        Conversion conversion = new Conversion(
                "TRY",
                "USD",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(1.5294)
        );

        when(rateService.getRates(symbols)).thenReturn(rates);
        when(repository.save(conversion)).thenReturn(conversion);

        assertEquals(
                conversion.getToAmount().stripTrailingZeros(),
                exchangeService.convert("TRY", "USD", BigDecimal.valueOf(10))
                        .getToAmount().stripTrailingZeros()
        );
    }

    @Test
    void testGetConversionsById() {
        Conversion conversion = mock(Conversion.class);
        ArrayList<Conversion> conversionArrayList = new ArrayList<>();
        conversionArrayList.add(conversion);
        Page<Conversion> conversions = new PageImpl<>(conversionArrayList);

        when(repository.findById(anyLong(), any())).thenReturn(conversions);
        assertEquals(exchangeService.getConversions((long)1, null, null), conversions);
        verify(repository, times(1)).findById((long) 1, null);
    }

    @Test
    void testGetConversionsByDate() {
        Conversion conversion = mock(Conversion.class);
        ArrayList<Conversion> conversionArrayList = new ArrayList<>();
        conversionArrayList.add(conversion);
        Page<Conversion> conversions = new PageImpl<>(conversionArrayList);
        Date date = mock(Date.class);

        when(repository.findByDate(eq(date), any())).thenReturn(conversions);
        assertEquals(exchangeService.getConversions(null, date, null), conversions);
        verify(repository, times(1)).findByDate(date, null);
    }

    @Test
    void testGetConversionsWithNullArgs() {
        assertThrows(IllegalArgumentException.class, () ->
                exchangeService.getConversions(null, null, mock(Pageable.class))
        );
    }
}