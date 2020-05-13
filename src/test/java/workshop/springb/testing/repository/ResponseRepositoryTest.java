package workshop.springb.testing.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import workshop.springb.testing.model.Response;

import java.time.LocalDateTime;

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

    /*
        TODO 4 README
        Metoda testowa -  zapisujemy Response w bazie przy użyciu save, sprawdzamy zapisany obiekt
     */
    @Test
    @DisplayName("Should save one Response with id 1L")
    public void shouldSaveAresponseWithId1() {
        assertTrue(responseRepository.findAll().isEmpty());
        Response savedResponse = responseRepository.save(new Response("Hello, World!", LocalDateTime.now()));
        assertAll(
                () -> assertEquals(1, responseRepository.findAll().size()),
                () -> assertEquals(savedResponse.getId(), responseRepository.findById(1L).get().getId()),
                () -> assertEquals(savedResponse.getGreeting(), responseRepository.findById(1L).get().getGreeting())
        );
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