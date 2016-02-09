/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.abstracts.property;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

import com.horstmann.violet.framework.util.SerializableEnumeration;

/**
 * This class defines arrow heads of various shapes.
 */
public class ArrowHead extends SerializableEnumeration
{

    /**
     * Draws the arrowhead.
     * 
     * @param g2 the graphics context
     * @param p a point on the axis of the arrow head
     * @param q the end point of the arrow head
     */
	//public void draw (Graphics2D g2, Point2D p, Point2D q,)
    public void draw(Graphics2D g2, Point2D p, Point2D q)
    {
    	
        GeneralPath path = getPath(p, q);
        Color oldColor = g2.getColor();
        if (this != V && this != HALF_V && this != NONE)
        {
           if (this == BLACK_DIAMOND || this == BLACK_TRIANGLE)
              g2.setColor(Color.BLACK);
           else
              g2.setColor(Color.WHITE);
           g2.fill(path);
        }        
        
        g2.setColor(oldColor);
        g2.draw(path);
    }

    /**
     * Gets the path of the arrowhead
     * 
     * @param p a point on the axis of the arrow head
     * @param q the end point of the arrow head
     * @return the path
     */
    public GeneralPath getPath(Point2D p, Point2D q)
    {
        GeneralPath path = new GeneralPath();
        if (this == NONE) return path;
        final double ARROW_ANGLE = Math.PI / 6;
        final double ARROW_LENGTH = 10;

        double dx = q.getX() - p.getX();
        double dy = q.getY() - p.getY();
        double angle = Math.atan2(dy, dx);
        double x1 = q.getX() - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
        double y1 = q.getY() - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
        double x2 = q.getX() - ARROW_LENGTH * Math.cos(angle - ARROW_ANGLE);
        double y2 = q.getY() - ARROW_LENGTH * Math.sin(angle - ARROW_ANGLE);

        if (this == V)
        {
            path.moveTo((float) x1, (float) y1);
            path.lineTo((float) q.getX(), (float) q.getY());
            path.lineTo((float) x2, (float) y2);
            path.lineTo((float) q.getX(), (float) q.getY());
            path.lineTo((float) x1, (float) y1);
            path.closePath();
        }
        else if (this == TRIANGLE || this == BLACK_TRIANGLE)
        {
            path.moveTo((float) q.getX(), (float) q.getY());
            path.lineTo((float) x1, (float) y1);
            path.lineTo((float) x2, (float) y2);
            path.closePath();
        }
        else if (this == DIAMOND || this == BLACK_DIAMOND)
        {
            path.moveTo((float) q.getX(), (float) q.getY());
            path.lineTo((float) x1, (float) y1);
            double x3 = x2 - ARROW_LENGTH * Math.cos(angle + ARROW_ANGLE);
            double y3 = y2 - ARROW_LENGTH * Math.sin(angle + ARROW_ANGLE);
            path.lineTo((float) x3, (float) y3);
            path.lineTo((float) x2, (float) y2);
            path.closePath();
        }
        else if (this == LOLLIPOP)
        {
        	int arrowLength = 5;
        	double arrowAngle = Math.PI/2;
            double startingX = x2 - arrowLength * Math.cos(angle + arrowAngle) +5;
            double startingY = y2 - arrowLength * Math.sin(angle + arrowAngle);
        	int radius = 8;
        	double kappa = 0.5522847498;
        	path.moveTo(startingX, startingY-radius); 
        	path.curveTo(startingX+radius*kappa, startingY-radius, startingX+radius, startingY-radius*kappa, startingX+radius, startingY); 
        	path.curveTo(startingX+radius, startingY+radius*kappa, startingX+radius*kappa, startingY+radius, startingX, startingY+radius );
        	path.curveTo(startingX-radius*kappa, startingY+radius, startingX-radius, startingY+radius*kappa, startingX-radius, startingY);
        	path.curveTo(startingX-radius, startingY-radius*kappa, startingX-radius*kappa, startingY-radius, startingX, startingY-radius );  	
        	path.closePath();
        	
        }
        
        else if (this == LOLLIPOPWITHX)
        {
        	int arrowLength = 5;
        	double arrowAngle = Math.PI/2;
            double startingX = x2 - arrowLength * Math.cos(angle + arrowAngle) +5;
            double startingY = y2 - arrowLength * Math.sin(angle + arrowAngle);
        	int radius = 8;
        	double kappa = 0.5522847498;
        	double kappaLine = 0.7;
        	path.moveTo(startingX, startingY-radius); 
        	path.curveTo(startingX+radius*kappa, startingY-radius, startingX+radius, startingY-radius*kappa, startingX+radius, startingY); 
        	path.curveTo(startingX+radius, startingY+radius*kappa, startingX+radius*kappa, startingY+radius, startingX, startingY+radius );
        
        	//path.lineTo(startingX-radius,startingY+radius/2 );
            
        	path.closePath();
        	
        }
        
        else if (this == LOLLIPOPC)
        {
 
            double startingX = x1;
            double startingY = y1;
        	
        	path.moveTo(startingX, startingY); 
        	path.quadTo(x1-10, y1+20, x2+10, y2);
        	
        }
        return path;
    }

    /** Array head type : this head has no shape */
    public static final ArrowHead NONE = new ArrowHead();

    /** Array head type : this head is a triangle */
    public static final ArrowHead TRIANGLE = new ArrowHead();

    /** Array head type : this head is a black filled triangle */
    public static final ArrowHead BLACK_TRIANGLE = new ArrowHead();

    /** Array head type : this head is a V */
    public static final ArrowHead V = new ArrowHead();

    /** Array head type : this head is a half V */
    public static final ArrowHead HALF_V = new ArrowHead();

    /** Array head type : this head is a diamond */
    public static final ArrowHead DIAMOND = new ArrowHead();

    /** Array head type : this head is black filled diamond */
    public static final ArrowHead BLACK_DIAMOND = new ArrowHead();
    
    /** Lollipop */
    public static final ArrowHead LOLLIPOP = new ArrowHead();
    
    /** Lollipop */
    public static final ArrowHead LOLLIPOPSOCKET = new ArrowHead();
    
    public static final ArrowHead LOLLIPOPWITHX = new ArrowHead();
    
    public static final ArrowHead LOLLIPOPC = new ArrowHead();

    /** Internal Java UID */
    private static final long serialVersionUID = -3824887997763775890L;

}
