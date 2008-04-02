/*
 * $Id: TestInputTag.java,v 1.4 2004/02/26 20:32:14 eburns Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.webapp;


import javax.faces.component.UIComponent;
import javax.faces.el.ValueBinding;


// Test UIInput Tag
public class TestInputTag extends UIComponentTag {

    public TestInputTag() {
        super();
    }


    public TestInputTag(String componentId) {
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
        return ("TestInput");
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
