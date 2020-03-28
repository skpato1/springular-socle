package com.sifast.web.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.OAuth;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	public static final String SECURITY_SCHEMA_OAUTH2 = "oauth2schema";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.sifast.web.service"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo())
				.securitySchemes(Collections.singletonList(securitySchema()))
				.securityContexts(Collections.singletonList(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Springular Framework", "", "", "", null, "", "", Collections.emptyList());
	}

	private OAuth securitySchema() {
		List<springfox.documentation.service.AuthorizationScope> authorizationScopeList = new ArrayList<>();
		authorizationScopeList.add(new springfox.documentation.service.AuthorizationScope("read", "read all"));
		authorizationScopeList.add(new springfox.documentation.service.AuthorizationScope("trust", "trust all"));
		authorizationScopeList.add(new springfox.documentation.service.AuthorizationScope("write", "access all"));
		List<GrantType> grantTypes = new ArrayList<>();
		GrantType creGrant = new ResourceOwnerPasswordCredentialsGrant("/springular-framework/oauth/token");
		grantTypes.add(creGrant);
		return new OAuth(SECURITY_SCHEMA_OAUTH2, authorizationScopeList, grantTypes);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.ant("/api/")).build();
	}

	private List<SecurityReference> defaultAuth() {
		final AuthorizationScope[] authorizationScopes = new AuthorizationScope[3];
		authorizationScopes[0] = new AuthorizationScope("read", "read all");
		authorizationScopes[1] = new AuthorizationScope("trust", "trust all");
		authorizationScopes[2] = new AuthorizationScope("write", "write all");
		return Collections.singletonList(new SecurityReference(SECURITY_SCHEMA_OAUTH2, authorizationScopes));
	}

	@Bean
	public UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().validatorUrl(null).build();
	}
}
