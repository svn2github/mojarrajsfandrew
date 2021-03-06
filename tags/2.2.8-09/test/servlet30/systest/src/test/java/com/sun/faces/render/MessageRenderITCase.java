/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
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

package com.sun.faces.render;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.sun.faces.htmlunit.HtmlUnitFacesITCase;
import junit.framework.Test;
import junit.framework.TestSuite;


public class MessageRenderITCase extends HtmlUnitFacesITCase {

    public MessageRenderITCase(String name) {
        super(name);
    }

    /**
     * Set up instance variables required by this test case.
     */
    public void setUp() throws Exception {
        super.setUp();
    }


    /**
     * Return the tests included in this test suite.
     */
    public static Test suite() {
        return (new TestSuite(MessageRenderITCase.class));
    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }


    public void testCommandButtonButton() throws Exception {
        getPage("/faces/render/messageRender.xhtml");


        // Check that ids were rendered
        try {
            lastpage.getHtmlElementById("testform1:msgs");
        } catch (Exception e) {
            fail("testform1:msgs not rendered");
        }
        try {
            lastpage.getHtmlElementById("testform1a:msgs");
        } catch (Exception e) {
            fail("testform1a:msgs not rendered");
        }
        try {
            lastpage.getHtmlElementById("testform2:msg");
        } catch (Exception e) {
            fail("testform2:msg not rendered");
        }

        // check that other ids weren't

        try {
            lastpage.getHtmlElementById("testform3:msgs");
            fail("testform3:msgs rendered - not correct");
        } catch (Exception e) {
            //  Success
        }
        try {
            lastpage.getHtmlElementById("testform3a:msgs");
            fail("testform3:msgs rendered - not correct");
        } catch (Exception e) {
            //  Success
        }
        try {
            lastpage.getHtmlElementById("testform4:msg");
            fail("testform4:msg rendered - not correct");
        } catch (Exception e) {
            //  Success
        }
    }

    public void testMessagesToolTip() throws Exception {
        HtmlPage page = getPage("/faces/message05.xhtml");
        String pageXml = page.asXml().replaceAll("\n","");
        pageXml = pageXml.replaceAll("\t","");
        pageXml = pageXml.replaceAll("\r","");
        String case1 = "<!-- Case 1: Expected output: Both summary and detail rendered. -->      This is the summary This is the detail";
        String case2 = "<!-- Case 2: Expected output: Both summary and detail rendered. Tooltip detail rendered. -->      <span title=" + '"' + "This is the detail" + '"' + ">        This is the summary This is the detail      </span>";
        String case3 = "<!-- Case 3: Expected output: Detail rendered. Tooltip detail rendered. -->      <span title=" + '"' + "This is the detail" + '"' + ">        This is the detail      </span>";
        String case4 = "!-- Case 4: Expected output: Detail rendered. Tooltip detail rendered. -->      <span title=" + '"' + "This is the detail" + '"' + ">        This is the detail      </span>";
        String case5 = "<!-- Case 5: Expected output: Both summary and detail rendered. Tooltip detail rendered. -->      <span title=" + '"' + "This is the detail" + '"' + ">        This is the summary This is the detail      </span>";
        String case6 = "<!-- Case 6: Expected output: Summary rendered. Tooltip detail rendered. -->      <span title=" + '"' + "This is the detail" + '"' + ">        This is the summary       </span>";
        assertTrue(-1 != pageXml.indexOf(case1));
        assertTrue(-1 != pageXml.indexOf(case2));
        assertTrue(-1 != pageXml.indexOf(case3));
        assertTrue(-1 != pageXml.indexOf(case4));
        assertTrue(-1 != pageXml.indexOf(case5));
        assertTrue(-1 != pageXml.indexOf(case6));

    }


}
