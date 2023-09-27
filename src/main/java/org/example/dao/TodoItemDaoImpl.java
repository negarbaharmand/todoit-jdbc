package org.example.dao;

import org.example.db.MySQLConnection;
import org.example.model.Person;
import org.example.model.Todo_Item;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TodoItemDaoImpl implements TodoItemsDao {
    @Override
    public Todo_Item create(Todo_Item item) {

        String query = "insert into todo_item (title, description, deadline, done, assignee_id) values (?, ?, ?, ?, ?)";

        try (Connection connection = MySQLConnection.getConnection();
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
        try (Connection connection = MySQLConnection.getConnection();
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
        return null;
    }

    @Override
    public Collection<Todo_Item> findByDoneStatus(boolean val) {
        return null;
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
        return false;
    }
}
