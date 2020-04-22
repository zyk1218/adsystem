package com.remember.index;
/**
  * @author remember
  * @date 2020/4/22 11:49
  */
public interface IndexAware<K,V> {
    V get(K key);

    void update(K key,V value);

    void delete(K key,V value);

    void add(K key,V value);
}
