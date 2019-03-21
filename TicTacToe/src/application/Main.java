package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;

/**
 * Simple application for 2 players to play Tic Tac Toe on the same desktop.
 * Utilizes JavaFX to create a simple GUI.
 * @author Max Medberry
 *
 */

public class Main extends Application {
	static volatile boolean playerIsX = true;
	static volatile boolean gameOver = false;

	@Override
	public void start(Stage primaryStage) throws IOException {

		
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setGridLinesVisible(true);
		Scale scale = new Scale(); 
	      
	      //Scales the entire window to be half size so it will fit better on any desktop
	      scale.setX(0.5); 
	      scale.setY(0.5); 
	      
	    /*
	     * Import images used in the game. New images can easily be swapped in by 
	     * adding them to the project folder and updating the path names. Shapes could also be used.
	     */
		Image oImg = new Image("o.jpg", 100, 100, false, false);
		Image xImg = new Image("x.jpg", 100, 100, false, false);
		
		/*
		 * Create an array of nodes to populate the grid.
		 */
		ImageView[] cells = new ImageView[] { new ImageView(xImg), new ImageView(xImg), new ImageView(xImg),
				new ImageView(xImg), new ImageView(xImg), new ImageView(xImg), new ImageView(xImg), new ImageView(xImg),
				new ImageView(xImg) };
		
		Text text = new Text("X's turn");
		Button bt = new Button("Play Again?");
		bt.setVisible(false);
		/*
		 * Even handler for "Play again" button.
		 * Reset the window to it's initial state.
		 */
		bt.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	for(int i = 0; i<9; i++) {
		    		cells[i].setOpacity(0);
		    	}
		    	gameOver = false;
		    	playerIsX = true;
		    	text.setText("X's turn");
		    	bt.setVisible(false);
		    }
		});
		VBox vb1 = new VBox(10);
		vb1.getChildren().addAll(grid, text, bt);
		vb1.setAlignment(Pos.TOP_CENTER);
		vb1.setPadding(new Insets(10, 10, 10, 10));
		Scene scene1 = new Scene(vb1);

		/*
		 * The following block loops through each of the 9 nodes and sets them to opacity 0.
		 * This makes the grid block look empty, but still allows it to be clicked on.
		 * Each node is initialized to the same image and will change depending on which player's
		 * turn it is.
		 */
		int n = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int c = n;
				cells[c].setOpacity(0);
				cells[c].setPickOnBounds(true); // allows click on transparent areas
				/*
				 * Below sets the action of each of the tic-tac-toe cells.
				 * First checks if the game is over or if the cell has already been selected.
				 * If conditions are true, checks which player's turn it is, and sets the image to the corresponding
				 * player. The image opacity is then set to 1 so it can be seen.
				 * Finally it calls a method to check if the game has finished.
				 */
				cells[c].setOnMouseClicked((MouseEvent e) -> {
					if (cells[c].getOpacity() == 0 && !gameOver) {
						if (playerIsX) {
							cells[c].setImage(xImg);
							cells[c].setOpacity(100);
							text.setText("O's turn");
							playerIsX = false;

						} else {
							cells[c].setImage(oImg);
							cells[c].setOpacity(100);
							text.setText("X's turn");
							playerIsX = true;
						}
						//below loop checks if all cells have been selected
						int totalMarked = 0;
						for (int p=0; p<9; p++) {
							if(cells[p].getOpacity()>0) {
								totalMarked++;
							}
						}
						if(winCheck(cells)) {
							if(playerIsX) {
								text.setText("O wins!");
							}
							else {
								text.setText("X wins!");
							}
							gameOver = true;
							bt.setVisible(true);
							
						}
						//a draw has been reached if below condition is true
						else if(totalMarked==9) {
							text.setText("Draw");
							gameOver = true;
							bt.setVisible(true);
						}

					}
				});
				grid.add(cells[n], i, j);
				n++;
			}
		}
		

		primaryStage.setTitle("Tic-tac-toe");
		
		primaryStage.setScene(scene1);
		primaryStage.show();

	}

	/**
	 * Helper method to check if a win condition has been reached.There are 8 win conditions to check.
	 * We know the position of each cell in the array passed in the argument,
	 * so we simply check those 3 locations to see if the images are visible and match for each win condition. 
	 * @param arr The array of the ImageView nodes that make up the 3x3 grid.
	 * @return true if a win condition has been met.
	 */
	private boolean winCheck(ImageView[] arr) {
		if (arr[0].getOpacity() > 0 && arr[1].getOpacity() > 0 && arr[2].getOpacity() > 0) {
			if (arr[0].getImage().equals(arr[1].getImage()) && arr[1].getImage().equals(arr[2].getImage())) {
				return true;
			}
		}
		if (arr[3].getOpacity() > 0 && arr[4].getOpacity() > 0 && arr[5].getOpacity() > 0) {
			if (arr[3].getImage().equals(arr[4].getImage()) && arr[4].getImage().equals(arr[5].getImage())) {
				return true;
			}
		}
		if (arr[6].getOpacity() > 0 && arr[7].getOpacity() > 0 && arr[8].getOpacity() > 0) {
			if (arr[6].getImage().equals(arr[7].getImage()) && arr[7].getImage().equals(arr[8].getImage())) {
				return true;
			}
		}
		if (arr[0].getOpacity() > 0 && arr[3].getOpacity() > 0 && arr[6].getOpacity() > 0) {
			if (arr[3].getImage().equals(arr[0].getImage()) && arr[3].getImage().equals(arr[6].getImage())) {
				return true;
			}
		}
		if (arr[1].getOpacity() > 0 && arr[4].getOpacity() > 0 && arr[7].getOpacity() > 0) {
			if (arr[1].getImage().equals(arr[4].getImage()) && arr[4].getImage().equals(arr[7].getImage())) {
				return true;
			}
		}
		if (arr[2].getOpacity() > 0 && arr[5].getOpacity() > 0 && arr[8].getOpacity() > 0) {
			if (arr[2].getImage().equals(arr[5].getImage()) && arr[5].getImage().equals(arr[8].getImage())) {
				return true;
			}
		}
		if (arr[0].getOpacity() > 0 && arr[4].getOpacity() > 0 && arr[8].getOpacity() > 0) {
			if (arr[0].getImage().equals(arr[4].getImage()) && arr[4].getImage().equals(arr[8].getImage())) {
				return true;
			}
		}
		if (arr[2].getOpacity() > 0 && arr[4].getOpacity() > 0 && arr[6].getOpacity() > 0) {
			if (arr[2].getImage().equals(arr[4].getImage()) && arr[4].getImage().equals(arr[6].getImage())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Main Method to initialize and run the game.
	 * @param args Default
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
