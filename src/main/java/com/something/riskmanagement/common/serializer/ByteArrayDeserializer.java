package com.something.riskmanagement.common.serializer;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class ByteArrayDeserializer extends JsonDeserializer<byte[]> {

    @Override
    public byte[] deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List<Byte> byteList = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);
        Iterator<JsonNode> elements = node.elements();
        while (elements.hasNext()) {
            JsonNode element = elements.next();
            int i = element.asInt();
            byteList.add((byte) i);
        }

        byte[] bytes = new byte[byteList.size()];

        for (int i = 0; i < byteList.size(); i++) {
            bytes[i] = byteList.get(i);
        }

        return bytes;
//        String base64 = node.asText();
//        return base64.getBytes();

    }
}
