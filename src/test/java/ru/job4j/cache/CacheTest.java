package ru.job4j.cache;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenAddToCache() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        assertTrue(cache.add(base));
        assertThat(cache.size(), is(1));
    }

    @Test
    public void whenNotAddSameBaseToCache() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 1);
        cache.add(base);
        assertFalse(cache.add(base2));
    }


    @Test
    public void whenDeleteFromCache() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.size(), is(0));
    }

    @Test
    public void whenNotDeleteFromCacheIfThereIsNoBase() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(2, 1);
        cache.add(base);
        cache.delete(base2);
        assertThat(cache.size(), is(1));
    }

    @Test(expected = OptimisticException.class)
    public void whenUpdateThenGetException() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        cache.add(base);
        Base newBase = new Base(1, 2);
        cache.update(newBase);
    }

    @Test
    public void whenUpdateBaseThenTrue() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("baseName");
        cache.add(base);
        Base newBase = new Base(1, 1);
        newBase.setName("newBaseName");
        assertTrue(cache.update(newBase));
    }

    @Test
    public void whenUpdateTwiceThenTrue() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        base.setName("base");
        Base base2 = new Base(1, 1);
        base2.setName("base2");
        Base base3 = new Base(1, 2);
        base3.setName("base3");
        cache.add(base);
        assertTrue(cache.update(base2));
        assertTrue(cache.update(base3));
    }
}