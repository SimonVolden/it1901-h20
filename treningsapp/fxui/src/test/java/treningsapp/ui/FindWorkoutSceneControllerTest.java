package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindWorkoutSceneControllerTest extends ApplicationTest {
    
    private FindWorkoutSceneController controller;
    private Map<String, Object> namespace;

    @Override
    public void start(final Stage stage) throws Exception {
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("FindWorkoutScene.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController(); //oppretter controller remote access
        namespace = loader.getNamespace();
        stage.setScene(new Scene(root));
        stage.show();
    }

    
    @Test
    public void enterFindWorkoutSceneTest() {
        //clickOn("#findWorkoutButton");
        Scene findWorkoutScene = Stage.getWindows().get(0).getScene();
        clickOn("#backButton");
        Scene mainScene = Stage.getWindows().get(0).getScene();
        assertFalse(mainScene.equals(findWorkoutScene));
    }

    @Test
    public void chooseSpecificWorkoutTest() {
        //clickOn("#findWorkoutButton");
        ArrayList<TextField> premade_workouts = new ArrayList<>();
        for (Node n: lookup(".preWorkout-textfield").queryAll()) {
            if (n instanceof TextField) {
                premade_workouts.add((TextField) n);
            }
            
        }
        TextField searchField = (TextField) namespace.get("searchField");
        searchField.setText("ab");
        clickOn(premade_workouts.get(0));
        clickOn("#backButton");
        premade_workouts.clear();

        for (Node n: lookup(".preWorkout-textfield").queryAll()) {
            if (n instanceof TextField) {
                premade_workouts.add((TextField) n);
            }
        }
        clickOn(premade_workouts.get(0));
        clickOn("#useWorkoutButton");


        
    }

}