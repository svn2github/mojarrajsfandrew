/* Generated By:JJTree: Do not edit this line. AstMinus.java */

package com.sun.faces.el.impl;

public class AstMinus extends AbstractNode
{
    public AstMinus(int id)
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