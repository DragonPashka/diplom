package ru.crypthocurrency.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crypthocurrency.tables.CrypthoCurrencyHistoryEntity;

@Repository
public interface CrypthoCurrencyHistoryRepository extends JpaRepository<CrypthoCurrencyHistoryEntity, Long> {
}
