package com.inthinc.sbs.utils;

/**
 * @author jason litzinger
 * 
 * The contents of this file are confidential and/or proprietary.  Any unauthorized release or use
 * of this information is prohibited and punishable by law.
 *
 * Copyright 2003-2011 Inthinc Technology Solutions, Inc.  All rights reserved worldwide.
 * **/

public class GeoUtil {
	
	public static final int [][] longlat2feet_table = new int[91][2];
	
	static
	{		
	        longlat2feet_table[0][0] = 365221;
	        longlat2feet_table[0][1] = 362772;        // 0
	        longlat2feet_table[1][0] = 365166;
	        longlat2feet_table[1][1] = 362773;        // 1
	        longlat2feet_table[2][0] = 365000;
	        longlat2feet_table[2][1] = 362776;        // 2
	        longlat2feet_table[3][0] = 364724; 
	        longlat2feet_table[3][1] = 362782;        // 3
	        longlat2feet_table[4][0] = 364337; 
	        longlat2feet_table[4][1] = 362790;        // 4
	        longlat2feet_table[5][0] = 363840; 
	        longlat2feet_table[5][1] = 362800;        // 5
	        longlat2feet_table[6][0] = 363233; 
	        longlat2feet_table[6][1] = 362812;        // 6
	        longlat2feet_table[7][0] = 362517; 
	        longlat2feet_table[7][1] = 362826;        // 7
	        longlat2feet_table[8][0] = 361690; 
	        longlat2feet_table[8][1] = 362843;        // 8
	        longlat2feet_table[9][0] = 360754; 
	        longlat2feet_table[9][1] = 362862;        // 9
	        longlat2feet_table[10][0] = 359709; 
	        longlat2feet_table[10][1] = 362883;        // 10
	        longlat2feet_table[11][0] = 358554; 
	        longlat2feet_table[11][1] = 362906;        // 11
	        longlat2feet_table[12][0] = 357292; 
	        longlat2feet_table[12][1] = 362931;        // 12
	        longlat2feet_table[13][0] = 355921; 
	        longlat2feet_table[13][1] = 362958;        // 13
	        longlat2feet_table[14][0] = 354442; 
	        longlat2feet_table[14][1] = 362987;        // 14
	        longlat2feet_table[15][0] =       352855; 
	        longlat2feet_table[15][1] = 363018;        // 15
	        longlat2feet_table[16][0] =       351162; 
	        longlat2feet_table[16][1] = 363051;        // 16
	        longlat2feet_table[17][0] =       349362; 
	        longlat2feet_table[17][1] = 363086;        // 17
	        longlat2feet_table[18][0] =       347457; 
	        longlat2feet_table[18][1] = 363123;        // 18
	        longlat2feet_table[19][0] =       345446; 
	        longlat2feet_table[19][1] = 363161;        // 19
	        longlat2feet_table[20][0] =       343330; 
	        longlat2feet_table[20][1] = 363202;        // 20
	        longlat2feet_table[21][0] =       341110; 
	        longlat2feet_table[21][1] = 363244;        // 21
	        longlat2feet_table[22][0] =       338786; 
	        longlat2feet_table[22][1] = 363287;        // 22
	        longlat2feet_table[23][0] =       336360; 
	        longlat2feet_table[23][1] = 363333;        // 23
	        longlat2feet_table[24][0] =       333831; 
	        longlat2feet_table[24][1] = 363380;        // 24
	        longlat2feet_table[25][0] =       331201; 
	        longlat2feet_table[25][1] = 363428;        // 25
	        longlat2feet_table[26][0] =       328470; 
	        longlat2feet_table[26][1] = 363478;        // 26
	        longlat2feet_table[27][0] =       325639; 
	        longlat2feet_table[27][1] = 363529;        // 27
	        longlat2feet_table[28][0] =       322709; 
	        longlat2feet_table[28][1] = 363581;        // 28
	        longlat2feet_table[29][0] =       319681; 
	        longlat2feet_table[29][1] = 363635;        // 29
	        longlat2feet_table[30][0] =       316556; 
	        longlat2feet_table[30][1] = 363690;        // 30
	        longlat2feet_table[31][0] =       313334; 
	        longlat2feet_table[31][1] = 363746;        // 31
	        longlat2feet_table[32][0] =       310016; 
	        longlat2feet_table[32][1] = 363803;        // 32
	        longlat2feet_table[33][0] =       306605; 
	        longlat2feet_table[33][1] = 363861;        // 33
	        longlat2feet_table[34][0] =       303099; 
	        longlat2feet_table[34][1] = 363920;        // 34
	        longlat2feet_table[35][0] =       299501; 
	        longlat2feet_table[35][1] = 363980;        // 35
	        longlat2feet_table[36][0] =       295812; 
	        longlat2feet_table[36][1] = 364041;        // 36
	        longlat2feet_table[37][0] =       292032; 
	        longlat2feet_table[37][1] = 364102;        // 37
	        longlat2feet_table[38][0] =       288164; 
	        longlat2feet_table[38][1] = 364164;        // 38
	        longlat2feet_table[39][0] =       284207; 
	        longlat2feet_table[39][1] = 364227;        // 39
	        longlat2feet_table[40][0] =       280163; 
	        longlat2feet_table[40][1] = 364290;        // 40
	        longlat2feet_table[41][0] =       276033; 
	        longlat2feet_table[41][1] = 364353;        // 41
	        longlat2feet_table[42][0] =       271819; 
	        longlat2feet_table[42][1] = 364417;        // 42
	        longlat2feet_table[43][0] =       267522; 
	        longlat2feet_table[43][1] = 364480;        // 43
	        longlat2feet_table[44][0] =       263143; 
	        longlat2feet_table[44][1] = 364544;        // 44
	        longlat2feet_table[45][0] =       258683; 
	        longlat2feet_table[45][1] = 364609;        // 45
	        longlat2feet_table[46][0] =       254144; 
	        longlat2feet_table[46][1] = 364673;        // 46
	        longlat2feet_table[47][0] =       249527; 
	        longlat2feet_table[47][1] = 364737;        // 47
	        longlat2feet_table[48][0] =       244833; 
	        longlat2feet_table[48][1] = 364801;        // 48
	        longlat2feet_table[49][0] =       240064; 
	        longlat2feet_table[49][1] = 364864;        // 49
	        longlat2feet_table[50][0] =       235221; 
	        longlat2feet_table[50][1] = 364927;        // 50
	        longlat2feet_table[51][0] =       230306; 
	        longlat2feet_table[51][1] = 364990;        // 51
	        longlat2feet_table[52][0] =       225320; 
	        longlat2feet_table[52][1] = 365053;        // 52
	        longlat2feet_table[53][0] =       220265; 
	        longlat2feet_table[53][1] = 365115;        // 53
	        longlat2feet_table[54][0] =       215142; 
	        longlat2feet_table[54][1] = 365176;        // 54
	        longlat2feet_table[55][0] =       209953; 
	        longlat2feet_table[55][1] = 365237;        // 55
	        longlat2feet_table[56][0] =       204699; 
	        longlat2feet_table[56][1] = 365297;        // 56
	        longlat2feet_table[57][0] =       199382; 
	        longlat2feet_table[57][1] = 365356;        // 57
	        longlat2feet_table[58][0] =       194004; 
	        longlat2feet_table[58][1] = 365414;        // 58
	        longlat2feet_table[59][0] =       188566; 
	        longlat2feet_table[59][1] = 365471;        // 59
	        longlat2feet_table[60][0] =       183069; 
	        longlat2feet_table[60][1] = 365527;        // 60
	        longlat2feet_table[61][0] =       177516; 
	        longlat2feet_table[61][1] = 365582;        // 61
	        longlat2feet_table[62][0] =       171909; 
	        longlat2feet_table[62][1] = 365636;        // 62
	        longlat2feet_table[63][0] =       166248; 
	        longlat2feet_table[63][1] = 365688;        // 63
	        longlat2feet_table[64][0] =       160536; 
	        longlat2feet_table[64][1] = 365739;        // 64
	        longlat2feet_table[65][0] =       154774; 
	        longlat2feet_table[65][1] = 365789;        // 65
	        longlat2feet_table[66][0] =       148964; 
	        longlat2feet_table[66][1] = 365838;        // 66
	        longlat2feet_table[67][0] =       143108; 
	        longlat2feet_table[67][1] = 365884;        // 67
	        longlat2feet_table[68][0] =       137208; 
	        longlat2feet_table[68][1] = 365930;        // 68
	        longlat2feet_table[69][0] =       131266; 
	        longlat2feet_table[69][1] = 365974;        // 69
	        longlat2feet_table[70][0] =       125282; 
	        longlat2feet_table[70][1] = 366016;        // 70
	        longlat2feet_table[71][0] =       119260; 
	        longlat2feet_table[71][1] = 366056;        // 71
	        longlat2feet_table[72][0] =       113201; 
	        longlat2feet_table[72][1] = 366095;        // 72
	        longlat2feet_table[73][0] =       107107; 
	        longlat2feet_table[73][1] = 366131;        // 73
	        longlat2feet_table[74][0] =       100980; 
	        longlat2feet_table[74][1] = 366166;        // 74
	        longlat2feet_table[75][0] =       94821;  
	        longlat2feet_table[75][1] = 366199;        // 75
	        longlat2feet_table[76][0] =       88633;  
	        longlat2feet_table[76][1] = 366230;        // 76
	        longlat2feet_table[77][0] =       82418;  
	        longlat2feet_table[77][1] = 366259;        // 77
	        longlat2feet_table[78][0] =       76177;  
	        longlat2feet_table[78][1] = 366287;        // 78
	        longlat2feet_table[79][0] =       69912;  
	        longlat2feet_table[79][1] = 366312;        // 79
	        longlat2feet_table[80][0] =       63626;  
	        longlat2feet_table[80][1] = 366335;        // 80
	        longlat2feet_table[81][0] =       57320;  
	        longlat2feet_table[81][1] = 366355;        // 81
	        longlat2feet_table[82][0] =       50996;  
	        longlat2feet_table[82][1] = 366374;        // 82
	        longlat2feet_table[83][0] =       44656;  
	        longlat2feet_table[83][1] = 366391;        // 83
	        longlat2feet_table[84][0] =       38302;  
	        longlat2feet_table[84][1] = 366405;        // 84
	        longlat2feet_table[85][0] =       31937;  
	        longlat2feet_table[85][1] = 366417;        // 85
	        longlat2feet_table[86][0] =       25561;  
	        longlat2feet_table[86][1] = 366427;        // 86
	        longlat2feet_table[87][0] =       19178;  
	        longlat2feet_table[87][1] = 366435;        // 87
	        longlat2feet_table[88][0] =       12788;  
	        longlat2feet_table[88][1] = 366441;        // 88
	        longlat2feet_table[89][0] =       6395;   
	        longlat2feet_table[89][1] = 366444;        // 89
	        longlat2feet_table[90][0] =       0;      
	        longlat2feet_table[90][1] = 366445;        // 90
	}
	
	public static double getLongToMile(double latitude)
	{
		return ((longlat2feet_table[Math.abs((int) latitude)][0]) / 5280.0);
	}
	
	public static double getLatToMile(double latitude)
	{
		return ((longlat2feet_table[Math.abs((int) latitude)][1]) / 5280.0);
	}
	
}