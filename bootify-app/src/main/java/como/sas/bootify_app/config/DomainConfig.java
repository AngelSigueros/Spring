package como.sas.bootify_app.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("como.sas.bootify_app.domain")
@EnableJpaRepositories("como.sas.bootify_app.repos")
@EnableTransactionManagement
public class DomainConfig {
}
