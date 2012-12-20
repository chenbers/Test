package com.inthinc.pro.selenium.pageObjects;

import com.inthinc.pro.automation.elements.Button;
import com.inthinc.pro.automation.elements.Text;
import com.inthinc.pro.automation.elements.TextLink;
import com.inthinc.pro.automation.elements.TextLinkScoreTable;
import com.inthinc.pro.automation.elements.TextTable;
import com.inthinc.pro.automation.elements.TextTableLink;
import com.inthinc.pro.automation.interfaces.SeleniumEnums;
import com.inthinc.pro.selenium.pageEnums.TeamVehicleStatsEnum;

public class PageTeamVehicleStatistics extends TeamBar {

    private String pageScroller = "teamStatisticsVehicleForm:drivers:driverScroller_table";
    private static final String page = "teamVehicleStatistics";

    public class TeamDashboardStatisticsLinks extends TeamBarLinks {

        public TextTableLink driverValue() {
            return new TextTableLink(TeamVehicleStatsEnum.DRIVER_NAME_ENTRY);
        }

        public TextTableLink vehicleValue() {
            return new TextTableLink(TeamVehicleStatsEnum.DRIVER_VEHICLE_ENTRY);
        }

        public TextLinkScoreTable scoreValue() {
            return new TextLinkScoreTable(TeamVehicleStatsEnum.DRIVER_SCORE_ENTRY);
        }

        public TextLink sortByDriver() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_NAME_FILTER);
        }

        public TextLink sortByScore() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_SCORE_FILTER);
        }

        public TextLink sortByVehicle() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_VEHICLE_FILTER);
        }

        public TextLink sortByTrips() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_TRIPS_FILTER);
        }

        public TextLink sortByStops() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_STOPS_FILTER);
        }

        public TextLink sortByDistanceDriven() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_DISTANCE_FILTER);
        }

        public TextLink sortByDuration() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_DURATION_FILTER);
        }

        public TextLink sortByIdleTime() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_IDLE_FILTER);
        }

        public TextLink sortByLowIdle() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_LOW_FILTER);
        }

        public TextLink sortByHighIdle() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_HIGH_FILTER);
        }

        public TextLink sortByIdlePercent() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_PERCENT_FILTER);
        }

        public TextLink sortByFuelEfficiency() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_FUEL_FILTER);
        }

        public TextLink sortByCrashes() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_CRASHES_FILTER);
        }

        public TextLink sortBySafety() {
            return new TextLink(TeamVehicleStatsEnum.DRIVER_SAFETY_FILTER);
        }

    }

    public class TeamDashboardStatisticsTexts extends TeamBarTexts {

        public TextTable tripsValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_TRIPS_ENTRY);
        }

        public TextTable stopsValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_STOPS_ENTRY);
        }

        public TextTable distanceDrivenValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_DISTANCE_ENTRY);
        }

        public TextTable durationValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_DURATION_ENTRY);
        }

        public TextTable idleTimeValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_IDLE_ENTRY);
        }

        public TextTable lowIdleValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_LOW_ENTRY);
        }

        public TextTable highIdleValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_HIGH_ENTRY);
        }

        public TextTable idlePercentValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_PERCENT_ENTRY);
        }

        public TextTable fuelEfficiencyValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_FUEL_ENTRY);
        }

        public TextTable crashesValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_CRASHES_ENTRY);
        }

        public TextTable safetyValue() {
            return new TextTable(TeamVehicleStatsEnum.DRIVER_SAFETY_ENTRY);
        }

        public Text driverTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_NAME_HEADER);
        }

        public Text scoreTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_SCORE_HEADER);
        }

        public Text tripsTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_TRIPS_HEADER);
        }

        public Text stopsTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_STOPS_HEADER);
        }

        public Text distanceDrivenTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_DISTANCE_HEADER);
        }

        public Text durationTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_DURATION_HEADER);
        }

        public Text idleTimeTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_IDLE_HEADER);
        }

        public Text lowIdleTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_LOW_HEADER);
        }

        public Text highIdleTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_HIGH_HEADER);
        }

        public Text idlePercentTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_PERCENT_HEADER);
        }

        public Text fuelEfficiencyTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_FUEL_HEADER);
        }

        public Text crashesTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_CRASHES_HEADER);
        }

        public Text safetyTeamHeader() {
            return new Text(TeamVehicleStatsEnum.TEAM_SAFETY_HEADER);
        }

        public Text driverTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_NAME_VALUE);
        }

        public Text scoreTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_SCORE_VALUE);
        }

        public Text tripsTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_TRIPS_VALUE);
        }

        public Text stopsTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_STOPS_VALUE);
        }

        public Text distanceDrivenTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_DISTANCE_VALUE);
        }

        public Text durationTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_DURATION_VALUE);
        }

        public Text idleTimeTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_IDLE_VALUE);
        }

        public Text lowIdleTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_LOW_VALUE);
        }

        public Text highIdleTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_HIGH_VALUE);
        }

        public Text idlePercentTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_PERCENT_VALUE);
        }

        public Text fuelEfficiencyTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_FUEL_VALUE);
        }

        public Text crashesTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_CRASHES_VALUE);
        }

        public Text safetyTeamValue() {
            return new Text(TeamVehicleStatsEnum.TEAM_SAFETY_VALUE);
        }
    }

    public class TeamDashboardStatisticsTextFields extends TeamBarTextFields {}

    public class TeamDashboardStatisticsButtons extends TeamBarButtons {

        public Button tools() {
            return new Button(TeamVehicleStatsEnum.EXPORT_TOOLS);
        }

        public Button emailReport() {
            return new Button(TeamVehicleStatsEnum.EXPORT_EMAIL_TOOL);
        }

        public Button exportToPDF() {
            return new Button(TeamVehicleStatsEnum.EXPORT_PDF_TOOL);
        }

        public Button exportToExcel() {
            return new Button(TeamVehicleStatsEnum.EXPORT_EXCEL_TOOL);
        }

        public Button editColumns() {
            return new Button(TeamVehicleStatsEnum.EDIT_COLUMNS_BUTTON);
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
        return TeamVehicleStatsEnum.DEFAULT_URL;
    }

    @Override
    protected boolean checkIsOnPage() {
        return _link().sortByDriver().isPresent() && _link().sortByVehicle().isPresent();
    }

}