package org.escapar.leyline.framework.interfaces.rest;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.escapar.cms.infrastructure.security.ROLE_CONSTS;
import org.escapar.leyline.framework.domain.LeylineDO;
import org.escapar.leyline.framework.domain.user.LeylineUser;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;
import org.escapar.leyline.framework.interfaces.dto.PageJSON;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.escapar.leyline.framework.interfaces.view.LeylineView;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.escapar.leyline.framework.service.LeylineUserDetailsService;
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
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by POJO on 5/30/16.
 */
@EnableSpringDataWebSupport
@RestController
public abstract class LeylineRestCRUD<Service extends LeylineDomainService, Domain extends LeylineDO, DTO extends LeylineDTO>{
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static Class<?>[] typeArgs;
    private static Type typeService;
    private static Type typeDTO;
    private static Type typeDO;
    private Class<Service> classService;
    private Class<DTO> classDTO;
    private Class<Domain> classDO;
    private static JavaType typeDTOList;
    private static JavaType typeDOList;
    private static BooleanExpression ownership;
    private DTOAssembler<Domain, DTO> dtoAssembler;
    @Autowired private Service service;
    @Autowired private LeylineUserDetailsService userDetailsService;
    private static ObjectMapper mapper = new ObjectMapper();

    @SuppressWarnings(value = "unchecked")
    @Autowired
    public LeylineRestCRUD() {
        typeArgs = TypeResolver.resolveRawArguments(LeylineRestCRUD.class, getClass());
        classService = (Class<Service>) typeArgs[0];
        classDTO = (Class<DTO>) typeArgs[2];
        classDO = (Class<Domain>) typeArgs[1];
        typeService = TypeToken.of(classService).getType();
        typeDTO = TypeToken.of(classDTO).getType();
        typeDO = TypeToken.of(classDO).getType();
        typeDTOList = mapper.getTypeFactory().constructCollectionType(List.class, classDTO);
        typeDOList = mapper.getTypeFactory().constructCollectionType(List.class, classDO);
        setDTOAssembler(new DTOAssembler<>(typeDO, typeDTO));
    }

    private Page doQueryDSL(Pageable p, MultiValueMap<String, String> parameters) throws PersistenceException {
        Page res = service.findAll(buildPredicate(parameters), p);
        return dtoAssembler.buildPageDTO(res);
    }

    private Predicate buildPredicate(MultiValueMap<String, String> parameters){
        TypeInformation<Domain> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);
        appendBindings(bindings);

        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        appendPredicate(predicate);

        return predicate;
    }

    public void appendBindings(QuerydslBindings bindings){

    }

    public void appendPredicate(Predicate predicate){
        if (getOwnership() != null) {
            predicate = getOwnership().and(predicate);
        }
    }


    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public PageJSON<DTO> list(@PageableDefault(page = 0, size = 99)
            Pageable p) throws PersistenceException {
        checkQuery(null);
        Page res = service.findAll(p);
        res = dtoAssembler.buildPageDTO(res);
        return new PageJSON<>(res, p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/list/query", method = RequestMethod.GET)
    public PageJSON<DTO> listWithQuery(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        checkQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @SuppressWarnings(value = "unchecked")
    @JsonView(LeylineView.LIST.class)
    @RequestMapping(value = "/query", method = RequestMethod.GET)
    public PageJSON<DTO> listWithDetail(
            Pageable p, @RequestParam MultiValueMap<String, String> parameters) throws PersistenceException, NoSuchMethodException {
        checkQuery(parameters);
        return new PageJSON<>(doQueryDSL(p, parameters), p);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.LIST.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public DTO find(@PathVariable Long id,@RequestParam MultiValueMap<String, String> parameters) throws PersistenceException {
        checkQuery(null);
        parameters.add("id",id.toString());
        Predicate p = buildPredicate(parameters);
        return dtoAssembler.buildDTO((Domain) service.findOne(p).orElse(null));
    }

    @RequestMapping(value = "/batch", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void update(@RequestBody String json) throws IOException, PersistenceException {
        List<Domain> doList = dtoAssembler.buildDOList(mapper.readValue(json, typeDTOList));
        checkUpdateBatch(doList);
        service.save(doList);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void updateOne(@RequestBody DTO obj) throws IOException, PersistenceException {
        Domain objDO = dtoAssembler.buildDO(obj);
        checkUpdate(objDO);
        service.save(objDO);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void insert(@RequestBody String json) throws IOException, PersistenceException {
        update(json);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public DTO insertOne(@RequestBody DTO obj) throws PersistenceException {
        Domain objDO = dtoAssembler.buildDO(obj);
        checkUpdate(objDO);
        ModelMapper mm = new ModelMapper();
        mm.getConfiguration().setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return mm.map(service.save(objDO), typeDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@PathVariable Long id) throws PersistenceException {
        checkDelete();
        service.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void delete(@RequestBody List<Long> id) throws PersistenceException {
        checkDelete();
        service.delete(id);
    }

    @RequestMapping(value = "/batch", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public void deleteBatch(@RequestBody List<Long> id) throws PersistenceException {
        checkDelete();
        delete(id);
    }

    public LeylineUser getCurrentUser() {
        return userDetailsService.getCurrentUser();
    }

    public boolean isAdmin() {
        return getCurrentUser() != null && getCurrentUser().getRole() == ROLE_CONSTS.ADMIN.val;
    }

    public void setDTOAssembler(DTOAssembler<Domain, DTO> dtoAssembler) {
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

    public void checkUpdateBatch(List<Domain> o) {
        o.forEach(this::checkUpdate);
    }

    public void checkUpdate(Domain o) {

    }

    public void checkDelete() {

    }

    public BooleanExpression getOwnership() {
        return ownership;
    }

    public Service getService() {
        return service;
    }

    public LeylineUserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}

