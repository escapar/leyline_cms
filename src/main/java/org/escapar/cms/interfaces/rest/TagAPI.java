package org.escapar.cms.interfaces.rest;

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.domain.tag.TagDTO;
import org.escapar.cms.business.service.TagService;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/tag/")
public class TagAPI extends LeylineReadonlyRestCRUD<TagService, Tag, TagDTO> {

    @Override
    public void appendBindings(QuerydslBindings bindings){
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
    }

}
