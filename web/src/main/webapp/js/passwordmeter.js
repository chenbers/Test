/*
---------------------------------------------------------------
Copyright (c) 2006 Steve Moitozo

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following
conditions:

   The above copyright notice and this permission notice shall
be included in all copies or substantial portions of the
Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY
KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.
---------------------------------------------------------------
 
===================================
Password Strength Factors and Weightings

password length:
level 0 (3 point): less than 4 characters
level 1 (6 points): between 5 and 7 characters
level 2 (12 points): between 8 and 15 characters
level 3 (18 points): 16 or more characters

letters:
level 0 (0 points): no letters
level 1 (5 points): all letters are lower case
level 2 (7 points): letters are mixed case

numbers:
level 0 (0 points): no numbers exist
level 1 (5 points): one number exists
level 1 (7 points): 3 or more numbers exists

special characters:
level 0 (0 points): no special characters
level 1 (5 points): one special character exists
level 2 (10 points): more than one special character exists

combinations:
level 0 (1 points): letters and numbers exist
level 1 (1 points): mixed case letters
level 1 (2 points): letters, numbers and special characters 
					exist
level 1 (2 points): mixed case letters, numbers and special 
					characters exist


************************************************************ */
//TODO: jwimmer: following lines not working in IE 8
var formSubmit = document.getElementById("formSubmit");
var meterFull = document.getElementById("meterFull");
var strengthMsg = document.getElementById("strengthMsg");

function testPasswordStrength(passwd, minScore)
{
	var intScore   = 0;
	var strVerdict = 0;
	
	// PASSWORD LENGTH
	if (passwd.length>0 && passwd.length<5) // length between 1 and 4
	{
		intScore = (intScore+3);
	}
	else if (passwd.length>4 && passwd.length<8) // length between 5 and 7
	{
		intScore = (intScore+6);
	}
	else if (passwd.length>7 && passwd.length<12)// length between 8 and 15
	{
		intScore = (intScore+12);
	}
	else if (passwd.length>11)                    // length 16 or more
	{
		intScore = (intScore+18);
	}
	
	
	// LETTERS (Not exactly implemented as dictated above because of my limited understanding of Regex)
	if (passwd.match(/[a-z]/))                              // [verified] at least one lower case letter
	{
		intScore = (intScore+1);
	}
	
	if (passwd.match(/[A-Z]/))                              // [verified] at least one upper case letter
	{
		intScore = (intScore+5);
	}
	
	// NUMBERS
	if (passwd.match(/\d+/))                                 // [verified] at least one number
	{
		intScore = (intScore+5);
	}
	
	if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/))             // [verified] at least three numbers
	{
		intScore = (intScore+5);
	}
	
	
	// SPECIAL CHAR
	if (passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/))            // [verified] at least one special character
	{
		intScore = (intScore+5);
	}
	
															 // [verified] at least two special characters
	if (passwd.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/))
	{
		intScore = (intScore+5);
	}

	
	// COMBOS
	if (passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/))        // [verified] both upper and lower case
	{
		intScore = (intScore+2);
	}

    if (passwd.match(/([a-zA-Z])/) && passwd.match(/([0-9])/)) // [verified] both letters and numbers
    {
            intScore = (intScore+2);
    }

															  // [verified] letters, numbers, and special characters
	if (passwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/))
	{
		intScore = (intScore+2);
	}


//if you don't want to prevent submission of weak passwords you can comment out
//		   document.getElementById("formSubmit").disabled = true;



	if(intScore <= 15)
	{
		strVerdict = passwordMessages[1];
	}
	else if (intScore > 15 && intScore < 25)
	{
		strVerdict = passwordMessages[1];
	}
	else if (intScore > 24 && intScore < 35)
	{
		strVerdict = passwordMessages[2];
	}
	else if (intScore > 34 && intScore < 45)
	{
		strVerdict = passwordMessages[3];
	}
	else if(intScore >= 45)
	{
		strVerdict = passwordMessages[4];
	}
	
	var percentScore = (intScore*100)/50;
	document.getElementById("meterFull").style.width = percentScore+"%";
	if (intScore >= minScore)
	{
		//document.getElementById("formSubmit").disabled = false;
	}
	else
	{
		//document.getElementById("formSubmit").disabled = true;
	   if (intScore < minScore)
	   {
			strVerdict = passwordMessages[0];
	   }
	}

	document.getElementById("strengthMsg").innerHTML= (strVerdict);
}
