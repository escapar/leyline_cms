package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.service.TopicService;
import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.interfaces.dto.TopicDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/topic/")
public class TopicAPI extends LeylineReadonlyRestCRUD<TopicService, Topic, TopicDTO> {

}
