//package demo;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
//import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
////@EnableJdbcHttpSession
//public class Config {
//
////sessions are variables which are held in the browser.
//
////    @Bean
////    public EmbeddedDatabase dataSource() {
////        return new EmbeddedDatabaseBuilder()
////                .setType(EmbeddedDatabaseType)
////                .addScript("org/springframework/session/jdbc/schema-h2.sql").build();
////    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}