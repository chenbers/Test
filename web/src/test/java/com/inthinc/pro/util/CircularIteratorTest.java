package com.inthinc.pro.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CircularIteratorTest {
	

	@Test
	public void circularIteratorTest(){
		
		//Test for null stuff
		CircularIterator<Integer> ci = new CircularIterator<Integer>(null);
		Assert.assertFalse(ci.hasNext());
		Assert.assertNull(ci.next());
		
		//See what happens with a no elements element
		List<Integer> list = new ArrayList<Integer>();
		ci = new CircularIterator<Integer>(list);
		Assert.assertFalse(ci.hasNext());
		Assert.assertNull(ci.next());
		
		//With 1 element
		list.add(0);
		
		ci= new CircularIterator<Integer>(list);
		Assert.assertTrue(ci.hasNext());
		for(int i = 0; i<10;i++){
			
			Assert.assertEquals(0, ci.next().intValue());
		}
		//What if you remove the only element
		ci.remove(); //Should remove 0
		Assert.assertFalse(ci.hasNext());
		Assert.assertNull(ci.next());

		for(int i = 0; i<10; i++){
			
			list.add(i);
		}
		
		//Add some more, see if it cycles round ok
		ci= new CircularIterator<Integer>(list);
		Assert.assertTrue(ci.hasNext());
		for(int i = 0; i<10;i++){
			
			Assert.assertEquals(i, ci.next().intValue());
		}
		Assert.assertTrue(ci.hasNext());
		for(int i = 0; i<10;i++){
			
			Assert.assertEquals(i, ci.next().intValue());
		}
		//Remove an element, does it behave like a regular iterator
		ci= new CircularIterator<Integer>(list);
		ci.next();
		ci.remove(); //Should remove 0
		Assert.assertEquals(1, ci.next().intValue());
		for(int i = 2; i<10;i++){
			
			Assert.assertEquals(i, ci.next().intValue());
		}
		Assert.assertTrue(ci.hasNext());
		for(int i = 1; i<10;i++){
			
			Assert.assertEquals(i, ci.next().intValue());
		}
	}
}
