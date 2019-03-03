package Game;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import Objects.Brick;
import Objects.Option;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LevelBuilder{

	private WallSmasher Game;
	private Color SelectedColor;
	public boolean IsLevelBuilder = false, NewLevelBuilder = false, IsSavingName = false, IsEditor = false;
	private Color[] BlockColors = new Color[7];
	private int[][] CustomLevel = new int[16][16];
	private Brick [][]Bricks = new Brick[16][16];
	private Brick[] BrickColors = new Brick[7];
	private Brick CurrentBrickColor;
	private Scanner scanner;
	private Text NewLevel, EditLevel, SaveText;
	public Rectangle Selection, SaveRec;
	private String FileName = "";
	private Text[] MadeLevels;
	private int NumOfLevels = 0, SelectedColorInt = 1;
	private Option[] MenuOptions = new Option[2];
	public int MainCounter = 1, MaxMainCounter = 2;
	
	public LevelBuilder(WallSmasher Game){
		this.Game = Game;
		BlockColors[1] = Color.RED;
		BlockColors[2] = Color.BLUE;
		BlockColors[3] = Color.GREEN;
		BlockColors[4] = Color.YELLOW;
		BlockColors[5] = Color.BROWN;
		BlockColors[6] = Color.WHITE;
		SelectedColor = BlockColors[1];
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++)
				CustomLevel[i][j] = 0;
		try{
			scanner = new Scanner(new File("custom/INFO.txt"));
			NumOfLevels = scanner.nextInt();
			MadeLevels = new Text[NumOfLevels];
			scanner.nextLine();
			for (int i = 0; i < NumOfLevels; i++){
				MadeLevels[i] = new Text();
				MadeLevels[i].setText(scanner.nextLine());
			scanner.close();
			}
		} catch (Exception e) {}
	}
	
	public void LevelBuilderMenu(){
		Game.root.getChildren().removeAll();
		Rectangle BG = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*1.05);
		BG.setFill(Color.ROYALBLUE);
		
		for (int i = 0; i < 2; i++){
			MenuOptions[i] = new Option(WallSmasher.WIDTH*0.50, WallSmasher.HEIGHT*0.15, Color.ROYALBLUE, Color.WHITE, Color.BLUEVIOLET, Game.GAME_HANDLER);
			MenuOptions[i].setTranslateX(WallSmasher.WIDTH*0.25);
			MenuOptions[i].setTranslateY(WallSmasher.HEIGHT*0.40 + i*WallSmasher.HEIGHT*0.20);
			MenuOptions[i].SetFontSize((int) (WallSmasher.WIDTH*0.05));
		}
		
		MenuOptions[0].SetOptions("Create new level", 1);
		MenuOptions[1].SetOptions("Edit existing level", 2);
		
		Game.root.getChildren().add(BG);
		Game.root.getChildren().addAll(MenuOptions);
	}
	
	public void GenerateLevelBuilder(){
		IsLevelBuilder = false;
		Game.root.getChildren().removeAll();
		Rectangle BG = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*1.05);
		BG.setFill(Color.ROYALBLUE);
		Game.root.getChildren().add(BG);
		for (int i = 0; i < 16; i++)
			for (int j = 0; j < 16; j++){
				Rectangle rec = new Rectangle(0, 0, WallSmasher.WIDTH/16, WallSmasher.HEIGHT/32);
				rec.setFill(null);
				rec.setStroke(Color.WHITE);
				rec.setStrokeWidth(1);
				rec.getStrokeDashArray().addAll(3.0, 6.0);
				rec.setTranslateX(i*WallSmasher.WIDTH/16);
				rec.setTranslateY(j*WallSmasher.HEIGHT/32);
				Game.root.getChildren().add(rec);
			}
		Text CurrentColor = new Text();
		CurrentColor.setText("Selected Color: ");
		CurrentColor.setFont(new Font("Century Gothic", WallSmasher.WIDTH*0.02));
		CurrentColor.setFill(Color.WHITE);
		CurrentColor.setTranslateX(WallSmasher.WIDTH*0.15);
		CurrentColor.setTranslateY(WallSmasher.HEIGHT*0.60);
		Game.root.getChildren().add(CurrentColor);
		CurrentBrickColor = new Brick(Color.RED);
		CurrentBrickColor.setTranslateX(WallSmasher.WIDTH*0.31);
		CurrentBrickColor.setTranslateY(WallSmasher.HEIGHT*0.575);
		Game.root.getChildren().add(CurrentBrickColor);
		for (int i = 1; i < 7; i++){
			BrickColors[i] = new Brick(BlockColors[i]);
			BrickColors[i].setTranslateX(i * WallSmasher.WIDTH/14 + WallSmasher.WIDTH*0.1);
			BrickColors[i].setTranslateY(WallSmasher.HEIGHT*0.80);
			Game.root.getChildren().add(BrickColors[i]);
		}
		Text Save = new Text();
		Save.setText("Save level");
		Save.setFont(new Font("Century Gothic", (int) (WallSmasher.HEIGHT*0.04)));
		Save.setFill(Color.WHITE);
		Save.setTranslateX(WallSmasher.WIDTH*0.45);
		Save.setTranslateY(WallSmasher.HEIGHT*0.95);
		SaveRec = new Rectangle(0, 0, WallSmasher.WIDTH*0.20, WallSmasher.HEIGHT*0.05);
		SaveRec.setFill(null);
		SaveRec.setStroke(Color.WHITE);
		SaveRec.setStrokeWidth(3);
		SaveRec.setTranslateX(WallSmasher.WIDTH*0.4);
		SaveRec.setTranslateY(WallSmasher.HEIGHT*0.91);
		Game.root.getChildren().add(SaveRec);
		Game.root.getChildren().add(Save);
		NewLevelBuilder = true;
	}
	
	public void AddLetter(String C){
		if (FileName.length() <= 16){
			FileName += C.substring(C.length()-1);
			SaveText.setText(FileName);	
		}
	}
	public void RemoveLetter(){
		if (FileName.length() > 0){
			FileName = FileName.substring(0, FileName.length()-1);
			SaveText.setText(FileName);	
		}
	}
	
	private void SaveLevel(){
		Game.root.getChildren().removeAll();
		Rectangle BG = new Rectangle(0, 0, WallSmasher.WIDTH, WallSmasher.HEIGHT*1.05);
		BG.setFill(Color.ROYALBLUE);
		Game.root.getChildren().add(BG);
		Selection = new Rectangle(0, 0, WallSmasher.WIDTH*0.5, WallSmasher.HEIGHT/16);
		Selection.setFill(null);
		Selection.setStroke(Color.WHITE);
		Selection.setStrokeWidth(3);
		Selection.setTranslateX(WallSmasher.WIDTH*0.25);
		Selection.setTranslateY(WallSmasher.HEIGHT*0.30);
		SaveText = new Text();
		SaveText.setFill(Color.WHITE);
		SaveText.setFont(new Font("Century Gothic", (int)(WallSmasher.HEIGHT*0.05)));
		SaveText.setTranslateX(WallSmasher.WIDTH*0.26);
		SaveText.setTranslateY(WallSmasher.HEIGHT*0.35);
		Game.root.getChildren().add(Selection);
		Game.root.getChildren().add(SaveText);
		Text Save = new Text();
		Save.setText("Save level");
		Save.setFont(new Font("Century Gothic", (int)(WallSmasher.HEIGHT*0.05)));
		Save.setFill(Color.WHITE);
		Save.setTranslateX(WallSmasher.WIDTH*0.43);
		Save.setTranslateY(WallSmasher.HEIGHT*0.50);
		SaveRec = new Rectangle(0, 0, WallSmasher.WIDTH*0.3, WallSmasher.HEIGHT/16);
		SaveRec.setFill(null);
		SaveRec.setStroke(Color.WHITE);
		SaveRec.setStrokeWidth(3);
		SaveRec.setTranslateX(WallSmasher.WIDTH*0.35);
		SaveRec.setTranslateY(WallSmasher.HEIGHT*0.45);
		Game.root.getChildren().add(SaveRec);
		Game.root.getChildren().add(Save);
		IsSavingName = true;
		NewLevelBuilder = false;
		IsLevelBuilder = false;
	}
	public void ClearBuilder(){
		for (int i = 0; i < 16; i ++)
			for (int j = 0; j < 16; j++){
				if (CustomLevel[i][j] != 0){
					Game.root.getChildren().remove(Bricks[i][j]);
					Bricks[i][j] = null;
					CustomLevel[i][j] = 0;
				}
			}
	}
	
	public void MouseClicked(MouseEvent e){
		if (NewLevelBuilder){
			if (e.getSceneY() <= WallSmasher.HEIGHT*0.5){
				int i = (int)(e.getSceneX()/(WallSmasher.WIDTH/16));
				int j = (int)(e.getSceneY()/(WallSmasher.HEIGHT/32));
//				System.out.println(i + " " +  j);
				if (e.getButton() == MouseButton.PRIMARY){
					if (CustomLevel[i][j] == 0){
						Bricks[i][j] = new Brick(SelectedColor);
						CustomLevel[i][j] = SelectedColorInt;
						Bricks[i][j].setTranslateX(i * WallSmasher.WIDTH/16);
						Bricks[i][j].setTranslateY(j * WallSmasher.HEIGHT/32);
						Game.root.getChildren().add(Bricks[i][j]);
					}
				} else if (e.getButton() == MouseButton.SECONDARY){
					if (CustomLevel[i][j] != 0){
						Game.root.getChildren().remove(Bricks[i][j]);
						CustomLevel[i][j] = 0;
					}
				}
			} else {
				double X = e.getSceneX();
				double Y = e.getSceneY();
				for (int i = 1; i < 7; i++){
					if (BrickColors[i].getBoundsInParent().intersects(X, Y, 1, 1)){
						SelectedColor = BlockColors[i];
						SelectedColorInt = i;
						Game.root.getChildren().remove(CurrentBrickColor);
						CurrentBrickColor = new Brick(SelectedColor);
						CurrentBrickColor.setTranslateX(WallSmasher.WIDTH*0.31);
						CurrentBrickColor.setTranslateY(WallSmasher.HEIGHT*0.575);
						Game.root.getChildren().add(CurrentBrickColor);
						break;
					}
				}
				if (SaveRec.getBoundsInParent().intersects(X, Y, 1, 1)){
					SaveLevel();
				}
			}
		} else if (IsSavingName){
			double X = e.getSceneX();
			double Y = e.getSceneY();
			if (SaveRec.getBoundsInParent().intersects(X, Y, 1, 1)){
				//TODO: Save level INFO
				try{
					NumOfLevels++;
					PrintWriter writer = new PrintWriter("custom/" + FileName + ".txt", "UTF-8");
					writer.println("2 1 10 11");
					for (int i = 0; i < 16; i++) {
						for (int j = 0; j < 16; j++)
							if (j <= 15) writer.print(CustomLevel[j][i] + " ");
							else writer.print(CustomLevel[j][i]);
						if (i != 15) writer.println();
					}
					writer.close();
					//Fix it
//					PrintWriter OutFile = new PrintWriter("custom/INFO.txt", "UTF-8");
//					OutFile.println(Integer.toString(NumOfLevels));
//					System.out.println(NumOfLevels);
//					for (int i = 0; i < NumOfLevels; i++){
//						OutFile.println(MadeLevels[i].getText());
//						System.out.println(MadeLevels[i].getText());
//					}
//					OutFile.println(SaveText.getText());
//					System.out.println(SaveText.getText());
//					OutFile.close();
//					Game.LB = new LevelBuilder(Game);
				} catch (Exception d) {}
				ClearBuilder();
				Game.GAME_HANDLER.ESC();	
			}
		}
	}
}
