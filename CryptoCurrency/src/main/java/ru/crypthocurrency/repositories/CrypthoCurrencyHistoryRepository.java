package ru.crypthocurrency.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

import java.util.List;

@Repository
public interface CrypthoCurrencyHistoryRepository extends JpaRepository<CrypthoCurrencyHistoryEntity, Long> {

    List<CrypthoCurrencyHistoryEntity> findCrypthoCurrencyHistoryEntityByCurrencyEntity(CrypthoCurrencyEntity currencyEntity);
}
