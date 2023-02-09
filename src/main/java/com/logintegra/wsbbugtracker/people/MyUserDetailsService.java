package com.logintegra.wsbbugtracker.people;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    @Autowired
    public MyUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Person person = personRepository.findByUsername(userName);
        List<GrantedAuthority> authorities = new ArrayList<>();
        return buildUserForAuthentication(person, authorities);
    }

    protected UserDetails buildUserForAuthentication(Person person, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(person.username, person.password,
                true, true, true, true, authorities);
    }
}
