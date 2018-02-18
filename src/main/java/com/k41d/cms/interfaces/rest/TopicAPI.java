package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.service.TagService;
import com.k41d.cms.business.service.TopicDetailService;
import com.k41d.cms.business.service.TopicService;
import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.domain.topic.TopicDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import com.k41d.leyline.framework.interfaces.dto.PageJSON;
import com.k41d.leyline.framework.interfaces.rest.LeylineAdminRestCRUD;
import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/topic/")
public class TopicAPI extends LeylineReadonlyRestCRUD<TopicService, Topic, TopicDTO> {
    @Autowired TagService ts;
    @Autowired TopicDetailService tds;

    @Override
    public void checkUpdate(Topic t) {
        try {
            t.setTags(ts.save(t.getTags()));
            t.setLatest(tds.save(t.getLatest())); // temporary solution
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    @RequestMapping(value = "/publish", method = RequestMethod.GET)
//    public Topic publish(TopicDTO topic) throws PersistenceException, NoSuchMethodException {
//        Topic toSave = dtoAssembler.buildDO(topic);
//        return new PageJSON<>(doQueryDSL(p, parameters), p);
//    }
}
