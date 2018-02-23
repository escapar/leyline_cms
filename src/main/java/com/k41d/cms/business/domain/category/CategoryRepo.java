package com.k41d.cms.business.domain.category;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.k41d.leyline.framework.domain.LeylineRepo;

@Repository
public interface CategoryRepo extends LeylineRepo<Category> {
    public Category findByAliasIgnoreCase(String name);
    public List<Category> findByReferenceIsNotNull();

}
