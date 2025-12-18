package tobyspring.hellospring;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import tobyspring.hellospring.application.required.order.OrderRepository;
import tobyspring.hellospring.application.required.order.OrderRepositoryImpl;

import javax.sql.DataSource;

import static org.springframework.orm.jpa.vendor.Database.H2;

@Configuration
public class DataConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("tobyspring.hellospring.domain");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {
            {
                setDatabase(H2);
                setGenerateDdl(true);
                setShowSql(true);
            }
        });
        return emf;
    }

    @Bean
    public OrderRepository orderRepository(EntityManagerFactory emf) {
        return new OrderRepositoryImpl(emf);
    }
}
