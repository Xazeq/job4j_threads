package ru.job4j.store;

public interface Storage {
    boolean add(User user);
    boolean update(User user);
    boolean delete(User user);
    boolean transfer(int fromId, int toId, int amount);
}
