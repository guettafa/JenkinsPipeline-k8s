package edu.mv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.mv.models.RocketDTO;
import edu.mv.service.RocketService;

@RestController
@RequestMapping("/rocket")
public class RocketController {

    private final RocketService rocketService;

    public RocketController(RocketService rocketService) {
        this.rocketService = rocketService;
    }

    @GetMapping("/{rocketId}")
    public ResponseEntity<?> getRocket(@PathVariable(value = "rocketId") final String id) {
        try {
            return new ResponseEntity<>(
                    rocketService.getRocket(Integer.parseInt(id)),
                    HttpStatus.FOUND
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @PostMapping("/")
    public RocketDTO saveRocket(@RequestBody RocketDTO rocket) {
        return rocketService.putRocket(rocket);
    }
}
