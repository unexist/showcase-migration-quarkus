/**
 * @package Showcase
 * @file
 * @copyright 2022 Christoph Kappel <christoph@unexist.dev>
 * @version $Id\$
 *
 *         This program can be distributed under the terms of the Apache License v2.0.
 *         See the file LICENSE for details.
 **/

package dev.unexist.showcase.todo.infrastructure.persistence;

import dev.unexist.showcase.todo.domain.todo.DueDate;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

public class DueDateType implements UserType {

    @Override
    public int[] sqlTypes() {
        return new int[]{ Types.DATE, Types.DATE };
    }

    @Override
    public Class<?> returnedClass() {
        return DueDate.class;
    }

    @Override
    public boolean equals(Object o1, Object o2) throws HibernateException {
        return o1.equals(o2);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SharedSessionContractImplementor session, Object owner)
            throws HibernateException, SQLException
    {
        if (resultSet.wasNull()) {
            return null;
        }

        LocalDate start = resultSet.getDate(names[0]).toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

        LocalDate due = resultSet.getDate(names[1]).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        return new DueDate(start, due);
    }

    @Override
    public void nullSafeSet(PreparedStatement prepStatement, Object value, int idx,
                            SharedSessionContractImplementor session)
            throws HibernateException, SQLException
    {
        if (Objects.isNull(value)) {
            throw new SQLException("Start and due cannot be null");
        } else {
            DueDate dueDate = (DueDate)value;

            prepStatement.setDate(idx, Date.valueOf(dueDate.getStart()));
            prepStatement.setDate(idx + 1, Date.valueOf(dueDate.getDue()));
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return null;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return null;
    }

    @Override
    public Object assemble(Serializable serializable, Object o)
            throws HibernateException
    {
        return null;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2)
            throws HibernateException
    {
        return null;
    }
}
