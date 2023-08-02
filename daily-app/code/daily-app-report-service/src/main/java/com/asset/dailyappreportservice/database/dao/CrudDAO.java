package com.asset.dailyappreportservice.database.dao;



import com.asset.dailyappreportservice.exception.ReportsException;

import java.util.List;

public interface CrudDAO<T> {

    Integer add(T model) throws ReportsException;

    T get(Integer privilegeId) throws ReportsException;

    int delete(Integer privilegeId) throws ReportsException;

    int update(T privilege) throws ReportsException;

    List<T> getAll() throws ReportsException;

}
