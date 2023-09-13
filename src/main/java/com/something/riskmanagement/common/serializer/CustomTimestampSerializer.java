package com.something.riskmanagement.common.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class CustomTimestampSerializer extends StdSerializer<Date> {

    private static SimpleDateFormat formatter
            = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public CustomTimestampSerializer() {
        this(null);
    }

    public CustomTimestampSerializer(Class<Date> t) {
        super(t);
    }

    @Override
    public void serialize(
            Date value, JsonGenerator gen, SerializerProvider arg2)
            throws IOException, JsonProcessingException {
        gen.writeString(formatter.format(value));
    }
}
