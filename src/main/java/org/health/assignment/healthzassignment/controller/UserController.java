package org.health.assignment.healthzassignment.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.health.assignment.healthzassignment.dto.UserDto;
import org.health.assignment.healthzassignment.service.IUserService;
import org.health.assignment.healthzassignment.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class to handle  the user related API.
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtil tokenUtil;

    /**
     * This API will create the user.
     *
     * @param userDto
     * @return
     */
    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUserDetails(userDto);
        return ResponseEntity.ok().body(savedUser);
    }

    /**
     * This API will get the user details of that email address
     * which is provided in authorization token as basic token.
     *
     * @return
     */
    @GetMapping(value = "/self", produces = "application/json")
    public ResponseEntity<UserDto> getSelfUserDetails() {
        String authorizationToken = request.getHeader("Authorization");
        // Validate the token.
        boolean isTokenValid = this.tokenUtil.validateToken(authorizationToken);
        if (!isTokenValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String emailAddress = this.tokenUtil.getEmailAddressFromToken(authorizationToken);
        UserDto savedUser = userService.getUserDetails(emailAddress);
        return ResponseEntity.ok().body(savedUser);
    }

    /**
     * This API will update the user details of user associated with that email
     * address which is provided in Authorization header as basic token.
     *
     * @param userDto
     * @return
     */
    @PutMapping(value = "/self", produces = "application/json")
    public ResponseEntity<UserDto> updateSelfUserDetails(@RequestBody UserDto userDto) {
        String authorizationToken = request.getHeader("Authorization");
        //Validate the token.
        boolean isTokenValid = this.tokenUtil.validateToken(authorizationToken);
        if (!isTokenValid) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        String emailAddress = this.tokenUtil.getEmailAddressFromToken(authorizationToken);
        UserDto savedUser = userService.updateUserDetails(emailAddress, userDto);
        return ResponseEntity.ok().body(savedUser);
    }


}
