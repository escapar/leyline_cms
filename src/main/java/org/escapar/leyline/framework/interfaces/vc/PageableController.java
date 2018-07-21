package org.escapar.leyline.framework.interfaces.vc;

import com.google.common.reflect.TypeToken;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.escapar.leyline.framework.domain.LeylineDO;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.LeylineDTO;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.jodah.typetools.TypeResolver;
import org.omg.CORBA.portable.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.QuerydslBindingsFactory;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 分页增删改查后端渲染控制器抽象类,自动完成DO->DTO的Mapping
 * 包含了分页排序的基础功能
 * Mapping可以通过替换Assembler来自定义
 */
@Controller
public abstract class PageableController<Service extends LeylineDomainService, Domain extends LeylineDO, DTO extends LeylineDTO> {
    private static final QuerydslBindingsFactory bindingsFactory = new QuerydslBindingsFactory(SimpleEntityPathResolver.INSTANCE);
    private static final QuerydslPredicateBuilder predicateBuilder = new QuerydslPredicateBuilder(new DefaultConversionService(), bindingsFactory.getEntityPathResolver());
    @Autowired
    public Service service;
    public DTOAssembler<Domain, DTO> dtoAssembler;
    private Class<?>[] typeArgs;
    private Class<Domain> classDO;
    private static BooleanExpression ownership;
    private Class<DTO> classDTO;
    private Type typeDO;
    private Type typeDTO;
    private Integer pagesize = 20;
    private String modelName;

    @SuppressWarnings(value = "unchecked")
    public PageableController() {
        typeArgs = TypeResolver.resolveRawArguments(PageableController.class, getClass());
        classDO = (Class<Domain>) typeArgs[1];
        classDTO = (Class<DTO>) typeArgs[2];
        this.typeDTO = TypeToken.of(classDTO).getType();
        this.typeDO = TypeToken.of(classDTO).getType();
        String[] nameOfDTO = typeDTO.getTypeName().split("\\.");
        modelName = nameOfDTO[nameOfDTO.length - 1].toLowerCase().replace("dto", "");
        setDtoAssembler(new DTOAssembler(typeDO, typeDTO));
    }

    /**
     * 用QueryDSL构建复杂JPA查询
     *
     * @param p
     * @param parameters
     * @return
     * @throws PersistenceException
     */
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
    public BooleanExpression getOwnership() {
        return ownership;
    }


    /**
     * 分页列出辅助方法
     *
     * @param model
     * @param page
     * @return
     * @throws ApplicationException
     */
    public String list(Model model, Page page) throws ApplicationException {
        model.addAttribute("page", page);
        return modelName.concat("/list");
    }

    public String list(Model model, Page page, String pageName) throws ApplicationException {
        model.addAttribute("page", page);
        return modelName.concat("/").concat(pageName);
    }

    /**
     * 分页列出辅助方法
     *
     * @param model
     * @param pageable
     * @return
     * @throws ApplicationException
     */
    public String list(Model model, Pageable pageable) throws ApplicationException, PersistenceException {
        Page res = service.findAll(pageable);
        model.addAttribute("page", dtoAssembler.buildPageDTO(res));
        return modelName.concat("/list");
    }

    /**
     * 默认入口
     *
     * @param model
     * @param page
     * @param direction
     * @param properties
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("")
    public String listDefault(Model model, @RequestParam(required = false) Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize)
            throws ApplicationException, PersistenceException {
        if (page == null || page < 0) {
            page = 0;
        }
        return list(model, page, direction, properties, pagesize);
    }

    public String list(Model model, Integer page, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws ApplicationException, PersistenceException {
        return list(model, getPageRequestSpecifiedDirection(page, properties, pagesize));
    }

    /**
     * 全部显示带分页
     *
     * @param model
     * @param page
     * @param direction
     * @param properties
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/page/{page}")
    public String list(Model model, @PathVariable Integer page, @RequestParam(required = false) String direction, @RequestParam(required = false, defaultValue = "id") String properties, @RequestParam(required = false) Integer pagesize) throws ApplicationException, PersistenceException {
        return list(model, getPageRequest(page, direction, properties, pagesize));
    }

    /**
     * 属性排序带分页
     *
     * @param model
     * @param page
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("sort/{property}/page/{page}")
    public String listByProperty(Model model, @PathVariable Integer page, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws ApplicationException, PersistenceException {
        return list(model, page, property, pagesize);
    }

    /**
     * 属性排序+方向带分页
     *
     * @param model
     * @param page
     * @param direction
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("sort/{property}/{direction}/page/{page}")
    public String listByPropertyAndDirection(Model model, @PathVariable Integer page, @PathVariable String direction, @PathVariable String property, @RequestParam(required = false) Integer pagesize) throws ApplicationException, PersistenceException {
        return list(model, page, direction, property, pagesize);
    }

    /**
     * 以id为标识的详情页
     *
     * @param model
     * @param id
     * @return
     * @throws ApplicationException
     */
    @RequestMapping("/detail_{id}")
    public String list(Model model, @PathVariable Long id, @RequestParam MultiValueMap<String, String> parameters) throws ApplicationException, IOException, PersistenceException {
        parameters.add("id",id.toString());
        Predicate p = buildPredicate(parameters);

        model.addAttribute("res", dtoAssembler.buildDTO((Domain) service.findOne(p).orElse(null)));
        return modelName.concat("/detail");
    }

    /**
     * 造PageRequest分页信息对象
     *
     * @param page
     * @param direction
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    public static PageRequest getPageRequestStatic(Integer page, String direction, String property, Integer pagesize) throws ApplicationException {
//        if (pagesize == null) {
//            pagesize = getPagesize();
//        }
        Sort.Direction d = getDirection(direction);

        assertThat(pagesize).isPositive();
        assertThat(page).isNotNegative();

        return PageRequest.of(page, pagesize, d, property == null ? new String[]{"id"} : property.split(","));
    }

    public PageRequest getPageRequest(Integer page, String direction, String property, Integer pagesize) throws ApplicationException {
        if (pagesize == null) {
            pagesize = getPagesize();
        }
        return getPageRequestStatic(page,direction,property,pagesize);
    }


    /**
     * 处理形如sort/{property}/page/{page} ,sort后只有一个参数 , 也就是排序方向和参数写在一起的请求
     *
     * @param page
     * @param property
     * @param pagesize
     * @return
     * @throws ApplicationException
     */
    public PageRequest getPageRequestSpecifiedDirection(Integer page, String property, Integer pagesize) throws ApplicationException {
        if (pagesize == null) {
            pagesize = getPagesize();
        }
        String[] candidates = property.split(",");

        assertThat(candidates.length % 2).isZero();
        assertThat(pagesize).isPositive();
        assertThat(page).isNotNegative();

        Sort sort = new Sort(
                IntStream.range(0, candidates.length / 2)
                        .mapToObj(i -> new Sort.Order(getDirection(candidates[i * 2 + 1]), candidates[i * 2]))
                        .collect(Collectors.toList())
        );

        return PageRequest.of(page, pagesize, sort);
    }

    /**
     * ASC字符串->DIRECTION.ASC,否则就是DIRECTION.DESC,确定排序
     *
     * @param direction
     * @return
     */
    public static Sort.Direction getDirection(String direction) {
        Sort.Direction d = Sort.Direction.DESC;
        if (direction != null && !direction.isEmpty() && direction.toUpperCase().equals("ASC")) {
            d = Sort.Direction.ASC;
        }
        return d;
    }


    public Integer getPagesize() {
        return pagesize;
    }

    public void setPagesize(Integer pagesize) {
        this.pagesize = pagesize;
    }

    public DTOAssembler getDtoAssembler() {
        return dtoAssembler;
    }

    public void setDtoAssembler(DTOAssembler dtoAssembler) {
        this.dtoAssembler = dtoAssembler;
    }

}