package lifegame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.util.Duration;

public class Main extends Application{

    private static Life aGameOfLife;
    private static Stage aStage;
    private static Scene aGameScene;
    private static final int[] aScreenDimensions = {650,600};
    private static final int[] aControlDimensions = {50,600};

    public static void main(String[] args){
        Application.launch(Main.class, args);
    }

    public void start(Stage pStage){
        // HANDLE INITIALIZING GAME OBJECTS

        Timeline timeline = new Timeline();

        aGameOfLife = new Life(100,100, aScreenDimensions[0]-aControlDimensions[0], aScreenDimensions[1]);

        // HANDLE GUI

        VBox vBox = new VBox(aGameOfLife.getCellMatrix());

        Button nextButton = new Button("Next Iteration");
        Button playPauseButton = new Button("Play");
        Button resetButton = new Button("Randomize");

        HBox buttonBox = new HBox(playPauseButton, nextButton,resetButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(20);

        vBox.getChildren().add(buttonBox);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(aControlDimensions[0] * 0.35);

        aGameScene = new Scene(vBox, aScreenDimensions[1], aScreenDimensions[0]);

        aStage = pStage;
        aStage.setScene(aGameScene);
        aStage.show();
        aStage.setAlwaysOnTop(true);

        // HANDLE ANIMATION

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.2), event -> {
            aGameOfLife.iterate();
        });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);


        // FOLLOWING CODE HANDLES ALL BUTTON PRESSES

        nextButton.setOnAction(e -> {
            aGameOfLife.iterate();
        });

        playPauseButton.setOnAction(e -> {
            if(timeline.getStatus().equals(Animation.Status.RUNNING)){
                playPauseButton.setText("Play");
                timeline.pause();
            } else{
                playPauseButton.setText("Pause");
                timeline.play();
            }
        });

        resetButton.setOnAction(e -> {
            // Stop simulation
            timeline.pause();

            aGameOfLife = new Life(100,100, aScreenDimensions[0]-aControlDimensions[0], aScreenDimensions[1]);
            vBox.getChildren().set(0,aGameOfLife.getCellMatrix());
            aStage.show();

            // Set buttons to correct text
            playPauseButton.setText("Play");
        });

        //System.out.println("A game of life matrix:\n\n"+aGameOfLife);
    }
}
