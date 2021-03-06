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
 *
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Copyright 2005-2007 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.sun.faces.facelets.tag.jsf.core;

import com.sun.faces.RIConstants;
import com.sun.faces.facelets.tag.TagHandlerImpl;
import com.sun.faces.facelets.tag.jsf.ComponentSupport;

import com.sun.faces.util.FacesLogger;
import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseEvent;
import javax.faces.view.facelets.FaceletContext;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagConfig;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.facelets.TagAttributeException;

/**
 * Container for all JavaServer Faces core and custom component actions used on
 * a page. <p/> See <a target="_new"
 * href="http://java.sun.com/j2ee/javaserverfaces/1.1_01/docs/tlddocs/f/view.html">tag
 * documentation</a>.
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
public final class ViewHandler extends TagHandlerImpl {
    
    private static final Logger LOGGER = FacesLogger.TAGLIB.getLogger();

    private final static Class[] LISTENER_SIG = new Class[] { PhaseEvent.class };

    private final TagAttribute locale;

    private final TagAttribute renderKitId;
    
    private final TagAttribute contentType;
    
    private final TagAttribute encoding;

    private final TagAttribute beforePhase;

    private final TagAttribute afterPhase;
    
    /**
     * Stores the contracts tag attribute.
     */
    private final TagAttribute contracts;

    /**
     * @param config
     */
    public ViewHandler(TagConfig config) {
        super(config);
        this.locale = this.getAttribute("locale");
        this.renderKitId = this.getAttribute("renderKitId");
        this.contentType = this.getAttribute("contentType");
        this.encoding = this.getAttribute("encoding");
        TagAttribute testForNull = this.getAttribute("beforePhase");
        this.beforePhase = (null == testForNull) ? 
                         this.getAttribute("beforePhaseListener") : testForNull;
        testForNull = this.getAttribute("afterPhase");
        this.afterPhase = (null == testForNull) ?
                         this.getAttribute("afterPhaseListener") : testForNull;
        this.contracts = this.getAttribute("contracts");
    }

    /**
     * See taglib documentation.
     */
    public void apply(FaceletContext ctx, UIComponent parent)
            throws IOException {
        UIViewRoot root = ComponentSupport.getViewRoot(ctx, parent);
        Object partialStateSavingVal = null;
        if (root != null) {
            if (this.locale != null) {
                try {
                    root.setLocale(ComponentSupport.getLocale(ctx,
                            this.locale));
                } catch (TagAttributeException tae) {
                    Object result = this.locale.getObject(ctx);
                    if (null == result) {
                        Locale l = Locale.getDefault();
                        // Special case for bugdb 13582626
                        if (LOGGER.isLoggable(Level.WARNING)) {
                            LOGGER.log(Level.WARNING, "Using {0} for locale because expression {1} returned null.", new Object[]{l, this.locale.toString()});
                        }
                        root.setLocale(l);
                    }
                }
            }
            if (this.renderKitId != null) {
                String v = this.renderKitId.getValue(ctx);
                root.setRenderKitId(v);
            }
            if (this.contentType != null) {
                String v = this.contentType.getValue(ctx);
                ctx.getFacesContext().getAttributes().put("facelets.ContentType", v);
            }
            if (this.encoding != null) {
                String v = this.encoding.getValue(ctx);
                ctx.getFacesContext().getAttributes().put(RIConstants.FACELETS_ENCODING_KEY, v);
            }
            if (this.beforePhase != null) {
                MethodExpression m = this.beforePhase
                        .getMethodExpression(ctx, null, LISTENER_SIG);
                root.setBeforePhaseListener(m);
            }
            if (this.afterPhase != null) {
                MethodExpression m = this.afterPhase
                        .getMethodExpression(ctx, null, LISTENER_SIG);
                root.setAfterPhaseListener(m);
            }
            
            if (this.contracts != null) {
                List<String> contractList = Arrays.asList(this.contracts.getValue(ctx).split(","));
                ctx.getFacesContext().setResourceLibraryContracts(contractList);
            }
            
            String viewId = root.getViewId();

            // At this point in the lifecycle we should have a non-null/empty
            // view id.  The partial state saving check below requires this.
            assert(null != viewId);
            assert(0 < viewId.length());

        }

        this.nextHandler.apply(ctx, parent);
    }

}
