
    package com.example.homebanking.controllers;
    import com.example.homebanking.dtos.ClientDTO;
    import com.example.homebanking.models.Account;
    import com.example.homebanking.models.Client;
    import com.example.homebanking.repositories.AccountRepository;
    import com.example.homebanking.repositories.ClientRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.web.bind.annotation.*;

    import java.time.LocalDateTime;
    import java.util.List;
    import java.util.Objects;

    @RestController
    @RequestMapping("/api")
    public class ClientController {
        @Autowired
        private AccountRepository accountRepository;
        @Autowired
        private ClientRepository clientRepository;
        @Autowired
        private PasswordEncoder passwordEncoder;

        @GetMapping("/clients")
        public List<ClientDTO> getClient() {
            return clientRepository.findAll().stream().map(ClientDTO::new).toList();
        }


        @GetMapping("/clients/current")
        public ClientDTO getClient(Authentication authentication) {
           Client client = this.clientRepository.findByEmail(authentication.getName()).get();
           return new ClientDTO(client);
        }


        @GetMapping("/clients/{id}/loans")
        public ClientDTO getClientLoans(@PathVariable Long id){
            return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
        }

        @GetMapping("/clients/emailAndFirstName")
        public ClientDTO getClientByEmailAndFirstname(@RequestParam String email, @RequestParam String firstName){
            return clientRepository.findByEmailAndFirstName(email,firstName).map(ClientDTO::new).orElse(null);
        }
        @RequestMapping("/clients/email/{email}")
        public  ClientDTO getByEmailClient(@PathVariable String email){
            return clientRepository.findByEmail(email).map(ClientDTO::new).orElse(null);
        }
        @PostMapping("/clients")
        public ResponseEntity<Object> createClient(@RequestParam String firstName, @RequestParam String lastName,
                                                   @RequestParam String email, @RequestParam String password){

            if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()){
                return new ResponseEntity<>("missing data", HttpStatus.FORBIDDEN);
            }
            if(clientRepository.findByEmail(email).isPresent()){
                return  new ResponseEntity<>("UserName is already exists", HttpStatus.FORBIDDEN);
            }
            try{
                Client client =clientRepository
                        .save(new Client(firstName,lastName,email,passwordEncoder.encode(password)));
                Account account = accountRepository
                        .save(new Account(LocalDateTime.now(),0,client));
                }catch (Exception ex){
                ex.printStackTrace();
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

            }


            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }
