package treningsapp.ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class FxApp extends Application {

    private static WorkoutModelAccess workoutModelAccess = new RemoteModelAccess();

    @Override
    public void start(final Stage primaryStage) throws Exception {


        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
        final Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);
        MainSceneController controller = firstPaneLoader.getController();
        //controller.setWorkoutModelAccess(this.access); 
        // Running in production, use the default access model
        controller.initData();

        primaryStage.setScene(firstScene);
        primaryStage.show();
    }

    public static void setWorkoutModelAccess(WorkoutModelAccess model) {
        workoutModelAccess = model;
    }
    
    public static WorkoutModelAccess getWorkoutModelAccess() {
        return workoutModelAccess;
    }

    public static void main(final String[] args) {
        launch(args);
    }
}

