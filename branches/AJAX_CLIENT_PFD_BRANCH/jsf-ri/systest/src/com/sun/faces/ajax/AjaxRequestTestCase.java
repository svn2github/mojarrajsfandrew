package com.sun.faces.ajax;

import com.sun.faces.htmlunit.AbstractTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class AjaxRequestTestCase extends AbstractTestCase {

    public AjaxRequestTestCase(String name) {
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
        return (new TestSuite(AjaxRequestTestCase.class));
    }


    /**
     * Tear down instance variables required by this test case.
     */
    public void tearDown() {
        super.tearDown();
    }


    public void testAjaxCount() throws Exception {
        getPage("/faces/ajax/ajaxCount.xhtml");
        System.out.println("Start ajax count test");

        // First we'll check the first page was output correctly
        assertTrue(check("countForm:out1","0"));
        assertTrue(check("out2","1"));

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("countForm:button1");
        HtmlPage lastpage = (HtmlPage) button1.click();

        // Check that the ajax request succeeds
        assertTrue(check("countForm:out1","2"));

        // Check that the request did NOT update the rest of the page.
        assertTrue(check("out2","1"));
    }

    public void testAjaxRequestDefaultsButton() throws Exception {
        System.out.println("Starting Request Defaults Button Test");
        getPage("/faces/ajax/ajaxRequestDefaultsButton.xhtml");

        String out1 = "form1:out1";
        String out2 = "form1:out2";
        String out3 = "out3";
        String reload = "form1:reload";
        String reset1 = "form1:reset1";
        String reset2 = "form1:reset2";
        String reset3 = "form1:reset3";
        String reset4 = "form1:reset4";

        // First, we'll test to make sure the initial values come out right
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));

        // Reload the page
        HtmlSubmitInput button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, make the Ajax call to first reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset1);
        lastpage = (HtmlPage) button.click();

        // Check that the ajax request succeeds
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, make the Ajax call to second reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset2);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, make the Ajax call to third reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset3);
        lastpage = (HtmlPage) button.click();

        // Check the page did *not* update
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, Reload the page, to check that reset3 actually executed
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, make the Ajax call to fourth reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset4);
        lastpage = (HtmlPage) button.click();

        // Check the page did *not* update
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, Reload the page, to check that reset4 actually executed
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));
    }

    public void testAjaxRequestDefaultsButtonNoPrepend() throws Exception {
        System.out.println("Starting Request Defaults Button No Prepend Test");
        getPage("/faces/ajax/ajaxRequestDefaultsButtonNoPrepend.xhtml");

        String out1 = "out1";
        String out2 = "out2";
        String out3 = "out3";
        String reload = "reload";
        String reset1 = "reset1";
        String reset2 = "reset2";
        String reset3 = "reset3";
        String reset4 = "reset4";


        // First, we'll test to make sure the initial values come out right
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));

        // Reload the page
        HtmlSubmitInput button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, make the Ajax call to first reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset1);
        lastpage = (HtmlPage) button.click();

        // Check that the ajax request succeeds
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, make the Ajax call to second reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset2);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, make the Ajax call to third reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset3);
        lastpage = (HtmlPage) button.click();

        // Check the page did *not* update
        assertTrue(check(out1,"1"));
        assertTrue(check(out2,"2"));
        assertTrue(check(out3,"3"));

        // Now, Reload the page, to check that reset3 actually executed
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));

        // Reload the page
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();

        // Check that the page updated correctly
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, make the Ajax call to fourth reset button
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reset4);
        lastpage = (HtmlPage) button.click();

        // Check the page did *not* update
        assertTrue(check(out1,"3"));
        assertTrue(check(out2,"4"));
        assertTrue(check(out3,"5"));

        // Now, Reload the page, to check that reset4 actually executed
        button = (HtmlSubmitInput) lastpage.getHtmlElementById(reload);
        lastpage = (HtmlPage) button.click();
        assertTrue(check(out1,"0"));
        assertTrue(check(out2,"1"));
        assertTrue(check(out3,"2"));
    }

    public void testAjaxRequestDefaultsEdit() throws Exception {
        System.out.println("Starting Request Defaults Edit Test");
        getPage("/faces/ajax/ajaxRequestDefaultsEdit.xhtml");

        String out1 = "form1:out1";
        String out2 = "form1:out2";
        String out3 = "out3";
        String echo1Out = "form1:echo1Out";
        String echo2Out = "form1:echo2Out";
        String echo3Out = "form1:echo3Out";
        String echo4Out = "form1:echo4Out";
        String edit1 = "form1:edit1";
        String edit2 = "form1:edit2";
        String edit3 = "form1:edit3";
        String edit4 = "form1:edit4";
        String refresh = "form1:refresh";

        // First, we'll test to make sure the initial values come out right
        assertTrue(check(out1,"echo"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));
        assertTrue(check(echo1Out,""));
        assertTrue(check(echo2Out,""));
        assertTrue(check(echo3Out,""));
        assertTrue(check(echo4Out,""));

        // Next, enter data into first field
        HtmlTextInput echo1 = ((HtmlTextInput)lastpage.getHtmlElementById(edit1));
        echo1.focus();
        echo1.type("test1");
        echo1.blur();

        // Refresh the panel to check the listener fired
        HtmlSubmitInput button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo1Out,"test1"));
        assertTrue(check(out1,"test1"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into second field
        HtmlTextInput echo2 = ((HtmlTextInput)lastpage.getHtmlElementById(edit2));
        echo2.focus();
        echo2.type("test2");
        echo2.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo2Out,"test2"));
        assertTrue(check(out1,"test2"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into third field
        HtmlTextInput echo3 = ((HtmlTextInput)lastpage.getHtmlElementById(edit3));
        echo3.focus();
        echo3.type("test3");
        echo3.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo3Out,"test3"));
        assertTrue(check(out1,"test3"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into the fourth field
        HtmlTextInput echo4 = ((HtmlTextInput)lastpage.getHtmlElementById(edit4));
        echo4.focus();
        echo4.type("test4");
        echo4.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo4Out,"test4"));
        assertTrue(check(out1,"test4"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

    }

    public void testAjaxRequestDefaultsEditNoPrepend() throws Exception {
        System.out.println("Starting Request Defaults Edit No Prepend Test");
        getPage("/faces/ajax/ajaxRequestDefaultsEditNoPrepend.xhtml");

        String out1 = "out1";
        String out2 = "out2";
        String out3 = "out3";
        String echo1Out = "echo1Out";
        String echo2Out = "echo2Out";
        String echo3Out = "echo3Out";
        String echo4Out = "echo4Out";
        String edit1 = "edit1";
        String edit2 = "edit2";
        String edit3 = "edit3";
        String edit4 = "edit4";
        String refresh = "refresh";

        // First, we'll test to make sure the initial values come out right
        assertTrue(check(out1,"echo"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));
        assertTrue(check(echo1Out,""));
        assertTrue(check(echo2Out,""));
        assertTrue(check(echo3Out,""));
        assertTrue(check(echo4Out,""));

        // Next, enter data into first field
        HtmlTextInput echo1 = ((HtmlTextInput)lastpage.getHtmlElementById(edit1));
        echo1.focus();
        echo1.type("test1");
        echo1.blur();

        // Refresh the panel to check the listener fired
        HtmlSubmitInput button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo1Out,"test1"));
        assertTrue(check(out1,"test1"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into second field
        HtmlTextInput echo2 = ((HtmlTextInput)lastpage.getHtmlElementById(edit2));
        echo2.focus();
        echo2.type("test2");
        echo2.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo2Out,"test2"));
        assertTrue(check(out1,"test2"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into third field
        HtmlTextInput echo3 = ((HtmlTextInput)lastpage.getHtmlElementById(edit3));
        echo3.focus();
        echo3.type("test3");
        echo3.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo3Out,"test3"));
        assertTrue(check(out1,"test3"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));

        // Next, enter data into the fourth field
        HtmlTextInput echo4 = ((HtmlTextInput)lastpage.getHtmlElementById(edit4));
        echo4.focus();
        echo4.type("test4");
        echo4.blur();

        // Refresh the panel to check the listener fired
        button = lastpage.getHtmlElementById(refresh);
        button.click();
        assertTrue(check(echo4Out,"test4"));
        assertTrue(check(out1,"test4"));
        assertTrue(check(out2,"echo"));
        assertTrue(check(out3,"echo"));
    }

    public void testAjaxEvent() throws Exception {
        getPage("/faces/ajax/ajaxEvent.xhtml");
        System.out.println("Start ajax event test");

        // First we'll check the first page was output correctly
        assertTrue(check("countForm:out1","0"));
        assertTrue(check("out2","1"));

        // Submit the ajax request
        HtmlSubmitInput button1 = (HtmlSubmitInput) lastpage.getHtmlElementById("countForm:button1");
        HtmlPage lastpage = (HtmlPage) button1.click();

        // Check that the ajax request succeeds
        assertTrue(check("countForm:out1","2"));

        // Check that the request did NOT update the rest of the page.
        assertTrue(check("out2","1"));

        // Check that events were written to the page.
        String statusArea = "Name: countForm:button1 Event: begin ";
        statusArea = statusArea + "Name: countForm:button1 Event: complete " ;
        statusArea = statusArea + "Name: countForm:button1 Event: success " ;
        //System.out.println(statusArea);
        //System.out.println(getText("statusArea"));
        assertTrue(check("statusArea",statusArea));
    }

}
