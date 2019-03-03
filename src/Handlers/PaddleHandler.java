package Handlers;

import Game.WallSmasher;

public class PaddleHandler {

	private WallSmasher Game;
	
	public PaddleHandler(WallSmasher Game){
		this.Game = Game;
	}
	
	public void ResetPaddle(){
		Game.paddle.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.paddle.setTranslateY(WallSmasher.HEIGHT*0.90);
		Game.paddle.Reset();
	}
}
