/*
 * $Id: ApplyRequestValuesPhase.java,v 1.8 2003/03/12 19:51:04 rkitain Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// ApplyRequestValuesPhase.java

package com.sun.faces.lifecycle;

import com.sun.faces.context.FacesContextImpl;

import org.mozilla.util.Assert;
import org.mozilla.util.ParameterCheck;

import javax.faces.FacesException;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;

import java.io.IOException;

/**

 * <B>Lifetime And Scope</B> <P> Same lifetime and scope as
 * DefaultLifecycleImpl.
 *
 * @version $Id: ApplyRequestValuesPhase.java,v 1.8 2003/03/12 19:51:04 rkitain Exp $
 * 
 */

public class ApplyRequestValuesPhase extends Phase {
//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//

// Relationship Instance Variables

//
// Constructors and Genericializers    
//

    public ApplyRequestValuesPhase() {
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

    public int getId() {
        return Phase.APPLY_REQUEST_VALUES;
    }

    public void execute(FacesContext facesContext) throws FacesException {

        UIComponent component = 
            (UIComponent)facesContext.getTree().getRoot();
        Assert.assert_it(null != component);

        try {
            component.processDecodes(facesContext);
        } catch (IOException e) {
            facesContext.renderResponse();
        }
    }

// The testcase for this class is TestApplyRequestValuesPhase.java


} // end of class ApplyRequestValuesPhase
