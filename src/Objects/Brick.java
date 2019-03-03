package Objects;

import Game.WallSmasher;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Brick extends Group {

	private Rectangle BR;
	private double SizeX = WallSmasher.WIDTH/16, SizeY = WallSmasher.HEIGHT/32;
	
	//Creates a brick with a given color K
	public Brick(Color K){
		Stop[] stops = new Stop[] {new Stop(0, K), new Stop(0.5, K), new Stop(1, Color.BLACK)}; 
		BR = new Rectangle(0, 0, SizeX, SizeY);
		BR.setFill(new RadialGradient(0, 0, 0.5, 0.5, 0.8, true, CycleMethod.NO_CYCLE, stops));
		
		ImagePattern MyFill = new ImagePattern(new Image("RedBrick.jpg"));
		
		if (K == Color.RED) MyFill = new ImagePattern(new Image("RedBrick.jpg"));
		else if (K == Color.BLUE) MyFill = new ImagePattern(new Image("DarkWhiteBrick.jpg"));
		else if (K == Color.GREEN) MyFill = new ImagePattern(new Image("OtherRedBrick.jpg"));
		else if (K == Color.YELLOW) MyFill = new ImagePattern(new Image("DarkBrick.jpg"));
		else if (K == Color.BROWN) MyFill = new ImagePattern(new Image("BrownBrick.jpg"));
		else if (K == Color.WHITE) MyFill = new ImagePattern(new Image("WhiteBrick.jpg"));
		
		
		BR.setFill(MyFill);
		
		BR.setStroke(Color.BLACK);
		BR.setStrokeWidth(2);
		getChildren().add(BR);
	}
}
