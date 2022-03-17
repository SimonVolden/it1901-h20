package treningsapp.restserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.restapi.WorkoutProgramService;

public class ServerTest extends JerseyTest {

    protected boolean shouldLog() {
        return false;
    }

    @Override
    protected ResourceConfig configure() {
        final TreningConfig config = new TreningConfig();
        if (shouldLog()) {
            enable(TestProperties.LOG_TRAFFIC);
            enable(TestProperties.DUMP_ENTITY);
            config.property(LoggingFeature.LOGGING_FEATURE_LOGGER_LEVEL_SERVER, "WARNING");
        }
        return config;
    }

    @Override
    protected TestContainerFactory getTestContainerFactory() throws TestContainerException {
        return new GrizzlyTestContainerFactory();
    }

    private ObjectMapper objectMapper;

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        objectMapper = new TreningModuleObjectMapperProvider().getContext(getClass());
    }

    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testPutAndGet() {
        Workout workout = new Workout("Today");
        workout.setSaved(false);
        //testing if workout is put
		Response getResponse = target(WorkoutProgramService.WORKOUT_PROGRAM_SERVICE_PATH)
                .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").put(Entity.json(workout));
        //204 = serveren fikk tak i info, men reuturner ikke noe. 
                assertEquals(204, getResponse.getStatus());
         
        getResponse = target(WorkoutProgramService.WORKOUT_PROGRAM_SERVICE_PATH)
                .request(MediaType.APPLICATION_JSON + ";" + MediaType.CHARSET_PARAMETER + "=UTF-8").get();
        assertEquals(200, getResponse.getStatus());
        //Testing get method above
        String response = getResponse.readEntity(String.class);
        try {
            WorkoutProgram w1 = objectMapper.readValue(response, WorkoutProgram.class);
            //navn på program,
            assertEquals("name", w1.getName());
            //navn på første workout, skal ikke være saved
            assertEquals("Today",w1.getWorkouts().get(w1.getWorkouts().size()-1).getName());
            assertEquals(false, w1.getWorkouts().get(w1.getWorkouts().size()-1).getSaved());
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }

  }
    
}