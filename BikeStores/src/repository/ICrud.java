package repository;

import java.util.HashMap;

public interface ICrud<K, V> {
    int create(V newItem);
    HashMap<K, V> read();
    V details(K id); //
    int update(V editItem);
    int delete(K id); 
}
