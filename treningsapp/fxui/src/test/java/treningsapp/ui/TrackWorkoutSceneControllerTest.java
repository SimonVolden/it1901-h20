package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TrackWorkoutSceneControllerTest extends ApplicationTest {

    private TrackWorkoutSceneController controller;

    @Override
    public void start(final Stage stage) throws Exception {
        FxApp.setWorkoutModelAccess(new DirectModelAccess());
        
        final FXMLLoader loader = new FXMLLoader(getClass().getResource("TrackWorkoutScene.fxml"));
        final Parent root = loader.load();
        this.controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    } 
    
    @Test
    public void testControllerNotNull() {
        assertNotNull(this.controller);
    }
    
    @Test
    public void handleButtonstest() {
        clickOn("#useSavedWorkoutButton");
        Scene savedWorkoutScene = Stage.getWindows().get(0).getScene();
        clickOn("#backButton");
        Scene trackWorkoutScene = Stage.getWindows().get(0).getScene();
        assertFalse(savedWorkoutScene.equals(trackWorkoutScene));
        clickOn("#backButton");
        Scene mainScene = Stage.getWindows().get(0).getScene(); 
        assertFalse(mainScene.equals(trackWorkoutScene));
    }

    



}