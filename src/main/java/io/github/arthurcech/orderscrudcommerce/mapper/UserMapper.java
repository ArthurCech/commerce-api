package io.github.arthurcech.orderscrudcommerce.mapper;

import io.github.arthurcech.orderscrudcommerce.dto.auth.AuthenticationResponse;
import io.github.arthurcech.orderscrudcommerce.dto.user.RegisterPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.UpdateUserPayload;
import io.github.arthurcech.orderscrudcommerce.dto.user.UserResponse;
import io.github.arthurcech.orderscrudcommerce.entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    User toUser(RegisterPayload payload);

    UserResponse toUserResponse(User user);

    AuthenticationResponse toAuthenticationResponse(User user);

    void updateUserFromPayload(
            UpdateUserPayload payload,
            @MappingTarget User user
    );

}
