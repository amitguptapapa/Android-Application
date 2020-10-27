package com.vis.android.Activities.Industrial;

/**
 * Created by ravi on 20/02/18.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vis.android.Database.TableIndustrial;
import com.vis.android.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class NotesAdapterIndustrial extends RecyclerView.Adapter<NotesAdapterIndustrial.MyViewHolder> {

    private Context context;
    private List<TableIndustrial> notesList;


    ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //public TextView note;
        public TextView dot;
        public TextView timestamp;

        public TextView tvSnoEdit, tvBlockNameEdit,tvTotalSlabEdit,tvHeightEdit,tvYrConsEdit,tvTypeConsEdit,tvStrucCondEdit,tvAreaEdit;


        public ArrayList<HashMap<String, String>> sub_cat = new ArrayList<HashMap<String, String>>();

        public ImageView remove, edit;

        public MyViewHolder(View view) {
            super(view);
            //note = view.findViewById(R.id.note);
            //dot = view.findViewById(R.id.dot);
            //timestamp = view.findViewById(R.id.timestamp);


            tvSnoEdit = view.findViewById(R.id.tvSnoEdit);
            tvBlockNameEdit = view.findViewById(R.id.tvBlockNameEdit);
            tvTotalSlabEdit = view.findViewById(R.id.tvTotalSlabEdit);
            tvHeightEdit = view.findViewById(R.id.tvHeightEdit);
            tvYrConsEdit = view.findViewById(R.id.tvYrConsEdit);
            tvTypeConsEdit = view.findViewById(R.id.tvTypeConsEdit);
            tvStrucCondEdit = view.findViewById(R.id.tvStrucCondEdit);
            tvAreaEdit = view.findViewById(R.id.tvAreaEdit);

            remove = view.findViewById(R.id.remove);
            edit = view.findViewById(R.id.edit);



        }
    }


    public NotesAdapterIndustrial(Context context, List<TableIndustrial> notesList) {
        this.context = context;
        this.notesList = notesList;
        //this.sub_cat = sub_cat;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        TableIndustrial note = notesList.get(position);

        Log.v("position2", String.valueOf(position));

        holder.tvSnoEdit.setText(note.getSNo());
        holder.tvBlockNameEdit.setText(note.getBlockName());
        holder.tvTotalSlabEdit.setText(note.getTotalSlab());
        holder.tvHeightEdit.setText(note.getHeight());
        holder.tvYrConsEdit.setText(note.getYrCons());
        holder.tvTypeConsEdit.setText(note.getTypeCons());
        holder.tvStrucCondEdit.setText(note.getStructure());
        holder.tvAreaEdit.setText(note.getArea());

/*
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("posss", String.valueOf(position));
                //Industrial4.deleteNote(position);
                //Industrial4.deleteNote(position);
                Industrial4.showActionsDialog(position);
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Industrial4.showNoteDialog(true, notesList.get(position), position);
            }
        });*/





        // Displaying dot from HTML character code
//        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
      //  holder.timestamp.setText(formatDate(note.getTimestamp()));
    }


   /* private void deleteNote(int position) {

        Log.v("positionn", String.valueOf(position));

        // deleting the note from db
        Industrial4.db.deleteIndustrialData(notesList.get(position));

        // removing the note from the list
        Industrial4.notesList.remove(position);
        //sub_cat.remove(position);
        Industrial4.mAdapter.notifyItemRemoved(position);

        Industrial4.toggleEmptyNotes();
    }*/

 /*   public void showNoteDialog(final boolean shouldUpdate, final TableIndustrial note, final int position) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
        View view = layoutInflaterAndroid.inflate(R.layout.note_dialog, null);
        //View view = layoutInflaterAndroid.inflate(R.layout.dynamic_row, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
        alertDialogBuilderUserInput.setView(view);

        final EditText dynamicEts_no;
        final EditText dynamicEtBlock_name;
        final EditText dynamicEtSlabs;
        final EditText dynamicEtHeight;
        final EditText dynamicEtYr_Cons;
        final EditText dynamicEtType_Cons;
        final EditText dynamicEtStructure_Cons;
        final EditText dynamicEtArea;

        dynamicEts_no = view.findViewById(R.id.dynamicet_sno);
        dynamicEtBlock_name = view.findViewById(R.id.dynamicet_blockno);
        dynamicEtSlabs = view.findViewById(R.id.dynamicet_floors);
        dynamicEtHeight = view.findViewById(R.id.dynamicet_height);
        dynamicEtYr_Cons = view.findViewById(R.id.dynamicet_yearCons);
        dynamicEtType_Cons = view.findViewById(R.id.dynamicet_typeCons);
        dynamicEtStructure_Cons = view.findViewById(R.id.dynamicet_structure);
        dynamicEtArea = view.findViewById(R.id.dynamicet_area);

        TextView dialogTitle = view.findViewById(R.id.dialog_title);
        dialogTitle.setText(!shouldUpdate ? context.getString(R.string.lbl_new_note_title) : context.getString(R.string.lbl_edit_note_title));

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
                    //Toast.makeText(context, "Enter S.No.!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter S.No.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEts_no.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(dynamicEtBlock_name.getText().toString())) {
                    //Toast.makeText(context, "Enter Block Name!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Block Name", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtBlock_name.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(dynamicEtSlabs.getText().toString())) {
                    // Toast.makeText(context, "Enter Total Slabs!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Total Slabs/Floors", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtSlabs.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtHeight.getText().toString())) {
                    //   Toast.makeText(context, "Enter Height!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Height", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtHeight.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtYr_Cons.getText().toString())) {
                    //Toast.makeText(context, "Enter Yr Cons!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Year of Construction", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtYr_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtType_Cons.getText().toString())) {
                    // Toast.makeText(context, "Enter Type Cons!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Type of Construction", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtType_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtStructure_Cons.getText().toString())) {
                    // Toast.makeText(context, "Enter Structure!", Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(dynamicEts_no, "Please Enter Structure Condition", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    dynamicEtStructure_Cons.requestFocus();
                    return;
                }

                else if (TextUtils.isEmpty(dynamicEtArea.getText().toString())) {
                    //Toast.makeText(context, "Enter Area!", Toast.LENGTH_SHORT).show();
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
                    Industrial4.updateNote(dynamicEts_no.getText().toString(),
                            dynamicEtBlock_name.getText().toString(),
                            dynamicEtSlabs.getText().toString(),
                            dynamicEtHeight.getText().toString(),
                            dynamicEtYr_Cons.getText().toString(),
                            dynamicEtType_Cons.getText().toString(),
                            dynamicEtStructure_Cons.getText().toString(),
                            dynamicEtArea.getText().toString(),position);

                } else {
                    // create new note
                    Industrial4.createNote(dynamicEts_no.getText().toString(),
                            dynamicEtBlock_name.getText().toString(),
                            dynamicEtSlabs.getText().toString(),
                            dynamicEtHeight.getText().toString(),
                            dynamicEtYr_Cons.getText().toString(),
                            dynamicEtType_Cons.getText().toString(),
                            dynamicEtStructure_Cons.getText().toString(),
                            dynamicEtArea.getText().toString());
                }
            }
        });
    }

    private void deleteNote(int position) {

        Log.v("positionn", String.valueOf(position));

        // deleting the note from db
        Industrial4.db.deleteIndustrialData(notesList.get(position));

        // removing the note from the list
        notesList.remove(position);
        //sub_cat.remove(position);
        Industrial4.mAdapter.notifyItemRemoved(position);

        Industrial4.toggleEmptyNotes();
    }*/

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    /**
     * Formatting timestamp to `MMM d` format
     * Input: 2018-02-21 00:15:42
     * Output: Feb 21
     */
    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("MMM d");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }
}
