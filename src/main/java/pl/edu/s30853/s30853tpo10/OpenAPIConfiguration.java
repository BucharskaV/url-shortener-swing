package pl.edu.s30853.s30853tpo10;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("s30853-TPO10");

        Contact myContact = new Contact();
        myContact.setName("s30853 Vladyslava Bucharska");

        Info information = new Info()
                .title("s30853-TPO10 API")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}
