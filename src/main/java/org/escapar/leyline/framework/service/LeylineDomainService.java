package org.escapar.leyline.framework.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.querydsl.core.types.Predicate;

import org.escapar.cms.business.domain.user.User;
import org.escapar.leyline.framework.domain.LeylineDO;
import org.escapar.leyline.framework.domain.LeylineRepo;
import org.escapar.leyline.framework.domain.user.LeylineUser;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by POJO on 5/29/16.
 */
@Service
@Transactional(rollbackFor = Throwable.class, isolation = Isolation.REPEATABLE_READ)

public abstract class LeylineDomainService<Repo extends LeylineRepo, Domain extends LeylineDO> {
    @Autowired private Repo repo;

    @Autowired private LeylineUserDetailsService userDetailsService;

    public Domain save(Domain entity) throws PersistenceException {
        try {
            return (Domain) repo.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    public List<Domain> save(Collection<Domain> entities) throws PersistenceException {
        try {
            return (List<Domain>) repo.saveAll(entities);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Collection<Domain> entities) throws PersistenceException {
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
            repo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
        return true;
    }

    @SuppressWarnings(value = "unchecked")
    public boolean delete(Domain entity) throws PersistenceException {
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
    public Optional<Domain> findById(Long id) throws PersistenceException {
        try {
            return (Optional<Domain>) repo.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }


    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Domain> findOne(Predicate id) throws PersistenceException {
        try {
            return (Optional<Domain>) repo.findOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }


    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Optional<Domain> get(Long id) throws PersistenceException {
        return findById(id);
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Domain> findAll(List<Integer> ids) throws PersistenceException {
        try {
            return (List<Domain>) repo.findAllById(ids);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Domain> findAll() throws PersistenceException {
        try {
            return (List<Domain>) repo.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Domain> findAll(Pageable p) throws PersistenceException {
        try {
            return repo.findAll(p);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Page<Domain> findAll(Predicate p, Pageable pageable) throws PersistenceException {
        try {
            return repo.findAll(p, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @SuppressWarnings(value = "unchecked")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public List<Domain> findAll(Sort s) throws PersistenceException {
        try {
            return (List<Domain>) repo.findAll(s);
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

    public Repo getRepo() {
        return repo;
    }

    public LeylineUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}
