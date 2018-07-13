package org.escapar.cms.interfaces.rest;

import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.service.TopicDetailService;
import org.escapar.cms.business.domain.topic.TopicDetailDTO;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/topic/detail/")
public class TopicDetailAPI extends LeylineReadonlyRestCRUD<TopicDetailService, TopicDetail, TopicDetailDTO> {

}
