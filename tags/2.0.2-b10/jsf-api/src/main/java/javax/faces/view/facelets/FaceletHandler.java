/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2008 Sun Microsystems, Inc. All rights reserved.
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
 *
 * This file incorporates work covered by the following copyright and
 * permission notice:
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package javax.faces.view.facelets;

import java.io.IOException;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;

/**
 * <p class="changed_added_2_0">This is the root class for markup
 * elements in Facelets VDL.  Facelets is XHTML, and XHTML is XML, and
 * the root unit of abstraction in XML is the element.  A
 * <code>FaceletHandler</code> instance represents an XML element at
 * runtime.  Two direct implementations exist to embody the contract for
 * more specific behavior.</p>
 *
 * <div class="changed_added_2_0">

 * <dl>

 * <dt>{@link CompositeFaceletHandler}</dt>

 * <dd>A container for other <code>FaceletHandler</code>s</dd>

 * <dt>{@link TagHandler}</dt>

 * <dd>The foundation class for <code>FaceletHandler</code>s associated
 * with markup in a Facelet document.</dd>

 * </dl>

 * </div>

 * @since 2.0
 */
public interface FaceletHandler {

    /**
     * <p class="changed_added_2_0">Process changes on a particular
     * UIComponent</p>
     * 
     * @param ctx the current FaceletContext instance for this execution
     * @param parent the parent UIComponent to operate upon

     * @throws IOException if unable to load <code>relativePath</code>

     * @throws FaceletException if unable to parse the markup loaded from <code>relativePath</code>

     * @throws FacesException if unable to create child <code>UIComponent</code> instances

     * @throws ELException if any of the expressions in the markup
     * loaded from <code>relativePath</code> fail

     * @since 2.0

     */
    public void apply(FaceletContext ctx, UIComponent parent)
    throws IOException;
}
