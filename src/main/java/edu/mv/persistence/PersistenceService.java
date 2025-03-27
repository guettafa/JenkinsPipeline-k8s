package edu.mv.persistence;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mv.db.models.Rocket;
import edu.mv.mapping.RocketMapper;
import edu.mv.models.RocketDTO;
import edu.mv.repository.RocketRepository;

@Service
public class PersistenceService {

    @Autowired
    private RocketRepository rocketRepository;

    public RocketDTO retrieve(int id) throws RocketNotFoundException {
        Optional<Rocket> rocketOptional = rocketRepository.findById(id);
        if (rocketOptional.isPresent()) {
            return convertToRocketDTO(rocketOptional.get());
        }
        throw new RocketNotFoundException(id);
    }

    public RocketDTO save(RocketDTO Rocket) {
        rocketRepository.save(convertToRocketPersistence(Rocket));
        return Rocket;
    }

    private Rocket convertToRocketPersistence(RocketDTO RocketDTO) {
        return RocketMapper.INSTANCE.RocketDTOToRocket(RocketDTO);
    }

    private RocketDTO convertToRocketDTO(Rocket rocket) {
        return RocketMapper.INSTANCE.RocketToRocketDTO(rocket);
    }

}
