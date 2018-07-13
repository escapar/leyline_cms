package org.escapar.cms.interfaces.rest;

import org.escapar.cms.business.domain.tag.Tag;
import org.escapar.cms.business.service.TagService;
import org.escapar.cms.business.domain.tag.TagDTO;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/tag/")
public class TagAPI extends LeylineReadonlyRestCRUD<TagService, Tag, TagDTO> {

}
