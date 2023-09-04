package noob.blogapi.controller;

import noob.blogapi.entity.Post;
import noob.blogapi.payload.PostDto;
import noob.blogapi.payload.PostResponse;
import noob.blogapi.repository.PostRepository;
import noob.blogapi.service.PostService;
import noob.blogapi.service.impl.PostServiceImpl;
import noob.blogapi.utils.AppConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
class PostControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PostService postService;

    @Test
    void getAllPosts() throws Exception {
       List<PostDto> posts = new ArrayList<>();
       PostDto postDto = new PostDto();
       postDto.setTitle("Hello");
       postDto.setId(19L);
       postDto.setContent("Hello");
       postDto.setDescription("Hello");

       PostDto postDto2 = new PostDto();
       postDto.setTitle("Hello");
       postDto.setId(19L);
       postDto.setContent("Hello");
       postDto.setDescription("Hello");
       posts.add(postDto);
       posts.add(postDto2);
       PostResponse postResponse = new PostResponse();
       postResponse.setContent(posts);

       given(postService.getAllPosts(0,10, AppConstants.DEFAULT_SORT_BY, AppConstants.DEFAULT_SORT_DIRECTION)).willReturn(postResponse);
       mockMvc.perform(get("/api/posts").accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.content.length()",is(posts.size())));

    }
}