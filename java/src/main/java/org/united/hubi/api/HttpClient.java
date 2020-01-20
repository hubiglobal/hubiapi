package org.united.hubi.api;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.united.hubi.api.domain.AccessKeys;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class HttpClient {

    private static final Charset DEFAULT_CHARSET = Charsets.UTF_8;
    private static final ThreadLocal<SimpleDateFormat> ISO_DATE_FORMAT
        = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sssZ"));
    private static final Joiner SIGNATURE_PARAMS_JOINER = Joiner.on(',');
    private static final Joiner.MapJoiner PARAMS_JOINER = Joiner.on("&").withKeyValueSeparator("=").useForNull("");

    private final OkHttpClient httpClient = new OkHttpClient.Builder().build();
    private final String baseUrl;
    private final String version = "1.0.0";
    private AtomicInteger seqNum = new AtomicInteger(1);
    private final AccessKeys accessKeys;

    public HttpClient(AccessKeys accessKeys) {
        this("https://api.hubi.com", accessKeys);
    }

    public HttpClient(String baseUrl, AccessKeys accessKeys) {
        this.baseUrl = baseUrl;
        this.accessKeys = accessKeys;
    }

    public Response postForm(String url, Map<String, String> parameters) {
        try {
            Request request = new Request.Builder()
                .headers(this.sign(url, parameters))
                .url(baseUrl + url)
                .post(this.formBody(parameters))
                .build();

            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response put(String url, Map<String, String> parameters) {
        try {
            Request request = new Request.Builder()
                .headers(this.sign(url, parameters))
                .url(baseUrl + url)
                .put(this.formBody(parameters))
                .build();

            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response get(String url) {
        try {
            Request request = new Request.Builder()
                .headers(this.sign(url, Maps.newHashMap()))
                .url(baseUrl + buildGetUrl(url, Maps.newHashMap()))
                .get()
                .build();

            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Response get(String url, Map<String, String> parameters) {
        try {
            Request request = new Request.Builder()
                .headers(this.sign(url, parameters))
                .url(baseUrl + buildGetUrl(url, parameters))
                .get()
                .build();

            return httpClient.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildGetUrl(String url, Map<String, String> parameters) {
        StringBuilder queryUrl = new StringBuilder(url);
        if (parameters != null && !parameters.isEmpty()) {
            queryUrl.append("?");
            parameters.forEach(
                (key, value) -> {
                    queryUrl.append(key).append("=").append(value).append("&");
                }
            );
            queryUrl.deleteCharAt(queryUrl.lastIndexOf("&"));
        }
        return queryUrl.toString();
    }

    /**
     * 构建 from 表单参数
     *
     * @param parameters 请求参数
     * @return 返回请求的 {@link RequestBody}
     */
    @NotNull
    private RequestBody formBody(Map<String, String> parameters) {
        FormBody.Builder builder = new FormBody.Builder();
        if (parameters != null) {
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    /**
     * 请求签名
     *
     * @param url        要请求的URL
     * @param parameters 请求参数
     * @return 返回请求头信息{@link Headers}
     */
    private Headers sign(String url, Map<String, String> parameters) {
        Headers.Builder headers = new Headers.Builder();

        headers.add("Authorization", accessKeys.authorization());

        Date now = new Date();
        //"yyyy-MM-dd'T'HH:mm:ssZ"
        String timestamp = ISO_DATE_FORMAT.get().format(now);

        //构造X-API-Signature-Params
        String params = SIGNATURE_PARAMS_JOINER.join(parameters.keySet());
        //构造要签名的参数字符串
        String paramsMapString = PARAMS_JOINER.join(parameters);

        //构造X-API-Nonce
        String nonce = accessKeys.getKey() + timestamp + seqNum.getAndIncrement();
        log.debug("nonce=" + nonce);
        String nonceHash = Hashing.md5().hashString(nonce, DEFAULT_CHARSET).toString();
        //构造要签名的内容
        String content = paramsMapString + version + nonceHash + url;

        headers.add("X-API-Key", accessKeys.getKey());
        headers.add("X-API-Version", version);
        headers.add("X-API-Timestamp", timestamp);
        headers.add("X-API-Nonce", nonceHash);
        headers.add("X-API-Signature-Params", params);
        headers.add("X-API-Signature-Method", "HmacSHA256");
        headers.add("X-API-Signature", Hashing.hmacSha256(accessKeys.getSecret().getBytes(DEFAULT_CHARSET)).hashString(content, DEFAULT_CHARSET).toString());
        return headers.build();

    }
}
