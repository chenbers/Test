package com.inthinc.pro.validators;

import static org.junit.Assert.assertTrue;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EmailValidatorTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIsValid() {
		UIComponent uiev = new HtmlInputText();
		EmailValidator ev = new EmailValidator();

		assertTrue(ev.isValid("t_estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t!estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("testi&ngemail@inthinc.com", uiev));
		assertTrue(ev.isValid("tes`tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("tes$tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("testin|gemail@inthinc.com", uiev));
		assertTrue(ev.isValid("te~stingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("testingem#ail@inthinc.com", uiev));
		assertTrue(ev.isValid("tes%tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("testingemail=@inthinc.com", uiev));
		assertTrue(ev.isValid("tes/tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t?estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t'estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t.estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t*estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t^estingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("tes-tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("tes+tingemail@inthinc.com", uiev));
		assertTrue(ev.isValid("testi{ngemail@inthinc.com", uiev));
		assertTrue(ev.isValid("t}estingemail@inthinc.com", uiev));

		assertTrue(ev.isValid("t_!&`$|~#%=/?'.*^-+{}estingemail@inthinc.com",
				uiev));

	}

}
