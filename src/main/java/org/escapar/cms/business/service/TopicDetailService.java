package org.escapar.cms.business.service;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicDetailRepo;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.springframework.stereotype.Service;

@Service
public class TopicDetailService extends LeylineDomainService<TopicDetailRepo,TopicDetail> {
    @Override
    public TopicDetail save(TopicDetail entity) throws PersistenceException {
        try {
            return getRepo().save(entity.setSavedAt(ZonedDateTime.now()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    @Override
    public List<TopicDetail> save(Collection<TopicDetail> entity) throws PersistenceException {
        try {
            return (List<TopicDetail>)getRepo().saveAll(entity.stream().map(e->e.setSavedAt(ZonedDateTime.now())).collect(Collectors.toList()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

    public TopicDetail publish(TopicDetail persistedTd) throws PersistenceException {
        return getRepo().save(persistedTd.upgradeMainVersion(persistedTd.fillInVersion().getMainVersion())
                .setPublished(true)
                .setPublishedAt(ZonedDateTime.now()));
    }

    public TopicDetail draft(TopicDetail draft,long id) throws PersistenceException {
        Optional<TopicDetail> op = get(id);
        if(!op.isPresent()) throw new PersistenceException("Invalid ID");

        TopicDetail t = op.get();
        if(draft.getId()>0 && !draft.getMainVersion().equals("draft")){
            draft.setId(0);
        }
        draft = save(draft.setSubVersion("draft").setMainVersion("draft").setCreatedAt(ZonedDateTime.now()));
        getRepo().save(t.setDraft(draft));
        return draft;
    }
}
