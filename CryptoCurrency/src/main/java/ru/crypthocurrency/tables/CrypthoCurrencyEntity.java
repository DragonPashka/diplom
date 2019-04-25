package ru.crypthocurrency.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class CrypthoCurrencyEntity {

    @Id
    @Column(name = "TOKKEN_ADDRESS")
    private String address;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "HOLDERS")
    private BigDecimal holders;

    @Column(name = "TRANSFERS")
    private BigDecimal transfers;

    @Column(name = "SITE")
    private String site;

}
