package com.k41d.leyline.framework.service;

import javaslang.collection.Stream;
import com.k41d.leyline.framework.domain.user.LeylineUser;
import com.k41d.leyline.framework.domain.user.LeylineUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by POJO on 6/8/16.
 */
@Service
public abstract class LeylineUserDetailsService<T extends LeylineUserRepo, D extends LeylineUser>  extends LeylineDomainService<T,D> implements UserDetailsService {

    @Autowired
    private T userRepo;

    @SuppressWarnings(value = "unchecked")
    @Override
    public D loadUserByUsername(String username) throws
            UsernameNotFoundException {
        D user = (D) userRepo.findByUsernameEquals(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
        return user;
    }

    public String getPassword(D user) {
        return user.getPassword();
    }

    public Collection<? extends GrantedAuthority> getRole(D user) {
        return Stream.of(new SimpleGrantedAuthority("ROLE_USER")).toJavaList();
    }

    /**
     * 当前登录用户
     */
    public LeylineUser getCurrentUser() {
        Authentication auth =
                SecurityContextHolder.getContext().getAuthentication();
        return auth instanceof LeylineUser ? (LeylineUser) auth : null;
    }

    /**
     * 传入用户是否当前登录用户
     */
    public Boolean checkOwner(LeylineUser user) {
        return getCurrentUser() != null && getCurrentUser().getId() == (user.getId());
    }

    public Optional<D> get(Long id) {
        return userRepo.findById(id);
    }
}
