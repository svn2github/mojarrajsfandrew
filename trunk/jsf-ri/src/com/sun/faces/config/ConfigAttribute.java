/*
 * $Id: ConfigAttribute.java,v 1.2 2003/04/29 20:51:31 eburns Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.sun.faces.config;


/**
 * <p>Config Bean for an Attribute.</p>
 */
public class ConfigAttribute extends ConfigFeature {

    private String attributeClass;
    public String getAttributeClass() {
        return (this.attributeClass);
    }
    public void setAttributeClass(String attributeClass) {
        this.attributeClass = attributeClass;
    }

    private String attributeName;
    public String getAttributeName() {
        return (this.attributeName);
    }
    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

}
