/*
 * $Id: TestApplicationFactoryImpl.java,v 1.6 2004/05/07 13:53:20 eburns Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// TestApplicationFactoryImpl.java

package com.sun.faces.application;

import com.sun.faces.JspFacesTestCase;

import javax.faces.application.Application;

/**
 * <B>TestApplicationFactoryImpl</B> is a class ...
 * <p/>
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TestApplicationFactoryImpl.java,v 1.6 2004/05/07 13:53:20 eburns Exp $
 */

public class TestApplicationFactoryImpl extends JspFacesTestCase {

//
// Protected Constants
//

//
// Class Variables
//

//
// Instance Variables
//
    private ApplicationFactoryImpl applicationFactory = null;

// Attribute Instance Variables

// Relationship Instance Variables

//
// Constructors and Initializers    
//

    public TestApplicationFactoryImpl() {
        super("TestApplicationFactoryImpl");
    }


    public TestApplicationFactoryImpl(String name) {
        super(name);
    }
//
// Class methods
//

//
// General Methods
//

    public void testFactory() {
        applicationFactory = new ApplicationFactoryImpl();
	com.sun.faces.config.StoreServletContext storeSC = 
	    new com.sun.faces.config.StoreServletContext();
	storeSC.setServletContext(config.getServletContext());
	ApplicationAssociate.clearInstance(config.getServletContext());


        // 1. Verify "getApplication" returns the same Application instance
        //    if called multiple times.
        //  
        Application application1 = applicationFactory.getApplication();
        Application application2 = applicationFactory.getApplication();
        assertTrue(application1 == application2);

        // 2. Verify "setApplication" adds instances.. /
        //    and "getApplication" returns the same instance
        //
	ApplicationAssociate.clearInstance(config.getServletContext());
        Application application3 = new ApplicationImpl();
        applicationFactory.setApplication(application3);
        Application application4 = applicationFactory.getApplication();
        assertTrue(application3 == application4);
    }


    public void testSpecCompliance() {
        applicationFactory = new ApplicationFactoryImpl();
	com.sun.faces.config.StoreServletContext storeSC = 
	    new com.sun.faces.config.StoreServletContext();
	storeSC.setServletContext(config.getServletContext());
	ApplicationAssociate.clearInstance(config.getServletContext());

        assertTrue(null != applicationFactory.getApplication());
    }


    public void testExceptions() {
        applicationFactory = new ApplicationFactoryImpl();

        // 1. Verify NullPointer exception which occurs when attempting
        //    to add a null Application
        //
        boolean thrown = false;
        try {
            applicationFactory.setApplication(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }


} // end of class TestApplicationFactoryImpl
