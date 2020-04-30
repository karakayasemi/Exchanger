package com.openpayd.exchanger.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = false)
@Data
public class RateRequest extends Request {

    @NotBlank
    @Length(min =  3, max = 3, message = "From length must be 3")
    private String from;

    @NotBlank
    @Length(min =  3, max = 3, message = "To length must be 3")
    private String to;

    public RateRequest(String from, String to) {
        super();
        this.from = from;
        this.to = to;
    }
}
