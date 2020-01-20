package org.united.hubi.api.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author HUBI
 */
@Data
@RequiredArgsConstructor
public class AccessKeys {
    private final String tokenType = "Bearer";
    private final String key;
    private final String secret;
    private final String accessToken;

    public String authorization() {
        return tokenType + " " + accessToken;
    }
}
