package Objects;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import Game.PowerUpType;
import Game.WallSmasher;

public class PowerUp extends Group {

	private PowerUpType Type = PowerUpType.NONE;
	private Color C = Color.BLACK;
	private double Velocity = 5.0;
	private double IsPlaying = 1.0;
	
	public PowerUp(int T){
		Text text = new Text();
		switch (T){
		case 1: Type = PowerUpType.DOUBLE_PADDLE_WIDTH; C = Color.CHOCOLATE; text.setText("P"); break;
		case 2: Type = PowerUpType.BONUS_LIFE; C = Color.DARKBLUE; text.setText("L"); break;
		case 3: Type = PowerUpType.BOUNCE_BAR; C = Color.DIMGRAY; text.setText("B"); break;
		case 4: Type = PowerUpType.RED_BALL; C = Color.RED; text.setText("R"); break;
		}
		text.setFill(Color.WHITE);
		text.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.04));
		text.setStrokeWidth(2);
		text.setStroke(Color.WHITE);
		text.setTranslateX(WallSmasher.HEIGHT*0.01);
		text.setTranslateY(WallSmasher.HEIGHT*0.04);
		Rectangle Rec = new Rectangle(0, 0, WallSmasher.WIDTH/40, WallSmasher.WIDTH/40);
		Rec.setFill(C);
		Rec.setStroke(Color.GOLD);
		Rec.setStrokeWidth(3);
		getChildren().add(Rec);
		getChildren().add(text);
	}
	
	public void Pause() { IsPlaying = 0; }
	public void UnPause() { IsPlaying = 1.0; }
	public PowerUpType GetPowerUp() { return Type; }
	public boolean update(){
		setTranslateY(getTranslateY() + IsPlaying*Velocity);
		if (getTranslateY() > WallSmasher.HEIGHT*0.95) return true;
		return false;
	}
}
