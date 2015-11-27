package pl.mcx.corrupted.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.system.ApplicationPidFileWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import pl.mcx.corrupted.project.security.sm.SystemModule;

import java.time.Clock;
import java.util.Map;


@SpringBootApplication
@EnableIntegration
@IntegrationComponentScan
@ComponentScan({"org.apache.camel.spring.issues.contextscan",
                       "pl.mcx.corrupted.project",
                       "org.apache.camel.spring.boot"})
public class MainApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    public static void main(final String[] args) {
        SpringApplication springApplication = new SpringApplication(MainApp.class);
        springApplication.addListeners(new ApplicationPidFileWriter());
        springApplication.setRegisterShutdownHook(false);
        springApplication.run(args);

        LOGGER.info("Application started!");
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    @Primary
    public SystemModule systemModule(final Map<String, SystemModule> systemModules) {
        return systemModules.get("basicSM");
    }

}
