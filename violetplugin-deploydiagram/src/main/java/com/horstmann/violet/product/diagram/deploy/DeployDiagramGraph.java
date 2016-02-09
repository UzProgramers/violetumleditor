/*
 Violet - A program for editing UML diagrams.

 Copyright (C) 2007 Cay S. Horstmann (http://horstmann.com)
 Alexandre de Pellegrin (http://alexdp.free.fr);

 This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation; either version 2 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program; if not, write to the Free Software
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.horstmann.violet.product.diagram.deploy;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import com.horstmann.violet.product.diagram.abstracts.AbstractGraph;
import com.horstmann.violet.product.diagram.abstracts.edge.IEdge;
import com.horstmann.violet.product.diagram.abstracts.node.INode;
import com.horstmann.violet.product.diagram.abstracts.property.MultiLineString;
import com.horstmann.violet.product.diagram.common.NoteEdge;
import com.horstmann.violet.product.diagram.common.NoteNode;

/**
 * An UML-style object diagram that shows object references.
 */
public class DeployDiagramGraph extends AbstractGraph
{
    
    @Override
    public boolean addNode(INode newNode, Point2D p)
    {
        INode foundNode = findNode(p);
 
        return super.addNode(newNode, p);
    }
    
    public List<INode> getNodePrototypes()
    {
        return NODE_PROTOTYPES;
    }

    public List<IEdge> getEdgePrototypes()
    {
        return EDGE_PROTOTYPES;
    }

    private static final List<INode> NODE_PROTOTYPES = new ArrayList<INode>();

    private static final List<IEdge> EDGE_PROTOTYPES = new ArrayList<IEdge>();

    static
    {
        ResourceBundle rs = ResourceBundle.getBundle(DeployDiagramConstant.DEPLOY_DIAGRAM_STRINGS, Locale.getDefault());

        NoteNode node2 = new NoteNode();
        node2.setToolTip(rs.getString("node.note"));
        NODE_PROTOTYPES.add(node2);
        
        CompositionEdge compositionEdge = new CompositionEdge();
        compositionEdge.setToolTip(rs.getString("edge.composition"));
        EDGE_PROTOTYPES.add(compositionEdge);
        
        DeploymentNode deploymentNode = new DeploymentNode();
        deploymentNode.setToolTip(rs.getString("node.deployment"));
        NODE_PROTOTYPES.add(deploymentNode);

        DependencyEdge dependencyEdge = new DependencyEdge();
        dependencyEdge.setToolTip(rs.getString("edge.dependency"));
        EDGE_PROTOTYPES.add(dependencyEdge);
        
        AggregationEdge aggregationEdge = new AggregationEdge();
        aggregationEdge.setToolTip(rs.getString("edge.aggregation"));
        EDGE_PROTOTYPES.add(aggregationEdge);
        
        GeneralizationEdge generalizationEdge = new GeneralizationEdge();
        generalizationEdge.setToolTip(rs.getString("edge.generalization"));
        EDGE_PROTOTYPES.add(generalizationEdge);
        
        LollipopNode lollipopNode = new LollipopNode();
        lollipopNode.setToolTip(rs.getString("node.lollipopi"));
        NODE_PROTOTYPES.add(lollipopNode);
        
        LollipopSocketNode lollipopSocketNode = new LollipopSocketNode();
        lollipopSocketNode.setToolTip(rs.getString("node.lollipops"));
        NODE_PROTOTYPES.add(lollipopSocketNode);

        NoteEdge noteEdge = new NoteEdge();
        noteEdge.setToolTip(rs.getString("edge.note"));
        EDGE_PROTOTYPES.add(noteEdge);
        
        LollipopEdge lollipopEdge = new LollipopEdge();
        lollipopEdge.setToolTip(rs.getString("edge.lollipop"));
        EDGE_PROTOTYPES.add(lollipopEdge);
        
        StereotypeNode stereotypeNode = new StereotypeNode();
        stereotypeNode.setToolTip(rs.getString("node.stereotype"));
        NODE_PROTOTYPES.add(stereotypeNode);
        
        PackageNode packageNode = new PackageNode();
        packageNode.setToolTip(rs.getString("node.package"));
        NODE_PROTOTYPES.add(packageNode);
    }

}
