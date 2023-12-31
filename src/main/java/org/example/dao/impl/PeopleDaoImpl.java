package org.example.dao.impl;

import org.example.dao.PeopleDao;
import org.example.dao.impl.db.MySQLConnection;
import org.example.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PeopleDaoImpl implements PeopleDao {
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
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from Person where person_id = ? ");
        ) {
            preparedStatement.setInt(1, id);
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()) {
                Person person = null;
                if (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    person = new Person(id, firstName, lastName);
                }
                return person;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Person> findByName(String name) {
        List<Person> people = new ArrayList<Person>();
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from Person where first_name = ? ");
        ) {
            preparedStatement.setString(1, "name");
            try (
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int personId = resultSet.getInt(1);
                    String firstName = resultSet.getString(2);
                    String lastName = resultSet.getString(3);
                    people.add(new Person(personId, firstName, lastName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return people;
    }

    @Override
    public Person update(Person person) {

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update person set first_name = ?, last_name = ? where person_id = ?")) {

            preparedStatement.setString(1, person.getFirstName());
            preparedStatement.setString(2, person.getLastName());
            preparedStatement.setInt(3, person.getPerson_id());

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated == 1) {
                System.out.println("Person with id: " + person.getPerson_id() + "was successfully updated.");
                return person;
            } else {
                System.out.println("Person with id: " + person.getPerson_id() + " was not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }


    @Override
    public boolean deleteById(int id) {
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from person where person_id = ?")) {
            preparedStatement.setInt(1, id);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
