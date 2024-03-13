package com.example.baseballscoresheet.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NoArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@NoArgsConstructor
public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    public static final String CLAIMS = "claims";
    private final JwtGrantedAuthoritiesConverter defaultGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

    /**
     * This method extracts resource roles from a Jwt token.
     * It uses an ObjectMapper to read the Jwt token and extract the roles from the resource_access, realm_access, aud, and groups claims.
     * @param jwt the Jwt token to extract the roles from
     * @return a collection of GrantedAuthority representing the extracted roles
     * @throws JsonProcessingException if there is an error processing the Jwt token
     */
    private static Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt) throws JsonProcessingException {
        Set<GrantedAuthority> resourcesRoles = new HashSet<>();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        resourcesRoles.addAll(extractResourceAccess("resource_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get(CLAIMS)));
        resourcesRoles.addAll(extractRealAccess("realm_access", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get(CLAIMS)));
        resourcesRoles.addAll(extractAud("aud", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get(CLAIMS)));
        resourcesRoles.addAll(extractGroups("groups", objectMapper.readTree(objectMapper.writeValueAsString(jwt)).get(CLAIMS)));
        return resourcesRoles;
    }

    /**
     * This method extracts resource access roles from a JsonNode representing a Jwt token.
     * It iterates over the elements of the resource_access claim and adds the roles to a set with the prefix "ROLE_".
     * @param route the route to the resource_access claim in the JsonNode
     * @param jwt the JsonNode representing the Jwt token
     * @return a list of GrantedAuthority representing the extracted roles
     */
    private static List<GrantedAuthority> extractResourceAccess(String route, JsonNode jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();

        jwt.path(route)
                .elements()
                .forEachRemaining(e -> e.path("roles")
                        .elements()
                        .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText()))
                );

        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    /**
     * This method extracts realm access roles from a JsonNode representing a Jwt token.
     * It iterates over the elements of the realm_access claim and adds the roles to a set with the prefix "ROLE_".
     * @param route the route to the realm_access claim in the JsonNode
     * @param jwt the JsonNode representing the Jwt token
     * @return a list of GrantedAuthority representing the extracted roles
     */
    private static List<GrantedAuthority> extractRealAccess(String route, JsonNode jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();

        jwt.path(route)
                .elements()
                .forEachRemaining(e -> e.elements()
                        .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText()))
                );

        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    /**
     * This method extracts the audience from a JsonNode representing a Jwt token.
     * It iterates over the elements of the aud claim and adds the audience to a set with the prefix "AUD_".
     * @param route the route to the aud claim in the JsonNode
     * @param jwt the JsonNode representing the Jwt token
     * @return a list of GrantedAuthority representing the extracted audience
     */
    private static List<GrantedAuthority> extractAud(String route, JsonNode jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();

        jwt.path(route)
                .elements()
                .forEachRemaining(e -> rolesWithPrefix.add("AUD_" + e.asText()));

        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    /**
     * This method extracts the groups from a JsonNode representing a Jwt token.
     * It iterates over the elements of the groups claim and adds the groups to a set with the prefix "GROUP_".
     * @param route the route to the groups claim in the JsonNode
     * @param jwt the JsonNode representing the Jwt token
     * @return a list of GrantedAuthority representing the extracted groups
     */
    private static List<GrantedAuthority> extractGroups(String route, JsonNode jwt) {
        Set<String> rolesWithPrefix = new HashSet<>();

        jwt.path(route)
                .elements()
                .forEachRemaining(e -> rolesWithPrefix.add("GROUP_" + e.asText()));

        return AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
    }

    /**
     * This method converts a Jwt token into an JwtAuthenticationToken.
     * It calls the getGrantedAuthorities method to extract the authorities from the Jwt token and creates a new JwtAuthenticationToken with the extracted authorities.
     * @param source the Jwt token to convert
     * @return an JwtAuthenticationToken representing the converted Jwt token
     */
    public AbstractAuthenticationToken convert(final Jwt source) {
        Collection<GrantedAuthority> authorities = null;
        try {
            authorities = this.getGrantedAuthorities(source);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new JwtAuthenticationToken(source, authorities);
    }

    /**
     * This method extracts the granted authorities from a Jwt token.
     * It calls the defaultGrantedAuthoritiesConverter's convert method and the extractResourceRoles method to extract the authorities from the Jwt token.
     * @param source the Jwt token to extract the authorities from
     * @return a collection of GrantedAuthority representing the extracted authorities
     * @throws JsonProcessingException if there is an error processing the Jwt token
     */
    public Collection<GrantedAuthority> getGrantedAuthorities(Jwt source) throws JsonProcessingException {
        return Stream.concat(this.defaultGrantedAuthoritiesConverter.convert(source).stream(),
                extractResourceRoles(source).stream()).collect(Collectors.toSet());
    }
}