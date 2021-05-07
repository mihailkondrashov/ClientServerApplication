package com.kondrashov.server.repositories.interfaces;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface EntityRepository<E,K> {

    Collection<E> findAll() throws SQLException;
    Optional<E> find(K id);
    Optional<E> update(E entity);
    boolean delete(K id);
    Long save(E entity) throws SQLException;

}
