package com.example.homebanking.repositories;

import com.example.homebanking.models.Client;
import com.example.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> {
    List<ClientLoan> findByClient(Long client_id);

    List<ClientLoan> findByAmountGreaterThan(double amount);

    List<ClientLoan> findByClientAndAmountLessThan(Client client, double min);
}
