package Handlers;

import Game.WallSmasher;
import Objects.Background;

public class LevelHandler {

	@SuppressWarnings("unused")
	private WallSmasher Game;
	
	public LevelHandler(WallSmasher Game){
		this.Game = Game;
	}
	
	public Background GenerateBackground(){
		Background BG = new Background(1);
		return BG;
	}
}
