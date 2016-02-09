package com.horstmann.violet.product.diagram.deploy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;

import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.node.RectangularNode;
import com.horstmann.violet.product.diagram.abstracts.property.ChoiceList;
import com.horstmann.violet.product.diagram.abstracts.property.FileProperty;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.common.PointNode;

/**
 * @author Jakub Homlala This class represents sterotype node in deployment
 *         diagram
 */
public class StereotypeNode extends RectangularNode {
	/**
	 * Text label inside node
	 */
	private MultiLineString name;
	/**
	 * Image inside node
	 */
	private ChoiceList type;
	/**
	 * HashMap which contains image name and url
	 */
	private HashMap<String, String> iconsList;

	/**
	 * default compartment height , used for getting middle bounds
	 */
	private static int DEFAULT_COMPARTMENT_HEIGHT = 10;
	/**
	 * default node width
	 */
	private static int DEFAULT_WIDTH = 100;
	/**
	 * default node heigjt
	 */
	private static int DEFAULT_HEIGHT = 60;

	/**
	 * URI to .properties file which contains Icons settings
	 */
	private static String iconProperties = "properties/Icons.properties";

	/*
	 * This variable holds references too all instances of SterotypeNode We need
	 * them to recreate choice list when we add additional custom type
	 */
	private static List<StereotypeNode> instances = new ArrayList<StereotypeNode>();

	/*
	 * This variable is used for creating custom type
	 */
	private FileProperty customType;

	public StereotypeNode() {
		name = new MultiLineString();
		name.setSize(MultiLineString.LARGE);
		customType = new FileProperty();
		setBorderColor(new Color(0, 0, 0));
		initTypeList();
		instances.add(this);
	}

	/**
	 * This getter is used for violet framework in order to make type property
	 * visible in property editor. Get choice type list used in node.
	 * 
	 * @return type choice list
	 */
	public ChoiceList getType() {
		return type;
	}

	/**
	 * This setter is used for violet framework in order to make type property
	 * visible in property editor. Set node type choice list.
	 * 
	 * @param type
	 *            -
	 */
	public void setType(ChoiceList type) {
		this.type = type;
	}

	/*
	 * Getter for violet framework
	 */
	public FileProperty getCustomType() {
		return customType;
	}

	/*
	 * Setter for violet framework
	 */
	public void setCustomType(FileProperty customType) {
		this.customType = customType;
	}

	/**
	 * This method is used for populating choice list with values from iconsList
	 * hash map
	 */
	private void initTypeList() {
		boolean isSavedState = false;
		ChoiceList savedState = null;
		if (type != null) {
			isSavedState = true;
			savedState = type;
		}

		iconsList = getIcons();
		List<String> iconNames = new ArrayList<String>();
		if (iconsList.size() > 0) {
			Set<String> iconsListKeys = iconsList.keySet();
			for (String iconsListKey : iconsListKeys) {
				iconNames.add(iconsListKey);
			}
		}

		String iconNamesArray[] = new String[iconNames.size()];
		iconNames.toArray(iconNamesArray);
		type = new ChoiceList(iconNamesArray);
		if (isSavedState) {
			type.setSelectedItem(savedState.getSelectedItem());
		}

	}

	/**
	 * This method get current selected type and search for image url in icon
	 * list hash map
	 * 
	 * @return image url
	 */
	private String getCurrentTypeImageUrl() {
		String imageUrl = "";
		if (type != null) {
			String selectedItem = type.getSelectedItem();
			if (selectedItem != null) {
				if (iconsList.containsKey(selectedItem)) {
					imageUrl = "img/" + (String) iconsList.get(selectedItem);
				}
			}
		}
		return imageUrl;
	}

	/**
	 * This method is used for creating hash map based on Icons.properties file.
	 * It returns hashMap: key is image(stereotype) name, and value is image
	 * url.
	 * 
	 * @return icon hash map
	 */
	private HashMap<String, String> getIcons() {
		HashMap<String, String> iconsHashMap = new HashMap<String, String>();
		try {
			Properties properties = new Properties();
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(iconProperties);
			if (is != null) {

				properties.load(is);
				Set<Object> iconNames = properties.keySet();
				for (Object object : iconNames) {
					String iconName = (String) object;
					String iconImage = properties.getProperty(iconName);
					iconsHashMap.put(iconName, iconImage);
				}

				is.close();
			}

		} catch (IOException ioException) {
			ioException.printStackTrace();
			return iconsHashMap;
		}

		return iconsHashMap;

	}

	/**
	 * @return bounds of top rectangle
	 */
	private Rectangle2D getTopRectangleBounds() {
		Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);

		globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, 10));
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
	 * @return bounds of middle rectangle
	 */
	private Rectangle2D getMiddleRectangleBounds() {
		Rectangle2D globalBounds = new Rectangle2D.Double(0, 0, 0, 0);
		Rectangle2D nameBounds = name.getBounds();
		globalBounds.add(nameBounds);

		globalBounds.add(new Rectangle2D.Double(0, 0, DEFAULT_WIDTH, 2 * DEFAULT_COMPARTMENT_HEIGHT));

		Rectangle2D topBounds = getTopRectangleBounds();
		double x = topBounds.getX();
		double y = topBounds.getMaxY();
		double w = globalBounds.getWidth();
		double h = globalBounds.getHeight();
		globalBounds.setFrame(x, y, w, h);
		Rectangle2D snappedBounds = getGraph().getGridSticker().snap(globalBounds);
		return snappedBounds;
	}
    //
	/*
	 * (non-Javadoc) This method calculate bounds based on bounds of top and
	 * middle rectangle.
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.node.INode#getBounds()
	 */
	@Override
	public Rectangle2D getBounds() {
		Rectangle2D top = getTopRectangleBounds();
		Rectangle2D mid = getMiddleRectangleBounds();
		top.add(mid);
		Rectangle2D snappedBounds = getGraph().getGridSticker().snap(top);
		return snappedBounds;
	}

	/*
	 * This method is used for updating image list in all stereotype nodes
	 */
	private void updateCustomTypesInStereotypeNodes(){
		for (StereotypeNode instance : instances) {
			instance.customType.setUpdateRequired(false);
			instance.initTypeList();
			if (this.getId().equals(instance.getId())) {
				instance.type.setSelectedItem(customType.getUpdateElementName());
			}
		}
	}
	
	/*
	 * This method is used for drawing image inside stereotype node
	 */
	private void drawImageInNode(Graphics2D g2,Rectangle2D currentBounds){
		try {
			int imageHeight = DEFAULT_HEIGHT / 3;
			int imageWidth = imageHeight;
			int imageX = (int) (currentBounds.getBounds().getX() + currentBounds.getBounds().getWidth() - imageWidth
					- 5);
			int imageY = (int) currentBounds.getBounds().getY() + 5;
			String imageUrl = getCurrentTypeImageUrl();
			if (imageUrl != null && imageUrl.length() > 0) {
				URL url = ClassLoader.getSystemResource(imageUrl);
				if (url != null) {
					final BufferedImage image = ImageIO.read(url);
					if (image != null)
						g2.drawImage(image, imageX, imageY, imageWidth, imageHeight, null);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/*
	 * (non-Javadoc) This method is used for drawing stereotype node. Important
	 * thing is to set correct location of element node ( this node can be
	 * inside for example deployment node).
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.node.RectangularNode#draw(
	 * java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2) {

		if (customType.isUpdateRequired()) {
			updateCustomTypesInStereotypeNodes();	
		}
		
		Color oldColor = g2.getColor();

		Point2D nodeLocationOnGraph = getLocationOnGraph();
		Point2D nodeLocation = getLocation();
		Point2D g2Location = new Point2D.Double(nodeLocationOnGraph.getX() - nodeLocation.getX(),
				nodeLocationOnGraph.getY() - nodeLocation.getY());
		g2.translate(g2Location.getX(), g2Location.getY());
		super.draw(g2);
		Rectangle2D currentBounds = getBounds();
		Rectangle2D topBounds = getTopRectangleBounds();

		if (topBounds.getWidth() < currentBounds.getWidth()) {

			topBounds.setRect(topBounds.getX(), topBounds.getY(), currentBounds.getWidth(), topBounds.getHeight());
		}
		g2.setColor(getBackgroundColor());
		g2.fill(currentBounds);
		g2.setColor(new Color(0, 0, 0));
		g2.draw(currentBounds);

		drawImageInNode(g2,currentBounds);

		g2.setColor(getTextColor());
		name.draw(g2, this.getMiddleRectangleBounds());
		g2.translate(-g2Location.getX(), -g2Location.getY());
		g2.setColor(oldColor);

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.horstmann.violet.product.diagram.abstracts.RectangularNode#clone()
	 */
	public StereotypeNode clone() {
		StereotypeNode cloned = (StereotypeNode) super.clone();
		cloned.name = (MultiLineString) name.clone();
		cloned.type = type.clone();
		cloned.customType = customType.clone();
		instances.add(cloned);
		return cloned;
	}

}
