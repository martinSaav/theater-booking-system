package com.theater.booking.interfaces;


import com.theater.booking.model.Base;
import java.io.Serializable;
import java.util.List;


public interface IBaseService<E extends Base, ID extends Serializable> {

    List<E> findAll();

    E findById(ID id);

    E save(E entity);

    E update(ID id, E entity);

    boolean delete(ID id);

}