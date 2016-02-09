package com.horstmann.violet.product.diagram.deploy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import com.horstmann.violet.product.diagram.abstracts.Direction;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.IResizableNode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;

/**
 * @author Jakub Homlala A deployment node in a UML diagram. This node is almost
 *         same as package node. The only one difference is how this diagram
 *         drawing.
 */
public class DeploymentNode extends RectangularNode {

	/**
	 * 'title' of this node
	 */
	private MultiLineString name;
	/**
	 * text inside this node
	 */
	private MultiLineString content;
	/**
	 * wanted size of this node
	 */
	private Rectangle2D wantedSize;

	/**
	 * default top bounds width
	 */
	private static int DEFAULT_TOP_WIDTH = 60;

	/**
	 * default top bounds height
	 */
	private static int DEFAULT_TOP_HEIGHT = 10;

	/**
	 * default node width
	 */
	private static int DEFAULT_WIDTH = 100;

	/**
	 * default node height
	 */
	private static int DEFAULT_HEIGHT = 80;

	/**
	 * default name gap ( space between border and text)
	 */
	private static final int NAME_GAP = 5;

	/**
	 * default child gap (space between border and child element)
	 */
	private static final int CHILD_GAP = 20;

	public DeploymentNode() {
		name = new MultiLineString();
		name.setSize(MultiLineString.LARGE);
		content = new MultiLineString();
		setBorderColor(new Color(0, 0, 0));
	}

	/*
	 * (non-Javadoc) This method have fixed SOUTH AND WEST direction connection point
	 * because deployment diagram have custom border which look like 3D.
	 * 
	 * @see com.horstmann.violet.product.diagram.abstracts.node.RectangularNode#
	 * getConnectionPoint(com.horstmann.violet.product.diagram.abstracts.edge.
	 * IEdge)
	 */
	@Override
	public Point2D getConnectionPoint(IEdge e) {

		Point2D connectionPoint = super.getConnectionPoint(e);
		Direction d = e.getDirection(this);
		Direction nearestCardinalDirection = d.getNearestCardinalDirection();
		if (Direction.SOUTH.equals(nearestCardinalDirection)) {
			double x = connectionPoint.getX();
			double y = connectionPoint.getY();
			connectionPoint = new Point2D.Double(x, y + 10);

		}
		if (Direction.WEST.equals(nearestCardinalDirection)) {
			double x = connectionPoint.getX();
			double y = connectionPoint.getY();
			connectionPoint = new Point2D.Double(x-10, y);

		}
		return connectionPoint;
	}

	/**
	 * @return top rectangle bounds
	 */
	private Rectangle2D getTopRectangleBounds() {

		Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);
		globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, DEFAULT_TOP_HEIGHT));
		Point2D currentLocation = getLocation();
		double x = currentLocation.getX();
		double y = currentLocation.getY();
		double w = globalBounds.getWidth();
		double h = globalBounds.getHeight();
		globalBounds.setFrame(x, y, w, h);
		Rectangle2D snappedBounds = getGraph().getGridSticker().snap(globalBounds);
		return snappedBounds;
	}

	/**
	 * @return middle rectangle bounds
	 */
	private Rectangle2D getMiddleRectangleBounds() {
		Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);
		Rectangle2D contentsBounds = content.getBounds();
		Rectangle2D nameBounds = name.getBounds();
		globalBounds.add(nameBounds);
		globalBounds.add(contentsBounds);
		globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT));
		Rectangle2D childrenBounds = new Rectangle2D.Double(0, 0, 0, 0);
		for (INode child : getChildren()) {
			Rectangle2D childBounds = child.getBounds();
			childrenBounds.add(childBounds);
		}
		childrenBounds.setFrame(childrenBounds.getX(), childrenBounds.getY(), childrenBounds.getWidth() + CHILD_GAP,
				childrenBounds.getHeight() + CHILD_GAP);
		globalBounds.add(childrenBounds);
		Rectangle2D topBounds = getTopRectangleBounds();
		double x = topBounds.getX();
		double y = topBounds.getMaxY();
		double w = Math.max(globalBounds.getWidth(), topBounds.getWidth() + 2 * NAME_GAP);
		double h = globalBounds.getHeight();
		globalBounds.setFrame(x, y, w, h + 10);
		Rectangle2D snappedBounds = getGraph().getGridSticker().snap(globalBounds);
		return snappedBounds;
	}

	/*
	 * (non-Javadoc) Get total bounds.
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.node.INode#getBounds()
	 */
	@Override
	public Rectangle2D getBounds() {
		Rectangle2D top = getTopRectangleBounds();
		Rectangle2D mid = getMiddleRectangleBounds();
		top.add(mid);
		top.add(getTopBox(top, mid).getBounds2D());
		top.add(getRightBox(top, mid).getBounds2D());
		Rectangle2D snappedBounds = getGraph().getGridSticker().snap(top);
		snappedBounds.setRect(snappedBounds.getX(), snappedBounds.getY(), snappedBounds.getWidth(),
				snappedBounds.getHeight());
		return snappedBounds;
	}

	/**
	 * Get path2d of right shadow box
	 * 
	 * @return pathRight
	 */
	private Path2D getRightBox(Rectangle2D topBounds, Rectangle2D bottomBounds) {

		Path2D.Double pathRight = new Path2D.Double();
		pathRight.moveTo(bottomBounds.getX() + bottomBounds.getWidth(), bottomBounds.getY());
		pathRight.lineTo(pathRight.getCurrentPoint().getX() + DEFAULT_TOP_HEIGHT,
				pathRight.getCurrentPoint().getY() - DEFAULT_TOP_HEIGHT);
		pathRight.lineTo(pathRight.getCurrentPoint().getX(),
				pathRight.getCurrentPoint().getY() + bottomBounds.getHeight());
		pathRight.lineTo(pathRight.getCurrentPoint().getX() - DEFAULT_TOP_HEIGHT,
				pathRight.getCurrentPoint().getY() + DEFAULT_TOP_HEIGHT);
		pathRight.lineTo(bottomBounds.getX() + bottomBounds.getWidth(), bottomBounds.getY());
		pathRight.closePath();
		return pathRight;
	}

	/**
	 * Get path of shadow top
	 * 
	 * @return pathTop
	 */
	private Path2D getTopBox(Rectangle2D topBounds, Rectangle2D bottomBounds) {

		Path2D.Double pathTop = new Path2D.Double();
		pathTop.moveTo(bottomBounds.getX(), bottomBounds.getY());

		pathTop.lineTo(topBounds.getX() + DEFAULT_TOP_HEIGHT, topBounds.getY() + DEFAULT_TOP_HEIGHT);

		pathTop.lineTo(pathTop.getCurrentPoint().getX() + bottomBounds.getWidth(), pathTop.getCurrentPoint().getY());
		pathTop.lineTo(pathTop.getCurrentPoint().getX() - DEFAULT_TOP_HEIGHT,
				pathTop.getCurrentPoint().getY() + DEFAULT_TOP_HEIGHT);

		pathTop.lineTo(bottomBounds.getX(), bottomBounds.getY());
		pathTop.closePath();
		return pathTop;
	}

	/*
	 * (non-Javadoc) This node have specific draw method, because we need to
	 * draw some 3d border near main frame. We draw these 3d border with Line2D.
	 * We need also to add child elements at the end of drawing.
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.node.RectangularNode#draw(
	 * java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2) {
		Color oldColor = g2.getColor();

		Point2D nodeLocationOnGraph = getLocationOnGraph();
		Point2D nodeLocation = getLocation();
		Point2D g2Location = new Point2D.Double(nodeLocationOnGraph.getX() - nodeLocation.getX(),
				nodeLocationOnGraph.getY() - nodeLocation.getY());
		g2.translate(g2Location.getX(), g2Location.getY());

		super.draw(g2);
		Rectangle2D topBounds = getTopRectangleBounds();
		Rectangle2D bottomBounds = getMiddleRectangleBounds();
		Path2D topBox = getTopBox(topBounds, bottomBounds);
		Path2D rightBox = getRightBox(topBounds, bottomBounds);
		g2.setColor(getBackgroundColor());
		g2.fill(topBox);
		g2.fill(rightBox);
		g2.fill(bottomBounds);

		g2.setColor(getBorderColor());
		g2.draw(topBox);
		g2.draw(rightBox);
		g2.draw(bottomBounds);
		g2.setColor(getTextColor());
		Rectangle2D nameBounds = topBounds;

		nameBounds.setRect(nameBounds.getX() + NAME_GAP, nameBounds.getY() + 4*NAME_GAP, name.getBounds().getWidth(),
				name.getBounds().getHeight());

		name.draw(g2, topBounds);
		content.draw(g2, bottomBounds);
		g2.translate(-g2Location.getX(), -g2Location.getY());
		g2.setColor(oldColor);
		for (INode node : getChildren()) {
			fixChildLocation(topBounds, node);
			node.draw(g2);
		}
	}

	/**
	 * Ensure that child node respects the minimum gap with package borders
	 * 
	 * @param topBounds
	 * @param node
	 */
	private void fixChildLocation(Rectangle2D topBounds, INode node) {
		Point2D childLocation = node.getLocation();
		if (childLocation.getY() <= topBounds.getHeight() + CHILD_GAP) {
			node.translate(0, topBounds.getHeight() + CHILD_GAP - childLocation.getY());
		}
		if (childLocation.getX() < CHILD_GAP) {
			node.translate(CHILD_GAP - childLocation.getX(), 0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.horstmann.violet.product.diagram.abstracts.node.RectangularNode#
	 * getShape()
	 */
	@Override
	public Shape getShape() {
		GeneralPath path = new GeneralPath();
		Rectangle2D topBounds = getTopRectangleBounds();
		Rectangle2D midBounds = getMiddleRectangleBounds();
		Rectangle2D topBox = getTopBox(topBounds, midBounds).getBounds2D();
		Rectangle2D rightBox = getRightBox(topBounds, midBounds).getBounds2D();

		path.append(topBox, false);
		path.append(rightBox, false);
		path.append(topBox, false);
		path.append(rightBox, false);

		return path;
	}

	/*
	 * (non-Javadoc)
	 * @see com.horstmann.violet.product.diagram.abstracts.node.AbstractNode#addChild(com.horstmann.violet.product.diagram.abstracts.node.INode, java.awt.geom.Point2D)
	 * Add child to this node.
	 */
	@Override
	public boolean addChild(INode n, Point2D p) {
		n.setParent(this);
		n.setGraph(this.getGraph());
		n.setLocation(p);
		addChild(n, getChildren().size());
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.node.AbstractNode#clone()
	 */
	public DeploymentNode clone() {
		DeploymentNode cloned = (DeploymentNode) super.clone();
		cloned.name = name.clone();
		cloned.content = content.clone();

		return cloned;
	}

	/**
	 * Sets the name property value.
	 * 
	 * @param newValue
	 *            the class name
	 */
	public void setName(MultiLineString newValue) {
		name = newValue;
	}

	/**
	 * Gets the name property value.
	 * 
	 * @return the class name
	 */
	public MultiLineString getName() {
		return name;
	}

	/**
	 * Sets the contents property value.
	 * 
	 * @param newValue
	 *            the contents of this class
	 */
	public void setContent(MultiLineString newValue) {
		content = newValue;
	}

	/**
	 * Gets the contents property value.
	 * 
	 * @return the contents of this class
	 */
	public MultiLineString getContent() {
		return content;
	}

}
