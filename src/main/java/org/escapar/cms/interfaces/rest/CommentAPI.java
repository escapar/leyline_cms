package org.escapar.cms.interfaces.rest;

import org.escapar.cms.business.domain.comment.Comment;
import org.escapar.cms.business.domain.comment.CommentDTO;
import org.escapar.cms.business.service.CommentService;
import org.escapar.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/comment/")
public class CommentAPI extends LeylineReadonlyRestCRUD<CommentService, Comment, CommentDTO> {

}
