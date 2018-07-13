package org.escapar.cms.interfaces.rest;

import java.nio.file.AccessDeniedException;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonView;
import org.escapar.cms.business.domain.category.Category;
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

//    @Override
//    public void checkUpdate(Topic t) {
//        try {
//            t.setTags(ts.save(t.getTags()));
//
//            TopicDetail latest = tds.save(t.getLatest());
//            t.setLatest(latest); // temporary solution
//
//            t.setVersions(t.getVersions().stream().filter(i->i.getId()!=latest.getId()).collect(Collectors.toList())); //remove if existed
//            t.getVersions().add(latest);  // add new
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
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
        return tdmm.buildDTO(service.draft(tdmm.buildDO(dto),id));
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

}
