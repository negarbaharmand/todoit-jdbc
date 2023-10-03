package org.example.dao.impl;

import org.example.dao.TodoItemsDao;
import org.example.dao.impl.db.MySQLConnection;
import org.example.model.Person;
import org.example.model.Todo_Item;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class TodoItemDaoImpl implements TodoItemsDao {
    private Connection connection;

    public TodoItemDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Todo_Item create(Todo_Item item) {

        String query = "insert into todo_item (title, description, deadline, done, assignee_id) values (?, ?, ?, ?, ?)";

        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, item.getTitle());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDate(3, java.sql.Date.valueOf(item.getDeadline()));
            preparedStatement.setBoolean(4, item.getDone());
            preparedStatement.setInt(5, item.getAssignee_id());
            preparedStatement.executeUpdate();

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Todo items created successfully!");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedTodoId = generatedKeys.getInt(1);
                    System.out.println("generatedTodoId= " + generatedTodoId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }
        return item;
    }

    @Override
    public Collection<Todo_Item> findAll() {
        List<Todo_Item> items = new ArrayList<Todo_Item>();
        String query = "SELECT * from todo_item";
        try (
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("query");
        ) {
            while (resultSet.next()) {
                int todoId = resultSet.getInt(1);
                String title = resultSet.getString(2);
                String description = resultSet.getString(3);
                LocalDate deadline = resultSet.getDate(4).toLocalDate();
                boolean done = resultSet.getBoolean(5);
                int assignedId = resultSet.getInt(6);
                Todo_Item item = new Todo_Item(todoId, title, description, deadline, done, assignedId);
                items.add(item);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public Todo_Item findById(int id) {
        String query = "select * from todo_item where id = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);

        ) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    int todoId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    boolean done = resultSet.getBoolean(5);
                    int assignedId = resultSet.getInt(6);
                    return new Todo_Item(todoId, title, description, deadline, done, assignedId);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Collection<Todo_Item> findByDoneStatus(boolean val) {
        List<Todo_Item> items = new ArrayList<>();
        String query = "Select * from todo_item where done = ?";
        try (
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setBoolean(1, val);
            try (ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    int todoId = resultSet.getInt(1);
                    String title = resultSet.getString(2);
                    String description = resultSet.getString(3);
                    LocalDate deadline = resultSet.getDate(4).toLocalDate();
                    int assignedId = resultSet.getInt(6);
                    Todo_Item item = new Todo_Item(todoId, title, description, deadline, val, assignedId);
                    items.add(item);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
        return items;
    }


    @Override
    public Collection<Todo_Item> findByAssignee(Person per) {
        return null;
    }

    @Override
    public Collection<Todo_Item> findByUnassignedTodoItems() {
        return null;
    }

    @Override
    public Todo_Item update(Todo_Item item) {
        return null;
    }

    @Override
    public boolean deleteById(int delId) {
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from todo_item where person_id = ?")) {
            preparedStatement.setInt(1, delId);
            int rowsDeleted = preparedStatement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
