package ml.jjeaby.RestAssuredSample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 // Swagger 2.0 version을 사용 annotation
@Configuration
@EnableWebMvc // WebMvc 사용 설정 annotation
@ComponentScan({ "ml.jjeaby.RestAssuredSample" }) // REST API 를 찾을 Package 지정
											// annotation

public class SwaggerConfiguration {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(springfox.documentation.builders.PathSelectors.regex("/rest/.*")).build();
	}

}
