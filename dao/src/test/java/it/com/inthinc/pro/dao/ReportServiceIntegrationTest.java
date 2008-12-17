package it.com.inthinc.pro.dao;

import java.util.List;

import it.config.IntegrationConfig;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import com.inthinc.pro.dao.hessian.DVQMapHessianDAO;
import com.inthinc.pro.dao.hessian.DriveQMapHessianDAO;
import com.inthinc.pro.dao.hessian.GQVMapHessianDAO;
import com.inthinc.pro.dao.hessian.ScoreHessianDAO;
import com.inthinc.pro.dao.hessian.extension.HessianDebug;
import com.inthinc.pro.dao.hessian.proserver.ReportService;
import com.inthinc.pro.dao.hessian.proserver.ReportServiceCreator;
import com.inthinc.pro.dao.hessian.proserver.SiloService;
import com.inthinc.pro.dao.hessian.proserver.SiloServiceCreator;
import com.inthinc.pro.dao.mock.proserver.ReportServiceMockImpl;
import com.inthinc.pro.model.DVQMap;
import com.inthinc.pro.model.DriveQMap;
import com.inthinc.pro.model.GQVMap;
import com.inthinc.pro.model.QuintileMap;

public class ReportServiceIntegrationTest
{
    private static final Logger logger = Logger.getLogger(ReportServiceIntegrationTest.class);

    private static ReportService reportService;
    private static SiloService siloService;
    
    //16777218 for drivers and vehicles
    //16777217 for groupID
    
    //Colleen suggests 16777228 groupID
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        IntegrationConfig config = new IntegrationConfig();

        String host = config.get(IntegrationConfig.SILO_HOST).toString();
        Integer port = Integer.valueOf(config.get(IntegrationConfig.SILO_PORT).toString());
        
        reportService = new ReportServiceCreator(host, port).getService();
        siloService = new SiloServiceCreator(host, port).getService();
//        HessianDebug.debugIn = true;
//        HessianDebug.debugOut = true;
        HessianDebug.debugRequest = true;
        
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
    }
    
//  Driver Reports
        
    @Test
    public void getDScoreByDM()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDScoreByDM *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getDScoreByDM(
                    16777218, 100000);
            if ( dqm != null ) {                
                System.out.println("for driver 16777218 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getDScoreByDM failed");
        }
        System.out.println("***** end *****");
        
    }
    
    @Test
    public void getDScoreByDT()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDScoreByDT *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getDScoreByDT(
                    16777218, 5);
            if ( dqm != null ) {
                System.out.println("for driver 16777218 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getDScoreByDT failed");
        }
        System.out.println("***** end *****");        
    }
    
    @Test
    public void getDTrendByDTC()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDTrendByDTC *****");
        
        List<DriveQMap> dqmList = null;
        try 
        {
            dqmList = dqmDAO.getDTrendByDTC(
                    16777218, 1, 1);
            if ( dqmList != null ) { 
                for ( DriveQMap d: dqmList )
                {
                    System.out.println("overall " +
                        d.getOverall());
                }
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getDTrendByDTC failed");
        }
        System.out.println("***** end *****");        
    }
    
    @Test
    public void getDTrendByDMC()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDTrendByDMC *****");
        
        List<DriveQMap> dqmList = null;
        try 
        {
            dqmList = dqmDAO.getDTrendByDMC(
                    16777218, 100000, 10);
            if ( dqmList != null ) { 
                for ( DriveQMap d: dqmList )
                {
                    System.out.println("overall " +
                        d.getOverall());
                }
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getDTrendByDMC failed");
        }
        System.out.println("***** end *****");             
    }
    
//  Vehicle Reports
    
    @Test
    public void getVScoreByVM()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getVScoreByVM *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getVScoreByVM(
                    16777218, 100000);
            if ( dqm != null ) {
                System.out.println("for vehicle 16777218 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getVScoreByVM failed");
        }
        System.out.println("***** end *****");            
    }
    
    @Test
    public void getVScoreByVT()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getVScoreByVT *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getVScoreByVT(
                    16777218, 5);
            if ( dqm != null ) {
                System.out.println("for vehicle 16777218 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getVScoreByVT failed");
        }
        System.out.println("***** end *****");             
    }
    
    @Test
    public void getVTrendByVMC()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getVTrendByVMC *****");
        
        List<DriveQMap> dqmList = null;
        try 
        {
            dqmList = dqmDAO.getVTrendByVMC(
                    16777218, 100000, 10);
            if ( dqmList != null ) { 
                for ( DriveQMap d: dqmList )
                {
                    System.out.println("overall " +
                        d.getOverall());
                }
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getVTrendByVMC failed");
        }
        System.out.println("***** end *****");            
    }
    
    @Test
    public void getVTrendByVTC()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getVTrendByVTC *****");
        
        List<DriveQMap> dqmList = null;
        try 
        {
            dqmList = dqmDAO.getVTrendByVTC(
                    16777218, 5, 1);
            if ( dqmList != null ) { 
                for ( DriveQMap d: dqmList )
                {
                    System.out.println("overall " +
                        d.getOverall());
                }
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getVTrendByVTC failed");
        }
        System.out.println("***** end *****");             
    }
    
//  Group Driver Reports
    
    @Test
    public void getDVScoresByGT()
    {
        DVQMapHessianDAO dvqmDAO = new DVQMapHessianDAO();
        dvqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDVScoresByGT *****");
        
        List<DVQMap> dvqmList = null;
        try 
        {
            dvqmList = dvqmDAO.getDVScoresByGT(
                    16777217, 5);
            
            if ( dvqmList != null ) { 
                for ( DVQMap d: dvqmList )
                {
                    System.out.println(
                            " driver " +
                                d.getDriver().getPerson().getFirst() + " " +
                                d.getDriver().getPerson().getLast() +
                            " overall " + d.getDriveQ().getOverall());
                }
            } else {
                System.out.println("null map returned");
            }

        } catch (Exception e)
        {
            System.out.println("getDVScoresByGT failed");
        }
        System.out.println("***** end *****");                
    }
    
    @Test
    public void getGDScoreByGT()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getGDScoreByGT *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getGDScoreByGT(
                    16777218, 5);
            if ( dqm != null ) {
                System.out.println("for driver 16777218 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getGDScoreByGT failed");
        }
        System.out.println("***** end *****");           
    }
    
    @Test
    public void getGDTrendByGTC()
    {
        DriveQMapHessianDAO dqmDAO = new DriveQMapHessianDAO();
        dqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getGDTrendByGTC *****");
        
        try 
        {
            DriveQMap dqm = dqmDAO.getGDTrendByGTC(
                    16777217, 5);
            if ( dqm != null ) {
                System.out.println("for group 16777217 " +
                        dqm.getOverall());
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getGDTrendByGTC failed");
        }
        System.out.println("***** end *****");               
    }
    
    @Test
    public void getDPctByGT()
    {
        DVQMapHessianDAO dvqmDAO = new DVQMapHessianDAO();
        dvqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getDPctByGT *****");
        
        try 
        {
            QuintileMap qm = dvqmDAO.getDPctByGT(
                    16777217, 5, 3);
            if ( qm != null ) {
                System.out.println("for group 16777217 " +
                        qm.getPercent1() + " " +
                        qm.getPercent2() + " " +
                        qm.getPercent3() + " " +
                        qm.getPercent4() + " " +
                        qm.getPercent5()
                        );
            } else {
                System.out.println("null map returned");
            }
        } catch (Exception e)
        {
            System.out.println("getGDTrendByGTC failed");
        }
        System.out.println("***** end *****");              
    }

    
    @Test
    public void getSDScoresByGT()
    {
        GQVMapHessianDAO gqvDAO = new GQVMapHessianDAO();
        gqvDAO.setReportService(reportService);
        
        System.out.println("***** begin getSDScoresByGT *****");
        
        List<GQVMap> gqvList = null;
        try 
        {
            gqvList = gqvDAO.getSDScoresByGT(
                    16777217, 5);
            
            if ( gqvList != null ) { 
                for ( GQVMap d: gqvList )
                {
                    System.out.println(
                            " group " +
                                d.getGroup().getGroupID());
                     for ( DriveQMap q: d.getDriveQV() )
                     {
                         System.out.println(
                            " score " + q.getOverall());
                     }
                }
            } else {
                System.out.println("null map returned");
            }

        } catch (Exception e)
        {
            System.out.println("getDVScoresByGT failed");
        }
        System.out.println("***** end *****");            
    }
    
    @Test
    public void getSDTrendsByGTC()
    {
        GQVMapHessianDAO gqvDAO = new GQVMapHessianDAO();
        gqvDAO.setReportService(reportService);
        
        System.out.println("***** begin getSDTrendsByGTC *****");
        
        List<GQVMap> gqvList = null;
        try 
        {
            gqvList = gqvDAO.getSDTrendsByGTC(
                    16777217, 5, 3);
            
            if ( gqvList != null ) { 
                for ( GQVMap d: gqvList )
                {
                    System.out.println(
                            " group " +
                                d.getGroup().getGroupID());
                     for ( DriveQMap q: d.getDriveQV() )
                     {
                         System.out.println(
                            " score " + q.getOverall());
                     }
                }
            } else {
                System.out.println("null map returned");
            }

        } catch (Exception e)
        {
            System.out.println("getSDTrendsByGTC failed");
        }
        System.out.println("***** end *****");                  
    }
    
//  Group Vehicle Reports
    
    @Test
    public void getVDScoresByGT()
    {
        DVQMapHessianDAO dvqmDAO = new DVQMapHessianDAO();
        dvqmDAO.setReportService(reportService);
        
        System.out.println("***** begin getVDScoresByGT *****");
        
        List<DVQMap> dvqmList = null;
        try 
        {
            dvqmList = dvqmDAO.getVDScoresByGT(
                    16777217, 5);
            
            if ( dvqmList != null ) { 
                for ( DVQMap d: dvqmList )
                {
                    System.out.println(
                            " driver " +
                                d.getDriver().getPerson().getFirst() + " " +
                                d.getDriver().getPerson().getLast() +
                            " overall " + d.getDriveQ().getOverall());
                }
            } else {
                System.out.println("null map returned");
            }

        } catch (Exception e)
        {
            System.out.println("getVDScoresByGT failed");
        }
        System.out.println("***** end *****");           
    }
}
