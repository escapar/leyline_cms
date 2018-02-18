package com.k41d.leyline.framework.service;

import com.k41d.cms.business.domain.user.User;
import com.querydsl.core.types.Predicate;
import com.k41d.leyline.framework.domain.LeylineDO;
import com.k41d.leyline.framework.domain.LeylineRepo;
import com.k41d.leyline.framework.domain.user.LeylineUser;
import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class, isolation = Isolation.REPEATABLE_READ)

public abstract class LeylineDomainService<T extends LeylineRepo, E extends LeylineDO> {
    @Autowired
    protected T repo;

    @Autowired
    protected LeylineUserDetailsService userDetailsService;

    public LeylineDomainService(T repo , LeylineUserDetailsService userDetailsService){
        this.repo = repo;
        this.userDetailsService = userDetailsService;
    }

    public LeylineDomainService(){

    }

    @SuppressWarnings(value = "unchecked")
    protected static List<Map<String, Object>> resMap(String[] params, Iterable res) {
        List resultList = new ArrayList<Map<String, Object>>();
        if (params != null && params.length > 0 && res != null) {
            for (Object i : res) {
                int c = 0;
                HashMap resMap = new HashMap();
                for (Object e : (Object[]) i) {
                    resMap.put(params[c++], e);
                }
                resultList.add(resMap);
            }
        }
        return resultList;
    }

    public E save(E entity) throws PersistenceException {
        try {
            return (E) repo.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<E> save(Collection<E> entities) throws PersistenceException {
        try {
            return (List<E>) repo.saveAll(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Collection<E> entities) throws PersistenceException {
        try {
            repo.deleteAll(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Long id) throws PersistenceException {
        try {
            repo.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(E entity) throws PersistenceException {
        try {
            repo.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<E> findById(Long id) throws PersistenceException {
        try {
            return (Optional<E>) repo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }


    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<E> findOne(Predicate id) throws PersistenceException {
        try {
            return (Optional<E>) repo.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }


    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<E> get(Long id) throws PersistenceException {
        return findById(id);
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAllById(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll() throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<? extends E> findAll(Pageable p) throws PersistenceException {
        try {
            return repo.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<? extends E> findAll(Predicate p, Pageable pageable) throws PersistenceException {
        try {
            return repo.findAll(p, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<? extends E> findAll(Sort s) throws PersistenceException {
        try {
            return (List<? extends E>) repo.findAll(s);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    public LeylineUser getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public Boolean checkOwnerOf(LeylineUser u) {
        return getCurrentUser() == null ? null : getCurrentUser().getUsername().equals(u.getName());
    }

    public Boolean checkOwnerOf(User u) {
        return getCurrentUser() != null && getCurrentUser().getUsername().equals(u.getUsername());
    }

}
