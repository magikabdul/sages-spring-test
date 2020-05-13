package workshop.springb.testing.service;

import org.springframework.stereotype.Service;
import workshop.springb.testing.model.Response;
import workshop.springb.testing.repository.ResponseRepository;

import java.time.LocalDateTime;

@Service
public class GreetService {

    private final ResponseRepository repository;

    public GreetService(ResponseRepository repository) {
        this.repository = repository;
    }

    public Response greet(String name, boolean isFormal) {
        String greet = isFormal ? "Hello" : "Hi";
        var response = new Response(String.format("%s, %s!", greet, name), LocalDateTime.now());
        return repository.save(response);
    }

}