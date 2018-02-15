package com.k41d.cms.business.service;

import java.util.Collection;

import com.k41d.cms.business.domain.user.User;
import com.k41d.cms.business.domain.user.UserRepo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;

import com.k41d.leyline.framework.service.LeylineUserDetailsService;

@Service
public class UserService extends LeylineUserDetailsService<UserRepo,User> {


    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public User checkAndGet(String username, String password) {
        return repo.checkAndGet(username, password);
    }
    //
    //    public User reg(User reg) throws PersistenceException {
    //
    //        User user = save(
    //                reg
    //                        .setUnHashedPassword(reg.getPassword())
    //                        .setRole(ROLE_CONSTS.ROLE_UNCHECKED_USER));
    //
    //        websiteService.save(
    //                new Website()
    //                        .setCreatedAt(new DateTime().getMillis())
    //                        .setDomain(reg.getDomain())
    //                        .setTitle(reg.getDomain())
    //                        .setVerifyKey(RandomStringUtils.random(7))
    //                        .setUser(user));
    //
    //        return user;
    //    }
    //
    //    public User verify(User user) throws PersistenceException {
    //        return save(user.setG(ROLE_CONSTS.ROLE_USER));
    //    }


    @Override
    public Collection<? extends GrantedAuthority> getRole(User user) {
        return User.getRole(user);
    }



    public User getByClaims(Claims c) throws Exception {
        return c == null ? null : repo.findOne(Long.valueOf((Integer)c.get("id")));
    }
}
