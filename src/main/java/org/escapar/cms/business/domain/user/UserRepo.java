package org.escapar.cms.business.domain.user;

import org.escapar.leyline.framework.domain.user.LeylineUserRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends LeylineUserRepo<User> {
    User findByUsernameEquals(String name);

    default Boolean authOK(String name, String password) {
        if (name == null || password == null || name.isEmpty() || password.isEmpty()) {
            return false;
        }
        User u = findByUsernameEquals(name);
        //        return u != null && BCrypt.checkpw(password, u.getPassword());
        return u != null && password.equals(u.getPassword());

    }

    default User checkAndGet(String name, String password) {
        return authOK(name, password) ? findByUsernameEquals(name) : null;
    }
}
