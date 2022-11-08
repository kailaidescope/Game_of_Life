package lifegame;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Random;

public class Life {

    private final GridPane aCellMatrix;

    public Life(int pMatrixHeight, int pMatrixWidth,int pWindowHeight, int pWindowWidth) throws InvalidParameterException {
        // Check if matrix dimensions are acceptable
        if(pMatrixHeight <= 0 || pMatrixWidth <= 0){
            throw new InvalidParameterException("Cannot have a matrix width or height <= 0.");
        }
        // Check if window dimensions are acceptable
        if(pWindowHeight <= 0 || pWindowWidth <= 0){
            throw new InvalidParameterException("Cannot have a window width or height <= 0.");
        }

        // Initialize cell matrix size
        aCellMatrix = new GridPane();
        aCellMatrix.setStyle("-fx-background-color: black;");

        int cellHeight = pWindowHeight / pMatrixHeight;
        int cellWidth = pWindowWidth / pMatrixWidth;

        // Fill grid with cells
        // (i is y index and j is x index)
        for(int i = 0;i<pMatrixHeight;i++){
            for(int j = 0;j<pMatrixWidth;j++){

                // Initialize each cell with a random alive value
                aCellMatrix.add(new Cell(new Random().nextBoolean(),cellHeight,cellWidth),i,j);
            }
        }

        // Set adjacency matrix of each cell
        // (i is y index and j is x index)
        for(int i = 0;i<pMatrixHeight;i++){
            for(int j = 0;j<pMatrixWidth;j++){
                Cell[] neighbors = new Cell[8];
                int[][] primeDirections = {{1,0},{0,1},{-1,0},{0,-1}};

                if(i==0){primeDirections[2] = null;}
                if(i==pMatrixHeight-1){primeDirections[0] = null;}
                if(j==0){primeDirections[3] = null;}
                if(j==pMatrixWidth-1){primeDirections[1] = null;}

                Cell currentCell = ((Cell)aCellMatrix.getChildren().get(i*8+j));

                // Concatenate directions to possible directions
                for(int k = 0;k<4;k++){
                    if(primeDirections[k] != null){
                        int y = i + primeDirections[k][0];
                        int x = j + primeDirections[k][1];
                        Cell c = (Cell)aCellMatrix.getChildren().get((i+primeDirections[k][0])*8+(j+primeDirections[k][1]));
                        currentCell.addNeighbor((Cell)aCellMatrix.getChildren().get((i+primeDirections[k][0])*8+(j+primeDirections[k][1])));
                        for(int l = k+1;l<4;l++){
                            if(primeDirections[l] != null){
                                currentCell.addNeighbor((Cell)aCellMatrix.getChildren().get((i+primeDirections[l][0]+primeDirections[k][0])*8+(j+primeDirections[l][1]+primeDirections[k][1])));
                            }
                        }
                        System.out.println((Cell)aCellMatrix.getChildren().get(i*8+j));
                    }
                }

                // Initialize each cell with a random alive value
                //aCellMatrix[i][j] = new Cell(new Random().nextBoolean());
            }
        }
    }

    public GridPane getCellMatrix(){
        return aCellMatrix;
    }

    public void startSimulation(int ticks, long delayMilli){
        for(int i = 0;i<ticks;i++){
            for(int j = 0;j<aCellMatrix.getChildren().size();j++){
                ((Cell)aCellMatrix.getChildren().get(j)).calculateNextAlive();
            }
            for(int j = 0;j<aCellMatrix.getChildren().size();j++){
                ((Cell)aCellMatrix.getChildren().get(j)).updateAlive();
            }

            try{
                Thread.sleep(delayMilli);
            } catch(Exception e){
                System.out.println("Wait was interrupted\n"+e);
            }
        }
    }
}
