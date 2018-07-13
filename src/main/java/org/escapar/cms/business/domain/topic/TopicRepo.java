package org.escapar.cms.business.domain.topic;


import org.escapar.leyline.framework.domain.LeylineRepo;
import org.springframework.stereotype.Repository;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.leyline.framework.domain.LeylineRepo;

import java.util.List;

@Repository
public interface TopicRepo extends LeylineRepo<Topic> {
    List<Topic> findByNameLike(String name);
    List<Topic> findByCategoryOrderByCreatedAtDesc(Category c);
    List<Topic> findTop5ByCategoryOrderByCreatedAtDesc(Category c);
    List<Topic> findTop3ByOrderByCreatedAtDesc();
}
