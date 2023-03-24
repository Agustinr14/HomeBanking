package com.example.homebanking.repositories;

import com.example.homebanking.models.Transaction;
import com.example.homebanking.models.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
        List<Transaction> findByDateBetween (LocalDateTime date1, LocalDateTime date2);

       List<Transaction> findByAmountGreaterThanAndAmountLessThan(double max, double min);

        List<Transaction> findTransactionByType (TransactionType type);
}
