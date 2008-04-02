/*
 * $Id: UISelectItemBaseTestCase.java,v 1.9 2003/09/23 21:33:49 jvisvanathan Exp $
 */

/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package javax.faces.component.base;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItem;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.TestUtil;
import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * <p>Unit tests for {@link UISelectItemBase}.</p>
 */

public class UISelectItemBaseTestCase extends ValueHolderTestCaseBase {


    // ------------------------------------------------------------ Constructors


    /**
     * Construct a new instance of this test case.
     *
     * @param name Name of the test case
     */
    public UISelectItemBaseTestCase(String name) {
        super(name);
    }


    // ---------------------------------------------------- Overall Test Methods


    // Set up instance variables required by this test case.
    public void setUp() {
        super.setUp();
        component = new UISelectItemBase();
        expectedRendererType = null;
    }

    
    // Return the tests included in this test case.
    public static Test suite() {
        return (new TestSuite(UISelectItemBaseTestCase.class));
    }


    // Tear down instance variables required by ths test case
    public void tearDown() {
        super.tearDown();
    }


    // ------------------------------------------------- Individual Test Methods


    // Test attribute-property transparency
    public void testAttributesTransparency() {

        super.testAttributesTransparency();
        UISelectItem selectItem = (UISelectItem) component;

        assertEquals(selectItem.getItemDescription(),
                     (String) selectItem.getAttributes().get("itemDescription"));
        selectItem.setItemDescription("foo");
        assertEquals("foo", (String) selectItem.getAttributes().get("itemDescription"));
        selectItem.setItemDescription(null);
        assertNull((String) selectItem.getAttributes().get("itemDescription"));
        selectItem.getAttributes().put("itemDescription", "bar");
        assertEquals("bar", selectItem.getItemDescription());
        selectItem.getAttributes().put("itemDescription", null);
        assertNull(selectItem.getItemDescription());

        assertEquals(selectItem.getItemLabel(),
                     (String) selectItem.getAttributes().get("itemLabel"));
        selectItem.setItemLabel("foo");
        assertEquals("foo", (String) selectItem.getAttributes().get("itemLabel"));
        selectItem.setItemLabel(null);
        assertNull((String) selectItem.getAttributes().get("itemLabel"));
        selectItem.getAttributes().put("itemLabel", "bar");
        assertEquals("bar", selectItem.getItemLabel());
        selectItem.getAttributes().put("itemLabel", null);
        assertNull(selectItem.getItemLabel());

        assertEquals(selectItem.getItemValue(),
                     (String) selectItem.getAttributes().get("itemValue"));
        selectItem.setItemValue("foo");
        assertEquals("foo", (String) selectItem.getAttributes().get("itemValue"));
        selectItem.setItemValue(null);
        assertNull((String) selectItem.getAttributes().get("itemValue"));
        selectItem.getAttributes().put("itemValue", "bar");
        assertEquals("bar", selectItem.getItemValue());
        selectItem.getAttributes().put("itemValue", null);
        assertNull(selectItem.getItemValue());

    }


    // Test a pristine UISelectItemBase instance
    public void testPristine() {

        super.testPristine();
        UISelectItem selectItem = (UISelectItem) component;

        assertNull("no itemDescription", selectItem.getItemDescription());
        assertNull("no itemLabel", selectItem.getItemLabel());
        assertNull("no itemValue", selectItem.getItemValue());

    }


    // Test setting properties to invalid values
    public void testPropertiesInvalid() throws Exception {

        super.testPropertiesInvalid();
        UISelectItem selectItem = (UISelectItem) component;

    }


    // Test setting properties to valid values
    public void testPropertiesValid() throws Exception {

        super.testPropertiesValid();
        UISelectItem selectItem = (UISelectItem) component;

        selectItem.setItemDescription("foo");
        assertEquals("foo", selectItem.getItemDescription());
        selectItem.setItemDescription(null);
        assertNull(selectItem.getItemDescription());

        selectItem.setItemLabel("foo");
        assertEquals("foo", selectItem.getItemLabel());
        selectItem.setItemLabel(null);
        assertNull(selectItem.getItemLabel());

        selectItem.setItemValue("foo");
        assertEquals("foo", selectItem.getItemValue());
        selectItem.setItemValue(null);
        assertNull(selectItem.getItemValue());

    }

    public void testStateHolder() {

        UIComponent testParent = new TestComponentNamingContainer("root");
	UISelectItem
	    preSave = null,
	    postSave = null;
	Object state = null;

	// test selectItem with nothing
	testParent.getChildren().clear();
	preSave = new UISelectItemBase();
	preSave.setId("selectItem");
	preSave.setRendererType(null); // necessary: we have no renderkit
	testParent.getChildren().add(preSave);
        preSave.getClientId(facesContext);
	state = preSave.saveState(facesContext);
	assertTrue(null != state);
	testParent.getChildren().clear();
	
	postSave = new UISelectItemBase();
	postSave.setId("selectItem");
	testParent.getChildren().add(postSave);
        try {
	    postSave.restoreState(facesContext, state);
	}
	catch (Throwable e) {
	    assertTrue(false);
	}
	assertTrue(propertiesAreEqual(facesContext, preSave, postSave));

	// test selectItem with value only
	testParent.getChildren().clear();
	preSave = new UISelectItemBase();
	preSave.setId("selectItem");
	preSave.setRendererType(null); // necessary: we have no renderkit
	preSave.setItemValue("value");
	testParent.getChildren().add(preSave);
        preSave.getClientId(facesContext);
	state = preSave.saveState(facesContext);
	assertTrue(null != state);
	testParent.getChildren().clear();
	
	postSave = new UISelectItemBase();
	postSave.setId("selectItem");
	testParent.getChildren().add(postSave);
	try {
	    postSave.restoreState(facesContext, state);
	}
	catch (Throwable e) {
	    assertTrue(false);
	}
	assertTrue(propertiesAreEqual(facesContext, preSave, postSave));

	// test selectItem with the works
	testParent.getChildren().clear();
	preSave = new UISelectItemBase();
	preSave.setId("selectItem");
	preSave.setRendererType(null); // necessary: we have no renderkit
	preSave.setItemDescription("description");
	preSave.setItemLabel("label");
	preSave.setItemValue("value");
	testParent.getChildren().add(preSave);
        preSave.getClientId(facesContext);
	state = preSave.saveState(facesContext);
	assertTrue(null != state);
	testParent.getChildren().clear();
	
	postSave = new UISelectItemBase();
	postSave.setId("selectItem");
	testParent.getChildren().add(postSave);
	try {
	    postSave.restoreState(facesContext, state);
	}
	catch (Throwable e) {
	    assertTrue(false);
	}
	assertTrue(propertiesAreEqual(facesContext, preSave, postSave));
    }
     
    // -------------------------------------------------------- Support Methods


    protected ValueHolder createValueHolder() {

        UIComponent component = new UISelectItemBase();
        component.setRendererType(null);
        return ((ValueHolder) component);

    }


    protected boolean propertiesAreEqual(FacesContext context,
					 UIComponent comp1,
					 UIComponent comp2) {
	if (super.propertiesAreEqual(context, comp1, comp2)) {
	    UISelectItem 
		selectItem1 = (UISelectItem) comp1,
		selectItem2 = (UISelectItem) comp2;
	    // if their not both null, or not the same string
	    if (!TestUtil.equalsWithNulls(selectItem1.getItemLabel(),
					  selectItem2.getItemLabel())) {
		return false;
	    }
	    // if their not both null, or not the same string
	    if (!TestUtil.equalsWithNulls(selectItem1.getItemDescription(),
					  selectItem2.getItemDescription())) {
		return false;
	    }
	    // if their not both null, or not the same string
	    if (!TestUtil.equalsWithNulls(selectItem1.getItemValue(),
					  selectItem2.getItemValue())) {
		return false;
	    }
	}
	return true;
    }



}
