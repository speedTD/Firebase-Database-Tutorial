package com.developer.dinhduy.firebase_database_tutorial;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class LanguageAdapter  extends RecyclerView.Adapter<LanguageAdapter.LanguageHoder> {
    List<Language> languageList;
    Context context;
    DatabaseReference databaseReference;



    public LanguageAdapter(List<Language> languageList, Context context) {
        this.languageList = languageList;
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Duy");
        databaseReference.addChildEventListener(new GetKey());
    }

    @NonNull
    @Override
    public LanguageHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.custom_recyclerview,parent,false);
        return new LanguageHoder(view);
    }
 List<String> keylist= new ArrayList<>();
    @Override
    public void onBindViewHolder(@NonNull LanguageHoder holder, final int position) {
        Language language=languageList.get(position);
        holder.textView.setText(language.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // remove Item
                databaseReference.child(keylist.get(position)).removeValue();
                Toast.makeText(context, "deleted " +keylist.get(position).toString(), Toast.LENGTH_SHORT).show();

                MainActivity.getData();
            }
        });

    }

    @Override
    public int getItemCount() {
        return languageList.size();
    }

    public class LanguageHoder extends RecyclerView.ViewHolder {
        TextView textView;
        public LanguageHoder(View itemView) {
            super(itemView);
            textView=(TextView) itemView.findViewById(R.id.textView);
        }
    }
    public class GetKey implements ChildEventListener{

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
         // sorry :)))
            String key=dataSnapshot.getKey();
            keylist.add(key);
            notifyDataSetChanged();


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }
}
