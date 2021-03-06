/*******************************************************************************
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package org.worldgrower.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import org.worldgrower.World;
import org.worldgrower.WorldObject;
import org.worldgrower.goal.PerceptionPropertyUtils;

public class PlayerCharacterVisionPainter {

	private final PlayerCharacterPosition playerCharacterPosition;
	private int lastCircleRadius = 0;
	private BufferedImage lastImage = null;
	
	private final Map<Integer, BufferedImage> playerVisionImages = new HashMap<>();
	
	public PlayerCharacterVisionPainter(PlayerCharacterPosition playerCharacterPosition) {
		super();
		this.playerCharacterPosition = playerCharacterPosition;
	}

	public void paintPlayerCharacterVision(Graphics worldPanelGraphics, WorldObject playerCharacter, World world, WorldPanel worldPanel) {
		int circleRadius = (PerceptionPropertyUtils.calculateRadius(playerCharacter, world)) * 48;
		int playerCharacterX = playerCharacterPosition.getScreenX(playerCharacter);
		int playerCharacterY = playerCharacterPosition.getScreenY(playerCharacter);
		
		final BufferedImage playerVisionImage;
		if (circleRadius == lastCircleRadius) {
			playerVisionImage = lastImage;
		} else {
			playerVisionImage = getPlayerVisionImage(circleRadius);
		}
		int circleLeft = playerCharacterX - circleRadius;
		int circleTop = playerCharacterY - circleRadius;
		worldPanelGraphics.drawImage(playerVisionImage, circleLeft, circleTop, null);
		
		paintUnexploredTerrain(worldPanelGraphics, worldPanel, circleRadius, circleLeft, circleTop);
		
		lastCircleRadius = circleRadius;
		lastImage = playerVisionImage;
	}

	private void paintUnexploredTerrain(Graphics worldPanelGraphics, WorldPanel worldPanel, int circleRadius, int circleLeft, int circleTop) {
		float circleDiameter = circleRadius * 2.0f;
		Shape circle = new Ellipse2D.Float(circleLeft, circleTop, circleDiameter, circleDiameter);
		Area worldPanelRectangle = new Area(new Rectangle(worldPanel.getWorldViewWidth(), worldPanel.getWorldViewHeight()));
		worldPanelRectangle.subtract(new Area(circle));
        
		worldPanelGraphics.setColor(Color.BLACK);
		((Graphics2D)worldPanelGraphics).fill(worldPanelRectangle);
	}

	private BufferedImage getPlayerVisionImage(int radius) {
		BufferedImage playerVisionImage = playerVisionImages.get(radius);
		if (playerVisionImage == null) {
			playerVisionImage = createPlayerVisionImage(radius);
			playerVisionImages.put(radius, playerVisionImage);
		}
		return playerVisionImage;
	}

	private BufferedImage createPlayerVisionImage(int circleRadius) {
		float circleDiameter = circleRadius * 2.0f;
		Shape circle = new Ellipse2D.Float(0, 0, circleDiameter, circleDiameter);
		BufferedImage image = new BufferedImage((int)circleDiameter, (int)circleDiameter, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D ga = (Graphics2D) image.createGraphics();
		
		Point2D center = new Point2D.Float(circleRadius, circleRadius);
	    Point2D focus = new Point2D.Float(circleRadius, circleRadius);
	    float[] dist = {0.0f, 0.70f, 0.95f};
	    Color transparentBlack = new Color(0, 0, 0, 0);
		Color[] colors = {transparentBlack, transparentBlack, Color.BLACK};
	    RadialGradientPaint p =
	        new RadialGradientPaint(center, circleRadius, focus,
	                                 dist, colors,
	                                 CycleMethod.NO_CYCLE);
	    
	    ga.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    ga.setPaint(p);
		
		ga.fill(circle);
		ga.setStroke(new BasicStroke(2));
		ga.draw(circle);
		
		ga.dispose();
		
		return image;
	}
}
