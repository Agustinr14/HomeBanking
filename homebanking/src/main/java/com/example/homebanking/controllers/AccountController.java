package com.example.homebanking.controllers;

import com.example.homebanking.dtos.AccountDTO;
import com.example.homebanking.dtos.CardDTO;
import com.example.homebanking.dtos.ClientDTO;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccount() {
        return accountRepository.findAll().stream().map(AccountDTO::new).toList();
    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id) {
        return accountRepository.findById(id).map(AccountDTO::new).orElse(null);
    }
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Optional<Client> optionalClient = clientRepository.findByEmail(authentication.getName());
        if (optionalClient.isPresent()) {
            return optionalClient.get().getAccount().stream().map(AccountDTO::new).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication) {
      Optional<Client> client = this.clientRepository.findByEmail(authentication.getName());
      if(client.isPresent()){
          if (client.get().getAccount().stream().count()>=3){
              return new ResponseEntity<>("You already have 3 accounts",HttpStatus.FORBIDDEN);
          }
          try{
              accountRepository.save(new Account(LocalDateTime.now(),0.0,client.get()));
              return new ResponseEntity<>(HttpStatus.CREATED);
          }catch (Exception ex){
              ex.printStackTrace();
              return new ResponseEntity<>("Unexpected error",HttpStatus.INTERNAL_SERVER_ERROR);
          }
      }else{
          return new ResponseEntity<>("Client doesn't exist",HttpStatus.FORBIDDEN);
      }
    }

}
//    --------------------------




