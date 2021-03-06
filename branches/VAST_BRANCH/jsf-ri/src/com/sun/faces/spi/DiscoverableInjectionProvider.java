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

package com.sun.faces.spi;

import com.sun.faces.util.Util;

/**
 * <p><code>InjectionProvider</code>s that implement this interface
 * can be configured via <code>META-INF/services/com.sun.faces.spi.InjectionProvider</code>.
 *
 * <p>The format of the configuration entries is:</p>
 * <ul>
 *   <li><code>&lt;InjectionProviderClassName&gt;:&lt;DelegateClassName&gt;</code></li>
 * <ul>
 *
 * <p>Example:</p}
 * <ul>
 *    <li><code>com.sun.faces.vendor.GlassFishInjectionProvider:com.sun.enterprise.InjectionManager</code></li>
 * </ul>
 *
 * <p>Multiple <code>DiscoverableInjectionProvider</code>s can be configured
 * within a single services entry.</p>
 */
public abstract class DiscoverableInjectionProvider implements InjectionProvider {


    /**
     * @param delegateClass the name of the delegate used by the
     *  <code>InjectionProvider</code> implementation.
     * @return returns <code>true</code> if the
     *  <code>InjectionProvider</code> instance
     *  is appropriate for the container its currently
     *  deployed within, otherwise return <code>false</code>
     */
    public static boolean isInjectionFeatureAvailable(String delegateClass) {

        try {
            Util.loadClass(delegateClass, null);
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }

}
