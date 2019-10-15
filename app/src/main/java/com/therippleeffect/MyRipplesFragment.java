package com.therippleeffect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class MyRipplesFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final Puddle[] puddleItem = new Puddle[1];
        View rootview = inflater.inflate(R.layout.puddles_list_view, container, false);
        final ArrayList<Puddle> puddlesList = new ArrayList<>();
        final ListView puddlesListView = rootview.findViewById(R.id.list_of_puddles_listView);
        final PuddleAdapter puddleAdapter = new PuddleAdapter(getActivity(), puddlesList);
        puddlesListView.setAdapter(puddleAdapter);
        database.getReference().child("Puddles").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String keyofnewChild = dataSnapshot.getKey();
                puddleItem[0] = new Puddle(-1,
                        dataSnapshot.child(Puddle.nameKey).getValue().toString(),
                        dataSnapshot.child(Puddle.initiatorKey).getValue().toString(),
                        dataSnapshot.child(Puddle.questKey).getValue().toString(),
                        dataSnapshot.child(Puddle.countryKey).getValue().toString(),
                        dataSnapshot.child(Puddle.cityKey).getValue().toString(),
                        dataSnapshot.child(Puddle.reqRipplesKey).getValue().toString(),
                        dataSnapshot.child(Puddle.createdRipplesKey).getValue().toString(),
                        dataSnapshot.child(Puddle.typeKey).getValue().toString(),
                        dataSnapshot.child(Puddle.statusKey).getValue().toString(),
                        dataSnapshot.child(Puddle.credibilityKey).getValue().toString(),
                        dataSnapshot.child(Puddle.reportsKey).getValue().toString(),
                        dataSnapshot.child(Puddle.detailsKey).getValue().toString(),
                        dataSnapshot.child(Puddle.dateKey).getValue().toString());
                puddlesList.add(puddleItem[0]);
                puddleAdapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) { }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        puddlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Puddle puddle = puddlesList.get(i);
                Intent readQuestIntent = new Intent(getContext(), AcceptQuestActivity.class);
                readQuestIntent.putExtra(Puddle.nameKey, puddle.getPuddleName());
                readQuestIntent.putExtra(Puddle.initiatorKey, puddle.getPuddleInitiator());
                readQuestIntent.putExtra(Puddle.questKey, puddle.getPuddleQuest());
                readQuestIntent.putExtra(Puddle.countryKey, puddle.getPuddleCountryLocation());
                readQuestIntent.putExtra(Puddle.cityKey, puddle.getPuddleCityLocation());
                readQuestIntent.putExtra(Puddle.reqRipplesKey, puddle.getPuddleRequiredRipples());
                readQuestIntent.putExtra(Puddle.createdRipplesKey, puddle.getPuddleCreatedRipples());
                readQuestIntent.putExtra(Puddle.typeKey, puddle.getPuddleType());
                readQuestIntent.putExtra(Puddle.statusKey, puddle.getPuddleStatus());
                readQuestIntent.putExtra(Puddle.credibilityKey, puddle.getPuddleCredibilityBoostsNumber());
                readQuestIntent.putExtra(Puddle.reportsKey, puddle.getPuddleCredibilityReportsNumber());
                readQuestIntent.putExtra(Puddle.detailsKey, puddle.getPuddleDetails());
                readQuestIntent.putExtra(Puddle.dateKey,puddle.getPuddleDateCreated());

                startActivity(readQuestIntent);
            }
        });
        return rootview;
    }
}
