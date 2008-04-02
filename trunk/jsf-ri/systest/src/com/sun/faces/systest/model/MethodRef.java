/*
 * $Id: MethodRef.java,v 1.13 2006/03/29 22:38:52 rlubke Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.systest.model;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import com.sun.faces.systest.TestValueChangeListener;

public class MethodRef extends Object {


    protected String buttonPressedOutcome = null;


    protected String changeOutcome;


    protected String validateOutcome;

    protected UIInput inputField = null;

    // ------------------------------------------------------------ Constructors


    public MethodRef() {
    }

    // ---------------------------------------------------------- Public Methods


    public String getButtonPressedOutcome() {

        return buttonPressedOutcome;

    }


    public void setButtonPressedOutcome(String newButtonPressedOutcome) {

        buttonPressedOutcome = newButtonPressedOutcome;

    }


    public String getChangeOutcome() {

        return changeOutcome;

    }


    public void setChangeOutcome(String newChangeOutcome) {

        changeOutcome = newChangeOutcome;

    }


    public UIInput getInputField() {

        if (inputField == null) {
            inputField = new UIInput();
            inputField.addValueChangeListener(new TestValueChangeListener());
            Class args[] = {ValueChangeEvent.class};
            MethodBinding mb =
                  FacesContext.getCurrentInstance().getApplication().
                        createMethodBinding("#{methodRef.inputFieldValueChange}",
                                            args);
            inputField.setValueChangeListener(mb);
        }
        return inputField;

    }


    public void setInputField(UIInput input) {

        this.inputField = input;

    }


    public String getValidateOutcome() {

        return validateOutcome;

    }


    public void setValidateOutcome(String newValidateOutcome) {

        validateOutcome = newValidateOutcome;

    }


    public String button1Pressed() {

        setButtonPressedOutcome("button1 was pressed");
        return null;

    }


    public String button2Pressed() {

        setButtonPressedOutcome("button2 was pressed");
        return null;

    }


    public void button3Pressed(ActionEvent event) {

        setButtonPressedOutcome(event.getComponent().getId() +
                                " was pressed");

    }


    public void inputFieldValueChange(ValueChangeEvent vce) {

        vce.getComponent().getAttributes().put("onblur",
                                               vce.getNewValue().toString());

    }


    public String invalidateSession() {

        FacesContext fContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)
              fContext.getExternalContext().getSession(true);
        session.invalidate();
        return null;

    }


    public void validateInput(FacesContext context, UIComponent toValidate,
                              Object valueObj) {

        String value = (String) valueObj;
        if (!value.equals("batman")) {
            throw new ValidatorException(new FacesMessage(
                  "You didn't enter batman",
                  "You must enter batman"));
        }

    }


    public void valueChange(ValueChangeEvent vce) {

        vce.getComponent().getAttributes().put("onblur",
                                               vce.getNewValue().toString());
        setChangeOutcome(vce.getNewValue().toString());

    }

}


