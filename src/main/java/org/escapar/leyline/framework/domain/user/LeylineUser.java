package org.escapar.leyline.framework.domain.user;

import org.escapar.leyline.framework.domain.LeylineDO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by POJO on 6/8/16.
 */
public interface LeylineUser extends LeylineDO, UserDetails, CredentialsContainer, Authentication {
    public long getId();

    public String getPassword();

    public String getUsername();

    public int getRole();
}
