package com.openpayd.exchanger.http;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class RateResponse extends Response {
    private BigDecimal rate;

    public RateResponse(BigDecimal rate) {
        super();
        this.rate = rate;
    }
}
