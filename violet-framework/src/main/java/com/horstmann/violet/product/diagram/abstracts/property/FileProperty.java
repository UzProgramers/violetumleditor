package com.horstmann.violet.product.diagram.abstracts.property;

import java.io.Serializable;

public class FileProperty implements Serializable,Cloneable {

	/*
	 * Field used for holding message, that all stereotype need to refresh their types list 
	 * (choice lists)
	 */
	private boolean updateRequired = false;
	/*
	 * Field used for holding element name which will be added in choice list of type
	 */
	private String updateElementName;
	

	public boolean isUpdateRequired() {
		return updateRequired;
	}

	public void setUpdateRequired(boolean updateRequired) {
		this.updateRequired = updateRequired;
	}

	public String getUpdateElementName() {
		return updateElementName;
	}

	public void setUpdateElementName(String updateElementName) {
		this.updateElementName = updateElementName;
	}

	public FileProperty clone()
	{
		FileProperty cloned = new FileProperty();
		cloned.updateRequired = this.updateRequired;
		cloned.updateElementName = this.updateElementName;
		return cloned;
	}
	
}
