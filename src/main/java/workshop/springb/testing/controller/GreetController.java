package workshop.springb.testing.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import workshop.springb.testing.model.Response;
import workshop.springb.testing.service.GreetService;

import javax.validation.Valid;
import java.util.Objects;

@RestController
public class GreetController {

    private final GreetService greetService;

    public GreetController(GreetService greetService) {
        this.greetService = greetService;
    }

    @GetMapping("/greet")
    public Response greet(@RequestParam(required = false, defaultValue = "World") String name,
                          @RequestParam boolean isFormal) {
        return greetService.greet(name, isFormal);
    }

    @PostMapping("greet")
    public Response greet(@Valid @RequestBody Request request) {
        var name = (request.getName() == null || Objects.equals(request.getName(), "")) ? "World" : request.getName();
        return greetService.greet(name, request.isFormal());
    }
}
