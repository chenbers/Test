package com.inthinc.pro.validators;

import static org.junit.Assert.assertTrue;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputTextarea;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class MultipleEmailValidatorTest {

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

		UIComponent uimev = new HtmlInputTextarea();
		MultipleEmailValidator meval = new MultipleEmailValidator();

		assertTrue(meval.isValid("t_estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("te!stingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("test&ingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t`estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("testinge$mail@inthinc.com", uimev));
		assertTrue(meval.isValid("testi|ngemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t~estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("testingemail#@inthinc.com", uimev));
		assertTrue(meval.isValid("t%estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t=estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("testin/gemail@inthinc.com", uimev));
		assertTrue(meval.isValid("testingem?ail@inthinc.com", uimev));
		assertTrue(meval.isValid("test'ingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t.estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t*estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t^estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("test-ingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t+estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t{estingemail@inthinc.com", uimev));
		assertTrue(meval.isValid("t}estingemail@inthinc.com", uimev));

		assertTrue(meval.isValid(
				"t_!&`$|~#%=/?'.*^-+{}estingemail@inthinc.com", uimev));

	}

}
