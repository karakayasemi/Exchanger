package com.openpayd.exchanger.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConversionRequest extends Request {

    @NotBlank
    @Length(min =  3, max = 3, message = "From length must be 3")
    private String from;

    @NotBlank
    @Length(min =  3, max = 3, message = "To length must be 3")
    private String to;

    @NotNull
    @DecimalMin(value = "0", inclusive = false)
    private BigDecimal amount;

    public ConversionRequest(String from, String to, BigDecimal amount) {
        super();
        this.from = from;
        this.to = to;
        this.amount = amount;
    }
}
