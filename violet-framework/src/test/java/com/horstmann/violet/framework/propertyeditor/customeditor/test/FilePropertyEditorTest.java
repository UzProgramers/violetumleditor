package com.horstmann.violet.framework.propertyeditor.customeditor.test;

import static org.junit.Assert.*;

import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.junit.Test;

import com.horstmann.violet.framework.propertyeditor.customeditor.FilePropertyEditor;
import com.horstmann.violet.product.diagram.abstracts.property.FileProperty;

import static org.junit.Assert.assertThat;

public class FilePropertyEditorTest {

	/*@Test
	public void fileUploadComponentTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		Component component = mockup.getFileUploadComponent();
		assertEquals((component != null), true);
		assertEquals(component.getParent(), null);
		JPanel panelComponent = (JPanel) component;
		Component compList[] = panelComponent.getComponents();
		assertEquals(compList.length, 3);
		assertEquals(compList[0] instanceof JLabel, true);
		assertEquals(compList[1] instanceof JButton, true);
		assertEquals(compList[2] instanceof JButton, true);

	}

	@Test
	public void openButtonActionTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		Component component = mockup.getFileUploadComponent();
		JPanel panelComponent = (JPanel) component;
		Component compList[] = panelComponent.getComponents();
		JLabel label = (JLabel) compList[0];
		// mockup.openButtonAction(label);
		// assertEquals(label.getText(),"No selected item");
	}

	@Test
	public void getTextForLabelTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/verylongnamefiletest1234.txt");
		assertEquals(mockup.getTextForLabel(f) != null, true);
		assertEquals(mockup.getTextForLabel(f).length(), 18);
		File f2 = new File("/short.txt");
		assertEquals(mockup.getTextForLabel(f2) != null, true);
		assertEquals(mockup.getTextForLabel(f2).length(), f2.getName().length());

	}

	@Test
	public void saveButtonActionTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.png");
		File[] files = new File[] { f };
		mockup.setSelectedFiles(files);
		mockup.selectedFiles = files;
		mockup.saveButtonAction();
	}

	@Test
	public void validateFileTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		mockup.setSelectedFiles(null);
		assertEquals(mockup.validateFile(), "No selected file.");
		File f = new File("/image.xd");
		File[] files = new File[] { f };
		mockup.setSelectedFiles(files);
		assertEquals(mockup.validateFile(), "Invalid file type. Only jpeg/png images are supported.");
		try {
			File bigFile = new File("a.jpg");
			FileOutputStream s = new FileOutputStream(bigFile);
			byte[] buf = new byte[250 * 1024];
			s.write(buf);
			s.flush();
			s.close();
			File[] files2 = new File[] { bigFile };
			mockup.setSelectedFiles(files2);
			assertNotEquals(mockup.validateFile(), "");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File f3 = new File("/image.jpg");
		File[] files3 = new File[] { f3 };
		mockup.setSelectedFiles(files3);
		assertEquals(mockup.validateFile(), "");
	}

	@Test
	public void validateImagePatternTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.xd");
		assertNotEquals(mockup.validateImagePattern(f), "");
		assertNotEquals(mockup.validateImagePattern(new File("test.jpg.png.xd")), "");
		assertNotEquals(mockup.validateImagePattern(new File("asasdasdda")), "");
		assertNotEquals(mockup.validateImagePattern(new File(".test")), "");
		assertNotEquals(mockup.validateImagePattern(new File(".xd.")), "");
		assertNotEquals(mockup.validateImagePattern(new File(".aasd..Xxa1123123")), "");
	}

	@Test
	public void validateFileSizeTest() {
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.xd");
		assertEquals(mockup.validateImageSize(f), "");
		try {
			File bigFile = new File("a.jpg");
			FileOutputStream s = new FileOutputStream(bigFile);
			byte[] buf = new byte[250 * 1024];
			s.write(buf);
			s.flush();
			s.close();

			assertNotEquals(mockup.validateImageSize(bigFile), "");
			s = new FileOutputStream(bigFile);
			buf = new byte[11 * 1024];
			s.write(buf);
			s.flush();
			s.close();
			assertNotEquals(mockup.validateImageSize(bigFile), "");
			
			s = new FileOutputStream(bigFile);
			buf = new byte[10 * 1024];
			s.write(buf);
			s.flush();
			s.close();
			assertEquals(mockup.validateImageSize(bigFile), "");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
	
	@Test
	public void addFileTest(){
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.xd");
		File[] files = new File[] { f };
		mockup.setSelectedFiles(files);
		assertNotEquals(mockup.addFile(),"");
	}
	
	@Test
	public void insertFileToResourcesTest(){
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/Users/user/Desktop/test.txt.png");
		try {
			f.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		assertEquals(mockup.insertFileToResources(f, null),"Could not add file. Please check if file is readable.");
		try {
			URL url = new URL("file://Users/user/Desktop/");
			assertEquals(mockup.insertFileToResources(f, url),"Could not modify File Property. Internal program error. Please try again.");

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateFilePropertyTest(){
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.xd");
		assertEquals(mockup.updateFileProperty(f),"Could not modify File Property. Internal program error. Please try again.");
		mockup.setFileProperty(new FileProperty());
		File[] files = new File[] { f };
		mockup.setSelectedFiles(files);
		assertEquals(mockup.updateFileProperty(f),"Could not modify File Property. Internal program error. Please try again.");

	}
	@Test
	public void addImageToPropertiesTest(){
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f = new File("/image.xd");
		try {
			assertEquals(mockup.addFileToImageProperties(f),"Cant load images properties path.");
			assertEquals(mockup.addFileToImageProperties(new File("/Users/user/Destkop/modem.png")),"Cant load images properties path.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	@Test
	public void copyFileUsingChannelTest(){
		FilePropertyEditorMockup mockup = new FilePropertyEditorMockup();
		File f1 = new File("/Users/user/Desktop/modemTest test.png");
		File f2 = new File("/Users/user/Desktop/modem3.png");
	
		
		try {
			assertEquals(mockup.copyFileUsingChannel(f1, f2),"File not found!");
			assertEquals(mockup.copyFileUsingChannel(new File("/Users/user/Desktop/modem.png"), f2),"Cant transfer file to destination place.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

}
