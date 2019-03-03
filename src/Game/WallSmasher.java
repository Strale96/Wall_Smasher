package Game;

import Objects.InformationBlock;
import Objects.Level;
import Objects.Paddle;
import Objects.PowerUp;
import Objects.Background;
import Objects.Ball;

import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
import java.util.List;

import Handlers.BallHandler;
import Handlers.GameHandler;
import Handlers.PaddleHandler;
import Handlers.PowerHandler;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.*;
import javafx.util.Duration;

public class WallSmasher extends Application implements EventHandler<KeyEvent>{

	//TODO: multiple balls?
	public static final double WIDTH = Screen.getPrimary().getVisualBounds().getWidth();
	public static final double HEIGHT = Screen.getPrimary().getVisualBounds().getHeight();
	public LevelBuilder LB = new LevelBuilder(this);
	public Paddle paddle;
	public Ball ball = new Ball();
	public Level level = new Level();
	public InformationBlock IB = new InformationBlock();
	public Timeline clock;
	public Group root = new Group();
	public List<PowerUp> CurrPowers = new ArrayList<PowerUp>();
	public GameHandler GAME_HANDLER = new GameHandler(this);
	public PowerHandler POWER_HANDLER = new PowerHandler(this);
	public BallHandler BALL_HANDLER = new BallHandler(this);
	public PaddleHandler PADDLE_HANDLER = new PaddleHandler(this);
	public Background bg;
	public Scene scena;
	public Stage prozor;
	
	public AnimationTimer GameTimer = new AnimationTimer() {
        @Override
        public void handle(long currentNanoTime) {
            update();
        }
    };
	
	private void update(){
		if (!GAME_HANDLER.IsMainMenu && !GAME_HANDLER.IsPaused && !LB.IsLevelBuilder && !LB.IsSavingName && !LB.NewLevelBuilder){
			paddle.update();
			BALL_HANDLER.CheckBallOut();
			IB.update();
			POWER_HANDLER.UpdatePowers();
			BALL_HANDLER.CheckBrickHit();
			BALL_HANDLER.CheckPaddleBounce();
			POWER_HANDLER.CheckPowerUp();
			BALL_HANDLER.CheckLaunch();
			GAME_HANDLER.CheckGameWon();
		}
	}
	
	private void AddElements(Group root){
		GAME_HANDLER.LoadMainMenu();
	}
	
	@Override
	public void start(Stage prozor){
		this.prozor = prozor;
		Scene scena = new Scene(root, WIDTH, HEIGHT);
		AddElements(root);
		
		scena.addEventHandler(KeyEvent.KEY_PRESSED, event -> handle(event));
		scena.addEventHandler(KeyEvent.KEY_RELEASED, event -> handle(event));
		scena.setOnMouseClicked(e -> handleMouse(e));
//		scena.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> paddle.MouseInUse(e, 1));
//		scena.addEventHandler(MouseEvent.MOUSE_MOVED, e -> paddleMouse(e));
//		scena.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> paddle.MouseInUse(e, 3));
		
		this.scena = scena;
		
		paddle = new Paddle(prozor);
//		paddle.SetPaddle();
		
		prozor.setTitle("WallSmasher");
		prozor.setScene(scena);
		prozor.show();
		prozor.setFullScreen(true);
		prozor.setResizable(false);
		GameTimer.start();
        clock = new Timeline (new KeyFrame(Duration.millis(1000), e -> IB.IncTime()));
        clock.setCycleCount(Animation.INDEFINITE);
//        clock.play();
	}
	
	@Override
	public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_PRESSED){
			paddle.MoveRight();
		} else if (event.getCode() == KeyCode.RIGHT && event.getEventType() == KeyEvent.KEY_RELEASED){
			paddle.StopMoving();
		} else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_PRESSED) {
			paddle.MoveLeft();
        } else if (event.getCode() == KeyCode.LEFT && event.getEventType() == KeyEvent.KEY_RELEASED) {
           paddle.StopMoving();
        } else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
        	if (ball.IsMoving() == false) ball.Fire();
        } else if (event.getCode() == KeyCode.ESCAPE && event.getEventType() == KeyEvent.KEY_PRESSED) {
        	prozor.setFullScreen(true);
        	GAME_HANDLER.ESC();
        } else if (event.getCode() == KeyCode.ENTER && event.getEventType() == KeyEvent.KEY_PRESSED){
        	GAME_HANDLER.ENTER();
        } else if (event.getCode() == KeyCode.BACK_SPACE && event.getEventType() == KeyEvent.KEY_RELEASED){
        	GAME_HANDLER.BACKSPACE();
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) GAME_HANDLER.OTHER(event.getCode());
	}
	
	private void paddleMouse(MouseEvent e){
//		if (GAME_HANDLER.GamePlaying)
//		paddle.MouseInUse(e, 2);
	}
	
	public void handleMouse(MouseEvent e){
		LB.MouseClicked(e);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
