package com.jhomlala.violet.deployment.test;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.junit.Test;
import org.mockito.Mockito;

import com.horstmann.violet.product.diagram.abstracts.property.ChoiceList;
import com.horstmann.violet.product.diagram.abstracts.property.FileProperty;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.deploy.DeploymentNode;
import com.horstmann.violet.product.diagram.deploy.StereotypeNode;

public class TestStereotypeNode {
	@Test
	public void testStartingValues() {
		StereotypeNode node = new StereotypeNode();
		assertEquals(node.getName().getText(), "");
		assertEquals(node.getBorderColor(), new Color(0, 0, 0));
		assertEquals(node.getBackgroundColor(), new Color(255, 255, 255));

	}
	@Test
	public void testChoiceList()
	{
		StereotypeNode node = new StereotypeNode();
		String [] headers = {"a","b","c"};
		ChoiceList list = new ChoiceList(headers);
		node.setType(list);
		
		assertEquals(node.getType(),list);
		
	}
	
	@Test
	public void testDraw()
	{
		StereotypeNode node = new StereotypeNode();
		Color oldColor = node.getBackgroundColor();
		Graphics2D gMock = Mockito.mock(Graphics2D.class);
		node.draw(gMock);
		Mockito.verify(gMock).setColor(oldColor);
		
	}
	
	@Test
	public void testClonedNodeValues() {
		StereotypeNode node = new StereotypeNode();
		node.getName().setText("Jakub");
		StereotypeNode node2 = node.clone();
		assertEquals(node.getName().toString(), node2.getName().toString());
		assertEquals(node.getType().getList(),node2.getType().getList());
		

	}
	
	@Test
	public void testSetName() {
		StereotypeNode node = new StereotypeNode();
		MultiLineString multiLineString = new MultiLineString();
		node.setName(multiLineString);
		assertEquals(node.getName(), multiLineString);
	}
/*	
	@Test
	public void testInitTypeList(){
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		assertEquals(mockup.getType().getList().length,13);
		mockup.initTypeList();
		assertEquals(mockup.getType().getList().length,13);
		String type = mockup.getType().getSelectedItem();
		mockup.initTypeList();
		assertEquals(type,mockup.getType().getSelectedItem());
		mockup.setType(null);
		mockup.initTypeList();
		assertEquals(mockup.getType().getList().length,13);

	}
	
	@Test
	public void testGetCurrentTypeImageURL(){
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		mockup.setType(null);
		assertEquals(mockup.getCurrentTypeImageUrl(),"");
		mockup.initTypeList();
		assertEquals(mockup.getCurrentTypeImageUrl(),"img/modem.png");
		mockup.getType().setSelectedItem("modem");
		assertEquals(mockup.getCurrentTypeImageUrl(),"img/modem.png");
	}
	
	@Test
	public void testGetIcons(){
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		assertEquals(mockup.getIcons().size() > 0 ,true);

		
	}
	
	@Test
	public void testGetTopRectangleBounds() {
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		Rectangle2D rec = mockup.getTopRectangleBounds();
		Rectangle2D rec2 =new Rectangle2D.Double(0,0,100,10);
		assertEquals(rec,rec2);
		
	}
	
	@Test
	public void testGetMiddleRectangleBounds() {
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		Rectangle2D rec = mockup.getMiddleRectangleBounds();
		Rectangle2D rec2 =new Rectangle2D.Double(0,10,100,20);
		assertEquals(rec,rec2);
		
	}
	@Test
	public void testUpdateCustomTypesInStereotypeNodes(){
		StereotypeNodeMockup mockup = new StereotypeNodeMockup();
		StereotypeNodeMockup mockup2 = (StereotypeNodeMockup) mockup.clone();
		StereotypeNodeMockup mockup3 = (StereotypeNodeMockup) mockup.clone();
		
		
		FileProperty property = new FileProperty();
		property.setUpdateElementName("server");
		property.setUpdateRequired(true);
		mockup.setCustomType(property);
		mockup.updateCustomTypesInStereotypeNodes();
		assertEquals(mockup.getType().getSelectedItem(),"server");
		String [] list = mockup2.getType().getList();
		//for (String str: list){
		//	System.out.println(str);
		//}
		
	}

	
	
	@Test
	public void testDrawImageInNode()
	{
		StereotypeNodeMockup node = new StereotypeNodeMockup();
		Color oldColor = node.getBackgroundColor();
		Graphics2D gMock = Mockito.mock(Graphics2D.class);
		Rectangle2D rec2 =new Rectangle2D.Double(0,10,100,20);
		node.drawImageInNode(gMock, rec2);
		Mockito.verify(gMock);
		
	}
	*/

}
