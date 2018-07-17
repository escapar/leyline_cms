package org.escapar.cms.interfaces.rest;

import java.nio.file.AccessDeniedException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.annotation.JsonView;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.topic.QTopic;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.domain.topic.TopicDTO;
import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicDetailDTO;
import org.escapar.cms.business.service.TagService;
import org.escapar.cms.business.service.TopicDetailService;
import org.escapar.cms.business.service.TopicService;
import org.escapar.cms.infrastructure.security.ROLE_CONSTS;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.interfaces.dto.assembler.DTOAssembler;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.escapar.leyline.framework.interfaces.view.LeylineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/topic/")
public class TopicAPI extends LeylineReadonlyRestCRUD<TopicService, Topic, TopicDTO> {
    @Autowired TagService ts;
    @Autowired TopicDetailService tds;

    @RequestMapping(value = "/like/{id}", method = RequestMethod.GET)
    public boolean like(@PathVariable  long id, HttpServletRequest request) throws PersistenceException, NoSuchMethodException {
        return getService().like(id,request.getRemoteAddr()) != null;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(value = "/publish/{id}", method = RequestMethod.GET)
    public TopicDTO publish(@PathVariable  long id) throws PersistenceException, NoSuchMethodException {
        try {
            return getDtoAssembler().buildDTO(getService().publish(id));
        }catch (PersistenceException e){
            return null;
        }
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
    public void appendBindings(QuerydslBindings bindings){
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
        bindings.bind(QTopic.topic.tags.any()).first(((path, value) -> path.id.eq(value.getId())));
    }

}
