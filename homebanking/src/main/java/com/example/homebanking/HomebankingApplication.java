package com.example.homebanking;

import com.example.homebanking.models.*;
import com.example.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;
import javax.tools.DocumentationTool;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomebankingApplication {
//	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {

		SpringApplication.run(HomebankingApplication.class, args);


	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
//			Client client1 = new Client("Melba", "Lorenzo", "melbalorenzo@gmail.com", passwordEncoder.encode("654321"));
//			clientRepository.save(client1);
//			Client client2 = new Client("Diego", "Ramirez", "agustinramirez@gmail.com", passwordEncoder.encode("123456"));
//			clientRepository.save(client2);
//			Account account1 = new Account(LocalDateTime.now().minusDays(1),0,client1);
//			accountRepository.save(account1);
//			Account account2 = new Account(LocalDateTime.now().minusDays(15), 0,client1);
//			accountRepository.save(account2);
//			Account account3 = new Account(LocalDateTime.now(),1000.00,client2);
//			accountRepository.save(account3);
//			Account account4 = new Account(LocalDateTime.now(),2305.22,client2);
//			accountRepository.save(account4);
//
//
//			Transaction transaction1 = new Transaction(TransactionType.CREDIT,200000,"SALARIO MENSUAL",LocalDateTime.now(),account1);
//			transactionRepository.save(transaction1);
//			Transaction transaction2 = new Transaction(TransactionType.DEBIT,2000.00,"NETFLIX",LocalDateTime.now().plusDays(1),account1);
//			transactionRepository.save(transaction2);
//			Transaction transaction3 = new Transaction(TransactionType.DEBIT,6000,"ZARA",LocalDateTime.now().minusDays(14),account2);
//			transactionRepository.save(transaction3);
//			Transaction transaction4 = new Transaction(TransactionType.CREDIT,7000,"PAGO",LocalDateTime.now().minusDays(15),account2);
//			transactionRepository.save(transaction4);
//
//
//			Loan loan1 = new Loan("Hipotecario",500000, List.of(12,24,36,48,60));
//			loanRepository.save(loan1);
//			Loan loan2 = new Loan("Auto",200000,List.of(12,24));
//			loanRepository.save(loan2);
//
//			ClientLoan clientLoan1 = new ClientLoan(3000.0,36,client1,loan1);
//			clientLoanRepository.save(clientLoan1);
//			ClientLoan clientLoan2 = new ClientLoan(15000.0,12,client1,loan2);
//			clientLoanRepository.save(clientLoan2);
//
//			Card card1 = new Card(CardType.DEBIT,CardColor.GOLD,client1);
//			cardRepository.save(card1);
//			Card card2 = new Card(CardType.CREDIT,CardColor.TITANIUM,client1);
//			cardRepository.save(card2);
//
//
//
//
////-----------------------------------------------------RE-SAVE-----------------------------------------------------
//			accountRepository.save(account1);
//			accountRepository.save(account2);
//			accountRepository.save(account3);
//			accountRepository.save(account4);
//
//		};
//	}
		};
	}
}
