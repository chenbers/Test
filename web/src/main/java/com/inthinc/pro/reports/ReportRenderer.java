package com.inthinc.pro.reports;
 
import java.util.List;

import javax.servlet.ServletResponse;

import com.inthinc.pro.model.DriverReportItem;
import com.inthinc.pro.reports.model.CategorySeriesData;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.wrapper.ScoreableEntityPkg;

public interface ReportRenderer
{
    void exportDriverReport(List<DriverReportItem> driverlist);
    
    void exportOverallScoreToPDF(List<PieScoreData> overallScoreData
            ,List<PieScoreData> drivingStyleData,List<PieScoreData> seatbeltData,
            List<PieScoreData> speedData,String overallscore);
    


    void exportTrendReportToPDF(List<CategorySeriesData> trendChartData, List<ScoreableEntityPkg> scoreableEntityData);
    
    void exportSingleReportToPDF(ReportCriteria reportCriteria,ServletResponse response);
}
