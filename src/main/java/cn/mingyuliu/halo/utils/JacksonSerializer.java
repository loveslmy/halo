package cn.mingyuliu.halo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Copyright (c) 1999-2017 携程旅行网
 * all rights reserved
 *
 * @author duwl
 * 2017/5/15.9:32
 */
public final class JacksonSerializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JacksonSerializer.class);
    private static final JacksonSerializer DEFAULT = new JacksonSerializer();
    /**
     * 线程安全(官方:can reuse, share globally)
     */
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        /*
         * 序列化的规则
         */
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    }

    public static JacksonSerializer defaultSerializer() {
        return DEFAULT;
    }

    /**
     * 使用jackson序列化对象
     *
     * @param data object
     * @return json string
     */
    public String serialize(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (IOException e) {
            LOGGER.error("serialize data error", e);
            return null;
        }
    }

    /**
     * 反序列化
     *
     * @param content 序列化后的字符串
     * @param clazz   class
     * @param <T>     type
     * @return object
     */
    public <T> T deserialize(String content, Class<T> clazz) {
        try {
            return MAPPER.readValue(content, clazz);
        } catch (IOException e) {
            LOGGER.error("serialize data error", e);
            return null;
        }
    }

}
