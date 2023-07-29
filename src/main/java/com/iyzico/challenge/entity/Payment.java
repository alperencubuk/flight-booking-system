package com.iyzico.challenge.entity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class Payment extends BaseEntity {

    private BigDecimal price;
    private String bankResponse;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getBankResponse() {
        return bankResponse;
    }

    public void setBankResponse(String bankResponse) {
        this.bankResponse = bankResponse;
    }
}
