package jpa.buddy.blog.app.mappers;

import jpa.buddy.blog.app.dtos.PostDto;
import jpa.buddy.blog.app.entities.Post;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface PostMapper {
    Post postDtoToPost(PostDto postDto);

    PostDto postToPostDto(Post post);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Post updatePostFromPostDto(PostDto postDto, @MappingTarget Post post);
}
