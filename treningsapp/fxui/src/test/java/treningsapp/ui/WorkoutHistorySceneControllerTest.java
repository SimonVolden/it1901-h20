package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkoutHistorySceneControllerTest extends ApplicationTest {

    private AddExerciseSceneController controller;
    private Map<String, Object> namespace;
    private List<TextField> box_textField;
    private TextField tf;

    //går ikke an å gå på workoutHistoryScene.fxml 

    @Override
    public void start(final Stage stage) throws Exception {
        FxApp.setWorkoutModelAccess(new DirectModelAccess());

        final FXMLLoader loader = new FXMLLoader(getClass().getResource("AddExerciseScene.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        namespace = loader.getNamespace();
        stage.show();
    }

    @BeforeEach
    public void start() {
        tf = (TextField) namespace.get("exerciseNameTextField");
        tf.setText("TreningA");
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
    }

    private List<TextField> getNewVboxList() {
        ScrollPane sp = (ScrollPane) namespace.get("exerciseScrollPane");
        VBox box = new VBox();
        if (sp.getContent() instanceof VBox) {
            box = (VBox) sp.getContent();
        }
        box_textField = new ArrayList<>();
        for (Node n: box.getChildren()) {
            if (n instanceof TextField) {
                box_textField.add((TextField) n);
            }
        }
        return box_textField;
    }
    
      @Test
    public void testControllerNotNull() {
        assertNotNull(this.controller);
    }
    
    @Test
    public void showWorkoutHistoryTest() {
        //først lage en workout
        clickOn("#startWorkoutButton");
        clickOn("#addSetButton");
        clickOn("#workoutDoneButton");
        clickOn("#handleBackToMenuButton");
        clickOn("#workoutHistoryButton");



        ArrayList<TextField> list_text = new ArrayList<>();
        for (Node node: lookup(".workout-textfield").queryAll()) {
            if (node instanceof TextField) {
                list_text.add((TextField) node);
                }  
            }
        
        clickOn(list_text.get(0));
        clickOn("#backButton");
        list_text.clear();

        for (Node node: lookup(".workout-textfield").queryAll()) {
            if (node instanceof TextField) {
                list_text.add((TextField) node);
            }
        }
        clickOn(list_text.get(0));
        clickOn("#workoutNameText").write("Armer");
        clickOn("#saveWorkoutButton");
        clickOn("#backButton");
        clickOn("#backButton");


        //tester videre for savedworkout:
        clickOn("#savedWorkoutsButton");
        //tester backbutton også:
        clickOn("#backButton");
        clickOn("#savedWorkoutsButton");
        //skal være et en workout der nå som skal være klikkbart. 
        ArrayList<TextField> workout_list = new ArrayList<>();
        for (Node n: lookup(".savedWorkout-textfield").queryAll()) {
            if (n instanceof TextField) {
                workout_list.add((TextField) n);
            }
        }
        clickOn(workout_list.get(0));
        clickOn("#useWorkoutButton");

    }

    @Test
    public void handleBackTest() {
        clickOn("#startWorkoutButton");
        clickOn("#workoutDoneButton");
        clickOn("#handleBackToMenuButton");
        clickOn("#workoutHistoryButton");
        clickOn("#backButton");
    }
    
}