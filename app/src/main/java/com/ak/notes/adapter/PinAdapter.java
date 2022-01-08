package com.ak.notes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ak.notes.R;
import com.ak.notes.activity.MainActivity;
import com.ak.notes.activity.NotesShowActivity;
import com.ak.notes.database.NotesDatabase;
import com.ak.notes.database.PinEntity;

import java.util.List;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.PinHolder> {
    private List<PinEntity> list;
    private Context context;

    public PinAdapter(List<PinEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        return new PinHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PinHolder holder, final int position) {
        holder.tittle.setText(list.get(position).getTitle());
        holder.discription.setText(list.get(position).getDetail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(context, NotesShowActivity.class);
                i.putExtra("notesId",list.get(position).getId());
                context.startActivity(i);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do You Want Delete ?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NotesDatabase.getInstance(context.getApplicationContext()).notesDao().deletePin(list.get(position).getId());
                        list.remove(position);
                        notifyDataSetChanged();
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        builder.create()
                .show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class PinHolder extends RecyclerView.ViewHolder{
        private  TextView tittle;
        private  TextView discription;
        public PinHolder(@NonNull View itemView) {
            super(itemView);
            tittle = itemView.findViewById(R.id.note_tittle);
            discription = itemView.findViewById(R.id.note_disc);
        }
    }
}
