package treningsapp.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class WorkoutSceneControllerTest extends ApplicationTest {

    private AddExerciseSceneController controller;
    private List<TextField> box_textField;
    private Map<String, Object> namespace;
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

    @Test
    public void testControllerNotNull() {
        assertNotNull(this.controller);
    }

    private List<TextField> getNewVboxList() {
        ScrollPane sp = (ScrollPane) namespace.get("exerciseScrollPane");
        VBox box = new VBox();
        if (sp.getContent() instanceof VBox) {
            box = (VBox) sp.getContent();
        }
        // VBox box = (VBox) sp.getContent();
        box_textField = new ArrayList<>();
        for (Node n : box.getChildren()) {
            if (n instanceof TextField) {
                box_textField.add((TextField) n);
            }
        }
        return box_textField;
    }

    @BeforeEach
    public void setup() {
        tf = (TextField) namespace.get("exerciseNameTextField");
        tf.setText("TreningA");
        clickOn("#addExerciseButton");
        box_textField = getNewVboxList();
    }

    @Test
    public void testSetButtons() throws IOException {
        TextField textfield = (TextField) namespace.get("exerciseNameTextField");
        textfield.setText("TreningB");
        clickOn("#addExerciseButton");
        // lager ny vbox med alle treningA og treningB
        box_textField = getNewVboxList();
        clickOn("#startWorkoutButton");

        // Sjekker om textfield viser riktig ??velse
        TextField exerciseName = lookup("#workoutExerciseName").query();
        assertEquals("TreningA", exerciseName.getText());

        // man kan klikke p?? addset(flere set per ??velse) og fjernset uten at det er noe
        // reps eller vekt s?? tester om det funker:
        clickOn("#addSetButton");
        clickOn("#addSetButton");
        clickOn("#removeSetButton");

        // ved ?? trykke p?? nextExercise b??r exerciseName textfield bytte til neste alts??
        // TreningB
        clickOn("#nextExercise");
        assertEquals("TreningB", exerciseName.getText());

        clickOn("#addSetButton");
        clickOn("#lastExercise"); // -> tilbake til forrige ??velse
        assertFalse(exerciseName.getText().equals("TreningB"));

        // f?? testet at backButton fungerer
        clickOn("#backButton");
        clickOn("#startWorkoutButton");
    }

}

