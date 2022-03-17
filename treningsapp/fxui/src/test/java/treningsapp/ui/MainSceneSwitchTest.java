package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainSceneSwitchTest extends ApplicationTest {

    
    private MainSceneController controller;



    // metode som setter oss inn i hovedScene
    @Override
    public void start(final Stage stage) throws Exception {
        FxApp.setWorkoutModelAccess(new DirectModelAccess());
        
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController(); //oppretter controller remote access
        this.controller.initData(); //hente fra lokal backend
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testControllerNotNull() {
        assertNotNull(this.controller);
    }




    // test om man har byttet scene og om knappen i neste scene brukes.
    @Test
    public void switchMainScenetoAddExerciseScene() {
        clickOn("#trackButton");
        //clickOn("#trackAsYouGoButton");
        
    }

    @Test
    public void SwitchMainSceneToWorkoutHistoryScene() {
        clickOn("#workoutHistoryButton");
        
    
    }

    @Test
    public void SwitchMainSceneToSavedWorkoutScene() {
        clickOn("#savedWorkoutsButton");
    }

    @Test
    public void switchMainScenetoFindWorkoutScene() {
        clickOn("#findWorkoutButton");
    }
}