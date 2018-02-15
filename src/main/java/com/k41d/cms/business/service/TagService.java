package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.tag.Tag;
import com.k41d.cms.business.domain.tag.TagRepo;

import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class TagService extends LeylineDomainService<TagRepo,Tag> {

}
