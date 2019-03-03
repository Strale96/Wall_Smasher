package Objects;

import Game.WallSmasher;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class InformationBlock extends Group {
	
	private Text timer = new Text();
	private Text score = new Text();
	private Text lives = new Text();
	private int Time = 0;
	private int Score = 0;
	private int Lives = 3;
	private double MyWidth = WallSmasher.WIDTH;
	private double MyHeight = WallSmasher.HEIGHT/10;
	private Rectangle Bg = new Rectangle(0, 0, MyWidth, MyHeight);
	private Text PaddleText, BounceText, RedText;
	
	public void GameOver(){ Time = 0; }
	public void AddScore(int Add){ Score += Add; }
	public void RemoveLife(){ if (Lives != 0) Lives--; }
	public void AddLife() { Lives++; }
	public void IncTime() { Time++; }
	public int GetScore() { return Score; }
	public int GetLives() { return Lives; }
	
	public void update(){
		score.setText("Score: " + Score);
		timer.setText("Time: " + Time);
		lives.setText("Lives: " + Lives);
	}
	
	public InformationBlock(){
		Bg.setFill(Color.DARKKHAKI);
		getChildren().add(Bg);
	
		//Sets on screen timer
		timer.setText("Time: " + Time);
		timer.setFont(new Font("Century Gothic", MyWidth/40));
		timer.setTranslateX(MyWidth*0.46);
		timer.setTranslateY(MyHeight*0.63);
		getChildren().add(timer);
		
		//Sets on screen score
		score.setText("Score: " + Score);
		score.setFont(new Font("Century Gothic", MyWidth/40));
		score.setTranslateX(MyWidth*0.05);
		score.setTranslateY(MyHeight*0.63);
		getChildren().add(score);
		
		//Sets number of lives on screen
		lives.setText("Lives: " + Lives);
		lives.setFont(new Font("Century Gothic", MyWidth/40));
		lives.setTranslateX(MyWidth*0.85);
		lives.setTranslateY(MyHeight*0.63);
		getChildren().add(lives);
	
		PaddleText = new Text();
		PaddleText.setText("P");
		PaddleText.setFont(new Font("Century Gothic", MyWidth/60));
		PaddleText.setFill(Color.WHITE);
		PaddleText.setTranslateX(MyWidth*0.25);
		PaddleText.setTranslateY(MyHeight*0.3);
		PaddleText.setStroke(Color.BLACK);
		PaddleText.setStrokeWidth(2);
		BounceText = new Text();
		BounceText.setText("B");
		BounceText.setFont(new Font("Century Gothic", MyWidth/60));
		BounceText.setFill(Color.WHITE);
		BounceText.setTranslateX(MyWidth*0.25);
		BounceText.setTranslateY(MyHeight*0.55);
		BounceText.setStroke(Color.BLACK);
		BounceText.setStrokeWidth(2);
		RedText = new Text();
		RedText.setText("R");
		RedText.setFont(new Font("Century Gothic", MyWidth/60));
		RedText.setFill(Color.WHITE);
		RedText.setTranslateX(MyWidth*0.25);
		RedText.setTranslateY(MyHeight*0.8);
		RedText.setStroke(Color.BLACK);
		RedText.setStrokeWidth(2);
		getChildren().addAll(PaddleText, BounceText, RedText);
	}
}
