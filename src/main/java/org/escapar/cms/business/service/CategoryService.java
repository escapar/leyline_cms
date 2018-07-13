package org.escapar.cms.business.service;

import java.util.List;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.category.CategoryRepo;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.stereotype.Service;

import org.escapar.leyline.framework.service.LeylineDomainService;

@Service
public class CategoryService extends LeylineDomainService<CategoryRepo,Category> {
    public Category findOneByAlias(String name){
        return repo.findByAliasIgnoreCase(name);
    }
    public List<Category> findStaticCategories(){
        return repo.findByReferenceIsNotNull();
    }

}
