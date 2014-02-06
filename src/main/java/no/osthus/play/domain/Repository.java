package no.osthus.play.domain;

import com.google.common.base.Optional;

public interface Repository<T> {
    T save(T entity);

    Optional<T> findOne(Long id);

    void remove(T entity);

    Iterable<T> findAll();

    Long count();
}
