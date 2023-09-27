package org.example.model;

import org.example.dataInterfaces.TodoItems;

import java.time.LocalDate;
import java.util.Collection;

public class Todo_Item implements TodoItems {
    private int todo_id;
    private String title;
    private String description;
    LocalDate deadline ;
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
        return null;
    }

    @Override
    public Collection<Todo_Item> findAll() {
        return null;
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
