package org.health.assignment.healthzassignment.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.health.assignment.healthzassignment.dto.UserDto;
import org.health.assignment.healthzassignment.exception.BadRequestException;
import org.health.assignment.healthzassignment.service.IUserService;
import org.health.assignment.healthzassignment.utils.TokenUtil;
import org.health.assignment.healthzassignment.validation.CreateUserGroup;
import org.health.assignment.healthzassignment.validation.UpdateUserGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.security.sasl.AuthenticationException;

/**
 * Controller class to handle  the user related API.
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final IUserService userService;

    private final TokenUtil tokenUtil;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(IUserService userService, TokenUtil tokenUtil) {
        this.userService = userService;
        this.tokenUtil = tokenUtil;
    }

    /**
     * Endpoint for creating a new user.
     *
     * @param userDto the user data transfer object containing details of the user to be created,
     *                validated against the CreateUserGroup constraints
     * @return a ResponseEntity containing the saved user details
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> createUser(@RequestBody @Validated(CreateUserGroup.class) UserDto userDto, HttpServletRequest request) {
        validateQueryParameters(request);
        validateReadOnlyFields(userDto);
        LOGGER.debug("Creating the user.");
        UserDto savedUser = userService.saveUserDetails(userDto);
        return ResponseEntity.ok().body(savedUser);
    }

    /**
     * This API will get the user details of that email address
     * which is provided in authorization token as basic token.
     *
     * @return User details.
     */
    @GetMapping(value = "/self", produces = "application/json")
    public ResponseEntity<UserDto> getSelfUserDetails(HttpServletRequest request) throws AuthenticationException {
        validateQueryParameters(request);
        String emailAddress = this.tokenUtil.validateAndGetEmailAddressFromToken();
        UserDto savedUser = userService.getUserDetails(emailAddress);
        return ResponseEntity.ok().body(savedUser);
    }

    private void validateQueryParameters(HttpServletRequest request) {
        if (request.getQueryString() != null) {
            throw new BadRequestException("Query Parameters are not allowed for this API.");
        }
    }

    private void validateReadOnlyFields(UserDto user) {
        if (user.getId() != null) {

            throw new BadRequestException("ID field is read only.");
        } else if (user.getAccountCreated() != null) {
            throw new BadRequestException("account_created field cannot be provided.");
        } else if (user.getAccountUpdated() != null) {
            throw new BadRequestException("account_updated field cannot be provided.");
        }
    }

    /**
     * This API will update the user details of user associated with that email
     * address which is provided in Authorization header as basic token.
     *
     * @param userDto
     * @return
     */
    @PutMapping(value = "/self", produces = "application/json")
    public ResponseEntity<UserDto> updateSelfUserDetails(@RequestBody @Validated(UpdateUserGroup.class) UserDto userDto, HttpServletRequest request) throws AuthenticationException {
        validateQueryParameters(request);
        validateReadOnlyFields(userDto);
        String emailAddress = this.tokenUtil.validateAndGetEmailAddressFromToken();
        userService.updateUserDetails(emailAddress, userDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path = "/self", method = {RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.POST,
            RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE})
    public void handleUnsupportedMethodForUserSelfApi(HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException(request.getMethod());
    }

    @RequestMapping(method = {RequestMethod.PATCH, RequestMethod.DELETE, RequestMethod.PUT,
            RequestMethod.HEAD, RequestMethod.OPTIONS, RequestMethod.TRACE, RequestMethod.GET})
    public void handleUnsupportedMethodForUserApi(HttpServletRequest request) throws HttpRequestMethodNotSupportedException {
        throw new HttpRequestMethodNotSupportedException(request.getMethod());
    }


}
