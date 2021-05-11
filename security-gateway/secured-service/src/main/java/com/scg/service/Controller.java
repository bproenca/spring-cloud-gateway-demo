package com.scg.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    private static final Logger LOG = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private Environment env;

    private DTO buildDto() {
        DTO dto = new DTO();
        dto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            dto.setHostName(localHost.getHostName());
            dto.setHostAddress(localHost.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return dto;
    }

    @GetMapping("/api/tenant/{tenant}/resource")
    public DTO multiTenantResource(@AuthenticationPrincipal Jwt jwt, @PathVariable(required = true) String tenant) {
        String expectedClientID = "client-tenant-" + tenant;
        DTO dto = buildDto();
        if (expectedClientID.equalsIgnoreCase(jwt.getClaims().get("clientId").toString())) {
            dto.setMessage(String.format("Ok | clientId: %s allowed to access tenant: %s", jwt.getClaims().get("clientId"),
                    tenant));
        } else {
            dto.setMessage(String.format("Error | clientId: %s cannot access tenant: %s", jwt.getClaims().get("clientId"),
                    tenant));
        }
        return dto;
    }

    @GetMapping("/api/private")
    public DTO privateResource(@AuthenticationPrincipal Jwt jwt) {
        LOG.debug("\n***** JWT Headers: {}\n", jwt.getHeaders());
        LOG.debug("\n***** JWT Claims: {}\n", jwt.getClaims().toString());
        LOG.debug("\n***** JWT Token: {}\n", jwt.getTokenValue());
        LOG.debug("\n***** JWT Audience: {}\n", jwt.getAudience());
        LOG.debug("\n***** JWT Expires at: {}\n", jwt.getExpiresAt());
        LOG.debug("\n***** JWT Issuer: {}\n", jwt.getIssuer());

        DTO dto = buildDto();
        dto.setMessage(String.format("Resource accessed by: %s (with subjectId: %s)", jwt.getClaims().get("clientId"),
                jwt.getSubject()));
        return dto;
    }

    @GetMapping("/api/public")
    public DTO publicResource() {
        DTO dto = buildDto();
        dto.setMessage("Public resource " + LocalTime.now());
        return dto;
    }
}
