package ru.job4j.cache;

import net.jcip.annotations.ThreadSafe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ThreadSafe
public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        Base result = memory.computeIfPresent(model.getId(), (key, value) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            String name = model.getName();
            value = new Base(stored.getId(), stored.getVersion() + 1);
            value.setName(name);
            return value;
        });
        return result != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public int size() {
        return memory.size();
    }
}
