package workshop.springb.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workshop.springb.testing.model.Response;

public interface ResponseRepository extends JpaRepository<Response, Long> {
}
