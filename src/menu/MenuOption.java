package menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import physicsEngine.Physics;
import physicsEngine.Point;;

public class MenuOption {
	String text;
	Point position;
	int size;
	Color color;
	Font font;
	
	MenuOption(String name, int x, int y, int s, Color c) {
		this.text = name;
		this.position = new Point(x,y);
		this.size = s;
		this.color = c;
		this.font = new Font(Font.DIALOG, Font.PLAIN, this.size);
	}
	
	boolean mouseOver(Point mousePos) {
		if (Physics.contains(this.position, new Point(this.position.getX()+(this.text.length()*this.size/1.6),this.position.getY()-this.size), mousePos)) {
			return true;
		} else {
			return false;
		}
	}
	
	void draw(Graphics g, Point mousePos) {
		g.setFont(this.font);
		if (this.mouseOver(mousePos)) {
			g.setColor(Color.GREEN);
		} else {
			g.setColor(this.color);
		}
	
		//g.drawLine((int)this.position.xComponent, (int)this.position.yComponent,(int)(this.position.xComponent+(this.text.length()*this.size/1.6)),(int)this.position.yComponent-this.size);
		g.drawString(this.text, (int)this.position.getX(), (int)this.position.getY());
	}
}

