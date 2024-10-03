package org.health.assignment.healthzassignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import org.health.assignment.healthzassignment.utils.UUIDDeserializer;
import org.health.assignment.healthzassignment.validation.CreateUserGroup;
import org.health.assignment.healthzassignment.validation.UpdateUserGroup;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Class to interact for user details request model.
 */
public class UserDto implements Serializable {

    @JsonProperty("id")
    @JsonDeserialize(using = UUIDDeserializer.class)
    @Null(message = "Id field can not be provided. Read only field.", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private UUID id;

    @JsonProperty("first_name")
    @NotBlank(message = "first name cannot be blank", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    @Size(min = 1, max = 255, groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Last name cannot be blank", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    @Size(min = 1, max = 255, groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private String lastName;

    @JsonProperty("email")
    @NotBlank(groups = CreateUserGroup.class, message = "Email should not be empty")
    @Null(groups = UpdateUserGroup.class, message = "Email cannot be updated.")
    @Email(groups = CreateUserGroup.class, message = "Provide a valid email")
    @Size(min = 1, max = 255)
    private String email;

    @JsonProperty(value = "password", access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "password should not be blank", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    @NotNull(message = "Password is required.", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    @Size(min = 8, max = 15, message = "Password size should be between 8 to 15 character.", groups = {CreateUserGroup.class, UpdateUserGroup.class})
    private String password;


    @JsonProperty(value = "account_created")
    private Date accountCreated;

    @JsonProperty(value = "account_updated")
    private Date accountUpdated;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getAccountCreated() {
        return accountCreated;
    }

    public void setAccountCreated(Date accountCreated) {
        this.accountCreated = accountCreated;
    }

    public Date getAccountUpdated() {
        return accountUpdated;
    }

    public void setAccountUpdated(Date accountUpdated) {
        this.accountUpdated = accountUpdated;
    }
}
