package workshop.springb.testing.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// TODO 1 wyłączenie domyślnej konfiguracji bazy
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ContextConfiguration(initializers = ResponseRepositoryTest.PostgresInitializer.class)
class ResponseRepositoryTest {

//    public static PostgreSQLContainer<?> postgresSQLContainer = new PostgreSQLContainer<>("postgres:14");
//
//    static {
//        postgresSQLContainer.start();
//    }

//    public static class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
//
//        @Override
//        public void initialize(ConfigurableApplicationContext applicationContext) {
//
//            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
//                    applicationContext
////                    "spring.datasource.url=" + postgresSQLContainer.getJdbcUrl(),
////                    "spring.datasource.username=" + postgresSQLContainer.getUsername(),
////                    "spring.datasource.password=" + postgresSQLContainer.getPassword()
//            );
//        }
//
//    }

    @Autowired
    private ResponseRepository responseRepository;

    @DisplayName("Postgres DB initialized with one record (TestContainers)")
    @Test
    void shouldReturnOnlyOneRecord() {
        assertEquals(1, responseRepository.findAll().size());
    }

}
