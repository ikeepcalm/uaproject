package dev.ua.ikeepcalm.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FormWrapper {
    private String nickname;
    private String birthday;
    private String launcher;
    private String person;
    private String source;
    private String hobbies;
    private String tasks;
    private String experience;
    private String conflicts;
    private String memory;

    public static FormWrapper wrapForm(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, FormWrapper.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
