/*
 * $Id: MethodExpressionActionListener.java,v 1.6 2007/04/27 22:00:08 ofung Exp $
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

package javax.faces.event;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.component.StateHolder;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.StringWriter;
import java.io.PrintWriter;
import javax.el.MethodNotFoundException;

/**
 * <p><strong><span
 * class="changed_modified_2_0">MethodExpressionActionListener</span></strong>
 * is an {@link ActionListener} that wraps a {@link
 * MethodExpression}. When it receives a {@link ActionEvent}, it
 * executes a method on an object identified by the {@link
 * MethodExpression}.</p>
 */

public class MethodExpressionActionListener implements ActionListener,
    StateHolder {

    private static final Logger LOGGER =
          Logger.getLogger("javax.faces.event", "javax.faces.LogStrings");
    

    // ------------------------------------------------------ Instance Variables

    private MethodExpression methodExpressionOneArg = null;
    private MethodExpression methodExpressionZeroArg = null;
    private boolean isTransient;
    private final static Class[] ACTION_LISTENER_ZEROARG_SIG = new Class[] { };

    public MethodExpressionActionListener() { }

    /**
     * <p><span class="changed_modified_2_0">Construct<span
     * class="changed_modified_2_0"> a {@link ValueChangeListener} that
     * contains a {@link MethodExpression}.  <span
     * class="changed_added_2_0">To accomodate method expression targets
     * that take no arguments instead of taking an {@link ActionEvent}
     * argument</span>, the implementation of this class must take the
     * argument <code>methodExpressionOneArg</code>, extract its
     * expression string, and create another
     * <code>MethodExpression</code> whose expected param types match
     * those of a zero argument method.  The usage requirements for both
     * of these <code>MethodExpression</code> instances are described in
     * {@link #processAction}.</span></p>
     *
     * @param methodExpressionOneArg a <code>MethodExpression</code>
     * that points to a method that returns <code>void</code> and takes
     * a single argument of type {@link ActionEvent}.
     */
    public MethodExpressionActionListener(MethodExpression methodExpressionOneArg) {

        super();
        this.methodExpressionOneArg = methodExpressionOneArg;
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        this.methodExpressionZeroArg = context.getApplication().
                getExpressionFactory().createMethodExpression(elContext, 
                  methodExpressionOneArg.getExpressionString(), Void.class, 
                  ACTION_LISTENER_ZEROARG_SIG);

    }

    public MethodExpressionActionListener(MethodExpression methodExpressionOneArg,
            MethodExpression methodExpressionZeroArg) {

        super();
        this.methodExpressionOneArg = methodExpressionOneArg;
        this.methodExpressionZeroArg = methodExpressionZeroArg;

    }

    // ------------------------------------------------------- Event Method

    /**
     * <p><span class="changed_modified_2_0">Call</span> through to the
     * {@link MethodExpression} passed in our constructor.  <span
     * class="changed_added_2_0">First, try to invoke the
     * <code>MethodExpression</code> passed to the constructor of this
     * instance, passing the argument {@link ActionEvent} as the
     * argument.  If a {@link MethodNotFoundException} is thrown, call
     * to the zero argument <code>MethodExpression</code> derived from
     * the <code>MethodExpression</code> passed to the constructor of
     * this instance.  If that fails for any reason, throw an {@link
     * AbortProcessingException}, including the cause of the
     * failure.</span></p>
     * 
     * @throws NullPointerException {@inheritDoc}     
     * @throws AbortProcessingException {@inheritDoc}     
     */
    public void processAction(ActionEvent actionEvent) throws AbortProcessingException {

        Throwable cause = null;
        Throwable thrown = null;
        if (actionEvent == null) {
            throw new NullPointerException();
        }
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        try {
            methodExpressionOneArg.invoke(elContext, new Object[] {actionEvent});
        } catch (MethodNotFoundException mnfe) {
          if (null != methodExpressionZeroArg) {
                try {
                    // try to invoke a no-arg version
                    methodExpressionZeroArg.invoke(elContext, new Object[]{});
                }
                catch (ELException ee) {
                    cause = ee.getCause();
                    thrown = ee;
                }
              
          }
        } catch (ELException ee) {
            cause = ee.getCause();
            thrown = ee;
        }
        if (null != thrown) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE,
                           "severe.event.exception_invoking_processaction",
                           new Object[]{
                                 cause == null ? thrown.getClass().getName() : cause.getClass().getName(),
                                 methodExpressionOneArg.getExpressionString(),
                                 actionEvent.getComponent().getId()
                           });
                StringWriter writer = new StringWriter(1024);
                if (cause == null) {
                    thrown.printStackTrace(new PrintWriter(writer));
                } else {
                    cause.printStackTrace(new PrintWriter(writer));
                }
                LOGGER.severe(writer.toString());
            }
            throw cause == null ? new AbortProcessingException(thrown.getMessage(), 
                    thrown) : new AbortProcessingException(thrown.getMessage(), cause);
            
        }
    }


    // ------------------------------------------------ Methods from StateHolder


    /**
     * <p class="changed_modified_2_0">Both {@link MethodExpression}
     * instances described in the constructor must be saved.</p>
     */
    public Object saveState(FacesContext context) {

        return new Object[] { methodExpressionOneArg, methodExpressionZeroArg  };

    }


    /**
     * <p class="changed_modified_2_0">Both {@link MethodExpression}
     * instances described in the constructor must be restored.</p>
     */
    public void restoreState(FacesContext context, Object state) {

        methodExpressionOneArg = (MethodExpression) ((Object[]) state)[0];
        methodExpressionZeroArg = (MethodExpression) ((Object[]) state)[1];

    }


    public boolean isTransient() {

        return isTransient;

    }


    public void setTransient(boolean newTransientValue) {

        isTransient = newTransientValue;

    }

}
