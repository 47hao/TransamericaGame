import java.awt.Color;

public class City {
	private Color color;
	private String name;
	private Position pos;
	
	public static final String[] CITYNAMES = {
			"America", "Brazil", "Czechslovakia"
	};
	
	public City(Position p, String n, Color c)
	{
		pos = p;
		name = n;
		color = c;
	}
	
	public Position getPos()
	{
		return pos;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	
	
}
