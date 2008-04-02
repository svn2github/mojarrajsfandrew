/*
 * $Id: ValidatorTag.java,v 1.17 2004/11/11 16:09:39 rogerk Exp $
 */

/*
 * Copyright 2004 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.webapp;

import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;



/**
 * <p><strong>ValidatorTag</strong> is a base class for all JSP custom actions
 * that create and register a <code>Validator</code> instance on the
 * {@link EditableValueHolder} associated with our most immediate surrounding instance
 * of a tag whose implementation class is a subclass of {@link UIComponentTag}.
 * To avoid creating duplicate instances when a page is redisplayed,
 * creation and registration of a {@link Validator} occurs
 * <strong>only</strong> if the corresponding {@link UIComponent} was
 * created (by the owning {@link UIComponentTag}) during the execution of the
 * current page.</p>
 *
 * <p>This class may be used directly to implement a generic validator
 * registration tag (based on the validator-id specified by the
 * <code>id</code> attribute), or as a base class for tag instances that
 * support specific {@link Validator} subclasses.  This <code>id</code>
 * attribute must refer to one of the well known validator-ids, or a
 * custom validator-id as defined in a <code>faces-config.xml</code>
 * file.</p>
 *
 * <p>Subclasses of this class must implement the
 * <code>createValidator()</code> method, which creates and returns a
 * {@link Validator} instance.  Any configuration properties that specify
 * the limits to be enforced by this {@link Validator} must have been
 * set by the <code>createValidator()</code> method.  Generally, this occurs
 * by copying corresponding attribute values on the tag instance.</p>
 *
 * <p>This tag creates no output to the page currently being created.  It
 * is used solely for the side effect of {@link Validator} creation.</p>
 */

public class ValidatorTag extends TagSupport {


    // ------------------------------------------------------------- Attributes

    public static final String INVALID_EXPRESSION_MESSAGE_ID = "javax.faces.el.INVALID_EXPRESSION";
    public static final String COMPONENT_FROM_TAG_ERROR_MESSAGE_ID = "javax.faces.webapp.COMPONENT_FROM_TAG_ERROR";
    public static final String NOT_NESTED_IN_TYPE_TAG_ERROR_MESSAGE_ID = "javax.faces.webapp.NOT_NESTED_IN_TYPE_TAG_ERROR";
    public static final String NOT_NESTED_IN_FACES_TAG_ERROR_MESSAGE_ID = "javax.faces.webapp.NOT_NESTED_IN_FACES_TAG_ERROR";
    public static final String CANT_CREATE_CLASS_ID = "javax.faces.webapp.CANT_CREATE_CLASS";

    /**
     * <p>The identifier of the {@link Validator} instance to be created.</p>
     */
    private String validatorId = null;
    
    /**
     * <p>The {@link ValueBinding} expression that evaluates to an object that 
     * implements {@link Validator}.</p>
     */
    private String binding = null;

    /**
     * <p>Set the identifer of the {@link Validator} instance to be created.
     *
     * @param validatorId The new identifier of the validator instance to be
     *                    created.
     */
    public void setValidatorId(String validatorId) {

        this.validatorId = validatorId;

    }

    /*
     * <p>Set the value binding expression of the {@link Validator} instance 
     * to be created.</p>
     *
     * @param binding The new value binding expression
     *
     * @throws JspException if a JSP error occurs
     */
    public void setBinding(String binding) 
        throws JspException {
        if (binding != null && !UIComponentTag.isValueReference(binding)) {
            Object[] params = {binding};
            FacesMessage message = MessageFactory.getMessage(
                INVALID_EXPRESSION_MESSAGE_ID, params);
            if (message != null) {
                throw new JspException(message.getSummary());
            } else {
                throw new JspException("Invalid Expression:"+binding);
            }
        }
        this.binding = binding;
    }
    // --------------------------------------------------------- Public Methods


    /**
     * <p>Create a new instance of the specified {@link Validator}
     * class, and register it with the {@link UIComponent} instance associated
     * with our most immediately surrounding {@link UIComponentTag} instance, if
     * the {@link UIComponent} instance was created by this execution of the
     * containing JSP page.</p>
     *
     * @exception JspException if a JSP error occurs
     */
    public int doStartTag() throws JspException {
        
        Validator validator = null;
        
        
        // Locate our parent UIComponentTag
        UIComponentTag tag =
            UIComponentTag.getParentUIComponentTag(pageContext);
        if (tag == null) { 
            Object[] params = {this.getClass().getName()};
            FacesMessage message = MessageFactory.getMessage(
                NOT_NESTED_IN_FACES_TAG_ERROR_MESSAGE_ID, params);
            if (message != null) {
                throw new JspException(message.getSummary());
            } else {
                throw new JspException("Not nested in a UIComponentTag Error for tag with handler class:"+
                    this.getClass().getName());
            }
        }

        // Nothing to do unless this tag created a component
        if (!tag.getCreated()) {
            return (SKIP_BODY);
        }
        
        UIComponent component = tag.getComponentInstance();
        if (component == null) {            
            FacesMessage message = MessageFactory.getMessage(
                COMPONENT_FROM_TAG_ERROR_MESSAGE_ID, null);
            if (message != null) {
                throw new JspException(message.getSummary());
            } else {
                throw new JspException("Can't create Component from tag.");
            }
        }
        if (!(component instanceof EditableValueHolder)) {
            Object params [] = {this.getClass().getName()};
            FacesMessage message = MessageFactory.getMessage(
                NOT_NESTED_IN_TYPE_TAG_ERROR_MESSAGE_ID, params);
            if (message != null) {
                throw new JspException(message.getSummary());
            } else {
                throw new JspException("Not nested in a tag of proper type. Error for tag with handler class:"+
                    this.getClass().getName());
            }
        }

        validator = createValidator();
        
        if (validator == null) {
            String validateError = null;
            if (binding != null) {
                validateError = binding;
            }
            if (validatorId != null) {
                if (validateError != null) {
                    validateError += " or " + validatorId;
                } else {
                    validateError = validatorId;
                }
            }
                
            Object params [] = {"javax.faces.validator.Validator",validateError};
            FacesMessage message = MessageFactory.getMessage(
                CANT_CREATE_CLASS_ID, params);
            if (message != null) {
                throw new JspException(message.getSummary());
            } else {
                throw new JspException("Can't create class of type:"+
                    "javax.faces.validator.Validator from:"+validateError);
            }
        }

        // Register an instance with the appropriate component
        ((EditableValueHolder)component).addValidator(validator);
        
        return (SKIP_BODY);

    }


    /**
     * <p>Release references to any acquired resources.
     */
    public void release() {

        this.id = null;

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Create and return a new {@link Validator} to be registered
     * on our surrounding {@link UIComponent}.</p>
     *
     * @exception JspException if a new instance cannot be created
     */
    protected Validator createValidator()
        throws JspException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        Validator validator = null;
        ValueBinding vb = null;
        
        // If "binding" is set, use it to create a validator instance.
        if (binding != null) {
            vb = context.getApplication().createValueBinding(binding);
            if (vb != null) {
                try {
                    validator = (Validator)vb.getValue(context);
                    if (validator != null) {
                        return validator;
                    }
                } catch (Exception e) {
                    throw new JspException(e);
                }
            }
        } 
        // If "validatorId" is set, use it to create the validator
        // instance.  If "validatorId" and "binding" are both set, store the 
        // validator instance in the value of the property represented by
        // the value binding expression.      
        if (validatorId != null) {
            try {
                String validatorIdVal = validatorId;
                if (UIComponentTag.isValueReference(validatorId)) {
                    ValueBinding idBinding = context.getApplication().createValueBinding(validatorId);
                    validatorIdVal = (String)idBinding.getValue(context);
                }
                validator = context.getApplication().createValidator(validatorIdVal);
                if (validator != null) { 
                    if (vb != null) {
                        vb.setValue(context, validator);
                    }
                }
            } catch (Exception e) {
                throw new JspException(e);
            }
        }
        return validator;
    }


}
