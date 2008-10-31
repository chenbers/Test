package com.inthinc.pro.backing;

import com.inthinc.pro.model.Duration;

public class BaseDurationBean extends BaseBean
{
    private Duration duration           = Duration.DAYS;
    private String   styleClass30Days   = "on";
    private String   styleClass3Months  = "";
    private String   styleClass6Months  = "";
    private String   styleClass12Months = "";
    private final static String   ON = "on";

    
    public BaseDurationBean()
    {

    }

    public Duration getDuration()
    {
        return duration;
    }

    public void setDuration(Duration duration)
    {
        this.duration = duration;
    }

    public String getStyleClass30Days() {
//        reset();
        return ( duration.equals(Duration.DAYS)) ? "on" : ""; 
    
//        return styleClass30Days;
    }

//    public void setStyleClass30Days(String styleClass30Days)
//    {
//        this.styleClass30Days = styleClass30Days;
//    }

    public String getStyleClass3Months()
    {
/*        
        reset();
        if (this.duration.toString().equalsIgnoreCase(Duration.THREE.toString()))
        {
            styleClass3Months = "on";
        }

        return styleClass3Months;
*/        
        return ( duration.equals(Duration.THREE)) ? "on" : ""; 
    }

    public void setStyleClass3Months(String styleClass3Months)
    {
        this.styleClass3Months = styleClass3Months;
    }

    public String getStyleClass6Months()
    {
/*        
        reset();
        if (this.duration.toString().equalsIgnoreCase(Duration.SIX.toString()))
        {
            styleClass6Months = "on";
        }

        return styleClass6Months;
*/        
        return ( duration.equals(Duration.SIX)) ? "on" : ""; 
    }

    public void setStyleClass6Months(String styleClass6Months)
    {
        this.styleClass6Months = styleClass6Months;
    }

    public String getStyleClass12Months()
    {
/*        
        reset();
        if (this.duration.toString().equalsIgnoreCase(Duration.TWELVE.toString()))
        {
            styleClass12Months = "on";
        }

        return styleClass12Months;
*/        
        return ( duration.equals(Duration.TWELVE)) ? "on" : ""; 
    }

    public void setStyleClass12Months(String styleClass12Months)
    {
        this.styleClass12Months = styleClass12Months;
    }

    private void reset()
    {
        this.styleClass30Days = "";
        this.styleClass3Months = "";
        this.styleClass6Months = "";
        this.styleClass12Months = "";
    }

}
