package com.logintegra.wsbbugtracker.people;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue
    Long id;

    String name;

    String username;
    String password;

}
