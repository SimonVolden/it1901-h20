package treningsapp.ui;


import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;

public class SavedWorkoutsSceneController implements Initializable {

    @FXML
    Button backButton;
    @FXML 
    Button useWorkoutButton;
    @FXML
    ScrollPane savedWorkoutsScrollPane;
    private boolean fromMainScene;
    private WorkoutProgram savedWorkouts;
    private boolean isViewingWorkout;
    private Workout currentWorkout;
    private WorkoutModelAccess access = new RemoteModelAccess();


    /**
     * A handle back method.
     * 
     * @throws IOException Exception when the scene cannot be loaded.
     */
    @FXML
    public void handleBack() throws IOException {
        if (this.isViewingWorkout) {
            this.isViewingWorkout = false;
            showWorkouts();
            useWorkoutButton.setVisible(false);
        } else {
            //fromMainScene keeps track of what scene you came from
            //so that when you click back, you go back to the same scene
            if (this.fromMainScene) {
                loadScene("MainScene.fxml");
            } else {
                loadScene("TrackWorkoutScene.fxml");
            }
        }
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

    private void showWorkouts() {
        int counter = 0;
        VBox root = new VBox();
        Iterator<Workout> savedWorkoutsIterator = this.savedWorkouts.getWorkouts().iterator();
        while (savedWorkoutsIterator.hasNext()) {
            Workout tempWorkout = savedWorkoutsIterator.next();
            
            if (tempWorkout.getSaved() == true) {
                counter += 1;
                Iterator<Exercise> exerciseIterator = tempWorkout.getExercises().iterator();
                StringBuilder exercisesStringBuilder = new StringBuilder();
                while (exerciseIterator.hasNext()) {
                    exercisesStringBuilder.append(" | ");
                    exercisesStringBuilder.append(exerciseIterator.next().getName());
                }
                
                TextField workoutTextField = new TextField(
                        counter + ": " + tempWorkout.getName()
                            + ": " + exercisesStringBuilder.toString() + " |");
                workoutTextField.setEditable(false);
                workoutTextField.getStyleClass().add("savedWorkout-textfield");

                EventHandler<MouseEvent> textClickedHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        if (e.getSource() instanceof TextField) {
                            TextField workoutText = (TextField) e.getSource();
                            int stringIndex = workoutText.getText().indexOf(":");
                            int workoutIndex = Integer.parseInt(workoutText
                                .getText(0, stringIndex));
                            showWorkout(workoutIndex);
                        }
                    }
                };
                workoutTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, textClickedHandler);
                root.getChildren().add(workoutTextField);
            }
        }

        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setMinWidth(240);
        // root.setPrefWidth(240);
        savedWorkoutsScrollPane.setContent(root);
        savedWorkoutsScrollPane.setPannable(true);
    }

    private void showWorkout(int workoutIndex) {
        this.isViewingWorkout = true;
        Workout w1 = savedWorkouts.getWorkouts().get(workoutIndex - 1);
        this.currentWorkout = w1;

        VBox root = new VBox();
        Iterator<Exercise> exerciseIterator = w1.getExercises().iterator();

        while (exerciseIterator.hasNext()) {
            Exercise tempExercise = exerciseIterator.next();
            Iterator<Sets> setIterator = tempExercise.getSets().iterator();
            int totalReps = 0;
            while (setIterator.hasNext()) {
                totalReps += setIterator.next().getRepetions();
            }

            TextField tempText = new TextField(tempExercise.getName());
            tempText.setAlignment(Pos.CENTER);
            tempText.setEditable(false);
            TextField tempText2 = new TextField(
                    "Total sets: " + tempExercise.getSets().size() + "  Repetitions: " + totalReps);
            tempText2.setEditable(false);

            root.getChildren().add(tempText);
            root.getChildren().add(tempText2);
        }

        root.setSpacing(10);
        root.setMinWidth(240);
        //root.setPrefWidth(240);
        root.setPadding(new Insets(10));
        savedWorkoutsScrollPane.setContent(root);
        savedWorkoutsScrollPane.setPannable(true);
        useWorkoutButton.setVisible(true);

    }

    /**
     * Method to handle track workout.
     */
    @FXML
    public void handleTrackWorkout() {
        if (this.currentWorkout.getExercises().size() > 0) {
            try {
                Stage mainStage = (Stage) this.useWorkoutButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutScene.fxml"));
                final Parent workoutSceneParent = loader.load();
                Scene workoutScene = new Scene(workoutSceneParent);

                WorkoutSceneController controller = loader.getController();
                Workout w1 = this.currentWorkout;
                w1.setSaved(false);
                controller.initData(w1, 0);

                mainStage.setScene(workoutScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void getSavedWorkouts() {
        this.savedWorkouts = getWorkoutProgram();

    }

    public void setWorkoutModelAccess(WorkoutModelAccess access) {
        this.access = access;
    }

    private WorkoutProgram getWorkoutProgram() {
        return this.access.getWorkoutProgram();
    }
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        this.access = FxApp.getWorkoutModelAccess();
        getSavedWorkouts();
        showWorkouts();
    }

    /**
     * Method for initializing data.
     * 
     * @param fromMainScene a boolean if its from mainscene.
     */
    public void initData(boolean fromMainScene) {
        this.fromMainScene = fromMainScene;
    }

}
