package ch.admin.bj.swiyu.registry.status.authoring.common.config;

import ch.admin.bit.jeap.security.resource.token.JeapAuthenticationToken;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableJpaAuditing
@Slf4j
public class AuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            JeapAuthenticationToken auth = (JeapAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
            if (!auth.isAuthenticated()) {
                log.error("Data is being mutated by an anonymous user. This should not happen.");
                return Optional.empty();
            }
            if (auth.getTokenSubject() == null) {
                // This is a special case for test execution, because @WithJeapAuthenticationToken doesn't set the subject
                log.error("Data is being mutated by an user without subject. This should not happen.");
                return Optional.of("anonymous");
            }
            // Example <subject>:<name>
            // from apiGw -> <ref_identifier_for_dev-pams_7b24f978-afe7-4f60-af2f-57ae5a01d303>:<>
            // from portal -> <B4F0FC08-D843-46B4-628B-72991BF521E4>:<Max Mustermann>
            return Optional.ofNullable(auth.getName())
                .map(name -> "%s:%s".formatted(auth.getTokenSubject(), name))
                .or(() -> Optional.of(auth.getTokenSubject()));
        };
    }
}
