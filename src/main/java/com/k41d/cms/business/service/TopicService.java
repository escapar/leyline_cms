package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.domain.topic.TopicRepo;

import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class TopicService extends LeylineDomainService<TopicRepo,Topic> {

}
