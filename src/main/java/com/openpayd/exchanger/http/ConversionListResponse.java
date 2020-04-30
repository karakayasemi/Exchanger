package com.openpayd.exchanger.http;

import com.openpayd.exchanger.entity.Conversion;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConversionListResponse extends Response {
    private Page<Conversion> conversionList;

    public ConversionListResponse(Page<Conversion> conversionList) {
        super();
        this.conversionList = conversionList;
    }
}
