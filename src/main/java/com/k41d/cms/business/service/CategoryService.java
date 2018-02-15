package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.domain.category.CategoryRepo;
import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class CategoryService extends LeylineDomainService<CategoryRepo,Category> {

}
