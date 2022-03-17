package treningsapp.ui;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.stream.Stream;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import treningsapp.core.Exercise;
import treningsapp.core.Sets;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.json.TreningsappModule;

public class WorkoutDoneSceneController implements Initializable {

    private Workout w1;
    private int currentExercise;
    private WorkoutModelAccess access = new RemoteModelAccess();

    @FXML
    Button backButton;

    @FXML
    Button handleBackToMenuButton;

    @FXML
    ScrollPane exerciseScrollPane;
    private WorkoutProgram workoutHistory;

    @FXML
    private void handleBack() {

        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutScene.fxml"));
            final Parent WorkoutSceneParent = loader.load();
            Scene workoutScene = new Scene(WorkoutSceneParent);

            WorkoutSceneController controller = loader.getController();
            controller.initData(this.w1, this.currentExercise);

            mainStage.setScene(workoutScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackToMenu() throws IOException {
        putWorkoutProgram(this.w1);
        handleBackToMenuFile();
        try {
            Stage mainStage = (Stage) this.backButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            final Parent MainSceneParent = loader.load();
            Scene mainScene = new Scene(MainSceneParent);
            mainStage.setScene(mainScene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private WorkoutProgram importWorkoutHistory() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TreningsappModule());
        // Path fileName =
        // Path.of("treningsapp/src/main/resources/savefiles/saveFile.json");
        Path fileName = null;
        fileName = Paths.get(System.getProperty("user.home"), "workoutHistoryFile.json");
        Stream<String> saveFiles;
        try {
            saveFiles = Files.lines(fileName);
            String saveFile = saveFiles.iterator().next();
            saveFiles.close();
            return mapper.readValue(saveFile, WorkoutProgram.class);
        } catch (NoSuchFileException e) {
            return new WorkoutProgram(); // returner nytt program
        } catch (JsonProcessingException | NoSuchElementException e) {
            e.printStackTrace();
            return new WorkoutProgram(); // workout program er ødelagt, lag en ny
        }
    }

    private void handleBackToMenuFile() throws IOException {
        this.workoutHistory = importWorkoutHistory();
        this.workoutHistory.addWorkouts(this.w1);

        TreningsappModule treningsmodule = new TreningsappModule();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(treningsmodule);

        Path fileName = Paths.get(System.getProperty("user.home"), "workoutHistoryFile.json");
        String fileNameString = fileName.toString();

        boolean fail = true;
        FileWriter fileWriter = new FileWriter(fileNameString, Charset.defaultCharset());
        try {

            fileWriter.write(mapper.writeValueAsString(this.workoutHistory));
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

    /**
     * method to initialize data.
     * 
     * @param workout         workout object as input
     * @param currentExercise amount of current exercises
     */
    public void initData(Workout workout, int currentExercise) {
        this.w1 = workout;
        this.currentExercise = currentExercise;

        if (currentExercise >= w1.getExercises().size()) {
            currentExercise = 0;
        }

        VBox root = new VBox();

        Iterator<Exercise> exerciseIterator = this.w1.getExercises().iterator();

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
        root.setPrefWidth(240);
        root.setPadding(new Insets(10));
        exerciseScrollPane.setContent(root);
        exerciseScrollPane.setPannable(true);
    }

    public void setWorkoutModelAccess(WorkoutModelAccess access) {
        this.access = access;
    }

    private void putWorkoutProgram(Workout w) {
        this.access.putWorkoutProgram(w);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.access = FxApp.getWorkoutModelAccess();

    }

}