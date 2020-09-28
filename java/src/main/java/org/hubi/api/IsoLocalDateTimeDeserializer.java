package org.hubi.api;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.format.DateTimeFormatter;

public class IsoLocalDateTimeDeserializer extends LocalDateTimeDeserializer {
    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    public IsoLocalDateTimeDeserializer() {
        super(FORMATTER);
    }
}
