package com.example.homebanking.repositories;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
//El repositorio es que él comunica con la base de datos. Aca desarrollamos las diferentes querys para consultar con la base.
//El controller utiliza el repositorio para que realize tanto la búsqueda de informacion como en un GET o inyecte informacion en la base de datos con un POST, etc.
@RepositoryRestResource
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByLastNameIgnoreCase(String lastName);

    List<Client> findByFirstName(String firstName);
    Optional<Client> findByEmail(String email);

    Optional<Client> findByEmailAndFirstName(String email,String firstName);

}
