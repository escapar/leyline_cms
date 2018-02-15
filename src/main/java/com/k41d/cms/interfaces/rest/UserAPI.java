package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.user.User;
import com.k41d.cms.infrastructure.security.JWTTokenUtils;
import com.k41d.cms.business.service.UserService;
import com.k41d.leyline.framework.interfaces.dto.TokenDTO;
import com.k41d.cms.business.domain.user.UserDTO;

import com.k41d.leyline.framework.infrastructure.common.exceptions.LeylineException;
import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user/")
public class UserAPI extends LeylineReadonlyRestCRUD<UserService, User, UserDTO> {



    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody TokenDTO login(@RequestBody final UserDTO login)
            throws LeylineException {

        if (login == null) {
            throw new LeylineException("Invalid login");
        }
        User user = service.checkAndGet(login.getUsername(), login.getPassword());

        return new TokenDTO(JWTTokenUtils.sign(user));
    }




}
