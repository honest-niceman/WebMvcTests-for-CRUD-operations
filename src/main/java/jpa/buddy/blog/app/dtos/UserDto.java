package jpa.buddy.blog.app.dtos;

import java.io.Serializable;

public record UserDto(Long id, String firstName, String lastName, String email) implements Serializable { }
