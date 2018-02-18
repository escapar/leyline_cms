package com.k41d.cms.business.domain.user;

import java.time.LocalDateTime;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;


@Data public class UserDTO implements LeylineDTO {

    private long id;

    private LocalDateTime createdAt;

    private LocalDateTime birthday;

    private String mail;

    private String username;

    private String password;

    private int role;


}
