package com.example.homebanking.models;

import Utils.CardsUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name= "native", strategy = "native")
    private Long id;
    private String cardHolder;
    private CardType type;
    private CardColor color;
    private String number;
    private String cvv;
    private LocalDate fromDate;
    private LocalDate thruDate;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;


    public Card() {
    }

    public Card(Client client, CardType type, CardColor color, String number, LocalDate fromDate) {
        this.cardHolder = client.getFirstName()+ " "+ client.getLastName();
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = CardsUtils.generateCvv(3);
        this.fromDate = fromDate;
        this.thruDate = fromDate.plusYears(5);
        this.client = client;
    }

    public Card(CardType type, CardColor color, Client client) {
        this.cardHolder = client.getFirstName()+ " "+ client.getLastName();
        this.type = type;
        this.color = color;
        this.fromDate = LocalDate.now();
        this.client = client;
        this.cvv = CardsUtils.generateCvv(3);
        this.number = CardsUtils.generateNumber(4)+ "-"+ CardsUtils.generateNumber(4)+"-"+CardsUtils.generateNumber(4)+"-"+CardsUtils.generateNumber(4);
        this.thruDate = LocalDate.now().plusYears(5);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }





}
