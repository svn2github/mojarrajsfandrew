/*
 * $Id: TestPhase.java,v 1.5 2003/08/22 16:50:39 eburns Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// TestPhase.java

package com.sun.faces.lifecycle;

import org.apache.cactus.WebRequest;

import org.mozilla.util.Assert;
import org.mozilla.util.ParameterCheck;

import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.component.UIInput;
import javax.faces.component.UIPage;
import javax.faces.component.base.UIFormBase;
import javax.faces.component.base.UIInputBase;
import javax.faces.component.base.UIPageBase;

import java.util.Iterator;

import com.sun.faces.ServletFacesTestCase;
import com.sun.faces.CompareFiles;
import com.sun.faces.FileOutputResponseWrapper;
import com.sun.faces.lifecycle.Phase;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;


/**
 *
 *  <B>TestPhase</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestPhase.java,v 1.5 2003/08/22 16:50:39 eburns Exp $
 * 
 * @see	Blah
 * @see	Bloo
 *
 */

public class TestPhase extends ServletFacesTestCase
{
//
// Protected Constants
//

public static final String TEST_URI = "/components.jsp";

//
// Class Variables
//

//
// Instance Variables
//

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public TestPhase() {super("TestPhase");}
    public TestPhase(String name) {super(name);}

//
// Class methods
//

//
// General Methods
//

public void beginExecute(WebRequest theRequest)
{
    theRequest.setURL("localhost:8080", null, null, TEST_URI, null);
    theRequest.addParameter("userName", "jerry");
}

public void testExecute()
{

    Phase restoreView = new RestoreViewPhase();
    try {
        restoreView.execute(getFacesContext());
    }
    catch (Throwable e) {
        e.printStackTrace();
        assertTrue(false);
    }

    assertTrue(!(getFacesContext().getRenderResponse()) &&
        !(getFacesContext().getResponseComplete()));
    assertTrue(null != getFacesContext().getViewRoot());

    // 2. Add components to tree
    //
    UIComponent root = getFacesContext().getViewRoot();
    UIForm basicForm = new UIFormBase();
    basicForm.setId("basicForm");
    UIInput userName = new UIInputBase();
    userName.setId("userName");
    root.getChildren().add(basicForm);
    basicForm.getChildren().add(userName);
    UIPage page = new UIPageBase();
    page.setViewId("root");    
    getFacesContext().setViewRoot(page);

    Phase applyValues = new ApplyRequestValuesPhase();

    try {
	applyValues.execute(getFacesContext());
    }
    catch (Throwable e) {
	System.out.println("Throwable: " + e.getMessage());
	e.printStackTrace();
	assertTrue(false);
    }
    assertTrue(!(getFacesContext().getRenderResponse()) &&
        !(getFacesContext().getResponseComplete()));
}

} // end of class TestPhase
