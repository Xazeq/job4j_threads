package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private final List<User> users = new ArrayList<>();

    @Override
    public synchronized boolean add(User user) {
        return users.add(user);
    }

    @Override
    public synchronized boolean update(User user) {
        boolean result = false;
        int index = users.indexOf(user);
        if (index != -1) {
            users.set(index, user);
            result = true;
        }
        return result;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user);
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = findById(fromId);
        User userTo = findById(toId);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            result = true;
        }
        return result;
    }

    private synchronized User findById(int id) {
        for (var user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public synchronized List<User> getUsers() {
        return users;
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        storage.transfer(1, 2, 50);
        System.out.println(storage.getUsers());
    }
}
