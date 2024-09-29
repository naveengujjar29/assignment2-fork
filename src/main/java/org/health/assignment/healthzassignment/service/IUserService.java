package org.health.assignment.healthzassignment.service;

import org.health.assignment.healthzassignment.dto.UserDto;

public interface IUserService {

    /**
     * This method will save the user details.
     *
     * @param UserDto
     * @return saved user details
     */
    UserDto saveUserDetails(UserDto UserDto);

    /**
     * This method will get the user details associated with that email address.
     *
     * @param emailId
     * @return saved user details.
     */
    UserDto getUserDetails(String emailId);

    /**
     * This method will update the user details associated with an email address.
     *
     * @param emailAddress
     * @param userDto
     * @return updated user details.
     */
    UserDto updateUserDetails(String emailAddress, UserDto userDto);
}
