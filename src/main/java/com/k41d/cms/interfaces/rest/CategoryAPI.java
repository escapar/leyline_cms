package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.category.Category;
import com.k41d.cms.business.service.CategoryService;
import com.k41d.cms.business.domain.category.CategoryDTO;

import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/category/")
public class CategoryAPI extends LeylineReadonlyRestCRUD<CategoryService, Category, CategoryDTO> {

}
