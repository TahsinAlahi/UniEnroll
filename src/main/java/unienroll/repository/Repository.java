package unienroll.repository;

import java.util.List;

// This is the base interface for all repositories
public interface Repository <T>{
    T save(T entity);
    T findById(String id);
    List<T> findAll();
    void deleteById(String id);
    boolean existsById(String id);
}
