package com.itcast.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JSON处理辅助功能
 */
public final class JsonUtils {

    /**
     * MAP对象类型
     */
    private static final MapType MAP_TYPE;

    /**
     * MAP对象类型
     */
    private static final CollectionType LIST_TYPE;

    /**
     * 默认JSON对象映射器
     */
    private static ObjectMapper defaultMapper;

    // 静态变量初始化
    static {
        MAP_TYPE = TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, Object.class);
        LIST_TYPE = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, MAP_TYPE);
        defaultMapper = new ObjectMapper();
        defaultMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        defaultMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        defaultMapper.enable(JsonParser.Feature.ALLOW_MISSING_VALUES);
        defaultMapper.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        defaultMapper.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    }

    /**
     * 构造方法（静态类禁止创建）
     */
    private JsonUtils() {
    }

    /**
     * 对象输出JSON文本
     *
     * @param out    输出
     * @param object 对象
     */
    public static void toOutput(OutputStream out, Object object) {
        if (object == null) {
            return;
        }
        try {
            defaultMapper.writeValue(out, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 对象转换为JSON文本
     *
     * @param object 对象
     * @return String JSON文本
     */
    public static String toText(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return defaultMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文本转换为对象
     *
     * @param <T>      类型
     * @param jsonText JSON文本
     * @param cls      类型
     * @return T 数据对象
     */
    public static <T> T toObject(String jsonText, Class<T> cls) {
        if (jsonText == null || jsonText.isEmpty()) {
            return null;
        }
        try {
            return defaultMapper.readValue(jsonText, cls);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文本转换为对象
     *
     * @param jsonText JSON文本
     * @return Map
     */
    public static Map<String, Object> toMap(String jsonText) {
        if (jsonText == null || jsonText.isEmpty()) {
            return null;
        }
        try {
            return defaultMapper.readValue(jsonText, MAP_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文本转换为对象
     *
     * @param <T>      类型
     * @param jsonText JSON文本
     * @param cls      类型
     * @return Map
     */
    public static <T> Map<String, T> toMap(String jsonText, Class<T> cls) {
        if (jsonText == null || jsonText.isEmpty()) {
            return null;
        }
        try {
            return defaultMapper.readValue(jsonText,
                    TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, cls));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文本转换为列表
     *
     * @param jsonText JSON文本
     * @return List<Map>
     */
    public static List<Map<String, Object>> toList(String jsonText) {
        if (jsonText == null || jsonText.isEmpty()) {
            return null;
        }
        try {
            return defaultMapper.readValue(jsonText, LIST_TYPE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * JSON文本转换为列表
     *
     * @param <T>      类型
     * @param jsonText JSON文本
     * @param cls      类型
     * @return List<T> 数据列表
     */
    public static <T> List<T> toList(String jsonText, Class<T> cls) {
        if (jsonText == null || jsonText.isEmpty()) {
            return null;
        }
        try {
            return defaultMapper.readValue(jsonText,
                    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, cls));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
