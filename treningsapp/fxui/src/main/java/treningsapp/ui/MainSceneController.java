package treningsapp.ui;


import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import treningsapp.core.Exercise;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public class MainSceneController implements Initializable {

    private WorkoutProgram workoutHistory = null;
    private WorkoutModelAccess access = new RemoteModelAccess();

    @FXML
    Button trackButton;
    @FXML
    Button workoutHistoryButton;
    @FXML
    Button savedWorkoutsButton;
    @FXML
    Button findWorkoutButton;

    /**
     * method to change scene to trackWorkoutScene.
     */
    @FXML
    public void handleTrackWorkout() {
        try {
            Stage mainStage = (Stage) this.workoutHistoryButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackWorkoutScene.fxml"));
            final Parent trackWorkoutParent = loader.load();
            Scene trackWorkoutScene = new Scene(trackWorkoutParent);

            mainStage.setScene(trackWorkoutScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method to go to workoutHistoryScene.
     */
    @FXML
    public void handleWorkoutHistory() {
        try {
            Stage mainStage = (Stage) this.workoutHistoryButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutHistoryScene.fxml"));
            final Parent workoutHistoryParent = loader.load();
            Scene workoutHistoryScene = new Scene(workoutHistoryParent);

            WorkoutHistorySceneController controller = loader.getController();
            controller.initData();

            mainStage.setScene(workoutHistoryScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to go to savedWorkoutScene.
     */
    @FXML
    public void handleSavedWorkouts() {
        boolean fromMainScene = true;
        try {
            Stage mainStage = (Stage) this.savedWorkoutsButton.getScene().getWindow();

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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.access = FxApp.getWorkoutModelAccess();
    }

    /**
     * A method to get init data.
     * 
     */
    public void initData() {
        this.workoutHistory = getWorkoutProgram();
        if (this.workoutHistory.getWorkouts().size() == 0) {
            Workout chest = new Workout("Chest");
            Exercise bench = new Exercise("Bench");
            bench.addSet(10, 10.0);
            chest.addExercise(bench);
            this.workoutHistory.addWorkouts(chest);
        }

    }

    /**
     * This method is function is to move to workoutHistoryScene view.
     * 
     * @throws URISyntaxException URiSyntaxException
     */
    @FXML
    public void handleFindWorkouts() throws URISyntaxException {
        try {
            Stage mainStage = (Stage) this.workoutHistoryButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FindWorkoutScene.fxml"));
            final Parent workoutHistoryParent = loader.load();
            Scene workoutHistoryScene = new Scene(workoutHistoryParent);

            mainStage.setScene(workoutHistoryScene);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private WorkoutProgram getWorkoutProgram() {
        return this.access.getWorkoutProgram();
    }
}
