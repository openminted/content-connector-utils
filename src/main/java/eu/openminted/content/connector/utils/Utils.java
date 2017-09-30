package eu.openminted.content.connector.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
    public static String json(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }
}
