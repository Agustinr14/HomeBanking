package com.example.homebanking.controllers;

import com.example.homebanking.dtos.TransactionDTO;
import com.example.homebanking.models.*;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/transactions")
    public List<TransactionDTO> getTransaction() {
        return transactionRepository.findAll().stream().map(TransactionDTO::new).toList();
    }

    @GetMapping("/transactions/{id}")
    public TransactionDTO getTransaction(@PathVariable Long id) {
        return transactionRepository.findById(id).map(TransactionDTO::new).orElse(null);
    }

    //    -------------------
    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam Double amount, @RequestParam String description,
                                                    @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication) {

        if (amount.isNaN()||description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        Optional<Account> optionalToAccount = accountRepository.findByNumber(toAccountNumber);
        Optional<Account> optionalFromAccount = accountRepository.findByNumber(fromAccountNumber);

        Optional<Client> client = clientRepository.findByEmail(authentication.getName());

//        if (client.get().getAccount().stream().noneMatch(account -> account.getNumber().equals(fromAccountNumber))) {
//            return new ResponseEntity<>("This account doesn't belong to you.", HttpStatus.FORBIDDEN);
//        }
        if(optionalToAccount.isPresent() && optionalFromAccount.isPresent()){

            if (accountRepository.findByNumber(optionalFromAccount.get().getNumber()).equals(optionalToAccount.get().getNumber())) {
                return new ResponseEntity<>("The accounts are identical", HttpStatus.FORBIDDEN);
            }
                if(amount>optionalFromAccount.get().getBalance()){
                    return new ResponseEntity<>("Exceeded amount available( "+optionalFromAccount.get().getBalance() + ")",HttpStatus.FORBIDDEN);
                }

                Transaction transactionCredit = transactionRepository.save(new Transaction(TransactionType.CREDIT,amount,description+" - "+optionalFromAccount.get().getNumber(), LocalDateTime.now(),optionalToAccount.get()));
                Transaction transactionDebit = transactionRepository.save(new Transaction(TransactionType.DEBIT,amount,description+" - "+optionalToAccount.get().getNumber(), LocalDateTime.now(),optionalFromAccount.get()));
                return new ResponseEntity<>("Successful transaction", HttpStatus.CREATED);

                } else{
                    return new ResponseEntity<>("One of the accounts is missing", HttpStatus.FORBIDDEN);
                }
        }



    }


