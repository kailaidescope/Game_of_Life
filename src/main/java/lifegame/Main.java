package lifegame;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Application;

import java.util.Scanner;

public class Main extends Application{

    public static void main(String[] args){
        Application.launch(Main.class, args);
    }


    public void start(Stage pStage){

        final int[] screenDimensions = {400,400};

        Life gameOfLife = new Life(10,10, screenDimensions[0], screenDimensions[1]);

        Scene scene = new Scene(gameOfLife.getCellMatrix(),screenDimensions[1],screenDimensions[0]);

        pStage.setScene(scene);
        pStage.show();
        pStage.setAlwaysOnTop(true);

        try{
            Thread.sleep(100);
        } catch(Exception e){
            System.out.println("Wait was interrupted\n"+e);
        }

        Boolean b = (new Scanner(System.in)).nextBoolean();
        if(b){
            gameOfLife.startSimulation(2,400);
        }
    }

}
