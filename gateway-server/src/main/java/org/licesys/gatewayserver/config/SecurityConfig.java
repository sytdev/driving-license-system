package org.licesys.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtGrantedAuthoritiesConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {

        return http.authorizeExchange(auth -> auth
                        .pathMatchers(HttpMethod.POST,"/license-cmd/api/v1/command.licenses").hasAnyRole("CREATOR")
                        .pathMatchers(HttpMethod.POST, "/license-cmd/api/v1/command.owners").hasAnyRole("CREATOR")

                        .pathMatchers(HttpMethod.PUT, "/license-cmd/api/v1/command.licenses/**").hasAnyRole("EDITOR")
                        .pathMatchers(HttpMethod.PUT, "/license-cmd/api/v1/command.owners/**").hasAnyRole("EDITOR")

                        .pathMatchers(HttpMethod.POST, "/license-cmd/api/v1/command.licenses/invalidate/**").hasAnyRole("REMOVER")

                        .pathMatchers(HttpMethod.GET, "/license-qry/api/v1/query.licenses/validate/**").hasAnyRole("VIEWER")
                        .pathMatchers(HttpMethod.POST, "/license-qry/api/v1/query.licenses/**").hasAnyRole("VIEWER")

                        //TODO - MANAGE API DOCS BETTER(SECURITY, ACCESS)
                        .pathMatchers("/license-cmd/api-docs.yml").permitAll()
                        .pathMatchers("/license-qry/api-docs.yml").permitAll()
                        .anyExchange().authenticated())
                .csrf(csrfSpec -> csrfSpec.disable())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
                .build();
    }

    private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        Converter<Jwt, Collection<GrantedAuthority>> jwtGrantedAuthoritiesConverter = jwt ->
                (Collection<GrantedAuthority>) extractResourceRoles(jwt,"licesys");

        var jwtAuthenticationConverter = new ReactiveJwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(
                new ReactiveJwtGrantedAuthoritiesConverterAdapter(jwtGrantedAuthoritiesConverter));

        return jwtAuthenticationConverter;
    }

    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt, final String resourceId)
    {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (resourceAccess != null && (resource = (Map<String, Object>) resourceAccess.get(resourceId)) != null &&
                (resourceRoles = (Collection<String>) resource.get("roles")) != null)
            return resourceRoles.stream()
                    .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                    .collect(Collectors.toSet());
        return Collections.emptySet();
    }
}
