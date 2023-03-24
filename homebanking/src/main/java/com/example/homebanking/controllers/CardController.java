package com.example.homebanking.controllers;

import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @GetMapping("/cards")
    public List<CardDTO> getCards(){return cardRepository.findAll().stream().map(CardDTO::new).toList();}
    @Autowired
    private ClientRepository clientRepository;
    @GetMapping("/clients/current/cards")
    public List<CardDTO> getCards(Authentication authentication) {
        Optional<Client> optionalClient = clientRepository.findByEmail(authentication.getName());
        if(optionalClient.isPresent()){
            return optionalClient.get().getCards().stream().map(CardDTO::new).collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }


    @PostMapping("clients/current/cards")
    public ResponseEntity<Object> createCard(@RequestParam CardType cardType, @RequestParam
                                            CardColor cardColor, Authentication authentication){

//------ Con este post se podra crear las tarjetas del usuario.
//------ La primera validacion que tiene es para que nos recolecte las tarjetas del cliente previamente logueado.

        Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());
        if (client.isPresent()){

//------ Una vez que tenemos el cliente validamos que no contenga mas de 3 tarjetas del mismo tipo.

            if (client.get().getCards().stream().filter(card -> card.getType() == cardType).count()>=3)
            {
            return new ResponseEntity<>("You already have 3"+cardType+ " Cards", HttpStatus.FORBIDDEN);
            }

//------ Una vez que pasamos esas validaciones procedemos a crear la tarjeta.

            cardRepository.save(new Card(cardType, cardColor, client.get()));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Missing Client", HttpStatus.FORBIDDEN);
        }

    }


}
