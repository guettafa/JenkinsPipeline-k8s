package edu.mv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.mv.models.RocketDTO;

public class Utils {

    public static String jsonRocket(RocketDTO rocketDTO) throws Exception {
        return new ObjectMapper().writeValueAsString(rocketDTO);
    }
}
