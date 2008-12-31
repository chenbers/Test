package com.inthinc.pro.reports;
 
import java.util.List;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.reports.model.LineGraphData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public interface ReportRenderer
{
    void exportDriverReport(List<DriverReportItem> driverlist);
    
    void exportOverallScoreToPDF(List<PieScoreData> overallScoreData
            ,List<PieScoreData> drivingStyleData,List<PieScoreData> seatbeltData,
            List<PieScoreData> speedData,String overallscore);
    


    void exportTrendReportToPDF(List<LineGraphData> trendChartData, List<ScoreableEntityPkg> scoreableEntityData);
}
