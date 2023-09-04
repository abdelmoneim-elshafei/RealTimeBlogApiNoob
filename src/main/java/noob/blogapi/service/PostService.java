package noob.blogapi.service;

import noob.blogapi.payload.PostDto;
import noob.blogapi.payload.PostResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);
    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostDto getById(Long id);
    PostDto updateById(Long id, PostDto postDto);
    void deleteById(Long id);
}
