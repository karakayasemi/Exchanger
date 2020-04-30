package com.openpayd.exchanger.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String fromCurrency;

    @Column
    private String toCurrency;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column
    private Date date;

    @Column
    private BigDecimal fromAmount;

    @Column
    private BigDecimal toAmount;

    public Conversion(String fromCurrency, String toCurrency, BigDecimal fromAmount, BigDecimal toAmount) {
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public Conversion() {
    }
}
