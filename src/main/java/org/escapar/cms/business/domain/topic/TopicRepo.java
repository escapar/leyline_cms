package org.escapar.cms.business.domain.topic;

import java.time.ZonedDateTime;
import java.util.List;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.leyline.framework.domain.LeylineRepo;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepo extends LeylineRepo<Topic> {
    List<Topic> findByNameLike(String name);
    List<Topic> findByCategoryOrderByCreatedAtDesc(Category c);
    List<Topic> findTop5ByCategoryOrderByCreatedAtDesc(Category c);
    List<Topic> findTop3ByFeaturedIsTrueAndLatestPublishedIsNotNullOrderByCreatedAtDesc();
    Topic findTop1ByCreatedAtAfterAndLatestPublishedIsNotNull(final ZonedDateTime createdAt);
    Topic findTop1ByCreatedAtBeforeAndLatestPublishedIsNotNull(final ZonedDateTime createdAt);

}
