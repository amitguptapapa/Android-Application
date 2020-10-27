package com.vis.android.Database;

public class TableGroupHousing {
   /* static final String SQL_CREATE_DATA = ("CREATE TABLE attachment (" +
            groupHousingColumn.projectName + " VARCHAR,"
            + groupHousingColumn.projectPromoters + " VARCHAR,"
            + groupHousingColumn.projectBuilder + " VARCHAR,"
            + groupHousingColumn.projectArchitect + " VARCHAR,"
            + groupHousingColumn.totalEstimatedProjectCost + " VARCHAR,"
            + groupHousingColumn.landCost + " VARCHAR,"
            + groupHousingColumn.estimatedBuildingConstructionCost + " VARCHAR,"
            + groupHousingColumn.completedConstructionCost + " VARCHAR,"
            + groupHousingColumn.totalNoOfTowers + " VARCHAR,"
            + groupHousingColumn.totalNoOfFloors + " VARCHAR,"
            + groupHousingColumn.totalNoOfFlats + " VARCHAR,"
            + groupHousingColumn.typeOfUnits + " VARCHAR,"
            + groupHousingColumn.superArea + " VARCHAR,"
            + groupHousingColumn.amenitiesPresent + " VARCHAR,"
            + groupHousingColumn.totalLandArea + " VARCHAR,"
            + groupHousingColumn.totalGroundCoverageArea + " VARCHAR,"
            + groupHousingColumn.farTotalCoveredArea + " VARCHAR,"
            + groupHousingColumn.proposedGreenArea + " VARCHAR,"
            + groupHousingColumn.basementParking + " VARCHAR,"
            + groupHousingColumn.stiltParking + " VARCHAR,"
            + groupHousingColumn.openParking + " VARCHAR,"
            + groupHousingColumn.proposedCompletionDate + " VARCHAR,"
            + groupHousingColumn.progressOfTheProject + " VARCHAR,"
            + groupHousingColumn.developerBuilderPastProject + " VARCHAR,"
            + groupHousingColumn.landmark + " VARCHAR,"
            + groupHousingColumn.approachRoadWidth + " VARCHAR,"
            + groupHousingColumn.projectLaunchRate + " VARCHAR,"
            + groupHousingColumn.currentBasicSalePrice + " VARCHAR,"
            + groupHousingColumn.north + " VARCHAR,"
            + groupHousingColumn.south + " VARCHAR,"
            + groupHousingColumn.east + " VARCHAR,"
            + groupHousingColumn.west + " VARCHAR" + ")");*/

   static final String SQL_CREATE_DATA = ("CREATE TABLE attachment (" +
           groupHousingColumn.data + " VARCHAR" + ")");

    public static final String attachment = "attachment";

    public enum groupHousingColumn {
        data
    /*    projectName,
        projectPromoters,
        projectBuilder,
        projectArchitect,
        totalEstimatedProjectCost,
        landCost,
        estimatedBuildingConstructionCost,
        completedConstructionCost,
        totalNoOfTowers,
        totalNoOfFloors,
        totalNoOfFlats,
        typeOfUnits,
        superArea,
        amenitiesPresent,
        totalLandArea,
        totalGroundCoverageArea,
        farTotalCoveredArea,
        proposedGreenArea,
        basementParking,
        stiltParking,
        openParking,
        proposedCompletionDate,
        progressOfTheProject,
        developerBuilderPastProject,
        landmark,
        approachRoadWidth,
        projectLaunchRate,
        currentBasicSalePrice,
        north,
        south,
        east,
        west*/

    }
}