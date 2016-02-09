package com.jhomlala.violet.deployment.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import org.junit.Test;
import org.mockito.Mockito;

import com.horstmann.violet.product.diagram.abstracts.property.ChoiceList;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.deploy.DeploymentNode;
import com.horstmann.violet.product.diagram.deploy.LollipopSocketNode;
import com.horstmann.violet.product.diagram.deploy.StereotypeNode;
import com.horstmann.violet.product.diagram.deploy.LollipopSocketNode;

public class LollipopSocketNodeTest {
	@Test
	public void testStartingValues() {
		LollipopSocketNode node = new LollipopSocketNode();
		assertEquals(node.getName().getText(), "");
		assertEquals(node.getBorderColor(), new Color(91,91,91));
		assertEquals(node.getBackgroundColor(), new Color(240,240,240));

	}
	@Test
	public void testGetBounds() {
		LollipopSocketNode node = new LollipopSocketNode();
		assertEquals(node.getBounds(),new Rectangle2D.Double(0,0,20,20));
	}
	
	@Test
	public void testSetName() {
		LollipopSocketNode node = new LollipopSocketNode();
		MultiLineString multiLineString = new MultiLineString();
		node.setName(multiLineString);
		assertEquals(node.getName(), multiLineString);
	}
	
	@Test
	public void testDraw() {
		LollipopSocketNode node = new LollipopSocketNode();

		Color oldColor = node.getBorderColor();
		Graphics2D gMock = Mockito.mock(Graphics2D.class);
		node.draw(gMock);
		
		Mockito.verify(gMock).setColor(oldColor);
		
	}
	
	
	@Test
	public void testAddChild() {
		LollipopSocketNode node = new LollipopSocketNode();
		LollipopSocketNode node2 = new LollipopSocketNode();
		Point2D point2D = new Point2D.Double(0, 0);
		assertEquals(node.addChild(node2, point2D), false);
	}
	
	
	@Test
	public void testClonedNodeValues() {
		LollipopSocketNode node = new LollipopSocketNode();
		node.getName().setText("Jakub");
		LollipopSocketNode node2 = node.clone();
		assertEquals(node.getName().toString(), node2.getName().toString());
		assertEquals(node.getType().getList(),node2.getType().getList());
		

	}
	
	@Test
	public void testChoiceList()
	{
		LollipopSocketNode node = new LollipopSocketNode();
		String [] headers = {"a","b","c"};
		ChoiceList list = new ChoiceList(headers);
		node.setType(list);
		
		assertEquals(node.getType(),list);
		
	}

}
