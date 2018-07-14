package org.escapar.cms.interfaces.rest;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonView;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.infrastructure.security.JWTTokenUtils;
import org.escapar.cms.business.service.UserService;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;
import org.escapar.cms.interfaces.view.CMSView;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.TokenDTO;
import org.escapar.cms.business.domain.user.UserDTO;

import org.escapar.leyline.framework.infrastructure.common.exceptions.LeylineException;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

import org.escapar.cms.business.domain.user.User;
import org.escapar.cms.business.domain.user.UserDTO;
import org.escapar.leyline.framework.infrastructure.common.exceptions.LeylineException;
import org.escapar.leyline.framework.interfaces.dto.TokenDTO;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.MultiValueMap;
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
            Optional<User> currentUser = service.get(user.getId());
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
        User user = service.checkAndGet(login.getUsername(), login.getPassword());

        return TokenDTO.fromUser(user,service.getJwtTokenUtils());
    }

    @JsonView(CMSView.LIST.class)
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public UserDTO info(@RequestParam("token") String token) throws Exception {
        Claims c = service.getJwtTokenUtils().parse(token);
        UserDTO userDTO =  dtoAssembler.buildDTO(service.getByClaims(c));
        return userDTO.setRoles(Arrays.asList(ROLE_CONSTS.getState(userDTO.getRole())));
    }

    @JsonView(CMSView.LIST.class)
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public boolean logout() throws Exception {
        return true;
    }

    @Override
    public Predicate buildPredicate(MultiValueMap<String, String> parameters){
        TypeInformation<User> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        if (getOwnership() != null) {
            predicate = getOwnership().and(predicate);
        }

        return predicate;
    }

}
