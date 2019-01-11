package ru.crypthocurrency.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.crypthocurrency.tables.CrypthoCurrencyEntity;

@Repository
public interface CrypthoCurrencyRepository extends JpaRepository<CrypthoCurrencyEntity, String> {
}
