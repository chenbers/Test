<%@ page import="java.io.OutputStream"%>
<%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Graphics"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.ImageIO"%>
<%
	//Decoded data from charts.
	String data="";
	//Rows of color values.
	String[] rows;
	//Width and height of chart.
	int width=0;
	int height=0;
	//Default background color of the chart
	String bgcolor="";
	Color bgColor;

	//Get the width and height from form
	try{
		width = Integer.parseInt(request.getParameter("width"));
		height = Integer.parseInt(request.getParameter("height"));	
	}
	catch(Exception e){
		//If the width and height have not been given, we cannot create the image.
		out.print("Image width/height not provided.");
		out.close();
	}

	if(width==0 || height==0){
		//If the width and height are less than 1, we cannot create the image.
		out.print("Image width/height not provided.");
		out.close();
	}
		
	//Get background color from request and set default
	bgcolor =request.getParameter("bgcolor");
	if (bgcolor==null || bgcolor=="" || bgcolor==null){
			bgcolor = "FFFFFF";
	}
	//Convert background color to color object	
	bgColor = new Color(Integer.parseInt(bgcolor,16));

	//Get image data  from request
	data = request.getParameter("data");

	if(data==null){
		//If image data not provided.
		out.print("Image Data not supplied.");
		out.close();
	}

	try{
		//Parse data 
		rows = new String[height+1];
		rows = data.split(";");
			
		//Bitmap to store the chart.
		//Reference to graphics object - gr
		BufferedImage chart = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
		Graphics gr = chart.createGraphics();
		gr.setColor(bgColor);
		gr.fillRect(0,0,width,height);	
	
		String c;
		int r;
		int ri = 0;
		for (int i=0; i<rows.length; i++){
			//Split individual pixels.			
			String[] pixels = rows[i].split(",");			
			//Set horizontal row index to 0
			ri = 0;
			for (int j=0; j<pixels.length; j++){				
				//Now, if it's not empty, we process it				
				//Split the color and repeat factor
	
				String[] clrs = pixels[j].split("_");	
				//Reference to color
				c = clrs[0];
				r = Integer.parseInt(clrs[1]);
	
				//If color is not empty (i.e. not background pixel)
				if (c!=null && c.length()>0 && c!=""){		
					if (c.length()<6){
						//If the hexadecimal code is less than 6 characters, pad with 0
						StringBuffer str = new StringBuffer(c);
						int strLength  = str.length();
						  for ( int p = c.length()+1; p <= 6 ; p ++ ) {
								  str.insert( 0, "0" );
						  }
						//Assing the new padded string
						c = str.toString();
					}
					for (int k=1; k<=r; k++){	
						//Draw each pixel
						gr.setColor(new Color(Integer.parseInt(c,16)));
						gr.fillRect(ri, i,1,1);
						//Increment horizontal row count
						ri++;						
					}
				}else{
					//Just increment horizontal index
					ri = ri + r;
				}
			}
		}
		
	
		//Returns the image
		response.setContentType("image/jpeg");
		response.addHeader("Content-Disposition", "attachment; filename=\"FusionCharts.jpg\"");
		OutputStream os = response.getOutputStream();
		ImageIO.write(chart, "jpeg", os);
		os.close();
		
	}catch(Exception e){
		//IF the image data is mal-formatted.
		out.print("Image data is not in proper format.");
		out.close();
	}
%>
