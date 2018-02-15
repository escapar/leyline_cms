package com.k41d.cms.business.service;

import com.k41d.cms.business.domain.comment.CommentRepo;
import com.k41d.cms.business.domain.comment.Comment;

import org.springframework.stereotype.Service;

import com.k41d.leyline.framework.service.LeylineDomainService;

@Service
public class CommentService extends LeylineDomainService<CommentRepo,Comment> {

}
