package app.mapper;

import app.controller.UserController;
import app.model.dto.UserDto;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<UserDto, EntityModel<UserDto>> {
    @Override
    public EntityModel<UserDto> toModel(UserDto userDto) {
        return EntityModel.of(userDto,
                linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel()
        );
    }

    @Override
    public CollectionModel<EntityModel<UserDto>> toCollectionModel(Iterable<? extends UserDto> userDtos) {
        return RepresentationModelAssembler.super.toCollectionModel(userDtos)
                .add(linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
    }
}
