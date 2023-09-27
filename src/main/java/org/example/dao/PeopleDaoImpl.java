package org.example.dao;

import org.example.db.MySQLConnection;
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
