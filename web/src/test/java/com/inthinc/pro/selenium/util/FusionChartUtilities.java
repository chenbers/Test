package com.inthinc.pro.selenium.util;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.inthinc.pro.automation.selenium.AbstractPage;

public class FusionChartUtilities extends AbstractPage {
    
    public FusionChartUtilities() {}

    public OverallScoreSummary findOverallScoreData(String chart) {
        OverallScoreSummary oss = new OverallScoreSummary();
        
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            if ( e.getNodeName().equalsIgnoreCase("set") ) {
                
                Integer value = new Integer(e.getAttribute("value"));
                String color = e.getAttribute("color");
                
                //                               all others                            team speed
                if (        color.equalsIgnoreCase("FF0101") || color.equalsIgnoreCase("#fd0500")) {
                    oss.setZeroToOne(value);

                } else if ( color.equalsIgnoreCase("FF6601") || color.equalsIgnoreCase("#d71b2d")) {
                    oss.setOneToTwo(value);

                } else if ( color.equalsIgnoreCase("F6B305") || color.equalsIgnoreCase("#fa6b00")) {
                    oss.setTwoToThree(value);

                } else if ( color.equalsIgnoreCase("1E88C8") || color.equalsIgnoreCase("#907021")) {
                    oss.setThreeToFour(value);

                } else if ( color.equalsIgnoreCase("6B9D1B") || color.equalsIgnoreCase("#bc8b1d")) {
                    oss.setFourToFive(value);
                }
                
            }
        }
        
        return oss;
    }

    public DataSummary findTrendData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data for the groups that are SHOWING...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // dataset, set up to get points
            if (        e.getNodeName().equalsIgnoreCase("dataset") ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset    
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                dat.getData().add(new Float(e.getAttribute("value")));
            }
            
        }
        
        // add the last one
        ds.getData().add(dat);
        
        return ds;
    }
    
    public DataSummary findSpeedingData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // dataset, set up to get points
            if (        (e.getNodeName().equalsIgnoreCase("dataset") || 
                         e.getNodeName().equalsIgnoreCase("lineset")) && 
                         e.getAttribute("seriesName").trim().length() != 0 ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset    
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                dat.getData().add(new Float(e.getAttribute("value")));     
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
        
        return ds;
    }
    
    public DataSummary findIdlingData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // dataset, set up to get points
            if (        (e.getNodeName().equalsIgnoreCase("dataset") || 
                         e.getNodeName().equalsIgnoreCase("lineset")) && 
                         e.getAttribute("seriesName").trim().length() != 0 ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset    
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                dat.getData().add(new Float(e.getAttribute("value")));     
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
        
        return ds;
    }
    
    public MPGSummary findMPGData(String chart) {
        MPGSummary mpgSum = new MPGSummary();
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // get group names
            if (        e.getNodeName().equalsIgnoreCase("category")  ) {
                
                MPGData mpgData = new MPGData();
                mpgData.setName(e.getAttribute("label"));
                mpgSum.getMpgSet().add(mpgData);
               
            // extract all points for each vehicle type    
            } else if ( e.getNodeName().equalsIgnoreCase("dataset") ) {
                NodeList nl = e.getChildNodes();
                
                if (        e.getAttribute("seriesName").equalsIgnoreCase("light") ) {
                    for ( int j = 0; j < nl.getLength(); j++ ) {
                        NamedNodeMap nnm = nl.item(j).getAttributes();
                        mpgSum.getMpgSet().get(j).setLight(new Float(nnm.getNamedItem("value").getNodeValue()));
                    }
                    
                } else if ( e.getAttribute("seriesName").equalsIgnoreCase("medium") ) {
                    for ( int j = 0; j < nl.getLength(); j++ ) {
                        NamedNodeMap nnm = nl.item(j).getAttributes();
                        mpgSum.getMpgSet().get(j).setMedium(new Float(nnm.getNamedItem("value").getNodeValue()));
                    }
                    
                } else if ( e.getAttribute("seriesName").equalsIgnoreCase("heavy" ) ) {
                    for ( int j = 0; j < nl.getLength(); j++ ) {
                        NamedNodeMap nnm = nl.item(j).getAttributes();
                        mpgSum.getMpgSet().get(j).setHeavy(new Float(nnm.getNamedItem("value").getNodeValue()));
                    }                    
                }
            }
            
        } 
        
        return mpgSum;
    }  
    
    public DataSummary findTeamSpeedingTabData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // get group names
            if (         e.getNodeName().equalsIgnoreCase("category")  ) {
                ds.getSpeedRanges().add(e.getAttribute("label"));
                
            // dataset, set up to get points
            } else if ( (e.getNodeName().equalsIgnoreCase("dataset") || 
                         e.getNodeName().equalsIgnoreCase("lineset")) && 
                         e.getAttribute("seriesName").trim().length() != 0 ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset    
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                dat.getData().add(new Float(e.getAttribute("value")));     
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
        
        return ds;
    }
    
    public DataSummary findTeamStyleTabData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
         
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // get style category names
            if (         e.getNodeName().equalsIgnoreCase("category")  ) {
                ds.getStyleCats().add(e.getAttribute("label"));
                
            // dataset, set up to get points
            } else if ( (e.getNodeName().equalsIgnoreCase("dataset") || 
                         e.getNodeName().equalsIgnoreCase("lineset")) && 
                         e.getAttribute("seriesName").trim().length() != 0 ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset    
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                dat.getData().add(new Float(e.getAttribute("value")));     
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
        
        // since they are saved by score range, create a new one with the
        //  data returned organized by driving style category, not by score range
        DataSummary out = new DataSummary();
        
        for (int i= 0; i < ds.getStyleCats().size(); i++ ) {
            Data d = new Data();
            d.setName(ds.getStyleCats().get(i));
            
            for ( int j = 0; j < ds.getData().size(); j++ ) {
                d.getData().add(ds.getData().get(j).getData().get(i));
            }
            out.getData().add(d);
        }
        
        return out;
    }
      
    public DataSummary findPerformanceXYData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = null;
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            // dataset, set up to get points
            if (        (e.getNodeName().equalsIgnoreCase("dataset") || 
                         e.getNodeName().equalsIgnoreCase("lineset")) && 
                         e.getAttribute("seriesName").trim().length() != 0 ) {
                
                // been loading one, save it
                if ( dat != null ) {
                    ds.getData().add(dat);
                    dat = null;
                }
                
                dat = new Data();
                dat.setName(e.getAttribute("seriesName"));
               
            // point in a dataset, if no value, set to -1 (quirk for these performance charts)
            } else if ( e.getNodeName().equalsIgnoreCase("set") ) {
                Float fltValue = new Float(-1.0f);
                
                if ( !e.getAttribute("value").equalsIgnoreCase("null") ) {
                    fltValue = new Float(e.getAttribute("value")); 
                }
                
                dat.getData().add(fltValue);     
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
        
        return ds;
    }
    
    public DataSummary findPerformanceCoachingData(String chart) {
        DataSummary ds = new DataSummary();
        Data dat = new Data();
        dat.setName("CoachingEvents");
        
        // get all the chart elements
        String xmlString = selenium.getValue(chart);
//        System.out.println("XML: " + xmlString);
        ArrayList<Element> fusionElements = XMLUtility.parseFusionXML(xmlString); 
        
        // yank out all the data ...
        for ( int i = 0; i < fusionElements.size(); i++ ) {
            Element e = fusionElements.get(i);
            
            if ( e.getNodeName().equalsIgnoreCase("set") ) {
                Float fltValue = new Float(-1.0f);
                
                if ( !e.getAttribute("value").equalsIgnoreCase("null") ) {
                    fltValue = new Float(e.getAttribute("value")); 
                }
                
                dat.getData().add(fltValue); 
            }
            
        } 
        
        // add the last one
        ds.getData().add(dat);
          
        return ds;
    }
}
