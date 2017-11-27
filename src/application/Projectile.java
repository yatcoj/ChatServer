package application;
//A abstract class that allows us to create as many projectiles and use them in the same situation

abstract public class Projectile
{
	private double x;
	private double y;
	private int direction;
	private int angle;
	
	private double xL;
	
	protected Projectile(double x, double y, int direction)
	{
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.angle = 0;
	}
	
	protected double getXLast()
	{
		return xL;
	}
	
	protected void setXLast(double xL)
	{
		this.xL = xL;
	}
	
	protected int getAngle()
	{
		return angle;
	}
	
	protected void setAngle(int angle)
	{
		this.angle = angle;
	}
	
	protected double getX()
	{
		return x;
	}
	protected void setX(double x)
	{
		this.x = x;
	}
	
	protected double getY()
	{
		return y;
	}
	protected void setY(double y)
	{
		this.y = y;
	}
	
	protected int getDirection()
	{
		return direction;
	}
	abstract void movement(int key, int player);
}
