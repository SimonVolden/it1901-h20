package treningsapp.ui;

import java.io.IOException;
import java.util.Iterator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import treningsapp.core.Exercise;
import treningsapp.core.Workout;

public class AddExerciseSceneController {

    private Workout w1 = new Workout();
    private int currentExercise;

    @FXML Button backButton;
    @FXML Button startWorkoutButton;
    @FXML Button addExerciseButton;
    @FXML Button removeExerciseButton;
    @FXML TextField exerciseNameTextField;
    @FXML ScrollPane exerciseScrollPane;

    /**
     * a method to handle back.
     */
    @FXML
    public void handleBack() {
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackWorkoutScene.fxml"));
            final Parent trackWorkoutParent = loader.load();
            Scene trackWorkoutScene = new Scene(trackWorkoutParent);
            mainStage.setScene(trackWorkoutScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * a method to add exercise.
     */
    @FXML
    public void handleAddExercise() {
        
        if (!exerciseNameTextField.getText().isBlank()) {
            this.w1.addExercise(new Exercise(exerciseNameTextField.getText()));
            Iterator<Exercise> exerciseIterator = this.w1.getExercises().iterator();
            VBox root = new VBox();
            while (exerciseIterator.hasNext()) {
                root.getChildren().add(new TextField(exerciseIterator.next().getName()));
            }
            
            root.setSpacing(10);
            root.setPadding(new Insets(10));
            root.setPrefWidth(260);
            exerciseScrollPane.setContent(root);
            exerciseScrollPane.setPannable(true);

            exerciseNameTextField.setText("");
        }
        
    }

    /**
     * a method to remove exercises.
     */
    @FXML
    public void handleRemoveExercise() {
        this.w1.removeExercise();

        VBox root = new VBox();
        Iterator<Exercise> exerciseIterator = this.w1.getExercises().iterator();
        while (exerciseIterator.hasNext()) {
            root.getChildren().add(new TextField(exerciseIterator.next().getName()));
        }
        
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setPrefWidth(260);
        exerciseScrollPane.setContent(root);
        exerciseScrollPane.setPannable(true);
    }

    /**
     *a method to handle start workout.
     */
    @FXML
    public void handleStartWorkout() {
        if (this.w1.getExercises().size() > 0) {
            try {
                Stage mainStage = (Stage) this.startWorkoutButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutScene.fxml"));
                final Parent workoutSceneParent = loader.load();
                Scene workoutScene = new Scene(workoutSceneParent);

                WorkoutSceneController controller = loader.getController();
                controller.initData(this.w1, this.currentExercise);

                mainStage.setScene(workoutScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * a method to initialize data.
     * 
     * @param workout takes a workout object as input
     * @param currentExercise takes amount of currentExercies as input
     */
    public void initData(Workout workout, int currentExercise) {
        this.w1 = workout;
        this.currentExercise = currentExercise;

        VBox root = new VBox();
        Iterator<Exercise> exerciseIterator = this.w1.getExercises().iterator();
        while (exerciseIterator.hasNext()) {
            TextField tempText = new TextField(exerciseIterator.next().getName());
            tempText.setEditable(false);
            root.getChildren().add(tempText);
        }
        
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        exerciseScrollPane.setContent(root);
        exerciseScrollPane.setPannable(true);
    }
}