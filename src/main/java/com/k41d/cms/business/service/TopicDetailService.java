package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.topic.TopicDetail;
import com.k41d.cms.business.domain.topic.TopicDetailRepo;

import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class TopicDetailService extends LeylineDomainService<TopicDetailRepo,TopicDetail> {

}
