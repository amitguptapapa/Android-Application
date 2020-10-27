package com.vis.android.Activities.General.Fragments;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.vis.android.Activities.Dashboard;
import com.vis.android.Activities.General.SpinnerAdapter;
import com.vis.android.Common.Constants;
import com.vis.android.Common.Preferences;
import com.vis.android.Database.DatabaseController;
import com.vis.android.Database.TableGeneralForm;
import com.vis.android.Extras.BaseFragment;
import com.vis.android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;

public class GeneralForm8 extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    // RelativeLayout back, dots;
    LinearLayout llQuesB, llUptoSlabLevel;
    TextView next, tvPrevious;
    TextView tvQuesC, tvQuesD, tvQuesE, tvQuesF, tvQuesG, tvQuesH, tvQuesI, tvQuesJ, //tvQuesK,
            tvQuesL, tvQuesM, tvQuesN, tvQuesO, tvQuesP, tvQuesQ, tvQuesR, tvQuesS, tvQuesT, tvQuesU, tvQuesV, tvQuesW, tvQuesX;
    Intent intent;
    Boolean rdChecker = false;

    String typeSurveyChecker;
    RadioGroup rgConstructionStatus, rgBuildingType, rgClassOfConstruction, rgAppearanceInternal, rgAppearanceExternal, //rgMaintenanceOfTheBuilding,
            rgInteriorDecoration, rgKitchen, rgClassOfElectricalsOne, rgClassOfElectricalsTwo, rgClassOfElectricalsThree, rgClassOfSanitarysOne, rgClassOfSanitarysTwo, rgClassOfSanitarysThree, rgFixedWoodenWork, rgMaintenanceOfTheBuilding2;

    EditText etTotalNumberOfFloors, etFloorOnWhich, etRoofHeight, etAgeOfBuilding, etRecentImprovements, etExtentOfViolation, etUptoSlabLevel,
            etCubicles, etCabins, etNumberOfRooms, etTypeOfUnit, etFlooringAnyOtherType;

    CheckBox cbRcc, cbRbc, cbGiShed, cbTinShed, cbStonePatla, cbSimplePlaster, cbPopPunning, cbPopFalseCeiling, cbCovedRoof, cbNoPlaster,
            cbDomedRoof, cbBeautifulCarving, cbNoInfoAvailable, cbUnderConstructionFinish, cbMudFlooring, cbBrickFlooring, cbVitrifiedTiles, cbCeramicTiles, cbSimpleMarble, cbMarbleChips, cbMosaic,
            cbGranite, cbItalianMarble, cbKotaStone, cbWooden, cbPcc, cbImportedMarble, cbPavers, cbChequered, cbBrickTiles, cbVinylFlooring,
            cbLaminateFlooring, cbLinoleumFlooring, cbNoFlooring, cbUnderConstruction, cbAnyOtherType, cbNoInfoAvaill, cbIntSimplePlastered, cbIntBrickWalls,
            cbIntDesignerTextured, cbIntPopPunning, cbIntCovedRoof, cbIntVeneerLaminated, cbIntSteelLaminated, cbIntPoorDepleted,
            cbItBrickTileCladding, cbPrismGlassPortionn, cbGlassPartitions, cbDesignerFalseCeiling, cbWoodVeneerLaminatedWalls, cbDesignerCovedRoof, cbNeatlyPlasteredAndPuttyCoatedWalls, cbArchitecturallyDesignedOrEevated, cbIntUnderConstruction, cbIntUnderFinishing, cbNInfoIhFinishing,
            cbExtSimplePlastered, cbExtBrickWalls, cbExtArchitecturally, cbExtBrickTile, cbExtStructuralGlazing, cbExtAluminumComposite, cbExtGlassFacade,
            cbExtDomb, cbExtPorch, cbExtPoorDepleted, cbExtUnderFinishing, cbExtUnderConstruction, cbJetPump, cbSubmersible, cbJalBoardSupply, cbNoInfoAvailWater, cbMaintenanceIssues, cbFinishingIssues, cbSeepageIssues,
            cbWaterSupplyIssues, cbElectricalIssues, cbStructuralIssues, cbVisibleCracks, cbRunDownBuilding, cbNoMoreSafe, cbNotAppropriate,
            cbUnderConstructionDefects, cbCompletelyRunDownBuilding, cbNoDefect, cbUnderConstructionAnyViolation, cbNoInfo, cbConstructionDoneWithoutMap, cbConstructionNotAsPer, cbExtraCovered, cbJoinedAdjacent, cbEncroachedAdjacent, cbNoViolation, cbNoViolence, cbUnderConstructionWater;

    int cbRccCheck, cbRbcCheck, cbGiShedCheck, cbTinShedCheck, cbStonePatlaCheck, cbSimplePlasterCheck, cbPopPunningCheck, cbPopFalseCeilingCheck,
            cbCovedRoofCheck, cbNoPlasterCheck, cbDomedRoofCheck, cbBeautifulCarvingCheck, cbNoInfoAvailableCheck, cbUnderConstructionFinishCheck, cbMudFlooringCheck, cbBrickFlooringCheck,
            cbVitrifiedTilesCheck, cbCeramicTilesCheck, cbSimpleMarbleCheck, cbMarbleChipsCheck, cbMosaicCheck, cbGraniteCheck,
            cbItalianMarbleCheck, cbKotaStoneCheck, cbWoodenCheck, cbPccCheck, cbImportedMarbleCheck, cbPaversCheck, cbChequeredCheck,
            cbBrickTilesCheck, cbVinylFlooringCheck, cbLaminateFlooringCheck, cbLinoleumFlooringCheck, cbNoFlooringCheck, cbUnderConstructionCheck,
            cbAnyOtherTypeCheck, cbNoInfoAvaillCheck, cbIntSimplePlasteredCheck, cbIntBrickWallsCheck, cbIntDesignerTexturedCheck, cbIntPopPunningCheck, cbIntCovedRoofCheck,
            cbIntVeneerLaminatedCheck, cbIntSteelLaminatedCheck, cbIntUnderConstructionCheck, cbIntUnderFinishingCheck, cbNInfoIhFinishingCheck, cbExtSimplePlasteredCheck,
            cbExtBrickWallsCheck, cbExtArchitecturallyCheck, cbExtBrickTileCheck, cbExtStructuralGlazingCheck, cbExtAluminumCompositeCheck,
            cbExtGlassFacadeCheck, cbExtDombCheck, cbExtPorchCheck, cbExtPoorDepletedCheck, cbExtUnderFinishingCheck, cbIntPoorDepletedCheck,
            cbItBrickTileCladdingCheck, cbPrismGlassPortionnCheck, cbGlassPartitionsCheck, cbDesignerFalseCeilingCheck, cbWoodVeneerLaminatedWallsCheck, cbDesignerCovedRoofCheck, cbNeatlyPlasteredAndPuttyCoatedWallsCheck, cbArchitecturallyDesignedOrEevatedCheck, cbExtUnderConstructionCheck, cbJetPumpCheck, cbSubmersibleCheck,
            cbJalBoardSupplyCheck, cbNoInfoAvailWaterCheck, cbMaintenanceIssuesCheck, cbFinishingIssuesCheck, cbSeepageIssuesCheck, cbWaterSupplyIssuesCheck,
            cbElectricalIssuesCheck, cbStructuralIssuesCheck, cbVisibleCracksCheck, cbRunDownBuildingCheck, cbNoMoreSafeCheck, cbNotAppropriateCheck,
            cbUnderConstructionDefectsCheck, cbCompletelyRunDownBuildingCheck, cbNoDefectCheck, cbNoInfoCheck, cbConstructionDoneWithoutMapCheck,
            cbConstructionNotAsPerCheck, cbExtraCoveredCheck, cbJoinedAdjacentCheck, cbEncroachedAdjacentCheck, cbUnderConstructionAnyViolationCheck, cbNoViolenceCheck, cbNoViolationCheck, cbUnderConstructionWaterCheck;
    int elect1 = -1, elect2 = -1, elect3 = -1, sanit1 = -1, sanit2 = -1, sanit3 = -1;
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    String[] arrayInCase = {"Choose an item", "Excavation and land development has been started", "Only excavation, land filling & land development completed", "Foundation work in progress",
            "Foundation work completed", "Super structure work in progress", "Super structure completed", "Slab work in progress", "Slab work completed", "Brick work in progress",
            "Super structure & Brick work completed", "Plaster work in progress", "Finishing in progress"};

    String[] arrayFtMtr = {"Ft.", "Mtr."};

    Spinner spinnerInCaseOfUnderConstruction, spinnerHeightFtMtr;
    //  ProgressBar progressbar;
    SpinnerAdapter spinnerAdapter;
    Preferences pref;
    Boolean edit_page = true;


    // TextView tv_caseheader,tv_caseid,tv_header;

    //RelativeLayout rl_casedetail;

    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_generalform8, container, false);

        getid(v);
        setListener();
        populateSinner();

//        tv_header.setVisibility(View.GONE);
//        rl_casedetail.setVisibility(View.VISIBLE);
//        tv_caseheader.setText("General Survey Form");
//        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));

//        progressbar.setMax(10);
//        progressbar.setProgress(8);
//       // Arrays.sort(arrayInCase, 1, arrayInCase.length);




        if (InitiateSurveyForm.surveyTypeCheck == 1) {
            etExtentOfViolation.setText("No information available");
        }


        try {
            if (InitiateSurveyForm.surveyTypeCheck == 1 && typeSurveyChecker.equalsIgnoreCase("true")) {
                cbNoInfoAvailable.setChecked(true);
                cbNoInfoAvaill.setChecked(true);
                ((RadioButton) rgAppearanceInternal.getChildAt(9)).setChecked(true);
                ((RadioButton) rgAppearanceExternal.getChildAt(9)).setChecked(true);
                ((RadioButton) rgInteriorDecoration.getChildAt(15)).setChecked(true);

                cbNInfoIhFinishing.setChecked(true);
//                ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);

                cbNoInfoAvailWater.setChecked(true);
                ((RadioButton) rgFixedWoodenWork.getChildAt(14)).setChecked(true);
                ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(9)).setChecked(true);
                cbNoInfo.setChecked(true);
                cbNoViolence.setChecked(true);
                rgInteriorDecoration.getChildAt(12).setSelected(true);
                ((RadioButton) rgClassOfElectricalsThree.getChildAt(2)).setChecked(true);
                rgClassOfElectricalsTwo.clearCheck();
                rgClassOfElectricalsOne.clearCheck();
                for (int imp = 0; imp < rgClassOfElectricalsTwo.getChildCount(); imp++)
                    rgClassOfElectricalsTwo.getChildAt(imp).setEnabled(false);
                for (int imp = 0; imp < rgClassOfElectricalsOne.getChildCount(); imp++)
                    rgClassOfElectricalsOne.getChildAt(imp).setEnabled(false);

                ((RadioButton) rgClassOfSanitarysThree.getChildAt(2)).setChecked(true);
                rgClassOfSanitarysTwo.clearCheck();
                rgClassOfSanitarysOne.clearCheck();
                for (int imp = 0; imp < rgClassOfSanitarysTwo.getChildCount(); imp++)
                    rgClassOfSanitarysTwo.getChildAt(imp).setEnabled(false);
                for (int imp = 0; imp < rgClassOfSanitarysOne.getChildCount(); imp++)
                    rgClassOfSanitarysOne.getChildAt(imp).setEnabled(false);


            } else {
                rgInteriorDecoration.getChildAt(13).setSelected(true);
                if(elect1>-1)
                ((RadioButton) rgClassOfElectricalsOne.getChildAt(elect1)).setChecked(true);
                if(elect2>-1)
                ((RadioButton) rgClassOfElectricalsTwo.getChildAt(elect2)).setChecked(true);
                if(elect3>-1)
                    ((RadioButton) rgClassOfElectricalsThree.getChildAt(elect3)).setChecked(true);

                if(sanit1>-1)
                ((RadioButton) rgClassOfSanitarysOne.getChildAt(sanit1)).setChecked(true);
                if(sanit2>-1)
                ((RadioButton) rgClassOfSanitarysTwo.getChildAt(sanit2)).setChecked(true);
                if(sanit3>-1)
                    ((RadioButton) rgClassOfSanitarysThree.getChildAt(sanit3)).setChecked(true);
                try {
                    selectDB();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!edit_page) {
            setEditiblity();
        }


        return v;
    }

    public void setEditiblity() {

        for (int i = 0; i < rgConstructionStatus.getChildCount(); i++)
            rgConstructionStatus.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgBuildingType.getChildCount(); i++)
            rgBuildingType.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgClassOfConstruction.getChildCount(); i++)
            rgClassOfConstruction.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgAppearanceInternal.getChildCount(); i++)
            rgAppearanceInternal.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgAppearanceExternal.getChildCount(); i++)
            rgAppearanceExternal.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgInteriorDecoration.getChildCount(); i++)
            rgInteriorDecoration.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgKitchen.getChildCount(); i++)
            rgKitchen.getChildAt(i).setClickable(false);


        for (int i = 0; i < rgClassOfElectricalsOne.getChildCount(); i++)
            rgClassOfElectricalsOne.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgClassOfElectricalsTwo.getChildCount(); i++)
            rgClassOfElectricalsTwo.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgClassOfElectricalsThree.getChildCount(); i++)
            rgClassOfElectricalsThree.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgClassOfSanitarysOne.getChildCount(); i++)
            rgClassOfSanitarysOne.getChildAt(i).setClickable(false);


        for (int i = 0; i < rgClassOfSanitarysTwo.getChildCount(); i++)
            rgClassOfSanitarysTwo.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgClassOfSanitarysThree.getChildCount(); i++)
            rgClassOfSanitarysThree.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgFixedWoodenWork.getChildCount(); i++)
            rgFixedWoodenWork.getChildAt(i).setClickable(false);
        for (int i = 0; i < rgMaintenanceOfTheBuilding2.getChildCount(); i++)
            rgMaintenanceOfTheBuilding2.getChildAt(i).setClickable(false);


        spinnerInCaseOfUnderConstruction.setEnabled(false);
        spinnerHeightFtMtr.setEnabled(false);

        etTotalNumberOfFloors.setEnabled(false);
        etFloorOnWhich.setEnabled(false);
        etRoofHeight.setEnabled(false);
        etAgeOfBuilding.setEnabled(false);
        etRecentImprovements.setEnabled(false);
        etExtentOfViolation.setEnabled(false);
        etUptoSlabLevel.setEnabled(false);

        etCubicles.setEnabled(false);
        etCabins.setEnabled(false);
        etNumberOfRooms.setEnabled(false);
        etTypeOfUnit.setEnabled(false);
        etFlooringAnyOtherType.setEnabled(false);

        cbRcc.setClickable(false);
        cbRbc.setClickable(false);
        cbGiShed.setClickable(false);
        cbTinShed.setClickable(false);
        cbStonePatla.setClickable(false);
        cbSimplePlaster.setClickable(false);
        cbPopPunning.setClickable(false);
        cbPopFalseCeiling.setClickable(false);
        cbCovedRoof.setClickable(false);
        cbNoPlaster.setClickable(false);

        cbDomedRoof.setClickable(false);
        cbBeautifulCarving.setClickable(false);
        cbNoInfoAvailable.setClickable(false);
        cbUnderConstructionFinish.setClickable(false);
        cbMudFlooring.setClickable(false);
        cbBrickFlooring.setClickable(false);
        cbVitrifiedTiles.setClickable(false);
        cbCeramicTiles.setClickable(false);
        cbSimpleMarble.setClickable(false);
        cbMarbleChips.setClickable(false);
        cbMosaic.setClickable(false);

        cbGranite.setClickable(false);
        cbItalianMarble.setClickable(false);
        cbKotaStone.setClickable(false);
        cbWooden.setClickable(false);
        cbPcc.setClickable(false);
        cbImportedMarble.setClickable(false);
        cbPavers.setClickable(false);
        cbChequered.setClickable(false);
        cbBrickTiles.setClickable(false);
        cbVinylFlooring.setClickable(false);

        cbLaminateFlooring.setClickable(false);
        cbLinoleumFlooring.setClickable(false);
        cbNoFlooring.setClickable(false);
        cbUnderConstruction.setClickable(false);
        cbAnyOtherType.setClickable(false);
        cbNoInfoAvaill.setClickable(false);
        cbIntSimplePlastered.setClickable(false);
        cbIntBrickWalls.setClickable(false);

        cbIntDesignerTextured.setClickable(false);
        cbIntPopPunning.setClickable(false);
        cbIntCovedRoof.setClickable(false);
        cbIntVeneerLaminated.setClickable(false);
        cbIntSteelLaminated.setClickable(false);
        cbIntPoorDepleted.setClickable(false);

        cbItBrickTileCladding.setClickable(false);
        cbPrismGlassPortionn.setClickable(false);
        cbGlassPartitions.setClickable(false);
        cbDesignerFalseCeiling.setClickable(false);
        cbWoodVeneerLaminatedWalls.setClickable(false);
        cbDesignerCovedRoof.setClickable(false);
        cbNeatlyPlasteredAndPuttyCoatedWalls.setClickable(false);
        cbArchitecturallyDesignedOrEevated.setClickable(false);
        cbIntUnderConstruction.setClickable(false);
        cbIntUnderFinishing.setClickable(false);
        cbNInfoIhFinishing.setClickable(false);

        cbExtSimplePlastered.setClickable(false);
        cbExtBrickWalls.setClickable(false);
        cbExtArchitecturally.setClickable(false);
        cbExtBrickTile.setClickable(false);
        cbExtStructuralGlazing.setClickable(false);
        cbExtAluminumComposite.setClickable(false);
        cbExtGlassFacade.setClickable(false);

        cbExtDomb.setClickable(false);
        cbExtPorch.setClickable(false);
        cbExtPoorDepleted.setClickable(false);
        cbExtUnderFinishing.setClickable(false);
        cbExtUnderConstruction.setClickable(false);
        cbJetPump.setClickable(false);
        cbSubmersible.setClickable(false);
        cbJalBoardSupply.setClickable(false);
        cbNoInfoAvailWater.setClickable(false);
        cbMaintenanceIssues.setClickable(false);
        cbFinishingIssues.setClickable(false);
        cbSeepageIssues.setClickable(false);

        cbWaterSupplyIssues.setClickable(false);
        cbElectricalIssues.setClickable(false);
        cbStructuralIssues.setClickable(false);
        cbVisibleCracks.setClickable(false);
        cbRunDownBuilding.setClickable(false);
        cbNoMoreSafe.setClickable(false);
        cbNotAppropriate.setClickable(false);

        cbUnderConstructionDefects.setClickable(false);
        cbCompletelyRunDownBuilding.setClickable(false);
        cbNoDefect.setClickable(false);
        cbUnderConstructionAnyViolation.setClickable(false);
        cbNoInfo.setClickable(false);
        cbConstructionDoneWithoutMap.setClickable(false);
        cbConstructionNotAsPer.setClickable(false);
        cbExtraCovered.setClickable(false);
        cbJoinedAdjacent.setClickable(false);
        cbEncroachedAdjacent.setClickable(false);
        cbNoViolation.setClickable(false);
        cbNoViolence.setClickable(false);
        cbUnderConstructionWater.setClickable(false);


    }

    public void getid(View v) {
//        tv_header = findViewById(R.id.tv_header);
//        rl_casedetail = findViewById(R.id.rl_casedetail);
//        tv_caseheader = findViewById(R.id.tv_caseheader);
//        tv_caseid = findViewById(R.id.tv_caseid);

        //    progressbar = (ProgressBar) findViewById(R.id.Progress);
        pref = new Preferences(mActivity);
        if (pref.get(Constants.page_editable).equals("false")) {
            edit_page = false;
//            Toast.makeText(getActivity(), "Completed Case", Toast.LENGTH_SHORT).show();
        } else {
            edit_page = true;
//            Toast.makeText(getActivity(), "Scheduled Case", Toast.LENGTH_SHORT).show();
        }
        typeSurveyChecker = pref.get("typeSurveyChecker8");
        //   back = findViewById(R.id.rl_back);
        next = v.findViewById(R.id.next);
        tvPrevious = v.findViewById(R.id.tvPrevious);
        //  dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
        //  dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
        llQuesB = v.findViewById(R.id.llQuesB);
        llUptoSlabLevel = v.findViewById(R.id.llUptoSlabLevel);

        //RadioGroup
        rgConstructionStatus = v.findViewById(R.id.rgConstructionStatus);
        rgBuildingType = v.findViewById(R.id.rgBuildingType);
        rgClassOfConstruction = v.findViewById(R.id.rgClassOfConstruction);
        rgAppearanceInternal = v.findViewById(R.id.rgAppearanceInternals);
        rgAppearanceExternal = v.findViewById(R.id.rgAppearanceExternal);
        //rgMaintenanceOfTheBuilding = v.findViewById(R.id.rgMaintenanceOfTheBuilding);
        rgInteriorDecoration = v.findViewById(R.id.rgInteriorDecoration);
        rgKitchen = v.findViewById(R.id.rgKitchens);
        rgClassOfElectricalsOne = v.findViewById(R.id.rgClassOfElectricalsOne);
        rgClassOfElectricalsTwo = v.findViewById(R.id.rgClassOfElectricalsTwo);
        rgClassOfElectricalsThree = v.findViewById(R.id.rgClassOfElectricalsThree);
        rgClassOfSanitarysOne = v.findViewById(R.id.rgClassOfSanitarysOne);
        rgClassOfSanitarysTwo = v.findViewById(R.id.rgClassOfSanitarysTwo);
        rgClassOfSanitarysThree = v.findViewById(R.id.rgClassOfSanitarysThree);
        rgFixedWoodenWork = v.findViewById(R.id.rgFixedWoodenWork);
        rgMaintenanceOfTheBuilding2 = v.findViewById(R.id.rgMaintenanceOfTheBuilding2);

        //EditText
        etTotalNumberOfFloors = v.findViewById(R.id.etTotalNumberOfFloors);
        etFloorOnWhich = v.findViewById(R.id.etFloorOnWhich);

        etTypeOfUnit = v.findViewById(R.id.etTypeOfUnit);
        etNumberOfRooms = v.findViewById(R.id.etNumberOfRooms);
        etCabins = v.findViewById(R.id.etCabins);
        etCubicles = v.findViewById(R.id.etCubicles);
        etFlooringAnyOtherType = v.findViewById(R.id.etFlooringAnyOtherType);

        etRoofHeight = v.findViewById(R.id.etRoofHeight);
        etAgeOfBuilding = v.findViewById(R.id.etAgeOfBuilding);
        etRecentImprovements = v.findViewById(R.id.etRecentImprovements);
        etExtentOfViolation = v.findViewById(R.id.etExtentOfViolation);
        etUptoSlabLevel = v.findViewById(R.id.etUptoSlabLevel);

        //CheckBox
        cbRcc = v.findViewById(R.id.cbRcc);
        cbRbc = v.findViewById(R.id.cbRbc);
        cbGiShed = v.findViewById(R.id.cbGiShed);
        cbTinShed = v.findViewById(R.id.cbTinShed);
        cbStonePatla = v.findViewById(R.id.cbStonePatla);
        cbSimplePlaster = v.findViewById(R.id.cbSimplePlaster);
        cbPopPunning = v.findViewById(R.id.cbPopPunning);
        cbPopFalseCeiling = v.findViewById(R.id.cbPopFalseCeiling);
        cbCovedRoof = v.findViewById(R.id.cbCovedRoof);
        cbNoPlaster = v.findViewById(R.id.cbNoPlaster);
        cbDomedRoof = v.findViewById(R.id.cbDomedRoof);
        cbBeautifulCarving = v.findViewById(R.id.cbBeautifulCarving);
        cbNoInfoAvailable = v.findViewById(R.id.cbNoInfoAvailable);
        cbUnderConstructionFinish = v.findViewById(R.id.cbUnderConstructionFinish);
        cbMudFlooring = v.findViewById(R.id.cbMudFlooring);
        cbBrickFlooring = v.findViewById(R.id.cbBrickFlooring);
        cbVitrifiedTiles = v.findViewById(R.id.cbVitrifiedTiles);
        cbCeramicTiles = v.findViewById(R.id.cbCeramicTiles);
        cbSimpleMarble = v.findViewById(R.id.cbSimpleMarble);
        cbMarbleChips = v.findViewById(R.id.cbMarbleChips);
        cbMosaic = v.findViewById(R.id.cbMosaic);
        cbGranite = v.findViewById(R.id.cbGranite);
        cbItalianMarble = v.findViewById(R.id.cbItalianMarble);
        cbKotaStone = v.findViewById(R.id.cbKotaStone);
        cbWooden = v.findViewById(R.id.cbWooden);
        cbPcc = v.findViewById(R.id.cbPcc);
        cbImportedMarble = v.findViewById(R.id.cbImportedMarble);
        cbPavers = v.findViewById(R.id.cbPavers);
        cbChequered = v.findViewById(R.id.cbChequered);
        cbBrickTiles = v.findViewById(R.id.cbBrickTiles);
        cbVinylFlooring = v.findViewById(R.id.cbVinylFlooring);
        cbLaminateFlooring = v.findViewById(R.id.cbLaminateFlooring);
        cbLinoleumFlooring = v.findViewById(R.id.cbLinoleumFlooring);
        cbNoFlooring = v.findViewById(R.id.cbNoFlooring);
        cbUnderConstruction = v.findViewById(R.id.cbUnderConstruction);
        cbAnyOtherType = v.findViewById(R.id.cbAnyOtherType);
        cbNoInfoAvaill = v.findViewById(R.id.cbNoInfoAvaill);
        cbIntSimplePlastered = v.findViewById(R.id.cbIntSimplePlastered);
        cbIntBrickWalls = v.findViewById(R.id.cbIntBrickWalls);
        cbIntDesignerTextured = v.findViewById(R.id.cbIntDesignerTextured);
        cbIntPopPunning = v.findViewById(R.id.cbIntPopPunning);
        cbIntCovedRoof = v.findViewById(R.id.cbIntCovedRoof);
        cbIntVeneerLaminated = v.findViewById(R.id.cbIntVeneerLaminated);
        cbIntSteelLaminated = v.findViewById(R.id.cbIntSteelLaminated);
        cbIntPoorDepleted = v.findViewById(R.id.cbIntPoorDepleted);
//        cbDesignerTexturedWalls = v.findViewById(R.id.cbDesignerTexturedWalls);
//        cbBrickWallsWithoutPlaster = v.findViewById(R.id.cbBrickWallsWithoutPlaster);
        cbItBrickTileCladding = v.findViewById(R.id.cbItBrickTileCladding);
        cbPrismGlassPortionn = v.findViewById(R.id.cbPrismGlassPortionn);
        cbGlassPartitions = v.findViewById(R.id.cbGlassPartitions);
        cbDesignerFalseCeiling = v.findViewById(R.id.cbDesignerFalseCeiling);
        cbWoodVeneerLaminatedWalls = v.findViewById(R.id.cbWoodVeneerLaminatedWalls);
        cbDesignerCovedRoof = v.findViewById(R.id.cbDesignerCovedRoof);
        cbNeatlyPlasteredAndPuttyCoatedWalls = v.findViewById(R.id.cbNeatlyPlasteredAndPuttyCoatedWalls);
        cbArchitecturallyDesignedOrEevated = v.findViewById(R.id.cbArchitecturallyDesignedOrEevated);

        cbIntUnderConstruction = v.findViewById(R.id.cbIntUnderConstruction);
        cbIntUnderFinishing = v.findViewById(R.id.cbIntUnderFinishing);
        // cbIntNoSurvey = v.findViewById(R.id.cbIntNoSurvey);
        cbNInfoIhFinishing = v.findViewById(R.id.cbNInfoIhFinishing);
        cbExtSimplePlastered = v.findViewById(R.id.cbExtSimplePlastered);
        cbExtBrickWalls = v.findViewById(R.id.cbExtBrickWalls);
        cbExtArchitecturally = v.findViewById(R.id.cbExtArchitecturally);
        cbExtBrickTile = v.findViewById(R.id.cbExtBrickTile);
        cbExtStructuralGlazing = v.findViewById(R.id.cbExtStructuralGlazing);
        cbExtAluminumComposite = v.findViewById(R.id.cbExtAluminumComposite);
        cbExtGlassFacade = v.findViewById(R.id.cbExtGlassFacade);
        cbExtDomb = v.findViewById(R.id.cbExtDomb);
        cbExtPorch = v.findViewById(R.id.cbExtPorch);
        cbExtPoorDepleted = v.findViewById(R.id.cbExtPoorDepleted);
        cbExtUnderFinishing = v.findViewById(R.id.cbExtUnderFinishing);
        cbExtUnderConstruction = v.findViewById(R.id.cbExtUnderConstruction);
        cbJetPump = v.findViewById(R.id.cbJetPump);
        cbSubmersible = v.findViewById(R.id.cbSubmersible);
        cbJalBoardSupply = v.findViewById(R.id.cbJalBoardSupply);
        cbNoInfoAvailWater = v.findViewById(R.id.cbNoInfoAvailWater);
        cbMaintenanceIssues = v.findViewById(R.id.cbMaintenanceIssues);
        cbFinishingIssues = v.findViewById(R.id.cbFinishingIssues);
        cbSeepageIssues = v.findViewById(R.id.cbSeepageIssues);
        cbWaterSupplyIssues = v.findViewById(R.id.cbWaterSupplyIssues);
        cbElectricalIssues = v.findViewById(R.id.cbElectricalIssues);
        cbStructuralIssues = v.findViewById(R.id.cbStructuralIssues);
        cbVisibleCracks = v.findViewById(R.id.cbVisibleCracks);
        cbRunDownBuilding = v.findViewById(R.id.cbRunDownBuilding);
        cbNoMoreSafe = v.findViewById(R.id.cbNoMoreSafe);
        cbNotAppropriate = v.findViewById(R.id.cbNotAppropriate);
        cbUnderConstructionDefects = v.findViewById(R.id.cbUnderConstructionDefects);
        cbCompletelyRunDownBuilding = v.findViewById(R.id.cbCompletelyRunDownBuilding);
        cbNoDefect = v.findViewById(R.id.cbNoDefect);
        cbNoInfo = v.findViewById(R.id.cbNoInfo);
        cbConstructionDoneWithoutMap = v.findViewById(R.id.cbConstructionDoneWithoutMap);
        cbConstructionNotAsPer = v.findViewById(R.id.cbConstructionNotAsPer);
        cbExtraCovered = v.findViewById(R.id.cbExtraCovered);
        cbJoinedAdjacent = v.findViewById(R.id.cbJoinedAdjacent);
        cbEncroachedAdjacent = v.findViewById(R.id.cbEncroachedAdjacent);
        cbNoViolation = v.findViewById(R.id.cbNoViolation);
        cbUnderConstructionAnyViolation = v.findViewById(R.id.cbUnderConstructionAnyViolation);
        cbNoViolence = v.findViewById(R.id.cbNoViolence);
        cbUnderConstructionWater = v.findViewById(R.id.cbUnderConstructionWater);

        spinnerInCaseOfUnderConstruction = v.findViewById(R.id.spinnerInCaseOfUnderConstruction);
        spinnerHeightFtMtr = v.findViewById(R.id.spinnerHeightFtMtr);

        tvQuesC = v.findViewById(R.id.tvQuesC);
        tvQuesD = v.findViewById(R.id.tvQuesD);
        tvQuesE = v.findViewById(R.id.tvQuesE);
        tvQuesF = v.findViewById(R.id.tvQuesF);
        tvQuesG = v.findViewById(R.id.tvQuesG);
        tvQuesH = v.findViewById(R.id.tvQuesH);
        tvQuesI = v.findViewById(R.id.tvQuesI);
        tvQuesJ = v.findViewById(R.id.tvQuesJ);
        //   tvQuesK= v.findViewById(R.id.tvQuesK);
        tvQuesL = v.findViewById(R.id.tvQuesL);
        tvQuesM = v.findViewById(R.id.tvQuesM);
        tvQuesN = v.findViewById(R.id.tvQuesN);
        tvQuesO = v.findViewById(R.id.tvQuesO);
        tvQuesP = v.findViewById(R.id.tvQuesP);
        tvQuesQ = v.findViewById(R.id.tvQuesQ);
        tvQuesR = v.findViewById(R.id.tvQuesR);
        tvQuesS = v.findViewById(R.id.tvQuesS);
        tvQuesT = v.findViewById(R.id.tvQuesT);
        tvQuesU = v.findViewById(R.id.tvQuesU);
        tvQuesV = v.findViewById(R.id.tvQuesV);
        tvQuesW = v.findViewById(R.id.tvQuesW);
        tvQuesX = v.findViewById(R.id.tvQuesX);


    }

    public void setListener() {
        //   back.setOnClickListener(this);
        next.setOnClickListener(this);
        tvPrevious.setOnClickListener(this);
        //  dots.setOnClickListener(this);
        cbAnyOtherType.setOnCheckedChangeListener(this);

        rgConstructionStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                RadioButton radioButton = (RadioButton) v.findViewById(i);
                int pos = radioGroup.indexOfChild(radioButton);

                if (radioButton.isChecked()) {
                    if (pos == 1) {
                        llQuesB.setVisibility(View.VISIBLE);

                        tvQuesC.setText(R.string.c_total_number_of_floors_in_the_building_);
                        tvQuesD.setText(R.string.d_floor_on_which_);
                        tvQuesE.setText(R.string.e_type_of_unit_n_);
                        tvQuesF.setText(R.string.f_building_type_);
                        tvQuesG.setText(R.string.g_class_of_construction_);
                        tvQuesH.setText(R.string.h_roof_);
                        tvQuesI.setText(R.string.i_flooring_);
                        tvQuesJ.setText(R.string.j_appearance_condition_of_the_building_);
                        // tvQuesK.setText(R.string.k_maintenance_of_the_building_);
                        tvQuesL.setText(R.string.l_interior_decoration_);
                        tvQuesM.setText(R.string.m_interior_finishing_);
                        tvQuesN.setText(R.string.n_exterior_finishing_);
                        tvQuesO.setText(R.string.o_kitchen_pantry_);
                        tvQuesP.setText(R.string.p_class_of_electrical_fittings_);
                        tvQuesQ.setText(R.string.q_class_of_sanitary_plumbing_amp_water_supply_fittings_);
                        tvQuesR.setText(R.string.r_water_arrangements_);
                        tvQuesS.setText(R.string.s_fixed_wooden_work_);
                        tvQuesT.setText(R.string.t_age_of_building_recent_improvements_renovations_done_);
                        tvQuesU.setText(R.string.u_maintenance_of_the_building_);
                        tvQuesV.setText(R.string.v_any_defects_in_the_building_);
                        tvQuesW.setText(R.string.w_any_violation_done_in_the_property_);
                        tvQuesX.setText(R.string.x_extent_of_violation_);

                        cbUnderConstruction.setChecked(true);
                        ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(true);
                        ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(true);
                        ((RadioButton) rgInteriorDecoration.getChildAt(12)).setChecked(true);
                        ((RadioButton) rgClassOfElectricalsThree.getChildAt(1)).setChecked(true);
                        ((RadioButton) rgClassOfSanitarysThree.getChildAt(1)).setChecked(true);


                        cbIntUnderConstruction.setChecked(true);
                        cbExtUnderConstruction.setChecked(true);
                        ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
//                        ((RadioButton) rgClassOfElectricalThree.getChildAt(1)).setChecked(true);
//                        ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setChecked(true);
                        cbUnderConstructionWater.setChecked(true);
                        ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(true);
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(true);
                        cbUnderConstructionDefects.setChecked(true);
                        cbUnderConstructionAnyViolation.setChecked(true);
                        cbUnderConstructionFinish.setChecked(true);


                        /////////
                        cbNoInfoAvailable.setChecked(false);
                        cbNoInfoAvaill.setChecked(false);

//                        ((RadioButton) rgAppearanceInternal.getChildAt(7)).setChecked(true);
//                        ((RadioButton) rgAppearanceExternal.getChildAt(7)).setChecked(true);
//                        ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);

                        cbNInfoIhFinishing.setChecked(false);
//                        ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);
//                        ((RadioButton) rgClassOfElectricalThree.getChildAt(2)).setChecked(true);
//                        ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);

                        cbNoInfoAvailWater.setChecked(false);
//                        ((RadioButton) rgFixedWoodenWork.getChildAt(9)).setChecked(true);
//                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(6)).setChecked(true);
                        cbNoInfo.setChecked(false);
                        cbNoViolence.setChecked(false);

                        ////

//                        rgInteriorDecoration.getChildAt(15).setSelected(true);


//                        rgClassOfElectricalTwo.clearCheck();

                       /* for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
                        }*/
//
//                        for (int k = 0; k < rgClassOfElectricalOne.getChildCount(); k++) {
//                            rgClassOfElectricalOne.getChildAt(k).setEnabled(false);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalTwo.getChildCount(); k++) {
//                            rgClassOfElectricalTwo.getChildAt(k).setEnabled(false);
//                        }
//
//
//                        rgClassOfSanitaryOne.clearCheck();
//                        rgClassOfSanitaryTwo.clearCheck();
//
//                       /* for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
//                        }*/
//
//                        for (int k = 0; k < rgClassOfSanitaryOne.getChildCount(); k++) {
//                            rgClassOfSanitaryOne.getChildAt(k).setEnabled(false);
//                        }
//
//                        for (int k = 0; k < rgClassOfSanitaryTwo.getChildCount(); k++) {
//                            rgClassOfSanitaryTwo.getChildAt(k).setEnabled(false);
//                        }


                    } else {
                        llQuesB.setVisibility(View.GONE);

                        cbUnderConstruction.setChecked(false);
                        ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(false);
                        ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(false);
//                        ((RadioButton) rgInteriorDecoration.getChildAt(8)).setChecked(false);
                        cbIntUnderConstruction.setChecked(false);
                        cbExtUnderConstruction.setChecked(false);
                        ((RadioButton) rgKitchen.getChildAt(4)).setChecked(false);

//                        ((RadioButton) rgClassOfElectricalThree.getChildAt(1)).setChecked(false);
//                        ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setChecked(false);
                        cbUnderConstructionWater.setChecked(false);
                        ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(false);
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(false);
                        cbUnderConstructionDefects.setChecked(false);
                        cbUnderConstructionAnyViolation.setChecked(false);
                        cbUnderConstructionFinish.setChecked(false);
//
//                        rgClassOfElectricalThree.clearCheck();
//                        rgClassOfElectricalOne.clearCheck();
//                        rgClassOfElectricalTwo.clearCheck();
//
//                        for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalOne.getChildCount(); k++) {
//                            rgClassOfElectricalOne.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalTwo.getChildCount(); k++) {
//                            rgClassOfElectricalTwo.getChildAt(k).setEnabled(true);
//                        }
//

//                        rgClassOfSanitaryThree.clearCheck();
//                        rgClassOfSanitaryOne.clearCheck();
//                        rgClassOfSanitaryTwo.clearCheck();
//
//                        for (int k = 0; k < rgClassOfSanitaryThree.getChildCount(); k++) {
//                            rgClassOfSanitaryThree.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfSanitaryOne.getChildCount(); k++) {
//                            rgClassOfSanitaryOne.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfSanitaryTwo.getChildCount(); k++) {
//                            rgClassOfSanitaryTwo.getChildAt(k).setEnabled(true);
//                        }


                        tvQuesC.setText(R.string.c_total_number_of_floors_in_the_building);
                        tvQuesD.setText(R.string.d_floor_on_which);
                        tvQuesE.setText(R.string.e_type_of_unit_n);
                        tvQuesF.setText(R.string.f_building_type);
                        tvQuesG.setText(R.string.g_class_of_construction);
                        tvQuesH.setText(R.string.h_roof);
                        tvQuesI.setText(R.string.i_flooring);
                        tvQuesJ.setText(R.string.j_appearance_condition_of_the_building);
                        // tvQuesK.setText(R.string.k_maintenance_of_the_building);
                        tvQuesL.setText(R.string.l_interior_decoration);
                        tvQuesM.setText(R.string.m_interior_finishing);
                        tvQuesN.setText(R.string.n_exterior_finishing);
                        tvQuesO.setText(R.string.o_kitchen_pantry);
                        tvQuesP.setText(R.string.p_class_of_electrical_fittings);
                        tvQuesQ.setText(R.string.q_class_of_sanitary_plumbing_amp_water_supply_fittings);
                        tvQuesR.setText(R.string.r_water_arrangements);
                        tvQuesS.setText(R.string.s_fixed_wooden_work);
                        tvQuesT.setText(R.string.t_age_of_building_recent_improvements_renovations_done);
                        tvQuesU.setText(R.string.u_maintenance_of_the_building);
                        tvQuesV.setText(R.string.v_any_defects_in_the_building);
                        tvQuesW.setText(R.string.w_any_violation_done_in_the_property);
                        tvQuesX.setText(R.string.x_extent_of_violation);




                    }
                }

            }
        });


        rgClassOfElectricalsOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rgClassOfElectricalsTwo.getCheckedRadioButtonId() != -1 && rgClassOfElectricalsOne.getCheckedRadioButtonId() != -1) {

                    rgClassOfElectricalsThree.clearCheck();

                    for (int k = 0; k < rgClassOfElectricalsThree.getChildCount(); k++) {
                        rgClassOfElectricalsThree.getChildAt(k).setEnabled(false);
                    }
                }

            }
        });

        rgClassOfElectricalsTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rgClassOfElectricalsOne.getCheckedRadioButtonId() != -1 && rgClassOfElectricalsTwo.getCheckedRadioButtonId() != -1) {

                    rgClassOfElectricalsThree.clearCheck();

                    for (int k = 0; k < rgClassOfElectricalsThree.getChildCount(); k++) {
                        rgClassOfElectricalsThree.getChildAt(k).setEnabled(false);
                    }

                }

            }
        });

        rgClassOfElectricalsThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rgClassOfElectricalsThree.getCheckedRadioButtonId() != -1) {

                    rgClassOfElectricalsOne.clearCheck();
                    rgClassOfElectricalsTwo.clearCheck();

                    for (int k = 0; k < rgClassOfElectricalsOne.getChildCount(); k++) {
                        rgClassOfElectricalsOne.getChildAt(k).setEnabled(false);
                    }

                    for (int k = 0; k < rgClassOfElectricalsTwo.getChildCount(); k++) {
                        rgClassOfElectricalsTwo.getChildAt(k).setEnabled(false);
                    }

                }


            }
        });

        rgClassOfSanitarysOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rgClassOfSanitarysTwo.getCheckedRadioButtonId() != -1 && rgClassOfSanitarysOne.getCheckedRadioButtonId() != -1) {

                    rgClassOfSanitarysThree.clearCheck();

                    for (int k = 0; k < rgClassOfSanitarysThree.getChildCount(); k++) {
                        rgClassOfSanitarysThree.getChildAt(k).setEnabled(false);
                    }
                }

            }
        });

        rgClassOfSanitarysTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                if (rgClassOfSanitarysOne.getCheckedRadioButtonId() != -1 && rgClassOfSanitarysTwo.getCheckedRadioButtonId() != -1) {

                    rgClassOfSanitarysThree.clearCheck();

                    for (int k = 0; k < rgClassOfSanitarysThree.getChildCount(); k++) {
                        rgClassOfSanitarysThree.getChildAt(k).setEnabled(false);
                    }

                }

            }
        });

        rgClassOfSanitarysThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (rgClassOfSanitarysThree.getCheckedRadioButtonId() != -1) {

                    rgClassOfSanitarysOne.clearCheck();
                    rgClassOfSanitarysTwo.clearCheck();

                    for (int k = 0; k < rgClassOfSanitarysOne.getChildCount(); k++) {
                        rgClassOfSanitarysOne.getChildAt(k).setEnabled(false);
                    }

                    for (int k = 0; k < rgClassOfSanitarysTwo.getChildCount(); k++) {
                        rgClassOfSanitarysTwo.getChildAt(k).setEnabled(false);
                    }

                }


            }
        });


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.cbAnyOtherType:
                if (isChecked) {
                    etFlooringAnyOtherType.setVisibility(View.VISIBLE);
                } else {
                    etFlooringAnyOtherType.setVisibility(View.GONE);
                }
                break;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
       /*     case R.id.rl_back:
                onBackPressed();
                break;*/

            case R.id.tvPrevious:
               /* intent = new Intent(GeneralForm8.this, GeneralForm7.class);
                startActivity(intent);*/
                // ((Dashboard)mActivity).displayView(12);
                mActivity.onBackPressed();
                break;

            case R.id.next:
                if (edit_page)
                    validation();
                else
                    ((Dashboard) mActivity).displayView(14);


                pref.set("typeSurveyChecker8", "false");
                pref.commit();
                break;

          /*  case R.id.rl_dots:
                if (dropdown.getVisibility() == View.GONE) {
                    showPopup(view);

                } else {

                }
                break;*/
        }
    }


    private void validation() {
        if (rgConstructionStatus.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Construction Status", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvConstruction);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llQuesB.getVisibility() == View.VISIBLE && spinnerInCaseOfUnderConstruction.getSelectedItemPosition() == 0) {
            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvInCaseOf);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (llUptoSlabLevel.getVisibility() == View.VISIBLE && etUptoSlabLevel.getText().toString().isEmpty()) {

            Snackbar snackbar = Snackbar.make(next, "Please Enter Upto Slab Level", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etUptoSlabLevel.requestFocus();
        } else if (etTotalNumberOfFloors.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Total Number of Floors in the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTotalNumberOfFloors.requestFocus();
        } else if (llUptoSlabLevel.getVisibility() == View.VISIBLE &&
                Integer.parseInt(etTotalNumberOfFloors.getText().toString()) < Integer.parseInt(etUptoSlabLevel.getText().toString())) {

            //    if (Integer.parseInt(etTotalNumberOfFloors.getText().toString())<Integer.parseInt(etUptoSlabLevel.getText().toString()))
            {
                etTotalNumberOfFloors.setError("Total number of floors in the building cannot be less than the value filled in Upto slab level above");
                // Toast.makeText(this, "Total number of floors in the building cannot be less than the value filled in Upto slab level above", Toast.LENGTH_SHORT).show();
                etTotalNumberOfFloors.requestFocus();
            }
        } else if (etFloorOnWhich.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Floors on which subject property is situated", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etFloorOnWhich.requestFocus();
        } else if (Integer.parseInt(etFloorOnWhich.getText().toString()) > Integer.parseInt(etTotalNumberOfFloors.getText().toString())) {
            etFloorOnWhich.setError("Floor on which subject property is situated cannot be greater than the value filled in total number of floors in the building above");
            // Toast.makeText(this, "Total number of floors in the building cannot be less than the value filled in Upto slab level above", Toast.LENGTH_SHORT).show();
            etFloorOnWhich.requestFocus();
        } else if (etTypeOfUnit.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Type of Unit", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etTypeOfUnit.requestFocus();
        } else if (etNumberOfRooms.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Number of Rooms", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etNumberOfRooms.requestFocus();
        } else if (etCabins.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Cabins", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCabins.requestFocus();
        } else if (etCubicles.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Cubicles", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etCubicles.requestFocus();
        } else if (rgBuildingType.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Building Type", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesF);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgClassOfConstruction.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Construction", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesG);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbRcc.isChecked()
                && !cbRbc.isChecked()
                && !cbGiShed.isChecked()
                && !cbTinShed.isChecked()
                && !cbStonePatla.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Make", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvRoofMake);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (etRoofHeight.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Roof Height", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRoofHeight.requestFocus();
        } else if (!cbSimplePlaster.isChecked()
                && !cbPopPunning.isChecked()
                && !cbPopFalseCeiling.isChecked()
                && !cbCovedRoof.isChecked()
                && !cbNoPlaster.isChecked()
                && !cbDomedRoof.isChecked()
                && !cbBeautifulCarving.isChecked()
                && !cbUnderConstructionFinish.isChecked()
                && !cbNoInfoAvailable.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Finish", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvFinish);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbMudFlooring.isChecked()
                && !cbBrickFlooring.isChecked()
                && !cbVitrifiedTiles.isChecked()
                && !cbCeramicTiles.isChecked()
                && !cbSimpleMarble.isChecked()
                && !cbMarbleChips.isChecked()
                && !cbMosaic.isChecked()
                && !cbGranite.isChecked()
                && !cbItalianMarble.isChecked()
                && !cbKotaStone.isChecked()
                && !cbWooden.isChecked()
                && !cbPcc.isChecked()
                && !cbImportedMarble.isChecked()
                && !cbPavers.isChecked()
                && !cbChequered.isChecked()
                && !cbBrickTiles.isChecked()
                && !cbVinylFlooring.isChecked()
                && !cbLaminateFlooring.isChecked()
                && !cbLinoleumFlooring.isChecked()
                && !cbNoFlooring.isChecked()
                && !cbUnderConstruction.isChecked()
                && !cbAnyOtherType.isChecked()
                && !cbNoInfoAvaill.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Flooring", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesI);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgAppearanceInternal.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance Internal", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvAppInt);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgAppearanceExternal.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance External", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvAppExt);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } /*else if (rgMaintenanceOfTheBuilding.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesK);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } */ else if (rgInteriorDecoration.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Decoration", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesL);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbIntSimplePlastered.isChecked()
                && !cbIntBrickWalls.isChecked()
                && !cbIntDesignerTextured.isChecked()
                && !cbIntPopPunning.isChecked()
                && !cbIntCovedRoof.isChecked()
                && !cbIntVeneerLaminated.isChecked()
                && !cbIntSteelLaminated.isChecked()
                && !cbIntUnderConstruction.isChecked()
                && !cbIntPoorDepleted.isChecked()
//                && !cbBrickWallsWithoutPlaster.isChecked()
                && !cbIntUnderFinishing.isChecked()
                && !cbItBrickTileCladding.isChecked()
                && !cbPrismGlassPortionn.isChecked()
                && !cbGlassPartitions.isChecked()
                && !cbDesignerFalseCeiling.isChecked()
                && !cbWoodVeneerLaminatedWalls.isChecked()
                && !cbDesignerCovedRoof.isChecked()
//                && !cbPOPPunning.isChecked()
//                && !cbDesignerTexturedWalls.isChecked()
                && !cbNeatlyPlasteredAndPuttyCoatedWalls.isChecked()
                && !cbArchitecturallyDesignedOrEevated.isChecked()
//                && !cbSimplePlasteredWalls.isChecked()
                && !cbNInfoIhFinishing.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesM);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbExtSimplePlastered.isChecked()
                && !cbExtBrickWalls.isChecked()
                && !cbExtArchitecturally.isChecked()
                && !cbExtBrickTile.isChecked()
                && !cbExtStructuralGlazing.isChecked()
                && !cbExtAluminumComposite.isChecked()
                && !cbExtGlassFacade.isChecked()
                && !cbExtDomb.isChecked()
                && !cbExtPorch.isChecked()
                && !cbExtPoorDepleted.isChecked()
                && !cbExtUnderFinishing.isChecked()
                && !cbExtUnderConstruction.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesN);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgKitchen.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Kitchen/ Pantry", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesO);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgClassOfElectricalsOne.getCheckedRadioButtonId() == -1 && rgClassOfElectricalsThree.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.rgClassOfElectricalsOne);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } else if (rgClassOfElectricalsTwo.getCheckedRadioButtonId() == -1 && rgClassOfElectricalsThree.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.rgClassOfElectricalsTwo);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } else if (rgClassOfElectricalsThree.getCheckedRadioButtonId() == -1 &&
                rgClassOfElectricalsTwo.getCheckedRadioButtonId() == -1 &&
                rgClassOfElectricalsOne.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.rgClassOfElectricalsTwo);
            targetView.getParent().requestChildFocus(targetView, targetView);
        } else if ((rgClassOfSanitarysOne.getCheckedRadioButtonId() == -1
                || rgClassOfSanitarysTwo.getCheckedRadioButtonId() == -1)
                && rgClassOfSanitarysThree.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary Section 1 and Section 2", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesQ);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgClassOfSanitarysThree.getCheckedRadioButtonId() == -1 &&
                (rgClassOfSanitarysOne.getCheckedRadioButtonId() == -1
                        || rgClassOfSanitarysTwo.getCheckedRadioButtonId() == -1)) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary Section 3", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesQ);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbJetPump.isChecked()
                && !cbSubmersible.isChecked()
                && !cbJalBoardSupply.isChecked()
                && !cbNoInfoAvailWater.isChecked()
                && !cbUnderConstructionWater.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesR);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (rgFixedWoodenWork.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesS);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (etAgeOfBuilding.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Age of Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etAgeOfBuilding.requestFocus();
        } else if (etRecentImprovements.getText().toString().isEmpty()) {
            Snackbar snackbar = Snackbar.make(next, "Please Enter Recent Improvements/ Renovations done", Snackbar.LENGTH_SHORT);
            snackbar.show();
            etRecentImprovements.requestFocus();
        } else if (rgMaintenanceOfTheBuilding2.getCheckedRadioButtonId() == -1) {
            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesU);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbMaintenanceIssues.isChecked()
                && !cbFinishingIssues.isChecked()
                && !cbSeepageIssues.isChecked()
                && !cbWaterSupplyIssues.isChecked()
                && !cbElectricalIssues.isChecked()
                && !cbStructuralIssues.isChecked()
                && !cbVisibleCracks.isChecked()
                && !cbRunDownBuilding.isChecked()
                && !cbNoMoreSafe.isChecked()
                && !cbCompletelyRunDownBuilding.isChecked()
                && !cbNotAppropriate.isChecked()
                && !cbUnderConstructionDefects.isChecked()
                && !cbNoDefect.isChecked()
                && !cbNoInfo.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Any defects in the building", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesV);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else if (!cbConstructionDoneWithoutMap.isChecked()
                && !cbConstructionNotAsPer.isChecked()
                && !cbExtraCovered.isChecked()
                && !cbJoinedAdjacent.isChecked()
                && !cbEncroachedAdjacent.isChecked()
                && !cbUnderConstructionAnyViolation.isChecked()
                && !cbNoViolation.isChecked()
                && !cbNoViolence.isChecked()) {
            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar.make(next, "Please Select Any Violation done in the property", Snackbar.LENGTH_SHORT);
            snackbar.show();
            View targetView = v.findViewById(R.id.tvQuesW);
            targetView.getParent().requestChildFocus(targetView, targetView);

        } else {
            putIntoHm();
            ((Dashboard) mActivity).displayView(14);
            return;
        }
    }

    public void putIntoHm() {

        String inCaseOfUnderConstruction = String.valueOf(spinnerInCaseOfUnderConstruction.getSelectedItemPosition());
        hm.put("inCaseOfUnderConstructionSpinner", inCaseOfUnderConstruction);
        hm.put("totalNoOfFloors", etTotalNumberOfFloors.getText().toString());
        hm.put("floorOnWhich", etFloorOnWhich.getText().toString());

        hm.put("typeOfUnitt", etTypeOfUnit.getText().toString());
        hm.put("numberOfRooms", etNumberOfRooms.getText().toString());
        hm.put("cabins", etCabins.getText().toString());
        hm.put("cubicles", etCubicles.getText().toString());

        hm.put("roofHeight", etRoofHeight.getText().toString());
        hm.put("ageOfBuildinggg", etAgeOfBuilding.getText().toString());
        hm.put("recentImprovements", etRecentImprovements.getText().toString());
        hm.put("extentOfViolation", etExtentOfViolation.getText().toString());
        if (llUptoSlabLevel.getVisibility() == View.VISIBLE) {
            hm.put("uptoSlabLevel", etUptoSlabLevel.getText().toString());
        } else {
            hm.put("uptoSlabLevel", "");
        }


        if (cbRcc.isChecked()) {
            cbRccCheck = 1;
            hm.put("cbRcc", String.valueOf(cbRccCheck));
        } else {
            cbRccCheck = 0;
            hm.put("cbRcc", String.valueOf(cbRccCheck));
        }
        if (cbRbc.isChecked()) {
            cbRbcCheck = 1;
            hm.put("cbRbc", String.valueOf(cbRbcCheck));
        } else {
            cbRbcCheck = 0;
            hm.put("cbRbc", String.valueOf(cbRbcCheck));
        }
        if (cbGiShed.isChecked()) {
            cbGiShedCheck = 1;
            hm.put("cbGiShed", String.valueOf(cbGiShedCheck));
        } else {
            cbGiShedCheck = 0;
            hm.put("cbGiShed", String.valueOf(cbGiShedCheck));
        }
        if (cbTinShed.isChecked()) {
            cbTinShedCheck = 1;
            hm.put("cbTinShed", String.valueOf(cbTinShedCheck));
        } else {
            cbTinShedCheck = 0;
            hm.put("cbTinShed", String.valueOf(cbTinShedCheck));
        }
        if (cbStonePatla.isChecked()) {
            cbStonePatlaCheck = 1;
            hm.put("cbStonePatla", String.valueOf(cbStonePatlaCheck));
        } else {
            cbStonePatlaCheck = 0;
            hm.put("cbStonePatla", String.valueOf(cbStonePatlaCheck));
        }


        if (cbSimplePlaster.isChecked()) {
            cbSimplePlasterCheck = 1;
            hm.put("cbSimplePlaster", String.valueOf(cbSimplePlasterCheck));
        } else {
            cbSimplePlasterCheck = 0;
            hm.put("cbSimplePlaster", String.valueOf(cbSimplePlasterCheck));
        }
        if (cbPopPunning.isChecked()) {
            cbPopPunningCheck = 1;
            hm.put("cbPopPunning", String.valueOf(cbPopPunningCheck));
        } else {
            cbPopPunningCheck = 0;
            hm.put("cbPopPunning", String.valueOf(cbPopPunningCheck));
        }
        if (cbPopFalseCeiling.isChecked()) {
            cbPopFalseCeilingCheck = 1;
            hm.put("cbPopFalseCeiling", String.valueOf(cbPopFalseCeilingCheck));
        } else {
            cbPopFalseCeilingCheck = 0;
            hm.put("cbPopFalseCeiling", String.valueOf(cbPopFalseCeilingCheck));
        }
        if (cbCovedRoof.isChecked()) {
            cbCovedRoofCheck = 1;
            hm.put("cbCovedRoof", String.valueOf(cbCovedRoofCheck));
        } else {
            cbCovedRoofCheck = 0;
            hm.put("cbCovedRoof", String.valueOf(cbCovedRoofCheck));
        }
        if (cbNoPlaster.isChecked()) {
            cbNoPlasterCheck = 1;
            hm.put("cbNoPlaster", String.valueOf(cbNoPlasterCheck));
        } else {
            cbNoPlasterCheck = 0;
            hm.put("cbNoPlaster", String.valueOf(cbNoPlasterCheck));
        }
        if (cbDomedRoof.isChecked()) {
            cbDomedRoofCheck = 1;
            hm.put("cbDomedRoof", String.valueOf(cbDomedRoofCheck));
        } else {
            cbDomedRoofCheck = 0;
            hm.put("cbDomedRoof", String.valueOf(cbDomedRoofCheck));
        }
        if (cbBeautifulCarving.isChecked()) {
            cbBeautifulCarvingCheck = 1;
            hm.put("cbBeautifulCarving", String.valueOf(cbBeautifulCarvingCheck));
        } else {
            cbBeautifulCarvingCheck = 0;
            hm.put("cbBeautifulCarving", String.valueOf(cbBeautifulCarvingCheck));
        }
        if (cbNoInfoAvailable.isChecked()) {
            cbNoInfoAvailableCheck = 1;
            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
        } else {
            cbNoInfoAvailableCheck = 0;
            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
        }

        if (cbUnderConstructionFinish.isChecked()) {
            cbUnderConstructionFinishCheck = 1;
            hm.put("cbUnderConstructionFinish", String.valueOf(cbUnderConstructionFinishCheck));
        } else {
            cbUnderConstructionFinishCheck = 0;
            hm.put("cbUnderConstructionFinish", String.valueOf(cbUnderConstructionFinishCheck));
        }


        if (cbMudFlooring.isChecked()) {
            cbMudFlooringCheck = 1;
            hm.put("cbMudFlooring", String.valueOf(cbMudFlooringCheck));
        } else {
            cbMudFlooringCheck = 0;
            hm.put("cbMudFlooring", String.valueOf(cbMudFlooringCheck));
        }
        if (cbBrickFlooring.isChecked()) {
            cbBrickFlooringCheck = 1;
            hm.put("cbBrickFlooring", String.valueOf(cbBrickFlooringCheck));
        } else {
            cbBrickFlooringCheck = 0;
            hm.put("cbBrickFlooring", String.valueOf(cbBrickFlooringCheck));
        }
        if (cbVitrifiedTiles.isChecked()) {
            cbVitrifiedTilesCheck = 1;
            hm.put("cbVitrifiedTiles", String.valueOf(cbVitrifiedTilesCheck));
        } else {
            cbVitrifiedTilesCheck = 0;
            hm.put("cbVitrifiedTiles", String.valueOf(cbVitrifiedTilesCheck));
        }
        if (cbCeramicTiles.isChecked()) {
            cbCeramicTilesCheck = 1;
            hm.put("cbCeramicTiles", String.valueOf(cbCeramicTilesCheck));
        } else {
            cbCeramicTilesCheck = 0;
            hm.put("cbCeramicTiles", String.valueOf(cbCeramicTilesCheck));
        }


        if (cbSimpleMarble.isChecked()) {
            cbSimpleMarbleCheck = 1;
            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
        } else {
            cbSimpleMarbleCheck = 0;
            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
        }
        if (cbMarbleChips.isChecked()) {
            cbMarbleChipsCheck = 1;
            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
        } else {
            cbMarbleChipsCheck = 0;
            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
        }
        if (cbMosaic.isChecked()) {
            cbMosaicCheck = 1;
            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
        } else {
            cbMosaicCheck = 0;
            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
        }
        if (cbGranite.isChecked()) {
            cbGraniteCheck = 1;
            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
        } else {
            cbGraniteCheck = 0;
            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
        }
        if (cbItalianMarble.isChecked()) {
            cbItalianMarbleCheck = 1;
            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
        } else {
            cbItalianMarbleCheck = 0;
            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
        }


        if (cbKotaStone.isChecked()) {
            cbKotaStoneCheck = 1;
            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
        } else {
            cbKotaStoneCheck = 0;
            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
        }
        if (cbWooden.isChecked()) {
            cbWoodenCheck = 1;
            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
        } else {
            cbWoodenCheck = 0;
            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
        }

        if (cbPcc.isChecked()) {
            cbPccCheck = 1;
            hm.put("cbPccc", String.valueOf(cbPccCheck));
        } else {
            cbPccCheck = 0;
            hm.put("cbPccc", String.valueOf(cbPccCheck));
        }
        if (cbImportedMarble.isChecked()) {
            cbImportedMarbleCheck = 1;
            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
        } else {
            cbImportedMarbleCheck = 0;
            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
        }
        if (cbPavers.isChecked()) {
            cbPaversCheck = 1;
            hm.put("cbPavers", String.valueOf(cbPaversCheck));
        } else {
            cbPaversCheck = 0;
            hm.put("cbPavers", String.valueOf(cbPaversCheck));
        }
        if (cbChequered.isChecked()) {
            cbChequeredCheck = 1;
            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
        } else {
            cbChequeredCheck = 0;
            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
        }

        if (cbBrickTiles.isChecked()) {
            cbBrickTilesCheck = 1;
            hm.put("cbBrickTiless", String.valueOf(cbBrickTilesCheck));
        } else {
            cbBrickTilesCheck = 0;
            hm.put("cbBrickTiless", String.valueOf(cbBrickTilesCheck));
        }
        if (cbVinylFlooring.isChecked()) {
            cbVinylFlooringCheck = 1;
            hm.put("cbVinylFlooring", String.valueOf(cbVinylFlooringCheck));
        } else {
            cbVinylFlooringCheck = 0;
            hm.put("cbVinylFlooring", String.valueOf(cbVinylFlooringCheck));
        }
        if (cbLaminateFlooring.isChecked()) {
            cbLaminateFlooringCheck = 1;
            hm.put("cbLaminateFlooring", String.valueOf(cbLaminateFlooringCheck));
        } else {
            cbLaminateFlooringCheck = 0;
            hm.put("cbLaminateFlooring", String.valueOf(cbLaminateFlooringCheck));
        }
        if (cbLinoleumFlooring.isChecked()) {
            cbLinoleumFlooringCheck = 1;
            hm.put("cbLinoleumFlooring", String.valueOf(cbLinoleumFlooringCheck));
        } else {
            cbImportedMarbleCheck = 0;
            hm.put("cbLinoleumFlooring", String.valueOf(cbLinoleumFlooringCheck));
        }
        if (cbNoFlooring.isChecked()) {
            cbNoFlooringCheck = 1;
            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
        } else {
            cbNoFlooringCheck = 0;
            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
        }
        if (cbUnderConstruction.isChecked()) {
            cbUnderConstructionCheck = 1;
            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
        } else {
            cbUnderConstructionCheck = 0;
            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
        }
        if (cbAnyOtherType.isChecked()) {
            cbAnyOtherTypeCheck = 1;
            hm.put("cbAnyOtherTypee", String.valueOf(cbAnyOtherTypeCheck));
        } else {
            cbUnderConstructionCheck = 0;
            hm.put("cbAnyOtherTypee", String.valueOf(cbAnyOtherTypeCheck));
        }

        if (cbNoInfoAvaill.isChecked()) {
            cbNoInfoAvaillCheck = 1;
            hm.put("cbNoInfoAvaill", String.valueOf(cbNoInfoAvaillCheck));
        } else {
            cbNoInfoAvaillCheck = 0;
            hm.put("cbNoInfoAvaill", String.valueOf(cbNoInfoAvaillCheck));
        }


        if (cbIntSimplePlastered.isChecked()) {
            cbIntSimplePlasteredCheck = 1;
            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
        } else {
            cbIntSimplePlasteredCheck = 0;
            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
        }

        if (cbIntBrickWalls.isChecked()) {
            cbIntBrickWallsCheck = 1;
            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
        } else {
            cbIntBrickWallsCheck = 0;
            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
        }
        if (cbIntDesignerTextured.isChecked()) {
            cbIntDesignerTexturedCheck = 1;
            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
        } else {
            cbIntDesignerTexturedCheck = 0;
            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
        }
        if (cbIntPopPunning.isChecked()) {
            cbIntPopPunningCheck = 1;
            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
        } else {
            cbIntPopPunningCheck = 0;
            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
        }
        if (cbIntCovedRoof.isChecked()) {
            cbIntCovedRoofCheck = 1;
            hm.put("cbIntCovedRoof", String.valueOf(cbIntCovedRoofCheck));
        } else {
            cbIntCovedRoofCheck = 0;
            hm.put("cbIntCovedRoof", String.valueOf(cbIntCovedRoofCheck));
        }
        if (cbIntVeneerLaminated.isChecked()) {
            cbIntVeneerLaminatedCheck = 1;
            hm.put("cbIntVeneerLaminated", String.valueOf(cbIntVeneerLaminatedCheck));
        } else {
            cbIntVeneerLaminatedCheck = 0;
            hm.put("cbIntVeneerLaminated", String.valueOf(cbIntVeneerLaminatedCheck));
        }
        if (cbIntSteelLaminated.isChecked()) {
            cbIntSteelLaminatedCheck = 1;
            hm.put("cbIntSteelLaminated", String.valueOf(cbIntSteelLaminatedCheck));
        } else {
            cbIntSteelLaminatedCheck = 0;
            hm.put("cbIntSteelLaminated", String.valueOf(cbIntSteelLaminatedCheck));
        }
        if (cbIntPoorDepleted.isChecked()) {
            cbIntPoorDepletedCheck = 1;
            hm.put("cbIntPoorDepleted", String.valueOf(cbIntPoorDepletedCheck));
        } else {
            cbIntPoorDepletedCheck = 0;
            hm.put("cbIntPoorDepleted", String.valueOf(cbIntPoorDepletedCheck));
        }

//        if (cbBrickWallsWithoutPlaster.isChecked()) {
//            cbBrickWallsWithoutPlasterCheck = 1;
//            hm.put("cbBrickWallsWithoutPlaster", String.valueOf(cbBrickWallsWithoutPlasterCheck));
//        } else {
//            cbBrickWallsWithoutPlasterCheck = 0;
//            hm.put("cbBrickWallsWithoutPlaster", String.valueOf(cbBrickWallsWithoutPlasterCheck));
//        }
        if (cbIntUnderFinishing.isChecked()) {
            cbIntUnderFinishingCheck = 1;
            hm.put("cbIntUnderFinishing", String.valueOf(cbIntUnderFinishingCheck));
        } else {
            cbIntUnderFinishingCheck = 0;
            hm.put("cbIntUnderFinishing", String.valueOf(cbIntUnderFinishingCheck));
        }

        if (cbItBrickTileCladding.isChecked()) {
            cbItBrickTileCladdingCheck = 1;
            hm.put("cbItBrickTileCladding", String.valueOf(cbItBrickTileCladdingCheck));
        } else {
            cbItBrickTileCladdingCheck = 0;
            hm.put("cbItBrickTileCladding", String.valueOf(cbItBrickTileCladdingCheck));
        }
        if (cbPrismGlassPortionn.isChecked()) {
            cbPrismGlassPortionnCheck = 1;
            hm.put("cbPrismGlassPortionn", String.valueOf(cbPrismGlassPortionnCheck));
        } else {
            cbPrismGlassPortionnCheck = 0;
            hm.put("cbPrismGlassPortionn", String.valueOf(cbPrismGlassPortionnCheck));
        }
        if (cbGlassPartitions.isChecked()) {
            cbGlassPartitionsCheck = 1;
            hm.put("cbGlassPartitions", String.valueOf(cbGlassPartitionsCheck));
        } else {
            cbGlassPartitionsCheck = 0;
            hm.put("cbGlassPartitions", String.valueOf(cbGlassPartitionsCheck));
        }

        if (cbDesignerFalseCeiling.isChecked()) {
            cbDesignerFalseCeilingCheck = 1;
            hm.put("cbDesignerFalseCeiling", String.valueOf(cbDesignerFalseCeilingCheck));
        } else {
            cbDesignerFalseCeilingCheck = 0;
            hm.put("cbDesignerFalseCeiling", String.valueOf(cbDesignerFalseCeilingCheck));
        }
        if (cbWoodVeneerLaminatedWalls.isChecked()) {
            cbWoodVeneerLaminatedWallsCheck = 1;
            hm.put("cbWoodVeneerLaminatedWalls", String.valueOf(cbWoodVeneerLaminatedWalls));
        } else {
            cbWoodVeneerLaminatedWallsCheck = 0;
            hm.put("cbWoodVeneerLaminatedWalls", String.valueOf(cbWoodVeneerLaminatedWallsCheck));
        }

        if (cbDesignerCovedRoof.isChecked()) {
            cbDesignerCovedRoofCheck = 1;
            hm.put("cbDesignerCovedRoof", String.valueOf(cbDesignerCovedRoofCheck));
        } else {
            cbDesignerCovedRoofCheck = 0;
            hm.put("cbDesignerCovedRoof", String.valueOf(cbDesignerCovedRoofCheck));
        }

//        if (cbPOPPunning.isChecked()) {
//            cbPOPPunningCheck = 1;
//            hm.put("cbPOPPunning", String.valueOf(cbPOPPunningCheck));
//        } else {
//            cbPOPPunningCheck = 0;
//            hm.put("cbPOPPunning", String.valueOf(cbPOPPunningCheck));
//        }
//        if (cbDesignerTexturedWalls.isChecked()) {
//            cbDesignerTexturedWallsCheck = 1;
//            hm.put("cbDesignerTexturedWalls", String.valueOf(cbDesignerTexturedWallsCheck));
//        } else {
//            cbDesignerTexturedWallsCheck = 0;
//            hm.put("cbDesignerTexturedWalls", String.valueOf(cbDesignerTexturedWallsCheck));
//        }

        if (cbNeatlyPlasteredAndPuttyCoatedWalls.isChecked()) {
            cbNeatlyPlasteredAndPuttyCoatedWallsCheck = 1;
            hm.put("cbNeatlyPlasteredAndPuttyCoatedWalls", String.valueOf(cbNeatlyPlasteredAndPuttyCoatedWallsCheck));
        } else {
            cbNeatlyPlasteredAndPuttyCoatedWallsCheck = 0;
            hm.put("cbNeatlyPlasteredAndPuttyCoatedWalls", String.valueOf(cbNeatlyPlasteredAndPuttyCoatedWallsCheck));
        }


        if (cbArchitecturallyDesignedOrEevated.isChecked()) {
            cbArchitecturallyDesignedOrEevatedCheck = 1;
            hm.put("cbArchitecturallyDesignedOrEevated", String.valueOf(cbArchitecturallyDesignedOrEevated));
        } else {
            cbArchitecturallyDesignedOrEevatedCheck = 0;
            hm.put("cbArchitecturallyDesignedOrEevated", String.valueOf(cbArchitecturallyDesignedOrEevatedCheck));
        }
//        if (cbSimplePlasteredWalls.isChecked()) {
//            cbSimplePlasteredWallsCheck = 1;
//            hm.put("cbSimplePlasteredWalls", String.valueOf(cbSimplePlasteredWallsCheck));
//        } else {
//            cbSimplePlasteredWallsCheck = 0;
//            hm.put("cbSimplePlasteredWalls", String.valueOf(cbSimplePlasteredWallsCheck));
//        }
        if (cbIntUnderConstruction.isChecked()) {
            cbIntUnderConstructionCheck = 1;
            hm.put("cbIntUnderConstruction", String.valueOf(cbIntUnderConstructionCheck));
        } else {
            cbIntUnderConstructionCheck = 0;
            hm.put("cbIntUnderConstruction", String.valueOf(cbIntUnderConstructionCheck));
        }
        /*if (cbIntNoSurvey.isChecked()) {
            cbIntNoSurveyCheck = 1;
            hm.put("cbIntNoSurvey", String.valueOf(cbIntNoSurveyCheck));
        } else {
            cbIntNoSurveyCheck = 0;
            hm.put("cbIntNoSurvey", String.valueOf(cbIntNoSurveyCheck));
        }*/

        if (cbNInfoIhFinishing.isChecked()) {
            cbNInfoIhFinishingCheck = 1;
            hm.put("cbNInfoIhFinishing", String.valueOf(cbNInfoIhFinishingCheck));
        } else {
            cbNInfoIhFinishingCheck = 0;
            hm.put("cbNInfoIhFinishing", String.valueOf(cbNInfoIhFinishingCheck));
        }


        if (cbExtSimplePlastered.isChecked()) {
            cbExtSimplePlasteredCheck = 1;
            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
        } else {
            cbExtSimplePlasteredCheck = 0;
            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
        }
        if (cbExtBrickWalls.isChecked()) {
            cbExtBrickWallsCheck = 1;
            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
        } else {
            cbExtBrickWallsCheck = 0;
            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
        }
        if (cbExtArchitecturally.isChecked()) {
            cbExtArchitecturallyCheck = 1;
            hm.put("cbExtArchitecturally", String.valueOf(cbExtArchitecturallyCheck));
        } else {
            cbExtArchitecturallyCheck = 0;
            hm.put("cbExtArchitecturally", String.valueOf(cbExtArchitecturallyCheck));
        }
        if (cbExtBrickTile.isChecked()) {
            cbExtBrickTileCheck = 1;
            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
        } else {
            cbExtBrickTileCheck = 0;
            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
        }
        if (cbExtStructuralGlazing.isChecked()) {
            cbExtStructuralGlazingCheck = 1;
            hm.put("cbExtStructuralGlazing", String.valueOf(cbExtStructuralGlazingCheck));
        } else {
            cbExtStructuralGlazingCheck = 0;
            hm.put("cbExtStructuralGlazing", String.valueOf(cbExtStructuralGlazingCheck));
        }
        if (cbExtAluminumComposite.isChecked()) {
            cbExtAluminumCompositeCheck = 1;
            hm.put("cbExtAluminumComposite", String.valueOf(cbExtAluminumCompositeCheck));
        } else {
            cbExtAluminumCompositeCheck = 0;
            hm.put("cbExtAluminumComposite", String.valueOf(cbExtAluminumCompositeCheck));
        }
        if (cbExtGlassFacade.isChecked()) {
            cbExtGlassFacadeCheck = 1;
            hm.put("cbExtGlassFacade", String.valueOf(cbExtGlassFacadeCheck));
        } else {
            cbExtGlassFacadeCheck = 0;
            hm.put("cbExtGlassFacade", String.valueOf(cbExtGlassFacadeCheck));
        }
        if (cbExtDomb.isChecked()) {
            cbExtDombCheck = 1;
            hm.put("cbExtDomb", String.valueOf(cbExtDombCheck));
        } else {
            cbExtDombCheck = 0;
            hm.put("cbExtDomb", String.valueOf(cbExtDombCheck));
        }
        if (cbExtPorch.isChecked()) {
            cbExtPorchCheck = 1;
            hm.put("cbExtPorch", String.valueOf(cbExtPorchCheck));
        } else {
            cbExtPorchCheck = 0;
            hm.put("cbExtPorch", String.valueOf(cbExtPorchCheck));
        }
        if (cbExtPoorDepleted.isChecked()) {
            cbExtPoorDepletedCheck = 1;
            hm.put("cbExtPoorDepleted", String.valueOf(cbExtPoorDepletedCheck));
        } else {
            cbExtPoorDepletedCheck = 0;
            hm.put("cbExtPoorDepleted", String.valueOf(cbExtPoorDepletedCheck));
        }
        if (cbExtUnderFinishing.isChecked()) {
            cbExtUnderFinishingCheck = 1;
            hm.put("cbExtUnderFinishing", String.valueOf(cbExtUnderFinishingCheck));
        } else {
            cbExtUnderFinishingCheck = 0;
            hm.put("cbExtUnderFinishing", String.valueOf(cbExtUnderFinishingCheck));
        }
        if (cbExtUnderConstruction.isChecked()) {
            cbExtUnderConstructionCheck = 1;
            hm.put("cbExtUnderConstruction", String.valueOf(cbExtUnderConstructionCheck));
        } else {
            cbExtUnderConstructionCheck = 0;
            hm.put("cbExtUnderConstruction", String.valueOf(cbExtUnderConstructionCheck));
        }


        if (cbJetPump.isChecked()) {
            cbJetPumpCheck = 1;
            hm.put("cbJetPump", String.valueOf(cbJetPumpCheck));
        } else {
            cbJetPumpCheck = 0;
            hm.put("cbJetPump", String.valueOf(cbJetPumpCheck));
        }
        if (cbSubmersible.isChecked()) {
            cbSubmersibleCheck = 1;
            hm.put("cbSubmersible", String.valueOf(cbSubmersibleCheck));
        } else {
            cbSubmersibleCheck = 0;
            hm.put("cbSubmersible", String.valueOf(cbSubmersibleCheck));
        }
        if (cbJalBoardSupply.isChecked()) {
            cbJalBoardSupplyCheck = 1;
            hm.put("cbJalBoardSupply", String.valueOf(cbJalBoardSupplyCheck));
        } else {
            cbJalBoardSupplyCheck = 0;
            hm.put("cbJalBoardSupply", String.valueOf(cbJalBoardSupplyCheck));
        }
        if (cbUnderConstructionWater.isChecked()) {
            cbUnderConstructionWaterCheck = 1;
            hm.put("cbUnderConstructionWater", String.valueOf(cbUnderConstructionWaterCheck));
        } else {
            cbJalBoardSupplyCheck = 0;
            hm.put("cbUnderConstructionWater", String.valueOf(cbUnderConstructionWaterCheck));
        }
        if (cbNoInfoAvailWater.isChecked()) {
            cbNoInfoAvailWaterCheck = 1;
            hm.put("cbNoInfoAvailWater", String.valueOf(cbNoInfoAvailWaterCheck));
        } else {
            cbNoInfoAvailWaterCheck = 0;
            hm.put("cbNoInfoAvailWater", String.valueOf(cbNoInfoAvailWaterCheck));
        }


        if (cbMaintenanceIssues.isChecked()) {
            cbMaintenanceIssuesCheck = 1;
            hm.put("cbMaintenanceIssuess", String.valueOf(cbMaintenanceIssuesCheck));
        } else {
            cbMaintenanceIssuesCheck = 0;
            hm.put("cbMaintenanceIssuess", String.valueOf(cbMaintenanceIssuesCheck));
        }
        if (cbFinishingIssues.isChecked()) {
            cbFinishingIssuesCheck = 1;
            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
        } else {
            cbFinishingIssuesCheck = 0;
            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
        }
        if (cbSeepageIssues.isChecked()) {
            cbSeepageIssuesCheck = 1;
            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
        } else {
            cbSeepageIssuesCheck = 0;
            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
        }
        if (cbWaterSupplyIssues.isChecked()) {
            cbWaterSupplyIssuesCheck = 1;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        } else {
            cbWaterSupplyIssuesCheck = 0;
            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
        }
        if (cbElectricalIssues.isChecked()) {
            cbElectricalIssuesCheck = 1;
            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
        } else {
            cbElectricalIssuesCheck = 0;
            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
        }
        if (cbStructuralIssues.isChecked()) {
            cbStructuralIssuesCheck = 1;
            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
        } else {
            cbStructuralIssuesCheck = 0;
            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
        }
        if (cbVisibleCracks.isChecked()) {
            cbVisibleCracksCheck = 1;
            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracksCheck));
        } else {
            cbVisibleCracksCheck = 0;
            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracksCheck));
        }
        if (cbRunDownBuilding.isChecked()) {
            cbRunDownBuildingCheck = 1;
            hm.put("cbRunDownBuilding", String.valueOf(cbRunDownBuildingCheck));
        } else {
            cbRunDownBuildingCheck = 0;
            hm.put("cbRunDownBuilding", String.valueOf(cbRunDownBuildingCheck));
        }
        ///////
        if (cbNoMoreSafe.isChecked()) {
            cbNoMoreSafeCheck = 1;
            hm.put("cbNoMoreSafe", String.valueOf(cbNoMoreSafeCheck));
        } else {
            cbNoMoreSafeCheck = 0;
            hm.put("cbNoMoreSafe", String.valueOf(cbNoMoreSafeCheck));
        }
        if (cbCompletelyRunDownBuilding.isChecked()) {
            cbCompletelyRunDownBuildingCheck = 1;
            hm.put("cbCompletelyRunDownBuilding", String.valueOf(cbCompletelyRunDownBuildingCheck));
        } else {
            cbCompletelyRunDownBuildingCheck = 0;
            hm.put("cbCompletelyRunDownBuilding", String.valueOf(cbCompletelyRunDownBuildingCheck));
        }
        if (cbNotAppropriate.isChecked()) {
            cbNotAppropriateCheck = 1;
            hm.put("cbNotAppropriate", String.valueOf(cbNotAppropriateCheck));
        } else {
            cbNotAppropriateCheck = 0;
            hm.put("cbNotAppropriate", String.valueOf(cbNotAppropriateCheck));
        }
        if (cbUnderConstructionDefects.isChecked()) {
            cbUnderConstructionDefectsCheck = 1;
            hm.put("cbUnderConstructionDefects", String.valueOf(cbUnderConstructionDefectsCheck));
        } else {
            cbUnderConstructionDefectsCheck = 0;
            hm.put("cbUnderConstructionDefects", String.valueOf(cbUnderConstructionDefectsCheck));
        }

        if (cbNoDefect.isChecked()) {
            cbNoDefectCheck = 1;
            hm.put("cbNoDefect", String.valueOf(cbNoDefectCheck));
        } else {
            cbNoDefectCheck = 0;
            hm.put("cbNoDefect", String.valueOf(cbNoDefectCheck));
        }

        if (cbNoInfo.isChecked()) {
            cbNoInfoCheck = 1;
            hm.put("cbNoInfo", String.valueOf(cbNoInfoCheck));
        } else {
            cbNoInfoCheck = 0;
            hm.put("cbNoInfo", String.valueOf(cbNoInfoCheck));
        }


        if (cbConstructionDoneWithoutMap.isChecked()) {
            cbConstructionDoneWithoutMapCheck = 1;
            hm.put("cbConstructionDoneWithoutMaap", String.valueOf(cbConstructionDoneWithoutMapCheck));
        } else {
            cbConstructionDoneWithoutMapCheck = 0;
            hm.put("cbConstructionDoneWithoutMaap", String.valueOf(cbConstructionDoneWithoutMapCheck));
        }
        if (cbConstructionNotAsPer.isChecked()) {
            cbConstructionNotAsPerCheck = 1;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        } else {
            cbConstructionNotAsPerCheck = 0;
            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
        }
        if (cbExtraCovered.isChecked()) {
            cbExtraCoveredCheck = 1;
            hm.put("cbExtraCovereed", String.valueOf(cbExtraCoveredCheck));
        } else {
            cbExtraCoveredCheck = 0;
            hm.put("cbExtraCovereed", String.valueOf(cbExtraCoveredCheck));
        }
        if (cbJoinedAdjacent.isChecked()) {
            cbJoinedAdjacentCheck = 1;
            hm.put("cbJoinedAdjacenntt", String.valueOf(cbJoinedAdjacentCheck));
        } else {
            cbJoinedAdjacentCheck = 0;
            hm.put("cbJoinedAdjacenntt", String.valueOf(cbJoinedAdjacentCheck));
        }

        if (cbEncroachedAdjacent.isChecked()) {
            cbEncroachedAdjacentCheck = 1;
            hm.put("cbEncroachedAdjacennt", String.valueOf(cbEncroachedAdjacentCheck));
        } else {
            cbEncroachedAdjacentCheck = 0;
            hm.put("cbEncroachedAdjacennt", String.valueOf(cbEncroachedAdjacentCheck));
        }

        if (cbNoViolation.isChecked()) {
            cbNoViolationCheck = 1;
            hm.put("cbNoViolation", String.valueOf(cbNoViolationCheck));
        } else {
            cbNoViolationCheck = 0;
            hm.put("cbNoViolation", String.valueOf(cbNoViolationCheck));
        }

        if (cbUnderConstructionAnyViolation.isChecked()) {
            cbUnderConstructionAnyViolationCheck = 1;
            hm.put("cbUnderConstructionAnyViolation", String.valueOf(cbUnderConstructionAnyViolationCheck));
        } else {
            cbUnderConstructionAnyViolationCheck = 0;
            hm.put("cbUnderConstructionAnyViolation", String.valueOf(cbUnderConstructionAnyViolationCheck));
        }

        if (cbNoViolence.isChecked()) {
            cbNoViolenceCheck = 1;
            hm.put("cbNoViolence", String.valueOf(cbNoViolenceCheck));
        } else {
            cbNoViolenceCheck = 0;
            hm.put("cbNoViolence", String.valueOf(cbNoViolenceCheck));
        }


        int selectedIdConstructionStatus = rgConstructionStatus.getCheckedRadioButtonId();
        View radioButtonConstructionStatus = v.findViewById(selectedIdConstructionStatus);
        int idx = rgConstructionStatus.indexOfChild(radioButtonConstructionStatus);
        RadioButton r = (RadioButton) rgConstructionStatus.getChildAt(idx);
        String selectedText = r.getText().toString();
        hm.put("radioButtonConstructionStatuss", selectedText);

        int selectedIdBuildingType = rgBuildingType.getCheckedRadioButtonId();
        View radioButtonBuildingType = v.findViewById(selectedIdBuildingType);
        int idx2 = rgBuildingType.indexOfChild(radioButtonBuildingType);
        RadioButton r2 = (RadioButton) rgBuildingType.getChildAt(idx2);
        String selectedText2 = r2.getText().toString();
        hm.put("radioButtonBuildingTyppe", selectedText2);

        int selectedIdClassOfConstruction = rgClassOfConstruction.getCheckedRadioButtonId();
        View radioButtonClassOfConstruction = v.findViewById(selectedIdClassOfConstruction);
        int idx3 = rgClassOfConstruction.indexOfChild(radioButtonClassOfConstruction);
        RadioButton r3 = (RadioButton) rgClassOfConstruction.getChildAt(idx3);
        String selectedText3 = r3.getText().toString();
        hm.put("radioButtonClassOfConstruction", selectedText3);

        int selectedIdAppearanceInternal = rgAppearanceInternal.getCheckedRadioButtonId();
        View radioButtonAppearanceInternal = v.findViewById(selectedIdAppearanceInternal);
        int idx4 = rgAppearanceInternal.indexOfChild(radioButtonAppearanceInternal);
        RadioButton r4 = (RadioButton) rgAppearanceInternal.getChildAt(idx4);
        String selectedText4 = r4.getText().toString();
        hm.put("radioButtonAppearanceInternal", selectedText4);

        int selectedIdAppearanceExternal = rgAppearanceExternal.getCheckedRadioButtonId();
        View radioButtonAppearanceExternal = v.findViewById(selectedIdAppearanceExternal);
        int idx5 = rgAppearanceExternal.indexOfChild(radioButtonAppearanceExternal);
        RadioButton r5 = (RadioButton) rgAppearanceExternal.getChildAt(idx5);
        String selectedText5 = r5.getText().toString();
        hm.put("radioButtonAppearanceExternal", selectedText5);

   /*     int selectedIdMaintenanceOfTheBuilding = rgMaintenanceOfTheBuilding.getCheckedRadioButtonId();
        View radioButtonMaintenanceOfTheBuilding = v.findViewById(selectedIdMaintenanceOfTheBuilding);
        int idx6 = rgMaintenanceOfTheBuilding.indexOfChild(radioButtonMaintenanceOfTheBuilding);
        RadioButton r6 = (RadioButton) rgMaintenanceOfTheBuilding.getChildAt(idx6);
        String selectedText6 = r6.getText().toString();
        hm.put("radioButtonMaintenanceOfTheBuildiing", selectedText6);*/

        int selectedIdInteriorDecoration = rgInteriorDecoration.getCheckedRadioButtonId();
        View radioButtonInteriorDecoration = v.findViewById(selectedIdInteriorDecoration);
        int idx7 = rgInteriorDecoration.indexOfChild(radioButtonInteriorDecoration);
        RadioButton r7 = (RadioButton) rgInteriorDecoration.getChildAt(idx7);
        String selectedText7 = r7.getText().toString();
        hm.put("radioButtonInteriorDecoration", selectedText7);

        int selectedIdKitchen = rgKitchen.getCheckedRadioButtonId();
        View radioButtonKitchen = v.findViewById(selectedIdKitchen);
        int idx8 = rgKitchen.indexOfChild(radioButtonKitchen);
        RadioButton r8 = (RadioButton) rgKitchen.getChildAt(idx8);
        String selectedText8 = r8.getText().toString();
        hm.put("radioButtonKitchen", selectedText8);

        try {
            int selectedIdClassOfElectrical = rgClassOfElectricalsOne.getCheckedRadioButtonId();
            View radioButtonClassOfElectrical = v.findViewById(selectedIdClassOfElectrical);
            int idx9 = rgClassOfElectricalsOne.indexOfChild(radioButtonClassOfElectrical);
            RadioButton r9 = (RadioButton) rgClassOfElectricalsOne.getChildAt(idx9);
            String selectedText9 = r9.getText().toString();
            hm.put("radioButtonClassOfElectricalOne", selectedText9);
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("radioButtonClassOfElectricalOne", "na");
        }


        try {
            int selectedIdClassOfElectricalTwo = rgClassOfElectricalsTwo.getCheckedRadioButtonId();
            View radioButtonClassOfElectricalTwo = v.findViewById(selectedIdClassOfElectricalTwo);
            int idx99 = rgClassOfElectricalsTwo.indexOfChild(radioButtonClassOfElectricalTwo);
            RadioButton r99 = (RadioButton) rgClassOfElectricalsTwo.getChildAt(idx99);
            String selectedText99 = r99.getText().toString();
            hm.put("radioButtonClassOfElectricalTwo", selectedText99);
        } catch (Exception e) {
            e.printStackTrace();
            hm.put("radioButtonClassOfElectricalTwo", "na");
        }


        try {
            int selectedIdClassOfElectricalThree = rgClassOfElectricalsThree.getCheckedRadioButtonId();
            View radioButtonClassOfElectricalThree = v.findViewById(selectedIdClassOfElectricalThree);
            int idx999 = rgClassOfElectricalsThree.indexOfChild(radioButtonClassOfElectricalThree);
            RadioButton r999 = (RadioButton) rgClassOfElectricalsThree.getChildAt(idx999);
            String selectedText999 = r999.getText().toString();
            hm.put("radioButtonClassOfElectricalThree", selectedText999);
        } catch (Exception e) {
            hm.put("radioButtonClassOfElectricalThree", "na");
            e.printStackTrace();
        }


        try {
            int selectedIdClassOfSanitary = rgClassOfSanitarysOne.getCheckedRadioButtonId();
            View radioButtonClassOfSanitary = v.findViewById(selectedIdClassOfSanitary);
            int idx10 = rgClassOfSanitarysOne.indexOfChild(radioButtonClassOfSanitary);
            RadioButton r10 = (RadioButton) rgClassOfSanitarysOne.getChildAt(idx10);
            String selectedText10 = r10.getText().toString();
            hm.put("radioButtonClassOfSanitaryOne", selectedText10);

        } catch (Exception e) {
            hm.put("radioButtonClassOfSanitaryOne", "na");
            e.printStackTrace();
        }
        try {
            int selectedIdClassOfSanitaryTwo = rgClassOfSanitarysTwo.getCheckedRadioButtonId();
            View radioButtonClassOfSanitaryTwo = v.findViewById(selectedIdClassOfSanitaryTwo);
            int idx100 = rgClassOfSanitarysTwo.indexOfChild(radioButtonClassOfSanitaryTwo);
            RadioButton r100 = (RadioButton) rgClassOfSanitarysTwo.getChildAt(idx100);
            String selectedText100 = r100.getText().toString();
            hm.put("radioButtonClassOfSanitaryTwo", selectedText100);
        } catch (Exception e) {
            hm.put("radioButtonClassOfSanitaryTwo", "na");
            e.printStackTrace();
        }
        try {

            int selectedIdClassOfSanitaryThree = rgClassOfSanitarysThree.getCheckedRadioButtonId();
            View radioButtonClassOfSanitaryThree = v.findViewById(selectedIdClassOfSanitaryThree);
            int idx101 = rgClassOfSanitarysThree.indexOfChild(radioButtonClassOfSanitaryThree);
            RadioButton r101 = (RadioButton) rgClassOfSanitarysThree.getChildAt(idx101);
            String selectedText101 = r101.getText().toString();
            hm.put("radioButtonClassOfSanitaryThree", selectedText101);
        } catch (Exception e) {
            hm.put("radioButtonClassOfSanitaryThree", "na");
            e.printStackTrace();
        }


        int selectedIdFixedWoodenWork = rgFixedWoodenWork.getCheckedRadioButtonId();
        View radioButtonFixedWoodenWork = v.findViewById(selectedIdFixedWoodenWork);
        int idx11 = rgFixedWoodenWork.indexOfChild(radioButtonFixedWoodenWork);
        RadioButton r11 = (RadioButton) rgFixedWoodenWork.getChildAt(idx11);
        String selectedText11 = r11.getText().toString();
        hm.put("radioButtonFixedWoodenWork", selectedText11);

        int selectedIdMaintenanceOfTheBuilding2 = rgMaintenanceOfTheBuilding2.getCheckedRadioButtonId();
        View radioButtonMaintenanceOfTheBuilding2 = v.findViewById(selectedIdMaintenanceOfTheBuilding2);
        int idx12 = rgMaintenanceOfTheBuilding2.indexOfChild(radioButtonMaintenanceOfTheBuilding2);
        RadioButton r12 = (RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(idx12);
        String selectedText12 = r12.getText().toString();
        hm.put("radioButtonMaintenanceOfTheBuildingg2", selectedText12);

        arrayListData.clear();

        arrayListData.add(hm);

        jsonArrayData = new JSONArray(arrayListData);

        //DatabaseController.removeTable(TableGeneralForm.attachment);
        // DatabaseController.deleteColumnData("column1");

        ContentValues contentValues = new ContentValues();

        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
        contentValues.put(TableGeneralForm.generalFormColumn.column8.toString(), jsonArrayData.toString());

        JSONObject obj = new JSONObject();
        try {
            obj.put("id_new", pref.get(Constants.case_id));
            obj.put("page", "8");

            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));
    }

    public void selectDB() throws JSONException {

        sub_cat = DatabaseController.getTableOne("column8", pref.get(Constants.case_id));

        if (sub_cat != null) {

            Log.v("getfromdb=====", String.valueOf(sub_cat));
            if (!sub_cat.get(0).get("column8").equalsIgnoreCase(null)) {
                JSONArray array = new JSONArray(sub_cat.get(0).get("column8"));

                Log.v("getfromdbarray=====", String.valueOf(array));

                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);


                    if (object.getString("inCaseOfUnderConstructionSpinner").equals("0")) {
                        spinnerInCaseOfUnderConstruction.setSelection(0);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("1")) {
                        spinnerInCaseOfUnderConstruction.setSelection(1);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("2")) {
                        spinnerInCaseOfUnderConstruction.setSelection(2);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("3")) {
                        spinnerInCaseOfUnderConstruction.setSelection(3);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("4")) {
                        spinnerInCaseOfUnderConstruction.setSelection(4);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("5")) {
                        spinnerInCaseOfUnderConstruction.setSelection(5);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("6")) {
                        spinnerInCaseOfUnderConstruction.setSelection(6);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("7")) {
                        spinnerInCaseOfUnderConstruction.setSelection(7);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("8")) {
                        spinnerInCaseOfUnderConstruction.setSelection(8);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("9")) {
                        spinnerInCaseOfUnderConstruction.setSelection(9);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("10")) {
                        spinnerInCaseOfUnderConstruction.setSelection(10);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("11")) {
                        spinnerInCaseOfUnderConstruction.setSelection(11);
                    } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("12")) {
                        spinnerInCaseOfUnderConstruction.setSelection(12);
                    }

                    etTotalNumberOfFloors.setText(object.getString("totalNoOfFloors"));
                    etFloorOnWhich.setText(object.getString("floorOnWhich"));

                    etTypeOfUnit.setText(object.getString("typeOfUnitt"));
                    etNumberOfRooms.setText(object.getString("numberOfRooms"));
                    etCabins.setText(object.getString("cabins"));
                    etCubicles.setText(object.getString("cubicles"));

                    etRoofHeight.setText(object.getString("roofHeight"));
                    etAgeOfBuilding.setText(object.getString("ageOfBuildinggg"));
                    etRecentImprovements.setText(object.getString("recentImprovements"));
                    etExtentOfViolation.setText(object.getString("extentOfViolation"));
                    etUptoSlabLevel.setText(object.getString("uptoSlabLevel"));

                    if (object.getString("cbRcc").equals("1")) {
                        cbRcc.setChecked(true);
                    } else {
                        cbRcc.setChecked(false);
                    }
                    if (object.getString("cbRbc").equals("1")) {
                        cbRbc.setChecked(true);
                    } else {
                        cbRbc.setChecked(false);
                    }
                    if (object.getString("cbGiShed").equals("1")) {
                        cbGiShed.setChecked(true);
                    } else {
                        cbGiShed.setChecked(false);
                    }
                    if (object.getString("cbTinShed").equals("1")) {
                        cbTinShed.setChecked(true);
                    } else {
                        cbTinShed.setChecked(false);
                    }
                    if (object.getString("cbStonePatla").equals("1")) {
                        cbStonePatla.setChecked(true);
                    } else {
                        cbStonePatla.setChecked(false);
                    }
                    if (object.getString("cbSimplePlaster").equals("1")) {
                        cbSimplePlaster.setChecked(true);
                    } else {
                        cbSimplePlaster.setChecked(false);
                    }
                    if (object.getString("cbPopPunning").equals("1")) {
                        cbPopPunning.setChecked(true);
                    } else {
                        cbPopPunning.setChecked(false);
                    }
                    if (object.getString("cbPopFalseCeiling").equals("1")) {
                        cbPopFalseCeiling.setChecked(true);
                    } else {
                        cbPopFalseCeiling.setChecked(false);
                    }
                    if (object.getString("cbCovedRoof").equals("1")) {
                        cbCovedRoof.setChecked(true);
                    } else {
                        cbCovedRoof.setChecked(false);
                    }
                    if (object.getString("cbNoPlaster").equals("1")) {
                        cbNoPlaster.setChecked(true);
                    } else {
                        cbNoPlaster.setChecked(false);
                    }
                    if (object.getString("cbDomedRoof").equals("1")) {
                        cbDomedRoof.setChecked(true);
                    } else {
                        cbDomedRoof.setChecked(false);
                    }
                    if (object.getString("cbBeautifulCarving").equals("1")) {
                        cbBeautifulCarving.setChecked(true);
                    } else {
                        cbBeautifulCarving.setChecked(false);
                    }
                    if (object.getString("cbNoInfoAvailable").equals("1")) {
                        cbNoInfoAvailable.setChecked(true);
                    } else {
                        cbNoInfoAvailable.setChecked(false);
                    }
                    if (object.getString("cbUnderConstructionFinish").equals("1")) {
                        cbUnderConstructionFinish.setChecked(true);
                    } else {
                        cbUnderConstructionFinish.setChecked(false);
                    }


                    if (object.getString("cbMudFlooring").equals("1")) {
                        cbMudFlooring.setChecked(true);
                    } else {
                        cbMudFlooring.setChecked(false);
                    }
                    if (object.getString("cbBrickFlooring").equals("1")) {
                        cbBrickFlooring.setChecked(true);
                    } else {
                        cbBrickFlooring.setChecked(false);
                    }
                    if (object.getString("cbVitrifiedTiles").equals("1")) {
                        cbVitrifiedTiles.setChecked(true);
                    } else {
                        cbVitrifiedTiles.setChecked(false);
                    }
                    if (object.getString("cbCeramicTiles").equals("1")) {
                        cbCeramicTiles.setChecked(true);
                    } else {
                        cbCeramicTiles.setChecked(false);
                    }


                    if (object.getString("cbSimpleMarble").equals("1")) {
                        cbSimpleMarble.setChecked(true);
                    } else {
                        cbSimpleMarble.setChecked(false);
                    }
                    if (object.getString("cbMarbleChips").equals("1")) {
                        cbMarbleChips.setChecked(true);
                    } else {
                        cbMarbleChips.setChecked(false);
                    }
                    if (object.getString("cbMosaic").equals("1")) {
                        cbMosaic.setChecked(true);
                    } else {
                        cbMosaic.setChecked(false);
                    }
                    if (object.getString("cbGranite").equals("1")) {
                        cbGranite.setChecked(true);
                    } else {
                        cbGranite.setChecked(false);
                    }
                    if (object.getString("cbItalianMarble").equals("1")) {
                        cbItalianMarble.setChecked(true);
                    } else {
                        cbItalianMarble.setChecked(false);
                    }
                    if (object.getString("cbKotaStone").equals("1")) {
                        cbKotaStone.setChecked(true);
                    } else {
                        cbKotaStone.setChecked(false);
                    }
                    if (object.getString("cbWooden").equals("1")) {
                        cbWooden.setChecked(true);
                    } else {
                        cbWooden.setChecked(false);
                    }
                    if (object.getString("cbPccc").equals("1")) {
                        cbPcc.setChecked(true);
                    } else {
                        cbPcc.setChecked(false);
                    }


                    if (object.getString("cbImportedMarble").equals("1")) {
                        cbImportedMarble.setChecked(true);
                    } else {
                        cbImportedMarble.setChecked(false);
                    }
                    if (object.getString("cbPavers").equals("1")) {
                        cbPavers.setChecked(true);
                    } else {
                        cbPavers.setChecked(false);
                    }
                    if (object.getString("cbChequered").equals("1")) {
                        cbChequered.setChecked(true);
                    } else {
                        cbChequered.setChecked(false);
                    }
                    if (object.getString("cbBrickTiless").equals("1")) {
                        cbBrickTiles.setChecked(true);
                    } else {
                        cbBrickTiles.setChecked(false);
                    }
                    if (object.getString("cbVinylFlooring").equals("1")) {
                        cbVinylFlooring.setChecked(true);
                    } else {
                        cbVinylFlooring.setChecked(false);
                    }

                    if (object.getString("cbLaminateFlooring").equals("1")) {
                        cbLaminateFlooring.setChecked(true);
                    } else {
                        cbLaminateFlooring.setChecked(false);
                    }
                    if (object.getString("cbLinoleumFlooring").equals("1")) {
                        cbLinoleumFlooring.setChecked(true);
                    } else {
                        cbLinoleumFlooring.setChecked(false);
                    }
                    if (object.getString("cbNoFlooring").equals("1")) {
                        cbNoFlooring.setChecked(true);
                    } else {
                        cbNoFlooring.setChecked(false);
                    }


                    if (object.getString("cbUnderConstruction").equals("1")) {
                        cbUnderConstruction.setChecked(true);
                    } else {
                        cbUnderConstruction.setChecked(false);
                    }
                    if (object.getString("cbAnyOtherTypee").equals("1")) {
                        cbAnyOtherType.setChecked(true);
                    } else {
                        cbAnyOtherType.setChecked(false);
                    }
                    if (object.getString("cbNoInfoAvaill").equals("1")) {
                        cbNoInfoAvaill.setChecked(true);
                    } else {
                        cbNoInfoAvaill.setChecked(false);
                    }

                    if (object.getString("cbIntSimplePlastered").equals("1")) {
                        cbIntSimplePlastered.setChecked(true);
                    } else {
                        cbIntSimplePlastered.setChecked(false);
                    }
                    if (object.getString("cbIntBrickWalls").equals("1")) {
                        cbIntBrickWalls.setChecked(true);
                    } else {
                        cbIntBrickWalls.setChecked(false);
                    }


                    if (object.getString("cbIntDesignerTextured").equals("1")) {
                        cbIntDesignerTextured.setChecked(true);
                    } else {
                        cbIntDesignerTextured.setChecked(false);
                    }
                    if (object.getString("cbIntPopPunning").equals("1")) {
                        cbIntPopPunning.setChecked(true);
                    } else {
                        cbIntPopPunning.setChecked(false);
                    }
                    if (object.getString("cbIntCovedRoof").equals("1")) {
                        cbIntCovedRoof.setChecked(true);
                    } else {
                        cbIntCovedRoof.setChecked(false);
                    }
                    if (object.getString("cbIntVeneerLaminated").equals("1")) {
                        cbIntVeneerLaminated.setChecked(true);
                    } else {
                        cbIntVeneerLaminated.setChecked(false);
                    }

                    if (object.getString("cbIntSteelLaminated").equals("1")) {
                        cbIntSteelLaminated.setChecked(true);
                    } else {
                        cbIntSteelLaminated.setChecked(false);
                    }
                    if (object.getString("cbIntPoorDepleted").equals("1")) {
                        cbIntPoorDepleted.setChecked(true);
                    } else {
                        cbIntPoorDepleted.setChecked(false);
                    }

                    if (object.getString("cbDesignerCovedRoof").equals("1")) {
                        cbDesignerCovedRoof.setChecked(true);
                    } else {
                        cbDesignerCovedRoof.setChecked(false);
                    }


                    if (object.getString("cbWoodVeneerLaminatedWalls").equals("1")) {
                        cbWoodVeneerLaminatedWalls.setChecked(true);
                    } else {
                        cbWoodVeneerLaminatedWalls.setChecked(false);
                    }


//                if (object.getString("cbBrickWallsWithoutPlaster").equals("1")) {
//                    cbBrickWallsWithoutPlaster.setChecked(true);
//                } else {
//                    cbBrickWallsWithoutPlaster.setChecked(false);
//                }


                    if (object.getString("cbIntUnderFinishing").equals("1")) {
                        cbIntUnderFinishing.setChecked(true);
                    } else {
                        cbIntUnderFinishing.setChecked(false);
                    }

                    if (object.getString("cbItBrickTileCladding").equals("1")) {
                        cbItBrickTileCladding.setChecked(true);
                    } else {
                        cbItBrickTileCladding.setChecked(false);
                    }
                    if (object.getString("cbPrismGlassPortionn").equals("1")) {
                        cbPrismGlassPortionn.setChecked(true);
                    } else {
                        cbPrismGlassPortionn.setChecked(false);
                    }

                    if (object.getString("cbDesignerFalseCeiling").equals("1")) {
                        cbDesignerFalseCeiling.setChecked(true);
                    } else {
                        cbDesignerFalseCeiling.setChecked(false);
                    }
                    if (object.getString("cbGlassPartitions").equals("1")) {
                        cbGlassPartitions.setChecked(true);
                    } else {
                        cbGlassPartitions.setChecked(false);
                    }

//                if (object.getString("cbPOPPunning").equals("1")) {
//                    cbPOPPunning.setChecked(true);
//                } else {
//                    cbPOPPunning.setChecked(false);
//                }
//                 if (object.getString("cbDesignerTexturedWalls").equals("1")) {
//                     cbDesignerTexturedWalls.setChecked(true);
//                } else {
//                     cbDesignerTexturedWalls.setChecked(false);
//                }

                    if (object.getString("cbNeatlyPlasteredAndPuttyCoatedWalls").equals("1")) {
                        cbNeatlyPlasteredAndPuttyCoatedWalls.setChecked(true);
                    } else {
                        cbNeatlyPlasteredAndPuttyCoatedWalls.setChecked(false);
                    }

                    if (object.getString("cbArchitecturallyDesignedOrEevated").equals("1")) {
                        cbArchitecturallyDesignedOrEevated.setChecked(true);
                    } else {
                        cbArchitecturallyDesignedOrEevated.setChecked(false);
                    }
//                if (object.getString("cbSimplePlasteredWalls").equals("1")) {
//                    cbSimplePlasteredWalls.setChecked(true);
//                } else {
//                    cbSimplePlasteredWalls.setChecked(false);
//                }
                    if (object.getString("cbIntUnderConstruction").equals("1")) {
                        cbIntUnderConstruction.setChecked(true);
                    } else {
                        cbIntUnderConstruction.setChecked(false);
                    }
               /* if (object.getString("cbIntNoSurvey").equals("1")) {
                    cbIntNoSurvey.setChecked(true);
                } else {
                    cbIntNoSurvey.setChecked(false);
                }*/
                    if (object.getString("cbNInfoIhFinishing").equals("1")) {
                        cbNInfoIhFinishing.setChecked(true);
                    } else {
                        cbNInfoIhFinishing.setChecked(false);
                    }

                    if (object.getString("cbExtSimplePlastered").equals("1")) {
                        cbExtSimplePlastered.setChecked(true);
                    } else {
                        cbExtSimplePlastered.setChecked(false);
                    }
                    if (object.getString("cbExtBrickWalls").equals("1")) {
                        cbExtBrickWalls.setChecked(true);
                    } else {
                        cbExtBrickWalls.setChecked(false);
                    }
                    if (object.getString("cbExtArchitecturally").equals("1")) {
                        cbExtArchitecturally.setChecked(true);
                    } else {
                        cbExtArchitecturally.setChecked(false);
                    }
                    if (object.getString("cbExtBrickTile").equals("1")) {
                        cbExtBrickTile.setChecked(true);
                    } else {
                        cbExtBrickTile.setChecked(false);
                    }
                    if (object.getString("cbExtStructuralGlazing").equals("1")) {
                        cbExtStructuralGlazing.setChecked(true);
                    } else {
                        cbExtStructuralGlazing.setChecked(false);
                    }
                    if (object.getString("cbExtAluminumComposite").equals("1")) {
                        cbExtAluminumComposite.setChecked(true);
                    } else {
                        cbExtAluminumComposite.setChecked(false);
                    }
                    if (object.getString("cbExtGlassFacade").equals("1")) {
                        cbExtGlassFacade.setChecked(true);
                    } else {
                        cbExtGlassFacade.setChecked(false);
                    }


                    if (object.getString("cbExtDomb").equals("1")) {
                        cbExtDomb.setChecked(true);
                    } else {
                        cbExtDomb.setChecked(false);
                    }
                    if (object.getString("cbExtPorch").equals("1")) {
                        cbExtPorch.setChecked(true);
                    } else {
                        cbExtPorch.setChecked(false);
                    }
                    if (object.getString("cbExtPoorDepleted").equals("1")) {
                        cbExtPoorDepleted.setChecked(true);
                    } else {
                        cbExtPoorDepleted.setChecked(false);
                    }
                    if (object.getString("cbExtUnderFinishing").equals("1")) {
                        cbExtUnderFinishing.setChecked(true);
                    } else {
                        cbExtUnderFinishing.setChecked(false);
                    }
                    if (object.getString("cbExtUnderConstruction").equals("1")) {
                        cbExtUnderConstruction.setChecked(true);
                    } else {
                        cbExtUnderConstruction.setChecked(false);
                    }
                    if (object.getString("cbJetPump").equals("1")) {
                        cbJetPump.setChecked(true);
                    } else {
                        cbJetPump.setChecked(false);
                    }
                    if (object.getString("cbSubmersible").equals("1")) {
                        cbSubmersible.setChecked(true);
                    } else {
                        cbSubmersible.setChecked(false);
                    }
                    if (object.getString("cbJalBoardSupply").equals("1")) {
                        cbJalBoardSupply.setChecked(true);
                    } else {
                        cbJalBoardSupply.setChecked(false);
                    }
                    if (object.getString("cbUnderConstructionWater").equals("1")) {
                        cbUnderConstructionWater.setChecked(true);
                    } else {
                        cbUnderConstructionWater.setChecked(false);
                    }

                    if (object.getString("cbNoInfoAvailWater").equals("1")) {
                        cbNoInfoAvailWater.setChecked(true);
                    } else {
                        cbNoInfoAvailWater.setChecked(false);
                    }


                    if (object.getString("cbMaintenanceIssuess").equals("1")) {
                        cbMaintenanceIssues.setChecked(true);
                    } else {
                        cbMaintenanceIssues.setChecked(false);
                    }

                    if (object.getString("cbFinishingIssues").equals("1")) {
                        cbFinishingIssues.setChecked(true);
                    } else {
                        cbFinishingIssues.setChecked(false);
                    }
                    if (object.getString("cbSeepageIssues").equals("1")) {
                        cbSeepageIssues.setChecked(true);
                    } else {
                        cbSeepageIssues.setChecked(false);
                    }
                    if (object.getString("cbWaterSupplyIssues").equals("1")) {
                        cbWaterSupplyIssues.setChecked(true);
                    } else {
                        cbWaterSupplyIssues.setChecked(false);
                    }

                    if (object.getString("cbElectricalIssues").equals("1")) {
                        cbElectricalIssues.setChecked(true);
                    } else {
                        cbElectricalIssues.setChecked(false);
                    }
                    if (object.getString("cbStructuralIssues").equals("1")) {
                        cbStructuralIssues.setChecked(true);
                    } else {
                        cbStructuralIssues.setChecked(false);
                    }
                    if (object.getString("cbVisibleCracks").equals("1")) {
                        cbVisibleCracks.setChecked(true);
                    } else {
                        cbVisibleCracks.setChecked(false);
                    }
                    if (object.getString("cbRunDownBuilding").equals("1")) {
                        cbRunDownBuilding.setChecked(true);
                    } else {
                        cbRunDownBuilding.setChecked(false);
                    }
                    if (object.getString("cbNoInfo").equals("1")) {
                        cbNoInfo.setChecked(true);
                    } else {
                        cbNoInfo.setChecked(false);
                    }
                    ///
                    if (object.getString("cbNoMoreSafe").equals("1")) {
                        cbNoMoreSafe.setChecked(true);
                    } else {
                        cbNoMoreSafe.setChecked(false);
                    }
                    if (object.getString("cbCompletelyRunDownBuilding").equals("1")) {
                        cbCompletelyRunDownBuilding.setChecked(true);
                    } else {
                        cbCompletelyRunDownBuilding.setChecked(false);
                    }
                    if (object.getString("cbNotAppropriate").equals("1")) {
                        cbNotAppropriate.setChecked(true);
                    } else {
                        cbNotAppropriate.setChecked(false);
                    }

                    if (object.getString("cbUnderConstructionDefects").equals("1")) {
                        cbUnderConstructionDefects.setChecked(true);
                    } else {
                        cbUnderConstructionDefects.setChecked(false);
                    }

                    if (object.getString("cbNoDefect").equals("1")) {
                        cbNoDefect.setChecked(true);
                    } else {
                        cbNoDefect.setChecked(false);
                    }

                    if (object.getString("cbConstructionDoneWithoutMaap").equals("1")) {
                        cbConstructionDoneWithoutMap.setChecked(true);
                    } else {
                        cbConstructionDoneWithoutMap.setChecked(false);
                    }
                    if (object.getString("cbConstructionNotAsPer").equals("1")) {
                        cbConstructionNotAsPer.setChecked(true);
                    } else {
                        cbConstructionNotAsPer.setChecked(false);
                    }
                    if (object.getString("cbExtraCovereed").equals("1")) {
                        cbExtraCovered.setChecked(true);
                    } else {
                        cbExtraCovered.setChecked(false);
                    }

                    if (object.getString("cbJoinedAdjacenntt").equals("1")) {
                        cbJoinedAdjacent.setChecked(true);
                    } else {
                        cbJoinedAdjacent.setChecked(false);
                    }

                    if (object.getString("cbEncroachedAdjacennt").equals("1")) {
                        cbEncroachedAdjacent.setChecked(true);
                    } else {
                        cbEncroachedAdjacent.setChecked(false);
                    }

                    if (object.getString("cbNoViolation").equals("1")) {
                        cbNoViolation.setChecked(true);
                    } else {
                        cbNoViolation.setChecked(false);
                    }

                    if (object.getString("cbUnderConstructionAnyViolation").equals("1")) {
                        cbUnderConstructionAnyViolation.setChecked(true);
                    } else {
                        cbUnderConstructionAnyViolation.setChecked(false);
                    }
                    if (object.getString("cbNoViolence").equals("1")) {
                        cbNoViolence.setChecked(true);
                    } else {
                        cbNoViolence.setChecked(false);
                    }


                    if (object.getString("radioButtonConstructionStatuss").equals("Built-up property in use")) {
                        ((RadioButton) rgConstructionStatus.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonConstructionStatuss").equals("Under construction")) {
                        ((RadioButton) rgConstructionStatus.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonConstructionStatuss").equals("Abandoned construction")) {
                        ((RadioButton) rgConstructionStatus.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonConstructionStatuss").equals("Debris")) {
                        ((RadioButton) rgConstructionStatus.getChildAt(3)).setChecked(true);
                    }


                    if (object.getString("radioButtonBuildingTyppe").equals("RCC Framed Structure")) {
                        ((RadioButton) rgBuildingType.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Load bearing Pillar Beam column")) {
                        ((RadioButton) rgBuildingType.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Ordinary brick wall structure")) {
                        ((RadioButton) rgBuildingType.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Iron trusses & Pillars")) {
                        ((RadioButton) rgBuildingType.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Red stone Patla on Iron girders and brick wall")) {
                        ((RadioButton) rgBuildingType.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Steel Structure")) {
                        ((RadioButton) rgBuildingType.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonBuildingTyppe").equals("Abandoned depleted structure")) {
                        ((RadioButton) rgBuildingType.getChildAt(6)).setChecked(true);
                    }


                    if (object.getString("radioButtonClassOfConstruction").equals("High class")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonClassOfConstruction").equals("First class")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonClassOfConstruction").equals("Second class")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonClassOfConstruction").equals("Third Class")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonClassOfConstruction").equals("Fourth class")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonClassOfConstruction").equals("Abandoned depleted structure")) {
                        ((RadioButton) rgClassOfConstruction.getChildAt(5)).setChecked(true);
                    }


                    if (object.getString("radioButtonAppearanceInternal").equals("Excellent")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Very Good")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Good")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Ordinary")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Average")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Poor")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Under construction")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Old construction")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(7)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("Debris")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(8)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceInternal").equals("No information available since internal survey of the property couldn’t be carried out")) {
                        ((RadioButton) rgAppearanceInternal.getChildAt(9)).setChecked(true);
                    }


                    if (object.getString("radioButtonAppearanceExternal").equals("Excellent")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Very Good")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Good")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Ordinary")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Average")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Poor")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Under construction")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Old construction")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(7)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("Debris")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(8)).setChecked(true);
                    } else if (object.getString("radioButtonAppearanceExternal").equals("No information available since internal survey of the property couldn’t be carried out")) {
                        ((RadioButton) rgAppearanceExternal.getChildAt(9)).setChecked(true);
                    }


             /*   if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Very Good")) {
                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(0)).setChecked(true);
                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Good")) {
                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(1)).setChecked(true);
                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Average")) {
                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(2)).setChecked(true);
                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Poor")) {
                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(3)).setChecked(true);
                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Under construction")) {
                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(4)).setChecked(true);
                }*/
                    //////
                    if (typeSurveyChecker.equalsIgnoreCase("false")) {
                        if (object.getString("radioButtonInteriorDecoration").equals("Excellent")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(0)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Very Good")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(1)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Good")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(2)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Simple")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(3)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Ordinary")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(4)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Average")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(5)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Below Average")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(6)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("No Interior Decoration")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(7)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Simple plain looking structure")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(8)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Beautifully & aesthetically designed interiors")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Good looking interiors. Medium use of interior decoration")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(10)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Modern design & architecture using Green Building Technology")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(11)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Under construction")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(12)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("Under finishing")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(13)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("No Interior Decoration")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(14)).setChecked(true);
                        } else if (object.getString("radioButtonInteriorDecoration").equals("No information available since internal survey of the property couldn’t be carried out")) {
                            ((RadioButton) rgInteriorDecoration.getChildAt(15)).setChecked(true);
                        }
                    } else {
                        ((RadioButton) rgInteriorDecoration.getChildAt(15)).setChecked(true);

                    }


                    if (object.getString("radioButtonKitchen").equals("Simple with no cupboard")) {
                        ((RadioButton) rgKitchen.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("Ordinary with cupboard")) {
                        ((RadioButton) rgKitchen.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("Normal Modular with chimney")) {
                        ((RadioButton) rgKitchen.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("High end Modular with chimney")) {
                        ((RadioButton) rgKitchen.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("Under construction")) {
                        ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("No Kitchen/ Pantry")) {
                        ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonKitchen").equals("No information available since internal survey of the property couldn’t be carried out")) {
                        ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);
                    }
//                if(rgClassOfElectricalsOne.getChildAt(0).isEnabled()) {
                    if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("External")) {
//                        ((RadioButton) rgClassOfElectricalsOne.getChildAt(0)).setChecked(true);
                        elect1 = 0;
                    } else if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("Internal")) {
//                        ((RadioButton) rgClassOfElectricalsOne.getChildAt(1)).setChecked(true);
                        elect1 = 1;
                    } else if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("Mixed (Internal & External)")) {
//                        ((RadioButton) rgClassOfElectricalsOne.getChildAt(2)).setChecked(true);
                        elect1 = 2;
                    }
//                }
//                if(rgClassOfElectricalsTwo.getChildAt(0).isEnabled()) {

                    if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Ordinary fixtures & fittings")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(0)).setChecked(true);
                        elect2 = 0;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Fancy lights")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(1)).setChecked(true);
                        elect2 = 1;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Chandeliers")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(2)).setChecked(true);
                        elect2 = 2;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("High end expensive brand fittings used")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(3)).setChecked(true);
                        elect2 = 3;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("CHigh quality fittings used")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(4)).setChecked(true);
                        elect2 = 4;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Normal quality fittings used")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(5)).setChecked(true);
                        elect2 = 5;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("COrdinary quality fittings used")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(6)).setChecked(true);
                        elect2 = 6;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Poor quality fittings used")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(7)).setChecked(true);
                        elect2 = 7;
                    } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Concealed lightning")) {
                        ((RadioButton) rgClassOfElectricalsTwo.getChildAt(8)).setChecked(true);
                        elect2 = 8;
                    }
//                    if(rgClassOfElectricalsThree.getChildAt(0).isEnabled()) {

                    if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("Poor depleted")) {
//                            ((RadioButton) rgClassOfElectricalsThree.getChildAt(0)).setChecked(true);
                        elect3 = 0;
                    } else if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("Under construction")) {
//                            ((RadioButton) rgClassOfElectricalsThree.getChildAt(1)).setChecked(true);
                        elect3 = 1;
                    } else if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("No information  available since internal survey of the property couldn’t be carried out")) {
//                            ((RadioButton) rgClassOfElectricalsThree.getChildAt(2)).setChecked(true);
                        elect3 = 2;
                    }
//                    }
//                }
//                if(rgClassOfSanitarysOne.getChildAt(0).isEnabled()) {

                    if (object.getString("radioButtonClassOfSanitaryOne").equals("External")) {
//                        ((RadioButton) rgClassOfSanitarysOne.getChildAt(0)).setChecked(true);
                        sanit1 = 0;
                    } else if (object.getString("radioButtonClassOfSanitaryOne").equals("Internal")) {
//                        ((RadioButton) rgClassOfSanitarysOne.getChildAt(1)).setChecked(true);
                        sanit1 = 1;
                    } else if (object.getString("radioButtonClassOfSanitaryOne").equals("Mixed (Internal & External)")) {
//                        ((RadioButton) rgClassOfSanitarysOne.getChildAt(2)).setChecked(true);
                        sanit1 = 2;
                    }

//                }
//                if(rgClassOfSanitarysTwo.getChildAt(0).isEnabled()) {

                    if (object.getString("radioButtonClassOfSanitaryTwo").equals("Excellent")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(0)).setChecked(true);
                        sanit2 = 0;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Very Good")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(1)).setChecked(true);
                        sanit2 = 1;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Good")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(2)).setChecked(true);
                        sanit2 = 2;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Simple")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(3)).setChecked(true);
                        sanit2 = 3;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Average")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(4)).setChecked(true);
                        sanit2 = 4;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("High end expensive brand fittings used")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(5)).setChecked(true);
                        sanit2 = 5;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("High quality fittings used")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(6)).setChecked(true);
                        sanit2 = 6;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Normal quality fittings used")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(7)).setChecked(true);
                        sanit2 = 7;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Ordinary quality fittings used")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(8)).setChecked(true);
                        sanit2 = 8;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Poor quality fittings used")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(9)).setChecked(true);
                        sanit2 = 9;
                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Below average")) {
//                        ((RadioButton) rgClassOfSanitarysTwo.getChildAt(10)).setChecked(true);
                        sanit2 = 10;
                    }
//                }
//                if(rgClassOfSanitarysThree.getChildAt(0).isEnabled()) {


                    try {
                        if (object.getString("radioButtonClassOfSanitaryThree").equals("Poor depleted")) {
//                            ((RadioButton) rgClassOfSanitarysThree.getChildAt(0)).setChecked(true);
                            sanit3 = 0;
                        } else if (object.getString("radioButtonClassOfSanitaryThree").equals("Under construction")) {
//                            ((RadioButton) rgClassOfSanitarysThree.getChildAt(1)).setChecked(true);
                            sanit3 = 1;
                        } else if (object.getString("radioButtonClassOfSanitaryThree").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                            ((RadioButton) rgClassOfSanitarysThree.getChildAt(2)).setChecked(true);
                            sanit3 = 2;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                }

                    if (object.getString("radioButtonFixedWoodenWork").equals("Excellent")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Very Good")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Good")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Simple")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Ordinary")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Average")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Below Average")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(6)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("No wooden work")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(7)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("Under construction")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(true);
                    } else if (object.getString("radioButtonFixedWoodenWork").equals("No information available since internal survey of the property couldn’t be carried out")) {
                        ((RadioButton) rgFixedWoodenWork.getChildAt(9)).setChecked(true);
                    }


                    if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Very Good")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(0)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Good")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(1)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Average")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(2)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Poor")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(3)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("No maintenance carried out in last few months")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(4)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Under construction")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Building requires some maintenance")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(6)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Very old debris structure and hence requires full scale renovation or redevelopment")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(7)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Building has structural issues with cracks are visible in the structure")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(8)).setChecked(true);
                    } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("No information available since full survey of the property couldn’t be carried out")) {
                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(9)).setChecked(true);
                    }


                    // String projectName = object.getString("projectName");
                    // Log.e("!!!projectName", projectName);

//                if (InitiateSurveyForm.surveyTypeCheck == 1) {
////
////                    ((RadioButton) rgClassOfElectricalsThree.getChildAt(2)).setChecked(true);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(0)).setClickable(false);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setClickable(false);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setClickable(false);
//
//
//                }
                }

            }
        }
    }

    private void populateSinner() {

   /*     //Spinner IsLandMerged
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_dropdown_item, arrayInCase);

        spinnerInCaseOfUnderConstruction.setAdapter(adapter);*/

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayFtMtr);
        spinnerHeightFtMtr.setAdapter(spinnerAdapter);

        spinnerAdapter = new SpinnerAdapter(mActivity, arrayInCase);
        spinnerInCaseOfUnderConstruction.setAdapter(spinnerAdapter);

        spinnerInCaseOfUnderConstruction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getSelectedItemPosition() == 5 || adapterView.getSelectedItemPosition() == 6
                        || adapterView.getSelectedItemPosition() == 7 || adapterView.getSelectedItemPosition() == 8) {

                    llUptoSlabLevel.setVisibility(View.VISIBLE);
                } else {
                    llUptoSlabLevel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(mActivity, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

        //if Required then only
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.general_one:
                        ((Dashboard) mActivity).displayView(6);
                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
                        startActivity(intent);*/
                        return true;

                    case R.id.general_two:
                        ((Dashboard) mActivity).displayView(7);
                        return true;

                    case R.id.general_three:
                        ((Dashboard) mActivity).displayView(8);
                        return true;

                    case R.id.general_four:
                        ((Dashboard) mActivity).displayView(9);
                        return true;

                    case R.id.general_five:
                        ((Dashboard) mActivity).displayView(10);
                        return true;

                    case R.id.general_six:
                        ((Dashboard) mActivity).displayView(11);
                        return true;

                    case R.id.general_seven:
                        ((Dashboard) mActivity).displayView(12);
                        return true;

                    case R.id.general_eight:
                        ((Dashboard) mActivity).displayView(13);
                        return true;

                    case R.id.general_nine:
                        ((Dashboard) mActivity).displayView(14);
                        return true;

                    case R.id.general_ten:
                        ((Dashboard) mActivity).displayView(15);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
}

//package com.vis.android.Activities.General.Fragments;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.support.design.widget.Snackbar;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.InputMethodManager;
//import android.widget.AdapterView;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.vis.android.Activities.Dashboard;
//import com.vis.android.Activities.General.SpinnerAdapter;
//import com.vis.android.Common.Constants;
//import com.vis.android.Common.Preferences;
//import com.vis.android.Database.DatabaseController;
//import com.vis.android.Database.TableGeneralForm;
//import com.vis.android.Extras.BaseFragment;
//import com.vis.android.R;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static com.vis.android.Activities.General.Fragments.GeneralForm1.arrayListData;
//import static com.vis.android.Activities.General.Fragments.GeneralForm1.hm;
//import static com.vis.android.Activities.General.Fragments.GeneralForm1.jsonArrayData;
//import static com.vis.android.Activities.General.Fragments.GeneralForm1.surveyTypeCheck;
//
//public class GeneralForm8 extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
//   // RelativeLayout back, dots;
//    LinearLayout llQuesB,llUptoSlabLevel;
//    TextView next, tvPrevious;
//    TextView tvQuesC, tvQuesD, tvQuesE, tvQuesF, tvQuesG, tvQuesH, tvQuesI, tvQuesJ, //tvQuesK,
//            tvQuesL, tvQuesM, tvQuesN, tvQuesO, tvQuesP, tvQuesQ, tvQuesR, tvQuesS, tvQuesT, tvQuesU, tvQuesV, tvQuesW, tvQuesX;
//    Intent intent;
//
//    RadioGroup rgConstructionStatus, rgBuildingType, rgClassOfConstruction, rgAppearanceInternal, rgAppearanceExternal, //rgMaintenanceOfTheBuilding,
//            rgInteriorDecoration, rgKitchen, rgClassOfElectricalOne,rgClassOfElectricalTwo,rgClassOfElectricalThree, rgClassOfSanitaryOne,rgClassOfSanitaryTwo,rgClassOfSanitaryThree, rgFixedWoodenWork, rgMaintenanceOfTheBuilding2;
//
//    EditText etTotalNumberOfFloors, etFloorOnWhich, etRoofHeight, etAgeOfBuilding, etRecentImprovements,etExtentOfViolation,etUptoSlabLevel,
//            etCubicles,etCabins,etNumberOfRooms,etTypeOfUnit,etFlooringAnyOtherType;
//
//    CheckBox cbRcc, cbRbc, cbGiShed, cbTinShed, cbStonePatla, cbSimplePlaster, cbPopPunning, cbPopFalseCeiling, cbCovedRoof, cbNoPlaster,
//            cbDomedRoof, cbBeautifulCarving, cbNoInfoAvailable,cbUnderConstructionFinish, cbMudFlooring, cbBrickFlooring, cbVitrifiedTiles, cbCeramicTiles, cbSimpleMarble, cbMarbleChips, cbMosaic,
//            cbGranite, cbItalianMarble, cbKotaStone, cbWooden, cbPcc, cbImportedMarble, cbPavers, cbChequered, cbBrickTiles, cbVinylFlooring,
//            cbLaminateFlooring, cbLinoleumFlooring, cbNoFlooring, cbUnderConstruction, cbAnyOtherType, cbNoInfoAvaill, cbIntSimplePlastered, cbIntBrickWalls,
//            cbIntDesignerTextured, cbIntPopPunning, cbIntCovedRoof, cbIntVeneerLaminated, cbIntSteelLaminated,cbIntPoorDepleted, cbIntUnderConstruction, cbNInfoIhFinishing,
//            cbExtSimplePlastered, cbExtBrickWalls, cbExtArchitecturally, cbExtBrickTile, cbExtStructuralGlazing, cbExtAluminumComposite, cbExtGlassFacade,
//            cbExtDomb, cbExtPorch,cbExtPoorDepleted, cbExtUnderConstruction, cbJetPump, cbSubmersible, cbJalBoardSupply, cbNoInfoAvailWater, cbMaintenanceIssues, cbFinishingIssues, cbSeepageIssues,
//            cbWaterSupplyIssues, cbElectricalIssues, cbStructuralIssues, cbVisibleCracks, cbRunDownBuilding,cbNoMoreSafe,cbNotAppropriate,
//            cbUnderConstructionDefects,cbNoDefect,cbUnderConstructionAnyViolation,cbNoInfo, cbConstructionDoneWithoutMap, cbConstructionNotAsPer, cbExtraCovered, cbJoinedAdjacent, cbEncroachedAdjacent,cbNoViolation, cbNoViolence,cbUnderConstructionWater;
//
//    int cbRccCheck, cbRbcCheck, cbGiShedCheck, cbTinShedCheck, cbStonePatlaCheck, cbSimplePlasterCheck, cbPopPunningCheck, cbPopFalseCeilingCheck,
//            cbCovedRoofCheck, cbNoPlasterCheck, cbDomedRoofCheck, cbBeautifulCarvingCheck, cbNoInfoAvailableCheck,cbUnderConstructionFinishCheck, cbMudFlooringCheck, cbBrickFlooringCheck,
//            cbVitrifiedTilesCheck, cbCeramicTilesCheck, cbSimpleMarbleCheck, cbMarbleChipsCheck, cbMosaicCheck, cbGraniteCheck,
//            cbItalianMarbleCheck, cbKotaStoneCheck, cbWoodenCheck, cbPccCheck, cbImportedMarbleCheck, cbPaversCheck, cbChequeredCheck,
//            cbBrickTilesCheck, cbVinylFlooringCheck, cbLaminateFlooringCheck, cbLinoleumFlooringCheck, cbNoFlooringCheck, cbUnderConstructionCheck,
//            cbAnyOtherTypeCheck, cbNoInfoAvaillCheck, cbIntSimplePlasteredCheck, cbIntBrickWallsCheck, cbIntDesignerTexturedCheck, cbIntPopPunningCheck, cbIntCovedRoofCheck,
//            cbIntVeneerLaminatedCheck, cbIntSteelLaminatedCheck, cbIntUnderConstructionCheck, cbNInfoIhFinishingCheck, cbExtSimplePlasteredCheck,
//            cbExtBrickWallsCheck, cbExtArchitecturallyCheck, cbExtBrickTileCheck, cbExtStructuralGlazingCheck, cbExtAluminumCompositeCheck,
//            cbExtGlassFacadeCheck, cbExtDombCheck, cbExtPorchCheck,cbExtPoorDepletedCheck,cbIntPoorDepletedCheck, cbExtUnderConstructionCheck, cbJetPumpCheck, cbSubmersibleCheck,
//            cbJalBoardSupplyCheck, cbNoInfoAvailWaterCheck, cbMaintenanceIssuesCheck, cbFinishingIssuesCheck, cbSeepageIssuesCheck, cbWaterSupplyIssuesCheck,
//            cbElectricalIssuesCheck, cbStructuralIssuesCheck, cbVisibleCracksCheck, cbRunDownBuildingCheck,cbNoMoreSafeCheck,cbNotAppropriateCheck,
//            cbUnderConstructionDefectsCheck,cbNoDefectCheck,cbNoInfoCheck, cbConstructionDoneWithoutMapCheck,
//            cbConstructionNotAsPerCheck, cbExtraCoveredCheck, cbJoinedAdjacentCheck, cbEncroachedAdjacentCheck,cbUnderConstructionAnyViolationCheck, cbNoViolenceCheck, cbNoViolationCheck,cbUnderConstructionWaterCheck;
//
//    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
//
//    String[] arrayInCase = {"Choose an item", "Excavation and land development has been started", "Only excavation, land filling & land development completed", "Foundation work in progress",
//            "Foundation work completed", "Super structure work in progress", "Super structure completed", "Slab work in progress", "Slab work completed", "Brick work in progress",
//            "Super structure & Brick work completed", "Plaster work in progress", "Finishing in progress"};
//
//    String[] arrayFtMtr = {"Ft.", "Mtr."};
//
//    Spinner spinnerInCaseOfUnderConstruction,spinnerHeightFtMtr;
//  //  ProgressBar progressbar;
//    SpinnerAdapter spinnerAdapter;
//    Preferences pref;
//
//   // TextView tv_caseheader,tv_caseid,tv_header;
//
//    //RelativeLayout rl_casedetail;
//
//    View v;
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        v = inflater.inflate(R.layout.activity_generalform8, container, false);
//
//        getid(v);
//        setListener();
//        populateSinner();
//
////        tv_header.setVisibility(View.GONE);
////        rl_casedetail.setVisibility(View.VISIBLE);
////        tv_caseheader.setText("General Survey Form");
////        tv_caseid.setText("Case ID: " + pref.get(Constants.case_u_id));
//
////        progressbar.setMax(10);
////        progressbar.setProgress(8);
////       // Arrays.sort(arrayInCase, 1, arrayInCase.length);
//            surveyTypeCheck=InitiateSurveyForm.surveyTypeCheck;
//        try {
//            if (InitiateSurveyForm.surveyTypeCheck == 1){
//                cbNoInfoAvailable.setChecked(true);
//                cbNoInfoAvaill.setChecked(true);
//
//                ((RadioButton) rgAppearanceInternal.getChildAt(7)).setChecked(true);
//                ((RadioButton) rgAppearanceExternal.getChildAt(7)).setChecked(true);
//                ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);
//
//                cbNInfoIhFinishing.setChecked(true);
//                ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);
//                ((RadioButton) rgClassOfElectricalThree.getChildAt(2)).setChecked(true);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//
//                ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//
////                rgClassOfSanitaryThree.setClickable(false);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setEnabled(false);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setEnabled(false);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(3)).setEnabled(false);
////
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setText("help");
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setText("helpA");
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(3)).setText("helpB");
////
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setClickable(false);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setClickable(false);
////                ((RadioButton) rgClassOfSanitaryThree.getChildAt(3)).setClickable(false);
//
//
//
//                cbNoInfoAvailWater.setChecked(true);
//                ((RadioButton) rgFixedWoodenWork.getChildAt(9)).setChecked(true);
//                ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(6)).setChecked(true);
//                cbNoInfo.setChecked(true);
//                cbNoViolence.setChecked(true);
//            }
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//        try {
//            selectDB();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (InitiateSurveyForm.surveyTypeCheck == 1){
//            etExtentOfViolation.setText("No information available");
//        }
//
//        return v;
//    }
//
//    public void getid(View v) {
////        tv_header = findViewById(R.id.tv_header);
////        rl_casedetail = findViewById(R.id.rl_casedetail);
////        tv_caseheader = findViewById(R.id.tv_caseheader);
////        tv_caseid = findViewById(R.id.tv_caseid);
//
//    //    progressbar = (ProgressBar) findViewById(R.id.Progress);
//        pref = new Preferences(mActivity);
//     //   back = findViewById(R.id.rl_back);
//        next = v.findViewById(R.id.next);
//        tvPrevious = v.findViewById(R.id.tvPrevious);
//      //  dropdown = (LinearLayout) v.findViewById(R.id.ll_dropdown);
//      //  dots = (RelativeLayout) v.findViewById(R.id.rl_dots);
//        llQuesB = v.findViewById(R.id.llQuesB);
//        llUptoSlabLevel = v.findViewById(R.id.llUptoSlabLevel);
//
//        //RadioGroup
//        rgConstructionStatus = v.findViewById(R.id.rgConstructionStatus);
//        rgBuildingType = v.findViewById(R.id.rgBuildingType);
//        rgClassOfConstruction = v.findViewById(R.id.rgClassOfConstruction);
//        rgAppearanceInternal = v.findViewById(R.id.rgAppearanceInternal);
//        rgAppearanceExternal = v.findViewById(R.id.rgAppearanceExternal);
//        //rgMaintenanceOfTheBuilding = v.findViewById(R.id.rgMaintenanceOfTheBuilding);
//        rgInteriorDecoration = v.findViewById(R.id.rgInteriorDecoration);
//        rgKitchen = v.findViewById(R.id.rgKitchen);
//        rgClassOfElectricalOne   = v.findViewById(R.id.rgClassOfElectricalOne);
//        rgClassOfElectricalTwo   = v.findViewById(R.id.rgClassOfElectricalTwo);
//        rgClassOfElectricalThree = v.findViewById(R.id.rgClassOfElectricalThree);
//        rgClassOfSanitaryOne   = v.findViewById(R.id.rgClassOfSanitaryOne);
//        rgClassOfSanitaryTwo   = v.findViewById(R.id.rgClassOfSanitaryTwo);
//        rgClassOfSanitaryThree = v.findViewById(R.id.rgClassOfSanitaryThree);
//        rgFixedWoodenWork = v.findViewById(R.id.rgFixedWoodenWork);
//        rgMaintenanceOfTheBuilding2 = v.findViewById(R.id.rgMaintenanceOfTheBuilding2);
//
//        //EditText
//        etTotalNumberOfFloors = v.findViewById(R.id.etTotalNumberOfFloors);
//        etFloorOnWhich = v.findViewById(R.id.etFloorOnWhich);
//
//        etTypeOfUnit = v.findViewById(R.id.etTypeOfUnit);
//        etNumberOfRooms = v.findViewById(R.id.etNumberOfRooms);
//        etCabins = v.findViewById(R.id.etCabins);
//        etCubicles = v.findViewById(R.id.etCubicles);
//        etFlooringAnyOtherType = v.findViewById(R.id.etFlooringAnyOtherType);
//
//        etRoofHeight = v.findViewById(R.id.etRoofHeight);
//        etAgeOfBuilding = v.findViewById(R.id.etAgeOfBuilding);
//        etRecentImprovements = v.findViewById(R.id.etRecentImprovements);
//        etExtentOfViolation = v.findViewById(R.id.etExtentOfViolation);
//        etUptoSlabLevel = v.findViewById(R.id.etUptoSlabLevel);
//
//        //CheckBox
//        cbRcc = v.findViewById(R.id.cbRcc);
//        cbRbc = v.findViewById(R.id.cbRbc);
//        cbGiShed = v.findViewById(R.id.cbGiShed);
//        cbTinShed = v.findViewById(R.id.cbTinShed);
//        cbStonePatla = v.findViewById(R.id.cbStonePatla);
//        cbSimplePlaster = v.findViewById(R.id.cbSimplePlaster);
//        cbPopPunning = v.findViewById(R.id.cbPopPunning);
//        cbPopFalseCeiling = v.findViewById(R.id.cbPopFalseCeiling);
//        cbCovedRoof = v.findViewById(R.id.cbCovedRoof);
//        cbNoPlaster = v.findViewById(R.id.cbNoPlaster);
//        cbDomedRoof = v.findViewById(R.id.cbDomedRoof);
//        cbBeautifulCarving = v.findViewById(R.id.cbBeautifulCarving);
//        cbNoInfoAvailable = v.findViewById(R.id.cbNoInfoAvailable);
//        cbUnderConstructionFinish = v.findViewById(R.id.cbUnderConstructionFinish);
//        cbMudFlooring = v.findViewById(R.id.cbMudFlooring);
//        cbBrickFlooring = v.findViewById(R.id.cbBrickFlooring);
//        cbVitrifiedTiles = v.findViewById(R.id.cbVitrifiedTiles);
//        cbCeramicTiles = v.findViewById(R.id.cbCeramicTiles);
//        cbSimpleMarble = v.findViewById(R.id.cbSimpleMarble);
//        cbMarbleChips = v.findViewById(R.id.cbMarbleChips);
//        cbMosaic = v.findViewById(R.id.cbMosaic);
//        cbGranite = v.findViewById(R.id.cbGranite);
//        cbItalianMarble = v.findViewById(R.id.cbItalianMarble);
//        cbKotaStone = v.findViewById(R.id.cbKotaStone);
//        cbWooden = v.findViewById(R.id.cbWooden);
//        cbPcc = v.findViewById(R.id.cbPcc);
//        cbImportedMarble = v.findViewById(R.id.cbImportedMarble);
//        cbPavers = v.findViewById(R.id.cbPavers);
//        cbChequered = v.findViewById(R.id.cbChequered);
//        cbBrickTiles = v.findViewById(R.id.cbBrickTiles);
//        cbVinylFlooring = v.findViewById(R.id.cbVinylFlooring);
//        cbLaminateFlooring = v.findViewById(R.id.cbLaminateFlooring);
//        cbLinoleumFlooring = v.findViewById(R.id.cbLinoleumFlooring);
//        cbNoFlooring = v.findViewById(R.id.cbNoFlooring);
//        cbUnderConstruction = v.findViewById(R.id.cbUnderConstruction);
//        cbAnyOtherType = v.findViewById(R.id.cbAnyOtherType);
//        cbNoInfoAvaill = v.findViewById(R.id.cbNoInfoAvaill);
//        cbIntSimplePlastered = v.findViewById(R.id.cbIntSimplePlastered);
//        cbIntBrickWalls = v.findViewById(R.id.cbIntBrickWalls);
//        cbIntDesignerTextured = v.findViewById(R.id.cbIntDesignerTextured);
//        cbIntPopPunning = v.findViewById(R.id.cbIntPopPunning);
//        cbIntCovedRoof = v.findViewById(R.id.cbIntCovedRoof);
//        cbIntVeneerLaminated = v.findViewById(R.id.cbIntVeneerLaminated);
//        cbIntSteelLaminated = v.findViewById(R.id.cbIntSteelLaminated);
//        cbIntPoorDepleted = v.findViewById(R.id.cbIntPoorDepleted);
//        cbIntUnderConstruction = v.findViewById(R.id.cbIntUnderConstruction);
//       // cbIntNoSurvey = v.findViewById(R.id.cbIntNoSurvey);
//        cbNInfoIhFinishing = v.findViewById(R.id.cbNInfoIhFinishing);
//        cbExtSimplePlastered = v.findViewById(R.id.cbExtSimplePlastered);
//        cbExtBrickWalls = v.findViewById(R.id.cbExtBrickWalls);
//        cbExtArchitecturally = v.findViewById(R.id.cbExtArchitecturally);
//        cbExtBrickTile = v.findViewById(R.id.cbExtBrickTile);
//        cbExtStructuralGlazing = v.findViewById(R.id.cbExtStructuralGlazing);
//        cbExtAluminumComposite = v.findViewById(R.id.cbExtAluminumComposite);
//        cbExtGlassFacade = v.findViewById(R.id.cbExtGlassFacade);
//        cbExtDomb = v.findViewById(R.id.cbExtDomb);
//        cbExtPorch = v.findViewById(R.id.cbExtPorch);
//        cbExtPoorDepleted = v.findViewById(R.id.cbExtPoorDepleted);
//        cbExtUnderConstruction = v.findViewById(R.id.cbExtUnderConstruction);
//        cbJetPump = v.findViewById(R.id.cbJetPump);
//        cbSubmersible = v.findViewById(R.id.cbSubmersible);
//        cbJalBoardSupply = v.findViewById(R.id.cbJalBoardSupply);
//        cbNoInfoAvailWater = v.findViewById(R.id.cbNoInfoAvailWater);
//        cbMaintenanceIssues = v.findViewById(R.id.cbMaintenanceIssues);
//        cbFinishingIssues = v.findViewById(R.id.cbFinishingIssues);
//        cbSeepageIssues = v.findViewById(R.id.cbSeepageIssues);
//        cbWaterSupplyIssues = v.findViewById(R.id.cbWaterSupplyIssues);
//        cbElectricalIssues = v.findViewById(R.id.cbElectricalIssues);
//        cbStructuralIssues = v.findViewById(R.id.cbStructuralIssues);
//        cbVisibleCracks = v.findViewById(R.id.cbVisibleCracks);
//        cbRunDownBuilding = v.findViewById(R.id.cbRunDownBuilding);
//        cbNoMoreSafe = v.findViewById(R.id.cbNoMoreSafe);
//        cbNotAppropriate = v.findViewById(R.id.cbNotAppropriate);
//        cbUnderConstructionDefects = v.findViewById(R.id.cbUnderConstructionDefects);
//        cbNoDefect = v.findViewById(R.id.cbNoDefect);
//        cbNoInfo = v.findViewById(R.id.cbNoInfo);
//        cbConstructionDoneWithoutMap = v.findViewById(R.id.cbConstructionDoneWithoutMap);
//        cbConstructionNotAsPer = v.findViewById(R.id.cbConstructionNotAsPer);
//        cbExtraCovered = v.findViewById(R.id.cbExtraCovered);
//        cbJoinedAdjacent = v.findViewById(R.id.cbJoinedAdjacent);
//        cbEncroachedAdjacent = v.findViewById(R.id.cbEncroachedAdjacent);
//        cbNoViolation = v.findViewById(R.id.cbNoViolation);
//        cbUnderConstructionAnyViolation = v.findViewById(R.id.cbUnderConstructionAnyViolation);
//        cbNoViolence = v.findViewById(R.id.cbNoViolence);
//        cbUnderConstructionWater = v.findViewById(R.id.cbUnderConstructionWater);
//
//        spinnerInCaseOfUnderConstruction = v.findViewById(R.id.spinnerInCaseOfUnderConstruction);
//        spinnerHeightFtMtr = v.findViewById(R.id.spinnerHeightFtMtr);
//
//        tvQuesC= v.findViewById(R.id.tvQuesC);
//        tvQuesD= v.findViewById(R.id.tvQuesD);
//        tvQuesE= v.findViewById(R.id.tvQuesE);
//        tvQuesF= v.findViewById(R.id.tvQuesF);
//        tvQuesG= v.findViewById(R.id.tvQuesG);
//        tvQuesH= v.findViewById(R.id.tvQuesH);
//        tvQuesI= v.findViewById(R.id.tvQuesI);
//        tvQuesJ= v.findViewById(R.id.tvQuesJ);
//     //   tvQuesK= v.findViewById(R.id.tvQuesK);
//        tvQuesL= v.findViewById(R.id.tvQuesL);
//        tvQuesM= v.findViewById(R.id.tvQuesM);
//        tvQuesN= v.findViewById(R.id.tvQuesN);
//        tvQuesO= v.findViewById(R.id.tvQuesO);
//        tvQuesP= v.findViewById(R.id.tvQuesP);
//        tvQuesQ= v.findViewById(R.id.tvQuesQ);
//        tvQuesR= v.findViewById(R.id.tvQuesR);
//        tvQuesS= v.findViewById(R.id.tvQuesS);
//        tvQuesT= v.findViewById(R.id.tvQuesT);
//        tvQuesU= v.findViewById(R.id.tvQuesU);
//        tvQuesV= v.findViewById(R.id.tvQuesV);
//        tvQuesW= v.findViewById(R.id.tvQuesW);
//        tvQuesX= v.findViewById(R.id.tvQuesX);
//
//
//    }
//
//    public void setListener() {
//     //   back.setOnClickListener(this);
//        next.setOnClickListener(this);
//        tvPrevious.setOnClickListener(this);
//      //  dots.setOnClickListener(this);
//        cbAnyOtherType.setOnCheckedChangeListener(this);
//
//        rgConstructionStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                RadioButton radioButton = (RadioButton) v.findViewById(i);
//                int pos = radioGroup.indexOfChild(radioButton);
//
//                if (radioButton.isChecked()){
//                    if (pos == 1){
//                        llQuesB.setVisibility(View.VISIBLE);
//
//                        tvQuesC.setText(R.string.c_total_number_of_floors_in_the_building_);
//                        tvQuesD.setText(R.string.d_floor_on_which_);
//                        tvQuesE.setText(R.string.e_type_of_unit_n_);
//                        tvQuesF.setText(R.string.f_building_type_);
//                        tvQuesG.setText(R.string.g_class_of_construction_);
//                        tvQuesH.setText(R.string.h_roof_);
//                        tvQuesI.setText(R.string.i_flooring_);
//                        tvQuesJ.setText(R.string.j_appearance_condition_of_the_building_);
//                       // tvQuesK.setText(R.string.k_maintenance_of_the_building_);
//                        tvQuesL.setText(R.string.l_interior_decoration_);
//                        tvQuesM.setText(R.string.m_interior_finishing_);
//                        tvQuesN.setText(R.string.n_exterior_finishing_);
//                        tvQuesO.setText(R.string.o_kitchen_pantry_);
//                        tvQuesP.setText(R.string.p_class_of_electrical_fittings_);
//                        tvQuesQ.setText(R.string.q_class_of_sanitary_plumbing_amp_water_supply_fittings_);
//                        tvQuesR.setText(R.string.r_water_arrangements_);
//                        tvQuesS.setText(R.string.s_fixed_wooden_work_);
//                        tvQuesT.setText(R.string.t_age_of_building_recent_improvements_renovations_done_);
//                        tvQuesU.setText(R.string.u_maintenance_of_the_building_);
//                        tvQuesV.setText(R.string.v_any_defects_in_the_building_);
//                        tvQuesW.setText(R.string.w_any_violation_done_in_the_property_);
//                        tvQuesX.setText(R.string.x_extent_of_violation_);
//
//                        cbUnderConstruction.setChecked(true);
//                        ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(true);
//                        ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(true);
//                        ((RadioButton) rgInteriorDecoration.getChildAt(8)).setChecked(true);
//                        cbIntUnderConstruction.setChecked(true);
//                        cbExtUnderConstruction.setChecked(true);
//                        ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
//                        ((RadioButton) rgClassOfElectricalThree.getChildAt(1)).setChecked(true);
////                        if(surveyTypeCheck==0) {
//                            ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setChecked(true);
////                        }
//                    cbUnderConstructionWater.setChecked(true);
//                        ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(true);
//                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(true);
//                        cbUnderConstructionDefects.setChecked(true);
//                        cbUnderConstructionAnyViolation.setChecked(true);
//                        cbUnderConstructionFinish.setChecked(true);
//
//
//                        /////////
//                        cbNoInfoAvailable.setChecked(false);
//                        cbNoInfoAvaill.setChecked(false);
//
////                        ((RadioButton) rgAppearanceInternal.getChildAt(7)).setChecked(true);
////                        ((RadioButton) rgAppearanceExternal.getChildAt(7)).setChecked(true);
////                        ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);
//
//                        cbNInfoIhFinishing.setChecked(false);
////                        ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);
////                        ((RadioButton) rgClassOfElectricalThree.getChildAt(2)).setChecked(true);
////                        ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//
//                        cbNoInfoAvailWater.setChecked(false);
////                        ((RadioButton) rgFixedWoodenWork.getChildAt(9)).setChecked(true);
////                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(6)).setChecked(true);
//                        cbNoInfo.setChecked(false);
//                        cbNoViolence.setChecked(false);
//
//                        ////
//
//
//
//
//                        rgClassOfElectricalOne.clearCheck();
//                        rgClassOfElectricalTwo.clearCheck();
//
//                       /* for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
//                        }*/
//
//                        for (int k = 0; k < rgClassOfElectricalOne.getChildCount(); k++) {
//                            rgClassOfElectricalOne.getChildAt(k).setEnabled(false);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalTwo.getChildCount(); k++) {
//                            rgClassOfElectricalTwo.getChildAt(k).setEnabled(false);
//                        }
//
//
//                        rgClassOfSanitaryOne.clearCheck();
//                        rgClassOfSanitaryTwo.clearCheck();
//
//                       /* for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
//                        }*/
//
//                        for (int k = 0; k < rgClassOfSanitaryOne.getChildCount(); k++) {
//                            rgClassOfSanitaryOne.getChildAt(k).setEnabled(false);
//                        }
//
//                        for (int k = 0; k < rgClassOfSanitaryTwo.getChildCount(); k++) {
//                            rgClassOfSanitaryTwo.getChildAt(k).setEnabled(false);
//                        }
//
//
//
//                    }else {
//                        llQuesB.setVisibility(View.GONE);
//
//                        cbUnderConstruction.setChecked(false);
//                        ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(false);
//                        ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(false);
//                        ((RadioButton) rgInteriorDecoration.getChildAt(8)).setChecked(false);
//                        cbIntUnderConstruction.setChecked(false);
//                        cbExtUnderConstruction.setChecked(false);
//                        ((RadioButton) rgKitchen.getChildAt(5)).setChecked(false);
//                        ((RadioButton) rgClassOfElectricalThree.getChildAt(1)).setChecked(false);
//                        ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setChecked(false);
//                        cbUnderConstructionWater.setChecked(false);
//                        ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(false);
//                        ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(false);
//                        cbUnderConstructionDefects.setChecked(false);
//                        cbUnderConstructionAnyViolation.setChecked(false);
//                        cbUnderConstructionFinish.setChecked(false);
//
//                        rgClassOfElectricalThree.clearCheck();
//                        rgClassOfElectricalOne.clearCheck();
//                        rgClassOfElectricalTwo.clearCheck();
//
//                        for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                            rgClassOfElectricalThree.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalOne.getChildCount(); k++) {
//                            rgClassOfElectricalOne.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfElectricalTwo.getChildCount(); k++) {
//                            rgClassOfElectricalTwo.getChildAt(k).setEnabled(true);
//                        }
//
////                        if(InitiateSurveyForm.surveyTypeCheck==0) {
//                            rgClassOfSanitaryThree.clearCheck();
////                        }
//                    rgClassOfSanitaryOne.clearCheck();
//                        rgClassOfSanitaryTwo.clearCheck();
////                        if(surveyTypeCheck==0) {
//                            for (int k = 0; k < rgClassOfSanitaryThree.getChildCount(); k++) {
//                                rgClassOfSanitaryThree.getChildAt(k).setEnabled(true);
//                            }
////                        }
//                        for (int k = 0; k < rgClassOfSanitaryOne.getChildCount(); k++) {
//                            rgClassOfSanitaryOne.getChildAt(k).setEnabled(true);
//                        }
//
//                        for (int k = 0; k < rgClassOfSanitaryTwo.getChildCount(); k++) {
//                            rgClassOfSanitaryTwo.getChildAt(k).setEnabled(true);
//                        }
//
//
//                        tvQuesC.setText(R.string.c_total_number_of_floors_in_the_building);
//                        tvQuesD.setText(R.string.d_floor_on_which);
//                        tvQuesE.setText(R.string.e_type_of_unit_n);
//                        tvQuesF.setText(R.string.f_building_type);
//                        tvQuesG.setText(R.string.g_class_of_construction);
//                        tvQuesH.setText(R.string.h_roof);
//                        tvQuesI.setText(R.string.i_flooring);
//                        tvQuesJ.setText(R.string.j_appearance_condition_of_the_building);
//                       // tvQuesK.setText(R.string.k_maintenance_of_the_building);
//                        tvQuesL.setText(R.string.l_interior_decoration);
//                        tvQuesM.setText(R.string.m_interior_finishing);
//                        tvQuesN.setText(R.string.n_exterior_finishing);
//                        tvQuesO.setText(R.string.o_kitchen_pantry);
//                        tvQuesP.setText(R.string.p_class_of_electrical_fittings);
//                        tvQuesQ.setText(R.string.q_class_of_sanitary_plumbing_amp_water_supply_fittings);
//                        tvQuesR.setText(R.string.r_water_arrangements);
//                        tvQuesS.setText(R.string.s_fixed_wooden_work);
//                        tvQuesT.setText(R.string.t_age_of_building_recent_improvements_renovations_done);
//                        tvQuesU.setText(R.string.u_maintenance_of_the_building);
//                        tvQuesV.setText(R.string.v_any_defects_in_the_building);
//                        tvQuesW.setText(R.string.w_any_violation_done_in_the_property);
//                        tvQuesX.setText(R.string.x_extent_of_violation);
//
//                    }
//                }
//
//            }
//        });
//
//
//        rgClassOfElectricalOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfElectricalTwo.getCheckedRadioButtonId() != -1){
//
//                    rgClassOfElectricalThree.clearCheck();
//
//                    for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                        rgClassOfElectricalThree.getChildAt(k).setEnabled(false);
//                    }
//                }
//
//            }
//        });
//
//        rgClassOfElectricalTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfElectricalOne.getCheckedRadioButtonId() != -1){
//
//                    rgClassOfElectricalThree.clearCheck();
//
//                    for (int k = 0; k < rgClassOfElectricalThree.getChildCount(); k++) {
//                        rgClassOfElectricalThree.getChildAt(k).setEnabled(false);
//                    }
//
//                }
//
//            }
//        });
//
//        rgClassOfElectricalThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfElectricalThree.getCheckedRadioButtonId() != -1){
//
//                    rgClassOfElectricalOne.clearCheck();
//                    rgClassOfElectricalTwo.clearCheck();
//
//                    for (int k = 0; k < rgClassOfElectricalOne.getChildCount(); k++) {
//                        rgClassOfElectricalOne.getChildAt(k).setEnabled(false);
//                    }
//
//                    for (int k = 0; k < rgClassOfElectricalTwo.getChildCount(); k++) {
//                        rgClassOfElectricalTwo.getChildAt(k).setEnabled(false);
//                    }
//
//                }
//
//
//            }
//        });
//
//
//        rgClassOfSanitaryOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfSanitaryTwo.getCheckedRadioButtonId() != -1 &&(surveyTypeCheck==0)){
//
//                    rgClassOfSanitaryThree.clearCheck();
//
//                    for (int k = 0; k < rgClassOfSanitaryThree.getChildCount(); k++) {
//                        rgClassOfSanitaryThree.getChildAt(k).setEnabled(false);
//                    }
//                }
//
//            }
//        });
//
//        rgClassOfSanitaryTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfSanitaryOne.getCheckedRadioButtonId() != -1 &&(surveyTypeCheck==0)){
//
//                    rgClassOfSanitaryThree.clearCheck();
//
//                    for (int k = 0; k < rgClassOfSanitaryThree.getChildCount(); k++) {
//                        rgClassOfSanitaryThree.getChildAt(k).setEnabled(false);
//                    }
//
//                }
//
//            }
//        });
//
//        rgClassOfSanitaryThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//
//                if (rgClassOfSanitaryThree.getCheckedRadioButtonId() != -1){
//
//                    rgClassOfSanitaryOne.clearCheck();
//                    rgClassOfSanitaryTwo.clearCheck();
//
//                    for (int k = 0; k < rgClassOfSanitaryOne.getChildCount(); k++) {
//                        rgClassOfSanitaryOne.getChildAt(k).setEnabled(false);
//                    }
//
//                    for (int k = 0; k < rgClassOfSanitaryTwo.getChildCount(); k++) {
//                        rgClassOfSanitaryTwo.getChildAt(k).setEnabled(false);
//                    }
//
//                }
//
//
//            }
//        });
//
//    }
//
//    @Override
//    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//        switch (compoundButton.getId()){
//            case R.id.cbAnyOtherType:
//                if (isChecked){
//                    etFlooringAnyOtherType.setVisibility(View.VISIBLE);
//                }else {
//                    etFlooringAnyOtherType.setVisibility(View.GONE);
//                }
//                break;
//
//        }
//    }
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//       /*     case R.id.rl_back:
//                onBackPressed();
//                break;*/
//
//            case R.id.tvPrevious:
//               /* intent = new Intent(GeneralForm8.this, GeneralForm7.class);
//                startActivity(intent);*/
//               // ((Dashboard)mActivity).displayView(12);
//                mActivity.onBackPressed();
//                break;
//
//            case R.id.next:
//                validation();
//                break;
//
//          /*  case R.id.rl_dots:
//                if (dropdown.getVisibility() == View.GONE) {
//                    showPopup(view);
//
//                } else {
//
//                }
//                break;*/
//        }
//    }
//
//
//    private void validation() {
//        if (rgConstructionStatus.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Construction Status", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvConstruction);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (llQuesB.getVisibility()==View.VISIBLE && spinnerInCaseOfUnderConstruction.getSelectedItemPosition() == 0) {
//            Snackbar snackbar = Snackbar.make(next, "Please Choose an item", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvInCaseOf);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        }
//        else if (llUptoSlabLevel.getVisibility() == View.VISIBLE && etUptoSlabLevel.getText().toString().isEmpty()){
//
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Upto Slab Level", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etUptoSlabLevel.requestFocus();
//        }
//        else if (etTotalNumberOfFloors.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Total Number of Floors in the Building", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etTotalNumberOfFloors.requestFocus();
//        }
//        else if (llUptoSlabLevel.getVisibility() == View.VISIBLE &&
//                Integer.parseInt(etTotalNumberOfFloors.getText().toString())<Integer.parseInt(etUptoSlabLevel.getText().toString())){
//
//        //    if (Integer.parseInt(etTotalNumberOfFloors.getText().toString())<Integer.parseInt(etUptoSlabLevel.getText().toString()))
//            {
//                etTotalNumberOfFloors.setError("Total number of floors in the building cannot be less than the value filled in Upto slab level above");
//               // Toast.makeText(this, "Total number of floors in the building cannot be less than the value filled in Upto slab level above", Toast.LENGTH_SHORT).show();
//                etTotalNumberOfFloors.requestFocus();
//            }
//        }
//        else if (etFloorOnWhich.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Floors on which subject property is situated", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etFloorOnWhich.requestFocus();
//        }
//        else if (Integer.parseInt(etFloorOnWhich.getText().toString())>Integer.parseInt(etTotalNumberOfFloors.getText().toString())){
//            etFloorOnWhich.setError("Floor on which subject property is situated cannot be greater than the value filled in total number of floors in the building above");
//            // Toast.makeText(this, "Total number of floors in the building cannot be less than the value filled in Upto slab level above", Toast.LENGTH_SHORT).show();
//            etFloorOnWhich.requestFocus();
//        }
//
//        else if (etTypeOfUnit.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Type of Unit", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etTypeOfUnit.requestFocus();
//        } else if (etNumberOfRooms.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Number of Rooms", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etNumberOfRooms.requestFocus();
//        } else if (etCabins.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Cabins", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etCabins.requestFocus();
//        } else if (etCubicles.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Cubicles", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etCubicles.requestFocus();
//        }
//
//        else if (rgBuildingType.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Building Type", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesF);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgClassOfConstruction.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Construction", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesG);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbRcc.isChecked()
//                && !cbRbc.isChecked()
//                && !cbGiShed.isChecked()
//                && !cbTinShed.isChecked()
//                && !cbStonePatla.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Make", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvRoofMake);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (etRoofHeight.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Roof Height", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etRoofHeight.requestFocus();
//        } else if (!cbSimplePlaster.isChecked()
//                && !cbPopPunning.isChecked()
//                && !cbPopFalseCeiling.isChecked()
//                && !cbCovedRoof.isChecked()
//                && !cbNoPlaster.isChecked()
//                && !cbDomedRoof.isChecked()
//                && !cbBeautifulCarving.isChecked()
//                && !cbUnderConstructionFinish.isChecked()
//                && !cbNoInfoAvailable.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Roof Finish", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvFinish);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbMudFlooring.isChecked()
//                && !cbBrickFlooring.isChecked()
//                && !cbVitrifiedTiles.isChecked()
//                && !cbCeramicTiles.isChecked()
//                && !cbSimpleMarble.isChecked()
//                && !cbMarbleChips.isChecked()
//                && !cbMosaic.isChecked()
//                && !cbGranite.isChecked()
//                && !cbItalianMarble.isChecked()
//                && !cbKotaStone.isChecked()
//                && !cbWooden.isChecked()
//                && !cbPcc.isChecked()
//                && !cbImportedMarble.isChecked()
//                && !cbPavers.isChecked()
//                && !cbChequered.isChecked()
//                && !cbBrickTiles.isChecked()
//                && !cbVinylFlooring.isChecked()
//                && !cbLaminateFlooring.isChecked()
//                && !cbLinoleumFlooring.isChecked()
//                && !cbNoFlooring.isChecked()
//                && !cbUnderConstruction.isChecked()
//                && !cbAnyOtherType.isChecked()
//                && !cbNoInfoAvaill.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Flooring", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesI);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgAppearanceInternal.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance Internal", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvAppInt);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgAppearanceExternal.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Appearance External", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvAppExt);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } /*else if (rgMaintenanceOfTheBuilding.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesK);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } */else if (rgInteriorDecoration.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Decoration", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesL);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbIntSimplePlastered.isChecked()
//                && !cbIntBrickWalls.isChecked()
//                && !cbIntDesignerTextured.isChecked()
//                && !cbIntPopPunning.isChecked()
//                && !cbIntCovedRoof.isChecked()
//                && !cbIntVeneerLaminated.isChecked()
//                && !cbIntSteelLaminated.isChecked()
//                && !cbIntUnderConstruction.isChecked()
//                && !cbIntPoorDepleted.isChecked()
//                && !cbNInfoIhFinishing.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesM);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbExtSimplePlastered.isChecked()
//                && !cbExtBrickWalls.isChecked()
//                && !cbExtArchitecturally.isChecked()
//                && !cbExtBrickTile.isChecked()
//                && !cbExtStructuralGlazing.isChecked()
//                && !cbExtAluminumComposite.isChecked()
//                && !cbExtGlassFacade.isChecked()
//                && !cbExtDomb.isChecked()
//                && !cbExtPorch.isChecked()
//                && !cbExtPoorDepleted.isChecked()
//                && !cbExtUnderConstruction.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesN);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgKitchen.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Kitchen/ Pantry", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesO);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgClassOfElectricalOne.getCheckedRadioButtonId() == -1 && rgClassOfElectricalThree.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesP);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//        }else if (rgClassOfElectricalTwo.getCheckedRadioButtonId() == -1 && rgClassOfElectricalThree.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesP);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//        }else if (rgClassOfElectricalThree.getCheckedRadioButtonId() == -1 &&
//                rgClassOfElectricalTwo.getCheckedRadioButtonId() == -1 &&
//                rgClassOfElectricalOne.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Electrical fittings", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesP);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//        }
//
//        else if ((rgClassOfSanitaryOne.getCheckedRadioButtonId() == -1
//                || rgClassOfSanitaryTwo.getCheckedRadioButtonId() == -1)
//                && rgClassOfSanitaryThree.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary Section 1 and Section 2", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesQ);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        }
//
//        else if (rgClassOfSanitaryThree.getCheckedRadioButtonId() == -1 &&
//                (rgClassOfSanitaryOne.getCheckedRadioButtonId() == -1
//                || rgClassOfSanitaryTwo.getCheckedRadioButtonId() == -1)) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary Section 3", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesQ);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbJetPump.isChecked()
//                && !cbSubmersible.isChecked()
//                && !cbJalBoardSupply.isChecked()
//                && !cbNoInfoAvailWater.isChecked()
//                && !cbUnderConstructionWater.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Interior Finishing", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesR);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (rgFixedWoodenWork.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Class of Sanitary", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesS);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (etAgeOfBuilding.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Age of Building", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etAgeOfBuilding.requestFocus();
//        }  else if (etRecentImprovements.getText().toString().isEmpty()) {
//            Snackbar snackbar = Snackbar.make(next, "Please Enter Recent Improvements/ Renovations done", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            etRecentImprovements.requestFocus();
//        } else if (rgMaintenanceOfTheBuilding2.getCheckedRadioButtonId() == -1) {
//            Snackbar snackbar = Snackbar.make(next, "Please Select Maintenance of the Building", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesU);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbMaintenanceIssues.isChecked()
//                && !cbFinishingIssues.isChecked()
//                && !cbSeepageIssues.isChecked()
//                && !cbWaterSupplyIssues.isChecked()
//                && !cbElectricalIssues.isChecked()
//                && !cbStructuralIssues.isChecked()
//                && !cbVisibleCracks.isChecked()
//                && !cbRunDownBuilding.isChecked()
//                && !cbNoMoreSafe.isChecked()
//                && !cbNotAppropriate.isChecked()
//                && !cbUnderConstructionDefects.isChecked()
//                && !cbNoDefect.isChecked()
//                && !cbNoInfo.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Any defects in the building", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesV);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else if (!cbConstructionDoneWithoutMap.isChecked()
//                && !cbConstructionNotAsPer.isChecked()
//                && !cbExtraCovered.isChecked()
//                && !cbJoinedAdjacent.isChecked()
//                && !cbEncroachedAdjacent.isChecked()
//                && !cbUnderConstructionAnyViolation.isChecked()
//                && !cbNoViolation.isChecked()
//                && !cbNoViolence.isChecked()) {
//            //Toast.makeText(this, "Please Select Location Consideration of the Society", Toast.LENGTH_SHORT).show();
//            Snackbar snackbar = Snackbar.make(next, "Please Select Any Violation done in the property", Snackbar.LENGTH_SHORT);
//            snackbar.show();
//            View targetView = v.findViewById(R.id.tvQuesW);
//            targetView.getParent().requestChildFocus(targetView, targetView);
//
//        } else {
//            putIntoHm();
//            ((Dashboard)mActivity).displayView(14);
//            return;
//        }
//    }
//
//    public void putIntoHm() {
//
//        String inCaseOfUnderConstruction = String.valueOf(spinnerInCaseOfUnderConstruction.getSelectedItemPosition());
//        hm.put("inCaseOfUnderConstructionSpinner", inCaseOfUnderConstruction);
//        hm.put("totalNoOfFloors", etTotalNumberOfFloors.getText().toString());
//        hm.put("floorOnWhich", etFloorOnWhich.getText().toString());
//
//        hm.put("typeOfUnitt", etTypeOfUnit.getText().toString());
//        hm.put("numberOfRooms", etNumberOfRooms.getText().toString());
//        hm.put("cabins", etCabins.getText().toString());
//        hm.put("cubicles", etCubicles.getText().toString());
//
//        hm.put("roofHeight", etRoofHeight.getText().toString());
//        hm.put("ageOfBuildinggg", etAgeOfBuilding.getText().toString());
//        hm.put("recentImprovements", etRecentImprovements.getText().toString());
//        hm.put("extentOfViolation", etExtentOfViolation.getText().toString());
//        if (llUptoSlabLevel.getVisibility() == View.VISIBLE){
//            hm.put("uptoSlabLevel", etUptoSlabLevel.getText().toString());
//        }else {
//            hm.put("uptoSlabLevel", "");
//        }
//
//
//        if (cbRcc.isChecked()) {
//            cbRccCheck = 1;
//            hm.put("cbRcc", String.valueOf(cbRccCheck));
//        } else {
//            cbRccCheck = 0;
//            hm.put("cbRcc", String.valueOf(cbRccCheck));
//        }
//        if (cbRbc.isChecked()) {
//            cbRbcCheck = 1;
//            hm.put("cbRbc", String.valueOf(cbRbcCheck));
//        } else {
//            cbRbcCheck = 0;
//            hm.put("cbRbc", String.valueOf(cbRbcCheck));
//        }
//        if (cbGiShed.isChecked()) {
//            cbGiShedCheck = 1;
//            hm.put("cbGiShed", String.valueOf(cbGiShedCheck));
//        } else {
//            cbGiShedCheck = 0;
//            hm.put("cbGiShed", String.valueOf(cbGiShedCheck));
//        }
//        if (cbTinShed.isChecked()) {
//            cbTinShedCheck = 1;
//            hm.put("cbTinShed", String.valueOf(cbTinShedCheck));
//        } else {
//            cbTinShedCheck = 0;
//            hm.put("cbTinShed", String.valueOf(cbTinShedCheck));
//        }
//        if (cbStonePatla.isChecked()) {
//            cbStonePatlaCheck = 1;
//            hm.put("cbStonePatla", String.valueOf(cbStonePatlaCheck));
//        } else {
//            cbStonePatlaCheck = 0;
//            hm.put("cbStonePatla", String.valueOf(cbStonePatlaCheck));
//        }
//
//
//        if (cbSimplePlaster.isChecked()) {
//            cbSimplePlasterCheck = 1;
//            hm.put("cbSimplePlaster", String.valueOf(cbSimplePlasterCheck));
//        } else {
//            cbSimplePlasterCheck = 0;
//            hm.put("cbSimplePlaster", String.valueOf(cbSimplePlasterCheck));
//        }
//        if (cbPopPunning.isChecked()) {
//            cbPopPunningCheck = 1;
//            hm.put("cbPopPunning", String.valueOf(cbPopPunningCheck));
//        } else {
//            cbPopPunningCheck = 0;
//            hm.put("cbPopPunning", String.valueOf(cbPopPunningCheck));
//        }
//        if (cbPopFalseCeiling.isChecked()) {
//            cbPopFalseCeilingCheck = 1;
//            hm.put("cbPopFalseCeiling", String.valueOf(cbPopFalseCeilingCheck));
//        } else {
//            cbPopFalseCeilingCheck = 0;
//            hm.put("cbPopFalseCeiling", String.valueOf(cbPopFalseCeilingCheck));
//        }
//        if (cbCovedRoof.isChecked()) {
//            cbCovedRoofCheck = 1;
//            hm.put("cbCovedRoof", String.valueOf(cbCovedRoofCheck));
//        } else {
//            cbCovedRoofCheck = 0;
//            hm.put("cbCovedRoof", String.valueOf(cbCovedRoofCheck));
//        }
//        if (cbNoPlaster.isChecked()) {
//            cbNoPlasterCheck = 1;
//            hm.put("cbNoPlaster", String.valueOf(cbNoPlasterCheck));
//        } else {
//            cbNoPlasterCheck = 0;
//            hm.put("cbNoPlaster", String.valueOf(cbNoPlasterCheck));
//        }
//        if (cbDomedRoof.isChecked()) {
//            cbDomedRoofCheck = 1;
//            hm.put("cbDomedRoof", String.valueOf(cbDomedRoofCheck));
//        } else {
//            cbDomedRoofCheck = 0;
//            hm.put("cbDomedRoof", String.valueOf(cbDomedRoofCheck));
//        }
//        if (cbBeautifulCarving.isChecked()) {
//            cbBeautifulCarvingCheck = 1;
//            hm.put("cbBeautifulCarving", String.valueOf(cbBeautifulCarvingCheck));
//        } else {
//            cbBeautifulCarvingCheck = 0;
//            hm.put("cbBeautifulCarving", String.valueOf(cbBeautifulCarvingCheck));
//        }
//        if (cbNoInfoAvailable.isChecked()) {
//            cbNoInfoAvailableCheck = 1;
//            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
//        } else {
//            cbNoInfoAvailableCheck = 0;
//            hm.put("cbNoInfoAvailable", String.valueOf(cbNoInfoAvailableCheck));
//        }
//
//        if (cbUnderConstructionFinish.isChecked()) {
//            cbUnderConstructionFinishCheck = 1;
//            hm.put("cbUnderConstructionFinish", String.valueOf(cbUnderConstructionFinishCheck));
//        } else {
//            cbUnderConstructionFinishCheck = 0;
//            hm.put("cbUnderConstructionFinish", String.valueOf(cbUnderConstructionFinishCheck));
//        }
//
//
//        if (cbMudFlooring.isChecked()) {
//            cbMudFlooringCheck = 1;
//            hm.put("cbMudFlooring", String.valueOf(cbMudFlooringCheck));
//        } else {
//            cbMudFlooringCheck = 0;
//            hm.put("cbMudFlooring", String.valueOf(cbMudFlooringCheck));
//        }
//        if (cbBrickFlooring.isChecked()) {
//            cbBrickFlooringCheck = 1;
//            hm.put("cbBrickFlooring", String.valueOf(cbBrickFlooringCheck));
//        } else {
//            cbBrickFlooringCheck = 0;
//            hm.put("cbBrickFlooring", String.valueOf(cbBrickFlooringCheck));
//        }
//        if (cbVitrifiedTiles.isChecked()) {
//            cbVitrifiedTilesCheck = 1;
//            hm.put("cbVitrifiedTiles", String.valueOf(cbVitrifiedTilesCheck));
//        } else {
//            cbVitrifiedTilesCheck = 0;
//            hm.put("cbVitrifiedTiles", String.valueOf(cbVitrifiedTilesCheck));
//        }
//        if (cbCeramicTiles.isChecked()) {
//            cbCeramicTilesCheck = 1;
//            hm.put("cbCeramicTiles", String.valueOf(cbCeramicTilesCheck));
//        } else {
//            cbCeramicTilesCheck = 0;
//            hm.put("cbCeramicTiles", String.valueOf(cbCeramicTilesCheck));
//        }
//
//
//        if (cbSimpleMarble.isChecked()) {
//            cbSimpleMarbleCheck = 1;
//            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
//        } else {
//            cbSimpleMarbleCheck = 0;
//            hm.put("cbSimpleMarble", String.valueOf(cbSimpleMarbleCheck));
//        }
//        if (cbMarbleChips.isChecked()) {
//            cbMarbleChipsCheck = 1;
//            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
//        } else {
//            cbMarbleChipsCheck = 0;
//            hm.put("cbMarbleChips", String.valueOf(cbMarbleChipsCheck));
//        }
//        if (cbMosaic.isChecked()) {
//            cbMosaicCheck = 1;
//            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
//        } else {
//            cbMosaicCheck = 0;
//            hm.put("cbMosaic", String.valueOf(cbMosaicCheck));
//        }
//        if (cbGranite.isChecked()) {
//            cbGraniteCheck = 1;
//            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
//        } else {
//            cbGraniteCheck = 0;
//            hm.put("cbGranite", String.valueOf(cbGraniteCheck));
//        }
//        if (cbItalianMarble.isChecked()) {
//            cbItalianMarbleCheck = 1;
//            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
//        } else {
//            cbItalianMarbleCheck = 0;
//            hm.put("cbItalianMarble", String.valueOf(cbItalianMarbleCheck));
//        }
//
//
//        if (cbKotaStone.isChecked()) {
//            cbKotaStoneCheck = 1;
//            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
//        } else {
//            cbKotaStoneCheck = 0;
//            hm.put("cbKotaStone", String.valueOf(cbKotaStoneCheck));
//        }
//        if (cbWooden.isChecked()) {
//            cbWoodenCheck = 1;
//            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
//        } else {
//            cbWoodenCheck = 0;
//            hm.put("cbWooden", String.valueOf(cbWoodenCheck));
//        }
//
//        if (cbPcc.isChecked()) {
//            cbPccCheck = 1;
//            hm.put("cbPccc", String.valueOf(cbPccCheck));
//        } else {
//            cbPccCheck = 0;
//            hm.put("cbPccc", String.valueOf(cbPccCheck));
//        }
//        if (cbImportedMarble.isChecked()) {
//            cbImportedMarbleCheck = 1;
//            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
//        } else {
//            cbImportedMarbleCheck = 0;
//            hm.put("cbImportedMarble", String.valueOf(cbImportedMarbleCheck));
//        }
//        if (cbPavers.isChecked()) {
//            cbPaversCheck = 1;
//            hm.put("cbPavers", String.valueOf(cbPaversCheck));
//        } else {
//            cbPaversCheck = 0;
//            hm.put("cbPavers", String.valueOf(cbPaversCheck));
//        }
//        if (cbChequered.isChecked()) {
//            cbChequeredCheck = 1;
//            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
//        } else {
//            cbChequeredCheck = 0;
//            hm.put("cbChequered", String.valueOf(cbChequeredCheck));
//        }
//
//        if (cbBrickTiles.isChecked()) {
//            cbBrickTilesCheck = 1;
//            hm.put("cbBrickTiless", String.valueOf(cbBrickTilesCheck));
//        } else {
//            cbBrickTilesCheck = 0;
//            hm.put("cbBrickTiless", String.valueOf(cbBrickTilesCheck));
//        }
//        if (cbVinylFlooring.isChecked()) {
//            cbVinylFlooringCheck = 1;
//            hm.put("cbVinylFlooring", String.valueOf(cbVinylFlooringCheck));
//        } else {
//            cbVinylFlooringCheck = 0;
//            hm.put("cbVinylFlooring", String.valueOf(cbVinylFlooringCheck));
//        }
//        if (cbLaminateFlooring.isChecked()) {
//            cbLaminateFlooringCheck = 1;
//            hm.put("cbLaminateFlooring", String.valueOf(cbLaminateFlooringCheck));
//        } else {
//            cbLaminateFlooringCheck = 0;
//            hm.put("cbLaminateFlooring", String.valueOf(cbLaminateFlooringCheck));
//        }
//        if (cbLinoleumFlooring.isChecked()) {
//            cbLinoleumFlooringCheck = 1;
//            hm.put("cbLinoleumFlooring", String.valueOf(cbLinoleumFlooringCheck));
//        } else {
//            cbImportedMarbleCheck = 0;
//            hm.put("cbLinoleumFlooring", String.valueOf(cbLinoleumFlooringCheck));
//        }
//        if (cbNoFlooring.isChecked()) {
//            cbNoFlooringCheck = 1;
//            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
//        } else {
//            cbNoFlooringCheck = 0;
//            hm.put("cbNoFlooring", String.valueOf(cbNoFlooringCheck));
//        }
//        if (cbUnderConstruction.isChecked()) {
//            cbUnderConstructionCheck = 1;
//            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
//        } else {
//            cbUnderConstructionCheck = 0;
//            hm.put("cbUnderConstruction", String.valueOf(cbUnderConstructionCheck));
//        }
//        if (cbAnyOtherType.isChecked()) {
//            cbAnyOtherTypeCheck = 1;
//            hm.put("cbAnyOtherTypee", String.valueOf(cbAnyOtherTypeCheck));
//        } else {
//            cbUnderConstructionCheck = 0;
//            hm.put("cbAnyOtherTypee", String.valueOf(cbAnyOtherTypeCheck));
//        }
//
//        if (cbNoInfoAvaill.isChecked()) {
//            cbNoInfoAvaillCheck = 1;
//            hm.put("cbNoInfoAvaill", String.valueOf(cbNoInfoAvaillCheck));
//        } else {
//            cbNoInfoAvaillCheck = 0;
//            hm.put("cbNoInfoAvaill", String.valueOf(cbNoInfoAvaillCheck));
//        }
//
//
//        if (cbIntSimplePlastered.isChecked()) {
//            cbIntSimplePlasteredCheck = 1;
//            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
//        } else {
//            cbIntSimplePlasteredCheck = 0;
//            hm.put("cbIntSimplePlastered", String.valueOf(cbIntSimplePlasteredCheck));
//        }
//
//        if (cbIntBrickWalls.isChecked()) {
//            cbIntBrickWallsCheck = 1;
//            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
//        } else {
//            cbIntBrickWallsCheck = 0;
//            hm.put("cbIntBrickWalls", String.valueOf(cbIntBrickWallsCheck));
//        }
//        if (cbIntDesignerTextured.isChecked()) {
//            cbIntDesignerTexturedCheck = 1;
//            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
//        } else {
//            cbIntDesignerTexturedCheck = 0;
//            hm.put("cbIntDesignerTextured", String.valueOf(cbIntDesignerTexturedCheck));
//        }
//        if (cbIntPopPunning.isChecked()) {
//            cbIntPopPunningCheck = 1;
//            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
//        } else {
//            cbIntPopPunningCheck = 0;
//            hm.put("cbIntPopPunning", String.valueOf(cbIntPopPunningCheck));
//        }
//        if (cbIntCovedRoof.isChecked()) {
//            cbIntCovedRoofCheck = 1;
//            hm.put("cbIntCovedRoof", String.valueOf(cbIntCovedRoofCheck));
//        } else {
//            cbIntCovedRoofCheck = 0;
//            hm.put("cbIntCovedRoof", String.valueOf(cbIntCovedRoofCheck));
//        }
//        if (cbIntVeneerLaminated.isChecked()) {
//            cbIntVeneerLaminatedCheck = 1;
//            hm.put("cbIntVeneerLaminated", String.valueOf(cbIntVeneerLaminatedCheck));
//        } else {
//            cbIntVeneerLaminatedCheck = 0;
//            hm.put("cbIntVeneerLaminated", String.valueOf(cbIntVeneerLaminatedCheck));
//        }
//        if (cbIntSteelLaminated.isChecked()) {
//            cbIntSteelLaminatedCheck = 1;
//            hm.put("cbIntSteelLaminated", String.valueOf(cbIntSteelLaminatedCheck));
//        } else {
//            cbIntSteelLaminatedCheck = 0;
//            hm.put("cbIntSteelLaminated", String.valueOf(cbIntSteelLaminatedCheck));
//        }
//        if (cbIntPoorDepleted.isChecked()) {
//            cbIntPoorDepletedCheck = 1;
//            hm.put("cbIntPoorDepleted", String.valueOf(cbIntPoorDepletedCheck));
//        } else {
//            cbIntSteelLaminatedCheck = 0;
//            hm.put("cbIntPoorDepleted", String.valueOf(cbIntPoorDepletedCheck));
//        }
//        if (cbIntUnderConstruction.isChecked()) {
//            cbIntUnderConstructionCheck = 1;
//            hm.put("cbIntUnderConstruction", String.valueOf(cbIntUnderConstructionCheck));
//        } else {
//            cbIntUnderConstructionCheck = 0;
//            hm.put("cbIntUnderConstruction", String.valueOf(cbIntUnderConstructionCheck));
//        }
//        /*if (cbIntNoSurvey.isChecked()) {
//            cbIntNoSurveyCheck = 1;
//            hm.put("cbIntNoSurvey", String.valueOf(cbIntNoSurveyCheck));
//        } else {
//            cbIntNoSurveyCheck = 0;
//            hm.put("cbIntNoSurvey", String.valueOf(cbIntNoSurveyCheck));
//        }*/
//
//        if (cbNInfoIhFinishing.isChecked()) {
//            cbNInfoIhFinishingCheck = 1;
//            hm.put("cbNInfoIhFinishing", String.valueOf(cbNInfoIhFinishingCheck));
//        } else {
//            cbNInfoIhFinishingCheck = 0;
//            hm.put("cbNInfoIhFinishing", String.valueOf(cbNInfoIhFinishingCheck));
//        }
//
//
//        if (cbExtSimplePlastered.isChecked()) {
//            cbExtSimplePlasteredCheck = 1;
//            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
//        } else {
//            cbExtSimplePlasteredCheck = 0;
//            hm.put("cbExtSimplePlastered", String.valueOf(cbExtSimplePlasteredCheck));
//        }
//        if (cbExtBrickWalls.isChecked()) {
//            cbExtBrickWallsCheck = 1;
//            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
//        } else {
//            cbExtBrickWallsCheck = 0;
//            hm.put("cbExtBrickWalls", String.valueOf(cbExtBrickWallsCheck));
//        }
//        if (cbExtArchitecturally.isChecked()) {
//            cbExtArchitecturallyCheck = 1;
//            hm.put("cbExtArchitecturally", String.valueOf(cbExtArchitecturallyCheck));
//        } else {
//            cbExtArchitecturallyCheck = 0;
//            hm.put("cbExtArchitecturally", String.valueOf(cbExtArchitecturallyCheck));
//        }
//        if (cbExtBrickTile.isChecked()) {
//            cbExtBrickTileCheck = 1;
//            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
//        } else {
//            cbExtBrickTileCheck = 0;
//            hm.put("cbExtBrickTile", String.valueOf(cbExtBrickTileCheck));
//        }
//        if (cbExtStructuralGlazing.isChecked()) {
//            cbExtStructuralGlazingCheck = 1;
//            hm.put("cbExtStructuralGlazing", String.valueOf(cbExtStructuralGlazingCheck));
//        } else {
//            cbExtStructuralGlazingCheck = 0;
//            hm.put("cbExtStructuralGlazing", String.valueOf(cbExtStructuralGlazingCheck));
//        }
//        if (cbExtAluminumComposite.isChecked()) {
//            cbExtAluminumCompositeCheck = 1;
//            hm.put("cbExtAluminumComposite", String.valueOf(cbExtAluminumCompositeCheck));
//        } else {
//            cbExtAluminumCompositeCheck = 0;
//            hm.put("cbExtAluminumComposite", String.valueOf(cbExtAluminumCompositeCheck));
//        }
//        if (cbExtGlassFacade.isChecked()) {
//            cbExtGlassFacadeCheck = 1;
//            hm.put("cbExtGlassFacade", String.valueOf(cbExtGlassFacadeCheck));
//        } else {
//            cbExtGlassFacadeCheck = 0;
//            hm.put("cbExtGlassFacade", String.valueOf(cbExtGlassFacadeCheck));
//        }
//        if (cbExtDomb.isChecked()) {
//            cbExtDombCheck = 1;
//            hm.put("cbExtDomb", String.valueOf(cbExtDombCheck));
//        } else {
//            cbExtDombCheck = 0;
//            hm.put("cbExtDomb", String.valueOf(cbExtDombCheck));
//        }
//        if (cbExtPorch.isChecked()) {
//            cbExtPorchCheck = 1;
//            hm.put("cbExtPorch", String.valueOf(cbExtPorchCheck));
//        } else {
//            cbExtPorchCheck = 0;
//            hm.put("cbExtPorch", String.valueOf(cbExtPorchCheck));
//        }
//        if (cbExtPoorDepleted.isChecked()) {
//            cbExtPoorDepletedCheck = 1;
//            hm.put("cbExtPoorDepleted", String.valueOf(cbExtPoorDepletedCheck));
//        } else {
//            cbExtPoorDepletedCheck = 0;
//            hm.put("cbExtPoorDepleted", String.valueOf(cbExtPoorDepletedCheck));
//        }
//        if (cbExtUnderConstruction.isChecked()) {
//            cbExtUnderConstructionCheck = 1;
//            hm.put("cbExtUnderConstruction", String.valueOf(cbExtUnderConstructionCheck));
//        } else {
//            cbExtUnderConstructionCheck = 0;
//            hm.put("cbExtUnderConstruction", String.valueOf(cbExtUnderConstructionCheck));
//        }
//
//
//        if (cbJetPump.isChecked()) {
//            cbJetPumpCheck = 1;
//            hm.put("cbJetPump", String.valueOf(cbJetPumpCheck));
//        } else {
//            cbJetPumpCheck = 0;
//            hm.put("cbJetPump", String.valueOf(cbJetPumpCheck));
//        }
//        if (cbSubmersible.isChecked()) {
//            cbSubmersibleCheck = 1;
//            hm.put("cbSubmersible", String.valueOf(cbSubmersibleCheck));
//        } else {
//            cbSubmersibleCheck = 0;
//            hm.put("cbSubmersible", String.valueOf(cbSubmersibleCheck));
//        }
//        if (cbJalBoardSupply.isChecked()) {
//            cbJalBoardSupplyCheck = 1;
//            hm.put("cbJalBoardSupply", String.valueOf(cbJalBoardSupplyCheck));
//        } else {
//            cbJalBoardSupplyCheck = 0;
//            hm.put("cbJalBoardSupply", String.valueOf(cbJalBoardSupplyCheck));
//        }
//        if (cbUnderConstructionWater.isChecked()) {
//            cbUnderConstructionWaterCheck = 1;
//            hm.put("cbUnderConstructionWater", String.valueOf(cbUnderConstructionWaterCheck));
//        } else {
//            cbJalBoardSupplyCheck = 0;
//            hm.put("cbUnderConstructionWater", String.valueOf(cbUnderConstructionWaterCheck));
//        }
//        if (cbNoInfoAvailWater.isChecked()) {
//            cbNoInfoAvailWaterCheck = 1;
//            hm.put("cbNoInfoAvailWater", String.valueOf(cbNoInfoAvailWaterCheck));
//        } else {
//            cbNoInfoAvailWaterCheck = 0;
//            hm.put("cbNoInfoAvailWater", String.valueOf(cbNoInfoAvailWaterCheck));
//        }
//
//
//        if (cbMaintenanceIssues.isChecked()) {
//            cbMaintenanceIssuesCheck = 1;
//            hm.put("cbMaintenanceIssuess", String.valueOf(cbMaintenanceIssuesCheck));
//        } else {
//            cbMaintenanceIssuesCheck = 0;
//            hm.put("cbMaintenanceIssuess", String.valueOf(cbMaintenanceIssuesCheck));
//        }
//        if (cbFinishingIssues.isChecked()) {
//            cbFinishingIssuesCheck = 1;
//            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
//        } else {
//            cbFinishingIssuesCheck = 0;
//            hm.put("cbFinishingIssues", String.valueOf(cbFinishingIssuesCheck));
//        }
//        if (cbSeepageIssues.isChecked()) {
//            cbSeepageIssuesCheck = 1;
//            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
//        } else {
//            cbSeepageIssuesCheck = 0;
//            hm.put("cbSeepageIssues", String.valueOf(cbSeepageIssuesCheck));
//        }
//        if (cbWaterSupplyIssues.isChecked()) {
//            cbWaterSupplyIssuesCheck = 1;
//            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
//        } else {
//            cbWaterSupplyIssuesCheck = 0;
//            hm.put("cbWaterSupplyIssues", String.valueOf(cbWaterSupplyIssuesCheck));
//        }
//        if (cbElectricalIssues.isChecked()) {
//            cbElectricalIssuesCheck = 1;
//            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
//        } else {
//            cbElectricalIssuesCheck = 0;
//            hm.put("cbElectricalIssues", String.valueOf(cbElectricalIssuesCheck));
//        }
//        if (cbStructuralIssues.isChecked()) {
//            cbStructuralIssuesCheck = 1;
//            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
//        } else {
//            cbStructuralIssuesCheck = 0;
//            hm.put("cbStructuralIssues", String.valueOf(cbStructuralIssuesCheck));
//        }
//        if (cbVisibleCracks.isChecked()) {
//            cbVisibleCracksCheck = 1;
//            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracksCheck));
//        } else {
//            cbVisibleCracksCheck = 0;
//            hm.put("cbVisibleCracks", String.valueOf(cbVisibleCracksCheck));
//        }
//        if (cbRunDownBuilding.isChecked()) {
//            cbRunDownBuildingCheck = 1;
//            hm.put("cbRunDownBuilding", String.valueOf(cbRunDownBuildingCheck));
//        } else {
//            cbRunDownBuildingCheck = 0;
//            hm.put("cbRunDownBuilding", String.valueOf(cbRunDownBuildingCheck));
//        }
//        ///////
//        if (cbNoMoreSafe.isChecked()) {
//            cbNoMoreSafeCheck = 1;
//            hm.put("cbNoMoreSafe", String.valueOf(cbNoMoreSafeCheck));
//        } else {
//            cbNoMoreSafeCheck = 0;
//            hm.put("cbNoMoreSafe", String.valueOf(cbNoMoreSafeCheck));
//        }
//        if (cbNotAppropriate.isChecked()) {
//            cbNotAppropriateCheck = 1;
//            hm.put("cbNotAppropriate", String.valueOf(cbNotAppropriateCheck));
//        } else {
//            cbNotAppropriateCheck = 0;
//            hm.put("cbNotAppropriate", String.valueOf(cbNotAppropriateCheck));
//        }
//        if (cbUnderConstructionDefects.isChecked()) {
//            cbUnderConstructionDefectsCheck = 1;
//            hm.put("cbUnderConstructionDefects", String.valueOf(cbUnderConstructionDefectsCheck));
//        } else {
//            cbUnderConstructionDefectsCheck = 0;
//            hm.put("cbUnderConstructionDefects", String.valueOf(cbUnderConstructionDefectsCheck));
//        }
//
//        if (cbNoDefect.isChecked()) {
//            cbNoDefectCheck = 1;
//            hm.put("cbNoDefect", String.valueOf(cbNoDefectCheck));
//        } else {
//            cbNoDefectCheck = 0;
//            hm.put("cbNoDefect", String.valueOf(cbNoDefectCheck));
//        }
//
//        if (cbNoInfo.isChecked()) {
//            cbNoInfoCheck = 1;
//            hm.put("cbNoInfo", String.valueOf(cbNoInfoCheck));
//        } else {
//            cbNoInfoCheck = 0;
//            hm.put("cbNoInfo", String.valueOf(cbNoInfoCheck));
//        }
//
//
//        if (cbConstructionDoneWithoutMap.isChecked()) {
//            cbConstructionDoneWithoutMapCheck = 1;
//            hm.put("cbConstructionDoneWithoutMaap", String.valueOf(cbConstructionDoneWithoutMapCheck));
//        } else {
//            cbConstructionDoneWithoutMapCheck = 0;
//            hm.put("cbConstructionDoneWithoutMaap", String.valueOf(cbConstructionDoneWithoutMapCheck));
//        }
//        if (cbConstructionNotAsPer.isChecked()) {
//            cbConstructionNotAsPerCheck = 1;
//            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
//        } else {
//            cbConstructionNotAsPerCheck = 0;
//            hm.put("cbConstructionNotAsPer", String.valueOf(cbConstructionNotAsPerCheck));
//        }
//        if (cbExtraCovered.isChecked()) {
//            cbExtraCoveredCheck = 1;
//            hm.put("cbExtraCovereed", String.valueOf(cbExtraCoveredCheck));
//        } else {
//            cbExtraCoveredCheck = 0;
//            hm.put("cbExtraCovereed", String.valueOf(cbExtraCoveredCheck));
//        }
//        if (cbJoinedAdjacent.isChecked()) {
//            cbJoinedAdjacentCheck = 1;
//            hm.put("cbJoinedAdjacenntt", String.valueOf(cbJoinedAdjacentCheck));
//        } else {
//            cbJoinedAdjacentCheck = 0;
//            hm.put("cbJoinedAdjacenntt", String.valueOf(cbJoinedAdjacentCheck));
//        }
//
//        if (cbEncroachedAdjacent.isChecked()) {
//            cbEncroachedAdjacentCheck = 1;
//            hm.put("cbEncroachedAdjacennt", String.valueOf(cbEncroachedAdjacentCheck));
//        } else {
//            cbEncroachedAdjacentCheck = 0;
//            hm.put("cbEncroachedAdjacennt", String.valueOf(cbEncroachedAdjacentCheck));
//        }
//
//        if (cbNoViolation.isChecked()) {
//            cbNoViolationCheck = 1;
//            hm.put("cbNoViolation", String.valueOf(cbNoViolationCheck));
//        } else {
//            cbNoViolationCheck = 0;
//            hm.put("cbNoViolation", String.valueOf(cbNoViolationCheck));
//        }
//
//        if (cbUnderConstructionAnyViolation.isChecked()) {
//            cbUnderConstructionAnyViolationCheck = 1;
//            hm.put("cbUnderConstructionAnyViolation", String.valueOf(cbUnderConstructionAnyViolationCheck));
//        } else {
//            cbUnderConstructionAnyViolationCheck = 0;
//            hm.put("cbUnderConstructionAnyViolation", String.valueOf(cbUnderConstructionAnyViolationCheck));
//        }
//
//        if (cbNoViolence.isChecked()) {
//            cbNoViolenceCheck = 1;
//            hm.put("cbNoViolence", String.valueOf(cbNoViolenceCheck));
//        } else {
//            cbNoViolenceCheck = 0;
//            hm.put("cbNoViolence", String.valueOf(cbNoViolenceCheck));
//        }
//
//
//        int selectedIdConstructionStatus = rgConstructionStatus.getCheckedRadioButtonId();
//        View radioButtonConstructionStatus = v.findViewById(selectedIdConstructionStatus);
//        int idx = rgConstructionStatus.indexOfChild(radioButtonConstructionStatus);
//        RadioButton r = (RadioButton) rgConstructionStatus.getChildAt(idx);
//        String selectedText = r.getText().toString();
//        hm.put("radioButtonConstructionStatuss", selectedText);
//
//        int selectedIdBuildingType = rgBuildingType.getCheckedRadioButtonId();
//        View radioButtonBuildingType = v.findViewById(selectedIdBuildingType);
//        int idx2 = rgBuildingType.indexOfChild(radioButtonBuildingType);
//        RadioButton r2 = (RadioButton) rgBuildingType.getChildAt(idx2);
//        String selectedText2 = r2.getText().toString();
//        hm.put("radioButtonBuildingTyppe", selectedText2);
//
//        int selectedIdClassOfConstruction = rgClassOfConstruction.getCheckedRadioButtonId();
//        View radioButtonClassOfConstruction = v.findViewById(selectedIdClassOfConstruction);
//        int idx3 = rgClassOfConstruction.indexOfChild(radioButtonClassOfConstruction);
//        RadioButton r3 = (RadioButton) rgClassOfConstruction.getChildAt(idx3);
//        String selectedText3 = r3.getText().toString();
//        hm.put("radioButtonClassOfConstruction", selectedText3);
//
//        int selectedIdAppearanceInternal = rgAppearanceInternal.getCheckedRadioButtonId();
//        View radioButtonAppearanceInternal = v.findViewById(selectedIdAppearanceInternal);
//        int idx4 = rgAppearanceInternal.indexOfChild(radioButtonAppearanceInternal);
//        RadioButton r4 = (RadioButton) rgAppearanceInternal.getChildAt(idx4);
//        String selectedText4 = r4.getText().toString();
//        hm.put("radioButtonAppearanceInternal", selectedText4);
//
//        int selectedIdAppearanceExternal = rgAppearanceExternal.getCheckedRadioButtonId();
//        View radioButtonAppearanceExternal = v.findViewById(selectedIdAppearanceExternal);
//        int idx5 = rgAppearanceExternal.indexOfChild(radioButtonAppearanceExternal);
//        RadioButton r5 = (RadioButton) rgAppearanceExternal.getChildAt(idx5);
//        String selectedText5 = r5.getText().toString();
//        hm.put("radioButtonAppearanceExternal", selectedText5);
//
//   /*     int selectedIdMaintenanceOfTheBuilding = rgMaintenanceOfTheBuilding.getCheckedRadioButtonId();
//        View radioButtonMaintenanceOfTheBuilding = v.findViewById(selectedIdMaintenanceOfTheBuilding);
//        int idx6 = rgMaintenanceOfTheBuilding.indexOfChild(radioButtonMaintenanceOfTheBuilding);
//        RadioButton r6 = (RadioButton) rgMaintenanceOfTheBuilding.getChildAt(idx6);
//        String selectedText6 = r6.getText().toString();
//        hm.put("radioButtonMaintenanceOfTheBuildiing", selectedText6);*/
//
//        int selectedIdInteriorDecoration = rgInteriorDecoration.getCheckedRadioButtonId();
//        View radioButtonInteriorDecoration = v.findViewById(selectedIdInteriorDecoration);
//        int idx7 = rgInteriorDecoration.indexOfChild(radioButtonInteriorDecoration);
//        RadioButton r7 = (RadioButton) rgInteriorDecoration.getChildAt(idx7);
//        String selectedText7 = r7.getText().toString();
//        hm.put("radioButtonInteriorDecoration", selectedText7);
//
//        int selectedIdKitchen = rgKitchen.getCheckedRadioButtonId();
//        View radioButtonKitchen = v.findViewById(selectedIdKitchen);
//        int idx8 = rgKitchen.indexOfChild(radioButtonKitchen);
//        RadioButton r8 = (RadioButton) rgKitchen.getChildAt(idx8);
//        String selectedText8 = r8.getText().toString();
//        hm.put("radioButtonKitchen", selectedText8);
//
//        try {
//            int selectedIdClassOfElectrical = rgClassOfElectricalOne.getCheckedRadioButtonId();
//            View radioButtonClassOfElectrical = v.findViewById(selectedIdClassOfElectrical);
//            int idx9 = rgClassOfElectricalOne.indexOfChild(radioButtonClassOfElectrical);
//            RadioButton r9 = (RadioButton) rgClassOfElectricalOne.getChildAt(idx9);
//            String selectedText9 = r9.getText().toString();
//            hm.put("radioButtonClassOfElectricalOne", selectedText9);
//        }catch (Exception e){
//            e.printStackTrace();
//            hm.put("radioButtonClassOfElectricalOne", "na");
//        }
//
//
//        try{
//            int selectedIdClassOfElectricalTwo = rgClassOfElectricalTwo.getCheckedRadioButtonId();
//            View radioButtonClassOfElectricalTwo = v.findViewById(selectedIdClassOfElectricalTwo);
//            int idx99 = rgClassOfElectricalTwo.indexOfChild(radioButtonClassOfElectricalTwo);
//            RadioButton r99 = (RadioButton) rgClassOfElectricalTwo.getChildAt(idx99);
//            String selectedText99 = r99.getText().toString();
//            hm.put("radioButtonClassOfElectricalTwo", selectedText99);
//        }catch (Exception e){
//            e.printStackTrace();
//            hm.put("radioButtonClassOfElectricalTwo", "na");
//        }
//
//
//        try {
//            int selectedIdClassOfElectricalThree = rgClassOfElectricalThree.getCheckedRadioButtonId();
//            View radioButtonClassOfElectricalThree = v.findViewById(selectedIdClassOfElectricalThree);
//            int idx999 = rgClassOfElectricalThree.indexOfChild(radioButtonClassOfElectricalThree);
//            RadioButton r999 = (RadioButton) rgClassOfElectricalThree.getChildAt(idx999);
//            String selectedText999 = r999.getText().toString();
//            hm.put("radioButtonClassOfElectricalThree", selectedText999);
//        }catch (Exception e){
//            hm.put("radioButtonClassOfElectricalThree", "na");
//            e.printStackTrace();
//        }
//
//
//        try {
//            int selectedIdClassOfSanitary = rgClassOfSanitaryOne.getCheckedRadioButtonId();
//            View radioButtonClassOfSanitary = v.findViewById(selectedIdClassOfSanitary);
//            int idx10 = rgClassOfSanitaryOne.indexOfChild(radioButtonClassOfSanitary);
//            RadioButton r10 = (RadioButton) rgClassOfSanitaryOne.getChildAt(idx10);
//            String selectedText10 = r10.getText().toString();
//            hm.put("radioButtonClassOfSanitaryOne", selectedText10);
//
//        }catch (Exception e){
//            hm.put("radioButtonClassOfSanitaryOne", "na");
//            e.printStackTrace();
//        }
//        try {
//            int selectedIdClassOfSanitaryTwo = rgClassOfSanitaryTwo.getCheckedRadioButtonId();
//            View radioButtonClassOfSanitaryTwo = v.findViewById(selectedIdClassOfSanitaryTwo);
//            int idx100 = rgClassOfSanitaryTwo.indexOfChild(radioButtonClassOfSanitaryTwo);
//            RadioButton r100 = (RadioButton) rgClassOfSanitaryTwo.getChildAt(idx100);
//            String selectedText100 = r100.getText().toString();
//            hm.put("radioButtonClassOfSanitaryTwo", selectedText100);
//        }catch (Exception e){
//            hm.put("radioButtonClassOfSanitaryTwo", "na");
//            e.printStackTrace();
//        }
//        try {
//
//            int selectedIdClassOfSanitaryThree = rgClassOfSanitaryThree.getCheckedRadioButtonId();
//            View radioButtonClassOfSanitaryThree = v.findViewById(selectedIdClassOfSanitaryThree);
//            int idx101 = rgClassOfSanitaryThree.indexOfChild(radioButtonClassOfSanitaryThree);
//            RadioButton r101 = (RadioButton) rgClassOfSanitaryThree.getChildAt(idx101);
//            String selectedText101 = r101.getText().toString();
//            hm.put("radioButtonClassOfSanitaryThree", selectedText101);
//        }catch (Exception e){
//            hm.put("radioButtonClassOfSanitaryThree", "na");
//            e.printStackTrace();
//        }
//
//
//        int selectedIdFixedWoodenWork = rgFixedWoodenWork.getCheckedRadioButtonId();
//        View radioButtonFixedWoodenWork = v.findViewById(selectedIdFixedWoodenWork);
//        int idx11 = rgFixedWoodenWork.indexOfChild(radioButtonFixedWoodenWork);
//        RadioButton r11 = (RadioButton) rgFixedWoodenWork.getChildAt(idx11);
//        String selectedText11 = r11.getText().toString();
//        hm.put("radioButtonFixedWoodenWork", selectedText11);
//
//        int selectedIdMaintenanceOfTheBuilding2 = rgMaintenanceOfTheBuilding2.getCheckedRadioButtonId();
//        View radioButtonMaintenanceOfTheBuilding2 = v.findViewById(selectedIdMaintenanceOfTheBuilding2);
//        int idx12 = rgMaintenanceOfTheBuilding2.indexOfChild(radioButtonMaintenanceOfTheBuilding2);
//        RadioButton r12 = (RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(idx12);
//        String selectedText12 = r12.getText().toString();
//        hm.put("radioButtonMaintenanceOfTheBuildingg2", selectedText12);
//
//        arrayListData.clear();
//
//        arrayListData.add(hm);
//
//        jsonArrayData = new JSONArray(arrayListData);
//
//        //DatabaseController.removeTable(TableGeneralForm.attachment);
//        // DatabaseController.deleteColumnData("column1");
//
//        ContentValues contentValues = new ContentValues();
//
//        //contentValues.put(TableGeneralForm.generalFormColumn.data.toString(), jsonArrayData.toString());
//        contentValues.put(TableGeneralForm.generalFormColumn.id.toString(), pref.get(Constants.case_id));
//        contentValues.put(TableGeneralForm.generalFormColumn.column8.toString(), jsonArrayData.toString());
//
//        JSONObject obj = new JSONObject();
//        try {
//            obj.put("id_new", pref.get(Constants.case_id));
//            obj.put("page", "8");
//
//            contentValues.put(TableGeneralForm.generalFormColumn.id_new.toString(), pref.get(Constants.case_id));
//            contentValues.put(TableGeneralForm.generalFormColumn.column_new.toString(), obj.toString());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        DatabaseController.insertUpdateData(contentValues, TableGeneralForm.attachment, "id", pref.get(Constants.case_id));
//    }
//
//    public void selectDB() throws JSONException {
//
//        sub_cat = DatabaseController.getTableOne("column8", pref.get(Constants.case_id));
//
//        if (sub_cat != null) {
//
//            Log.v("getfromdb=====", String.valueOf(sub_cat));
//
//            JSONArray array = new JSONArray(sub_cat.get(0).get("column8"));
//
//            Log.v("getfromdbarray=====", String.valueOf(array));
//
//            for (int i = 0; i < array.length(); i++) {
//                JSONObject object = array.getJSONObject(i);
//
//
//                if (object.getString("inCaseOfUnderConstructionSpinner").equals("0")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(0);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("1")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(1);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("2")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(2);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("3")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(3);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("4")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(4);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("5")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(5);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("6")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(6);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("7")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(7);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("8")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(8);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("9")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(9);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("10")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(10);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("11")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(11);
//                } else if (object.getString("inCaseOfUnderConstructionSpinner").equals("12")) {
//                    spinnerInCaseOfUnderConstruction.setSelection(12);
//                }
//
//                etTotalNumberOfFloors.setText(object.getString("totalNoOfFloors"));
//                etFloorOnWhich.setText(object.getString("floorOnWhich"));
//
//                etTypeOfUnit.setText(object.getString("typeOfUnitt"));
//                etNumberOfRooms.setText(object.getString("numberOfRooms"));
//                etCabins.setText(object.getString("cabins"));
//                etCubicles.setText(object.getString("cubicles"));
//
//                etRoofHeight.setText(object.getString("roofHeight"));
//                etAgeOfBuilding.setText(object.getString("ageOfBuildinggg"));
//                etRecentImprovements.setText(object.getString("recentImprovements"));
//                etExtentOfViolation.setText(object.getString("extentOfViolation"));
//                etUptoSlabLevel.setText(object.getString("uptoSlabLevel"));
//
//                if (object.getString("cbRcc").equals("1")) {
//                    cbRcc.setChecked(true);
//                } else {
//                    cbRcc.setChecked(false);
//                }
//                if (object.getString("cbRbc").equals("1")) {
//                    cbRbc.setChecked(true);
//                } else {
//                    cbRbc.setChecked(false);
//                }
//                if (object.getString("cbGiShed").equals("1")) {
//                    cbGiShed.setChecked(true);
//                } else {
//                    cbGiShed.setChecked(false);
//                }
//                if (object.getString("cbTinShed").equals("1")) {
//                    cbTinShed.setChecked(true);
//                } else {
//                    cbTinShed.setChecked(false);
//                }
//                if (object.getString("cbStonePatla").equals("1")) {
//                    cbStonePatla.setChecked(true);
//                } else {
//                    cbStonePatla.setChecked(false);
//                }
//                if (object.getString("cbSimplePlaster").equals("1")) {
//                    cbSimplePlaster.setChecked(true);
//                } else {
//                    cbSimplePlaster.setChecked(false);
//                }
//                if (object.getString("cbPopPunning").equals("1")) {
//                    cbPopPunning.setChecked(true);
//                } else {
//                    cbPopPunning.setChecked(false);
//                }
//                if (object.getString("cbPopFalseCeiling").equals("1")) {
//                    cbPopFalseCeiling.setChecked(true);
//                } else {
//                    cbPopFalseCeiling.setChecked(false);
//                }
//                if (object.getString("cbCovedRoof").equals("1")) {
//                    cbCovedRoof.setChecked(true);
//                } else {
//                    cbCovedRoof.setChecked(false);
//                }
//                if (object.getString("cbNoPlaster").equals("1")) {
//                    cbNoPlaster.setChecked(true);
//                } else {
//                    cbNoPlaster.setChecked(false);
//                }
//                if (object.getString("cbDomedRoof").equals("1")) {
//                    cbDomedRoof.setChecked(true);
//                } else {
//                    cbDomedRoof.setChecked(false);
//                }
//                if (object.getString("cbBeautifulCarving").equals("1")) {
//                    cbBeautifulCarving.setChecked(true);
//                } else {
//                    cbBeautifulCarving.setChecked(false);
//                }
//                if (object.getString("cbNoInfoAvailable").equals("1")) {
//                    cbNoInfoAvailable.setChecked(true);
//                } else {
//                    cbNoInfoAvailable.setChecked(false);
//                }
//                if (object.getString("cbUnderConstructionFinish").equals("1")) {
//                    cbUnderConstructionFinish.setChecked(true);
//                } else {
//                    cbUnderConstructionFinish.setChecked(false);
//                }
//
//
//                if (object.getString("cbMudFlooring").equals("1")) {
//                    cbMudFlooring.setChecked(true);
//                } else {
//                    cbMudFlooring.setChecked(false);
//                }
//                if (object.getString("cbBrickFlooring").equals("1")) {
//                    cbBrickFlooring.setChecked(true);
//                } else {
//                    cbBrickFlooring.setChecked(false);
//                }
//                if (object.getString("cbVitrifiedTiles").equals("1")) {
//                    cbVitrifiedTiles.setChecked(true);
//                } else {
//                    cbVitrifiedTiles.setChecked(false);
//                }
//                if (object.getString("cbCeramicTiles").equals("1")) {
//                    cbCeramicTiles.setChecked(true);
//                } else {
//                    cbCeramicTiles.setChecked(false);
//                }
//
//
//                if (object.getString("cbSimpleMarble").equals("1")) {
//                    cbSimpleMarble.setChecked(true);
//                } else {
//                    cbSimpleMarble.setChecked(false);
//                }
//                if (object.getString("cbMarbleChips").equals("1")) {
//                    cbMarbleChips.setChecked(true);
//                } else {
//                    cbMarbleChips.setChecked(false);
//                }
//                if (object.getString("cbMosaic").equals("1")) {
//                    cbMosaic.setChecked(true);
//                } else {
//                    cbMosaic.setChecked(false);
//                }
//                if (object.getString("cbGranite").equals("1")) {
//                    cbGranite.setChecked(true);
//                } else {
//                    cbGranite.setChecked(false);
//                }
//                if (object.getString("cbItalianMarble").equals("1")) {
//                    cbItalianMarble.setChecked(true);
//                } else {
//                    cbItalianMarble.setChecked(false);
//                }
//                if (object.getString("cbKotaStone").equals("1")) {
//                    cbKotaStone.setChecked(true);
//                } else {
//                    cbKotaStone.setChecked(false);
//                }
//                if (object.getString("cbWooden").equals("1")) {
//                    cbWooden.setChecked(true);
//                } else {
//                    cbWooden.setChecked(false);
//                }
//                if (object.getString("cbPccc").equals("1")) {
//                    cbPcc.setChecked(true);
//                } else {
//                    cbPcc.setChecked(false);
//                }
//
//
//                if (object.getString("cbImportedMarble").equals("1")) {
//                    cbImportedMarble.setChecked(true);
//                } else {
//                    cbImportedMarble.setChecked(false);
//                }
//                if (object.getString("cbPavers").equals("1")) {
//                    cbPavers.setChecked(true);
//                } else {
//                    cbPavers.setChecked(false);
//                }
//                if (object.getString("cbChequered").equals("1")) {
//                    cbChequered.setChecked(true);
//                } else {
//                    cbChequered.setChecked(false);
//                }
//                if (object.getString("cbBrickTiless").equals("1")) {
//                    cbBrickTiles.setChecked(true);
//                } else {
//                    cbBrickTiles.setChecked(false);
//                }
//                if (object.getString("cbVinylFlooring").equals("1")) {
//                    cbVinylFlooring.setChecked(true);
//                } else {
//                    cbVinylFlooring.setChecked(false);
//                }
//
//                if (object.getString("cbLaminateFlooring").equals("1")) {
//                    cbLaminateFlooring.setChecked(true);
//                } else {
//                    cbLaminateFlooring.setChecked(false);
//                }
//                if (object.getString("cbLinoleumFlooring").equals("1")) {
//                    cbLinoleumFlooring.setChecked(true);
//                } else {
//                    cbLinoleumFlooring.setChecked(false);
//                }
//                if (object.getString("cbNoFlooring").equals("1")) {
//                    cbNoFlooring.setChecked(true);
//                } else {
//                    cbNoFlooring.setChecked(false);
//                }
//
//
//                if (object.getString("cbUnderConstruction").equals("1")) {
//                    cbUnderConstruction.setChecked(true);
//                } else {
//                    cbUnderConstruction.setChecked(false);
//                }
//                if (object.getString("cbAnyOtherTypee").equals("1")) {
//                    cbAnyOtherType.setChecked(true);
//                } else {
//                    cbAnyOtherType.setChecked(false);
//                }
//                if (object.getString("cbNoInfoAvaill").equals("1")) {
//                    cbNoInfoAvaill.setChecked(true);
//                } else {
//                    cbNoInfoAvaill.setChecked(false);
//                }
//
//                if (object.getString("cbIntSimplePlastered").equals("1")) {
//                    cbIntSimplePlastered.setChecked(true);
//                } else {
//                    cbIntSimplePlastered.setChecked(false);
//                }
//                if (object.getString("cbIntBrickWalls").equals("1")) {
//                    cbIntBrickWalls.setChecked(true);
//                } else {
//                    cbIntBrickWalls.setChecked(false);
//                }
//
//
//                if (object.getString("cbIntDesignerTextured").equals("1")) {
//                    cbIntDesignerTextured.setChecked(true);
//                } else {
//                    cbIntDesignerTextured.setChecked(false);
//                }
//                if (object.getString("cbIntPopPunning").equals("1")) {
//                    cbIntPopPunning.setChecked(true);
//                } else {
//                    cbIntPopPunning.setChecked(false);
//                }
//                if (object.getString("cbIntCovedRoof").equals("1")) {
//                    cbIntCovedRoof.setChecked(true);
//                } else {
//                    cbIntCovedRoof.setChecked(false);
//                }
//                if (object.getString("cbIntVeneerLaminated").equals("1")) {
//                    cbIntVeneerLaminated.setChecked(true);
//                } else {
//                    cbIntVeneerLaminated.setChecked(false);
//                }
//
//                if (object.getString("cbIntSteelLaminated").equals("1")) {
//                    cbIntSteelLaminated.setChecked(true);
//                } else {
//                    cbIntSteelLaminated.setChecked(false);
//                }
//                if (object.getString("cbIntPoorDepleted").equals("1")) {
//                    cbIntPoorDepleted.setChecked(true);
//                } else {
//                    cbIntPoorDepleted.setChecked(false);
//                }
//                if (object.getString("cbIntUnderConstruction").equals("1")) {
//                    cbIntUnderConstruction.setChecked(true);
//                } else {
//                    cbIntUnderConstruction.setChecked(false);
//                }
//               /* if (object.getString("cbIntNoSurvey").equals("1")) {
//                    cbIntNoSurvey.setChecked(true);
//                } else {
//                    cbIntNoSurvey.setChecked(false);
//                }*/
//                if (object.getString("cbNInfoIhFinishing").equals("1")) {
//                    cbNInfoIhFinishing.setChecked(true);
//                } else {
//                    cbNInfoIhFinishing.setChecked(false);
//                }
//
//                if (object.getString("cbExtSimplePlastered").equals("1")) {
//                    cbExtSimplePlastered.setChecked(true);
//                } else {
//                    cbExtSimplePlastered.setChecked(false);
//                }
//                if (object.getString("cbExtBrickWalls").equals("1")) {
//                    cbExtBrickWalls.setChecked(true);
//                } else {
//                    cbExtBrickWalls.setChecked(false);
//                }
//                if (object.getString("cbExtArchitecturally").equals("1")) {
//                    cbExtArchitecturally.setChecked(true);
//                } else {
//                    cbExtArchitecturally.setChecked(false);
//                }
//                if (object.getString("cbExtBrickTile").equals("1")) {
//                    cbExtBrickTile.setChecked(true);
//                } else {
//                    cbExtBrickTile.setChecked(false);
//                }
//                if (object.getString("cbExtStructuralGlazing").equals("1")) {
//                    cbExtStructuralGlazing.setChecked(true);
//                } else {
//                    cbExtStructuralGlazing.setChecked(false);
//                }
//                if (object.getString("cbExtAluminumComposite").equals("1")) {
//                    cbExtAluminumComposite.setChecked(true);
//                } else {
//                    cbExtAluminumComposite.setChecked(false);
//                }
//                if (object.getString("cbExtGlassFacade").equals("1")) {
//                    cbExtGlassFacade.setChecked(true);
//                } else {
//                    cbExtGlassFacade.setChecked(false);
//                }
//
//
//                if (object.getString("cbExtDomb").equals("1")) {
//                    cbExtDomb.setChecked(true);
//                } else {
//                    cbExtDomb.setChecked(false);
//                }
//                if (object.getString("cbExtPorch").equals("1")) {
//                    cbExtPorch.setChecked(true);
//                } else {
//                    cbExtPorch.setChecked(false);
//                }
//                if (object.getString("cbExtPoorDepleted").equals("1")) {
//                    cbExtPoorDepleted.setChecked(true);
//                } else {
//                    cbExtPoorDepleted.setChecked(false);
//                }
//                if (object.getString("cbExtUnderConstruction").equals("1")) {
//                    cbExtUnderConstruction.setChecked(true);
//                } else {
//                    cbExtUnderConstruction.setChecked(false);
//                }
//                if (object.getString("cbJetPump").equals("1")) {
//                    cbJetPump.setChecked(true);
//                } else {
//                    cbJetPump.setChecked(false);
//                }
//                if (object.getString("cbSubmersible").equals("1")) {
//                    cbSubmersible.setChecked(true);
//                } else {
//                    cbSubmersible.setChecked(false);
//                }
//                if (object.getString("cbJalBoardSupply").equals("1")) {
//                    cbJalBoardSupply.setChecked(true);
//                } else {
//                    cbJalBoardSupply.setChecked(false);
//                }
//                if (object.getString("cbUnderConstructionWater").equals("1")) {
//                    cbUnderConstructionWater.setChecked(true);
//                } else {
//                    cbUnderConstructionWater.setChecked(false);
//                }
//
//                if (object.getString("cbNoInfoAvailWater").equals("1")) {
//                    cbNoInfoAvailWater.setChecked(true);
//                } else {
//                    cbNoInfoAvailWater.setChecked(false);
//                }
//
//
//                if (object.getString("cbMaintenanceIssuess").equals("1")) {
//                    cbMaintenanceIssues.setChecked(true);
//                } else {
//                    cbMaintenanceIssues.setChecked(false);
//                }
//
//                if (object.getString("cbFinishingIssues").equals("1")) {
//                    cbFinishingIssues.setChecked(true);
//                } else {
//                    cbFinishingIssues.setChecked(false);
//                }
//                if (object.getString("cbSeepageIssues").equals("1")) {
//                    cbSeepageIssues.setChecked(true);
//                } else {
//                    cbSeepageIssues.setChecked(false);
//                }
//                if (object.getString("cbWaterSupplyIssues").equals("1")) {
//                    cbWaterSupplyIssues.setChecked(true);
//                } else {
//                    cbWaterSupplyIssues.setChecked(false);
//                }
//
//                if (object.getString("cbElectricalIssues").equals("1")) {
//                    cbElectricalIssues.setChecked(true);
//                } else {
//                    cbElectricalIssues.setChecked(false);
//                }
//                if (object.getString("cbStructuralIssues").equals("1")) {
//                    cbStructuralIssues.setChecked(true);
//                } else {
//                    cbStructuralIssues.setChecked(false);
//                }
//                if (object.getString("cbVisibleCracks").equals("1")) {
//                    cbVisibleCracks.setChecked(true);
//                } else {
//                    cbVisibleCracks.setChecked(false);
//                }
//                if (object.getString("cbRunDownBuilding").equals("1")) {
//                    cbRunDownBuilding.setChecked(true);
//                } else {
//                    cbRunDownBuilding.setChecked(false);
//                }
//                if (object.getString("cbNoInfo").equals("1")) {
//                    cbNoInfo.setChecked(true);
//                } else {
//                    cbNoInfo.setChecked(false);
//                }
//                ///
//                if (object.getString("cbNoMoreSafe").equals("1")) {
//                    cbNoMoreSafe.setChecked(true);
//                } else {
//                    cbNoMoreSafe.setChecked(false);
//                }
//                if (object.getString("cbNotAppropriate").equals("1")) {
//                    cbNotAppropriate.setChecked(true);
//                } else {
//                    cbNotAppropriate.setChecked(false);
//                }
//
//                if (object.getString("cbUnderConstructionDefects").equals("1")) {
//                    cbUnderConstructionDefects.setChecked(true);
//                } else {
//                    cbUnderConstructionDefects.setChecked(false);
//                }
//
//                if (object.getString("cbNoDefect").equals("1")) {
//                    cbNoDefect.setChecked(true);
//                } else {
//                    cbNoDefect.setChecked(false);
//                }
//
//                if (object.getString("cbConstructionDoneWithoutMaap").equals("1")) {
//                    cbConstructionDoneWithoutMap.setChecked(true);
//                } else {
//                    cbConstructionDoneWithoutMap.setChecked(false);
//                }
//                if (object.getString("cbConstructionNotAsPer").equals("1")) {
//                    cbConstructionNotAsPer.setChecked(true);
//                } else {
//                    cbConstructionNotAsPer.setChecked(false);
//                }
//                if (object.getString("cbExtraCovereed").equals("1")) {
//                    cbExtraCovered.setChecked(true);
//                } else {
//                    cbExtraCovered.setChecked(false);
//                }
//
//                if (object.getString("cbJoinedAdjacenntt").equals("1")) {
//                    cbJoinedAdjacent.setChecked(true);
//                } else {
//                    cbJoinedAdjacent.setChecked(false);
//                }
//
//                if (object.getString("cbEncroachedAdjacennt").equals("1")) {
//                    cbEncroachedAdjacent.setChecked(true);
//                } else {
//                    cbEncroachedAdjacent.setChecked(false);
//                }
//
//                if (object.getString("cbNoViolation").equals("1")) {
//                    cbNoViolation.setChecked(true);
//                } else {
//                    cbNoViolation.setChecked(false);
//                }
//
//                if (object.getString("cbUnderConstructionAnyViolation").equals("1")) {
//                    cbUnderConstructionAnyViolation.setChecked(true);
//                } else {
//                    cbUnderConstructionAnyViolation.setChecked(false);
//                }
//                if (object.getString("cbNoViolence").equals("1")) {
//                    cbNoViolence.setChecked(true);
//                } else {
//                    cbNoViolence.setChecked(false);
//                }
//
//
//                if (object.getString("radioButtonConstructionStatuss").equals("Built-up property in use")) {
//                    ((RadioButton) rgConstructionStatus.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonConstructionStatuss").equals("Under construction")) {
//                    ((RadioButton) rgConstructionStatus.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonConstructionStatuss").equals("Abandoned construction")) {
//                    ((RadioButton) rgConstructionStatus.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonConstructionStatuss").equals("Debris")) {
//                    ((RadioButton) rgConstructionStatus.getChildAt(3)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonBuildingTyppe").equals("RCC Framed Structure")) {
//                    ((RadioButton) rgBuildingType.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Load bearing Pillar Beam column")) {
//                    ((RadioButton) rgBuildingType.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Ordinary brick wall structure")) {
//                    ((RadioButton) rgBuildingType.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Iron trusses & Pillars")) {
//                    ((RadioButton) rgBuildingType.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Red stone Patla on Iron girders and brick wall")) {
//                    ((RadioButton) rgBuildingType.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Steel Structure")) {
//                    ((RadioButton) rgBuildingType.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonBuildingTyppe").equals("Abandoned depleted structure")) {
//                    ((RadioButton) rgBuildingType.getChildAt(6)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonClassOfConstruction").equals("High class")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfConstruction").equals("First class")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfConstruction").equals("Second class")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfConstruction").equals("Third Class")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfConstruction").equals("Fourth class")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfConstruction").equals("Abandoned depleted structure")) {
//                    ((RadioButton) rgClassOfConstruction.getChildAt(5)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonAppearanceInternal").equals("Excellent")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Very Good")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Good")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Ordinary")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Average")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Poor")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("Under construction")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(6)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceInternal").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgAppearanceInternal.getChildAt(7)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonAppearanceExternal").equals("Excellent")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Very Good")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Good")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Ordinary")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Average")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Poor")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonAppearanceExternal").equals("Under construction")) {
//                    ((RadioButton) rgAppearanceExternal.getChildAt(6)).setChecked(true);
//                }
//
//
//             /*   if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Very Good")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Good")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Average")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Poor")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildiing").equals("Under construction")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding.getChildAt(4)).setChecked(true);
//                }*/
//
//
//                //////
//                if (object.getString("radioButtonInteriorDecoration").equals("Excellent")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Very Good")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Good")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Simple")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Ordinary")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Average")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Below Average")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(6)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("No Interior Decoration")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(7)).setChecked(true);
//                } else if (object.getString("radioButtonInteriorDecoration").equals("Under construction")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(8)).setChecked(true);
//                }/* else if (object.getString("radioButtonInteriorDecoration").equals("No Survey")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);
//                }*/ else if (object.getString("radioButtonInteriorDecoration").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgInteriorDecoration.getChildAt(9)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonKitchen").equals("Simple with no cupboard")) {
//                    ((RadioButton) rgKitchen.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("Ordinary with cupboard")) {
//                    ((RadioButton) rgKitchen.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("Normal Modular with chimney")) {
//                    ((RadioButton) rgKitchen.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("High end Modular with chimney")) {
//                    ((RadioButton) rgKitchen.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("Under construction")) {
//                    ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("No Kitchen/ Pantry")) {
//                    ((RadioButton) rgKitchen.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonKitchen").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgKitchen.getChildAt(6)).setChecked(true);
//                }
//
//                if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("External")) {
//                    ((RadioButton) rgClassOfElectricalOne.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("Internal")) {
//                    ((RadioButton) rgClassOfElectricalOne.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalOne").equalsIgnoreCase("Mixed (Internal & External)")) {
//                    ((RadioButton) rgClassOfElectricalOne.getChildAt(2)).setChecked(true);
//                }
//
//                 if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Ordinary fixtures & fittings")) {
//                    ((RadioButton) rgClassOfElectricalTwo.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Fancy lights")) {
//                    ((RadioButton) rgClassOfElectricalTwo.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Chandeliers")) {
//                    ((RadioButton) rgClassOfElectricalTwo.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalTwo").equalsIgnoreCase("Concealed lightning")) {
//                    ((RadioButton) rgClassOfElectricalTwo.getChildAt(3)).setChecked(true);
//                }
//
//                else if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("Poor depleted")) {
//                    ((RadioButton) rgClassOfElectricalThree.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("Under construction")) {
//                    ((RadioButton) rgClassOfElectricalThree.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfElectricalThree").equalsIgnoreCase("No information  available since internal survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgClassOfElectricalThree.getChildAt(2)).setChecked(true);
//                }
//
//                if (object.getString("radioButtonClassOfSanitaryOne").equals("External")) {
//                    ((RadioButton) rgClassOfSanitaryOne.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfSanitaryOne").equals("Internal")) {
//                    ((RadioButton) rgClassOfSanitaryOne.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonClassOfSanitaryOne").equals("Mixed (Internal & External)")) {
//                    ((RadioButton) rgClassOfSanitaryOne.getChildAt(2)).setChecked(true);
//                }
//
//                if(surveyTypeCheck==0) {
//                    if (object.getString("radioButtonClassOfSanitaryTwo").equals("Excellent")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(0)).setChecked(true);
//                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Very Good")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(1)).setChecked(true);
//                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Good")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(2)).setChecked(true);
//                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Simple")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(3)).setChecked(true);
//                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Average")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(4)).setChecked(true);
//                    } else if (object.getString("radioButtonClassOfSanitaryTwo").equals("Below average")) {
//                        ((RadioButton) rgClassOfSanitaryTwo.getChildAt(5)).setChecked(true);
//                    }
//
//                    try {
//                        if (object.getString("radioButtonClassOfSanitary").equals("Poor depleted")) {
//                            ((RadioButton) rgClassOfSanitaryThree.getChildAt(0)).setChecked(true);
//                        } else if (object.getString("radioButtonClassOfSanitary").equals("Under construction")) {
//                            ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setChecked(true);
//                        } else if (object.getString("radioButtonClassOfSanitary").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                            ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else {
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//                }
//
//                if (object.getString("radioButtonFixedWoodenWork").equals("Excellent")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Very Good")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Good")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Simple")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Ordinary")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Average")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Below Average")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(6)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("No wooden work")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(7)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("Under construction")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(8)).setChecked(true);
//                } else if (object.getString("radioButtonFixedWoodenWork").equals("No information available since internal survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgFixedWoodenWork.getChildAt(9)).setChecked(true);
//                }
//
//
//                if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Very Good")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(0)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Good")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(1)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Average")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(2)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Poor")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(3)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("No maintenance carried out in last few months")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(4)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("Under construction")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(5)).setChecked(true);
//                } else if (object.getString("radioButtonMaintenanceOfTheBuildingg2").equals("No information available since full survey of the property couldn’t be carried out")) {
//                    ((RadioButton) rgMaintenanceOfTheBuilding2.getChildAt(6)).setChecked(true);
//                }
//
//
//                // String projectName = object.getString("projectName");
//                // Log.e("!!!projectName", projectName);
//
//                if(pref.get(Constants.surveyTypeCheck).equalsIgnoreCase("1"))
//                {
//                    ((RadioButton) rgClassOfElectricalThree.getChildAt(2)).setChecked(true);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setChecked(true);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(0)).setClickable(false);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(1)).setClickable(false);
//                    ((RadioButton) rgClassOfSanitaryThree.getChildAt(2)).setClickable(false);
//
//                }
//            }
//
//        }
//    }
//
//    private void populateSinner() {
//
//   /*     //Spinner IsLandMerged
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                this, android.R.layout.simple_spinner_dropdown_item, arrayInCase);
//
//        spinnerInCaseOfUnderConstruction.setAdapter(adapter);*/
//
//        spinnerAdapter = new SpinnerAdapter(mActivity,arrayFtMtr);
//        spinnerHeightFtMtr.setAdapter(spinnerAdapter);
//
//        spinnerAdapter = new SpinnerAdapter(mActivity,arrayInCase );
//        spinnerInCaseOfUnderConstruction.setAdapter(spinnerAdapter);
//
//        spinnerInCaseOfUnderConstruction.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//             if (adapterView.getSelectedItemPosition() == 5 ||adapterView.getSelectedItemPosition() == 6
//                     ||adapterView.getSelectedItemPosition() == 7 ||adapterView.getSelectedItemPosition() == 8){
//
//                 llUptoSlabLevel.setVisibility(View.VISIBLE);
//             }else {
//                 llUptoSlabLevel.setVisibility(View.GONE);
//             }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//    }
//
//    public void hideKeyboard(View view) {
//        InputMethodManager in = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    public void showPopup(View v) {
//        PopupMenu popup = new PopupMenu(mActivity, v);
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_main, popup.getMenu());
//        popup.show();
//
//        //if Required then only
//        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.general_one:
//                        ((Dashboard)mActivity).displayView(6);
//                        /*intent=new Intent(GeneralForm3.this, GeneralForm1.class);
//                        startActivity(intent);*/
//                        return true;
//
//                    case R.id.general_two:
//                        ((Dashboard)mActivity).displayView(7);
//                        return true;
//
//                    case R.id.general_three:
//                        ((Dashboard)mActivity).displayView(8);
//                        return true;
//
//                    case R.id.general_four:
//                        ((Dashboard)mActivity).displayView(9);
//                        return true;
//
//                    case R.id.general_five:
//                        ((Dashboard)mActivity).displayView(10);
//                        return true;
//
//                    case R.id.general_six:
//                        ((Dashboard)mActivity).displayView(11);
//                        return true;
//
//                    case R.id.general_seven:
//                        ((Dashboard)mActivity).displayView(12);
//                        return true;
//
//                    case R.id.general_eight:
//                        ((Dashboard)mActivity).displayView(13);
//                        return true;
//
//                    case R.id.general_nine:
//                        ((Dashboard)mActivity).displayView(14);
//                        return true;
//
//                    case R.id.general_ten:
//                        ((Dashboard)mActivity).displayView(15);
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });
//    }
//}
