package noob.blogapi.service.impl;

import lombok.RequiredArgsConstructor;
import noob.blogapi.entity.Comment;
import noob.blogapi.entity.Comment;
import noob.blogapi.entity.Post;
import noob.blogapi.exception.BlogApiException;
import noob.blogapi.exception.ResourceNotFoundException;
import noob.blogapi.payload.CommentDto;
import noob.blogapi.payload.CommentDto;
import noob.blogapi.repository.CommentRepository;
import noob.blogapi.repository.CommentRepository;
import noob.blogapi.repository.PostRepository;
import noob.blogapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    final CommentRepository commentRepository;
    final PostRepository postRepository;
    final ModelMapper mapper;
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

            Comment comment = mapToEntity(commentDto);
            Post post = postRepository.findById(postId)
                 .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
            comment.setPost(post);
            return mapToDTO(commentRepository.save(comment));
    }

    @Override
    public List<CommentDto> getAllComment(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId,long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",id));
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"this comment not belong to this post");
        }

       return mapToDTO(comment);
    }

    @Override
    public CommentDto updateCommentById(long postId, long id, CommentDto commentDto) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"this comment not belong to this post");
        }

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        return  mapToDTO(commentRepository.save(comment));

    }

    @Override
    public void deleleCommentById(long postId, long id) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post","id",postId));

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",id));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST,"this comment not belong to this post");
        }
        commentRepository.deleteById(comment.getId());
    }


    public CommentDto mapToDTO(Comment comment){
       /* CommentDto newCommentDto = new CommentDto();
        newCommentDto.setId(comment.getId());
        newCommentDto.setName(comment.getName());
        newCommentDto.setEmail(comment.getEmail());
        newCommentDto.setBody(comment.getBody());*/
        return mapper.map(comment,CommentDto.class);
    }
    public Comment mapToEntity(CommentDto commentDto){
       /* Comment comment= new Comment();
        comment.setName(commentDto.getName());
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());*/
        return mapper.map(commentDto,Comment.class);
    }
}
