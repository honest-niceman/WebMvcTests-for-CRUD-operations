package jpa.buddy.blog.app.controllers;

import jpa.buddy.blog.app.dtos.PostDto;
import jpa.buddy.blog.app.entities.Post;
import jpa.buddy.blog.app.mappers.PostMapper;
import jpa.buddy.blog.app.repositories.PostRepository;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostController(PostMapper postMapper, PostRepository postRepository) {
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    @GetMapping("/post/find/{title}")
    public List<PostDto> findByTitle(@PathVariable String title) {
        List<Post> postList = postRepository.findByTitle(title);
        return postList.stream().map(postMapper::postToPostDto).collect(Collectors.toList());
    }

    @PostMapping("/post/new")
    public PostDto savePost(@RequestBody @NonNull PostDto postDto) {
        Post postEntity = postMapper.postDtoToPost(postDto);
        return postMapper.postToPostDto(postRepository.save(postEntity));
    }

    @PutMapping("/post/update")
    public PostDto updatePost(@RequestBody @NonNull PostDto postDto) {
        if (postDto.getId() == null)
            throw new IllegalArgumentException("Post ID is missing. Use /new to create a post");
        Post postEntity = postRepository.findById(postDto.getId()).orElseThrow(EntityNotFoundException::new);

        return postMapper.postToPostDto(
                postRepository.save(
                        postMapper.updatePostFromPostDto(postDto, postEntity)
                )
        );
    }

    @DeleteMapping("/post/delete/{id}")
    public PostDto deletePost(@PathVariable @NonNull Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        postRepository.delete(post);
        return postMapper.postToPostDto(post);
    }
}