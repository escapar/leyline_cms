package org.escapar.cms.interfaces.rest;

import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.domain.user.UserDTO;
import org.escapar.cms.business.service.UserService;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.infrastructure.common.exceptions.LeylineException;
import org.escapar.leyline.framework.interfaces.dto.TokenDTO;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;

@RestController
@RequestMapping(value = "api/user/")
public class UserAPI extends LeylineReadonlyRestCRUD<UserService, User, UserDTO> {

    @Override
    public void checkUpdate(User user) {
        if(user.getId() > 0 && user.getPassword() == null){
            Optional<User> currentUser = getService().get(user.getId());
            if(!currentUser.isPresent()){
                // this should not happen
                user.setId(0);
            }else{
                user.setPassword(currentUser.get().getPassword());
            }
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody TokenDTO login(@RequestBody UserDTO login)
            throws LeylineException {

        if (login == null) {
            throw new LeylineException("Invalid login");
        }
        User user = getService().checkAndGet(login.getUsername(), login.getPassword());

        return TokenDTO.fromUser(user,getService().getJwtTokenUtils());
    }

    @JsonView(CMSView.LIST.class)
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public UserDTO info(@RequestParam("token") String token) throws Exception {
        Claims c = getService().getJwtTokenUtils().parse(token);
        UserDTO userDTO =  getDtoAssembler().buildDTO(getService().getByClaims(c));
        return userDTO.setRoles(Arrays.asList(ROLE_CONSTS.getState(userDTO.getRole())));
    }

    @JsonView(CMSView.LIST.class)
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public boolean logout() throws Exception {
        return true;
    }


    @Override
    public void appendBindings(QuerydslBindings bindings){
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
    }

}
