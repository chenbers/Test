package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.inthinc.pro.model.EntityType;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.ScoreType;
import com.inthinc.pro.model.ScoreableEntity;
import com.inthinc.pro.reports.ReportCriteria;
import com.inthinc.pro.reports.ReportType;
import com.inthinc.pro.reports.model.PieScoreData;
import com.inthinc.pro.reports.model.PieScoreRange;

public class ReportRendererBeanTest extends BaseBeanTest
{
    @Test
    @Ignore
    public void testExportOverallScoreToPDF(){
        ReportRendererBean rrb = (ReportRendererBean)applicationContext.getBean("reportRenderer");
        NavigationBean navBean = (NavigationBean)applicationContext.getBean("navigationBean");
        navBean.setGroup(new Group(1,1,"Test",0));
        
        
        List<PieScoreData> list = new ArrayList<PieScoreData>();
        PieScoreData se1 = new PieScoreData(PieScoreRange.SCORE_0.getLabel(),50,PieScoreRange.SCORE_0.getLabel());
        PieScoreData se2 = new PieScoreData(PieScoreRange.SCORE_1.getLabel(),50,PieScoreRange.SCORE_1.getLabel());
        list.add(se1);
        list.add(se2);
        
        ReportCriteria rc = new ReportCriteria(ReportType.OVERALL_SCORE,"Group1","AccountName");
        rc.setMainDataset(list);
        rc.addParameter("DRIVER_STYLE_DATA", list);
        rc.addParameter("SEATBELT_USE_DATA", list);
        rc.addParameter("SPEED_DATA", list);
        rc.addParameter("OVERALL_SCORE", 2.3F);
        
        //rrb.exportSingleReportToPDF(rc,null);
        
    }

}
