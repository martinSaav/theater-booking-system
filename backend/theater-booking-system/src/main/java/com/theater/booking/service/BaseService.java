package com.theater.booking.service;


import com.theater.booking.interfaces.BaseRepository;
import com.theater.booking.interfaces.IBaseService;
import com.theater.booking.model.Base;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import java.io.Serializable;
import java.util.List;


public abstract class BaseService<E extends Base, ID extends Serializable> implements IBaseService<E, ID> {

    protected BaseRepository<E, ID> baseRepository;

    public BaseService(BaseRepository<E, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    @Override
    public List<E> findAll() {
        return baseRepository.findAll();
    }


    @Override
    public E findById(ID id) {
        return findByIdAux(id);
    }

    @Transactional
    @Override
    public E save(E entity) {
        return baseRepository.save(entity);
    }

    @Transactional
    @Override
    public E update(ID id, E entity) {
        if (entity.getId() != null && !id.equals(entity.getId())) {
            throw new IllegalArgumentException("El id del body no coincide con el id de la url");
        }
        E entityFound = findByIdAux(id);
        BeanUtils.copyProperties(entity, entityFound, "id");
        return baseRepository.save(entityFound);
    }

    @Transactional
    @Override
    public boolean delete(ID id) {
        E entityFound = findByIdAux(id);
        baseRepository.delete(entityFound);
        return true;
    }

    private E findByIdAux(ID id) {
        return baseRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("La entidad con el id: " + id + " no existe"));
    }
}