package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

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
import treningsapp.core.Exercise;
import treningsapp.core.Workout;
import treningsapp.core.WorkoutProgram;
import treningsapp.json.TreningsappModule;


public class AddExerciseSceneControllerTest extends ApplicationTest {

    private AddExerciseSceneController controller;
    private Map<String, Object> namespace;
    private List<TextField> box_textField;
    private TextField tf;


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


    private List<TextField> getNewVboxList() {
        ScrollPane sp = (ScrollPane) namespace.get("exerciseScrollPane");
        VBox box = new VBox();
        if (sp.getContent() instanceof VBox) {
            box = (VBox) sp.getContent();
        }
        //VBox box = (VBox) sp.getContent();
        box_textField = new ArrayList<>();
        for (Node n: box.getChildren()) {
            if (n instanceof TextField) {
                box_textField.add((TextField) n);
            }
        }
       // Iterator<Node> childIterator = box.getChildren().iterator();
        //while (childIterator.hasNext()) {
          //  box_textField.add((TextField) childIterator.next());
        //}
        return box_textField;
    }


    @BeforeEach
    public void setup() {
        FxApp.setWorkoutModelAccess(new DirectModelAccess());
        
        tf = (TextField) namespace.get("exerciseNameTextField");
        tf.setText("Trening1");
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
    }

    @Test
    public void testControllerNotNull() {
        assertNotNull(this.controller);
    }

    @Test
    public void emptyTextFieldAfterButtonPressedTest() {
        TextField tf = lookup("#exerciseNameTextField").query();
        assertEquals("", tf.getText());
        tf.setText("Trening");
        assertTrue(tf.getText().equals("Trening"));
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
        assertTrue(tf.getText().equals(""));
        assertEquals("Trening1", box_textField.get(0).getText());
    }



   
    @Test
    public void handleAddExerciseTest() {
        assertEquals(1, box_textField.size());
        assertEquals("Trening1", box_textField.get(0).getText());
        // TextField tf2 = (TextField)namespace.get("exerciseNameTextField");
        tf.setText("Trening2");

        assertEquals("Trening2", tf.getText());
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
        assertTrue(tf.getText().equals(""));
        assertEquals(2, box_textField.size());
        assertEquals("Trening2", box_textField.get(1).getText());
    }

    @Test
    public void noExerciseAddedIfEmptyStringTest() {
        TextField tf3 = (TextField) namespace.get("exerciseNameTextField");
        tf3.setText("");
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
        assertFalse(box_textField.size() == 2);
        assertEquals(1, box_textField.size());
    }

    @Test
    public void removeExerciseTest() {
        clickOn("#removeExerciseButton");
        box_textField = getNewVboxList();
        assertEquals(0, box_textField.size());
    }

   
    @Test
    public void switchSceneBetweenWorkoutDoneSceneTest() throws IOException {
        Scene scene1 = Stage.getWindows().get(0).getScene();
        clickOn("#startWorkoutButton");  
        Scene scene2 = Stage.getWindows().get(0).getScene();
        
        //sjekker om det har skjedd et scenebytte
        assertFalse(scene1.equals(scene2));
        
        //klikke gjennom alle knappene i workoutDoneScene som inneholder en backbutton og en backtomenubutton
        clickOn("#workoutDoneButton");
        clickOn("#backButton");

        
        //For å komme tibake til hovedscenen
        clickOn("#workoutDoneButton");
        Scene workoutDoneScene = Stage.getWindows().get(0).getScene();
        clickOn("#handleBackToMenuButton");
        Scene mainMenuScene = Stage.getWindows().get(0).getScene();
        assertFalse(workoutDoneScene.equals(mainMenuScene)); //funker bare hvis man har server oppe
        removeTestFiles();
    }
    
     /**
     * Used to remove testfiles.
     */
    private void removeTestFiles() throws IOException {
        WorkoutProgram workoutHistory = new WorkoutProgram();
        
        Exercise bench = new Exercise("Bench");
        bench.addSet(1, 2.0);
        Workout chest = new Workout("Chest");
        chest.addExercise(bench);
        workoutHistory.addWorkouts(chest);
        

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
    
}