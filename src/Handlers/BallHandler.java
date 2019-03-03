package Handlers;

import Game.WallSmasher;
import Objects.Ball;
import Objects.Level;

public class BallHandler {
	
	private WallSmasher Game;
	
	public BallHandler(WallSmasher Game){
		this.Game = Game;
	}
	
	//Resets ball
	public void ResetBall(){
		Game.root.getChildren().remove(Game.ball);
		Game.ball = new Ball();
		Game.ball.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.ball.setTranslateY(WallSmasher.HEIGHT*0.90);
		Game.ball.Reset();
		Game.root.getChildren().add(Game.ball);
	}
	
	
	public void CheckLaunch(){
		if (Game.ball.IsMoving() == false){
			Game.ball.setTranslateX(Game.paddle.getTranslateX()+Game.paddle.GetPaddleSize()/2);
		}
	}
	
	//Checks if ball fell out
	public void CheckBallOut(){
		boolean GameOver = Game.ball.update(Game.POWER_HANDLER.BounceBarExists);
		if (GameOver) {
			Game.IB.RemoveLife();
			if (Game.IB.GetLives() == 0){
				Game.GAME_HANDLER.GameOver();
			} else {
				ResetBall();
			}
		}
	}
	
	public void CheckPaddleBounce(){
		if (Game.ball.getBoundsInParent().intersects(Game.paddle.getBoundsInParent())){
			double BallX = Game.ball.getTranslateX();
			double PaddleX = Game.paddle.getTranslateX();
			double PaddleSize = Game.paddle.GetPaddleSize();
			HandlePaddleBounce(BallX, PaddleX, PaddleSize);
		}
	}
	
	private void HandlePaddleBounce(double BallX, double PaddleX, double PaddleSize){
		if (Game.ball.IsMoving()){
			if (BallX > PaddleX + PaddleSize) Game.ball.setTranslateX(PaddleX+PaddleSize);
			if (BallX < PaddleX) Game.ball.setTranslateX(PaddleX);
			
			if (BallX > PaddleX + PaddleSize/2) { //Right half
				BallX = BallX - PaddleX - PaddleSize/2;
				double koef = (BallX)/(PaddleSize/2);
				if (koef > 0.8) koef = 0.8;
				Game.ball.SetSpeedX(koef);
				Game.ball.SetSpeedY(1 - koef);
			} else { //Left half
				BallX = BallX - PaddleX;
				double koef = (BallX)/(PaddleSize/2);
				if (koef > 0.8) koef = 0.8;  
				Game.ball.SetSpeedX(-(0.8 - koef));
				Game.ball.SetSpeedY(koef);
			}
		}
	}
	
	public void CheckBrickHit(){
//		int k = -1;
		boolean found = false;
		int i = 0, j = 0;
		for (i = 0; i < 16; i ++){
			if (!found)
			for (j = 0; j < 16; j++){
				if (!found)
				if (Game.level.getLvL(i, j) != 0){
//					k++;
					if (Game.ball.getBoundsInParent().intersects(Game.level.Bricks[i][j].getBoundsInParent())){
						if (!Game.ball.RedBall()){
							if (XBounce(Game.ball, Game.level, i, j)) Game.ball.BounceX();
							else Game.ball.BounceY();
						}
						found = true;
						Game.IB.AddScore(Game.level.GetValue(i, j));
						Game.level.setLvL(i, j);
						break;
					}
				}
			} else {
				i--;
				break;
			}
		}
		if (found){
			if ( i == 16 ) i = 15;
			Game.POWER_HANDLER.GeneratePower(i, j);
			Game.level.DestroyBrick(i, j);
//			k--;
			Game.level.CheckLevelWon();
		}
	}
	
	private boolean XBounce(Ball ball, Level level, int i, int j){
		double BallY = ball.getTranslateY();
		double BrickY = level.Bricks[i][j].getTranslateY();
		int Bounce = HandleBrickBounce(ball, level, i, j);
		
		if (Bounce == 1) return true;
		if (Bounce == -1) return false;
		if (BallY >= BrickY-4 && BallY <= BrickY+25+4) return true;
		return false;
	}
	
	private int HandleBrickBounce(Ball ball, Level level, int i, int j){
		double XSpeed = ball.GetXSpeed();
		double YSpeed = ball.GetYSpeed();
		double BallX = ball.getTranslateX();
		double BallY = ball.getTranslateY();
		double PrevBallX = BallX - XSpeed;
		double PrevBallY = BallY - YSpeed;
		double BrickX = level.Bricks[i][j].getBoundsInParent().getMinX();
		double BrickY = level.Bricks[i][j].getBoundsInParent().getMinY();
		
		Ball simBall = new Ball();
		simBall.setTranslateX(PrevBallX);
		simBall.setTranslateY(PrevBallY);
		
		while (level.Bricks[i][j].getBoundsInParent().intersects(simBall.getBoundsInParent())){
			PrevBallX -= XSpeed;
			PrevBallY -= YSpeed;
			simBall.setTranslateX(PrevBallX);
			simBall.setTranslateY(PrevBallY);
		}
		if ((PrevBallX >= BrickX) && (PrevBallX <= (BrickX + 50))) return -1;
		if ((PrevBallY >= BrickY) && (PrevBallY <= (BrickY + 25))) return 1;
		return 0;
	}
}
