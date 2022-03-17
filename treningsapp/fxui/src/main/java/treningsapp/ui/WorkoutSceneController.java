package treningsapp.ui;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import treningsapp.core.Sets;
import treningsapp.core.Workout;


public class WorkoutSceneController implements Initializable {
    private Workout w1;
    private int currentExercise = 0;
    private WorkoutModelAccess access = new RemoteModelAccess();


    @FXML
    private Button nextExercise;
    @FXML
    private Button lastExercise;
    @FXML
    private Button backButton;
    @FXML
    private Button removeSetButton;
    @FXML
    private Button addSetButton;
    @FXML
    private Button workoutDoneButton;
    @FXML
    private TextField workoutExerciseName;
    @FXML
    private Spinner<Integer> repSpinner;
    @FXML
    private Spinner<Double> weightSpinner;
    @FXML
    private ScrollPane exerciseScrollPane;

    /**
     * a method to handle back.
     */
    @FXML
    public void handleBack() {
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddExerciseScene.fxml"));
            final Parent AddExerciseParent = loader.load();
            Scene addExerciseScene = new Scene(AddExerciseParent);

            AddExerciseSceneController controller = loader.getController();
            controller.initData(this.w1, currentExercise);

            mainStage.setScene(addExerciseScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to add sets.
     */
    @FXML
    public void addSet() {
        this.w1.getExercises().get(this.currentExercise).addSet(repSpinner.getValue(),
                weightSpinner.getValue());
        showCurrentWorkout();
    }

    /**
     * a method to remove the last set.
     */

    @FXML
    public void removeSet() {
        this.w1.getExercises().get(this.currentExercise).removeSet();
        showCurrentWorkout();
    }


    /**
     * a method to show the next exercise.
     */
    @FXML
    public void handleNextExercise() {

        if (this.currentExercise != w1.getExercises().size() - 1) {
            this.currentExercise += 1;
            workoutExerciseName.setText(w1.getExercises().get(this.currentExercise).getName());
            showCurrentWorkout();
        }
    }

    /**
     * mehtod to show the last exercise.
     */
    @FXML
    public void handleLastExercise() {

        if (this.currentExercise > 0) {
            this.currentExercise -= 1;
            workoutExerciseName.setText(w1.getExercises().get(this.currentExercise).getName());
            showCurrentWorkout();
        }
    }

    /**
     * a mehtod to handle workouts.
     */
    @FXML
    public void handleWorkoutDone() {

        try {
            Stage mainStage = (Stage) this.workoutDoneButton.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutDoneScene.fxml"));
            final Parent workoutDoneSceneParent = loader.load();
            Scene workoutDoneScene = new Scene(workoutDoneSceneParent);

            WorkoutDoneSceneController controller = loader.getController();
            controller.setWorkoutModelAccess(this.access);
            controller.initData(this.w1, this.currentExercise);

            mainStage.setScene(workoutDoneScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * mathod to initialize data.
     * 
     * @param workout         takes an workoutobject as input
     * @param currentExercise tales amount of current exercises as input
     */
    public void initData(Workout workout, int currentExercise) {
        this.w1 = workout;
        this.currentExercise = currentExercise;
        if (this.currentExercise >= w1.getExercises().size()) {
            this.currentExercise = 0;
        }
        workoutExerciseName.setText(w1.getExercises().get(this.currentExercise).getName());
        showCurrentWorkout();

    }

    private void showCurrentWorkout() {
        VBox root = new VBox();
        Iterator<Sets> setIterator =
                this.w1.getExercises().get(this.currentExercise).getSets().iterator();
        while (setIterator.hasNext()) {

            Sets tempSet = setIterator.next();
            TextField repetitionsInt = new TextField("" + tempSet.getRepetions());
            repetitionsInt.setMinWidth(40);
            repetitionsInt.setEditable(false);

            TextField weigthDouble = new TextField("" + tempSet.getWeight());
            weigthDouble.setMinWidth(40);
            weigthDouble.setEditable(false);

            TextField repetitions = new TextField("Reps: ");
            repetitions.setMinWidth(60);
            repetitions.setEditable(false);

            TextField weight = new TextField("Weight: ");
            weight.setMinWidth(70);
            weight.setEditable(false);
            
            HBox innerRoot = new HBox();
            innerRoot.getChildren().addAll(repetitions, repetitionsInt, weight, weigthDouble);
            root.getChildren().add(innerRoot);
        }

        root.setSpacing(10);
        root.setPrefWidth(260);
        root.setPadding(new Insets(10));
        exerciseScrollPane.setContent(root);
        exerciseScrollPane.setPannable(true);
        repSpinner.getValueFactory().setValue(0);
        weightSpinner.getValueFactory().setValue(0.0);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.access = FxApp.getWorkoutModelAccess();
        SpinnerValueFactory<Integer> setRepsFactory = 
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10000, 0, 1);
        repSpinner.setValueFactory(setRepsFactory);
        repSpinner.setEditable(true);

        SpinnerValueFactory<Double> setWeightFactory = 
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 10000, 0, 0.5);
        weightSpinner.setValueFactory(setWeightFactory);
        weightSpinner.setEditable(true);

    }
}