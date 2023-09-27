package org.example.model;

import org.example.dataInterfaces.TodoItems;
import org.example.db.MySQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Todo_Item implements TodoItems {
    private int todo_id;
    private String title;
    private String description;
    LocalDate deadline;
    private boolean done;
    private int assignee_id;

    public Todo_Item(int todo_id, String title, String description, LocalDate deadline, boolean done, int assignee_id) {
        this.todo_id = todo_id;
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.done = done;
        this.assignee_id = assignee_id;
    }

    public Todo_Item(String title, String description, LocalDate deadline, int assignee_id) {
        this.title = title;
        this.description = description;
        this.deadline = deadline;
        this.assignee_id = assignee_id;

    }

    public int getTodo_id() {
        return todo_id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public boolean getDone() {
        return done;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    @Override
    public String toString() {
        return "Todo_Item{" +
                "todo_id=" + todo_id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", done=" + done +
                ", assignee_id=" + assignee_id +
                '}';
    }

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
