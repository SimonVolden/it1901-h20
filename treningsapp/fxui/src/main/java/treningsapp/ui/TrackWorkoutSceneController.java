package treningsapp.ui;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TrackWorkoutSceneController {

    @FXML
    Button trackAsYouGoButton;
    @FXML
    Button useSavedWorkoutButton;
    @FXML
    Button backButton;


    /**
     * method to go back to last scene.
     * 
     * @throws IOException Exeption is thrown when the scene cannot be loaded.
     */
    @FXML
    public void handleBackButton() throws IOException {
        loadScene("MainScene.fxml");
    }

    /**
     * Method to next scene where you can track a workout.
     * 
     * @throws IOException Exception is thrown when the scene cannot be loaded.
     */

    @FXML
    public void handleTrackAsYouGo() throws IOException {
        loadScene("AddExerciseScene.fxml");
    }

    private void loadScene(String scene) throws IOException {
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(scene));
            final Parent trackWorkoutParent = loader.load();
            Scene trackWorkoutScene = new Scene(trackWorkoutParent);
            mainStage.setScene(trackWorkoutScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A handle to use saved workouts.
     */
    @FXML
    public void handleUseSavedWorkouts() {
        boolean fromMainScene = false;
        try {
            Stage mainStage = (Stage) this.trackAsYouGoButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedWorkoutsScene.fxml"));
            final Parent workoutSceneParent = loader.load();
            Scene workoutScene = new Scene(workoutSceneParent);

            SavedWorkoutsSceneController controller = loader.getController();
            controller.initData(fromMainScene);

            mainStage.setScene(workoutScene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    
}