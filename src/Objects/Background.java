package Objects;

import Game.WallSmasher;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;

public class Background extends Group {
	
//	private final int HEIGHT = 800;
//	private final int WIDTH = 800;
	private Stop[] stops;
	private int CurrentStops = 0;
	
	public void AddStop(double Value, Color C){
		stops[CurrentStops] = new Stop(Value, C);
		CurrentStops++;
	}
	
	private void GenerateLinGradient(){
		Rectangle BG = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT);
		BG.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops));
		getChildren().add(BG);
	}
	
	public void GenerateBackground(int GradientType){
		if (GradientType == 1) GenerateLinGradient();
	}
	
	public Background(int NumOfStops){
		stops = new Stop[NumOfStops];
	}

}
