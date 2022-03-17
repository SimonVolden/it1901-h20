package treningsapp.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
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
import treningsapp.core.Workout;

public class FindWorkoutSceneController implements Initializable {

    @FXML
    Button backButton;
    @FXML
    TextField searchField;
    @FXML
    ScrollPane workoutScrollPane;
    @FXML
    Button useWorkoutButton;

    private ArrayList<Workout> workoutDatabase = new ArrayList<Workout>();
    private ArrayList<Workout> filteredWorkoutDatabase = new ArrayList<>();
    private boolean isViewingWorkout;
    private Workout currentWorkout;
    //private WorkoutModelAccess access = new RemoteModelAccess();
    

    final Comparator<Workout> workoutNameComparator = new Comparator<Workout>() {
        @Override
        public int compare(Workout o1, Workout o2) {
            return o1.getName().toUpperCase().compareTo(o2.getName().toUpperCase());
        }
    };
    

    /**
     * Constructor for creating pre-exisiting exercises.
     * 
     */
    public FindWorkoutSceneController() {
        Workout chest = new Workout("Chest");
        chest.addExercise(new Exercise("Barbell Bench Press"));
        chest.addExercise(new Exercise("Incline Dumbbell Bench Press"));
        chest.addExercise(new Exercise("Flyes"));
        chest.addExercise(new Exercise("Dumbbell Pullover"));

        Workout legs = new Workout("Legs");
        legs.addExercise(new Exercise("Barbell Squat"));
        legs.addExercise(new Exercise("Deadlift"));
        legs.addExercise(new Exercise("Lunges"));
        legs.addExercise(new Exercise("Leg Extensions"));

        Workout back = new Workout("Back");
        back.addExercise(new Exercise("Barbell Rows"));
        back.addExercise(new Exercise("One-handed Dumbbell Row"));
        back.addExercise(new Exercise("Reverse Flyes"));
        back.addExercise(new Exercise("Seated Cable Rows"));

        Workout abs = new Workout("Abs");
        abs.addExercise(new Exercise("Lying Leg Raise"));
        abs.addExercise(new Exercise("Crunches"));
        abs.addExercise(new Exercise("Hanging Leg Raise"));
        abs.addExercise(new Exercise("Russian Twist"));

        this.workoutDatabase.add(chest);
        this.workoutDatabase.add(legs);
        this.workoutDatabase.add(back);
        this.workoutDatabase.add(abs);
    }

    /**
     * Method to handle back to mainScene view.
     * 
     */
    @FXML
    public void handleBackButton() {
        if (isViewingWorkout == true) {
            this.useWorkoutButton.setVisible(false);
            showWorkouts();
            isViewingWorkout = false;
        } else {
            try {
                Stage mainStage = (Stage) this.backButton.getScene().getWindow();
                FXMLLoader mainPaneLoader = new FXMLLoader(getClass()
                    .getResource("MainScene.fxml"));
                Parent mainPane;
                mainPane = mainPaneLoader.load();
                Scene mainScene = new Scene(mainPane);
                mainStage.setScene(mainScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }    
    }

    /**
     * Method for showing all current workouts.
     * 
     */
    @FXML
    public void showWorkouts() {
        this.filteredWorkoutDatabase.clear();
        String filterString = this.searchField.getText().toLowerCase();

        int counter = 0;
        VBox root = new VBox();
        Iterator<Workout> workoutDatabaseIterator = this.workoutDatabase.iterator();
        while (workoutDatabaseIterator.hasNext()) {
            Workout tempWorkout = workoutDatabaseIterator.next();
            String tempNameLowerCase = tempWorkout.getName().toLowerCase();
            if (tempNameLowerCase.contains(filterString) || filterString.isEmpty()) {
                this.filteredWorkoutDatabase.add(tempWorkout);
                counter += 1;
                TextField workoutTextField = new TextField(
                        counter + ": " + tempWorkout.getName());
                workoutTextField.setEditable(false);
                workoutTextField.getStyleClass().add("preWorkout-textfield");
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
        workoutScrollPane.setContent(root);
        workoutScrollPane.setPannable(true);
        
    }

    private void showWorkout(int workoutIndex) {
        this.isViewingWorkout = true;
        this.useWorkoutButton.setVisible(true);
        this.currentWorkout = filteredWorkoutDatabase.get(workoutIndex - 1);
        VBox root = new VBox();
        Iterator<Exercise> exerciseIterator = this.currentWorkout.getExercises().iterator();

        while (exerciseIterator.hasNext()) {
            Exercise tempExercise = exerciseIterator.next();
            TextField tempText = new TextField(tempExercise.getName());
            tempText.setAlignment(Pos.CENTER);
            tempText.setEditable(false);
            root.getChildren().add(tempText);
        }
        root.setSpacing(10);
        root.setMinWidth(240);
        //root.setPrefWidth(240);
        root.setPadding(new Insets(10));
        workoutScrollPane.setContent(root);
        workoutScrollPane.setPannable(true);
    }

    /**
     * Method to use the selected workout in workoutScene.
     * 
     */
    @FXML
    public void useWorkout() {
        if (this.currentWorkout.getExercises().size() > 0) {
            try {
                Stage mainStage = (Stage) this.useWorkoutButton.getScene().getWindow();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutScene.fxml"));
                final Parent workoutSceneParent = loader.load();
                Scene workoutScene = new Scene(workoutSceneParent);

                WorkoutSceneController controller = loader.getController();
                controller.initData(this.currentWorkout, 0);

                mainStage.setScene(workoutScene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //this.access = FxApp.getWorkoutModelAccess();
        this.isViewingWorkout = false;
        workoutDatabase.sort(workoutNameComparator);
        showWorkouts();
    }

   


}