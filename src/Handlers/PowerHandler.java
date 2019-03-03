package Handlers;

import java.util.ArrayList;
import java.util.List;

import Game.WallSmasher;
import Objects.Ball;
import Objects.PowerUp;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PowerHandler {

	private WallSmasher Game;
	private Rectangle BounceBarRec, PaddleTimer, BounceTimer, RedTimer;
	public boolean BounceBarExists = false;
	private Timeline RedBall, BounceBar, PaddleBar;
	
	public PowerHandler(WallSmasher Game){
		this.Game = Game;
	}
	
	public void GeneratePower(int i, int j){
		double Odds = Math.random();
		boolean PUp;
		if (Odds > 0.80) PUp = true;
		else PUp = false;
//		PUp = true;
		if (PUp) {
			AddPowerUp(i, j);
		}
	}
	
    public void AddPowerUp(int i, int j){
    	int X = (int)(Math.random()*4) + 1;
		PowerUp P = new PowerUp(X);
		P.setTranslateX(Game.level.Bricks[i][j].getTranslateX() + 20);
		P.setTranslateY(Game.level.Bricks[i][j].getTranslateY());
		Game.CurrPowers.add(P);
		Game.root.getChildren().add(P);
    }
	
	public void UpdatePowers(){
		List<PowerUp> ToRemove = new ArrayList<PowerUp>();
		for (PowerUp PU : Game.CurrPowers){
			if (PU.update()){
				Game.root.getChildren().remove(PU);
				ToRemove.add(PU);
			}
		}
		Game.CurrPowers.removeAll(ToRemove);
	}
	
	public void CheckPowerUp(){
		PowerUp ToRemove = null;
		for (PowerUp PU : Game.CurrPowers){
			if (PU.getBoundsInParent().intersects(Game.paddle.getBoundsInParent())){
				AddPower(PU);
				ToRemove = PU;
				break;
			}
		}
		Game.CurrPowers.remove(ToRemove);
	}
	
	private void CreateTimers(int Type){
		double offset = Type * WallSmasher.HEIGHT/33;
		switch(Type){
		case 0: {
			PaddleTimer = new Rectangle(0, 0, WallSmasher.WIDTH*0.10, WallSmasher.HEIGHT/60);
			PaddleTimer.setTranslateX(WallSmasher.WIDTH*0.28);
			PaddleTimer.setTranslateY(WallSmasher.HEIGHT*0.96 + offset);
			PaddleTimer.setFill(Color.CHOCOLATE);  
			Game.root.getChildren().add(PaddleTimer);
			break;
		}
		case 1: {
			BounceTimer = new Rectangle(0, 0, WallSmasher.WIDTH*0.10, WallSmasher.HEIGHT/60);
			BounceTimer.setTranslateX(WallSmasher.WIDTH*0.28);
			BounceTimer.setTranslateY(WallSmasher.HEIGHT*0.955 + offset);
			BounceTimer.setFill(Color.DIMGRAY); 
			Game.root.getChildren().add(BounceTimer);
			break;
		}
		case 2: {
			RedTimer = new Rectangle(0, 0, WallSmasher.WIDTH*0.10, WallSmasher.HEIGHT/60);
			RedTimer.setTranslateX(WallSmasher.WIDTH*0.28);
			RedTimer.setTranslateY(WallSmasher.HEIGHT*0.95 + offset);
			RedTimer.setFill(Color.RED); 
			Game.root.getChildren().add(RedTimer); 
			break;
		}
		}
	}
	
	public void AddPower(PowerUp PU){
		switch (PU.GetPowerUp()){
		case DOUBLE_PADDLE_WIDTH: ChangePaddleSize();	break;
		case BONUS_LIFE: Game.IB.AddLife(); break;
		case BOUNCE_BAR: AddBounceBar(); break;
		case RED_BALL: SetRedBall(Game.ball); break;
		case NONE: System.out.println("PowerUp error! non-existing power up"); break;
		}
		Game.root.getChildren().remove(PU);
	}
	
	private void ChangePaddleSize(){
		if(Game.paddle.DoublePaddle()) {
			Game.paddle.setTranslateX(Game.paddle.getTranslateX() - Game.paddle.GetPaddleSize()/4);
			CreateTimers(0);
			KeyValue StartValue = new KeyValue(PaddleTimer.widthProperty(), WallSmasher.WIDTH*0.10);
			KeyValue EndValue = new KeyValue(PaddleTimer.widthProperty(), 0);
			KeyFrame Start = new KeyFrame(Duration.millis(0), StartValue);
			KeyFrame End = new KeyFrame(Duration.millis(10000), EndValue);
			PaddleBar = new Timeline (Start, End, new KeyFrame(Duration.millis(10000), e -> RestorePaddleSize()));
			PaddleBar.setCycleCount(1);
			PaddleBar.play();
		} else {
			PaddleBar.playFromStart();
		}
	}
	
	private void RestorePaddleSize(){
		Game.root.getChildren().remove(PaddleTimer);
		Game.paddle.NormalPaddle();
		Game.paddle.setTranslateX(Game.paddle.getTranslateX() + Game.paddle.GetPaddleSize()/2);
	}
	
	
	private void RemoveBounceBar(){
		BounceBarExists = false;
		Game.root.getChildren().remove(BounceTimer);
		Game.root.getChildren().remove(BounceBarRec);
	}
	
	private void AddBounceBar(){
		if (BounceBarExists){
			BounceBar.playFromStart();
		} else {
			BounceBarRec = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*0.01);
			BounceBarRec.setFill(Color.PINK);
			BounceBarRec.setTranslateY(WallSmasher.HEIGHT*0.90);
			Game.root.getChildren().add(BounceBarRec);
			BounceBarExists = true;
			CreateTimers(1);
			KeyValue StartValue = new KeyValue(BounceTimer.widthProperty(), WallSmasher.WIDTH*0.10);
			KeyValue EndValue = new KeyValue(BounceTimer.widthProperty(), 0);
			KeyFrame Start = new KeyFrame(Duration.millis(0), StartValue);
			KeyFrame End = new KeyFrame(Duration.millis(10000), EndValue);
			BounceBar = new Timeline (Start, End, new KeyFrame(Duration.millis(10000), e -> RemoveBounceBar()));
			BounceBar.setCycleCount(1);
			BounceBar.play();
		}
	}
	
	private void RemoveRedBall(Ball CurrBall){
		Game.root.getChildren().remove(RedTimer);
		CurrBall.ClearRedBall();
	}
	
	private void SetRedBall(Ball CurrBall){
		if (CurrBall.RedBall()){
			RedBall.playFromStart();
		} else {
			CurrBall.SetRedBall();
			CreateTimers(2);
			KeyValue StartValue = new KeyValue(RedTimer.widthProperty(), WallSmasher.WIDTH*0.10);
			KeyValue EndValue = new KeyValue(RedTimer.widthProperty(), 0);
			KeyFrame Start = new KeyFrame(Duration.millis(0), StartValue);
			KeyFrame End = new KeyFrame(Duration.millis(5000), EndValue);
			RedBall = new Timeline (Start, End, new KeyFrame(Duration.millis(5000), e -> RemoveRedBall(CurrBall)));
			RedBall.setCycleCount(1);
			RedBall.play();
		}
	}
}
