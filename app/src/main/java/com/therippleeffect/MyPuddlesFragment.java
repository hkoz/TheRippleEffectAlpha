package com.therippleeffect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyPuddlesFragment extends Fragment {

    public static PuddleAdapter puddleAdapter;
    public static ArrayList<Puddle> puddlesList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.puddles_list_view, container, false);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        puddlesList = new ArrayList<>();
        final ArrayList<String> keysList = new ArrayList<>();
        final ListView puddlesListView = rootview.findViewById(R.id.search_list);
        puddleAdapter = new PuddleAdapter(getContext(), puddlesList);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                keysList.add(dataSnapshot.getKey());

                Puddle puddleItem = new Puddle(
                        dataSnapshot.getKey(),
                        dataSnapshot.child(Puddle.mainImageKey).getValue().toString(),
                        dataSnapshot.child(Puddle.nameKey).getValue().toString(),
                        dataSnapshot.child(Puddle.initiatorKey).getValue().toString(),
                        dataSnapshot.child(Puddle.questKey).getValue().toString(),
                        dataSnapshot.child(Puddle.countryKey).getValue().toString(),
                        dataSnapshot.child(Puddle.cityKey).getValue().toString(),
                        dataSnapshot.child(Puddle.locationLongitudeKey).getValue().toString(),
                        dataSnapshot.child(Puddle.locationLatitudeKey).getValue().toString(),
                        Integer.parseInt(dataSnapshot.child(Puddle.reqRipplesKey).getValue().toString()),
                        Integer.parseInt(dataSnapshot.child(Puddle.createdRipplesKey).getValue().toString()),
                        dataSnapshot.child(Puddle.typeKey).getValue().toString(),
                        dataSnapshot.child(Puddle.statusKey).getValue().toString(),
                        Integer.parseInt(dataSnapshot.child(Puddle.credibilityKey).getValue().toString()),
                        Integer.parseInt(dataSnapshot.child(Puddle.reportsKey).getValue().toString()),
                        dataSnapshot.child(Puddle.detailsKey).getValue().toString(),
                        dataSnapshot.child(Puddle.dateKey).getValue().toString(),
                        Puddle.StringToArrayList(dataSnapshot.child(Puddle.heroesArrayKey).getValue().toString()),
                        ImageListItem.createImageItemListArrayListFromString(dataSnapshot.child(Puddle.imagesArrayKey).getValue().toString()));
                puddlesList.add(puddleItem);
                puddleAdapter.notifyDataSetChanged();
                }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                puddleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                ((BaseAdapter) puddlesListView.getAdapter()).notifyDataSetChanged();
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                puddleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
        puddlesListView.setAdapter(puddleAdapter);
        puddlesListView.setEmptyView(rootview.findViewById(R.id.empty_view));
        puddlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Puddle puddle = puddlesList.get(i);
                Intent readQuestIntent = new Intent(getContext(), AcceptQuestActivity.class);
                readQuestIntent.putExtra(Puddle.key,puddle.getPuddleKey());
                readQuestIntent.putExtra("position In listView",i);
                readQuestIntent.putExtra("source", "From MyPuddlesFragment");
                startActivity(readQuestIntent);
            }
        });
        puddlesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                puddlesList.get(i);
                puddlesList.remove(i);
                databaseReference.child(keysList.get(i)).removeValue();
                keysList.remove(i);
                puddleAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return rootview;
    }
    @Override
    public void onStart() {
        super.onStart();
        puddleAdapter.notifyDataSetChanged();
    }

}

