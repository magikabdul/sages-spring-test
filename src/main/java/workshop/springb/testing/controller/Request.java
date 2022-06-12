package workshop.springb.testing.controller;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Request {

    private String name;

    @NotNull
    private boolean isFormal;
}
