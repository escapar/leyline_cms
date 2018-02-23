package com.k41d.cms.business.domain.topic;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.leyline.framework.domain.LeylineRepo;

@Repository
public interface TopicRepo extends LeylineRepo<Topic> {
    public List<Topic> findByCategoryOrderByCreatedAtDesc(Category c);
    public List<Topic> findTop5ByCategoryOrderByCreatedAtDesc(Category c);

}
