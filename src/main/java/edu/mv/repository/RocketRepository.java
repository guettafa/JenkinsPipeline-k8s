package edu.mv.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import edu.mv.db.models.Rocket;

import java.util.List;

@Service
public interface RocketRepository extends CrudRepository<Rocket, Integer> {

    List<Rocket> findAll();
}
