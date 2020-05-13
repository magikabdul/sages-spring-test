package workshop.springb.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

/*
    TODO 2 testy w Spring - ściągawka
 */
// Cały kontekst Spring
@SpringBootTest
class StarterApplicationTests {

    @Test
    void contextLoads() {
    }

}

// Spring - tylko kontrolery
@WebMvcTest
class Controllers {
}
// No Spring - testy JUnit + Mockito
@ExtendWith(MockitoExtension.class)
class Service_JUnit {
}
// Spring - tylko warstwa bazodanowa
@DataJpaTest
class Repositories {
}
// Ogólna struktura klasy w teście Spring
/*
    Adnotacje omocnicze
    - użyj Spring, Junit, Spock etc.
 */
class TestClass {
    // JUnit test - struktura metody testowej
    @Test
    void testImportantLogic() {
        /*

            1. given

            2. when

            3. then
                    - assertTrue, assertEquals, assertNotNull etc.
         */
    }

}