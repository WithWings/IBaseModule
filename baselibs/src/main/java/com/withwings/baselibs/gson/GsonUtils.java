package com.withwings.baselibs.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

    //线程安全的
    private static final Gson GSON;

    static {
        GSON = new GsonBuilder()
                //                .excludeFieldsWithoutExposeAnnotation() //不导出实体中没有用@Expose注解的属性
                .enableComplexMapKeySerialization() //支持Map的key为复杂对象的形式
                //                .serializeNulls().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS")//时间转化为特定格式
                //                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)//会把字段首字母大写,注:对于实体上使用了@SerializedName注解的不会生效.
                .setPrettyPrinting() //对json结果格式化. 自动换行，对于输出问件时有效
                .setVersion(1.0)    //有的字段不是一开始就有的,会随着版本的升级添加进来,那么在进行序列化和返序列化的时候就会根据版本号来选择是否要序列化.
                //@Since(版本号)能完美地实现这个功能.还的字段可能,随着版本的升级而删除,那么
                //@Until(版本号)也能实现这个功能,GsonBuilder.setVersion(double)方法需要调用.
                .create();
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    public static String toJsonList(List list) {
        Type listType = new TypeToken<List>() {
        }.getType();
        return GSON.toJson(list, listType);
    }

    public static <T> List<T> fromJsonList(String json, Type listType) {
        return GSON.fromJson(json, listType);
    }

    public static <T> T fromFile(File file, Class<T> tClass) {
        if (!file.exists()) {
            return null;
        }

        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return GSON.fromJson(reader, tClass);
    }

    public static <T> List<T> fromFileList(File file, Type listType) {
        if (!file.exists()) {
            return null;
        }

        FileReader reader;
        try {
            reader = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return GSON.fromJson(reader, listType);
    }

    /**
     * 将数据解析存入 File 文件
     *
     * @param object 数据类
     * @param file   文件
     */
    public static boolean toFile(Object object, File file) {
        if (file.exists() && file.isFile()) {
            boolean delete = file.delete();
            if(!delete) {
                return false;
            }
        }
        try {
            boolean newFile = file.createNewFile();
            if(!newFile) {
                return false;
            }
            FileWriter fileWriter = new FileWriter(file);
            return toFile(object, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean toFile(Object object, Writer writer) {
        String s = GSON.toJson(object);
        try {
            writer.write(s);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将数据解析存入 File 文件
     *
     * @param tList 数据类
     * @param file  文件
     */
    public static boolean toFileList(List tList, File file) {
        if (file.exists() && file.isFile()) {
            boolean delete = file.delete();
            if(!delete) {
                return false;
            }
        }
        try {
            boolean newFile = file.createNewFile();
            if(!newFile) {
                return false;
            }
            FileWriter fileWriter = new FileWriter(file);
            return toFileList(tList, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean toFileList(List tList, Writer writer) {
        String s = GSON.toJson(tList);
        try {
            writer.write(s);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}