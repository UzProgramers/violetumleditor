package com.horstmann.violet.product.diagram.deploy;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.horstmann.violet.framework.plugin.IDiagramPlugin;
import com.horstmann.violet.framework.plugin.extensionpoint.Violet016FileFilterExtensionPoint;
import com.horstmann.violet.product.diagram.abstracts.IGraph;

/**
 * Describes object diagram graph type
 * 
 * @author Alexandre de Pellegrin
 * 
 */
public class DeployDiagramPlugin implements IDiagramPlugin, Violet016FileFilterExtensionPoint
{

    @Override
    public String getDescription()
    {
        return "Deployment UML diagram";
    }

    @Override
    public String getProvider()
    {
        return "Jakub Homlala";
    }

    @Override
    public String getVersion()
    {
        return "1.0.0";
    }

    @Override
    public String getName()
    {
        return this.rs.getString("menu.deploy_diagram.name");
    }

    @Override
    public String getCategory()
    {
        return this.rs.getString("menu.deploy_diagram.category");
    }

    @Override
    public String getFileExtension()
    {
        return this.rs.getString("files.deploy.extension");
    }

    @Override
    public String getFileExtensionName()
    {
        return this.rs.getString("files.deploy.name");
    }

    @Override
    public String getSampleFilePath()
    {
        return this.rs.getString("sample.file.path");
    }

    @Override
    public Class<? extends IGraph> getGraphClass()
    {
        return DeployDiagramGraph.class;
    }

    public Map<String, String> getMappingToKeepViolet016Compatibility()
    {
        Map<String, String> replaceMap = new HashMap<String, String>();
        replaceMap.put("com.horstmann.violet.DeployDiagramGraph", DeployDiagramGraph.class.getName());
       
        return replaceMap;
    }

    ResourceBundle rs = ResourceBundle.getBundle(DeployDiagramConstant.DEPLOY_DIAGRAM_STRINGS, Locale.getDefault());

}
