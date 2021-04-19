package com.Kiiko.ExhibitionsApp.api;

import com.Kiiko.ExhibitionsApp.controller.model.UserModel;
import com.Kiiko.ExhibitionsApp.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "User management API")
@RequestMapping("api/v1/users")
public interface UserApi {
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email", paramType = "path", required = true, value = "User email")
    })
    @ApiOperation("Get user by email")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/{email}")
    UserModel getUserByEmail(@PathVariable String email);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User ID")
    })
    @ApiOperation("Get user by userId")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/userId/{userId}")
    UserModel getUserById(@PathVariable Long userId);

    @ApiOperation("Add user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserModel addUser(@Valid @RequestBody UserDto userDto);

    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = "path", required = true, value = "User ID")
    })
    @ApiOperation("Update user with userId")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    UserModel updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Long userId);
}
