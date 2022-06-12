package workshop.springb.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import workshop.springb.testing.model.Response;
import workshop.springb.testing.service.GreetService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*
    TODO 2 zamień @SpringBootTest na @WebMvcTest
    @WebMvcTest  charakteryzuje testy wyizolowanej warstwy kontrolera.
    Nie potrzebujemy adnotacji @AutoConfigureMockMvc - podejrzyj @WebMvcTest, będzie wiadomo dlaczego :).
 */
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest
class GreetControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GreetService greetService;

    /*
        TODO 3 pora na mock'owanie - zanim to zrobimy, uruchom test i zaobserwuj logi:
        "Caused by: org.springframework.beans.factory.NoSuchBeanDefinitionException:  No qualifying bean of type 'workshop.springb.testing.service.GreetService' available(...)"


        GreetController ma zależność GreetService z obecną konfiguracją (@WebMvcTest - tylko warstwa kontrolera),
        Spring nie wie skąd wziąć ziarno o typie GreetService.

        Pora na mock GreetService:

        1. Utwórz pole o typie serwisu i użyj adnotacji @MockBean

        @MockBean
        private BookService bookService;

     */

    @Test
    @DisplayName("http://localhost/greet -> 400")
    public void greetEndpoint_missingName_missingIsFormal_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("http://localhost/greet?name=X&isFormal=true -> 200")
    public void greetEndpoint_name_X_IsFormal_true_shouldReturn200() throws Exception {

        /*
            TODO 4 mamy nasz mock, pora na zdefiniowanie zachowania metody GreetService#greet:

            Jeśli podczas wywołania GreetService#greet przekażesz X i true metoda zwróci new Response("Hello, X!", LocalDateTime.now()):

            Mockito.when(greetService.greet("X", true)).thenReturn(new Response("Hello, X!", LocalDateTime.now()));
         */

        var expectedResponse = new Response("Hello, X!", LocalDateTime.now());

        when(greetService.greet("X", true)).thenReturn(expectedResponse);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json")
                .param("name", "X")
                .param("isFormal", "true"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());

        String jsonAsString = resultActions.andReturn().getResponse().getContentAsString();
        Response response = objectMapper.readValue(jsonAsString, Response.class);

        assertEquals("Hello, X!", response.getGreeting());
    }

    @Test
    @DisplayName("http://localhost/greet?name=X -> 400")
    public void greetEndpoint_name_X_missingIsFormal_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .param("name", "X")
                .contentType("application/json")
                )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("http://localhost/greet?isFormal=true -> 200")
    public void greetEndpoint_missingName_IsFormal_true_shouldReturn200() throws Exception {
        var expectedResponse = new Response("Hello, World!", LocalDateTime.now());

        when(greetService.greet("World", true)).thenReturn(expectedResponse);
        /*
            TODO 5 pora na Twoją implementację - analagicznie do todos'a 4  i w oparciu o nazwę testu / komunikat z
             @DisplayName, ustaw zachowanie metody greetService.greet
         */

        mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json")
                .param("isFormal", "true"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("Hello, World!"));
    }

    @Test
    @DisplayName("http://localhost/greet?name=X&isFormal=X -> 400")
    public void greetEndpoint_name_X_IsFormal_X_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet?name=X")
                .contentType("application/json")
                .param("isFormal", "X"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

/*
 _______________________________________________________________________________________________________________________
 TODO 1

 W testach akceptacyjnych testowaliśmy pełen kontekst aplikacji - wywoływaliśmy endpoint, metoda kontrolera serwis,
 serwis repozytorium, z kolei ono łączyło się z bazą danych.

 Teraz skupimy się na testach warstwy kontrolera - kontekst Springa będzie 'okrojony'.

 Potrzebujemy dokonać drobnej modyfikacji konfiguracji testu:

 1. Zmienimy adnotacje na poziomie klasy.
 2. Ponieważ testujemy tylko warstwę kontrolera,
 którego metoda korzysta z API serwisu, potrzebujemy mock serwisu - użyjemy adnotacji @MockBean.

 Czas na refactor - zajrzyj do kolejgego todos'a.
 _______________________________________________________________________________________________________________________
                                                         \
                                                          \
                                                            /  \~~~/  \
                                                           (    ..     )~~~~,
                                                            \__     __/      \
                                                              )|  /)         |\
                                                               | /\  /___\   / ^
                                                                "-|__|   |__|
 */
