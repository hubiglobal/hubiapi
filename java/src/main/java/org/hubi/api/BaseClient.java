package org.hubi.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Slf4j
public abstract class BaseClient {
    static final ThreadLocal<SimpleDateFormat> DATE_FORMAT
        = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    static final TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /**
     * 构造请求客户端
     */
    @Getter
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    protected BaseClient(AccessKeys accessKeys, ObjectMapper objectMapper) {
        this(new HttpClient(accessKeys), objectMapper);
    }

    protected BaseClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.client = httpClient;
        this.objectMapper = objectMapper;
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.objectMapper.setDateFormat(DATE_FORMAT.get());
        this.objectMapper.setTimeZone(DEFAULT_TIME_ZONE);
    }


    protected RuntimeException exception(Response response) throws IOException {
        JavaType jt = objectMapper.getTypeFactory().constructMapType(Map.class, String.class, String.class);
        Map<String, String> map = objectMapper.readValue(response.body().string(), jt);
        if (map.containsKey("message")) {
            return new RuntimeException(map.get("message"));
        }
        return new RuntimeException(response.body().string());
    }

    protected <T> T stringToObject(Response response, Class<T> valueType) throws IOException {
        if (response.isSuccessful()) {
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, valueType);
        } else {
            throw exception(response);
        }

    }

    protected <T> T stringToObject(Response response, Class<T> parametrized, Class<?>... parameterClasses) throws IOException {
        if (response.isSuccessful()) {
            JavaType jt = objectMapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, jt);
        } else {
            throw exception(response);
        }
    }


    protected <T> List<T> stringToList(Response response, Class<T> elementType) throws IOException {
        if (response.isSuccessful()) {
            JavaType jt = objectMapper.getTypeFactory().constructCollectionType(List.class, elementType);
            String s = response.body().string();
            log.debug(s);
            return objectMapper.readValue(s, jt);
        } else {
            throw exception(response);
        }

    }

}
