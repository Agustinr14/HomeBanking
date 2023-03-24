package com.example.homebanking.controllers;

import com.example.homebanking.dtos.LoanApplicationDTO;
import com.example.homebanking.dtos.LoanDTO;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import com.sun.xml.txw2.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @GetMapping("/loans")
    public List<LoanDTO> getLoan(){
        return loanRepository.findAll().stream().map(LoanDTO::new).toList();
    }

//    ----------------------------------
    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<String> createClientLoan (@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
        try{
            Client client = clientRepository.findByEmail(authentication.getName()).get();
            Optional<Loan> loan = loanRepository.findById(loanApplicationDTO.getLoanId());
            Optional<Account> account = accountRepository.findByNumber(loanApplicationDTO.getToAccountNumber());

//----->Validar que se introduzcan los datos en los inputs
            if (loanApplicationDTO.getLoanId() == 0 || loanApplicationDTO.getAmount().isNaN() || loanApplicationDTO.getPayments() == 0 || loanApplicationDTO.getToAccountNumber().isEmpty()) {
                return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
            }
//----->Validar que la cantidad que pide no exceda el maximo que se otorga
            if (loanApplicationDTO.getAmount() > loan.get().getMaxAmount()) {
                return new ResponseEntity<>("Exceeds Amount ", HttpStatus.FORBIDDEN);
            }
//------>Valida que se seleccione la cantidad de cuotas disponibles
            if (!loan.get().getPayments().contains(loanApplicationDTO.getPayments())) {
                return new ResponseEntity<>("Wrong amount of payments", HttpStatus.FORBIDDEN);
            }
//----->Valida que el numero de cuenta introducido sea correspondiente a una account de la cuenta.
            if (!authentication.getName().equals(account.get().getClient().getEmail())) {
                return new ResponseEntity<>("This account is wrong!", HttpStatus.FORBIDDEN);
            }
//------Como el prestamo tiene una tasa de 20% lo que hace esta funcion es aplicarle ese porcentaje cuando el cliente elije el amount.

            double loanFee = ((loanApplicationDTO.getAmount() * 0.20) + loanApplicationDTO.getAmount());
//            ----->Ahora se procede a realizar el prestamo que incluye una transaction hacia la cuenta del cliente y por supuesto el registro
//                  del prestamo con la cantidad de cuotas, el tipo de prestamo seleccionado y el total a pagar.

            Transaction transaction = new Transaction(TransactionType.CREDIT,loanApplicationDTO.getAmount(),loan.get().getName()+ " loan approved",LocalDateTime.now(),account.get());
            transactionRepository.save(transaction);
            ClientLoan clientLoan =clientLoanRepository
                                    .save(new ClientLoan(loanFee,loanApplicationDTO.getPayments(),client,loan.get()));
            return new ResponseEntity<>("Loan approved", HttpStatus.CREATED);

        }catch(Exception ex){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new ResponseEntity<>("Unexpected", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/admin/loans")
        public ResponseEntity<Object>createLoan(Authentication authentication, @RequestBody Loan loan){
            Client client = clientRepository.findByEmail(authentication.getName()).get();
            if (!client.getEmail().contains("@admin")){
                return new ResponseEntity<>("Access denied", HttpStatus.FORBIDDEN);
            }
            Loan loan1 = new Loan(loan.getName(),loan.getMaxAmount(),loan.getPayments());
            loanRepository.save(loan1);
            return new ResponseEntity<>("Loan created",HttpStatus.CREATED);
    }

}
