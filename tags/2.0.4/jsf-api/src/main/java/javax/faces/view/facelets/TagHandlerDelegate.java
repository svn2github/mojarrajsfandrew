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

package javax.faces.view.facelets;

import java.io.IOException;
import javax.faces.component.UIComponent;


/**
 * <p class="changed_added_2_0">Abstract class that defines methods
 * relating to helping tag handler instances.  This abstraction enables
 * implementation details to be hidden by the JSF implementation while
 * still allowing concrete classes to be defined for extension by
 * users.</p>
 * 
 * @since 2.0
 */

public abstract class TagHandlerDelegate {

    /**
     * <p class="changed_added_2_0">Return a {@link MetaRuleset}
     * particular to this kind of tag handler.  Called from classes that
     * implement {@link MetaTagHandler}.</p>
     *
     * @param type the <code>Class</code> for which the
     * <code>MetaRuleset</code> must be created.
     *
     * @since 2.0
     */ 
    
    public abstract MetaRuleset createMetaRuleset(Class type);
    

    /**
     * <p class="changed_added_2_0">Called by classes that implement
     * {@link javax.faces.view.facelets.FaceletHandler} in their
     * implementation of <code>apply()</code>.</p>
     *
     * @param ctx the <code>FaceletContext</code> for this request
     *
     * @param comp the <code>UIComponent</code> that corresponds to this
     * element.
     *
     */
    public abstract void apply(FaceletContext ctx, UIComponent comp)
    throws IOException;

}
