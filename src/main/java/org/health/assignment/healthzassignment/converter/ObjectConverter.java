package org.health.assignment.healthzassignment.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * An utility to map the request DTO with DB model beans class or vice versa.
 */
@Component
public class ObjectConverter {

    private final ModelMapper modelMapper = new ModelMapper();

    public Object convert(final Object source, final Class<?> destination) {
        try {
            return this.modelMapper.map(source, destination);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to map");
        }
    }
}
