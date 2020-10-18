package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.util.List;

/**
 * description:
 * author: lipanpan
 * date: 2020/5/15 下午3:46
 */
@Configuration
@ConditionalOnClass({ObjectMapper.class, Jackson2ObjectMapperBuilder.class})
public class JacksonObjectMapperConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperCustomizer() {
        return new Jackson2ObjectMapperCustomizer();
    }

    @Bean
    @Primary
    public ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
        ObjectMapper objectMapper = builder.createXmlMapper(false).build();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JsonSerializer jsonSerializer = new NullArrayJsonSerializer();
        BeanSerializerModifier beanSerializerModifier = new ArraySerializerModifier(jsonSerializer);
        SerializerFactory serializerFactory = objectMapper.getSerializerFactory().withSerializerModifier(beanSerializerModifier);
        objectMapper.setSerializerFactory(serializerFactory);
        return objectMapper;
    }

    /**
     * 普通序列化定制
     */
    private class Jackson2ObjectMapperCustomizer implements Jackson2ObjectMapperBuilderCustomizer {

        @Override
        public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
            jacksonObjectMapperBuilder.serializationInclusion(JsonInclude.Include.NON_NULL);
            //jacksonObjectMapperBuilder.simpleDateFormat(CommConstants.DATE_TIME_FORMAT);
        }

    }

    /**
     * 数组，列表类型序列化定制
     */
    private class ArraySerializerModifier extends BeanSerializerModifier {

        private JsonSerializer jsonSerializer;

        public ArraySerializerModifier(JsonSerializer jsonSerializer) {
            this.jsonSerializer = jsonSerializer;
        }

        @Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc,
                                                         List<BeanPropertyWriter> beanProperties) {
            for (int i = 0; i < beanProperties.size(); i++) {
                BeanPropertyWriter writer = beanProperties.get(i);
                if (isArrayType(writer)) {
                    writer.assignNullSerializer(jsonSerializer);
                }
            }
            return beanProperties;
        }

        private boolean isArrayType(BeanPropertyWriter writer) {
            JavaType javaType = writer.getType();
            return javaType.isArrayType() || javaType.isCollectionLikeType();
        }

    }

    /**
     * 数组，List, Collection, Set等为null，序列化返回[]
     */
    private class NullArrayJsonSerializer extends JsonSerializer {

        @Override
        public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            if (value == null) {
                gen.writeStartArray();
                gen.writeEndArray();
            } else {
                gen.writeObject(value);
            }
        }

    }

}
