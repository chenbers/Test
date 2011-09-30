package com.inthinc.pro.automation.elements.organization;


public class OrganizationLevels {
    
    public OrganizationLevels(){}
    

    public class TeamLevel extends OrganizationBase {
        public TeamLevel(int[] parent, int position){
            super(parent, position, OrganizationType.TEAM);
        }
        
        public TeamLevel(int[] parent, String position){
            super(parent, position, OrganizationType.TEAM);
        }
        
        public TeamLevel(){
            super(OrganizationType.TEAM);
        }
        
        public DriverLevel getDriver(int position){
            return new DriverLevel(structure, position);
        }
        
        public UserLevel getUser(int position){
            return new UserLevel(structure, position);
        }
        
        public VehicleLevel getVehicle(int position){
            return new VehicleLevel(structure, position);
        }
        
        public DriverLevel getDriver(String driverName){
            return new DriverLevel(structure, driverName);
        }
        
        public UserLevel getUser(String userName){
            return new UserLevel(structure, userName);
        }
        
        public VehicleLevel getVehicle(String vehicleName){
            return new VehicleLevel(structure, vehicleName);
        }
    }
    
    public class DivisionLevel extends OrganizationBase {
        public DivisionLevel(int[] parent, int position){
            super(parent, position, OrganizationType.DIVISION);
        }
        
        public DivisionLevel(int[] parent, String position){
            super(parent, position, OrganizationType.DIVISION);
        }
        
        public DivisionLevel(){
            super(OrganizationType.DIVISION);
        }
        

        public UserLevel getUser(int position){
            return new UserLevel(structure, position);
        }
        
        public DivisionLevel getDivision(int position){
            return new DivisionLevel(structure, position);
        }
        
        public TeamLevel getTeam(int position){
            return new TeamLevel(structure, position);
        }
        
        public UserLevel getUser(String userName){
            return new UserLevel(structure, userName);
        }
        
        public DivisionLevel getDivision(String divisionName){
            return new DivisionLevel(structure, divisionName);
        }
        
        public TeamLevel getTeam(String teamName){
            return new TeamLevel(structure, teamName);
        }
        
    }
    
    public class FleetLevel extends OrganizationBase {
        public FleetLevel(int[] parent, int position){
            super(parent, position, OrganizationType.FLEET);
        }
        
        public FleetLevel(int[] parent, String position){
            super(parent, position, OrganizationType.FLEET);
        }
        
        public FleetLevel(){
            super(OrganizationType.FLEET);
        }
        

        public UserLevel getUser(int position){
            return new UserLevel(structure, position);
        }
        
        public DivisionLevel getDivision(int position){
            return new DivisionLevel(structure, position);
        }
        
        public TeamLevel getTeam(int position){
            return new TeamLevel(structure, position);
        }
        
        public UserLevel getUser(String userName){
            return new UserLevel(structure, userName);
        }
        
        public DivisionLevel getDivision(String divisionName){
            return new DivisionLevel(structure, divisionName);
        }
        
        public TeamLevel getTeam(String teamName){
            return new TeamLevel(structure, teamName);
        }
        
    }
    
    
    public class UserLevel extends OrganizationBase{
        public UserLevel(int[] parent, int position){
            super(parent, position, OrganizationType.USER);
        }
        
        public UserLevel(int[] parent, String position){
            super(parent, position, OrganizationType.USER);
        }
        
        public UserLevel(){
            super(OrganizationType.USER);
        }
    }
    
    public class VehicleLevel  extends OrganizationBase{
        public VehicleLevel(int[] parent, int position){
            super(parent, position, OrganizationType.VEHICLE);
        }
        
        public VehicleLevel(int[] parent, String position){
            super(parent, position, OrganizationType.VEHICLE);
        }
        
        public VehicleLevel(){
            super(OrganizationType.VEHICLE);
        }
    }
    
    public class DriverLevel  extends OrganizationBase{
        public DriverLevel(int[] parent, int position){
            super(parent, position, OrganizationType.DRIVER);
        }
        
        public DriverLevel(int[] parent, String position){
            super(parent, position, OrganizationType.DRIVER);
        }
        
        public DriverLevel(){
            super(OrganizationType.DRIVER);
        }
    }

}
