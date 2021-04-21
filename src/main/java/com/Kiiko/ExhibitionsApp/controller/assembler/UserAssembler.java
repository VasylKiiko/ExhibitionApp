package com.Kiiko.ExhibitionsApp.controller.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.Kiiko.ExhibitionsApp.controller.TicketController;
import com.Kiiko.ExhibitionsApp.controller.UserController;
import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import com.Kiiko.ExhibitionsApp.dto.TicketDto;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto userDto) {
        UserModel userModel = new UserModel(userDto);
        Link getById = linkTo(methodOn(UserController.class).getUserById(userDto.getUserId())).withRel("get");
        Link getByEmail = linkTo(methodOn(UserController.class).getUserByEmail(userDto.getEmail())).withRel("get");
        Link addUser = linkTo(methodOn(UserController.class).addUser(userDto)).withRel("create");
        Link updateUser = linkTo(methodOn(UserController.class).updateUser(userDto, userDto.getUserId())).withRel("update");

        Link getUsersTickets = linkTo(methodOn(TicketController.class).getUsersTickets(userDto.getUserId())).withRel("getUsersTickets");

        userModel.add(getById, getByEmail, addUser, updateUser, getUsersTickets);
        return userModel;
    }


}
