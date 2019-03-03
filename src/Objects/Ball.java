package Objects;

import Game.WallSmasher;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Group {

	private Circle BLL;
	
	private double MaxXSpeed = WallSmasher.WIDTH/80;
	private double MaxYSpeed = -WallSmasher.HEIGHT/80;
	private double XSpeed = 0;
	private double YSpeed = 0;
	private double IsPlaying = 1.0;
	private boolean IsMoving = false;
	private boolean RedBall = false;
	private double Size = WallSmasher.WIDTH/160;
	
	//Launches the ball from the paddle
	public void Fire(){
		double koef = Math.random();
		if (koef > 0.8) koef = 0.8;
		SetSpeedX(koef);
		SetSpeedY(1-koef);
		IsMoving = true;
	}
	
	public void SetRedBall() { 
		ImagePattern MyFill = new ImagePattern(new Image("Lava.png"));
		RedBall = true;
		BLL.setFill(MyFill);
		BLL.setStroke(Color.WHITE);
		}
	public void ClearRedBall() { 
		RedBall = false; 
		ImagePattern MyFill = new ImagePattern(new Image("Rock.jpg"));
		BLL.setFill(MyFill);
		BLL.setStroke(Color.BLACK); 
		}
	public void Pause() { IsPlaying = 0; }
	public void UnPause() { IsPlaying = 1.0; }
	public void Reset() { IsMoving = false; }
	public void SetSpeedX(double Speed) { XSpeed = Speed*MaxXSpeed; }
	public void SetSpeedY(double Speed) { YSpeed = Speed*MaxYSpeed; }
	public void GameOver(){ YSpeed = 0; XSpeed = 0; } //TODO: make it nicer
	public void BounceY(){ YSpeed = -YSpeed; }
	public void BounceX(){ XSpeed = -XSpeed; }
	public boolean IsMoving(){ return IsMoving; }
	public double GetXSpeed() { return XSpeed; }
	public double GetYSpeed() { return YSpeed; }
	public boolean RedBall() { return RedBall; }
	
	public boolean update(boolean BounceBar){
		boolean LifeLost = false;
		if (getTranslateX() >= WallSmasher.WIDTH-Size) XSpeed = -XSpeed; //Right edge of the screen
		if (getTranslateX() <= Size) XSpeed = -XSpeed; //Left edge of the screen
		if (getTranslateY() <= Size) YSpeed = -YSpeed; //Top edge of the screen
		if (getTranslateY() >= WallSmasher.HEIGHT*0.89) if (BounceBar){
			YSpeed = -YSpeed;
		}
		if (getTranslateY() > WallSmasher.HEIGHT*0.95) LifeLost = true;
		setTranslateX(getTranslateX() + IsPlaying*XSpeed);
		setTranslateY(getTranslateY() + IsPlaying*YSpeed);
		return LifeLost;
	}
	
	public Ball(){
		BLL = new Circle(0, 0, Size);
		ImagePattern MyFill = new ImagePattern(new Image("Rock.jpg"));
		BLL.setFill(MyFill);
		BLL.setStroke(Color.BLACK);
		BLL.setStrokeWidth(2);
		getChildren().add(BLL);
	}
}
