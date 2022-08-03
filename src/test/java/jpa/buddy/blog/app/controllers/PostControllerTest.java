package jpa.buddy.blog.app.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import jpa.buddy.blog.app.entities.Post;
import jpa.buddy.blog.app.entities.User;
import jpa.buddy.blog.app.repositories.PostRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

@WebMvcTest(PostController.class)
@ComponentScan(basePackages = "jpa.buddy.blog.app")
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PostRepository postRepository;

    @Test
    void findByTitleTest() throws Exception {
        String title = "How to store text?";

        Post post = new Post();
        post.setTitle(title);

        Mockito.when(postRepository.findByTitle(title))
                .thenReturn(List.of(post));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/post/find/{title}", title))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.is(title)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void createPostTest() throws Exception {
        User user = new User();
        user.setFirstName("JPA");
        user.setLastName("Buddy");

        Post post = new Post();
        post.setId(1L);
        post.setUser(user);

        Mockito.when(postRepository.save(post)).thenReturn(post);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/v1/post/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(post));

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.firstName", Matchers.is("JPA")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.user.lastName", Matchers.is("Buddy")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void updatePostTest() throws Exception {
        String newTitle = "How to store digits?";

        Post post = new Post();
        post.setId(1L);
        post.setTitle("How to store text?");

        Post updatedPost = new Post();
        updatedPost.setId(1L);
        updatedPost.setTitle(newTitle);

        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));
        Mockito.when(postRepository.save(updatedPost)).thenReturn(updatedPost);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/api/v1/post/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(updatedPost));

        mockMvc.perform(mockRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", Matchers.is(newTitle)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void deletePostTest() throws Exception {
        Post post = new Post();
        post.setId(1L);
        post.setTitle("How to store text?");

        Mockito.when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/post/delete/{id}", post.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

}