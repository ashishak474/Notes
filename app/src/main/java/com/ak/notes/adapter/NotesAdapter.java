package com.ak.notes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.notes.R;
import com.ak.notes.activity.MainActivity;
import com.ak.notes.activity.NotesActivity;
import com.ak.notes.activity.NotesShowActivity;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.NotesEntity;
import com.ak.notes.modals.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    public interface DataDelete {
        void onLongClick();

        void onClick(int id);
    }

    private List<NotesEntity> list;
    private final Context context;
    private DataDelete dataDelete;
    private List<NotesEntity> noteSource;
    private Timer timer;


    public NotesAdapter(List<NotesEntity> list, Context context, DataDelete dataDelete) {
        this.list = list;
        this.context = context;
        this.dataDelete = dataDelete;
        noteSource = list;
    }

    @NonNull

    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesHolder holder, final int position) {
        NotesEntity notesEntity = list.get(position);
        holder.tittle.setText(notesEntity.getTitle());
        holder.discription.setText(notesEntity.getDetails());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataDelete.onClick(list.get(position).getId());
            }

        });


        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //    dataDelete.onLongClick();

                AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
                builder1.setMessage("Do You Want Delete ?.");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                NotesDatabase.getInstance(context.getApplicationContext()).notesDao().deleteNode(list.get(position).getId());
                                list.remove(position);
                                notifyDataSetChanged();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.create()
                        .show();


                return false;
            }
        });
        GradientDrawable gradientDrawable = (GradientDrawable) holder.linearLayout.getBackground();


        if (notesEntity.getColor() != null) {
            gradientDrawable.setColor(Color.parseColor(notesEntity.getColor()));

        } else {

            gradientDrawable.setColor(Color.parseColor("#FFFFFF"));
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NotesHolder extends RecyclerView.ViewHolder {

        private final TextView tittle;
        private final TextView discription;
        private CheckBox checkBox;
        private LinearLayout linearLayout;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.note_tittle);
            discription = itemView.findViewById(R.id.note_disc);
            checkBox = itemView.findViewById(R.id.checkboxDelete);
            linearLayout = itemView.findViewById(R.id.note_layout);

        }
    }

    public void searchNotes(final String searchkeyword) {
//        timer=new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
        if (searchkeyword.trim().isEmpty()) {
            list = noteSource;
        } else {
            ArrayList<NotesEntity> temp = new ArrayList();
            for (NotesEntity note : noteSource) {
                if (note.getTitle().toLowerCase().contains(searchkeyword.toLowerCase())
                        || note.getDetails().toLowerCase().contains(searchkeyword.toLowerCase())) {
                    Log.e("MAINACTIVITY", "getTittle");
                    temp.add(note);
                }

            }
            noteSource = temp;
            notifyDataSetChanged();
        }
//                new Handler(Looper.getMainLooper()).post(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.e("MAINACTIVITY","Handler");
//                        notifyDataSetChanged();
//                    }
//                });
//            }
//        },500);
//    }
//
//    public void cancleTimer(){
//        if(timer != null){
//            timer.cancel();
//        }
//    }
    }
}
