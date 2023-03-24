package com.example.homebanking.repositories;

import com.example.homebanking.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface AccountRepository extends JpaRepository<Account, Long> {
         List<Account> findByBalanceGreaterThan (Double maxBalance);

         List<Account> findByCreationDateLessThan (LocalDateTime date);

         Optional<Account> findByNumber (String number);

}
