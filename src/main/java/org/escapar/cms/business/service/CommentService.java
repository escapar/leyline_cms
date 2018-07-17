package org.escapar.cms.business.service;

import org.escapar.cms.business.domain.comment.Comment;
import org.escapar.cms.business.domain.comment.CommentRepo;
import org.escapar.leyline.framework.service.LeylineDomainService;
import org.springframework.stereotype.Service;

@Service
public class CommentService extends LeylineDomainService<CommentRepo,Comment> {

}
