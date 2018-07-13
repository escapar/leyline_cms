package org.escapar.cms.business.service;

import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicDetailRepo;

import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;

import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.springframework.stereotype.Service;

import org.escapar.leyline.framework.service.LeylineDomainService;

import java.time.ZonedDateTime;

@Service
public class TopicDetailService extends LeylineDomainService<TopicDetailRepo,TopicDetail> {
    public TopicDetail publish(TopicDetail persistedTd) throws PersistenceException {
        return repo.save(persistedTd.upgradeMainVersion(persistedTd.fillInVersion().getMainVersion())
                .setPublished(true)
                .setPublishedAt(ZonedDateTime.now()));
    }
}
