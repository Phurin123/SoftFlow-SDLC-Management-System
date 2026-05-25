package com.softflow.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * JPA Auditor Aware implementation.
 * Returns the current username for @CreatedBy and @LastModifiedBy.
 * In production, this should integrate with Spring Security to get the authenticated user.
 */
@Component("auditorAwareImpl")
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // TODO: Integrate with Spring Security to get authenticated username
        // For now, return a system default
        return Optional.of("system");
    }
}
