/*
 * SPDX-FileCopyrightText: 2025 Swiss Confederation
 *
 * SPDX-License-Identifier: MIT
 */

package ch.admin.bj.swiyu.registry.status.authoring.common.config;

import jakarta.validation.constraints.NotEmpty;
import java.net.URL;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "status-registry")
@Getter
@Setter
public class StatusRegistryProperties {

    /**
     * The format template to create a data read response for a status list entry.
     * Format specifiers:
     *   {0} -> The entry ID as UUID
     *   {1} -> The entry extension as string
     */
    @NotEmpty
    private String dataUrlTemplate;

    @NotEmpty
    private URL authoringBaseUrl;
}
