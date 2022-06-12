package workshop.springb.testing.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import workshop.springb.testing.model.Response;
import workshop.springb.testing.repository.ResponseRepository;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

/*
    TODO 2 README
    Poniższa adnotacja oznacza test JUnit 5 (bez infrastruktury Spring)
 */
@ExtendWith(MockitoExtension.class)
class GreetServiceTest {

    /*
        TODO 3 README
        Testujemy warstwę serwisu - metoda  GreetService#greet
        Ponieważ metoda GreetService#greet korzysta z ResponseRepository:

        1. mock'ujemy ResponseRepository
            @Mock
            private ResponseRepository responseRepository;
        2. tworzymy obiekt, którego API chcemy testować (GreetService), z zależnością (ResponseRepository)
             @InjectMocks
             private GreetService greetService;
     */
    @Mock
    private ResponseRepository responseRepository;
    @InjectMocks
    private GreetService greetService;

    @Test
    @DisplayName("greet(\"X\", true) should return a Response with HELLO, X! greeting")
    public void greetXTrue_shouldReturn_responseWithHELLOX() throws Exception {
        String greeting = "Hello, X!";
        Response stubbedResponse = new Response(greeting, LocalDateTime.now());
        /*
            TODO 4 README
            Ustawiamy zachowanie metody responseRepository.save
            Jeśli metoda responseRepository.save otrzyma Response z greeting "Hello, X!", niech zwróci obiekt stubbedResponse.
         */
        when(responseRepository.save(argThat(response -> greeting.equals(response.getGreeting())))).thenReturn(stubbedResponse);

        /*
            TODO 5 README
            Ttestujemy API serwisu (czy metoda GreetService#greet zmieni greeting na pisane wielkimi literami)
            -metoda GreetService#greet wywoła responseRepository.save,
            -responseRepository.save zachowa się w sposób zdefiniowany w TODO4
         */
        Response returnedFromService = greetService.greet("X", true);
        assertEquals("HELLO, X!", returnedFromService.getGreeting());
    }

    @Test
    @DisplayName("greet(\"X\", false) should return a Response with HI, X! greeting")
    public void greetXFalse_shouldReturn_responseWithHIX() {
        String greeting = "Hi, X!";
        Response stubbedResponse = new Response(greeting, LocalDateTime.now());

        when(responseRepository.save(argThat(response -> greeting.equals(response.getGreeting()))))
                .thenReturn(stubbedResponse);

        Response returnedFromService = greetService.greet("X", false);
        assertEquals("HI, X!", returnedFromService.getGreeting());
    }

}

/*
 _______________________________________________________________________________________________________________________
 TODO 1 Przeczytaj TODO 2-5, a następnie utwórz analogicznie test:
 greetXFalse_shouldReturn_responseWithHIX
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
