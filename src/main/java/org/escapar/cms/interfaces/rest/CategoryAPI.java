package org.escapar.cms.interfaces.rest;

import org.escapar.cms.business.domain.category.Category;
import org.escapar.cms.business.domain.category.CategoryDTO;
import org.escapar.cms.business.service.CategoryService;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/category/")
public class CategoryAPI extends LeylineReadonlyRestCRUD<CategoryService, Category, CategoryDTO> {

}
