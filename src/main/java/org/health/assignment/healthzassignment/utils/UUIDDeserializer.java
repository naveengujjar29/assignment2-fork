package org.health.assignment.healthzassignment.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.health.assignment.healthzassignment.exception.BadRequestException;

import java.io.IOException;
import java.util.UUID;

public class UUIDDeserializer extends JsonDeserializer<UUID> {

    @Override
    public UUID deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String uuidStr = p.getText();
        try {
            return UUID.fromString(uuidStr);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid UUID format.");
        }
    }
}
