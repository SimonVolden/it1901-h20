package treningsapp.ui;


import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.lang.RuntimeException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.json.TreningsappModule;

public class RemoteModelAccess implements WorkoutModelAccess {

    private final URI endpointUri;

    public RemoteModelAccess(URI uri) {
        this.endpointUri = uri;
    }

    /**
     * Making an URI.
     */
    public RemoteModelAccess() {
        try {
            this.endpointUri = new URI("http://localhost:8999/workoutProgram/");
        } catch (URISyntaxException e) {
            throw new RuntimeException("This should never happen with a hardcoded URI", e);
        }
        
    }

    /**
     * Method for deleting a WorkoutProgram.
     */
    public void deleteWorkoutProgram() {
        HttpRequest request = HttpRequest.newBuilder(this.endpointUri)
                .header("Accept", "application/json")
                .DELETE()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            System.out.println(responseString);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    
    /** 
     * REST method made to GET a workoutprogram from server.
     */
    public WorkoutProgram getWorkoutProgram() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new TreningsappModule());

        HttpRequest request = HttpRequest.newBuilder(this.endpointUri)
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
                    HttpResponse.BodyHandlers.ofString());
            final String responseString = response.body();
            WorkoutProgram w1 = mapper.readValue(responseString, WorkoutProgram.class);
            return w1;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * REST method for adding (or putting) a new WorkoutProgram to the server.
     */
    public void putWorkoutProgram(Workout workout) {
        ObjectMapper mapper = new ObjectMapper().registerModule(new TreningsappModule());

        try {
            String json = mapper.writeValueAsString(workout);
            HttpRequest request2 = HttpRequest.newBuilder(endpointUri)
                    .header("Accept", "application/json")
                    .header("Content-Type", "application/json")
                    .PUT(BodyPublishers.ofString(json)).build();
            final HttpResponse<String> response = HttpClient.newBuilder().build().send(request2,
                    HttpResponse.BodyHandlers.ofString());
            String responseString = response.body();
            System.out.println(responseString);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}