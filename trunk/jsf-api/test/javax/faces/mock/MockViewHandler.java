/*
 * $Id: MockViewHandler.java,v 1.6 2003/08/22 14:32:10 eburns Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.mock;

import java.io.IOException;
import java.io.Reader;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIViewRoot;
import javax.faces.application.ViewHandler;
import javax.faces.application.StateManager;


public class MockViewHandler extends Object implements ViewHandler {

    protected StateManager stateManager = null;

    public void renderView(FacesContext context)
        throws IOException, FacesException {}

    public UIViewRoot restoreView(FacesContext context, String viewId) {
	return null;
    }

    public StateManager getStateManager() {
	if (null == stateManager) {
	    stateManager = new StateManager() {
		    protected Object getTreeStructureToSave(FacesContext context) {
			return null;
		    }
		    protected Object getComponentStateToSave(FacesContext context) {
			return null;
		    }
		    public UIViewRoot getView(FacesContext context, String viewId) throws IOException { return null; }
		    public void writeStateMarker(FacesContext context) throws IOException {}
		    public void saveView(FacesContext context, Reader content, 
			SerializedView state) {}
		    protected UIViewRoot restoreTreeStructure(FacesContext context, 
							   String viewId) {
			return null;
		    }
		    protected void restoreComponentState(FacesContext context, UIViewRoot root) throws IOException {}
		};
	}
	return stateManager;
    }

}

		    



