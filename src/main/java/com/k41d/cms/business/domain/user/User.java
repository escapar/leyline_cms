package com.k41d.cms.business.domain.user;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;
import com.k41d.cms.business.infrastructure.security.ROLE_CONSTS;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.k41d.leyline.framework.domain.user.LeylineUser;
import com.k41d.leyline.framework.interfaces.view.LeylineView;

public class User implements Serializable,LeylineUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(LeylineView.LIST.class)

    private long id;

    @Column(name="created_at")
    private DateTime createdAt;

    @Column(name="birthday")
    private DateTime birthday;

    @Column(name="mail")
    private String mail;

    @Column(name="name")
    private String name;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private int role;

    @Transient
    private boolean isAuthenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRole(this);
    }

    @Override
    public Object getCredentials() {
        return password;
    }


    @Override
    public Object getDetails() {
        return this;
    }

    @Override
    public Object getPrincipal() {
        return name;
    }

    @Override
    public void setAuthenticated(boolean b) throws IllegalArgumentException {
        isAuthenticated = b;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public static Collection<? extends GrantedAuthority> getRole(User user) {
        Collection<? extends GrantedAuthority> authorities = null;

        if(user.getRole() == ROLE_CONSTS.ROLE_ADMIN.val){
            authorities = javaslang.collection.Stream.of("ROLE_ADMIN", "ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }else if(user.getRole() == ROLE_CONSTS.ROLE_USER.val){
            authorities = javaslang.collection.Stream.of("ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }else if(user.getRole() == ROLE_CONSTS.ROLE_UNCHECKED_USER.val){
            authorities = javaslang.collection.Stream.of("ROLE_UNCHECKED_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }else {
            authorities = javaslang.collection.Stream.of("ROLE_ANONYMOUS")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }


        return authorities;
    }

    @Override public long getId() {
        return id;
    }

    public User setId(final long id) {
        this.id = id;
        return this;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public User setCreatedAt(final DateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public User setBirthday(final DateTime birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getMail() {
        return mail;
    }

    public User setMail(final String mail) {
        this.mail = mail;
        return this;
    }

    @Override public String getName() {
        return name;
    }

    public User setName(final String name) {
        this.name = name;
        return this;
    }

    public User setPassword(final String password) {
        this.password = password;
        return this;
    }

    @Override public int getRole() {
        return role;
    }

    public User setRole(final int role) {
        this.role = role;
        return this;
    }
}