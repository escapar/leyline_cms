package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.topic.TopicDetail;
import com.k41d.cms.business.service.TopicDetailService;
import com.k41d.cms.interfaces.dto.TopicDetailDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/topic/detail/")
public class TopicDetailAPI extends LeylineReadonlyRestCRUD<TopicDetailService, TopicDetail, TopicDetailDTO> {

}
