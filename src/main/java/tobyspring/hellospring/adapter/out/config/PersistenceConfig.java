package tobyspring.hellospring.adapter.out.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import tobyspring.hellospring.adapter.out.persistence.JdbcOrderRepository;
import tobyspring.hellospring.application.required.order.OrderRepository;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    // About JPA
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        var emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource());
//        emf.setPackagesToScan("tobyspring.hellospring.domain");
//        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter() {
//            {
//                setDatabase(H2);
//                setGenerateDdl(true);
//                setShowSql(true);
//            }
//        });
//        return emf;
//    }
//
//    @Bean
//    public BeanPostProcessor persistenceAnnotationBeanPostProcessor() {
//        return new PersistenceAnnotationBeanPostProcessor();
//    }
    // About JPA is end

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public OrderRepository orderRepository(DataSource dataSource) {
        return new JdbcOrderRepository(dataSource);
    }
}
