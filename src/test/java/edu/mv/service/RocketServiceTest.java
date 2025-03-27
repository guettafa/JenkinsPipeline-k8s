package edu.mv.service;

import edu.mv.db.models.Rocket;
import edu.mv.mapping.RocketMapper;
import edu.mv.models.RocketDTO;
import edu.mv.persistence.PersistenceService;
import edu.mv.persistence.RocketNotFoundException;

import edu.mv.repository.RocketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RocketServiceTest {

    @InjectMocks
    private PersistenceService persistenceService;

    @Mock
    private RocketRepository rocketRepository;

    private Rocket rocket;

    /**
     * Executed before each test
     * Instance of the Rocket class that will
     * be used for some of my tests
     */
    @BeforeEach
    void setUp() {
        rocket = new Rocket(
                1,"MyRocket", "Flying"
        );
    }

    /**
     * Test Saving a rocket
     *
     * @result The rocket has been saved
     *         and the save() method has only
     *         been called once
     */
    @Test
    void whenSavingRocket() {
        when(rocketRepository.save(any(Rocket.class))).thenReturn(rocket);

        persistenceService.save(
                RocketMapper.INSTANCE.RocketToRocketDTO(rocket)
        );
        verify(rocketRepository, times(1)).save(any(Rocket.class));
    }

    /**
     * Test finding a rocket by its id
     *
     * @result The desired rocket associated
     *         with the id is returned and the
     *         findById() method has only been called once
     */
    @Test
    void whenFindingRocketWithId() throws RocketNotFoundException {
        when(rocketRepository.findById(anyInt()))
                .thenReturn(Optional.of(rocket));

        RocketDTO savedRocket = persistenceService.retrieve(1);

        verify(rocketRepository, times(1)).findById(anyInt());

        assertNotNull(savedRocket);
        assertEquals(rocket.getName(), savedRocket.getName());
        assertEquals(rocket.getSorte(), savedRocket.getType());
    }

    /**
     * Test finding a rocket with id that doesn't exist
     *
     * @result throw the RocketNotFoundException
     *         because no rocket with id 1
     *         was found
     */
    @Test
    void whenFindingRocketWithId_throwException() {
        assertThrows(
                RocketNotFoundException.class,
                () -> persistenceService.retrieve(1)
        );
    }
}