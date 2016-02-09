package com.jhomlala.violet.deployment.test;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

import org.junit.Test;
import org.mockito.Mockito;

import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.deploy.AggregationEdge;
import com.horstmann.violet.product.diagram.deploy.DeployDiagramGraph;
import com.horstmann.violet.product.diagram.deploy.DeploymentNode;

public class TestDeploymentNode {

	@Test
	public void testStartingValues() {
		DeploymentNode node = new DeploymentNode();
		assertEquals(node.getName().getText(), "");
		assertEquals(node.getContent().getText(), "");
		assertEquals(node.getBorderColor(), new Color(0, 0, 0));
		assertEquals(node.getBackgroundColor(), new Color(255, 255, 255));

	}

	@Test
	public void testClonedNodeValues() {
		DeploymentNode node = new DeploymentNode();
		node.getName().setText("Jakub");
		node.getContent().setText("Jakub2");
		DeploymentNode node2 = node.clone();
		assertEquals(node.getName().toString(), node2.getName().toString());
		assertEquals(node.getContent().getText(), node2.getContent().toString());

	}

	@Test
	public void testGetBounds() {
		DeploymentNode node = new DeploymentNode();
		Rectangle2D rectangle = new Rectangle2D.Double(0, 0, 116, 100);
		assertEquals(rectangle, node.getBounds());
	}

	@Test
	public void testGetShape() {
		DeploymentNode node = new DeploymentNode();
		assertEquals(node.getShape().getBounds(), node.getBounds());
		assertEquals(node.getShape().getBounds2D(), node.getBounds());
	}

	@Test
	public void testAddChild() {
		DeploymentNode node = new DeploymentNode();
		DeploymentNode node2 = new DeploymentNode();
		Point2D point2D = new Point2D.Double(0, 0);
		assertEquals(node.addChild(node2, point2D), true);
	}

	@Test
	public void testSetName() {
		DeploymentNode node = new DeploymentNode();
		MultiLineString multiLineString = new MultiLineString();
		node.setName(multiLineString);
		assertEquals(node.getName(), multiLineString);
	}

	@Test
	public void testSetContent() {
		DeploymentNode node = new DeploymentNode();
		MultiLineString multiLineString = new MultiLineString();
		node.setContent(multiLineString);
		assertEquals(node.getContent(), multiLineString);
	}

	@Test
	public void testGetContent() {
		DeploymentNode node = new DeploymentNode();
		assertEquals(node.getContent().getText(), "");
		MultiLineString multiLineString = new MultiLineString();
		multiLineString.setText("Tekst");
		node.setContent(multiLineString);
		assertEquals(node.getContent().getText(), "Tekst");
	}

	@Test
	public void testDraw() {
		DeploymentNode node = new DeploymentNode();
		DeploymentNode node2 = new DeploymentNode();
		node.addChild(node2, 0);
		Color oldColor = node.getBackgroundColor();
		Graphics2D gMock = Mockito.mock(Graphics2D.class);
		node.draw(gMock);
		
		Mockito.verify(gMock).setColor(oldColor);
		
	}
	
	
	@Test
	public void testGetConnectionPoint()
	{
		DeploymentNode node = new DeploymentNode();
		AggregationEdge edge = new AggregationEdge();
		edge.setStart(node);
		edge.setEnd(node);
		Point2D point = node.getConnectionPoint(edge);
		assertEquals(point,new Point2D.Double(58,50));
	}
	

}
