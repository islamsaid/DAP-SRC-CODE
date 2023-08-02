package com.asset.dailyappusermanagementservice.database.dao;

import com.asset.dailyappusermanagementservice.exception.UserManagementException;

import java.util.List;

public interface CrudDAO<T> {

  Integer add(T model) throws UserManagementException ;

    T get(Integer privilegeId) throws UserManagementException;
      int delete(Integer privilegeId)throws UserManagementException;
      int update(T privilege) throws UserManagementException;
     List<T> getAll()throws UserManagementException;

}
