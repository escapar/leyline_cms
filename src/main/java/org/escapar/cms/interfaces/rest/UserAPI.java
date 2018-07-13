package org.escapar.cms.interfaces.rest;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.infrastructure.security.JWTTokenUtils;
import org.escapar.cms.business.service.UserService;
import org.escapar.leyline.framework.interfaces.dto.TokenDTO;
import org.escapar.cms.business.domain.user.UserDTO;

import org.escapar.leyline.framework.infrastructure.common.exceptions.LeylineException;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.domain.user.UserDTO;
import org.escapar.leyline.framework.infrastructure.common.exceptions.LeylineException;
import org.escapar.leyline.framework.interfaces.dto.TokenDTO;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user/")
public class UserAPI extends LeylineReadonlyRestCRUD<UserService, User, UserDTO> {


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody TokenDTO login(@RequestBody UserDTO login)
            throws LeylineException {

        if (login == null) {
            throw new LeylineException("Invalid login");
        }
        User user = service.checkAndGet(login.getUsername(), login.getPassword());

        return TokenDTO.fromUser(user,service.getJwtTokenUtils());
    }




}
