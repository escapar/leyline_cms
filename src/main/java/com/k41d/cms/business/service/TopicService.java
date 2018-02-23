package com.k41d.cms.business.service;

import java.util.List;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.domain.topic.TopicRepo;

import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class TopicService extends LeylineDomainService<TopicRepo,Topic> {
    public List<Topic> findByCategory(Category c){
       return repo.findByCategoryOrderByCreatedAtDesc(c);
    }
    public List<Topic> findLatestByCategory(Category c){
        return repo.findTop5ByCategoryOrderByCreatedAtDesc(c);
    }
}
