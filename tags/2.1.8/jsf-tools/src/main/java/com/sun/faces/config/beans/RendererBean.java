/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.config.beans;


import java.util.Map;
import java.util.TreeMap;


/**
 * <p>Configuration bean for <code>&lt;renderer&gt; element.</p>
 */

public class RendererBean extends FeatureBean implements AttributeHolder {


    // -------------------------------------------------------------- Properties


    private String componentFamily;
    public String getComponentFamily() { return componentFamily; }
    public void setComponentFamily(String componentFamily)
    { this.componentFamily = componentFamily; }


    private String rendererClass;
    public String getRendererClass() { return rendererClass; }
    public void setRendererClass(String rendererClass)
    { this.rendererClass = rendererClass; }


    private String rendererType;
    public String getRendererType() { return rendererType; }
    public void setRendererType(String rendererType)
    { this.rendererType = rendererType; }


    // -------------------------------------------------------------- Extensions


    // true == this Renderer returns true for getRendersChildren()
    private boolean rendersChildren = false;
    public boolean isRendersChildren() { return rendersChildren; }
    public void setRendersChildren(boolean rendersChildren)
    { this.rendersChildren = rendersChildren; }

    private String excludeAttributes;
    public String getExcludeAttributes() {
        return excludeAttributes;
    }
    public void setExcludeAttributes(String newExcludeAttributes) {
        excludeAttributes = newExcludeAttributes;
    }

    private boolean notForJsp = false;
    public boolean isIgnoreForJsp() { return notForJsp; }
    public void setIgnoreForJsp(boolean notForJsp)
    { this.notForJsp = notForJsp; }

    private boolean ignoreAll = false;
    public boolean isIgnoreAll() { return ignoreAll; }
    public void setIgnoreAll(boolean ignoreAll)
    { this.ignoreAll = ignoreAll; }

    // true if the tag handler for this renderer should be a BodyTag
    // [default=false]
    public boolean bodyTag = false;
    public boolean isBodyTag() { return bodyTag; }
    public void setBodyTag(boolean bodyTag) { this.bodyTag = bodyTag; }

    // Tag name (if it doesn't follow the standard convention)
    private String tagName;
    public String getTagName() { return tagName; }
    public void setTagName(String tagName) { this.tagName = tagName; }


    // ------------------------------------------------- AttributeHolder Methods


    private Map<String,AttributeBean> attributes = new TreeMap<String, AttributeBean>();


    public void addAttribute(AttributeBean descriptor) {
        attributes.put(descriptor.getAttributeName(), descriptor);
    }


    public AttributeBean getAttribute(String name) {
        return (attributes.get(name));
    }


    public AttributeBean[] getAttributes() {
        AttributeBean results[] = new AttributeBean[attributes.size()];
        return (attributes.values().toArray(results));
    }


    public void removeAttribute(AttributeBean descriptor) {
        attributes.remove(descriptor.getAttributeName());
    }


    // ----------------------------------------------------------------- Methods


}
