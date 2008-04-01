/*
 * $Id: TextEntry_InputTag.java,v 1.12 2001/12/20 22:26:42 ofung Exp $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

// TextEntry_InputTag.java

package com.sun.faces.taglib.html_basic;

import org.mozilla.util.Assert;
import org.mozilla.util.Debug;
import org.mozilla.util.Log;
import org.mozilla.util.ParameterCheck;

import javax.faces.Constants;
import javax.faces.FacesException;
import javax.faces.RenderContext;
import javax.faces.Renderer;
import javax.faces.RenderKit;
import javax.faces.WTextEntry;
import javax.faces.ObjectTable;

import java.util.Vector;
import javax.servlet.http.*;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 *  <B>TextEntry_InputTag</B> is a class ...
 *
 * <B>Lifetime And Scope</B> <P>
 *
 * @version $Id: TextEntry_InputTag.java,v 1.12 2001/12/20 22:26:42 ofung Exp $
 * @author Jayashri Visvanathan
 * 
 *
 */

public class TextEntry_InputTag extends TagSupport
{
    //
    // Protected Constants
    //

    //
    // Class Variables
    //

    //
    // Instance Variables
    //

    // Attribute Instance Variables
    private String name = null;
    private String value = null;
    private String size = null;
    private String model = null;
    private String scope = null;
    private String valueChangeListener = null;
    private String maxlength = null;
    
    // Relationship Instance Variables

    //
    // Constructors and Initializers
    //

    public TextEntry_InputTag()
    {
        super();
        // ParameterCheck.nonNull();
        this.init();
    }

    protected void init()
    {
        // super.init();
    }

    
    //
    // Class methods
    //

    //
    // General Methods
    //

    /**
     * Renders TextEntry_InputTag's start tag and its attributes.
     */
    public int doStartTag() throws JspException{
  
        Assert.assert_it( pageContext != null ); 

        // PENDING(visvan) use tagext class to validate attributes.
        // get ObjectTable from ServletContext.
        ObjectTable ot = (ObjectTable) pageContext.getServletContext().
                getAttribute(Constants.REF_OBJECTTABLE);
        Assert.assert_it( ot != null );

        RenderContext rc = (RenderContext)ot.get(pageContext.getSession(),
                Constants.REF_RENDERCONTEXT);
        Assert.assert_it( rc != null );

        if ( name != null ) {

            // 1. Get or create the component instance.
            //
            WTextEntry c = (WTextEntry) ot.get(pageContext.getRequest(), name);
            if (c == null) {
                c = createComponent(rc);
                addToScope(c, ot); 
            }

            // 2. Render the component.
            //
            try {
                c.setRendererName(rc, "InputRenderer");
                c.render(rc);
            } catch (java.io.IOException e) {
                throw new JspException("Problem rendering component: "+
                    e.getMessage());
            } catch (FacesException f) {
                throw new JspException("Problem rendering component: "+
                    f.getMessage());
            }
        }

        return(EVAL_BODY_INCLUDE);
    }
    
    /**
     * End Tag Processing
     */
    public int doEndTag() throws JspException{

        Assert.assert_it( pageContext != null );
        // get ObjectTable from ServletContext.
        ObjectTable ot = (ObjectTable)pageContext.getServletContext().
                 getAttribute(Constants.REF_OBJECTTABLE);
        Assert.assert_it( ot != null );
        RenderContext rc =
            (RenderContext)ot.get(pageContext.getSession(),
            Constants.REF_RENDERCONTEXT);
        Assert.assert_it( rc != null );

//PENDING(rogerk)can we eliminate this extra get if component is instance
//variable? If so, threading issue?
//
        WTextEntry c = (WTextEntry) ot.get(pageContext.getRequest(), name);
        Assert.assert_it( c != null );

        // Complete the rendering process
        //
        try {
            c.renderComplete(rc);
        } catch (java.io.IOException e) {
            throw new JspException("Problem completing rendering: "+
                e.getMessage());
        } catch (FacesException f) {
            throw new JspException("Problem completing rendering: "+
                f.getMessage());
        }

        return EVAL_PAGE;
    }

    /**
     * Tag cleanup method.
     */
    public void release() {

        super.release();

        name = null;
        value = null;
        size = null;
        maxlength = null;
        model = null;
        scope = null;
        valueChangeListener = null;
    }

    /** Adds the component and listener to the ObjectTable
     * in the appropriate scope
     *
     * @param c WComponent to be stored in namescope
     * @param ot Object pool
     */
    public void addToScope(WTextEntry c, ObjectTable ot) {
    
        // PENDING ( visvan ) right now, we are not saving the state of the
        // components. So if the scope is specified as reques, when the form
        // is resubmitted we would't be able to retrieve the state of the
        // components. So to get away with that we are storing in session
        // scope. This should be fixed later.
        ot.put(pageContext.getSession(), name, c);

        if ( valueChangeListener != null ) {
            String lis_name = name.concat(Constants.REF_VALUECHANGELISTENERS);
            Vector listeners = (Vector) ot.get(pageContext.getRequest(), lis_name);
            if ( listeners == null) {
                listeners = new Vector();
            }    
            // this vector contains only the name of the listeners. The
            // listener itself is stored in the objectTable. We do this
            // because if the listeners are stored in the components, then
            // they have to exist for the event listeners to be dispatched 
            // at the time we process the events.
            // According to the spec, listeners should be dispatched
            // independent of components. 
            listeners.add(valueChangeListener);
            ot.put(pageContext.getSession(),lis_name, listeners);
        }
    }
    
    /**
     * Creates a TextEntry component and sets renderer specific
     * properties.
     *
     * @param rc renderContext client information
     */
    protected WTextEntry createComponent(RenderContext rc) throws
            JspException {
        WTextEntry c = new WTextEntry();
        // set renderer specific properties 
        c.setAttribute(rc, "name", name);
        c.setAttribute(rc, "size", size);
        c.setAttribute(rc, "maxlength", maxlength);
        
        // If model attribute is not found get it 
        // from parent form if it exists. If not
        // set text as an attribute so that it can be
        // used during rendering.

        // PENDING ( visvan )
        // make sure that the model object is registered
        if ( model != null ) {
            c.setModel(model);
        } else {
            // PENDING ( visvan ) all tags should implement a common
            // interface ??
            FormTag ancestor = null;
            try {
                ancestor = (FormTag) findAncestorWithClass(this,
                    FormTag.class);
               String model_str = ancestor.getModel();
               if ( model_str != null ) {
                   model = "$" + model_str + "." + name;
                   c.setModel(model);
               } 
            } catch ( Exception e ) {
                // If form tag cannot be found then model is null
            }
        }
        if ( value != null ) {
            c.setText(rc,value);
        }
        return c;
    }

    /**
     * Returns the value of the "name" attribute
     *
     * @return String value of "name" attribute
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the "name" attribute
     * @param name value of "name" attribute 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the value of the "value" attribute
     *
     * @return String value of "value" attribute
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Sets "value" attribute
     * @param value value of "value" attribute
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the size attribute
     * @param size value of size attribute
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Returns the value of size attribute
     *
     * @return String value of size attribute
     */
    public String getSize() {
        return this.size;
    }

    /**
     * Sets  maxlength attribute
     * @param  maxlength value of maxlength attribute
     */
    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

   /**
     * Returns the value of maxlength attribute
     *
     * @return String value of maxlength attribute
     */
    public String getMaxlength() {
        return this.maxlength;
    }
    
    /**
     * Returns the value of valueChangeListener attribute
     *
     * @return String value of valueChangeListener attribute
     */
    public String getValueChangeListener() {
        return this.valueChangeListener;
    }

    /**
     * Sets valueChangeListener attribute
     * @param change_listener value of formListener attribute
     */
    public void setValueChangeListener(String change_listener) {
        this.valueChangeListener = change_listener;
    }

    /**
     * Returns the value of the scope attribute
     *
     * @return String value of scope attribute
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * Sets scope attribute
     * @param scope value of scope attribute
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

    /**
     * Returns the value of the model attribute
     *
     * @return String value of model attribute
     */
    public String getModel() {
        return this.model;
    }

    /**
     * Sets the model attribute
     * @param model value of model attribute
     */
    public void setModel(String model) {
        this.model = model;
    }


} // end of class TextEntry_InputTag
