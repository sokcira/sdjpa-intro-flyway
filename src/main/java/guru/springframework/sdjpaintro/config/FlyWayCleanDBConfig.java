package guru.springframework.sdjpaintro.config;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("clean")
@Configuration
public class FlyWayCleanDBConfig {

    @Bean
    public FlywayMigrationStrategy dropAndCreateDB() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
            flyway.info();
        };
    }
}
