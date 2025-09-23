package com.example.Gymify.it.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonMapper {
    private static final ObjectMapper objectMapper=new ObjectMapper();

    public static   <T> T jsonToClass(String json, Class <T> tClass) throws Exception {
        return objectMapper.readValue(json, tClass);
        // <T> ExerciseDto sau orice alta clasa si nu mai punem si la parametru, punem direct in readValue
    }

    public static   <T> String classToJson(T tDto) throws  Exception {
        return objectMapper.writeValueAsString(tDto);
        //<T> punem T ca sa putem pune in parametru T Tdto,
        // dar puteam pune si fara si in () punem Object tdto
    }
}
