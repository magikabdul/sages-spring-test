package workshop.springb.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import workshop.springb.testing.model.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @Autowired
    private ObjectMapper mapper;

    private final static String URL = "/greet";
    private final static String PARAM_NAME = "name";
    private final static String PARAM_IS_FORMAL = "isFormal";

    private final static String FIELD_ID = "id";
    private final static String FIELD_GREETING = "greeting";
    private final static String FIELD_LOCAL_DATE_TIME = "localDateTime";

    @Test
    @DisplayName("http://localhost/greet -> 400")
    public void greetEndpoint_missingName_missingIsFormal_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
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

        var result = mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .contentType("application/json")
                .param(PARAM_NAME, "X")
                .param(PARAM_IS_FORMAL, "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var response = result.getResponse().getContentAsString();

        var value = mapper.readValue(response, Response.class);

        assertThat(value)
                .isInstanceOf(Response.class)
                .hasFieldOrPropertyWithValue(FIELD_GREETING, "Hello, X!");;
    }

    @Test
    @DisplayName("http://localhost/greet?name=X -> 400")
    public void greetEndpoint_name_X_missingIsFormal_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                .param(PARAM_NAME, "X")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    /*
        TODO 4 użycie JsonPath

        .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("Hello, World!"));

        Oba warianty (3 i 4) są stosowane produkcyjnie, w zależnosci od potrzeb  ツ
     */
    @Test
    @DisplayName("http://localhost/greet?isFormal=true -> 200")
    public void greetEndpoint_missingName_IsFormal_true_shouldReturn200() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param(PARAM_IS_FORMAL, "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.greeting").value("Hello, World!"))
                .andReturn();
    }

    @Test
    @DisplayName("http://localhost/greet?name=X&isFormal=X -> 400")
    public void greetEndpoint_name_X_IsFormal_X_shouldReturn400() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/greet?name=X")
                .contentType("application/json")
                .param("isFormal", "X"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @DisplayName("POST /greet (request body fields empty) -> 200")
    void postGreetingWithNoFieldProvided_thenReturn200() {
        var request = Request.builder().build();

        var asString = mapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);

        assertThat(response)
                .hasFieldOrPropertyWithValue(FIELD_ID, 1L)
                .hasFieldOrPropertyWithValue(FIELD_GREETING, "Hi, World!")
                .hasFieldOrProperty(FIELD_LOCAL_DATE_TIME);
    }

    @SneakyThrows
    @Test
    @DisplayName("POST /greet (request body isFormal:true) -> 200")
    void postGreetingWithFieldIsFormalTrueProvided_thenReturn200() {
        var request = Request.builder().isFormal(true).build();

        var asString = mapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);

        assertThat(response)
                .hasFieldOrPropertyWithValue(FIELD_ID, 1L)
                .hasFieldOrPropertyWithValue(FIELD_GREETING, "Hello, World!")
                .hasFieldOrProperty(FIELD_LOCAL_DATE_TIME);
    }

    @SneakyThrows
    @Test
    @DisplayName("POST /greet (request body isFormal:true and name:tom) -> 200")
    void postGreetingWithFieldIsFormalTrueAndNameProvided_thenReturn200() {
        var request = Request.builder()
                .name("Tom")
                .isFormal(true)
                .build();

        var asString = mapper.writeValueAsString(request);

        MvcResult result = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asString))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        Response response = mapper.readValue(result.getResponse().getContentAsString(), Response.class);

        assertThat(response)
                .hasFieldOrPropertyWithValue(FIELD_ID, 1L)
                .hasFieldOrPropertyWithValue(FIELD_GREETING, "Hello, Tom!")
                .hasFieldOrProperty(FIELD_LOCAL_DATE_TIME);
    }
}

/*
    TODO 5 zadanie extra - bez rozwiązania ツ

    W naszej aplikacji endpoint oczekuje parametrów z url, spróbuj zrefaktorować aplikację / testy tak,
    żeby zamiast przekazywać parametry w url, klient wysyłał POST z obiektem np. workshop.spring.testing.model.Request,
    w postaci JSON'a, z którego metoda kontrolera wyciągnie dane do procesowania
 */
