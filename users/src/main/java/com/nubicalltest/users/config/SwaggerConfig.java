package com.nubicalltest.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.nubicalltest.users.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo()).securitySchemes(Lists.newArrayList(apiKey()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User API").description("User API specification").version("1.0.0")
				.license("MIT").licenseUrl("https://opensource.org/licenses/MIT").contact(new Contact("Marcelo Wieja",
						"https://linkedin.com/in/marcelo-david-wieja/", "marcelo.wieja@gmail.com"))
				.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("Authorization", "api_key", "header");
	}
}
