package com.jhomlala.violet.deployment.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.junit.Test;
import org.mockito.Mockito;

import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.deploy.DeploymentNode;
import com.horstmann.violet.product.diagram.deploy.LollipopNode;

import com.horstmann.violet.product.diagram.deploy.LollipopNode;

public class LollipopNodeTest {
	@Test
	public void testStartingValues() {
		LollipopNode node = new LollipopNode();
		assertEquals(node.getName().getText(), "");
		assertEquals(node.getBorderColor(), new Color(91,91,91));
		assertEquals(node.getBackgroundColor(), new Color(240,240,240));

	}
	@Test
	public void testGetBounds() {
		LollipopNode node = new LollipopNode();
		assertEquals(node.getBounds(),new Rectangle2D.Double(0,0,20,20));
	}
	
	@Test
	public void testSetName() {
		LollipopNode node = new LollipopNode();
		MultiLineString multiLineString = new MultiLineString();
		node.setName(multiLineString);
		assertEquals(node.getName(), multiLineString);
	}
	
	@Test
	public void testDraw() {
		LollipopNode node = new LollipopNode();

		Color oldColor = node.getBackgroundColor();
		Graphics2D gMock = Mockito.mock(Graphics2D.class);
		node.draw(gMock);
		
		Mockito.verify(gMock).setColor(oldColor);
		
	}
	
	
	@Test
	public void testAddChild() {
		LollipopNode node = new LollipopNode();
		LollipopNode node2 = new LollipopNode();
		Point2D point2D = new Point2D.Double(0, 0);
		assertEquals(node.addChild(node2, point2D), false);
	}
	
	
	@Test
	public void testClonedNodeValues() {
		LollipopNode node = new LollipopNode();
		node.getName().setText("Jakub");
		LollipopNode node2 = node.clone();
		assertEquals(node.getName().toString(), node2.getName().toString());
		

	}

}
