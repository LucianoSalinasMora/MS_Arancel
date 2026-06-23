package SigueTuCarrera.Arancel.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import jakarta.persistence.GeneratedValue;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI ArancelOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Arancel (MS_Arancel)")
                        .description("Microservicio para la gestión de aranceles del sistema académico")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Duoc UC")
                                .email("contacto@duocuc.cl")));
    }

@Bean
    public io.swagger.v3.core.converter.ModelConverter generatedValueReadOnlyConverter() {
        return new io.swagger.v3.core.converter.ModelConverter() {
            @Override
            public io.swagger.v3.oas.models.media.Schema resolve(
                    io.swagger.v3.core.converter.AnnotatedType type, 
                    io.swagger.v3.core.converter.ModelConverterContext context, 
                    java.util.Iterator<io.swagger.v3.core.converter.ModelConverter> chain) {
                
                
                io.swagger.v3.oas.models.media.Schema<?> schema = 
                        (chain.hasNext()) ? chain.next().resolve(type, context, chain) : null;
                
                
                if (schema != null && schema.getProperties() != null && type.getType() instanceof Class<?>) {
                    Class<?> clazz = (Class<?>) type.getType();
                    
                    for (java.lang.reflect.Field field : clazz.getDeclaredFields()) {
                        if (field.isAnnotationPresent(GeneratedValue.class)) {
                            io.swagger.v3.oas.models.media.Schema<?> propertySchema = 
                                    (io.swagger.v3.oas.models.media.Schema<?>) schema.getProperties().get(field.getName());
                            if (propertySchema != null) {
                                propertySchema.setReadOnly(true);
                            }
                        }
                    }
                }
                return schema;
            }
        };
    }
}
