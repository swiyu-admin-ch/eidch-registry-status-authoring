package ch.admin.bj.swiyu.registry.status.authoring.test;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
public class PostgreSQLContainerInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static PostgreSQLContainer<?> database;

    private static PostgreSQLContainer<?> getDatabase() {
        if (database == null) {
            database = new PostgreSQLContainer<>(
                DockerImageName.parse("docker-hub.nexus.bit.admin.ch/postgres:15.8").asCompatibleSubstituteFor(
                    "postgres:15.8"
                )
            );
            database.start();
        }
        return database;
    }

    public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
        TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
            configurableApplicationContext,
            "spring.datasource.url=" + getDatabase().getJdbcUrl(),
            "spring.datasource.username=" + getDatabase().getUsername(),
            "spring.datasource.password=" + getDatabase().getPassword()
        );
    }
}
