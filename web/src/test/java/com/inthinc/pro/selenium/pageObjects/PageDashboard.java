package com.inthinc.pro.selenium.pageObjects;

import java.util.StringTokenizer;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.TableText;
import com.inthinc.pro.automation.elements.TableTextLink;
import com.inthinc.pro.automation.elements.TextButton;
import com.inthinc.pro.automation.elements.TimeLink;
import com.inthinc.pro.selenium.pageEnums.DivisionEnum;

public class PageDashboard extends NavigationBar {

    public class DashboardButtons extends NavigationBar.NavigationBarButtons {

        public Button fuelEfficiencyExpand() {
            return new Button(DivisionEnum.FUEL_EFFICIENCY_EXPAND);
        }

        public TextButton idlingToolsEmail() {
            return new TextButton(DivisionEnum.TOOL_EMAIL_BUTTON, idling);
        }

        public TextButton idlingToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON, idling);
        }

        public TextButton idlingToolsEmailSend() {
            return new TextButton(DivisionEnum.EMAIL_SEND_BUTTON, idling);
        }

        public Button liveFleetExpand() {
            return new Button(DivisionEnum.LIVE_FLEET_EXPAND);
        }

        public TextButton mpgToolsEmail() {
            return new TextButton(DivisionEnum.TOOL_EMAIL_BUTTON, mpg);
        }

        public TextButton mpgToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON, mpg);
        }

        public TextButton mpgToolsEmailSend() {
            return new TextButton(DivisionEnum.EMAIL_SEND_BUTTON, mpg);
        }

        public Button overallExpand() {
            return new Button(DivisionEnum.OVERALL_EXPAND);
        }

        public TextButton overallToolsEmail() {
            return new TextButton(DivisionEnum.TOOL_EMAIL_BUTTON, overall);
        }

        public TextButton overallToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON, overall);
        }

        public TextButton overallToolsEmailSend() {
            return new TextButton(DivisionEnum.EMAIL_SEND_BUTTON, overall);
        }

        public TextButton overviewToolsEmail() {
            return new TextButton(DivisionEnum.OVERVIEW_EMAIL_BUTTON);
        }

        public TextButton overviewToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON);
        }

        public TextButton overviewToolsEmailSend() {
            return new TextButton(DivisionEnum.OVERVIEW_SEND_BUTTON);
        }

        public TextButton speedToolsEmail() {
            return new TextButton(DivisionEnum.TOOL_EMAIL_BUTTON, speed);
        }

        public TextButton speedToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON, overall);
        }

        public TextButton speedToolsEmailSend() {
            return new TextButton(DivisionEnum.EMAIL_SEND_BUTTON, overall);
        }

        public Button trendExpand() {
            return new Button(DivisionEnum.TREND_EXPAND);
        }

        public TextButton trendToolsEmail() {
            return new TextButton(DivisionEnum.TOOL_EMAIL_BUTTON, trend);
        }

        public TextButton trendToolsEmailCancel() {
            return new TextButton(DivisionEnum.EMAIL_CANCEL_BUTTON, overall);
        }

        public TextButton trendToolsEmailSend() {
            return new TextButton(DivisionEnum.EMAIL_SEND_BUTTON, overall);
        }

    }

    public class DashboardLinks extends NavigationBar.NavigationBarLinks {

        public TimeLink fuelEfficiencyDuration() {
            return new TimeLink(DivisionEnum.FUEL_EFFICIENCY_DURATION);
        }

        public TimeLink idlingDuration() {
            return new TimeLink(DivisionEnum.IDLING_DURATION);
        }

        public TimeLink overallDuration() {
            return new TimeLink(DivisionEnum.OVERALL_DURATION);
        }

        public TimeLink speedingDuration() {
            return new TimeLink(DivisionEnum.SPEEDING_DURATION);
        }

        public TimeLink trendDuration() {
            return new TimeLink(DivisionEnum.TREND_DURATION);
        }
        
        public TableTextLink groupName(){
            return new TableTextLink(DivisionEnum.TREND_GROUP_LINK);
        }
    }
    
    public DashboardText _text(){
        return new DashboardText();
    }
    
    public class DashboardText extends NavigationBar.NavigationBarTexts{

        public TableText groupScore(){
            return new TableText(DivisionEnum.TREND_GROUP_LINK);
        }
        
        public TableText groupCrash(){
            return new TableText(DivisionEnum.TREND_GROUP_CRASH_NUMBER);
        }
    }

    private String overall = "overallScore", speed = "speedPercentagePanel", trend = "trend";

    private String idling = "idlingPercentagePanel", mpg = "mpgChart";

    public DashboardButtons _button() {
        return new DashboardButtons();
    }

    public DashboardLinks _link() {
        return new DashboardLinks();
    }

    private void clickIt(String rowQualifier, Integer row) {
        if (row != null) {
            rowQualifier = insertRow(rowQualifier, row);
        }
        selenium.click(rowQualifier);

        // makes sure the next "thing" is there
        selenium.pause(10);
    }

    private String insertRow(String rowQualifier, Integer row) {
        StringTokenizer st = new StringTokenizer(rowQualifier, ":");

        StringBuffer sb = new StringBuffer();
        sb.append(st.nextToken());
        sb.append(":");
        sb.append(st.nextToken());
        sb.append(":");
        sb.append(Integer.toString(row));
        sb.append(":");
        st.nextToken();
        sb.append(st.nextToken());

        return sb.toString();
    }
}
