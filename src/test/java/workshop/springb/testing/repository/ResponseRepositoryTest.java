package workshop.springb.testing.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// TODO 1 wyłączenie domyślenj konfiguracji bazy
// @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ResponseRepositoryTest {

    @Autowired
    private ResponseRepository responseRepository;

    @DisplayName("Postgres DB initialized with one record (TestContainers)")
    @Test
    void shouldReturnOnlyOneRecord() {
        assertEquals(1, responseRepository.findAll().size());
    }

}