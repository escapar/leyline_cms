package com.k41d.cms.interfaces.dto;

import lombok.Data;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;

import org.joda.time.DateTime;

@Data public class UserDTO implements LeylineDTO {

    private long id;

    private DateTime createdAt;

    private DateTime birthday;

    private String mail;

    private String name;

    private String password;

    private int role;


}
