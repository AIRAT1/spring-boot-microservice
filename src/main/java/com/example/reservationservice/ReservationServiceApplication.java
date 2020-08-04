package com.example.reservationservice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@SpringBootApplication
public class ReservationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReservationServiceApplication.class, args);
    }
}

@Component
class DummyCLR implements CommandLineRunner {
    private final ReservationRepository reservationRepository;

    @Autowired
    public DummyCLR(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Stream.of("Spencer", "Mark", "Stefan", "Maria", "Arnaud", "Antonio")
                .forEach(n -> reservationRepository.save(new Reservation(n)));
        reservationRepository.findAll().forEach(System.out::println);
    }
}

@RestController
@RefreshScope
class MessageRestController {
    @Value("${message}")
    private String msg;

    @RequestMapping("/message")
    String read() {
        return this.msg;
    }
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    private String reservationName;

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }
}
