package com.something.riskmanagement.common.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.something.riskmanagement.common.exception.BaseRuntimeException;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by H_Gohargazi
 * on 9/5/2023
 */

public class CustomDateDeserializer extends StdDeserializer<Date> {

    private SimpleDateFormat formatter =
            new SimpleDateFormat("yyyy-MM-dd");


    public CustomDateDeserializer() {
        this(null);
    }

    public CustomDateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new BaseRuntimeException("error.date.format.invalid");
        }
    }

}
