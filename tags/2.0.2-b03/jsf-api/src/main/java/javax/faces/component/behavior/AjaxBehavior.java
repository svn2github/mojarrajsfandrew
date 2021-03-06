/*
 * $Id: AjaxBehavior.java,v 1.0 2008/11/03 18:51:29 rogerk Exp $
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
 
package javax.faces.component.behavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorListener;


/**
 * <p class="changed_added_2_0">An instance of this class is added
 * as a {@link ClientBehavior} to a component using the 
 * {@link javax.faces.component.behavior.ClientBehaviorHolder#addClientBehavior} 
 * contract that components implement.  The presence of this 
 * {@link ClientBehavior} will cause the rendering of JavaScript that 
 * produces an <code>Ajax</code> request using the 
 * specification public JavaScript API when the component is 
 * rendered.</p> 
 *
 * @since 2.0
 */
public class AjaxBehavior extends ClientBehaviorBase {

    /**
     * <p class="changed_added_2_0">The standard id for this behavior.</p>
     */
    public static final String BEHAVIOR_ID = "javax.faces.behavior.Ajax";

    private static final Set<ClientBehaviorHint> HINTS = 
        Collections.unmodifiableSet(EnumSet.of(ClientBehaviorHint.SUBMITTING));

    private String onerror;
    private String onevent;
    private List<String> execute;
    private List<String> render;
    private Boolean disabled;
    private Boolean immediate;

    private Map<String, ValueExpression> bindings;

    // ---------------------------------------------------------- Public Methods
    @Override
    public String getRendererType() {
        // We use the same sring for both the behavior id and the renderer 
        // type.
        return  BEHAVIOR_ID;
    }


    /**
     * <p class="changed_added_2_0">
     * This method returns an unmodifiable <code>Set</code> containing
     * the {@link ClientBehaviorHint} <code>SUBMITTING</code>.</p> 
     *
     * @return unmodifiable set containing the hint {@link ClientBehaviorHint}
     * <code>SUBMITTING</code>.
     *
     * @since 2.0
     */
    public Set<ClientBehaviorHint> getHints() {
        return HINTS;
    }

    /**
     * <p class="changed_added_2_0">Return the <code>String</code> of
     * JavaScript function name that will be used to identify
     * the client callback function that should be run in the event of
     * an error.
     *
     * @since 2.0
     */
    public String getOnerror() {

        return (String) eval(ONERROR, onerror);

    }

    /**
     * <p class="changed_added_2_0">Sets the JavaScript function name 
     * that will be used to identify the client callback function that 
     * should be run in the event of an error.
     *
     * @param onerror the error handling function name
     *
     * @since 2.0
     */
    public void setOnerror(String onerror) {

        this.onerror = onerror;

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Return the <code>String</code> of
     * JavaScript function name that will be used to identify the
     * client callback function that should be run on the occurance
     * of a client-side event.
     *
     * @since 2.0
     */
    public String getOnevent() {

        return (String) eval(ONEVENT, onevent);

    }

    /**
     * <p class="changed_added_2_0">Sets the JavaScript function name 
     * that will be used to identify the client callback function that 
     * should be run in response to event activity.
     *
     * @param onevent the event handling function name
     *
     * @since 2.0
     */
    public void setOnevent(String onevent) {

        this.onevent = onevent;

        clearInitialState();
   }

    /**
     * <p class="changed_added_2_0">Return a non-empty
     * <code>Collection&lt;String&gt;</code> of component
     * identifiers that will be used to identify components that should be
     * processed during the <code>execute</code> phase of the request
     * processing lifecycle.</p>
     * <p>Note that the returned collection may be unmodifiable.  Modifications
     * should be performed by calling {@link #setExecute}.</p>
     *
     * @since 2.0
     */
    public Collection<String> getExecute() {

        return getCollectionValue(EXECUTE, execute);

    }

    /**
     * <p class="changed_added_2_0">Sets the component identifiers that 
     * will be used to identify components that should be
     * processed during the <code>execute</code> phase of the request
     * processing lifecycle.</p>
     *
     * @param execute the ids of components to execute
     *
     * @since 2.0
     */
    public void setExecute(Collection<String> execute) {

        this.execute = copyToList(execute);

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Return a non-empty
     * <code>Collection&lt;String&gt;</code> of component
     * identifiers that will be used to identify components that should be
     * processed during the <code>render</code> phase of the request
     * processing lifecycle.</p>
     * <p>Note that the returned collection may be unmodifiable.  Modifications
     * should be performed by calling {@link #setRender}.</p>
     *
     * @since 2.0
     */
    public Collection<String> getRender() {

        return getCollectionValue(RENDER, render);

    }

    /**
     * <p class="changed_added_2_0">Sets the component identifiers that 
     * will be used to identify components that should be
     * processed during the <code>render</code> phase of the request
     * processing lifecycle.</p>
     *
     * @param render the ids of components to render
     *
     * @since 2.0
     */
    public void setRender(Collection<String> render) {

        this.render = copyToList(render);

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Return the disabled status of this behavior.</p>
     *
     * @since 2.0
     */
    public boolean isDisabled() {

        Boolean result = (Boolean) eval(DISABLED, disabled);
        return ((result != null) ? result : false);
    }

    /**
     * <p class="changed_added_2_0">Sets the disabled status of this 
     * behavior.</p>
     *
     * @since 2.0
     */
    public void setDisabled(boolean disabled) {

        this.disabled = disabled;

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Return the immediate status of this 
     * behaivor.</p>
     *
     * @since 2.0
     */
    public boolean isImmediate() {
        Boolean result = (Boolean) eval(IMMEDIATE, immediate);
        return ((result != null) ? result : false);
    }


    /**
     * <p class="changed_added_2_0">Sets the immediate status of this 
     * behavior.</p>
     *
     * @since 2.0
     */
    public void setImmediate(boolean immediate) {

        this.immediate = immediate;

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Tests whether the immediate attribute
     * is specified.  Returns true if the immediate attribute is specified,
     * either as a locally set property or as a value expression.  This
     * information allows an associated client behavior renderer to fall back
     * on the parent component's immediate status when immediate is not 
     * explicitly specified on the <code>AjaxBehavior</code>.
     * </p>
     *
     * @since 2.0
     */
    public boolean isImmediateSet() {
        return ((immediate != null) || (getValueExpression(IMMEDIATE) != null));
    }

    /**
     * <p class="changed_added_2_0">Returns the {@link ValueExpression}
     * used to calculate the value for the specified property name, if any.
     * </p>
     *
     * @param name Name of the property for which to retrieve a
     *  {@link ValueExpression}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     */
    public ValueExpression getValueExpression(String name) {

        if (name == null) {
            throw new NullPointerException();
        }

        return ((bindings == null) ? null : bindings.get(name));
    }

    /**
     * <p class="changed_added_2_0">Sets the {@link ValueExpression} 
     * used to calculate the value for the specified property name.</p>
     * </p>
     *
     * @param name Name of the property for which to set a
     *  {@link ValueExpression}
     * @param binding The {@link ValueExpression} to set, or <code>null</code>
     *  to remove any currently set {@link ValueExpression}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     */
    public void setValueExpression(String name, ValueExpression binding) {

        if (name == null) {
            throw new NullPointerException();
        }

        if (binding != null) {

            if (binding.isLiteralText()) {
                setLiteralValue(name, binding);
            } else {
                if (bindings == null) {

                    // We use a very small initial capacity on this HashMap.
                    // The goal is not to reduce collisions, but to keep the
                    // memory footprint small.  It is very unlikely that an
                    // an AjaxBehavior would have more than 1 or 2 bound 
                    // properties - and even if more are present, it's okay
                    // if we have some collisions - will still be fast.
                    bindings = new HashMap<String, ValueExpression>(6,1.0f);
                }

                bindings.put(name, binding);
            }
        } else {
            if (bindings != null) {
                bindings.remove(name);
                if (bindings.isEmpty()) {
                    bindings = null;
                }
            }
        }

        clearInitialState();
    }

    /**
     * <p class="changed_added_2_0">Add the specified {@link AjaxBehaviorListener}
     * to the set of listeners registered to receive event notifications
     * from this {@link AjaxBehavior}.</p>
     *
     * @param listener The {@link AjaxBehaviorListener} to be registered
     *
     * @throws NullPointerException if <code>listener</code>
     *  is <code>null</code>
     *
     * @since 2.0
     */
    public void addAjaxBehaviorListener(AjaxBehaviorListener listener) {
        addBehaviorListener(listener);
    }

    /**
     * <p class="changed_added_2_0">Remove the specified {@link AjaxBehaviorListener}
     * from the set of listeners registered to receive event notifications
     * from this {@link AjaxBehavior}.</p>
     *
     * @param listener The {@link AjaxBehaviorListener} to be removed
     *
     * @throws NullPointerException if <code>listener</code>
     *  is <code>null</code>
     *
     * @since 2.0
     */
    public void removeAjaxBehaviorListener(AjaxBehaviorListener listener) {
        removeBehaviorListener(listener);
    }

    @Override
    public Object saveState(FacesContext context) {

        if (context == null) {
            throw new NullPointerException();
        }
        Object[] values;

        Object superState = super.saveState(context);

        if (initialStateMarked()) {
            if (superState == null) {
                values = null;
            } else {
                values = new Object[] { superState };
            }
        } else {
            values = new Object[8];
      
            values[0] = superState;
            values[1] = onerror;
            values[2] = onevent;
            values[3] = disabled;
            values[4] = immediate;
            values[5] = saveList(execute);
            values[6] = saveList(render);
            values[7] = saveBindings(context, bindings);
        }

        return values;
    }

    @Override
    public void restoreState(FacesContext context, Object state) {

        if (context == null) {
            throw new NullPointerException();
        }
        if (state != null) {

            Object[] values = (Object[]) state;
            super.restoreState(context, values[0]);

            if (values.length != 1) {
                onerror = (String)values[1];
                onevent = (String)values[2];
                disabled = (Boolean)values[3];
                immediate = (Boolean)values[4];
                execute = restoreList(EXECUTE, values[5]);
                render = restoreList(RENDER, values[6]);
                bindings = restoreBindings(context, values[7]);

                // If we saved state last time, save state again next time.
                clearInitialState();
            }
        }
    }


    // --------------------------------------------------------- Private Methods

    // Utility for saving bindings state
    private static Object saveBindings(FacesContext context,
                                       Map<String, ValueExpression> bindings) {

        // Note: This code is copied from UIComponentBase.  In a future
        // version of the JSF spec, it would be useful to define a
        // attribute/property/bindings/state helper object that can be
        // shared across components/behaviors/validaters/converters.
        
        if (bindings == null) {
            return (null);
        }

        Object values[] = new Object[2];
        values[0] = bindings.keySet().toArray(new String[bindings.size()]);

        Object[] bindingValues = bindings.values().toArray();
        for (int i = 0; i < bindingValues.length; i++) {
            bindingValues[i] = UIComponentBase.saveAttachedState(context, bindingValues[i]);
        }

        values[1] = bindingValues;

        return (values);
    }

    // Utility for restoring bindings from state
    private static Map<String, ValueExpression> restoreBindings(FacesContext context,
                                                                Object state) {

        // Note: This code is copied from UIComponentBase.  See note above
        // in saveBindings().

        if (state == null) {
            return (null);
        }
        Object values[] = (Object[]) state;
        String names[] = (String[]) values[0];
        Object states[] = (Object[]) values[1];
        Map<String, ValueExpression> bindings = new HashMap<String, ValueExpression>(names.length);
        for (int i = 0; i < names.length; i++) {
            bindings.put(names[i],
                    (ValueExpression) UIComponentBase.restoreAttachedState(context, states[i]));
        }
        return (bindings);
    }


    // Save the List<String>, either as a String (single element) or as
    // a String[] (multiple elements.
    private static Object saveList(List<String> list) {
        if ((list == null) || list.isEmpty()) {
            return null;
        }

        int size = list.size();

        if (size == 1) {
            return list.get(0);
        }

        return list.toArray(new String[size]);
    }

    // Restore the list from a String (single element) or a String[]
    // (multiple elements)
    private static List<String> restoreList(String propertyName, 
                                            Object state) {

        if (state == null) {
            return null;
        }

        List<String> list = null;

        if (state instanceof String) {
            list = toSingletonList(propertyName, (String)state);
        } else if (state instanceof String[]) {
            list = Collections.unmodifiableList(Arrays.asList((String[])state));
        }

        return list;
    }
      
    private Object eval(String propertyName, Object value) {

        if (value != null) {
            return value;
        }

        ValueExpression expression = getValueExpression(propertyName);

        if (expression != null) {
            FacesContext ctx = FacesContext.getCurrentInstance();
            return expression.getValue(ctx.getELContext());
        }

        return null;
    }


    @SuppressWarnings("unchecked")
    private Collection<String> getCollectionValue(String propertyName,
                                                  Collection<String> collection) {
        if (collection!= null) {
            return collection;
        }

        Collection<String> result = null;
        ValueExpression expression = getValueExpression(propertyName);

        if (expression != null) {

            FacesContext ctx = FacesContext.getCurrentInstance();
            Object value = expression.getValue(ctx.getELContext());

            if (value != null) {

                if (value instanceof Collection) {
                    // Unchecked cast to Collection<String>
                    return (Collection<String>)value;
                }

                result = toList(propertyName, expression, value);
            }
        }

        return result == null ? Collections.<String>emptyList() : result;
    }

    // Sets a property, converting it from a literal
    private void setLiteralValue(String propertyName,
                                 ValueExpression expression) {

        assert(expression.isLiteralText());

        Object value;
        ELContext context = FacesContext.getCurrentInstance().getELContext();

        try {
            value = expression.getValue(context);
        } catch (ELException ele) {
            throw new FacesException(ele);
        }

        if (ONEVENT.equals(propertyName)) {
            onevent = (String)value;
        } else if (ONERROR.equals(propertyName)) {
            onerror = (String)value;
        } else if (IMMEDIATE.equals(propertyName)) {
            immediate = (Boolean)value;
        } else if (DISABLED.equals(propertyName)) {
            disabled = (Boolean)value;
        } else if (EXECUTE.equals(propertyName)) {
            execute = toList(propertyName, expression, value);
        } else if (RENDER.equals(propertyName)) {
            render = toList(propertyName, expression, value);
        }
    }

    // Converts the specified object to a List<String>
    private static List<String> toList(String propertyName,
                                ValueExpression expression,
                                Object value) {

        if (value instanceof String) {

            String strValue = (String)value;

            // If the value contains no spaces, we can optimize.
            // This is worthwhile, since the execute/render lists
            // will often only contain a single value.
            if (strValue.indexOf(' ') == -1) {
                return toSingletonList(propertyName, strValue);
            }

            // We're stuck splitting up the string.
            String[] values = SPLIT_PATTERN.split(strValue);
            if ((values == null) || (values.length == 0)) {
                return null;
            }

            // Note that we could create a Set out of the values if
            // we care about removing duplicates.  However, the
            // presence of duplicates does not real harm.  They will
            // be consolidated during the partial view traversal.  So,
            // just create an list - garbage in, garbage out.
            return Collections.unmodifiableList(Arrays.asList(values));
        }

        // RELEASE_PENDING i18n ;
        throw new FacesException(expression.toString()
                                 + " : '"
                                 + propertyName
                                 + "' attribute value must be either a String or a Collection");
    }

    // Converts a String with no spaces to a singleton list
    private static List<String> toSingletonList(String propertyName,
                                         String value) {
        if ((null == value) || (value.length() == 0)) {
            return null;
        }

        if (value.charAt(0) == '@') {
            // These are very common, so we use shared copies
            // of these collections instead of re-creating.
            List<String> list;

            if (ALL.equals(value)) {
                list = ALL_LIST;
            } else if (FORM.equals(value)){
                list = FORM_LIST;
            } else if (THIS.equals(value)) {
                list = THIS_LIST; 
            } else if (NONE.equals(value)) {
                list = NONE_LIST;
            } else {
                // RELEASE_PENDING i18n ;
                throw new FacesException(value
                                     + " : Invalid id keyword specified for '"
                                     + propertyName
                                     + "' attribute");
            }
            
            return list;
        }
         
        return Collections.singletonList(value);
    }

    // Makes a defensive copy of the collection, converting to a List
    // (to make state saving a bit easier).
    private List<String> copyToList(Collection<String> collection) {
 
        if ((collection == null) || collection.isEmpty()) {
            return null;
        }

       return Collections.unmodifiableList(new ArrayList<String>(collection));
    }

    // Property name constants
    private static final String ONEVENT = "onevent";
    private static final String ONERROR = "onerror";
    private static final String IMMEDIATE = "immediate";
    private static final String DISABLED = "disabled";
    private static final String EXECUTE = "execute";
    private static final String RENDER = "render";

    // Id keyword constants
    private static String ALL = "@all";
    private static String FORM = "@form";
    private static String THIS = "@this";
    private static String NONE = "@none";

    // Shared execute/render collections
    private static List<String> ALL_LIST = Collections.singletonList("@all");
    private static List<String> FORM_LIST = Collections.singletonList("@form");
    private static List<String> THIS_LIST = Collections.singletonList("@this");
    private static List<String> NONE_LIST = Collections.singletonList("@none");

    // Pattern used for execute/render string splitting
    private static Pattern SPLIT_PATTERN = Pattern.compile(" ");

}
