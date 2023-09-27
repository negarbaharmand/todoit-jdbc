package org.example.model;

import org.example.dataInterfaces.People;
import org.example.db.MySQLConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Person implements People {
    private int person_id;
    private String firstName;
    private String lastName;

    public Person(String firstName, String lastName) {
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

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    @Override
    public Person create(Person person) {
        String query = "insert into person(first_name, last_name) values (?, ?)";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.executeUpdate();

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("People created successfully!");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedPersonId = generatedKeys.getInt(1);
                    System.out.println("generatedPersonId= " + generatedPersonId);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return person;
    }

    @Override
    public Collection<Person> findAll() {
        List<Person> people = new ArrayList<Person>();
        String query = "select * from person";
        try (Connection connection = MySQLConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("query");
        ) {
            while (resultSet.next()) {
                int personId = resultSet.getInt(1);
                String firstName = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Person person = new Person(personId, firstName, lastName);
                people.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person findById(int id) {
        return null;
    }

    @Override
    public Collection<Person> findByName(String name) {
        return null;
    }

    @Override
    public Person update(Person person) {
        return null;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }
}
