package Objects;

import Game.WallSmasher;
import Handlers.GameHandler;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;

public class Option extends Group {

	private Rectangle Background;
	private Color BorderColor, Fill, AltColor;
	private Text OptionText;
	private int Value;
	private GameHandler GameH;
	
	private void SetHandlers(){
		EventHandler<MouseEvent> MouseEnteredHandler = new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				Background.setFill(AltColor);
			}	
		};
		Background.setOnMouseEntered(MouseEnteredHandler);
		OptionText.setOnMouseEntered(MouseEnteredHandler);
		
		EventHandler<MouseEvent> MouseExitedHandler = new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				Background.setFill(Fill);
			}	
		};
		Background.setOnMouseExited(MouseExitedHandler);
		OptionText.setOnMouseExited(MouseExitedHandler);
		
		EventHandler<MouseEvent> MouseClickedHandler = new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				Background.setFill(Fill);
				GameH.OptionClicked(Value);
			}	
		};		
		Background.setOnMouseClicked(MouseClickedHandler);
		OptionText.setOnMouseClicked(MouseClickedHandler);
	}
	
	public Option(double Width, double Height, Color Fill, Color BorderColor, Color AltColor, String Text, int Value, GameHandler GameH){
		this.BorderColor = BorderColor;
		this.Fill = Fill;
		this.AltColor = AltColor;
		this.Value = Value;
		this.GameH = GameH;
		
		Background = new Rectangle(Width, Height);
		Background.setStroke(BorderColor);
		Background.setFill(Fill);

		
		OptionText = new Text();
		OptionText.setText(Text);
		OptionText.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.05));
		OptionText.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
		OptionText.setFill(Color.WHITE);
		
		SetHandlers();
		
		getChildren().addAll(Background, OptionText);
	}
	
	public Option(double Width, double Height, Color Fill, Color BorderColor, Color AltColor, GameHandler GameH){
		this.BorderColor = BorderColor;
		this.Fill = Fill;
		this.AltColor = AltColor;
		this.GameH = GameH;
		
		Background = new Rectangle(Width, Height);
		Background.setStroke(BorderColor);
		Background.setStrokeWidth(2.0);
		Background.setFill(Fill);
		
		OptionText = new Text();
		OptionText.setFont(new Font("Century Gothic", WallSmasher.HEIGHT*0.05));
		OptionText.setBoundsType(TextBoundsType.LOGICAL_VERTICAL_CENTER);
		OptionText.setFill(BorderColor);
		OptionText.setTranslateY(Height*0.75);
		OptionText.setTranslateX(Width/10);
		
		SetHandlers();
		
		getChildren().addAll(Background, OptionText);
	}
	
	public void SetOptions(String Text, int Value){
		this.Value = Value;
		OptionText.setText(Text);
	}
	
	public int GetValue(){ return Value; }
	
	public void SetFontSize(int Size){
		OptionText.setFont(new Font("Century Gothic", Size));
	}
}
