package com.k41d.leyline.framework.interfaces.rest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.k41d.cms.business.domain.category.Category;
import com.k41d.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import com.k41d.leyline.framework.interfaces.view.LeylineView;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.k41d.cms.infrastructure.security.ROLE_CONSTS;
import com.k41d.leyline.framework.domain.LeylineDO;
import com.k41d.leyline.framework.domain.user.LeylineUser;
import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import com.k41d.leyline.framework.interfaces.dto.LeylineDTO;
import com.k41d.leyline.framework.interfaces.dto.PageJSON;
import com.k41d.leyline.framework.service.LeylineDomainService;
import com.k41d.leyline.framework.service.LeylineUserDetailsService;
import org.jodah.typetools.TypeResolver;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.NameTokenizers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

@EnableSpringDataWebSupport
@RestController
public abstract class LeylineReadonlyRestCRUD<T extends LeylineDomainService, O extends LeylineDO, D extends LeylineDTO> {
    public static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    public static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    public final Logger logger = LoggerFactory.getLogger(getClass());
    public final Class<?>[] typeArgs;
    public final Type typeService;
    public final Type typeDTO;
    public final Type typeDO;
    public final Class<T> classService;
    public final Class<D> classDTO;
    public final Class<O> classDO;
    public final JavaType typeDTOList;
    public final JavaType typeDOList;
    public BooleanExpression ownership;
    public DTOAssembler<O, D> dtoAssembler;
    @Autowired
    protected T service;
    @Autowired
    protected LeylineUserDetailsService userDetailsService;
    private ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings(value = "unchecked")
    public LeylineReadonlyRestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(LeylineReadonlyRestCRUD.class, getClass());
        classService = (Class<T>) typeArgs[0];
        classDTO = (Class<D>) typeArgs[2];
        classDO = (Class<O>) typeArgs[1];
        this.typeService = TypeToken.of(classService).getType();
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDO).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, classDTO);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
        setDTOAssembler(new DTOAssembler<>(typeDO, typeDTO));
    }

    public Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        Page res = service.findAll(buildPredicate(parameters), p);
        return dtoAssembler.buildPageDTO(res);
    }


    public Predicate buildPredicate(MultiValueMap<String, String> parameters){
        TypeInformation<O> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        if (getOwnership() != null) {
            predicate = getOwnership().and(predicate);
        }

        return predicate;
    }


    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<D> list(Pageable p) throws PersistenceException {
        checkQuery(null);
        Page res = service.findAll(p);
        res = dtoAssembler.buildPageDTO(res);
        return new PageJSON<>(res, p);
    }


    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageJSON<D> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        checkQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }


    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.DETAIL.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<D> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        checkQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.DETAIL.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D find(@PathVariable Long id,@RequestParam MultiValueMap<String, String> parameters) throws PersistenceException {
        checkQuery(null);
        parameters.add("id",id.toString());
        Predicate p = buildPredicate(parameters);
        return dtoAssembler.buildDTO((O) service.findOne(p).orElse(null));
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public List<D> update(@RequestBody String json) throws IOException, PersistenceException {
        List<O> doList = dtoAssembler.buildDOList(mapper.readValue(json, typeDTOList));
        checkUpdateBatch(doList);
        return dtoAssembler.buildDTOList(service.save(doList));
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D updateOne(@RequestBody D obj) throws IOException, PersistenceException {
        O objDO = dtoAssembler.buildDO(obj);
        checkUpdate(objDO);
        return dtoAssembler.buildDTO((O)service.save(objDO));
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "/batch", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public List<D> insert(@RequestBody String json) throws IOException, PersistenceException {
        return update(json);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public D insertOne(@RequestBody D obj) throws PersistenceException {
        O objDO = dtoAssembler.buildDO(obj);
        checkUpdate(objDO);
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(objDO), typeDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public boolean delete(@PathVariable Long id) throws PersistenceException {
        checkDelete();
        return service.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public boolean delete(@RequestBody List<Long> id) throws PersistenceException {
        checkDelete();
        return service.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN')")

    @RequestMapping(value = "/batch", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public boolean deleteBatch(@RequestBody List<Long> id) throws PersistenceException {
        checkDelete();
        return delete(id);
    }

    public LeylineUser getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public boolean isAdmin() {
        return getCurrentUser() != null && getCurrentUser().getRole() == ROLE_CONSTS.ADMIN.val;
    }

    public void setDTOAssembler(DTOAssembler<O, D> dtoAssembler) {
        this.dtoAssembler = dtoAssembler;
    }

    /**
     * 增删改查检查预留方法 可以在implementation里override
     *
     * @param o
     * @return
     */
    public void checkQuery(Object o) {

    }

    public void checkUpdateBatch(List<O> o) {
        o.forEach(this::checkUpdate);
    }

    public void checkUpdate(O o) {

    }

    public void checkDelete() {

    }

    public BooleanExpression getOwnership() {
        return ownership;
    }


}
