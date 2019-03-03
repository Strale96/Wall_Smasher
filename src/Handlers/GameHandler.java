package Handlers;

import Game.WallSmasher;
import Objects.Ball;
import Objects.InformationBlock;
import Objects.Level;
import Objects.Option;
import Objects.Paddle;
import Objects.PowerUp;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GameHandler {

	private WallSmasher Game;
	private Rectangle 	PauseRec, PauseTextRec, Selection;
	private Rectangle[]	SelectionUnderRec = new Rectangle[4];
	private Text[] 		MainOption;
	private Text[] 		PauseOption;
	private Text 		TitleText, LevelOver, LevelText;
	public boolean 	IsPaused = false, IsMainMenu = false, GamePlaying = false;
	//Meni je bio public
	private int MenuCounter = 1,	MaxMenuCounter = 4, 
				PauseCounter = 1, 	MaxPauseCounter = 3;
	private Option[] MainMenuOptions = new Option[4], PauseOptions = new Option[3];
	
	public GameHandler(WallSmasher Game){
		MainOption = new Text[MaxMenuCounter];
		PauseOption = new Text[MaxPauseCounter];
		this.Game = Game;
	}
	
	//Key handlers
	public void ESC(){
		if (GamePlaying || IsPaused){
			if (!IsPaused){
	    		Pause();
	    	} else Unpause();
		} else if (Game.LB.IsLevelBuilder || Game.LB.IsSavingName || Game.LB.NewLevelBuilder) {
			IsPaused = false;
			Game.LB.IsLevelBuilder = false;
			Game.LB.NewLevelBuilder = false;
			Game.LB.IsSavingName = false;
			IsMainMenu = true;
			MenuCounter = 1;
			PauseCounter = 1;
			Game.LB.ClearBuilder();
			LoadMainMenu();
		}
	}

	public void ENTER(){
		if (IsPaused){
    		if (PauseCounter == 1){
    			Unpause();
    		} else if (PauseCounter == 2) {
    			IsPaused = false;
    			IsMainMenu = true;
    			GamePlaying = false;
    			MenuCounter = 1;
    			PauseCounter = 1;
    			Game.level.ClearRandomLevels();
    			LoadMainMenu();
    		} else if (PauseCounter == 3) {
    			Platform.exit();
    		}
    	} else if (IsMainMenu){
    		if (MenuCounter == 1){
    			IsMainMenu = false;
    			GamePlaying = true;
    			LoadFirstLevel();
    		} else if (MenuCounter == 2) {
    			Game.LB.LevelBuilderMenu();
    			IsMainMenu = false;
    			Game.LB.IsLevelBuilder = true;
    		} else if (MenuCounter == 3) {
    			IsMainMenu = false;
    			StartRandomLevels();
    		} else if (MenuCounter == 4) {
    			Platform.exit();
    		}
    	} else if (Game.LB.IsLevelBuilder){
    		if (Game.LB.MainCounter == 1){
    			Game.LB.GenerateLevelBuilder();
    		} else if (Game.LB.MainCounter == 2){
    			
    		}
    	}
	}
	public void BACKSPACE(){
		if (Game.LB.IsSavingName)
			Game.LB.RemoveLetter();
	}
	public void OTHER(KeyCode K){
		if (Game.LB.IsSavingName){
			if (K.isLetterKey() || K.isDigitKey()){
				Game.LB.AddLetter(K.toString());
			}	
		}
	}
	
	public void OptionClicked(int Value){
		if (IsPaused){
    		if (Value == 1){
    			Unpause();
    		} else if (Value == 2) {
    			IsPaused = false;
    			IsMainMenu = true;
    			GamePlaying = false;
    			Game.level.ClearRandomLevels();
    			LoadMainMenu();
    		} else if (Value == 3) {
    			Platform.exit();
    		}
    	} else if (IsMainMenu){
    		if (Value == 1){
    			IsMainMenu = false;
    			GamePlaying = true;
    			LoadFirstLevel();
    			Game.scena.setCursor(Cursor.NONE);
    		} else if (Value == 2) {
    			Game.LB.LevelBuilderMenu();
    			IsMainMenu = false;
    			Game.LB.IsLevelBuilder = true;
    		} else if (Value == 3) {
    			IsMainMenu = false;
    			StartRandomLevels();
    			Game.scena.setCursor(Cursor.NONE);
    		} else if (Value == 4) {
    			Platform.exit();
    		}
    	} else if (Game.LB.IsLevelBuilder){
    		if (Game.LB.MainCounter == 1){
    			Game.LB.GenerateLevelBuilder();
    		} else if (Game.LB.MainCounter == 2){		
    		}
    	}
	}
	
	private void StartRandomLevels(){
		GamePlaying = true;
		Game.root.getChildren().removeAll();
		//Loads background
		Game.level = new Level();
		Game.paddle = new Paddle(Game.prozor);
		Game.ball = new Ball();
		Game.IB = new InformationBlock();
		Game.level.SetRandomLevels();
		Game.bg = Game.level.GenerateBackground();
		Game.root.getChildren().add(Game.bg);
		//Loads information block
		Game.IB.setTranslateY(WallSmasher.HEIGHT*0.95);
		Game.root.getChildren().add(Game.IB);
		//Loads level
		Game.level.setTranslateX(0);
		Game.level.setTranslateY(0);
		Game.root.getChildren().add(Game.level);
		//Loads ball
		Game.ball.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.ball.setTranslateY(WallSmasher.HEIGHT*0.90);
		Game.root.getChildren().add(Game.ball);
		//Loads paddle
		Game.paddle.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.paddle.setTranslateY(WallSmasher.HEIGHT*0.92);
		Game.paddle.SetPaddle();
		Game.root.getChildren().add(Game.paddle);
		//Loads level text
		LevelText = new Text();
		LevelText.setText("Level " + Game.level.GetLevel());
		LevelText.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		LevelText.setTranslateY(WallSmasher.HEIGHT/15);
		LevelText.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		LevelText.setFill(Color.BLACK);
		Game.root.getChildren().add(LevelText);
		Game.clock.play();
	}
	
	private void LoadFirstLevel(){
		Game.root.getChildren().removeAll();
		//Loads background
		Game.level = new Level();
		Game.level.LoadLevel(1);
		Game.paddle = new Paddle(Game.prozor);
		Game.ball = new Ball();
		Game.IB = new InformationBlock();
		Game.bg = Game.level.GenerateBackground();
		Game.root.getChildren().add(Game.bg);
		//Loads information block
		Game.IB.setTranslateY(WallSmasher.HEIGHT*0.95);
		Game.root.getChildren().add(Game.IB);
		//Loads level
		Game.level.setTranslateX(0);
		Game.level.setTranslateY(0);
		Game.root.getChildren().add(Game.level);
		//Loads ball
		Game.ball.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.ball.setTranslateY(WallSmasher.HEIGHT*0.90);
		Game.root.getChildren().add(Game.ball);
		//Loads paddle
		Game.paddle.setTranslateX(WallSmasher.WIDTH/2 - Game.paddle.GetPaddleSize()/2);
		Game.paddle.setTranslateY(WallSmasher.HEIGHT*0.92);
		Game.paddle.SetPaddle();
		Game.root.getChildren().add(Game.paddle);
		//Loads level text
		LevelText = new Text();
		LevelText.setText("Level " + Game.level.GetLevel());
		LevelText.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		LevelText.setTranslateY(WallSmasher.HEIGHT/15);
		LevelText.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		LevelText.setFill(Color.BLACK);
		Game.root.getChildren().add(LevelText);
		Game.root.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent me){
				if (GamePlaying){
					if (Game.ball.IsMoving() == false) Game.ball.Fire();
				}
			}
		});
		Game.clock.play();
	}

	private void NewMainOptions(){
		for (int i = 0; i < MaxMenuCounter; i++){
			MainMenuOptions[i] = new Option(WallSmasher.WIDTH*0.30, WallSmasher.HEIGHT*0.07, Color.BLACK, Color.GREENYELLOW, new Color(0.2993, 0.1090, 0.5916, 1.0), this);
			MainMenuOptions[i].setTranslateX(WallSmasher.WIDTH*0.35);
			MainMenuOptions[i].setTranslateY(WallSmasher.HEIGHT*0.35 + i*WallSmasher.HEIGHT/8);
		}
		MainMenuOptions[0].SetOptions("Start Game", 1);
		MainMenuOptions[1].SetOptions("Level Builder", 2);
		MainMenuOptions[2].SetOptions("Random Levels", 3);
		MainMenuOptions[3].SetOptions("Exit", 4);
	}
	
	public void LoadMainMenu(){
		IsMainMenu = true;
		Game.root.getChildren().removeAll();
		PauseRec = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*1.05);
		PauseRec.setFill(Color.BLACK);
		PauseTextRec = new Rectangle(WallSmasher.WIDTH*0.25, WallSmasher.HEIGHT*0.05, WallSmasher.WIDTH*0.50, WallSmasher.HEIGHT*0.15);
		PauseTextRec.setFill(null);
		PauseTextRec.setStrokeWidth(3);
		PauseTextRec.setStroke(Color.GREENYELLOW);
		TitleText = new Text();
		TitleText.setText("Wall Smasher");
		TitleText.setTranslateX(WallSmasher.WIDTH*0.32);
		TitleText.setTranslateY(WallSmasher.HEIGHT*0.17);
		TitleText.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.11));
		TitleText.setFill(Color.GREENYELLOW);
		Text Autor = new Text();
		Autor.setText("Strahinja Milovanovic");
		Autor.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.05));
		Autor.setTranslateY(WallSmasher.HEIGHT*0.95);
		Autor.setTranslateX(WallSmasher.WIDTH*0.36);
		Autor.setFill(Color.WHITE);
		Game.root.getChildren().addAll(PauseRec, PauseTextRec, TitleText, Autor);
		MainMenuSelection();
	}
	public void MainMenuSelection(){
		NewMainOptions();
		Game.root.getChildren().addAll(MainMenuOptions);
	}
	
	public void NewPauseOptions(){
		for (int i = 0; i < MaxPauseCounter; i++){
			PauseOptions[i] = new Option(WallSmasher.WIDTH*0.30, WallSmasher.HEIGHT*0.07, new Color(1.0, 0, 0, 0), Color.YELLOWGREEN, new Color(0.2993, 0.1090, 0.5916, 1.0), this);
			PauseOptions[i].setTranslateX(WallSmasher.WIDTH*0.35);
			PauseOptions[i].setTranslateY(WallSmasher.HEIGHT*0.35 + i*WallSmasher.HEIGHT/8);
		}
		PauseOptions[0].SetOptions("RESUME", 1);
		PauseOptions[1].SetOptions("MAIN MENU", 2);
		PauseOptions[2].SetOptions("EXIT", 3);
	}
	
	public void GeneratePauseOptions(){
		for (int i = 0; i < MaxPauseCounter; i++){
			PauseOption[i] = new Text();
			PauseOption[i].setTranslateX(280);
			PauseOption[i].setTranslateY(300 + i*100);
			PauseOption[i].setFont(new Font("Century Gothic", 30));
			PauseOption[i].setFill(Color.LIME);
		}
		PauseOption[0].setText("RESUME");
		PauseOption[1].setText("MAIN MENU");
		PauseOption[2].setText("EXIT");
	}
	
	public void PauseSelection(){
		Game.root.getChildren().remove(Selection);
		Game.root.getChildren().removeAll(PauseOption);
//		GeneratePauseOptions();
		NewPauseOptions();
//		Selection = new Rectangle(290, 170 + PauseCounter*100, 250, 40);
//		Selection.setFill(null);
//		Selection.setStrokeWidth(3);
//		Selection.setStroke(Color.LIME);
		Game.root.getChildren().addAll(PauseOptions);
//		Game.root.getChildren().add(Selection);
	}
	public void Pause(){
		IsPaused = true;
		GamePlaying = false;
		Game.clock.pause();
		Game.paddle.Pause();
		Game.ball.Pause();
		Game.scena.setCursor(Cursor.DEFAULT);
		for (PowerUp PU : Game.CurrPowers){
			PU.Pause();
		}
		PauseRec = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*1.05);
		PauseRec.setFill(new Color(0, 0, 0, 0.5));
		TitleText = new Text();
		TitleText.setText("GAME PAUSED");
		TitleText.setTranslateX(WallSmasher.WIDTH*0.30);
		TitleText.setTranslateY(WallSmasher.HEIGHT*0.17);
		TitleText.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.11));
		TitleText.setFill(Color.LIME);
		PauseTextRec = new Rectangle(WallSmasher.WIDTH*0.25, WallSmasher.HEIGHT*0.05, WallSmasher.WIDTH*0.50, WallSmasher.HEIGHT*0.15);
		PauseTextRec.setFill(null);
		PauseTextRec.setStrokeWidth(3);
		PauseTextRec.setStroke(Color.GREENYELLOW);
		Selection = new Rectangle(290, 170 + PauseCounter*100, 250, 40);
		Selection.setFill(null);
		Selection.setStrokeWidth(3);
		Selection.setStroke(Color.LIME);
		Game.root.getChildren().add(PauseRec);
		Game.root.getChildren().add(PauseTextRec);
		Game.root.getChildren().addAll(TitleText);
		PauseSelection();
	}
	public void Unpause(){
		IsPaused = false;
		GamePlaying = true;
		Game.clock.play();
		Game.paddle.UnPause();
		Game.ball.UnPause();
		Game.scena.setCursor(Cursor.NONE);
		for (PowerUp PU : Game.CurrPowers){
			PU.UnPause();
		}
		Game.root.getChildren().remove(PauseRec);
		Game.root.getChildren().remove(PauseTextRec);
		Game.root.getChildren().remove(TitleText);
//		Game.root.getChildren().removeAll(PauseOption);
//		Game.root.getChildren().remove(Selection);
		Game.root.getChildren().removeAll(PauseOptions);
		PauseCounter = 1;
	}
	private void SetNextLevel(){
		Game.root.getChildren().remove(Game.level);
		Game.root.getChildren().remove(Game.paddle);
		Game.root.getChildren().remove(Game.ball);
		Game.root.getChildren().remove(Game.bg);
		Game.level.LoadNextLevel();
		Game.root.getChildren().remove(LevelText);
		LevelText = new Text();
		LevelText.setText("Level " + Game.level.GetLevel());
		LevelText.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		LevelText.setTranslateY(WallSmasher.HEIGHT/15);
		LevelText.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		LevelText.setFill(Color.BLACK);
		Game.bg = Game.level.GenerateBackground();
		Game.root.getChildren().add(Game.bg);
		Game.root.getChildren().add(LevelText);
		Game.root.getChildren().add(Game.paddle);
		Game.root.getChildren().add(Game.ball);
		Game.root.getChildren().add(Game.level);
	}
	private void NextLevel(){
		SetNextLevel();
		Game.PADDLE_HANDLER.ResetPaddle();
		Game.BALL_HANDLER.ResetBall();
		Game.clock.play();
		Game.root.getChildren().remove(LevelOver);
	}
	private void LevelOverText(){
		LevelOver = new Text();
		LevelOver.setText("Level " + Game.level.GetLevel() + " COMPLETED!");
		LevelOver.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		LevelOver.setTranslateY(WallSmasher.HEIGHT*0.5);
		LevelOver.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		LevelOver.setFill(Color.CRIMSON);
		Game.root.getChildren().add(LevelOver);
	}
	private void GameWonText(){
		
		
		Text GameWon = new Text();
		GameWon.setText("GAME WON!");
		GameWon.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		GameWon.setFill(Color.CRIMSON);
		GameWon.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		GameWon.setTranslateY(WallSmasher.HEIGHT*0.5);
		Game.root.getChildren().add(GameWon);
	}
	private void WaitForNextLevel(){
		Timeline waiting = new Timeline(new KeyFrame(Duration.millis(3000), e -> NextLevel()));
		waiting.setCycleCount(1);
		waiting.play();
	}	
	private void HandleWonLevel(){
		Game.paddle.GameOver();
		Game.ball.GameOver();
		Game.clock.stop();
		LevelOverText();
		Game.level.SetNotWon();
		WaitForNextLevel();
	}
	private void HandleWonGame(){
		Game.paddle.GameOver();
		Game.ball.GameOver();
		Game.clock.stop();
		GameWonText();
	}
	public void CheckGameWon(){
		Game.GameTimer.stop();
		if (Game.level.LevelWon())
		if (Game.level.GameCompleted()){
			HandleWonGame();
		} else {
			HandleWonLevel();
		}
		Game.GameTimer.start();
	}
	private void GameOverText(){
		Text GameOver = new Text();
		GameOver.setText("GAME OVER");
		GameOver.setFont(new Font("Century Gothic", WallSmasher.WIDTH/34));
		GameOver.setFill(Color.CRIMSON);
		GameOver.setTranslateX(WallSmasher.WIDTH/2 - WallSmasher.WIDTH/34);
		GameOver.setTranslateY(WallSmasher.HEIGHT*0.5);
		Game.root.getChildren().add(GameOver);
	}	
	public void GameOver(){
		Game.paddle.GameOver();
		Game.ball.GameOver();
		Game.IB.GameOver();
		Game.clock.stop();
		GameOverText();
	}
}
