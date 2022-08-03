package jpa.buddy.blog.app.dtos;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;

public class PostDto implements Serializable {
    private final Long id;
    private final UserDto user;
    private final String title;
    private final String text;
    private final OffsetDateTime publishedAt;

    public PostDto(Long id, UserDto user, String title, String text, OffsetDateTime publishedAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.text = text;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public UserDto getUser() {
        return user;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public OffsetDateTime getPublishedAt() {
        return publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDto entity = (PostDto) o;
        return Objects.equals(this.id, entity.id) &&
               Objects.equals(this.user, entity.user) &&
               Objects.equals(this.title, entity.title) &&
               Objects.equals(this.text, entity.text) &&
               Objects.equals(this.publishedAt, entity.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, title, text, publishedAt);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
               "id = " + id + ", " +
               "user = " + user + ", " +
               "title = " + title + ", " +
               "text = " + text + ", " +
               "publishedAt = " + publishedAt + ")";
    }
}
