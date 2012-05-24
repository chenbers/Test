package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkScoreTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamDriverStatsEnum;

public class PageTeamVehicleStatistics extends TeamBar {

    private String pageScroller = "teamStatisticsForm:drivers:driverScroller_table";
    private static final String page = "teamStatistics";

    public class TeamDashboardStatisticsLinks extends TeamBarLinks {

        public TextTableLink driverValue() {
            return new TextTableLink(TeamDriverStatsEnum.DRIVER_NAME_ENTRY);
        }

        public TextTableLink vehicleValue() {
            return new TextTableLink(TeamDriverStatsEnum.DRIVER_VEHICLE_ENTRY);
        }

        public TextLinkScoreTable scoreValue() {
            return new TextLinkScoreTable(TeamDriverStatsEnum.DRIVER_SCORE_ENTRY);
        }

        public TextLink driverSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_NAME_HEADER);
        }

        public TextLink scoreSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_SCORE_HEADER);
        }

        public TextLink vehicleSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_VEHICLE_HEADER);
        }

        public TextLink tripsSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_TRIPS_HEADER);
        }

        public TextLink stopsSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_STOPS_HEADER);
        }

        public TextLink distanceDrivenSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_DISTANCE_HEADER);
        }

        public TextLink durationSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_DURATION_HEADER);
        }

        public TextLink idleTimeSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_IDLE_HEADER);
        }

        public TextLink lowIdleSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_LOW_HEADER);
        }

        public TextLink highIdleSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_HIGH_HEADER);
        }

        public TextLink idlePercentSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_PERCENT_HEADER);
        }

        public TextLink fuelEfficiencySort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_FUEL_HEADER);
        }

        public TextLink crashesSort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_CRASHES_HEADER);
        }

        public TextLink safetySort() {
            return new TextLink(TeamDriverStatsEnum.DRIVER_SAFETY_HEADER);
        }

    }

    public class TeamDashboardStatisticsTexts extends TeamBarTexts {

        public TextTable tripsValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_TRIPS_ENTRY);
        }

        public TextTable stopsValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_STOPS_ENTRY);
        }

        public TextTable distanceDrivenValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_DISTANCE_ENTRY);
        }

        public TextTable durationValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_DURATION_ENTRY);
        }

        public TextTable idleTimeValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_IDLE_ENTRY);
        }

        public TextTable lowIdleValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_LOW_ENTRY);
        }

        public TextTable highIdleValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_HIGH_ENTRY);
        }

        public TextTable idlePercentValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_PERCENT_ENTRY);
        }

        public TextTable fuelEfficiencyValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_FUEL_ENTRY);
        }

        public TextTable crashesValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_CRASHES_ENTRY);
        }

        public TextTable safetyValue() {
            return new TextTable(TeamDriverStatsEnum.DRIVER_SAFETY_ENTRY);
        }

        public Text driverTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_NAME_HEADER);
        }

        public Text scoreTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_SCORE_HEADER);
        }

        public Text tripsTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_TRIPS_HEADER);
        }

        public Text stopsTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_STOPS_HEADER);
        }

        public Text distanceDrivenTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_DISTANCE_HEADER);
        }

        public Text durationTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_DURATION_HEADER);
        }

        public Text idleTimeTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_IDLE_HEADER);
        }

        public Text lowIdleTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_LOW_HEADER);
        }

        public Text highIdleTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_HIGH_HEADER);
        }

        public Text idlePercentTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_PERCENT_HEADER);
        }

        public Text fuelEfficiencyTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_FUEL_HEADER);
        }

        public Text crashesTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_CRASHES_HEADER);
        }

        public Text safetyTeamHeader() {
            return new Text(TeamDriverStatsEnum.TEAM_SAFETY_HEADER);
        }

        public Text driverTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_NAME_VALUE);
        }

        public Text scoreTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_SCORE_VALUE);
        }

        public Text tripsTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_TRIPS_VALUE);
        }

        public Text stopsTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_STOPS_VALUE);
        }

        public Text distanceDrivenTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_DISTANCE_VALUE);
        }

        public Text durationTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_DURATION_VALUE);
        }

        public Text idleTimeTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_IDLE_VALUE);
        }

        public Text lowIdleTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_LOW_VALUE);
        }

        public Text highIdleTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_HIGH_VALUE);
        }

        public Text idlePercentTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_PERCENT_VALUE);
        }

        public Text fuelEfficiencyTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_FUEL_VALUE);
        }

        public Text crashesTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_CRASHES_VALUE);
        }

        public Text safetyTeamValue() {
            return new Text(TeamDriverStatsEnum.TEAM_SAFETY_VALUE);
        }
    }

    public class TeamDashboardStatisticsTextFields extends TeamBarTextFields {}

    public class TeamDashboardStatisticsButtons extends TeamBarButtons {

        public Button tools() {
            return new Button(TeamDriverStatsEnum.EXPORT_TOOLS);
        }

        public Button emailReport() {
            return new Button(TeamDriverStatsEnum.EXPORT_EMAIL_TOOL);
        }

        public Button exportToPDF() {
            return new Button(TeamDriverStatsEnum.EXPORT_PDF_TOOL);
        }

        public Button exportToExcel() {
            return new Button(TeamDriverStatsEnum.EXPORT_EXCEL_TOOL);
        }

        public Button editColumns() {
            return new Button(TeamDriverStatsEnum.EDIT_COLUMNS_BUTTON);
        }
    }

    public class TeamDashboardStatisticsDropDowns extends TeamBarDropDowns {}

    public class TeamDashboardStatisticsPopUps extends MastheadPopUps {

        public TeamDashboardStatisticsPopUps() {
            super(page, Types.REPORT, 3);
        }

        public EditColumns editColumns() {
            return new EditColumns();
        }

        public Email emailReport() {
            return new Email();
        }
    }

    public TeamDashboardStatisticsLinks _link() {
        return new TeamDashboardStatisticsLinks();
    }

    public TeamDashboardStatisticsTexts _text() {
        return new TeamDashboardStatisticsTexts();
    }

    public TeamDashboardStatisticsButtons _button() {
        return new TeamDashboardStatisticsButtons();
    }

    public TeamDashboardStatisticsTextFields _textField() {
        return new TeamDashboardStatisticsTextFields();
    }

    public TeamDashboardStatisticsDropDowns _dropDown() {
        return new TeamDashboardStatisticsDropDowns();
    }

    public TeamDashboardStatisticsPopUps _popUp() {
        return new TeamDashboardStatisticsPopUps();
    }

    public Paging _page() {
        return new Paging(pageScroller);
    }

    @Override
    public SeleniumEnums setUrl() {
        return TeamDriverStatsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _text().crashesTeamHeader().isPresent() && _text().distanceDrivenTeamHeader().isPresent();
    }

}