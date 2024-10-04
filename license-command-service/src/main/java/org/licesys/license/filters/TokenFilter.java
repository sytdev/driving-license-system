package org.licesys.license.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.licesys.license.filters.utils.UserContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class TokenFilter implements Filter {

    private final ObjectMapper mapper;

    public TokenFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //TODO whitelist which endpoints don't request for a token
        //use method shouldNotFilter from OncePerRequestFilter
        if("/api-docs.yml".equalsIgnoreCase(request.getRequestURI()) ||
            "/actuator/prometheus".equalsIgnoreCase(request.getRequestURI())) {
            filterChain.doFilter(request, servletResponse);
            return;
        }

        String authValue = request.getHeader("Authorization");
        JsonNode jsonObj = decodeJWT(authValue);
        String username = jsonObj.get("preferred_username").asText();
        String fullName = jsonObj.get("name").asText();

        UserContextHolder.getContext().setUsername(username);
        UserContextHolder.getContext().setAuthToken(authValue);
        UserContextHolder.getContext().setFullName(fullName);

        filterChain.doFilter(request, servletResponse);
    }


    private JsonNode decodeJWT(String authHeader) throws JsonProcessingException {
        String accessToken = authHeader.replace("Bearer ", "");
        String[] split_string = accessToken.split("\\.");
        String base64EncodedBody = split_string[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody));
        JsonNode jsonObj = mapper.readTree(body);
        return jsonObj;
    }
}

