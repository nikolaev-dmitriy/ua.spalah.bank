package ua.spalah.bank.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Man on 23.03.2017.
 */
@Configuration
@Import(PersistenceConfig.class)
@ComponentScan("ua.spalah.bank.services")
public class RootConfig {
}
