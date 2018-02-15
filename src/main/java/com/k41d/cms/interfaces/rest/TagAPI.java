package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.tag.Tag;
import com.k41d.cms.business.service.TagService;
import com.k41d.cms.business.domain.tag.TagDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/tag/")
public class TagAPI extends LeylineReadonlyRestCRUD<TagService, Tag, TagDTO> {

}
