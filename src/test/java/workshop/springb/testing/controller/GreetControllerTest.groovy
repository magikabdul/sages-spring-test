package workshop.springb.testing.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification
import spock.lang.Title

@Title("GreetController Specification")
@SpringBootTest
@AutoConfigureMockMvc
class WebControllerTest extends Specification {

    @Autowired
    private MockMvc mvc

    def "when GET /greet is performed, without isFormal param then the response has status 400 "() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mvc.perform(MockMvcRequestBuilders.get("/greet")).andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    def "when GET /greet?name=X&isFormal=true then the response has status 200 "() {
        expect: "Status is 200 and the response is 'Hello world!'"
        mvc.perform(MockMvcRequestBuilders.get("/greet?name=X&isFormal=true")).andExpect(MockMvcResultMatchers.status().isOk())
    }
}
