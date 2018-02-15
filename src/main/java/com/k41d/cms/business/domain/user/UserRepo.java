package com.k41d.cms.business.domain.user;


import org.springframework.stereotype.Repository;

import com.k41d.leyline.framework.domain.user.LeylineUserRepo;

@Repository
public interface UserRepo extends LeylineUserRepo<User> {
    User findByNickEquals(String nick);

    default Boolean authOK(String name, String password) {
        if (name == null || password == null || name.isEmpty() || password.isEmpty()) {
            return false;
        }
        User u = findByNickEquals(name);
        //        return u != null && BCrypt.checkpw(password, u.getPassword());
        return u != null && password.equals(u.getPassword());

    }

    default User checkAndGet(String name, String password) {
        return authOK(name, password) ? findByNickEquals(name) : null;
    }
}
