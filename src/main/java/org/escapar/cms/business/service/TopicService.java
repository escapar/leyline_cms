package org.escapar.cms.business.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.domain.topic.Topic;
import org.escapar.cms.business.domain.topic.TopicDetail;
import org.escapar.cms.business.domain.topic.TopicLike;
import org.escapar.cms.business.domain.topic.TopicLikeRepo;
import org.escapar.cms.business.domain.topic.TopicRepo;
import org.escapar.cms.business.domain.user.User;
import org.escapar.leyline.framework.infrastructure.common.exceptions.PersistenceException;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.escapar.cms.infrastructure.utils.AppUtils.unchecked;

@Service
public class TopicService extends LeylineDomainService<TopicRepo,Topic> {

    @Autowired
    TopicDetailService topicDetailService;
    @Autowired
    TagService tagService;
    @Autowired
    TopicLikeRepo topicLikeRepo;

    public List<Topic> findByCategory(Category c){
        return repo.findByCategoryOrderByCreatedAtDesc(c);
    }
    public List<Topic> findLatestByCategory(Category c){
        return repo.findTop5ByCategoryOrderByCreatedAtDesc(c);
    }
    public List<Topic> findLatest3(){
        return repo.findTop3ByOrderByCreatedAtDesc();
    }

    public List<Topic> findByNameLike(String name){
        return repo.findByNameLike("%"+name+"%");
    }
    public Topic publish(long id) throws PersistenceException {
        Optional<Topic> op = get(id);
        if(!op.isPresent()) throw new PersistenceException("Invalid ID");

        Topic t = op.get();
        if(t.getLatest().isPublished())  throw new PersistenceException("Already Published");

        TopicDetail latest = topicDetailService.publish(t.getLatest());
        t.setLatestPublished(latest);

        return repo.save(t);
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
            List<Tag> tagsPersisted = entity.getTags().stream().map(unchecked(t->tagService.save(t))).collect(Collectors.toList());
            entity.setTags(tagsPersisted);
            if(entity.getId()>0) {
                // it's an update operation
                // reset tags
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
