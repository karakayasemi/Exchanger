package com.openpayd.exchanger.service.ratesapi;

import com.openpayd.exchanger.service.ConversionRateService;
import com.sun.jdi.request.InvalidRequestStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.util.*;

@Service
public class RatesApiExchangeService implements ConversionRateService {

    private final RestTemplate restTemplate;
    private static final String url = "https://api.ratesapi.io/api/latest";
    private static final String baseCurrency = "EUR";

    @Autowired
    RatesApiExchangeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Map<String, BigDecimal> getRates(List<String> symbols) {
        // RatesApi throws exception when base currency send as a symbol,
        // if it is asked, do not send base currency and, take its value as 1
        boolean containsBaseCurrency = false;
        if (symbols.contains(baseCurrency)) {
            symbols.remove(baseCurrency);
            containsBaseCurrency = true;
        }
        String symbolsString = String.join(",", symbols);
        String uri = UriComponentsBuilder.fromUriString(url).queryParam("symbols", symbolsString).toUriString();
        RatesApiResponse response = this.restTemplate.getForObject(uri, RatesApiResponse.class);
        if (response == null || response.getRates().size() < symbols.size()) {
            throw new InvalidRequestStateException("Conversion rate service response is invalid");
        }
        Map<String, BigDecimal> resultRates = response.getRates();
        if (containsBaseCurrency) {
            resultRates.put(baseCurrency, BigDecimal.ONE);
        }
        return resultRates;
    }
}
