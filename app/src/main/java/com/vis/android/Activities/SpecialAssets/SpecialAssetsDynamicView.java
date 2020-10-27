package com.vis.android.Activities.SpecialAssets;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.vis.android.Common.MyDividerItemDecoration;
import com.vis.android.Common.RecyclerTouchListener;
import com.vis.android.Database.DatabaseHelperSA_Dynamic;
import com.vis.android.Database.TableSpecialAssets;
import com.vis.android.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpecialAssetsDynamicView extends AppCompatActivity implements View.OnClickListener {
    public static NotesAdapterSpecialAssets mAdapter;
    private static List<TableSpecialAssets> notesList = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;
    private RecyclerView recyclerView;
    private static TextView noNotesView;

    public static DatabaseHelperSA_Dynamic db;

    public static ArrayList<HashMap<String, String>> arrayListData = new ArrayList<>();
    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

    TextView tvNext;

    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_assets_dynamic_view);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        //coordinatorLayout = findViewById(R.id.coordinator_layout);
        recyclerView = findViewById(R.id.recycler_view);
        noNotesView = findViewById(R.id.empty_notes_view);
        tvNext=(TextView) findViewById(R.id.tvNext);
        tvNext.setOnClickListener(this);

        mContext    = this;

        db = new DatabaseHelperSA_Dynamic(this);

        notesList.addAll(db.getAllSpecialAssetsData());

        //sub_cat.addAll(DatabaseController.getIndustrial());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoteDialog(false, null, -1);
            }
        });

        mAdapter = new NotesAdapterSpecialAssets(this, notesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(mAdapter);

        toggleEmptyNotes();

        /**
         * On long press on RecyclerView item, open alert dialog
         * with options to choose
         * Edit and Delete
         * */
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                showActionsDialog(position);
            }

            @Override
            public void onLongClick(View view, int position) {
                //showActionsDialog(position);
            }
        }));
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.tvNext:
                Intent intent=new Intent(SpecialAssetsDynamicView.this,SpecialAssets6.class);
                startActivity(intent);
        }

    }

    /**
     * Inserting new note in db
     * and refreshing the list
     */
    public static void createNote(String sno, String blockname, String totalslab, String height, String yrcons, String typecons, String structure, String area) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertSpecialAssetsData(sno,blockname,totalslab,height,yrcons,typecons,structure,area);

        // get the newly inserted note from db
        TableSpecialAssets n = db.getSpecialAssetsData(id);

        if (n != null) {
            // adding new note to array list at 0 position
            notesList.add(0, n);

            // refreshing the list
            mAdapter.notifyDataSetChanged();

            toggleEmptyNotes();
        }
    }

    /**
     * Updating note in db and updating
     * item in the list by its position
     */
    public static void updateNote(String sno, String blockname, String totalslab, String height, String yrcons, String typecons, String structure, String area, int position) {
        TableSpecialAssets n = notesList.get(position);
        //HashMap<String, String> n = sub_cat.get(position);

/*
        n.put("sno", dynamicEts_no.getText().toString());
        n.put("blockname", dynamicEtBlock_name.getText().toString());
        n.put("totalslab", dynamicEtSlabs.getText().toString());
        n.put("height", dynamicEtHeight.getText().toString());
        n.put("yrcons", dynamicEtYr_Cons.getText().toString());
        n.put("typecons", dynamicEtType_Cons.getText().toString());
        n.put("structure", dynamicEtStructure_Cons.getText().toString());
        n.put("area", dynamicEtArea.getText().toString());

        arrayListData.add(hm);*/

        // updating note text
        //n.setNote(note);
        n.setSNo(sno);
        n.setBlockName(blockname);
        n.setTotalSlab(totalslab);
        n.setHeight(height);
        n.setYrCons(yrcons);
        n.setTypeCons(typecons);
        n.setStructure(structure);
        n.setArea(area);

        //n.put()
        //DatabaseController.updateData(TableIndustrial.attachment,);
        //sub_cat.set(position,n);

        // updating note in db
        db.updateSpecialAssetsData(n);

        // refreshing the list
        notesList.set(position, n);
        mAdapter.notifyItemChanged(position);

        toggleEmptyNotes();
    }

    /**
     * Deleting note from SQLite and removing the
     * item from the list by its position
     */
    public static void deleteNote(int position) {
        // deleting the note from db
        db.deleteSpecialAssetsData(notesList.get(position));
        //DatabaseController.deleteRow(TableIndustrial.attachment,TableIndustrial.industrialColumn.sno.toString(),null);

        // removing the note from the list
        notesList.remove(position);
        //sub_cat.remove(position);
        mAdapter.notifyItemRemoved(position);

        toggleEmptyNotes();
    }

    /**
     * Opens dialog with Edit - Delete options
     * Edit - 0
     * Delete - 0
     */
    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Edit", "Delete"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose option");



        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    showNoteDialog(true, notesList.get(position), position);
                } else {
                    deleteNote(position);
                }
            }
        });
        builder.show();
    }


    /**
     * Shows alert dialog with EditText options to enter / edit
     * a note.
     * when shouldUpdate=true, it automatically displays old note and changes the
     * button text to UPDATE
     */
    public static void showNoteDialog(final boolean shouldUpdate, final TableSpecialAssets note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(mContext);
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);
        //View view = layoutInflaterAndroid.inflate(R.layout.dynamic_row, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(mContext);
        alertDialogBuilderUserInput.setView(view);



        final EditText dynamicEts_no;
        final EditText dynamicEtBlock_name;
        final EditText dynamicEtSlabs;
        final EditText dynamicEtHeight;
        final  EditText dynamicEtYr_Cons;
        final  EditText dynamicEtType_Cons;
        final  EditText dynamicEtStructure_Cons;
        final   EditText dynamicEtArea;

        dynamicEts_no = view.findViewById(R.id.dynamicet_sno);
        dynamicEtBlock_name = view.findViewById(R.id.dynamicet_blockno);
        dynamicEtSlabs = view.findViewById(R.id.dynamicet_floors);
        dynamicEtHeight = view.findViewById(R.id.dynamicet_height);
        dynamicEtYr_Cons = view.findViewById(R.id.dynamicet_yearCons);
        dynamicEtType_Cons = view.findViewById(R.id.dynamicet_typeCons);
        dynamicEtStructure_Cons = view.findViewById(R.id.dynamicet_structure);
        dynamicEtArea = view.findViewById(R.id.dynamicet_area);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? mContext.getString(R.string.lbl_new_note_title) : mContext.getString(R.string.lbl_edit_note_title));

        if (shouldUpdate && note != null) {
            dynamicEts_no.setText(note.getSNo());
            dynamicEtBlock_name.setText(note.getBlockName());
            dynamicEtSlabs.setText(note.getTotalSlab());
            dynamicEtHeight.setText(note.getHeight());
            dynamicEtYr_Cons.setText(note.getYrCons());
            dynamicEtType_Cons.setText(note.getTypeCons());
            dynamicEtStructure_Cons.setText(note.getStructure());
            dynamicEtArea.setText(note.getArea());
        }



        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(shouldUpdate ? "update" : "save", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton("cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(alertDialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        alertDialog.show();
        alertDialog.getWindow().setAttributes(lp);

        //alertDialog.show();

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // Show toast message when no text is entered
                if (TextUtils.isEmpty(dynamicEts_no.getText().toString())) {
                    //Toast.makeText(Industrial4.this, "Enter S.No.!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter S.No.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEts_no.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(dynamicEtBlock_name.getText().toString())) {
                    //Toast.makeText(Industrial4.this, "Enter Block Name!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Block Name", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtBlock_name.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(dynamicEtSlabs.getText().toString())) {
                    // Toast.makeText(Industrial4.this, "Enter Total Slabs!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Total Slabs/Floors", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtSlabs.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtHeight.getText().toString())) {
                    //   Toast.makeText(Industrial4.this, "Enter Height!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Height", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtHeight.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtYr_Cons.getText().toString())) {
                    //Toast.makeText(Industrial4.this, "Enter Yr Cons!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Year of Construction", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtYr_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtType_Cons.getText().toString())) {
                    // Toast.makeText(Industrial4.this, "Enter Type Cons!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Type of Construction", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtType_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtStructure_Cons.getText().toString())) {
                    // Toast.makeText(Industrial4.this, "Enter Structure!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Structure Condition", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtStructure_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtArea.getText().toString())) {
                    //Toast.makeText(Industrial4.this, "Enter Area!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Area", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtArea.requestFocus();
                    return;
                }


                else {
                    alertDialog.dismiss();
                }

                // check if user updating note
                if (shouldUpdate && note != null) {
                    // update note by it's id
                    updateNote(dynamicEts_no.getText().toString(),
                            dynamicEtBlock_name.getText().toString(),
                            dynamicEtSlabs.getText().toString(),
                            dynamicEtHeight.getText().toString(),
                            dynamicEtYr_Cons.getText().toString(),
                            dynamicEtType_Cons.getText().toString(),
                            dynamicEtStructure_Cons.getText().toString(),
                            dynamicEtArea.getText().toString(),position);
              /*      updateIndustrialData(dynamicEtBlock_name.getText().toString(), position);
                    updateIndustrialData(dynamicEtSlabs.getText().toString(), position);
                    updateIndustrialData(dynamicEtHeight.getText().toString(), position);
                    updateIndustrialData(dynamicEtYr_Cons.getText().toString(), position);
                    updateIndustrialData(dynamicEtType_Cons.getText().toString(), position);
                    updateIndustrialData(dynamicEtStructure_Cons.getText().toString(), position);
                    updateIndustrialData(dynamicEtArea.getText().toString(), position);*/

                } else {
                    // create new note
                    createNote(dynamicEts_no.getText().toString(),
                            dynamicEtBlock_name.getText().toString(),
                            dynamicEtSlabs.getText().toString(),
                            dynamicEtHeight.getText().toString(),
                            dynamicEtYr_Cons.getText().toString(),
                            dynamicEtType_Cons.getText().toString(),
                            dynamicEtStructure_Cons.getText().toString(),
                            dynamicEtArea.getText().toString());
                    /*createNote(dynamicEtBlock_name.getText().toString());
                    createNote(dynamicEtSlabs.getText().toString());
                    createNote(dynamicEtHeight.getText().toString());
                    createNote(dynamicEtYr_Cons.getText().toString());
                    createNote(dynamicEtType_Cons.getText().toString());
                    createNote(dynamicEtStructure_Cons.getText().toString());
                    createNote(dynamicEtArea.getText().toString());*/
                }
            }
        });
    }



    /**
     * Toggling list and empty notes view
     */
    public static void toggleEmptyNotes() {
        // you can check notesList.size() > 0

        if (db.getSpecialAssetsCount() > 0) {
            noNotesView.setVisibility(View.GONE);
        } else {
            noNotesView.setVisibility(View.VISIBLE);
        }
    }

}
