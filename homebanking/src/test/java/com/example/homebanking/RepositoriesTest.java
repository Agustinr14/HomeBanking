package com.example.homebanking;

import Utils.CardsUtils;
import com.example.homebanking.models.Account;
import com.example.homebanking.models.Card;
import com.example.homebanking.models.Client;
import com.example.homebanking.models.Loan;
import com.example.homebanking.repositories.AccountRepository;
import com.example.homebanking.repositories.CardRepository;
import com.example.homebanking.repositories.ClientRepository;
import com.example.homebanking.repositories.LoanRepository;
import org.hamcrest.text.CharSequenceLength;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@DataJpaTest

@AutoConfigureTestDatabase(replace = NONE)

public class RepositoriesTest {



    @Autowired

    LoanRepository loanRepository;



    @Test

    public void existLoans(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans,is(not(empty())));

    }



    @Test

    public void existPersonalLoan(){

        List<Loan> loans = loanRepository.findAll();

        assertThat(loans, hasItem(hasProperty("name", is("Auto"))));

    }


@Autowired
    ClientRepository clientRepository;

    @Test
    public void containsChart(){
        List<Client> client = clientRepository.findAll();
        assertThat(client,everyItem(hasProperty("email",containsString("@"))));
    }

    @Test
    public void clientHasEmail(){
        List<Client> client = clientRepository.findAll();
        assertThat(client,everyItem(hasProperty("email", not(emptyString()))));
    }

    @Autowired
    CardRepository cardRepository;

    @Test
    public void cardNumberEmpty(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, everyItem(hasProperty("number", not(emptyString()))));

    }
    @Test
    public void notCvvGenerate(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, everyItem(hasProperty("cvv", not(emptyString()))));
    }

    @Autowired
    AccountRepository accountRepository;


    @Test
    public void createdDateLessThanNow(){
        String date = "2022-11-16 00:00:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Account> accounts = accountRepository.findByCreationDateLessThan(LocalDateTime.parse(date, formatter));
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void emptyAccountNumber(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,everyItem(hasProperty("number",not(emptyString()))));
    }

//    ------------------TEST UNITARIOS------------------------------

    @Test
    public void generateRandomNumber(){
        String cardNumber = CardsUtils.generateNumber(6);
        assertThat(cardNumber, CharSequenceLength.hasLength(6));
    }
}