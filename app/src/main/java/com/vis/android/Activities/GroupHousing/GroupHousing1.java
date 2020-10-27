package com.vis.android.Activities.GroupHousing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vis.android.Database.DatabaseController;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class GroupHousing1 extends AppCompatActivity implements View.OnClickListener{
    TextView next;
    Intent intent;
    RelativeLayout back;

    //ArrayList
    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();

    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    public static HashMap<String, String> hm = new HashMap<>();


    //EditText
    public static EditText etProjectName, etProjectPromoters, etProjectBuilder, etProjectArchitect, etTotalEstimatedProjectCost, etLandCost, etEstimatedBuildnConstrtnCost, etCompltdConstrtnCost, etTotalNoOfTowers;
    public static EditText etTotalNoOfFloors, etTotalNoOfFlats, etTypeOfUnits, etSuperArea, etAmenitiesPresent, etTotalLandArea, etTotalGroundCoverageArea, etFarTotalCoveredArea, etProposedGreenArea, etBasementParking;
    public static EditText etStiltParking, etOpenParking, etProposedCompletionDate, etProgressOfTheProject, etDeveloperBuilderPastProjects, etLandMark, etApproachRoadWidth, etProjectLaunchRate, etCurrentBasicSale;
    public static EditText etNorth, etSouth, etEast, etWest;

    JSONArray jsonArrayData;
    JSONObject jsonObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_housing1);
        getid();
        setListener();
        try {
            selectDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getid() {
        back = (RelativeLayout) findViewById(R.id.rl_back);
        next=(TextView) findViewById(R.id.next);
        
        //EditText;
        etProjectName = findViewById(R.id.etProjectName);
        etProjectPromoters = findViewById(R.id.etProjectPromoter);
        etProjectBuilder = findViewById(R.id.etProjectBuilder);
        etProjectArchitect = findViewById(R.id.etProjectArchitect);
        etTotalEstimatedProjectCost = findViewById(R.id.etTotalEstimatedProjectCost);
        etLandCost = findViewById(R.id.etLandCost);
        etEstimatedBuildnConstrtnCost = findViewById(R.id.etEstmtdBuildnConstrctnCost);
        etCompltdConstrtnCost = findViewById(R.id.etCompletedConstrctnCost);
        etTotalNoOfTowers = findViewById(R.id.etTotalNoOfTowers);
        etTotalNoOfFloors = findViewById(R.id.etTotalNoOfFloors);
        etTotalNoOfFlats = findViewById(R.id.etTotalNoOfFlats);
        etTypeOfUnits = findViewById(R.id.etTypeOfUnits);
        etSuperArea = findViewById(R.id.etSuperArea);
        etAmenitiesPresent = findViewById(R.id.etAmenitiesPresent);
        etTotalLandArea = findViewById(R.id.etTotalLandArea);
        etTotalGroundCoverageArea = findViewById(R.id.etTotalGroundCoverageArea);
        etFarTotalCoveredArea = findViewById(R.id.etFarTotalCoveredArea);
        etProposedGreenArea = findViewById(R.id.etProposedGreenArea);
        etBasementParking = findViewById(R.id.etBasementParking);
        etStiltParking = findViewById(R.id.etStiltParking);
        etOpenParking = findViewById(R.id.etOpenParking);
        etProposedCompletionDate = findViewById(R.id.etProposedCompletionDate);
        etProgressOfTheProject = findViewById(R.id.etProgressOfTheProject);
        etDeveloperBuilderPastProjects = findViewById(R.id.etDeveloperBuilderPastProject);
        etLandMark = findViewById(R.id.etLanmark);
        etApproachRoadWidth = findViewById(R.id.etApproachRoadWidth);
        etProjectLaunchRate = findViewById(R.id.etProjectLaunchRate);
        etCurrentBasicSale = findViewById(R.id.etCurrentBasicSalePrice);
        etNorth = findViewById(R.id.etNorth);
        etSouth = findViewById(R.id.etSouth);
        etEast = findViewById(R.id.etEast);
        etWest = findViewById(R.id.etWest);
        
    }

    public void setListener() {
        back.setOnClickListener(this);
        next.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;

            case R.id.next:
                etValidation();
                break;
        }
    }

    private void etValidation(){
        if (etProjectName.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Project Name", Toast.LENGTH_SHORT).show();
            etProjectName.requestFocus();
        } else if (etProjectPromoters.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Project Promoters", Toast.LENGTH_SHORT).show();
            etProjectPromoters.requestFocus();
        } else if (etProjectBuilder.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Project Builder", Toast.LENGTH_SHORT).show();
            etProjectBuilder.requestFocus();
        } else if (etProjectArchitect.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Project Architect", Toast.LENGTH_SHORT).show();
            etProjectArchitect.requestFocus();
        } else if (etTotalEstimatedProjectCost.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total Estimated Project Cost", Toast.LENGTH_SHORT).show();
            etTotalEstimatedProjectCost.requestFocus();
        } else if (etLandCost.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Land Cost", Toast.LENGTH_SHORT).show();
            etLandCost.requestFocus();
        } else if (etEstimatedBuildnConstrtnCost.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Estimated Building Construction Cost", Toast.LENGTH_SHORT).show();
            etEstimatedBuildnConstrtnCost.requestFocus();
        } else if (etCompltdConstrtnCost.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Completed Construction Cost", Toast.LENGTH_SHORT).show();
            etCompltdConstrtnCost.requestFocus();
        } else if (etTotalNoOfTowers.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total No. of Towers/Blocks", Toast.LENGTH_SHORT).show();
            etTotalNoOfTowers.requestFocus();
        } else if (etTotalNoOfFloors.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total No. of Floors Per Tower", Toast.LENGTH_SHORT).show();
            etTotalNoOfFloors.requestFocus();
        } else if (etTotalNoOfFlats.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total No. of Flats", Toast.LENGTH_SHORT).show();
            etTotalNoOfFlats.requestFocus();
        } else if (etTypeOfUnits.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Type of Units", Toast.LENGTH_SHORT).show();
            etTypeOfUnits.requestFocus();
        } else if (etSuperArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Super Area/Covered Area of Units", Toast.LENGTH_SHORT).show();
            etSuperArea.requestFocus();
        } else if (etAmenitiesPresent.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Amenities Present In The Project", Toast.LENGTH_SHORT).show();
            etAmenitiesPresent.requestFocus();
        } else if (etTotalLandArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total Land Area", Toast.LENGTH_SHORT).show();
            etTotalLandArea.requestFocus();
        } else if (etTotalGroundCoverageArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Total Ground Coverage Area", Toast.LENGTH_SHORT).show();
            etTotalGroundCoverageArea.requestFocus();
        } else if (etFarTotalCoveredArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Far/Total Covered Area", Toast.LENGTH_SHORT).show();
            etFarTotalCoveredArea.requestFocus();
        } else if (etProposedGreenArea.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Proposed Green Area", Toast.LENGTH_SHORT).show();
            etProposedGreenArea.requestFocus();
        } else if (etBasementParking.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Basement Parking", Toast.LENGTH_SHORT).show();
            etBasementParking.requestFocus();
        } else if (etStiltParking.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Stilt Parking", Toast.LENGTH_SHORT).show();
            etStiltParking.requestFocus();
        } else if (etOpenParking.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Open Parking", Toast.LENGTH_SHORT).show();
            etOpenParking.requestFocus();
        } else if (etProposedCompletionDate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Proposed Proposed Completion Date of The Project", Toast.LENGTH_SHORT).show();
            etProposedCompletionDate.requestFocus();
        } else if (etProgressOfTheProject.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Progress of The Project", Toast.LENGTH_SHORT).show();
            etProgressOfTheProject.requestFocus();
        } else if (etDeveloperBuilderPastProjects.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Developer/Builder Past Projects", Toast.LENGTH_SHORT).show();
            etDeveloperBuilderPastProjects.requestFocus();
        } else if (etLandMark.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Landmark", Toast.LENGTH_SHORT).show();
            etLandMark.requestFocus();
        } else if (etApproachRoadWidth.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Approach Road Width", Toast.LENGTH_SHORT).show();
            etApproachRoadWidth.requestFocus();
        } else if (etProjectLaunchRate.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Project Launch Rate", Toast.LENGTH_SHORT).show();
            etProjectLaunchRate.requestFocus();
        } else if (etCurrentBasicSale.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Current Basic Sale Price", Toast.LENGTH_SHORT).show();
            etCurrentBasicSale.requestFocus();
        } else if (etNorth.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter North Boundary", Toast.LENGTH_SHORT).show();
            etNorth.requestFocus();
        } else if (etSouth.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter South Boundary", Toast.LENGTH_SHORT).show();
            etSouth.requestFocus();
        } else if (etEast.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter East Boundary", Toast.LENGTH_SHORT).show();
            etEast.requestFocus();
        } else if (etWest.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter West Boundary", Toast.LENGTH_SHORT).show();
            etWest.requestFocus();
        }
        else {
            putintoHashMap();
            intent=new Intent(GroupHousing1.this,GroupHousing2.class);

            intent.putExtra("arrayList", arrayListData);

            startActivity(intent);
            return;
        }
    }
    
    public void putintoHashMap(){

        hm.put("projectName", etProjectName.getText().toString());
        hm.put("projectPromoters", etProjectPromoters.getText().toString());
        hm.put("projectBuilder", etProjectBuilder.getText().toString());
        hm.put("projectArchitect", etProjectArchitect.getText().toString());
        hm.put("totalEstimatedProjectCost", etTotalEstimatedProjectCost.getText().toString());
        hm.put("landCost", etLandCost.getText().toString());
        hm.put("estimatedBuildingConstructionCost", etEstimatedBuildnConstrtnCost.getText().toString());
        hm.put("completedConstructionCost", etCompltdConstrtnCost.getText().toString());
        hm.put("totalNoOfTowers", etTotalNoOfTowers.getText().toString());
        hm.put("totalNoOfFloors", etTotalNoOfFloors.getText().toString());
        hm.put("totalNoOfFlats", etTotalNoOfFlats.getText().toString());
        hm.put("typeOfUnits", etTypeOfUnits.getText().toString());
        hm.put("superArea", etSuperArea.getText().toString());
        hm.put("amenitiesPresent", etAmenitiesPresent.getText().toString());
        hm.put("totalLandArea", etTotalLandArea.getText().toString());
        hm.put("totalGroundCoverageArea", etTotalGroundCoverageArea.getText().toString());
        hm.put("farTotalCoveredArea", etFarTotalCoveredArea.getText().toString());
        hm.put("proposedGreenArea", etProposedGreenArea.getText().toString());
        hm.put("basementParking", etBasementParking.getText().toString());
        hm.put("stiltParking", etStiltParking.getText().toString());
        hm.put("openParking", etOpenParking.getText().toString());
        hm.put("proposedCompletionDate", etProposedCompletionDate.getText().toString());
        hm.put("progressOfTheProject", etProgressOfTheProject.getText().toString());
        hm.put("developerBuilderPastProject", etDeveloperBuilderPastProjects.getText().toString());
        hm.put("landmark", etLandMark.getText().toString());
        hm.put("approachRoadWidth", etApproachRoadWidth.getText().toString());
        hm.put("projectLaunchRate", etProjectLaunchRate.getText().toString());
        hm.put("currentBasicSalePrice", etCurrentBasicSale.getText().toString());
        hm.put("north", etNorth.getText().toString());
        hm.put("south", etSouth.getText().toString());
        hm.put("east", etEast.getText().toString());
        hm.put("west", etWest.getText().toString());

        //arrayListData.add(hm);




       /* jsonArrayData = new JSONArray(arrayListData);

        Log.v("###jso",jsonArrayData.toString());

        DatabaseController.removeTable(TableGroupHousing.attachment);

        ContentValues contentValues = new ContentValues();

        contentValues.put(TableGroupHousing.groupHousingColumn.data.toString(), jsonArrayData.toString());

        Log.v("###contentValues", String.valueOf(contentValues));

        DatabaseController.insertData(contentValues, TableGroupHousing.attachment);


        Log.v("###ArrayData1", String.valueOf(arrayListData));*/

    }


    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getGroupHousing();
        if (sub_cat!=null){

            Log.v("getfromdb=====", String.valueOf(sub_cat));

            JSONArray array = new JSONArray(sub_cat.get(0).get("data"));

            Log.v("getfromdbarray=====", String.valueOf(array));

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                etProjectName.setText(object.getString("projectName"));
                etProjectPromoters.setText(object.getString("projectPromoters"));
                etProjectBuilder.setText(object.getString("projectBuilder"));
                etProjectArchitect.setText(object.getString("projectArchitect"));
                etTotalEstimatedProjectCost.setText(object.getString("totalEstimatedProjectCost"));
                etLandCost.setText(object.getString("landCost"));
                etEstimatedBuildnConstrtnCost.setText(object.getString("estimatedBuildingConstructionCost"));
                etCompltdConstrtnCost.setText(object.getString("completedConstructionCost"));
                etTotalNoOfTowers.setText(object.getString("totalNoOfTowers"));
                etTotalNoOfFloors.setText(object.getString("totalNoOfFloors"));
                etTotalNoOfFlats.setText(object.getString("totalNoOfFlats"));
                etTypeOfUnits.setText(object.getString("typeOfUnits"));
                etSuperArea.setText(object.getString("superArea"));
                etAmenitiesPresent.setText(object.getString("amenitiesPresent"));
                etTotalLandArea.setText(object.getString("totalLandArea"));
                etTotalGroundCoverageArea.setText(object.getString("totalGroundCoverageArea"));
                etFarTotalCoveredArea.setText(object.getString("farTotalCoveredArea"));
                etProposedGreenArea.setText(object.getString("proposedGreenArea"));
                etBasementParking.setText(object.getString("basementParking"));
                etStiltParking.setText(object.getString("stiltParking"));
                etOpenParking.setText(object.getString("openParking"));
                etProposedCompletionDate.setText(object.getString("proposedCompletionDate"));
                etProgressOfTheProject.setText(object.getString("progressOfTheProject"));
                etDeveloperBuilderPastProjects.setText(object.getString("developerBuilderPastProject"));
                etLandMark.setText(object.getString("landmark"));
                etApproachRoadWidth.setText(object.getString("approachRoadWidth"));
                etProjectLaunchRate.setText(object.getString("projectLaunchRate"));
                etCurrentBasicSale.setText(object.getString("currentBasicSalePrice"));
                etNorth.setText(object.getString("north"));
                etSouth.setText(object.getString("south"));
                etEast.setText(object.getString("east"));
                etWest.setText(object.getString("west"));



                String projectName = object.getString("projectName");
                Log.e("!!!projectName", projectName);
        }


        }
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
