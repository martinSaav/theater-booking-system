package com.theater.booking.interfaces;


import com.theater.booking.model.Base;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.io.Serializable;


public interface IBaseController<E extends Base, ID extends Serializable> {

    ResponseEntity<?> getAllRecord();

    ResponseEntity<?> getRecordById(@PathVariable ID id);

    ResponseEntity<?> save(@RequestBody E entity);

    ResponseEntity<?> update(@PathVariable ID id, @RequestBody E entity);

    ResponseEntity<?> delete(@PathVariable ID id);

}