package application;
//Basic bullet
import java.util.HashMap;

import javafx.animation.PathTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class Bullet extends Projectile
{
	HashMap<Integer, Integer> angles1 = new HashMap<>();
	HashMap<Integer, Integer> angles2 = new HashMap<>();
	private String imageLocation = "/TankPictures/bullet.png";
	private ImageView bul;
	private int direction;
	protected Bullet(double x, double y, int direction)
	{
		super(x, y, direction);
		bul = new ImageView(new Image(imageLocation));
		bul.setX(x);
		bul.setY(y);
		this.direction = direction;
		mainMenuController.root.getChildren().add(bul);
		
		angles1.put(0, 0);
		angles1.put(1, 20);
		angles1.put(2, 40);
		angles1.put(3, 60);
		angles2.put(0, 0);
		angles2.put(1, 20);
		angles2.put(2, 40);
		angles2.put(3, 60);
	}

	@Override
	void movement(int key, int player)
	{
		if(player == 0 &&  this.direction == 1)
		{
			switch (angles1.get(key))
			{
			case 0:
				Path path = new Path();
				path.getElements().add(new MoveTo(bul.getX(),bul.getY() + 20));
				path.getElements().add(new CubicCurveTo(bul.getX() + 50, 360, bul.getX() + 400, 360, bul.getX() + 500, 400));
				PathTransition pT = new PathTransition();
				pT.setDuration(Duration.millis(1000));
				pT.setPath(path);
				pT.setNode(bul);
				pT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT.setCycleCount(1);
				this.setXLast(bul.getX() + 500);
				pT.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT.play();
				break;
			case 20:
				Path path1 = new Path();
				path1.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path1.getElements().add(new CubicCurveTo(bul.getX() + 70, 300 , bul.getX() + 400, 200, bul.getX() + 750, 400));
				PathTransition pT1 = new PathTransition();
				pT1.setDuration(Duration.millis(1000));
				pT1.setPath(path1);
				pT1.setNode(bul);
				pT1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT1.setCycleCount(1);

				this.setXLast(bul.getX() + 750);
				pT1.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT1.play();
				break;
			case 40:
				Path path2 = new Path();
				path2.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path2.getElements().add(new CubicCurveTo(bul.getX() + 50, 160, bul.getX() + 1000, 140, bul.getX() + 1050, 400));
				PathTransition pT2 = new PathTransition();
				pT2.setDuration(Duration.millis(1000));
				pT2.setPath(path2);
				pT2.setNode(bul);
				pT2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT2.setCycleCount(1);
				this.setXLast(bul.getX() + 1050);
				pT2.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT2.play();
				break;
			case 60:
				Path path3 = new Path();
				path3.getElements().add(new MoveTo(bul.getX() - 20,bul.getY()));
				path3.getElements().add(new CubicCurveTo(bul.getX() + 20, 50, bul.getX() + 1050, 80, bul.getX() + 1250, 400));
				PathTransition pT3 = new PathTransition();
				pT3.setDuration(Duration.millis(1000));
				pT3.setPath(path3);
				pT3.setNode(bul);
				pT3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT3.setCycleCount(1);

				this.setXLast(bul.getX() + 1250);
				pT3.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT3.play();
				break;
			}
		}
		if(player == 1 && this.direction == -1)
		{
			switch (angles2.get(key))
			{
			case 0:
				Path path = new Path();
				path.getElements().add(new MoveTo(bul.getX(),bul.getY() + 20));
				path.getElements().add(new CubicCurveTo(bul.getX() - 50, 360, bul.getX() - 400, 360, bul.getX() - 500, 400));
				PathTransition pT = new PathTransition();
				pT.setDuration(Duration.millis(1000));
				pT.setPath(path);
				pT.setNode(bul);
				pT.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT.setCycleCount(1);

				this.setXLast(bul.getX() -500);
				pT.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT.play();
				break;
			case 20:
				Path path1 = new Path();
				path1.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path1.getElements().add(new CubicCurveTo(bul.getX() - 70, 300 , bul.getX() - 400, 200, bul.getX() - 750, 400));
				PathTransition pT1 = new PathTransition();
				pT1.setDuration(Duration.millis(1000));
				pT1.setPath(path1);
				pT1.setNode(bul);
				pT1.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT1.setCycleCount(1);

				this.setXLast(bul.getX() -750);
				pT1.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT1.play();
				break;
			case 40:
				Path path2 = new Path();
				path2.getElements().add(new MoveTo(bul.getX(),bul.getY()));
				path2.getElements().add(new CubicCurveTo(bul.getX() - 50, 160, bul.getX() - 1000, 140, bul.getX() - 1050, 400));
				PathTransition pT2 = new PathTransition();
				pT2.setDuration(Duration.millis(1000));
				pT2.setPath(path2);
				pT2.setNode(bul);
				pT2.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT2.setCycleCount(1);

				this.setXLast(bul.getX() - 1050);
				pT2.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT2.play();
				break;
			case 60:
				Path path3 = new Path();
				path3.getElements().add(new MoveTo(bul.getX()+35,bul.getY()));
				path3.getElements().add(new CubicCurveTo(bul.getX(), 100, bul.getX()-1200, 120, bul.getX() - 1150, 400));
				PathTransition pT3 = new PathTransition();
				pT3.setDuration(Duration.millis(1000));
				pT3.setPath(path3);
				pT3.setNode(bul);
				pT3.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
				pT3.setCycleCount(1);

				this.setXLast(bul.getX() - 1150);
				pT3.setOnFinished(event -> {mainMenuController.root.getChildren().remove(bul);});
				pT3.play();
				break;
			}
		}
	}
}
