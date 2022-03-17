package treningsapp.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.json.TreningsappModule;

public class WorkoutHistorySceneController implements Initializable {

    @FXML
    Button backButton;
    @FXML
    Button saveWorkoutButton;
    @FXML
    ScrollPane workoutHistoryScrollPane;
    @FXML
    VBox scenevBox;
    @FXML
    TextField workoutNameText;
    @FXML
    Label workoutSavedLabel;
    WorkoutProgram workoutHistory = null;
    boolean isViewingWorkout = false;
    int workoutIndex;
    private WorkoutModelAccess access = new RemoteModelAccess();

    /**
     * A handle back method.
     */
    @FXML
    public void handleBack() {

        if (this.isViewingWorkout == false) {
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
        } else {
            showWorkoutHistory();
            this.isViewingWorkout = false;
            this.saveWorkoutButton.setVisible(false);
            this.workoutNameText.setVisible(false);
            workoutSavedLabel.setVisible(false);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.access = FxApp.getWorkoutModelAccess();
    }

    /**
     * Initialization method for this scene. (WorkoutHistoryScene)
     */
    public void initData() {        
        this.workoutHistory = getWorkoutProgram();

        if (this.workoutHistory.getWorkouts().size() == 0) {
            System.out.println("WorkoutHistory is empty, using example");
            WorkoutProgram w2 = new WorkoutProgram();
            Workout chest = new Workout("Chest");
            Exercise bench = new Exercise("Bench");
            bench.addSet(10, 10.0);
            chest.addExercise(bench);
            w2.addWorkouts(chest);
            this.workoutHistory = w2;
        }
        showWorkoutHistory();
        this.workoutNameText.setVisible(false);
    }

    private void showWorkoutHistory() {
        int counter = 0;
        VBox root = new VBox();
        Iterator<Workout> workoutHistoryIterator = this.workoutHistory.getWorkouts().iterator();
        while (workoutHistoryIterator.hasNext()) {
            Workout tempWorkout = workoutHistoryIterator.next();
            Iterator<Exercise> exerciseIterator = tempWorkout.getExercises().iterator();
            StringBuilder exercisesStringBuilder = new StringBuilder();
            while (exerciseIterator.hasNext()) {
                exercisesStringBuilder.append(" | ");
                exercisesStringBuilder.append(exerciseIterator.next().getName());
            }
            counter += 1;
            TextField workoutTextField = new TextField(
                    counter + ": " + tempWorkout.getName() + ": " + exercisesStringBuilder + " |");
            workoutTextField.setEditable(false);
            workoutTextField.getStyleClass().add("workout-textfield");

            EventHandler<MouseEvent> textClickedHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getSource() instanceof TextField) {
                        TextField workoutText = (TextField) e.getSource();
                        int stringIndex = workoutText.getText().indexOf(":");
                        int workoutIndex = Integer.parseInt(workoutText.getText(0, stringIndex));
                        setWorkoutIndex(workoutIndex);
                        showWorkout(workoutIndex);
                    }
                }
            };

            workoutTextField.addEventHandler(MouseEvent.MOUSE_CLICKED, textClickedHandler);
            root.getChildren().add(workoutTextField);

        }

        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.setPrefWidth(240);
        workoutHistoryScrollPane.setContent(root);
        workoutHistoryScrollPane.setPannable(true);

    }

    private void showWorkout(int workoutIndex) {
        this.isViewingWorkout = true;
        Workout w1 = workoutHistory.getWorkouts().get(workoutIndex - 1);

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

        saveWorkoutButton.setVisible(true);
        workoutNameText.setVisible(true);

        root.setSpacing(10);
        root.setPrefWidth(240);
        root.setPadding(new Insets(10));
        workoutHistoryScrollPane.setContent(root);
        workoutHistoryScrollPane.setPannable(true);
    }

    /**
     * A handleSaveWorkout method.
     * 
     * @throws JsonProcessingException an exception
     * @throws IOException             an exception for IO
     * @throws URISyntaxException      Constructs an instance
     *                                 from the given input 
     *                                 string and reason.
     */
    @FXML
    public void handleSaveWorkout() 
        throws JsonProcessingException, IOException, URISyntaxException {
        this.workoutHistory = getWorkoutProgram();
        for (int i = 0; i < this.workoutHistory.getWorkouts().size(); i++) {
            deleteWorkoutProgram();
        }

        /*
        if (getSavedWorkouts().getWorkouts().size() > 0) {
            savedWorkouts = getSavedWorkouts();
        }*/

        Workout saveWorkout;
        saveWorkout = this.workoutHistory.getWorkouts().get(this.workoutIndex - 1);
        saveWorkout.setName(workoutNameText.getText());

        saveWorkout.setSaved(true);
        Iterator<Workout> workoutHistoryIterator = this.workoutHistory.getWorkouts().iterator();
        while (workoutHistoryIterator.hasNext()) {
            putWorkoutProgram(workoutHistoryIterator.next());
        }

        saveToFile(this.workoutHistory);
        
        workoutNameText.setText("");
        workoutNameText.setVisible(false);
        saveWorkoutButton.setVisible(false);
        workoutSavedLabel.setVisible(true);

    }

    private void setWorkoutIndex(int workoutIndex) {
        this.workoutIndex = workoutIndex;
    }

    private void saveToFile(WorkoutProgram workoutHistory) 
            throws JsonProcessingException, IOException {
        TreningsappModule treningsmodule = new TreningsappModule();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(treningsmodule);

        Path fileName = Paths.get(System.getProperty("user.home"), "workoutHistoryFile.json");
        String fileNameString = fileName.toString();

        boolean fail = true;
        FileWriter fileWriter = new FileWriter(fileNameString, Charset.defaultCharset());
        try {

            fileWriter.write(mapper.writeValueAsString(workoutHistory));
            fileWriter.close();
            fail = false;
        } finally {
            if (fail) {
                try {
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void deleteWorkoutProgram() {
        this.access.deleteWorkoutProgram();
    }

    private WorkoutProgram getWorkoutProgram() {
        return this.access.getWorkoutProgram();
    }

    private void putWorkoutProgram(Workout w) {
        this.access.putWorkoutProgram(w);
    }




}