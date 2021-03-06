package view;

import static common.Constants.NODE_HEIGHT;
import static common.Constants.NODE_RADIUS;
import static common.Constants.NODE_WIDTH;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;

import model.entity.MapEditorModel;
import model.entity.Node;
import model.entity.Street;
import model.entity.TemporaryStreet;

public class DrawingUtil {
	
	private static final Color NODE_COLOR = 	Color.DARK_GRAY;
	private static final Color SELECTED_COLOR = Color.CYAN;
	
	public static void drawMap(Graphics2D g2d, MapEditorModel model) {
		
		// antialiasing ON
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Street street : model.getStreets()) {
			
			if(street instanceof TemporaryStreet) {
				continue;
			}
			
			// Strassen
			if(street == model.getSelectedStreet()) {
				g2d.setColor(SELECTED_COLOR);
			} else {
				g2d.setColor(street.getStreetType().getColor());
			}
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(convertStreetToLine(street));
			
			if(street.isOneWay()) {
			    ImageIcon oneWay = new ImageIcon("images/oneway.gif");
			    oneWay.paintIcon(null, g2d, street.getAtEnd().getX(), street.getAtEnd().getY());
				
			}
			
			if(street.isNoPassing()) {
				ImageIcon noPassing = new ImageIcon("images/nopassing.gif");
				noPassing.paintIcon(null, g2d, street.getMiddle().getX(), street.getMiddle().getY());
			}
			
			
			// Nodes
			g2d.setColor(NODE_COLOR);
			g2d.fill(convertNodeToEllipse(street.getStart()));
			g2d.fill(convertNodeToEllipse(street.getEnd()));
		}
		
		if(model.getSelectedNode() != null) {
			g2d.setColor(SELECTED_COLOR);
			g2d.fill(convertNodeToEllipse(model.getSelectedNode()));
		}
		
		}	
	
	private static Ellipse2D.Float convertNodeToEllipse(Node node) {
		return new Ellipse2D.Float(node.getX() - NODE_RADIUS, node.getY() - NODE_RADIUS, NODE_WIDTH, NODE_HEIGHT);
	}
	static Line2D.Float convertStreetToLine(Street s) {
		return new Line2D.Float(s.getStart().getX(), s.getStart().getY(), s.getEnd().getX(), s.getEnd().getY());
	}

}
