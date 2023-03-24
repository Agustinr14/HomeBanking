package com.example.homebanking.Configurations;

import com.example.homebanking.models.Client;
import com.example.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;
import java.util.Optional;
// Con la web authentication logramos otorgar identificar y otorgar permisos a los logueados.
@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter {
    @Bean

    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();

    }

    @Autowired
    ClientRepository clientRepository;

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception{

        auth.userDetailsService(inputName -> {

            Optional<Client> client = clientRepository.findByEmail(inputName);
            if (client.isPresent()){
// ---------------Aca a traves de un EQUALS se otorga a un email generico a modo de prueba la autoridad de ADMIN
                if(client.get().getEmail().equals("admin@admin.com")) {//&& client.get().getPassword().equals("1234")
                    return new User(client.get().getEmail(), client.get().getPassword(),
                            AuthorityUtils.createAuthorityList("ADMIN"));
                }
                else {
                    return new User(client.get().getEmail(), client.get().getPassword(),
                            AuthorityUtils.createAuthorityList("CLIENT"));
                }
            }else{
                throw new UsernameNotFoundException("Unknown user: "+inputName);
            }
        });


    }
}
