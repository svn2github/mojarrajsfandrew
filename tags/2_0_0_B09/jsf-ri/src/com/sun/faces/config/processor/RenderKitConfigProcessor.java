/*
 * $Id: RenderKitConfigProcessor.java,v 1.7 2008/01/11 17:37:40 rlubke Exp $
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 * 
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 * 
 * Contributor(s):
 * 
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

package com.sun.faces.config.processor;

import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.config.ConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import javax.faces.FactoryFinder;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.faces.render.FacesRenderer;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;
import java.text.MessageFormat;
import javax.faces.context.FacesContext;

/**
 * <p>
 *  This <code>ConfigProcessor</code> handles all elements defined under
 *  <code>/faces-config/render-kit</code>.
 * </p>
 */
public class RenderKitConfigProcessor extends AbstractConfigProcessor {

    private static final Logger LOGGER = FacesLogger.CONFIG.getLogger();   

    /**
     * <p>/faces-config/render-kit</p>
     */
    private static final String RENDERKIT = "render-kit";

    /**
     * <p>/faces-config/render-kit/render-kit-id</p>
     */
    private static final String RENDERKIT_ID = "render-kit-id";

    /**
     * <p>/faces-config/render-kit/render-kit-class</p>
     */
    private static final String RENDERKIT_CLASS = "render-kit-class";

    /**
     * <p>/faces-config/render-kit/renderer</p>
     */
    private static final String RENDERER = "renderer";

    /**
     * <p>/faces-config/render-kit/renderer/component-family</p>
     */
    private static final String RENDERER_FAMILY = "component-family";

    /**
     * <p>/faces-config/render-kit/renderer/renderer-type</p>
     */
    private static final String RENDERER_TYPE = "renderer-type";

    /**
     * <p>/faces-config/render-kit/renderer/renderer-class</p>
     */
    private static final String RENDERER_CLASS = "renderer-class";


    // -------------------------------------------- Methods from ConfigProcessor


    /**
     * @see ConfigProcessor#process(org.w3c.dom.Document[])
     */
    public void process(Document[] documents)
    throws Exception {

        Map<String,Map<Document,List<Node>>> renderers =
              new LinkedHashMap<String,Map<Document,List<Node>>>();
        RenderKitFactory rkf = (RenderKitFactory)
             FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        for (int i = 0; i < documents.length; i++) {
            Document document = documents[i];
            if (LOGGER.isLoggable(Level.FINE)) {
                LOGGER.log(Level.FINE,
                           MessageFormat.format(
                                "Processing render-kit elements for document: ''{0}''",
                                document.getDocumentURI()));
            }
            String namespace = document.getDocumentElement()
                 .getNamespaceURI();
            NodeList renderkits = document.getDocumentElement()
                 .getElementsByTagNameNS(namespace, RENDERKIT);

            if (renderkits != null && renderkits.getLength() > 0) {
                addRenderKits(renderkits, document, renderers, rkf);
            }
        }

        // process annotated Renderers first as Renderers configured
        // via config files take precedence
        processAnnotations(FacesRenderer.class);

        // now add the accumulated renderers to the RenderKits
        for (Map.Entry<String,Map<Document,List<Node>>> entry : renderers.entrySet()) {
            RenderKit rk = rkf.getRenderKit(null, entry.getKey());
            if (rk == null) {
                throw new ConfigurationException(
                      MessageUtils.getExceptionMessageString(
                            MessageUtils.RENDERER_CANNOT_BE_REGISTERED_ID,
                            entry.getKey()));
            }
            
            for (Map.Entry<Document,List<Node>> renderEntry : entry.getValue().entrySet()) {
                addRenderers(rk, renderEntry.getKey(), renderEntry.getValue());
            }
        }
        invokeNext(documents);

    }


    // --------------------------------------------------------- Private Methods


    private void addRenderKits(NodeList renderKits,
                               Document owningDocument,
                               Map<String,Map<Document,List<Node>>> renderers,
                               RenderKitFactory rkf) {

        String namespace = owningDocument.getDocumentElement()
                 .getNamespaceURI();
        for (int i = 0, size = renderKits.getLength(); i < size; i++) {
            Node renderKit = renderKits.item(i);
            NodeList children = ((Element) renderKit)
                 .getElementsByTagNameNS(namespace, "*");
            String rkId = null;
            String rkClass = null;
            List<Node> renderersList =
                 new ArrayList<Node>(children.getLength());
            for (int c = 0, csize = children.getLength(); c < csize; c++) {
                Node n = children.item(c);
                if (RENDERKIT_ID.equals(n.getLocalName())) {
                    rkId = getNodeText(n);
                } else if (RENDERKIT_CLASS.equals(n.getLocalName())) {
                    rkClass = getNodeText(n);
                } else if (RENDERER.equals(n.getLocalName())) {
                    renderersList.add(n);
                }
            }

            rkId = ((rkId == null)
                    ? RenderKitFactory.HTML_BASIC_RENDER_KIT
                    : rkId);

            RenderKit rk = rkf.getRenderKit(null, rkId);
            if (rk == null && rkClass != null) {
                RenderKit previous = rkf.getRenderKit(FacesContext.getCurrentInstance(), 
                        rkId);
                rk = (RenderKit) createInstance(rkClass, RenderKit.class, previous, renderKit);
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE,
                               MessageFormat.format(
                                    "Calling RenderKitFactory.addRenderKit({0}, {1})",
                                    rkId,
                                    rkClass));
                }
                rkf.addRenderKit(rkId, rk);
            }
            Map<Document,List<Node>> existingRenderers = renderers.get(rkId);
            if (existingRenderers != null) {
                List<Node> list = existingRenderers.get(owningDocument);
                if (list != null) {
                    list.addAll(renderersList);
                } else {
                    existingRenderers.put(owningDocument, renderersList);
                }
            } else {
                existingRenderers = new LinkedHashMap<Document,List<Node>>();
                existingRenderers.put(owningDocument, renderersList);
            }
            renderers.put(rkId, existingRenderers);
        }

    }


    private void addRenderers(RenderKit renderKit,
                              Document owningDocument,
                              List<Node> renderers) {

        String namespace = owningDocument.getDocumentElement()
                 .getNamespaceURI();
        for (Node renderer : renderers) {
            NodeList children = ((Element) renderer)
                 .getElementsByTagNameNS(namespace, "*");
            String rendererFamily = null;
            String rendererType = null;
            String rendererClass = null;
            for (int i = 0, size = children.getLength(); i < size; i++) {
                Node n = children.item(i);
                if (RENDERER_FAMILY.equals(n.getLocalName())) {
                    rendererFamily = getNodeText(n);
                } else if (RENDERER_TYPE.equals(n.getLocalName())) {
                    rendererType = getNodeText(n);
                } else if (RENDERER_CLASS.equals(n.getLocalName())) {
                    rendererClass = getNodeText(n);
                }
            }

            if ((rendererFamily != null)
                  && (rendererType != null)
                  && (rendererClass != null)) {
                Renderer r = (Renderer) createInstance(rendererClass,
                                                       Renderer.class,
                                                       null,
                                                       renderer);                
                if (r != null) {
                    if (LOGGER.isLoggable(Level.FINE)) {
                        LOGGER.log(Level.FINE,
                                   MessageFormat.format(
                                        "Calling RenderKit.addRenderer({0},{1}, {2}) for RenderKit ''{3}''",
                                        rendererFamily,
                                        rendererType,
                                        rendererClass,
                                        renderKit.getClass()));
                    }
                    renderKit.addRenderer(rendererFamily, rendererType, r);
                }
            }
        }

    }

}
