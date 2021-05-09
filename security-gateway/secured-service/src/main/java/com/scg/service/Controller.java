package com.scg.service;

import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    @GetMapping("/api/tenant/{tenant}/resource")
    public String multiTenantResource(@AuthenticationPrincipal Jwt jwt, @PathVariable(required = true) String tenant) {
        String expectedClientID = "client-tenant-" + tenant;
        if (expectedClientID.equalsIgnoreCase(jwt.getClaims().get("clientId").toString())) {
            return String.format("Or | clientId: %s allowed to access tenant: %s", jwt.getClaims().get("clientId"),
                    tenant);
        } else {
            return String.format("Error | clientId: %s cannot access tenant: %s", jwt.getClaims().get("clientId"),
                    tenant);
        }
    }

    @GetMapping("/api/private")
    public String privateResource(@AuthenticationPrincipal Jwt jwt) {
        LOG.debug("\n***** JWT Headers: {}\n", jwt.getHeaders());
        LOG.debug("\n***** JWT Claims: {}\n", jwt.getClaims().toString());
        LOG.debug("\n***** JWT Token: {}\n", jwt.getTokenValue());
        LOG.debug("\n***** JWT Audience: {}\n", jwt.getAudience());
        LOG.debug("\n***** JWT Expires at: {}\n", jwt.getExpiresAt());
        LOG.debug("\n***** JWT Issuer: {}\n", jwt.getIssuer());

        return String.format("Resource accessed by: %s (with subjectId: %s)", jwt.getClaims().get("clientId"),
                jwt.getSubject());
    }

    @GetMapping("/api/public")
    public String publicResource() {
        return "Public resource " + LocalTime.now();
    }
}
