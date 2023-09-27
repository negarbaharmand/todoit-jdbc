package org.example.model;

import org.example.dataInterfaces.People;

import java.util.Collection;

public class Person implements People {
    private int person_id;
    private String firstName;
    private String lastName;

    public Person( String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    public Person(int person_id, String firstName, String lastName) {
        this.person_id = person_id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getPerson_id() {
        return person_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }



}
