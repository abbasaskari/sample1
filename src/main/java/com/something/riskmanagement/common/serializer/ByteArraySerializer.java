package com.something.riskmanagement.common.serializer;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ByteArraySerializer extends JsonSerializer<byte[]> {

    @Override
    public void serialize(byte[] value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (byte b : value) {
            gen.writeNumber(unsignedToBytes(b));
        }
        gen.writeEndArray();
    }

    private int unsignedToBytes(byte b) {
        return b & 0xFF;
    }

}
