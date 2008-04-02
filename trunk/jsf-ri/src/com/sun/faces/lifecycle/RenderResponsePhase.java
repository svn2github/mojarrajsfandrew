/*
 * $Id: RenderResponsePhase.java,v 1.10 2003/10/30 22:15:33 jvisvanathan Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// RenderResponsePhase.java

package com.sun.faces.lifecycle;


import org.mozilla.util.Assert;

import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.event.PhaseId;
import javax.faces.context.FacesContext;

import java.io.IOException;


/**

 * <B>Lifetime And Scope</B> <P> Same lifetime and scope as
 * DefaultLifecycleImpl.
 *
 * @version $Id: RenderResponsePhase.java,v 1.10 2003/10/30 22:15:33 jvisvanathan Exp $
 *
 */

public class RenderResponsePhase extends Phase {
//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Genericializers    
//

public RenderResponsePhase() {
    super();
}

//
// Class methods
//

//
// General Methods
//

//
// Methods from Phase
//

public PhaseId getId() {
    return PhaseId.RENDER_RESPONSE;
}

public void execute(FacesContext facesContext) throws FacesException
{
    try { 
	facesContext.getApplication().getViewHandler().
                renderView(facesContext, facesContext.getViewRoot()); 
    } catch (IOException e) { 
	throw new FacesException(e.getMessage(), e);
    }
}



// The testcase for this class is TestRenderResponsePhase.java


} // end of class RenderResponsePhase
