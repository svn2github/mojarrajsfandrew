/* Generated By:JJTree: Do not edit this line. AstNot.java */

package com.sun.faces.el.impl;

public class AstNot extends AbstractNode
{
    public AstNot(int id)
    {
        super(id);
    }

    /** Accept the visitor. * */
    public Object jjtAccept(JsfParserVisitor visitor, Object data)
            throws javax.faces.el.EvaluationException
    {
        return visitor.visit(this, data);
    }
}