/*
 * $Id: TestOutputTag.java,v 1.2 2003/12/17 15:11:32 rkitain Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.webapp;


import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;


// Test UIOutput Tag
public class TestOutputTag extends UIComponentTag {

    public TestOutputTag() {
        super();
    }


    public TestOutputTag(String componentId) {
        super();
        setId(componentId);
    }

    private boolean rendersChildren = false;
    private boolean rendersChildrenSet = false;

    public void setRendersChildren(boolean rendersChildren) {
        this.rendersChildren = rendersChildren;
        this.rendersChildrenSet = true;
    }

    public void release() {
        super.release();
        this.rendersChildrenSet = false;
    }

    public String getComponentType() {
        return ("TestOutput");
    }

    public String getRendererType() {
        return ("TestRenderer");
    }


    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        if (rendersChildrenSet) {
            ((TestComponent) component).setRendersChildren(rendersChildren);
        }
    }


}
