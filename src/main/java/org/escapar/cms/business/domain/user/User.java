package org.escapar.cms.business.domain.user;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;

import org.escapar.leyline.framework.domain.user.LeylineUser;
import org.escapar.leyline.framework.interfaces.view.LeylineView;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.escapar.leyline.framework.domain.user.LeylineUser;
import org.escapar.leyline.framework.interfaces.view.LeylineView;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Entity
@Table(name="user")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable,LeylineUser {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @JsonView(LeylineView.LIST.class)

    private long id;

    @Column(name="created_at")
    private ZonedDateTime createdAt;

    @Column(name="birthday")
    private ZonedDateTime birthday;

    @Column(name="mail")
    private String mail;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="avatar")
    private String avatar;

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
        return username;
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

    public String getUsername() {
        return username;
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

        if(user.getRole() == ROLE_CONSTS.ADMIN.val){
            authorities = javaslang.collection.Stream.of("ROLE_ADMIN", "ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }else if(user.getRole() == ROLE_CONSTS.USER.val){
            authorities = javaslang.collection.Stream.of("ROLE_USER")
                    .map(SimpleGrantedAuthority::new)
                    .toJavaList();
        }else if(user.getRole() == ROLE_CONSTS.UNCHECKED_USER.val){
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


    @Override public String getName() {
        return username;
    }


    @Override public int getRole() {
        return role;
    }

    public User setRole( int role) {
        this.role = role;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public User setAvatar(final String avatar) {
        this.avatar = avatar;
        return this;
    }
}
