/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 1997-2010 Sun Microsystems, Inc. All rights reserved.
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

import java.util.List;


/**
 * <p class="changed_added_2_0">Classes that implement this interface
 * agree to maintain a list of {@link SystemEventListener} instances
 * for each kind of {@link SystemEvent} they can generate.  This
 * interface enables arbitrary Objects to act as the source for {@link
 * SystemEvent} instances.</p>
 *
 * <p>If the implementing class is a {@link
 * javax.faces.component.UIComponent} or is referenced by a
 * <code>UIComponent</code>, care must be taken to ensure that the
 * implementing class, and all the members of the list returned by
 * {@link #getListenersForEventClass} work correctly with the state
 * management system.  One way to ensure this is to have the class and
 * the list members implement {@link javax.faces.component.StateHolder}
 * or {@link java.io.Serializable}.</p>
 *
 * @since 2.0
 */

public interface SystemEventListenerHolder {

    /**
     * <div class="changed_added_2_0">
     * <p>Return a <code>List</code> of {@link SystemEventListener}
     * instances that have been installed into the class implementing
     * this interface.</p>
     * </div>
     */
    public List<SystemEventListener> getListenersForEventClass(Class<? extends SystemEvent> facesEventClass);

}
