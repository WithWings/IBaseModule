package com.withwings.baselibs.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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

    public static <T> List<T> fromJsonList(String json, Class<T> tClass) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();
        return gson.fromJson(json,listType);
    }

    public static <T> T fromFile(File file, Class<T> tClass){
        if(!file.exists()) {
            return null;
        }
        Gson gson = new Gson();

        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return gson.fromJson(reader,tClass);
    }

    public static <T> List<T> fromFileList(File file, Class<T> tClass){
        if(!file.exists()) {
            return null;
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<T>>(){}.getType();

        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return gson.fromJson(reader,listType);
    }

    /**
     * 将数据解析存入 File 文件
     * @param object
     * @param file
     */
    public static boolean toFile(Object object, File file){
        if(!file.exists()) {
            return false;
        }
        Gson gson = new Gson();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            JsonElement jsonElement = gson.toJsonTree(object);
            JsonWriter jsonWriter = gson.newJsonWriter(fileWriter);
            gson.toJson(jsonElement,jsonWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;

    }

}