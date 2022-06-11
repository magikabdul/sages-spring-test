package workshop.springb.testing.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import workshop.springb.testing.model.Response;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/*
    TODO 2 README
    Podejrzyj javadoc dla poniższej adnotacji - opakowuje kilka innych.

    W skrócie -  @DataJpaTest wyłącza pełną autokonfigurację, pozostawiając tylko część istotną
    dla testów warstwy bazodanowej.
    Domyślnie testy z adnotacją @DataJpaTest będą używały bzy danych typu embedded / in-memory.
    Każdy test będzie uruchomiony w springowej transakcji.
 */
@DataJpaTest
class ResponseRepositoryTest {

    /*
        TODO 3 README
        Prosimy Spring o wstrzyknięcie repozytorium
     */
    @Autowired
    private ResponseRepository responseRepository;
    private Response response;

    private final static String FIELD_ID = "id";
    private final static String FIELD_GREETING = "greeting";
    private final static String FIELD_LOCAL_DATE_TIME = "localDateTime";

    @BeforeEach
    void setUp() {
        response = new Response("Hello, Thomas!", LocalDateTime.now());
    }

    /*
        TODO 4 README
        Metoda testowa -  zapisujemy Response w bazie przy użyciu save, sprawdzamy zapisany obiekt
     */
    @Test
    @DisplayName("Should save one Response with id 1L")
    public void shouldSaveAreResponseWithId1() {
        assertTrue(responseRepository.findAll().isEmpty());
        Response savedResponse = responseRepository.save(new Response("Hello, World!", LocalDateTime.now()));
        assertAll(
                () -> assertEquals(1, responseRepository.findAll().size()),
                () -> assertEquals(savedResponse.getId(), responseRepository.findById(1L).get().getId()),
                () -> assertEquals(savedResponse.getGreeting(), responseRepository.findById(1L).get().getGreeting())
        );
    }

    @Test
    @DisplayName("should find one Response by id with id 1L")
    @DirtiesContext
    public void shouldFindResponseById1() {
        responseRepository.save(response);

        Optional<Response> savedResponse = responseRepository.findById(1L);

        assertThat(savedResponse)
                .isInstanceOf(Optional.class)
                .get()
                .isInstanceOf(Response.class)
                .hasNoNullFieldsOrProperties()
                .hasFieldOrPropertyWithValue(FIELD_ID, 1L)
                .hasFieldOrPropertyWithValue(FIELD_GREETING, "Hello, Thomas!")
                .hasFieldOrProperty(FIELD_LOCAL_DATE_TIME);

//                .has(() -> LocalDateTime.now().isAfter(savedResponse.get().getLocalDateTime()));
    }

}

/*
 _______________________________________________________________________________________________________________________
 TODO 1 README

 W tym kroku, skupimy się na testowaniu warstwy repozytorium.

 Mamy już jeden test, przejdźmy po todos 2-4, dla zapoznanaia się z klasą / metodą testową, następnie wybierz
 minimum 3 metody z repozytorium i napisz do nich testy

 Wskazówka - poczytaj w dokumentacji o @DirtiesContext

 Dla tego zadania nie ma gotowego rozwiązania - na tym etapie, nie powinno stanowić dla Ciebie problemu nie do roziwązania :)

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
