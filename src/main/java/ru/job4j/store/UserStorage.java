package ru.job4j.store;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ThreadSafe
public class UserStorage implements Storage {
    @GuardedBy("this")
    private final Map<Integer, User> users;

    public UserStorage(Map<Integer, User> users) {
        this.users = users;
    }

    @Override
    public synchronized boolean add(User user) {
        return users.putIfAbsent(user.getId(), user) == null;
    }

    @Override
    public synchronized boolean update(User user) {
        return users.replace(user.getId(), user) != null;
    }

    @Override
    public synchronized boolean delete(User user) {
        return users.remove(user.getId(), user);
    }

    @Override
    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean result = false;
        User userFrom = users.get(fromId);
        User userTo = users.get(toId);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            result = true;
        }
        return result;
    }

    public synchronized List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    public static void main(String[] args) {
        UserStorage storage = new UserStorage(new HashMap<>());
        storage.add(new User(1, 100));
        storage.add(new User(2, 200));
        System.out.println("Before transfer " + storage.getUsers());
        storage.transfer(1, 2, 50);
        System.out.println("After transfer " + storage.getUsers());
        storage.update(new User(2, 500));
        System.out.println("After update " + storage.getUsers());
        storage.delete(new User(2, 500));
        System.out.println("After delete " + storage.getUsers());
    }
}
