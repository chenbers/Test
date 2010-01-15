package com.inthinc.pro.util;

import java.util.Iterator;

public class CircularIterator<E> implements Iterator<E> {

	Iterable<E> iterable;
	Iterator<E> iterator;
	boolean nonEmptyCollection;
	
	public CircularIterator(Iterable<E> iterable) {

		this.iterable = iterable;
		if (iterable != null){
			
			iterator = this.iterable.iterator();
			nonEmptyCollection = iterator.hasNext();
		}
		else {
			
			iterator = null;
			nonEmptyCollection = false;
		}
	}

	@Override
	public boolean hasNext() {
		
		return nonEmptyCollection;
	}

	@Override
	public E next() {
		
		if(hasNext()){
			
			if (iterator.hasNext()) return iterator.next();
			iterator = iterable.iterator();
			if (iterator.hasNext()) return iterator.next();
			return null;
		}
		else{ 
			
			return null;
		}
	}

	@Override
	public void remove() {
		
		if(hasNext()){
		
			iterator.remove();
			if(!iterator.hasNext()){
				
				nonEmptyCollection = false;
			}
		}
	}

}
