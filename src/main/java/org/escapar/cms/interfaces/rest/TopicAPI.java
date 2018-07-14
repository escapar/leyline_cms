package org.escapar.cms.interfaces.rest;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.domain.topic.QTopic;
import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicDetailDTO;
import org.escapar.cms.business.service.TagService;
import org.escapar.cms.business.service.TopicDetailService;
import org.escapar.cms.business.service.TopicService;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.domain.topic.TopicDTO;

import org.escapar.cms.infrastructure.security.ROLE_CONSTS;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.escapar.leyline.framework.interfaces.view.LeylineView;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.topic.TopicDTO;
import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicDetailDTO;
import org.escapar.cms.business.service.TagService;
import org.escapar.cms.business.service.TopicDetailService;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.escapar.leyline.framework.interfaces.view.LeylineView;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.ui.ModelMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.PageJSON;
import org.escapar.leyline.framework.interfaces.rest.LeylineAdminRestCRUD;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "api/topic/")
public class TopicAPI extends LeylineReadonlyRestCRUD<TopicService, Topic, TopicDTO> {
    @Autowired TagService ts;
    @Autowired TopicDetailService tds;

    @RequestMapping(value = "/like/{id}", method = RequestMethod.GET)
    public boolean like(@PathVariable  long id, HttpServletRequest request) throws PersistenceException, NoSuchMethodException {
        return service.like(id,request.getRemoteAddr()) != null;
    }

    @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
    public TopicDTO publish(@PathVariable  long id) throws PersistenceException, NoSuchMethodException {
        return dtoAssembler.buildDTO(service.publish(id));
    }

    @RequestMapping(value = "/draft/{id}", method = RequestMethod.POST)
    public TopicDetailDTO draft(@RequestBody TopicDetailDTO dto, @PathVariable long id) throws PersistenceException, NoSuchMethodException {
        DTOAssembler<TopicDetail,TopicDetailDTO> tdmm = new DTOAssembler<>(TopicDetail.class,TopicDetailDTO.class);
        return tdmm.buildDTO(tds.draft(tdmm.buildDO(dto),id));
    }

    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET, produces = "application/json")
    @JsonView(LeylineView.ADMIN.class)
    @ResponseBody
    @SuppressWarnings(value = "unchecked")
    public TopicDTO findAdmin(@PathVariable Long id,@RequestParam MultiValueMap<String, String> parameters) throws Exception {
        if(getCurrentUser() == null || getCurrentUser().getRole() != ROLE_CONSTS.ADMIN.val){
            throw new AccessDeniedException("Unauthorized");
        }
        return find(id,parameters);
    }

    @Override
    public Predicate buildPredicate(MultiValueMap<String, String> parameters){
        TypeInformation<Topic> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
        bindings.bind(QTopic.topic.tags.any()).first(((path, value) -> path.id.eq(value.getId())));
        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        if (getOwnership() != null) {
            predicate = getOwnership().and(predicate);
        }

        return predicate;
    }

}
