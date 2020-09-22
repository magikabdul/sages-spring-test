package workshop.springb.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import workshop.springb.testing.model.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/*

    TODO 1 README

    Dla utrwalnia, poniżej opisy klasy i metod testowych


    @SpringBootTest wystartuje całą aplikację Spring dla testów
    @AutoConfigureMockMvc porposi Sping o dostarczenie domyślnej instancji obiektu MockMvc.

    @Autowired - Srping wstrzyknie instancję obiektu do pola (wstrzykiwanie do pola nie jest zalecane... ale w teście jest ok).
    @Test - adnotacja JUnit oznaczająca testową metodę
    @DisplayName adnotacja JUnit (5) pozwalająca na bardziej rozbudowane opisy testu - przy uruchomieniu testu wyświetli nam się opis z tej adnotacji.


    Poniżej tabelka ze scenariuszami testowymi, metody są już zaimplementowane.
    Testujemy na razie tylko statusy odpowiedzi, za chwilę zrefaktorujemy kod.

        +---------------+-------------+---------------------------+-----------------+
        | endpoint  url | name param  | isFormal param            | returned status |
        +---------------+-------------+---------------------------+-----------------+
        |               |             |                           |                 |
        | /greet        | -           | -                         | 400             |
        |               |             |                           |                 |
        | /greet        | +           | +                         | 200             |
        |               |             |                           |                 |
        | /greet        | +           | -                         | 400             |
        |               |             |                           |                 |
        | /greet        | -           | +                         | 200             |
        |               |             |                           |                 |
        | /greet        | +           |present but not as boolean | 400             |
        +---------------+-------------+---------------------------+-----------------+


    Poniżej opid co API mockMvc może dla nas zrobić (to oczywiście  tylko część opcji)


    mockMvc.perform(MockMvcRequestBuilders.get("/greet?name=X")              | wyślij żądanie na endpoint /greet z HTTP GET, dodaj  do url 'name' z wartośćią 'X'
                .contentType("application/json")                             | ustaw wartość
                .param("isFormal", "X"))                                     | dodaj parametr 'isFormal' z wartośćią 'X' (alternatywny sposób na dodanie parametru do url'a)
                .andDo(print())                                              | dodj logowanie żądań (możesz uruchmić test z i bez andDo i zobaczyćróżnicę w logach)
                .andExpect(MockMvcResultMatchers.status().isBadRequest());   | test przejdzie, jeśli zwrócony statuc to 400

 */
@SpringBootTest
@AutoConfigureMockMvc
class GreetControllerTest {
    /*
        TODO 2
        Jak na razie testowaliśmy tylko statusy, nasza aplikacja zwraca obiekty w postaci JSON'a.
        Jeśli chcemy przetestować zwracany obiekt, mamy różne opcje, jedną z nich jest użycie ObjectMapper, którego API
        w wygodny sposób przekonwertuje zwracany JSON do obiektu Java. Następnie przetestujemy, czy pla zwróconego
        obiektu mają oczekiwane wartości. Kolejnym sposobem jest użycie JsonPAth do testowoania wartości w zwróconym JSONie.



        Mamy 2 metody, które oczekują status HTTP 200, zrefaktorujmy je tak, żeby przetestować wartości zwracanego obiektu:

        greetEndpoint_name_X_IsFormal_true_shouldReturn200() - użyjemy ObjectMapper
        greetEndpoint_missingName_IsFormal_true_shouldReturn200() - użyjemy JsonPath

        Przejdźdo kolejnego todos'a - użycie ObjectMapper


     */

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("http://localhost/greet -> 400")
    public void greetEndpoint_missingName_missingIsFormal_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    /*
        TODO 3 ObjectMapper API, konwersja java<->json.

        1. Potrzebujemy instancji ObjectMapper'a, 2 sposoby:
            @Autowired
            private ObjectMapper objectMapper;

            or

            private ObjectMapper objectMapper = new ObjectMapper();

        2. mockMvc.perform zwraca obiekt ResultActions - użyj jego API do pobrania JSON'a (String)

        String jsonAsString = resultActions.andReturn().getResponse().getContentAsString();

        3. użyj API ObjectMapper'a dla konwersji String (JSON) do biektu Java (nasz kontroler zwraca workshop.springb.testing.model.Response)

        Response response = objectMapper.readValue(jsonAsString, Response.class);

        4. Przetestuj pole obiektu, czy ma oczekiwaną wartość

        assertEquals("Hello, X!", response.getGreeting());
     */

    @Test
    @DisplayName("http://localhost/greet?name=X&isFormal=true -> 200")
    public void greetEndpoint_name_X_IsFormal_true_shouldReturn200() throws Exception {

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json")
                .param("name", "X")
                .param("isFormal", "true"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
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

    /*
        TODO 4 użycie JsonPath

        .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("Hello, World!"));

        Oba warianty (3 i 4) są stosowane produkcyjnie, w zależnosci od potrzeb  ツ
     */
    @Test
    @DisplayName("http://localhost/greet?isFormal=true -> 200")
    public void greetEndpoint_missingName_IsFormal_true_shouldReturn200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet")
                .contentType("application/json")
                .param("isFormal", "true"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
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
    TODO 5 zadanie extra - bez rozwiązania ツ

    W naszej aplikacji endpoin oczekuje parametrów z url, spróbuj zrefaktorować aplikację / testy tak,
    żeby zamiast przekazywać paramtery w url, klient wysyłał POST z obiektem np. workshop.springb.testing.model.Request,
    w postaci JSON'a, z którego metoda kontrollera wyciągnie dane do procesowania
 */