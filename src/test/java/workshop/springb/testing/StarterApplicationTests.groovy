package workshop.springb.testing

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title
import workshop.springb.testing.controller.GreetController

@Title("Application Specification")
@Narrative("Specification which beans are expected")
@SpringBootTest
class LoadContextTest extends Specification {

    @Autowired(required = false)
    private GreetController webController


    def "when context is loaded then all expected beans are created"() {
        expect: "the GreetController is created"
        webController
    }
}

/*
    TODO 1 Uruchom testy i zapoznaj się z nimi: to proste przykłady na użycie spock

    Zapoznaj się z poniższymi artykułami:
    -https://spockframework.org/spock/docs/2.0/spock_primer.html#_comparison_to_junit
    -https://www.baeldung.com/spring-spock-testing
 */