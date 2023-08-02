package com.asset.dailyapplookupservice.database.dao;


import com.asset.dailyapplookupservice.exception.LookupException;

import java.util.List;

public interface CrudDAO<T> {

  Integer add(T model) throws LookupException;

    T get(Integer privilegeId) throws LookupException;
      int delete(Integer privilegeId)throws LookupException;
      int update(T privilege) throws LookupException;
     List<T> getAll()throws LookupException;

}
