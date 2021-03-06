/*
 * $Id: UISelectMany.java,v 1.63 2007/07/27 19:59:08 rlubke Exp $
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

package javax.faces.component;


import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.el.ValueExpression;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.convert.Converter;


/**
 * <p><strong class="changed_modified_2_0">UISelectMany</strong> is a
 * {@link UIComponent} that represents the user's choice of a zero or
 * more items from among a discrete set of available options.  The user
 * can modify the selected values.  Optionally, the component can be
 * preconfigured with zero or more currently selected items, by storing
 * them as an array <span class="changed_modified_2_0">or
 * <code>Collection</code></span> in the <code>value</code> property of
 * the component.</p>
 *
 * <p>This component is generally rendered as a select box or a group of
 * checkboxes.</p>
 *
 * <p>By default, the <code>rendererType</code> property must be set to
 * "<code>javax.faces.Listbox</code>".  This value can be changed by
 * calling the <code>setRendererType()</code> method.</p>
 *
 * <p>The {@link javax.faces.render.Renderer} for this component must
 * perform the following logic on <a
 * name="#getConvertedValue"><code>getConvertedValue()</code></a>:</p>
 *
 * <ul>
 *
 * <p>Obtain the {@link javax.faces.convert.Converter} using the following algorithm:</p>
 *
 * <ul> 
 *
 * <p>If the component has an attached {@link javax.faces.convert.Converter}, use it.</p>
 *
 * <p>If not, look for a {@link ValueExpression} for <code>value</code>
 * (if any).  The {@link ValueExpression} must point to something that
 * is:</p>
 *
 * <ul> <li><p>An array of primitives (such as <code>int[]</code>).
 * Look up the registered by-class {@link javax.faces.convert.Converter}
 * for this primitive type.</p></li> 

 * <li><p>An array of objects (such as <code>Integer[]</code> or
 * <code>String[]</code>).  Look up the registered by-class {@link
 * javax.faces.convert.Converter} for the underlying element
 * type.</p></li> 

 * <li class="changed_modified_2_0"><p>A <code>java.util.Collection</code>.
 * Do not convert the values.</p></li>

 * </ul>
 *
 * <p>If for any reason a <code>Converter</code> cannot be found, assume
 * the type to be a String array.</p>

 * </ul>

 * <p>Use the selected {@link javax.faces.convert.Converter} (if any) to
 * convert each element in the values array from the request to the
 * proper type, <span class="changed_modified_2_0">and store the result of
 * each conversion in a data structure, called
 * <em>targetForConvertedValues</em> for discussion.  Create
 * <em>targetForConvertedValues</em> using the following
 * algorithm.</span></p>

 * <div class="changed_modified_2_0">

 * <ul>

 * <li><p>If the component has a <code>ValueExpression</code> for
 * <code>value</code> and the type of the expression is an array, let
 * <em>targetForConvertedValues</em> be a new array of the expected
 * type.</p></li>


 * <li><p>If the component has a <code>ValueExpression</code> for
 * <code>value</code>, let <em>modelType</em> be the type of the value
 * expression.  If <em>modelType</em> is a <code>Collection</code>, do
 * the following to arrive at <em>targetForConvertedValues</em>:</p>

 * <ul>

 * <li><p>Ask the component for its attribute under the key
 * "<code>collectionType</code>", without the quotes.  If there is a
 * value for that key, the value must be a String that is a fully
 * qualified Java class name, or a <code>Class</code> object, or a
 * <code>ValueExpression</code> that evaluates to a String or a
 * <code>Class</code>.  In all cases, the value serves to identify the
 * concrete type of the class that implements <code>Collection</code>.
 * For discussion, this is called <em>collectionType</em>.  Let
 * <em>targetForConvertedValues</em> be a new instance of
 * <code>Collection</code> implemented by the concrete class specified
 * in <em>collectionType</em>.  If, <em>collectionType</em> can not be
 * discovered, or an instance of <code>Collection</code> implemented by
 * the concrete class specified in <em>collectionType</em> cannot be
 * created, throw a {@link javax.faces.FacesException} with a correctly
 * localized error message.  Note that <code>FacesException</code> is
 * thrown instead of <code>ConverterException</code> because this case
 * would only arise from developer error, rather than end-user
 * error.</p></li>

 * <li><p>If there is no "<code>collectionType</code>" attribute, call
 * <code>getValue()</code> on the component.  The result will implement
 * <code>Collection</code>.  If the result also implements
 * <code>Cloneable</code>, let <em>targetForConvertedValues</em> be the
 * result of calling its <code>clone()</code> method, then calling
 * <code>clear()</code> on the cloned <code>Collection</code>.  If
 * unable to clone the value for any reason, log a message and proceed
 * to the next step.</p></li>

 * <li><p>If <em>modelType</em> is a concrete class, let
 * <em>targetForConvertedValues</em> be a new instance of that class.
 * Otherwise, the concrete type for <em>targetForConvertedValues</em> is
 * taken from the following table.  All classes are in the
 * <code>java.util</code> package.  All collections must be created with
 * an initial capacity equal to the length of the values array from the
 * request.</p>

 * <table border="1">

 * <tr>

 * <th>If <em>modelType</em> is an instance of</th>
 
 * <th>then <em>targetForConvertedValues</em> must be an instance
 * of</th>

 * </tr>

 * <tr>

 * <td><code>SortedSet</code></td>

 * <td><code>TreeSet</code></td>

 * </tr>

 * <tr>

 * <td><code>Queue</code></td>

 * <td><code>LinkedList</code></td>

 * </tr>

 * <tr>

 * <td><code>Set</code></td>

 * <td><code>HashSet</code></td>

 * </tr>

 * <tr>

 * <td>anything else</td>

 * <td><code>ArrayList</code></td>

 * </tr>

 * </table>

 * </li>

 * </ul>

 * <li><p>If the component does not have a <code>ValueExpression</code>
 * for <code>value</code>, let <em>targetForConvertedValues</em> be an
 * array of type <code>Object</code>.</p>

 * </ul>

 * </div>

 * <p>Return <em>targetForConvertedValues</em> after populating it with
 * the converted values.</p>

 * </ul>
 *
 */

public class UISelectMany extends UIInput {


    // ------------------------------------------------------ Manifest Constants


    /**
     * <p>The standard component type for this component.</p>
     */
    public static final String COMPONENT_TYPE = "javax.faces.SelectMany";


    /**
     * <p>The standard component family for this component.</p>
     */
    public static final String COMPONENT_FAMILY = "javax.faces.SelectMany";


    /**
     * <p>The message identifier of the
     * {@link javax.faces.application.FacesMessage} to be created if
     * a value not matching the available options is specified.
     */
    public static final String INVALID_MESSAGE_ID =
        "javax.faces.component.UISelectMany.INVALID";


    // ------------------------------------------------------------ Constructors


    /**
     * <p>Create a new {@link UISelectMany} instance with default property
     * values.</p>
     */
    public UISelectMany() {

        super();
        setRendererType("javax.faces.Listbox");

    }


    // -------------------------------------------------------------- Properties


    public String getFamily() {

        return (COMPONENT_FAMILY);

    }


    /**
     * <p>Return the currently selected values, or <code>null</code> if there
     * are no currently selected values.  This is a typesafe alias for
     * <code>getValue()</code>.</p>
     */
    public Object[] getSelectedValues() {

        return ((Object[]) getValue());

    }


    /**
     * <p>Set the currently selected values, or <code>null</code> to indicate
     * that there are no currently selected values.  This is a typesafe
     * alias for <code>setValue()</code>.</p>
     *
     * @param selectedValues The new selected values (if any)
     */
    public void setSelectedValues(Object selectedValues[]) {

        setValue(selectedValues);

    }


    // ---------------------------------------------------------------- Bindings


    /**
     * <p>Return any {@link ValueBinding} set for <code>value</code> if
     * a {@link ValueBinding} for <code>selectedValues</code> is
     * requested; otherwise, perform the default superclass processing
     * for this method.</p>
     *
     * <p>This method relies on the superclass to provide the
     * <code>ValueExpression</code> to <code>ValueBinding</code>
     * wrapping.</p>
     *
     * @param name Name of the attribute or property for which to retrieve
     *  a {@link ValueBinding}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     *
     * @deprecated this has been replaced by {@link #getValueExpression(java.lang.String)}.
     */
    public ValueBinding getValueBinding(String name) {

        if ("selectedValues".equals(name)) {
            return (super.getValueBinding("value"));
        } else {
            return (super.getValueBinding(name));
        }

    }


    /**
     * <p>Store any {@link ValueBinding} specified for
     * <code>selectedValues</code> under <code>value</code> instead;
     * otherwise, perform the default superclass processing for this
     * method.</p>
     *
     * <p>This method relies on the superclass to wrap the argument
     * <code>ValueBinding</code> in a <code>ValueExpression</code>.</p>
     *
     * @param name Name of the attribute or property for which to set
     *  a {@link ValueBinding}
     * @param binding The {@link ValueBinding} to set, or <code>null</code>
     *  to remove any currently set {@link ValueBinding}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     *
     * @deprecated This has been replaced by {@link #setValueExpression(java.lang.String, javax.el.ValueExpression)}.
     */
    public void setValueBinding(String name, ValueBinding binding) {

        if ("selectedValues".equals(name)) {
            super.setValueBinding("value", binding);
        } else {
            super.setValueBinding(name, binding);
        }

    }

    /**
     * <p>Return any {@link ValueExpression} set for <code>value</code> if a
     * {@link ValueExpression} for <code>selectedValues</code> is requested;
     * otherwise, perform the default superclass processing for this method.</p>
     *
     * @param name Name of the attribute or property for which to retrieve
     *  a {@link ValueExpression}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     * @since 1.2
     */
    public ValueExpression getValueExpression(String name) {

        if ("selectedValues".equals(name)) {
            return (super.getValueExpression("value"));
        } else {
            return (super.getValueExpression(name));
        }

    }
    
    /**
     * <p>Store any {@link ValueExpression} specified for
     * <code>selectedValues</code> under <code>value</code> instead;
     * otherwise, perform the default superclass processing for this method.</p>
     *
     * @param name Name of the attribute or property for which to set
     *  a {@link ValueExpression}
     * @param binding The {@link ValueExpression} to set, or <code>null</code>
     *  to remove any currently set {@link ValueExpression}
     *
     * @throws NullPointerException if <code>name</code>
     *  is <code>null</code>
     * @since 1.2
     */
    public void setValueExpression(String name, ValueExpression binding) {

        if ("selectedValues".equals(name)) {
            super.setValueExpression("value", binding);
        } else {
            super.setValueExpression(name, binding);
        }

    }
    
    // --------------------------------------------------------- UIInput Methods


    /**
     * <p>Return <code>true</code> if the new value is different from the
     * previous value. Value comparison must not be sensitive to element order.
     * </p>
     *
     * @param previous old value of this component
     * @param value new value of this component
     */
    protected boolean compareValues(Object previous, Object value) {

        if ((previous == null) && (value != null)) {
            return (true);
        } else if ((previous != null) && (value == null)) {
            return (true);
        } else if ((previous == null)) {
            return (false);
        }

        boolean valueChanged = false;
        Object oldarray[];
        Object newarray[];

        // The arrays may be arrays of primitives;  for simplicity,
        // perform the boxing here.
        if (!(previous instanceof Object[])) {
            previous = toObjectArray(previous);
        }

        if (!(value instanceof Object[])) {
            value = toObjectArray(value);
        }

        // If values are still not of the type Object[], it is perhaps a
        // mistake by the renderers, so return false, so that
        // ValueChangedEvent is not queued in this case.
        if (!(previous instanceof Object[]) || 
              !(value instanceof Object[])) {
              return false;
        }
        oldarray = (Object[]) previous;
        newarray = (Object[])value;
       
        // If we got here then both the arrays cannot be null
        // if their lengths vary, return false.
        if ( oldarray.length != newarray.length) {
            return true;
        }
        
        // make sure every element in the previous array occurs the same
        // number of times in the current array. This should help us
        // to find out the values changed are not. Since we cannot assume
        // the browser will send the elements in the same order everytime,
        // it will not suffice to just compare the element position and position.
        int count1;
        int count2;
        for ( int i= 0; i < oldarray.length; ++i ) {
            count1 = countElementOccurrence(oldarray[i], oldarray);
            count2 = countElementOccurrence(oldarray[i], newarray);
            if ( count1 != count2 ) {
                valueChanged = true;
                break;
            }     
        }    
        return valueChanged;

    }    

    
    /**
     * <p>Return the number of occurrances of a particular element in the
     * array.</p>
     *
     * @param element object whose occurrance is to be counted in the array.
     * @param array object representing the old value of this component.
     */
    private static int countElementOccurrence(Object element, Object[] array) {

        int count = 0;
        for ( int i= 0; i < array.length; ++i ) {
            Object arrayElement = array[i];
            if (arrayElement != null && element != null) {
                if (arrayElement.equals(element)) {
                    count ++;
                }
            }
        }    
        return count;

    }    

    
    /**
     * Convert an array of primitives to an array of boxed objects.
     * @param primitiveArray object containing the primitive values
     * @return an Object array, or null if the incoming value is not
     * in fact an array at all.
     */
    private static Object[] toObjectArray(Object primitiveArray) {
        if (primitiveArray == null) {
            throw new NullPointerException();
        }
        
        if (primitiveArray instanceof Object[]) {
            return (Object[]) primitiveArray;
        }

        if (primitiveArray instanceof List) {
            return ((List) primitiveArray).toArray();
        }
          
        Class clazz = primitiveArray.getClass();
        if (!clazz.isArray()) {
            return null;
        }

        int length = Array.getLength(primitiveArray);
        Object[] array = new Object[length];
        for (int i = 0; i < length; i++) {
            array[i] = Array.get(primitiveArray, i);
        }

        return array;
    }

    // ------------------------------------------------------ Validation Methods

    

    /**
     * <p>In addition to the standard validation behavior inherited from
     * {@link UIInput}, ensure that any specified values are equal to one of
     * the available options.  Before comparing each option, coerce the
     * option value type to the type of this component's value following
     * the Expression Language coercion rules.  If the specified value is not 
     * equal to any of the options,  enqueue an error message
     * and set the <code>valid</code> property to <code>false</code>.</p>
     *
     * <p class="changed_modified_2_0">This method must explicitly
     * support a value argument that is a single value or a value
     * argument that is a <code>Collection</code> or Array of
     * values.</p>
     *
     * @param context The {@link FacesContext} for the current request
     *
     * @param value The converted value to test for membership.
     *
     * @throws NullPointerException if <code>context</code>
     *  is <code>null</code>
     */

    protected void validateValue(FacesContext context, Object value) {
        super.validateValue(context, value);

        // Skip validation if it is not necessary
        if (!isValid() || (value == null)) {
            return;
        }

        // Ensure that the values match one of the available options
        // Don't arrays cast to "Object[]", as we may now be using an array
        // of primitives
        Converter converter = getConverter();
        for (Iterator i = getValuesIterator(value); i.hasNext(); ) {
            Iterator items = new SelectItemsIterator(context, this);
            Object currentValue = i.next();
            if (!SelectUtils.matchValue(context,
                                        this,
                                        currentValue,
                                        items,
                                        converter)) {
                // Enqueue an error message if an invalid value was specified
                FacesMessage message =
                      MessageFactory.getMessage(context,
                                                INVALID_MESSAGE_ID,
                                                MessageFactory.getLabel(context, this));
                context.addMessage(getClientId(context), message);
                setValid(false);
                return;
            }
        }

    }


    // --------------------------------------------------------- Private Methods


    private Iterator getValuesIterator(Object value) {

        if (value instanceof Collection) {
            return ((Collection) value).iterator();
        } else {
            return (new ArrayIterator(value));
        }

    }


    // ---------------------------------------------------------- Nested Classes


    /**
     * Exposes an Array as an Iterator.
     */
    private static final class ArrayIterator implements Iterator {

        private int length;
        private int idx = 0;
        private Object value;


        // -------------------------------------------------------- Constructors


        ArrayIterator(Object value) {

            this.value = value;
            length = Array.getLength(value);

        }


        // ------------------------------------------------------------ Iterator


        public boolean hasNext() {
            return (idx < length);
        }


        public Object next() {

            if (idx >= length) {
                throw new NoSuchElementException();
            } else {
                return Array.get(value, idx++);
            }
            
        }


        public void remove() {

            throw new UnsupportedOperationException();

        }

    }
}
