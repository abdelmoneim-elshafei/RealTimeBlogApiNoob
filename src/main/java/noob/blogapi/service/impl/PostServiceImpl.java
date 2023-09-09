package noob.blogapi.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import noob.blogapi.entity.Comment;
import noob.blogapi.entity.Post;
import noob.blogapi.exception.ResourceNotFoundException;
import noob.blogapi.payload.CommentDto;
import noob.blogapi.payload.PostDto;
import noob.blogapi.payload.PostResponse;
import noob.blogapi.repository.PostRepository;
import noob.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    final ModelMapper mapper;
    CommentServiceImpl commentService;
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post newPost = postRepository.save(post);
        return mapToDTO(newPost);
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
       Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
       Page<Post> pageList = postRepository.findAll(pageable);
       List<Post> list = pageList.getContent();
     /*  List<PostDto> postDtoList = new ArrayList<>();
       for (Post p: list) {
            PostDto postDto = mapToDTO(p);
            postDtoList.add(postDto);
       }
      return postDtoList;*/
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
        postResponse.setLast(pageList.isLast());
        postResponse.setPageNumber(pageNo);
        postResponse.setPageSize(pageSize);
        postResponse.setTotalPages(pageList.getTotalPages());
        postResponse.setTotalElements(pageList.getTotalElements());
        return  postResponse;
    }

    @Override
    public PostDto getById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updateById(Long id, PostDto postDto) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post","id",id));
        postRepository.deleteById(id);
    }

    private PostDto mapToDTO(Post newPost){

        /*PostDto newPostDto = new PostDto();
        newPostDto.setId(newPost.getId());
        newPostDto.setTitle(newPost.getTitle());
        newPostDto.setContent(newPost.getContent());
        newPostDto.setDescription(newPost.getDescription());*/
        return mapper.map(newPost,PostDto.class);
    }
    private Post mapToEntity(PostDto postDto){

        /*Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());*/

        return mapper.map(postDto,Post.class);
    }
}
