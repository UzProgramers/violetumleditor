package com.horstmann.violet.framework.propertyeditor.customeditor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyEditorSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.MimetypesFileTypeMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.horstmann.violet.product.diagram.abstracts.property.FileProperty;

public class FilePropertyEditor extends PropertyEditorSupport {

	/*
	 * Fileproperty is element which will hold our changes
	 */
	// @Test private->private
	private FileProperty fileProperty;
	/*
	 * Array of items selected
	 */
	private File[] selectedFiles;

	/*
	 * Pattern for regex for images
	 */
	private final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|png))$)";

	/*
	 * Path to properties which hold all images
	 */
	private final String ICON_PROPERTIES_PATH = "properties/Icons.properties";

	/*
	 * Maximal image size in kilobytes
	 */
	private final int MAX_IMAGE_SIZE = 10;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyEditorSupport#supportsCustomEditor() This method
	 * tell violet framework, that this editor is custom and we can setup like
	 * we want.
	 */
	public boolean supportsCustomEditor() {
		return true;
	}

	/*
	 * Constructor, we get here value of element which we want to edit
	 */
	public FilePropertyEditor() {
		this.fileProperty = (FileProperty) getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyEditorSupport#getCustomEditor() This method
	 * return custom editor which will be showed in properties window
	 */
	public Component getCustomEditor() {

		final JPanel panel = new JPanel();
		panel.add(getFileUploadComponent());
		return panel;
	}

	/*
	 * This method return component which will be used in our custom editor. We
	 * create here file upload view. When user click open button - he will be
	 * prompted about chosing file. After selecting file user need to click Save
	 * to save file to our system.
	 */
	private Component getFileUploadComponent() {
		JPanel panel = new JPanel();
		final JLabel label = new JLabel();
		label.setText("No selected item");

		JButton openButton = new JButton("Open");
		openButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				openButtonAction(label);
			}
		});
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				saveButtonAction();
			}

		});
		panel.add(label);
		panel.add(openButton);
		panel.add(saveButton);
		return panel;
	}

	/*
	 * This method is called when open button is clicked
	 */
	private void openButtonAction(JLabel label) {
		if (label != null) {
			JFileChooser chooser = new JFileChooser();
			chooser.setMultiSelectionEnabled(true);
			int option = chooser.showOpenDialog(new JPanel());
			if (option == JFileChooser.APPROVE_OPTION) {
				selectedFiles = chooser.getSelectedFiles();
				if (selectedFiles[0] != null && selectedFiles[0].getName().length() > 0) {
					label.setText(getTextForLabel(selectedFiles[0]));
				} else {
					label.setText("No selected item");
				}
			} else {
				label.setText("No selected item");
			}
		}

	}

	/*
	 * This method is used for cropping long file names 
	 */
	private String getTextForLabel(File file) {
		String textForLabel = "";
		if (file != null) {
			String fileText = file.getName();
			if (fileText != null) {
				if (fileText.length() > 15)
					textForLabel = fileText.substring(0, 15) + "...";
				else
					textForLabel = fileText;
			}

		}

		return textForLabel;
	}

	/*
	 * This method is called when save button is clicked
	 */
	private void saveButtonAction() {

		String error = validateFile();
		if (error != null && error.length() > 0) {
			JOptionPane.showMessageDialog(null, error);
		} else {
			error = addFile();
			if (error != null && error.length() > 0)
				JOptionPane.showMessageDialog(null, error);
			else
				JOptionPane.showMessageDialog(null,
						"Sucessfully added file! Please restart property view in order to see changes.");

		}

	}

	/*
	 * This method is called to validate our file. We accepting only jpg/png
	 * files which max size is 10kbs. It will return String with error if there
	 * something goes wrong.
	 */
	private String validateFile() {
		String error = "";
		if (selectedFiles != null && selectedFiles.length > 0 && selectedFiles[0] != null) {
			File selectedFile = selectedFiles[0];
			error = validateImagePattern(selectedFile);
			if (error != null && error.length() == 0)
				error = validateImageSize(selectedFile);
		} else {
			error = "No selected file.";
		}

		return error;
	}

	/*
	 * This method validates file based on image type (file should be png or
	 * jpg)
	 */
	private String validateImagePattern(File selectedFile) {
		String error = "";
		if (selectedFile != null) {
			Pattern pattern = Pattern.compile(IMAGE_PATTERN);
			Matcher matcher = pattern.matcher(selectedFile.getName());
			if (!matcher.matches()) {
				error = "Invalid file type. Only jpeg/png images are supported.";
			}
		} else {
			error = "Invalid file. Please try again!";
		}

		return error;
	}

	private String validateImageSize(File selectedFile) {
		String error = "";
		if (selectedFile != null) {
			double bytes = selectedFile.length();

			if (bytes < 0 || bytes / 1024 > MAX_IMAGE_SIZE) {
				error = "Invalid file size. Maximum image size is " + MAX_IMAGE_SIZE + "Kb";
			}
		} else {
			error = "Invalid file. Please try again!";
		}

		return error;
	}

	/*
	 * This method is called after file validation. We add file to resources and
	 * put additional row in properties.
	 */
	private String addFile() {
		String error = "";

		if (selectedFiles.length > 0 && selectedFiles[0] != null) {
			File selectedFile = selectedFiles[0];
			URL url = ClassLoader.getSystemResource("img");
			if (url == null) {
				error = "Could not add file! Images folder is not present.";
			} else {
				error = insertFileToResources(selectedFile, url);
			}
		}

		return error;
	}

	/*
	 * This method is used for insterting file to resources and adding file to
	 * properties
	 */
	private String insertFileToResources(File selectedFile, URL url) {
		String error = "";
		try {
			if (selectedFile != null && url != null) {
				File destination = new File(url.getPath() + "/" + selectedFile.getName());
				error = copyFileUsingChannel(selectedFile, destination);
				if (error != null)
					error = addFileToImageProperties(selectedFile);
				if (error != null)
					error = updateFileProperty(selectedFile);
			} else {
				error = "Could not add file. Please check if file is readable.";
			}
		} catch (IOException exc) {
			exc.printStackTrace();
			error = "Could not add file. Please try again.";
		}

		return error;

	}

	/*
	 * This method is used for updating file property values. After changing
	 * these values, node will know that update is required.
	 */
	private String updateFileProperty(File selectedFile) {
		String error = "";
		fileProperty = (FileProperty) getValue();
		if (fileProperty != null) {
			String[] selectedFileNameArray = selectedFile.getName().split("\\.");
			if (selectedFileNameArray.length > 0) {
				fileProperty.setUpdateElementName(selectedFileNameArray[0]);
				fileProperty.setUpdateRequired(true);
			} else
				error = "Could not modify File Property. Internal program error. Please try again.";
		} else {
			error = "Could not modify File Property. Internal program error. Please try again.";
		}
		return error;
	}

	/*
	 * This method adds row to properties with loaded image details
	 */
	private String addFileToImageProperties(File selectedFile) throws IOException {
		InputStream inputStream = null;
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			inputStream = classloader.getResourceAsStream(ICON_PROPERTIES_PATH);
			if (inputStream == null)
				return "Cant load images properties path.";
			else {
				URL url = ClassLoader.getSystemResource(ICON_PROPERTIES_PATH);
				
				if (url == null)
					return "Cant load images properties path.";
				else {
					Properties properties = new Properties();
					properties.load(inputStream);
					String[] selectedFileNameArray = selectedFile.getName().split("\\.");
					if (selectedFileNameArray != null && selectedFileNameArray.length > 0) {
						properties.put(selectedFileNameArray[0], selectedFile.getName());
						File f = new File(url.getPath());
						OutputStream out = new FileOutputStream(f);
						properties.store(out, "");
						out.close();
						return "";
					} else
						return "Invalid image name";
				}
			}
		} catch (IOException exc) {
			exc.printStackTrace();
			return "Unexpected error. Please try again.";
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

	}

	/*
	 * This method is used for copying file from source to destination
	 */
	private String copyFileUsingChannel(File source, File dest) throws IOException {
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		FileInputStream fileInputStream = null;
		FileOutputStream fileOutputStream = null;
		try {
			fileInputStream = new FileInputStream(source);
			sourceChannel = fileInputStream.getChannel();
			fileOutputStream = new FileOutputStream(dest);
			destChannel = fileOutputStream.getChannel();
			if (sourceChannel != null && destChannel != null) {
				long transferedBytes = destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
				if (transferedBytes > 0)
					return "";
				else
					return "Cant transfer file to destination place.";
			} else
				return "Invalid files for transfer.";
		} catch (FileNotFoundException exc1) {

			return "File not found!";
		} catch (IOException exc2) {

			return "Problem with reading/writing file!";
		}

		finally {
			if (sourceChannel != null)
				sourceChannel.close();
			if (destChannel != null)
				destChannel.close();
			if (fileInputStream != null)
				fileInputStream.close();
			if (fileOutputStream != null)
				fileOutputStream.close();
		}

	}
	
	
	/*
	 * These methods was used as helper methods in tests
	private void setSelectedFiles(File[] files) {
		this.selectedFiles = files;
	}

	private void setFileProperty(FileProperty prop) {
		this.fileProperty = prop;

	}
	*/
	
	
}
