package com.horstmann.violet.product.diagram.deploy;

import com.horstmann.violet.product.diagram.abstracts.edge.SegmentedLineEdge;
import com.horstmann.violet.product.diagram.abstracts.property.ArrowHead;
import com.horstmann.violet.product.diagram.abstracts.property.LineStyle;

public class GeneralizationEdge extends SegmentedLineEdge
{
    
    @Override
    public ArrowHead getStartArrowHead()
    {
        return ArrowHead.NONE;
    }

    @Override
    public ArrowHead getEndArrowHead()
    {
        return ArrowHead.TRIANGLE;
    }

    @Override
    public LineStyle getLineStyle()
    {
        return LineStyle.SOLID;
    }
    
}
