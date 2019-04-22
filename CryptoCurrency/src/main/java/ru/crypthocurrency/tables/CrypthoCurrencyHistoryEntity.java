package ru.crypthocurrency.tables;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity
public class CrypthoCurrencyHistoryEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long Id;

    @Column(name = "DATE")
    private Date date;

    @Column(name = "PRICE_USD")
    private BigDecimal priceUSD;

    @Column(name = "TRADES_COUNT")
    private BigDecimal tradesCount;

    @Column(name = "VOLUME_TRADED")
    private BigDecimal volumeTraded;

    @ManyToOne
    @JoinColumn(name = "TOKKEN_ADDRESS")
    private CrypthoCurrencyEntity currencyEntity;




}
