/*
 * $Id: LifecycleStage.java,v 1.1 2002/03/13 17:59:32 eburns Exp $
 */

/*
 * Copyright 2002 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces;

/**
 * The interface to be implemented by objects which handle
 * processing of a particular stage in the JavaServer Faces
 * request lifecycle.  This interface should be implemented
 * by JavaServer Faces implementations to drive the processing
 * of a JavaServer Faces request.
 * @see FacesServlet#executeLifecycle
 * <p>
 * A JavaServer Faces implementation must provide implementations
 * of this interface for each of the following request lifecycle stages,
 * and the stages must be invoked in this sequence:
 * <ol>
 * <li>&quot;updateState&quot;: applies any saved UI state to the UI component tree</li>
 * <li>&quot;processEvents&quot;: dispatches any events generated by the request; this
 *     may also trigger validation and page flow</li>
 * <li>&quot;preRender&quot;: invokes the pre-render lifecycle stage on the UI component
 *     tree to allow for any pre-render processing</li>
 * <li>&quot;saveState&quot;: saves any UI component state which must be preserved across
 *      requests</li>
 * <li>&quot;render&quot;: invokes rendering for the UI component tree</li>
 * <li>&quot;dispose&quot;: invoked to cleanup any resources after response has been sent</li>
 * </ol>
 * </p>
 * Additionally, listeners can be optionally added for each stage using
 * the LifecycleStageListener interface. 
 * @see LifecycleStageListener
 * If a listener has been registered for a particular stage, a JavaServer
 * Faces implementation must invoke the listener's <code>willExcecute()</code> method
 * just prior to invoking <code>execute</code> on the stage, and must invoke
 * the listener's <code>didExecute()</code> method immediately after invoking 
 * <code>execute()</code>.
 *
 */
public interface LifecycleStage {

    /**
     * @return String containing the name of this lifecycle stage
     */
    public String getName();

    /**
     * Implements the specified lifecycle stage.
     * @param ctx the FacesContext object used to process the current request
     * @param root A TreeNavigator for this tree.
     * @return if true, continue with the next stage.
     */
    public boolean execute(FacesContext ctx, TreeNavigator root) throws FacesException;

}
