﻿ /**
* @class FCEnum
* @author FusionCharts Technologies LLP, www.fusioncharts.com
* @version 3.2
*
* Copyright (C) FusionCharts Technologies LLP, 2010

* This class represents a basic Enumeration object, which stores
* a series of elements with a sequential numeric id associated to it.
* We've named it as FCEnum instead of Enum, as Enum is a reserved keyword
* by Flash for future usage.
*/
dynamic class com.fusioncharts.helper.FCEnum 
{
	//Private variable iterator to keep currently accessed value's index
	//By default set it to 1 - start of array.
	private var _iterator : Number = 1;
	//Count of total elements in the enumeration.
	private var _size : Number = 0;
	/**
	* Constructor function that takes in an array of string literals
	* and allots a sequential id to each one of them.
	*/
	function FCEnum ()
	{
		if (arguments.length == 0)
		{
			//We cannot create an empty enumeration
			throw new Error ("Cannot create empty Enumeration");
		}
		//Loop Variable
		var i : Number;
		//If the first element in arguments is array, then it means
		//that we've to render an enumeration from array
		//If the enumeration items is passed as an array
		if (arguments [0] instanceof Array)
		{
			//Get the list of items
			var arrItems : Array = arguments [0];
			for (i = 0; i < arrItems.length; i ++)
			{
				this [arrItems [i]] = i + 1;
			}
			//Store the size
			_size = arrItems.length;
		} else 
		{
			//Iterate through the list of parameters passed
			//using the arguments collection
			for (i = 0; i < arguments.length; i ++)
			{
				//Save each literal in the array as array's key
				//and binary value as array's value.
				this [arguments [i]] = i + 1;
			}
			//Store the size
			_size = arguments.length;
		}
	}
	/**
	* Moves the sequential iterator to start position.
	*/
	public function moveToFirst () : Void 
	{
		_iterator = 1;
	}
	/**
	* During sequential fetch, this method checks whether there are
	* any more elements in the enumeration and returns a boolean value
	* indicating that.
	* @return Boolean value indicating whether there are any more items
	*			in the enumeration.
	*/
	public function hasMoreElements () : Boolean 
	{
		return (_iterator <= _size);
	}
	/**
	* Returns the name and value of the next item in enumeration during
	* a sequential fetch.
	* @return Object containing name and value. Return value is of Object
	*			type having two properties - name and value.
	*/
	public function nextElement () : Object 
	{
		//If it's already the last element, throw an error
		if ( ! hasMoreElements ())
		{
			throw new Error ("No more elements in enumeration");
		}
		var item : String;
		var rtn : Object = new Object ();
		for (item in this)
		{
			if (this [item] == _iterator)
			{
				//If the class item has a value equal to the required iterator
				//value, we create a return object and assign values to it
				rtn.name = item;
				rtn.value = this [item];
			}
		}
		//Increment iterator
		_iterator ++;
		//Return
		return rtn;
	}
}
