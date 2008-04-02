/*
 * $Id: AbstractTestBean.java,v 1.4 2004/02/26 20:33:46 eburns Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.faces.systest.model;


/**
 * <p>Test JavaBean for managed object creation facility.</p>
 */

public abstract class AbstractTestBean {

    private String stringProperty = "String Property";


    public String getStringProperty() {
        return this.stringProperty;
    }


    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

}
