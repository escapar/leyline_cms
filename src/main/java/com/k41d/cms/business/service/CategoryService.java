package com.k41d.cms.business.service;

import java.util.List;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.domain.category.CategoryRepo;

import org.springframework.data.querydsl.binding.QuerydslPredicateBuilder;
import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class CategoryService extends LeylineDomainService<CategoryRepo,Category> {
    public Category findOneByAlias(String name){
        return repo.findByAliasIgnoreCase(name);
    }
    public List<Category> findStaticCategories(){
        return repo.findByReferenceIsNotNull();
    }

}
