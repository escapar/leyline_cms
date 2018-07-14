package org.escapar.cms.interfaces.rest;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.service.TagService;
import org.escapar.cms.business.domain.tag.TagDTO;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/tag/")
public class TagAPI extends LeylineReadonlyRestCRUD<TagService, Tag, TagDTO> {

    @Override
    public Predicate buildPredicate(MultiValueMap<String, String> parameters){
        TypeInformation<Tag> domainType = ClassTypeInformation.from(classDO);
        QuerydslBindings bindings = bindingsFactory.createBindingsFor(domainType);
        bindings.bind(String.class).first(
                (SingleValueBinding<StringPath, String>) StringExpression::like);
        Predicate predicate = predicateBuilder.getPredicate(domainType, parameters, bindings);
        if (getOwnership() != null) {
            predicate = getOwnership().and(predicate);
        }

        return predicate;
    }
}
