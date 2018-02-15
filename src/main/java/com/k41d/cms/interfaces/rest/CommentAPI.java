package com.k41d.cms.interfaces.rest;

import com.k41d.cms.business.domain.comment.Comment;
import com.k41d.cms.business.service.CommentService;
import com.k41d.cms.business.domain.comment.CommentDTO;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.k41d.leyline.framework.interfaces.rest.LeylineReadonlyRestCRUD;

@RestController
@RequestMapping(value = "api/comment/")
public class CommentAPI extends LeylineReadonlyRestCRUD<CommentService, Comment, CommentDTO> {

}
