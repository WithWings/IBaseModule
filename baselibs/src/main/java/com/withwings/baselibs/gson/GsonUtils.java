package com.withwings.baselibs.gson;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> String toJson(List<T> list) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.toJson(list,listType);
    }

    public static <T> T fromJson(String json,Class<T> tClass){
        Gson gson = new Gson();
        return gson.fromJson(json,tClass);
    }

    public static <T> List<T> fromJsonList(String json,Class<List<T>> listClass) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json,listType);
    }

}