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

    @Column(name = "CURRENCY_NAME")
    private String name;

    @Column(name = "SHORTt_NAME")
    private String shortName;

    @Column(name = "TOKKEN_ADDRESS")
    private String address;

    @Column(name = "ETHERSCAN")
    private String etherscan;

    @Column(name = "COIN_BASE")
    private String coinBase;

    @Column(name = "ETHERDELTA")
    private String etherdelta;

    @Column(name = "FORKDELTA")
    private String forkdelta;

    @Column(name = "HOLDERS")
    private BigDecimal holders;

    @Column(name = "TRANSFERS")
    private BigDecimal transfers;

    @Column(name = "SITE")
    private String site;

}
