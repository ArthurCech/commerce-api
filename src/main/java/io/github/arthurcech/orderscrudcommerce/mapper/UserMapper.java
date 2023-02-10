package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.user.UserCreatePayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserUpdatePayload;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(UserCreatePayload payload);

    UserResponse toUserResponse(User user);

    void updateUserFromPayload(UserUpdatePayload payload, @MappingTarget User user);

}
