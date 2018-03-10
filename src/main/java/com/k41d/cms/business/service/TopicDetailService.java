package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.topic.Topic;
import com.k41d.cms.business.domain.topic.TopicDetail;
import com.k41d.cms.business.domain.topic.TopicDetailRepo;

import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

import java.time.ZonedDateTime;

@Service
public class TopicDetailService extends LeylineDomainService<TopicDetailRepo,TopicDetail> {
    public TopicDetail publish(TopicDetail persistedTd) throws PersistenceException {
        return repo.save(persistedTd.upgradeMainVersion(persistedTd.fillInVersion().getMainVersion())
                .setPublished(true)
                .setPublishedAt(ZonedDateTime.now()));
    }
}
