/**
 * @package Showcase-Migration-Quarkus
 *
 * @file Todo postgres repository
 * @copyright 2022-present Christoph Kappel <christoph@unexist.dev>
 * @version $Id$
 *
 * This program can be distributed under the terms of the Apache License v2.0.
 * See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.infrastructure.persistence;

import dev.unexist.showcase.todo.domain.todo.Todo;
import dev.unexist.showcase.todo.domain.todo.TodoRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Named("todo_jpa")
public class JPATodoRepository implements TodoRepository {

    @Inject
    EntityManager entityManager;

    @Override
    @Transactional
    public boolean add(Todo todo) {
        this.entityManager.persist(todo);

        return true;
    }

    @Override
    public boolean update(Todo todo) {
        this.entityManager.persist(todo);

        return true;
    }

    @Override
    public boolean deleteById(int id) {
        findById(id).ifPresent(todo -> this.entityManager.remove(todo));

        return true;
    }

    @Override
    public List<Todo> getAll() {
        return this.entityManager.createNamedQuery(Todo.FIND_ALL, Todo.class)
                .getResultList();
    }

    @Override
    public Optional<Todo> findById(int id) {
        return this.entityManager.createNamedQuery(Todo.FIND_BY_ID, Todo.class)
                .setParameter("id", id)
                .getResultList()
                .stream()
                .findFirst();
    }
}
