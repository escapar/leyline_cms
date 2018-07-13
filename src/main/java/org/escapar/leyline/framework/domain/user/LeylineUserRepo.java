package org.escapar.leyline.framework.domain.user;

import org.escapar.leyline.framework.domain.LeylineRepo;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUserRepo<T extends LeylineUser> extends LeylineRepo<T> {
    T findByUsernameEquals(String username);

}
