package Objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Game.WallSmasher;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Level extends Group {
	
	private Color[] C = new Color[7];
	private int[][] LvL;
	private String Path = "levels/LEVEL_";
	private Scanner	scanner;
	private boolean LevelWon = false;
	private int NumOfStops;
	private int Transition;
	private int[] BgColors;
	private Color[] BgColorArray;
	private boolean RandomLevels = false;
	public Brick[][] Bricks = new Brick[16][16];
	
	public int getLvL(int i, int j){ return LvL[i][j]; }
	public void setLvL(int i, int j){ LvL[i][j] = 0; } //Removes a brick
	private int MaxLevel = 5;
	private int CurrentLevel = 1;
	
	public Background GenerateBackground(){
		Background BG = new Background(NumOfStops);
		for (int i = 0; i < NumOfStops; i++)
			BG.AddStop((((double)i) / NumOfStops) , BgColorArray[i]);
		BG.GenerateBackground(Transition);
		return BG;
	}
	private void FillColorArray(){
		BgColorArray = new Color[NumOfStops];
		for (int i = 0; i < NumOfStops; i++){
			switch(BgColors[i]){
			case 1: BgColorArray[i] = Color.CADETBLUE; break;
			case 2: BgColorArray[i] = Color.DARKOLIVEGREEN; break;
			case 3: BgColorArray[i] = Color.ANTIQUEWHITE; break;
			case 4: BgColorArray[i] = Color.CHARTREUSE; break;
			case 5: BgColorArray[i] = Color.DARKGOLDENROD; break;
			case 6: BgColorArray[i] = Color.DARKORANGE; break;
			case 7: BgColorArray[i] = Color.DARKORCHID; break;
			case 8: BgColorArray[i] = Color.PALETURQUOISE; break;
			case 9: BgColorArray[i] = Color.TOMATO; break;
			case 10: BgColorArray[i] = Color.DEEPSKYBLUE; break;
			case 11: BgColorArray[i] = Color.STEELBLUE; break;
			}
		}
	}
	public void CheckLevelWon(){
		boolean X = true;
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				if (LvL[i][j] != 0) X = false;
		LevelWon = X;
	}
	public int GetValue(int i, int j) { return LvL[i][j] * 100; } //TODO: Place this in Brick
	public boolean LevelWon() { return LevelWon; }
	public void LoadLevel(int LoadL){
		try {
		scanner = new Scanner(new File(Path + LoadL + ".txt"));
		int i = 0;
		int j = 0;
		LvL = new int[16][16];
		NumOfStops = scanner.nextInt();
		Transition = scanner.nextInt();
		BgColors = new int[NumOfStops];
		for (int p = 0; p < NumOfStops; p++){
			BgColors[p] = scanner.nextInt();
		}
		FillColorArray();
		while(scanner.hasNextInt()){
			LvL[i][j] = scanner.nextInt();
			j++;
			if (j == 16) {
				j = 0;
				i++;
			}
		}
		for (i = 0; i < 16; i ++){
			for (j = 0; j < 16; j++){
				if (LvL[i][j] != 0){
					Bricks[i][j] = new Brick(C[LvL[i][j]]);
					Bricks[i][j].setTranslateX(j*WallSmasher.WIDTH/16);
					Bricks[i][j].setTranslateY(i*WallSmasher.HEIGHT/32);
					getChildren().add(Bricks[i][j]);
				}
			}
		}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public int GetLevel() { return CurrentLevel; }
	public void SetNotWon() { LevelWon = false; }
	public void SetRandomLevels() { RandomLevels = true; CurrentLevel = 0; LoadNextLevel();}
	public void ClearRandomLevels() { RandomLevels = false; CurrentLevel = 1;}
	public boolean GameCompleted() { return (MaxLevel == CurrentLevel); }
	public void LoadNextLevel(){
		if (!RandomLevels){
			if(CurrentLevel < MaxLevel){
				CurrentLevel++;
				getChildren().removeAll();
				LoadLevel(CurrentLevel);
			}
		} else {
			CurrentLevel++;
			getChildren().removeAll();
			GenerateRandomLevel();
		}
	}
	
	
	private void RemoveBrick(int i, int j){
		getChildren().remove(Bricks[i][j]);
	}
	public void DestroyBrick(int i, int j){
		KeyValue StartX = new KeyValue(Bricks[i][j].scaleXProperty(), 1.0);
		KeyValue EndX = new KeyValue(Bricks[i][j].scaleXProperty(), 0);
		KeyValue StartY = new KeyValue(Bricks[i][j].scaleYProperty(), 1.0);
		KeyValue EndY = new KeyValue(Bricks[i][j].scaleYProperty(), 0);
		KeyFrame Start = new KeyFrame(Duration.ZERO, StartX, StartY);
		KeyFrame End = new KeyFrame(Duration.millis(500), EndX, EndY);
		Timeline BrickDestroy = new Timeline(Start, End);
		BrickDestroy.play();
		Timeline BrickRemove = new Timeline(new KeyFrame(Duration.millis(500), d -> RemoveBrick(i,j)));
		BrickRemove.play();
	}
	
	private void GenerateRandomLevel() {
		Transition = 1;
		NumOfStops = 2;
		BgColors = new int[NumOfStops];
		LvL = new int[16][16];
		for (int i = 0; i < NumOfStops; i++){
			BgColors[i] = (int)(Math.random()*9) + 1;
		}
		FillColorArray();
		for (int i = 0; i < 16; i ++){
			for (int j = 0; j < 16; j++){
				LvL[i][j] = (int)(Math.random()*10) + 1;
				if (LvL[i][j] > 6) LvL[i][j] = 0;
				if (LvL[i][j] != 0){
					Bricks[i][j] = new Brick(C[LvL[i][j]]);
					Bricks[i][j].setTranslateX(j*WallSmasher.WIDTH/16);
					Bricks[i][j].setTranslateY(i*WallSmasher.HEIGHT/32);
					getChildren().add(Bricks[i][j]);
				}
			}
		}
//		for (int i = 0; i < 16; i ++)
//			{
//				for (int j = 0; j < 16; j++)
//					System.out.print(LvL[i][j] + " ");
//				System.out.println();
//			}		
	}
	
	public Level(){
		C[1] = Color.RED;
		C[2] = Color.BLUE;
		C[3] = Color.GREEN;
		C[4] = Color.YELLOW;
		C[5] = Color.BROWN;
		C[6] = Color.WHITE;
		try{
			scanner = new Scanner(new File("levels/INFO.txt"));
			MaxLevel = scanner.nextInt();
			scanner.close();
		} catch (Exception e) {}
	}

}
