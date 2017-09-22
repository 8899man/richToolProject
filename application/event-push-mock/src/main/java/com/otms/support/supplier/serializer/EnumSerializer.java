package com.otms.support.supplier.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.otms.support.supplier.database.define.DBEnum;

import java.io.IOException;

/**
 * Created by bert on 2017/8/12.
 */
public class EnumSerializer extends JsonSerializer<Enum> {

    @Override
    public void serialize(Enum anEnum, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
            throws IOException {

        if (anEnum == null) {
            return;
        }

        if (anEnum instanceof DBEnum) {
            jsonGenerator.writeNumber(((DBEnum) anEnum).getConstant());
            String fieldName = jsonGenerator.getOutputContext().getCurrentName();
            jsonGenerator.writeStringField(fieldName + "Message", ((DBEnum) anEnum).getMessage());
        }
    }
}
