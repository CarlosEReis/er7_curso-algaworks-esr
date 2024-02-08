package com.er7.er7foodapi.infrastructure.repository;

import com.er7.er7foodapi.domain.repository.CustomJpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class CustoJpaRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID> {

    private EntityManager manager;

    public CustoJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.manager = entityManager;
    }

    @Override
    public Optional<T> buscarPrimeiro() {
        var jpql = "from " + getDomainClass().getName();
        T entity = this.manager
            .createQuery(jpql, getDomainClass())
            .setMaxResults(1)
            .getSingleResult();
        return Optional.ofNullable(entity);
    }

    @Override
    public void detach(T entity) {
        manager.detach(entity);
    }

}
