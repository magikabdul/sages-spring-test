package workshop.springb.testing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarterApplication {

    public static void main(String[] args) {

        SpringApplication.run(StarterApplication.class, args);

    }

}

/*
 _______________________________________________________________________________________________________________________
 TODO 1 README

 W tym module mamy ćwiczenia na pisanie testów w  aplikacji Spring Boot

 W pierwszym kroku nic nie implementujemy - zapoznaj się z aplikacją.


 client  <-> |controller <-> service <-> repository | <-> database

 Klient wywołuje endpoint /greet i w zależności od przekazanych parametrów otrzymuje odpowiedni obiekt Response w formacie JSON.
 Obiekt Response jest tworzony w warstwie serwisu, tam też wywołana jest metoda z repozytorium, zapisująca go w bazie.

 W kolejnych krokach (osobne branch'e) zajmiemy się testowaniem naszej aplikacji.
 Zaczniemy od testu akceptacyjnego (testujemy endpoint z pełnym kontekstem Spring'a), następnie będziemy testowali
 wyizolowane warstwy (kontroler, serwis, repository)
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