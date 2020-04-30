package com.openpayd.exchanger.http;

import com.openpayd.exchanger.entity.Conversion;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConversionResponse extends Response {
    private Long id;
    private BigDecimal amount;

    public ConversionResponse(Conversion conversion) {
        super();
        id = conversion.getId();
        amount = conversion.getToAmount();
    }
}
