package com.k41d.cms.business.domain.topic;


import org.springframework.stereotype.Repository;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.leyline.framework.domain.LeylineRepo;

import java.util.List;

@Repository
public interface TopicRepo extends LeylineRepo<Topic> {
    List<Topic> findByNameLike(String name);
    List<Topic> findByCategoryOrderByCreatedAtDesc(Category c);
    List<Topic> findTop5ByCategoryOrderByCreatedAtDesc(Category c);

}
