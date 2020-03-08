package org.mycompany.economics.storage;

import java.util.List;

public interface IStorage<K, V> {

    V findById(K id);

    void store(List<V> value);

    List<V> getAll();

}
