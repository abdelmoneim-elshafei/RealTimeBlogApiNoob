package noob.blogapi.service;

import noob.blogapi.entity.Comment;
import noob.blogapi.payload.CommentDto;
import noob.blogapi.payload.CommentResponse;
import noob.blogapi.payload.PostDto;
import noob.blogapi.payload.PostResponse;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long postId, CommentDto commentDto);
    List<CommentDto> getAllComment(Long postId);
    CommentDto getCommentById(long postId, long id);
    CommentDto updateCommentById(long postId, long id, CommentDto commentDto);
    void deleleCommentById(long postId, long id);

}
