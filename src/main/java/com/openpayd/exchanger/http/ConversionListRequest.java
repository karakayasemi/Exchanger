package com.openpayd.exchanger.http;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConversionListRequest extends Request {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;

    private Long id;
    private Pageable pageable;

    public ConversionListRequest(Date date, Long id, Pageable pageable) {
        super();
        this.date = date;
        this.id = id;
        this.pageable = pageable;
    }
}
