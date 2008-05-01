/*
 * $Id: CommandLinkRenderer.java,v 1.62 2007/08/30 19:29:12 rlubke Exp $
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

// CommandLinkRenderer.java

package com.sun.faces.renderkit.html_basic;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;

import com.sun.faces.RIConstants;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.MessageUtils;

/**
 * <B>CommandLinkRenderer</B> is a class that renders the current value of
 * <code>UICommand<code> as a HyperLink that acts like a Button.
 */

public class CommandLinkRenderer extends LinkRenderer {

    private static final String[] ATTRIBUTES =
          AttributeManager.getAttributes(AttributeManager.Key.COMMANDLINK);


    private static final String SCRIPT_STATE = RIConstants.FACES_PREFIX +
                                               "scriptState";

    // ---------------------------------------------------------- Public Methods


    @Override
    public void decode(FacesContext context, UIComponent component) {

        rendererParamsNotNull(context, component);

        if (!shouldDecode(component)) {
            return;
        }

        if (wasClicked(context, component)) {
            component.queueEvent(new ActionEvent(component));
            if (logger.isLoggable(Level.FINE)) {
                logger.fine("This commandLink resulted in form submission " +
                            " ActionEvent queued.");

            }
        }

    }


    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
          throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        boolean componentDisabled =
              Boolean.TRUE.equals(component.getAttributes().get("disabled"));

        String formClientId = getFormClientId(component, context);
        if (formClientId == null) {
            if (logger.isLoggable(Level.WARNING)) {
                logger.log(Level.WARNING,
                           "Component {0} must be enclosed inside a form",
                           component.getId());
            }
        }

        if (componentDisabled || formClientId == null) {
            renderAsDisabled(context, component);
        } else {
            if (!hasScriptBeenRendered(context)) {
                RenderKitUtils
                      .renderFormInitScript(
                            context.getResponseWriter(),
                            context);
                setScriptAsRendered(context);
            }
            renderAsActive(context, component);
        }

    }


    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
          throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncodeChildren(component)) {
            return;
        }

        if (component.getChildCount() > 0) {
            for (UIComponent kid : component.getChildren()) {
                kid.encodeBegin(context);
                if (kid.getRendersChildren()) {
                    kid.encodeChildren(context);
                }
                kid.encodeEnd(context);
            }
        }
        
    }


    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
          throws IOException {

        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);
        String formClientId = getFormClientId(component, context);
        if (formClientId == null) {
            writer.write(MessageUtils.getExceptionMessageString(
                  MessageUtils.COMMAND_LINK_NO_FORM_MESSAGE_ID));
            writer.endElement("span");
            return;
        }

        if (Boolean.TRUE.equals(component.getAttributes().get("disabled"))) {
            writer.endElement("span");
        } else {
            writer.endElement("a");
        }

    }


    @Override
    public boolean getRendersChildren() {

        return true;

    }

    // ------------------------------------------------------- Protected Methods


    @Override
    protected Object getValue(UIComponent component) {

        return ((UICommand) component).getValue();       

    }


    protected String getOnClickScript(String formClientId,
                                      String commandClientId,
                                      String target,
                                      Param[] params) {

        return RenderKitUtils.getCommandLinkOnClickScript(formClientId,
                                                          commandClientId,
                                                          target,
                                                          params);

    }


    protected void renderAsActive(FacesContext context, UIComponent command)
          throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        assert(writer != null);
        String formClientId = getFormClientId(command, context);
        if (formClientId == null) {
            return;
        }

        //make link act as if it's a button using javascript        
        writer.startElement("a", command);
        writeIdAttributeIfNecessary(context, writer, command);
        writer.writeAttribute("href", "#", "href");
        RenderKitUtils.renderPassThruAttributes(writer,
                                                command,
                                                ATTRIBUTES);
        RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, command);

        // render onclick
        String userOnclick = (String) command.getAttributes().get("onclick");
        StringBuffer sb = new StringBuffer(128);
        boolean userSpecifiedOnclick =
              (userOnclick != null && !"".equals(userOnclick));

        // if user specified their own onclick value, we are going to
        // wrap their js and the injected js each in a function and
        // execute them in a choose statement, if the user didn't specify
        // an onclick, the original logic executes unaffected
        if (userSpecifiedOnclick) {
            sb.append("var a=function(){");
            userOnclick = userOnclick.trim();
            sb.append(userOnclick);
            if (userOnclick.charAt(userOnclick.length() - 1) != ';') {
                sb.append(';');
            }
            sb.append("};var b=function(){");
        }

        Param[] params = getParamList(command);
        String commandClientId = command.getClientId(context);
        String target = (String) command.getAttributes().get("target");
        if (target != null) {
            target = target.trim();
        } else {
            target = "";
        }

        sb.append(
              getOnClickScript(formClientId,
                               commandClientId,
                               target,
                               params));

        // we need to finish wrapping the injected js then
        if (userSpecifiedOnclick) {
            sb.append("};return (a()==false) ? false : b();");
        }

        writer.writeAttribute("onclick", sb.toString(), "onclick");

        writeCommonLinkAttributes(writer, command);

        // render the current value as link text.
        writeValue(command, writer);
        writer.flush();

    }

    // --------------------------------------------------------- Private Methods


    /**
     * @param context the <code>FacesContext</code> for the current request
     *
     * @return <code>true</code> If the <code>add/remove</code> javascript
     *         has been rendered, otherwise <code>false</code>
     */
    private static boolean hasScriptBeenRendered(FacesContext context) {

        return (context.getExternalContext().getRequestMap()
              .get(SCRIPT_STATE) != null);

    }


    /**
     * <p>Set a flag to indicate that the <code>add/remove</code> javascript
     * has been rendered for the current form.
     *
     * @param context the <code>FacesContext</code> of the current request
     */
    private static void setScriptAsRendered(FacesContext context) {

        context.getExternalContext().getRequestMap()
              .put(SCRIPT_STATE, Boolean.TRUE);

    }


    /**
     * <p>Utility method to return the client ID of the parent form.</p>
     *
     * @param component typically a command component
     * @param context   the <code>FacesContext</code> for the current request
     *
     * @return the client ID of the parent form, if any
     */
    private static String getFormClientId(UIComponent component,
                                   FacesContext context) {

        UIForm form = getMyForm(component);
        if (form != null) {
            return form.getClientId(context);
        }

        return null;

    }


    private static UIForm getMyForm(UIComponent component) {

        UIComponent parent = component.getParent();
        while (parent != null) {
            if (parent instanceof UIForm) {
                break;
            }
            parent = parent.getParent();
        }       

        return (UIForm) parent;

    }


    private static boolean wasClicked(FacesContext context,
                                      UIComponent component) {

        Map<String,String> requestParamMap =
              context.getExternalContext().getRequestParameterMap();
        return (requestParamMap.containsKey(component.getClientId(context)));

    }

} // end of class CommandLinkRenderer
