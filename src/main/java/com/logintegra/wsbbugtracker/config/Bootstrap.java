package com.logintegra.wsbbugtracker.config;

import com.logintegra.wsbbugtracker.people.Person;
import com.logintegra.wsbbugtracker.people.PersonRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Bootstrap implements InitializingBean {

    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${my.admin.username}")
    private String adminUsername;

    @Value("${my.admin.password}")
    private String adminPassword;

    public Bootstrap(PersonRepository personRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void afterPropertiesSet() {
        createDefaultUser();
    }

    private void createDefaultUser() {
        if (personRepository.findByUsername(adminUsername) == null) {
            personRepository.save(new Person(adminUsername, bCryptPasswordEncoder.encode(adminPassword), adminUsername));
        }
    }
}