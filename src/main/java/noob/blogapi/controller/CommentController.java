package noob.blogapi.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import noob.blogapi.payload.CommentDto;
import noob.blogapi.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/")
public class CommentController {
    final CommentService commentService;
    @PostMapping("/posts/{id}/comment")
    public ResponseEntity<?> addNewComment(@PathVariable("id") Long id,@Valid @RequestBody CommentDto commentDto){
       return new ResponseEntity<>(commentService.createComment(id,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}/comment")
    public List<CommentDto> getAllComments(@PathVariable("id") long id){
        return commentService.getAllComment(id);
    }
    @GetMapping("/posts/{id}/comment/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") long id,@PathVariable long commentId){
        return new ResponseEntity<>(commentService.getCommentById(id,commentId),HttpStatus.OK);
    }
    @PutMapping("/posts/{id}/comment/{commentId}")
    public ResponseEntity<?> getCommentById(@PathVariable("id") long id,
                                            @PathVariable long commentId,
                                            @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateCommentById(id,commentId,commentDto),HttpStatus.NO_CONTENT);
    }
    @DeleteMapping("/posts/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteCommentById(@PathVariable("id") long id,
                                               @PathVariable long commentId){
        commentService.deleleCommentById(id,commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
