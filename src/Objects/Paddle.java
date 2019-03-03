package Objects;

import java.awt.AWTException;
import java.awt.Robot;

import Game.WallSmasher;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Paddle extends Group{

	private boolean Right = false;
	private boolean Left = false;
	private double Velocity = 7.5;
	private double SizeX = WallSmasher.WIDTH/8, SizeY = WallSmasher.HEIGHT/50;
	private double IsPlaying = 1.0;
	private Rectangle pdl;
	private boolean DoubleP = false, MouseMoved = false;
	private double PozX = 0.0, StaraX = 0.0, KorakX = 0.0;
	private Stage prozor;
	
	public Paddle(Stage prozor){		
		this.prozor = prozor;
		pdl = new Rectangle(0, 0, SizeX, SizeY);
		
		ImagePattern MyFill = new ImagePattern(new Image("Paddle.jpg"));
		pdl.setFill(MyFill);
		
//		pdl.setFill(Color.CRIMSON);
		pdl.setStrokeWidth(2);
		pdl.setStroke(Color.BLACK);
		getChildren().add(pdl);
		MouseMoved = false;
	}

	public void SetPaddle() {
		Robot robot;
		try {
			robot = new Robot();
//			IsPlaying = 0;
	        robot.mouseMove((int) prozor.getX() + (int)getTranslateX() + (int)SizeX/2, (int) getTranslateY());
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {
			 public void handle(MouseEvent me) {
					MouseInUse(me);
				}
		 };
		
		prozor.getScene().setOnMouseMoved(mouseHandler);
	}
	
	public void MoveRight(){
		Right = true;
		Left = false;
	}
	
	public void MoveLeft(){
		Left = true;
		Right = false;
	}
	
	public void StopMoving(){
		Left = false;
		Right = false;
	}
	
	
	public void Pause() { IsPlaying = 0;}
	public void UnPause() { IsPlaying = 1.0; }
	public double GetPaddleSize() { return SizeX; }
	public boolean Right() { return Right; }
	public boolean Left() { return Left; }
	public double Velocity() { return Velocity; }
	public void GameOver(){ Velocity = 0; }
	public void Reset() { Velocity = 7.5; }
	public boolean DoublePaddle() { 
		boolean IsChanged = false;
		if (!DoubleP){
			SizeX = SizeX*2; 
			getChildren().remove(pdl);
			pdl = new Rectangle(0, 0, SizeX, SizeY);
			
			ImagePattern MyFill = new ImagePattern(new Image("Paddle.jpg"));
			pdl.setFill(MyFill);
			
//			pdl.setFill(Color.CRIMSON);
			pdl.setStrokeWidth(2);
			pdl.setStroke(Color.BLACK);
			getChildren().add(pdl);	
			DoubleP = true;
			IsChanged = true;
		}
		return IsChanged;
	}
	public void NormalPaddle() { 
		if (DoubleP){
			SizeX = SizeX/2;
			getChildren().remove(pdl);
			pdl = new Rectangle(0, 0, SizeX, SizeY);
			
			ImagePattern MyFill = new ImagePattern(new Image("Paddle.jpg"));
			pdl.setFill(MyFill);
			
//			pdl.setFill(Color.CRIMSON);
			getChildren().add(pdl);	
			pdl.setStrokeWidth(2);
			pdl.setStroke(Color.BLACK);
			DoubleP = false;
		}
	}
	
	public void MouseInUse(MouseEvent e){
		if (!MouseMoved) {
			PozX = e.getScreenX();
			MouseMoved = true;
		}
		System.out.print(KorakX + " " + PozX + " " + StaraX + "   ");
		StaraX = PozX;
		PozX = e.getScreenX();
		KorakX = (PozX - StaraX);
		if (KorakX > 0){
			Right = true;
			Left = false;
		} else if (KorakX < 0){
			Right = false;
			Left = true;
		} else {
			Left = false;
			Right = false;
		}
		updateMouse();
		Left = false;
		Right = false;
		System.out.println(KorakX + " " + PozX + StaraX + " ");
	}
	
	private void updateMouse(){
		int X = 0;
		if (Left == false && Right == false) X = 0;
		if (Left == false && Right == true && getTranslateX() < (WallSmasher.WIDTH-SizeX)) X = 1;
//		else if (Left == false && Right == true && getTranslateX() >= (800-Size)) {
//			try {
//	            Robot robot = new Robot();
//	            IsPlaying = 0;
//	            robot.mouseMove((int) prozor.getX() + 750, 500);
//	            StaraX = prozor.getX() + 300;
//	            IsPlaying = 1;
//	        } catch (AWTException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//		}
		if (Left == true && Right == false && getTranslateX() > 0) X = 1;
//		else if (Left == true && Right == false && getTranslateX() <= 0 + Size) {
//			try {
//	            Robot robot = new Robot();
//	            IsPlaying = 0;
//	            robot.mouseMove((int) prozor.getX() + 1, 500);
//	            StaraX = prozor.getX() + 300;
//	            IsPlaying = 1;
//	        } catch (AWTException e) {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//		}
		setTranslateX(getTranslateX() + IsPlaying*X*KorakX);
	}
	
	public void update(){
		int X = 0;
		if (Left == false && Right == false) X = 0;
		if (Left == false && Right == true && getTranslateX() < (800-SizeX)) X = 1;
		if (Left == true && Right == false && getTranslateX() > 0) X = -1;
		setTranslateX(getTranslateX() + IsPlaying*X*Velocity);
	}
}
