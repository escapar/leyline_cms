package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.commons.VersionUtil;
import com.k41d.cms.business.domain.topic.*;

import com.k41d.cms.business.domain.user.User;
import com.k41d.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService extends LeylineDomainService<TopicRepo,Topic> {

    @Autowired
    TopicDetailService topicDetailService;
    @Autowired
    TopicLikeRepo topicLikeRepo;

    public Topic publish(long id) throws PersistenceException {
        Optional<Topic> op = get(id);
        if(!op.isPresent()) throw new PersistenceException("Invalid ID");

        Topic t = op.get();
        if(t.getLatest().isPublished())  throw new PersistenceException("Already Published");

        TopicDetail latest = topicDetailService.publish(t.getLatest());
        t.setLatestPublished(latest);

        return repo.save(t);
    }

    public TopicDetail draft(TopicDetail draft,long id) throws PersistenceException {
        Optional<Topic> op = get(id);
        if(!op.isPresent()) throw new PersistenceException("Invalid ID");

        Topic t = op.get();
        if(draft.getId()>0 && !draft.getMainVersion().equals("draft")){
            draft.setId(0);
        }
        draft = topicDetailService.save(draft.setSubVersion("draft").setMainVersion("draft").setCreatedAt(ZonedDateTime.now()));
        repo.save(t.setDraft(draft));
        return draft;
    }

    public Topic like(long id,String ip) throws PersistenceException {
        Optional<Topic> op = get(id);
        if(!op.isPresent()) throw new PersistenceException("Invalid ID");

        Topic t = op.get();
        TopicLike tl = new TopicLike().setCreatedAt(ZonedDateTime.now()).setIp(ip).setUser((User)getCurrentUser());

        List<TopicLike> likes = new ArrayList<>();
        likes.addAll(t.getLikes());
        likes.add(tl);
        t.setLikes(likes);
        return repo.save(t);
    }

    @Override
    public Topic save(Topic entity) throws PersistenceException {
        // save an aggregate from admin page
        try {
            if(entity.getId()>0) {
                // it's an update operation
                Optional<Topic> persisted = repo.findById(entity.getId());
                if(persisted.isPresent()) {

                    TopicDetail persistedLatest = persisted.get().getLatest();
                    TopicDetail transientLatest = entity.getLatest();

                    if(!persistedLatest.contentEquals(transientLatest)) {
                        // updates a new version
                        transientLatest = transientLatest.upgradeSubVersion(persistedLatest.fillInVersion().getSubVersion()).setPublished(false).setCreatedAt(ZonedDateTime.now());
                        persistedLatest = topicDetailService.save(transientLatest);
                        entity.addNewVersion(persistedLatest);
                    }else{
                        // contents remain the same,abandon content updates
                        entity.setLatest(persistedLatest);
                        entity.setVersions(persisted.get().getVersions());
                    }
                }else{
                    throw new PersistenceException("Invalid ID");
                }
            }else {
                // it's a create operation
                TopicDetail latest = entity.getLatest().setCreatedAt(ZonedDateTime.now()).setPublished(false);
                latest = topicDetailService.save(latest);
                entity.addNewVersion(latest).setCreatedAt(ZonedDateTime.now());
            }
            return repo.save(entity);

        } catch (Exception e) {
            e.printStackTrace();
            throw new PersistenceException(e.getMessage());
        }
    }

}
