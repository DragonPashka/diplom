package ru.crypthocurrency.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class CrypthoCurrencyEntity {

    @Column(name = "TOKKEN_ADDRESS")
    private String address;

    @Column(name = "CURRENCY_NAME")
    private String name;

    @Column(name = "COIN_BASE")
    private String coinBase;

    @Column(name = "BUY_PRICE")
    private BigDecimal buyPrice;

    @Column(name = "SELL_PRICE")
    private BigDecimal sellPrice;

    @Column(name = "DATE_MODIFIED")
    private Date dateModified;

    @Column(name = "HOLDERS")
    private BigDecimal holders;

    @Column(name = "TRANSFERS")
    private BigDecimal transfers;

    @Column(name = "SITE")
    private String site;

    @Column(name = "AVERAGE_TEN_IN_MONTH")
    private String average;

}
