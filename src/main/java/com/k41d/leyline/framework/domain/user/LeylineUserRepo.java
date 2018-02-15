package com.k41d.leyline.framework.domain.user;

import com.k41d.leyline.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineRepo<T> {
    T findByNameEquals(String username);

}
