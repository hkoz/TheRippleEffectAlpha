//package com.therippleeffect;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//
//
//public class SearchActivity extends AppCompatActivity {
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_menu,menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.create_new_puddle)
//        {startActivity(new Intent(SearchActivity.this, CreateActivity.class ));}
//        else if (item.getItemId() == R.id.logout)
//        {
//            FirebaseAuth mAuth = FirebaseAuth.getInstance();
//            mAuth.signOut();
//            startActivity(new Intent(SearchActivity.this,SingInActivity.class ));}
//        return super.onOptionsItemSelected(item);
//    }
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_search);
//        setTitle(getString(R.string.create_new_ripple));
//        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        final ArrayList<Puddle> puddlesList = new ArrayList<>();
//        final ArrayList<String> keysList = new ArrayList<>();
//        final ListView puddlesListView = findViewById(R.id.search_list);
//        final PuddleAdapter puddleAdapter = new PuddleAdapter(this, puddlesList);
//        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Puddles");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                keysList.add(dataSnapshot.getKey());
//                    Puddle puddleItem = new Puddle(-1,
//                            dataSnapshot.child(Puddle.nameKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.initiatorKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.questKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.countryKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.cityKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.reqRipplesKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.createdRipplesKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.typeKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.statusKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.credibilityKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.reportsKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.detailsKey).getValue().toString(),
//                            dataSnapshot.child(Puddle.dateKey).getValue().toString());
//                    puddlesList.add(puddleItem);
//                    puddleAdapter.notifyDataSetChanged();
//                }
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//            }
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) { }
//        });
//
//        puddlesListView.setAdapter(puddleAdapter);
//        puddlesListView.setEmptyView(findViewById(R.id.empty_view));
//        puddlesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Puddle puddle = puddlesList.get(i);
//                Intent readQuestIntent = new Intent(SearchActivity.this, AcceptQuestActivity.class);
//                readQuestIntent.putExtra(Puddle.nameKey, puddle.getPuddleName());
//                readQuestIntent.putExtra(Puddle.initiatorKey, puddle.getPuddleInitiator());
//                readQuestIntent.putExtra(Puddle.questKey, puddle.getPuddleQuest());
//                readQuestIntent.putExtra(Puddle.countryKey, puddle.getPuddleCountryLocation());
//                readQuestIntent.putExtra(Puddle.cityKey, puddle.getPuddleCityLocation());
//                readQuestIntent.putExtra(Puddle.reqRipplesKey, puddle.getPuddleRequiredRipples());
//                readQuestIntent.putExtra(Puddle.createdRipplesKey, puddle.getPuddleCreatedRipples());
//                readQuestIntent.putExtra(Puddle.typeKey, puddle.getPuddleType());
//                readQuestIntent.putExtra(Puddle.statusKey, puddle.getPuddleStatus());
//                readQuestIntent.putExtra(Puddle.credibilityKey, puddle.getPuddleCredibilityBoostsNumber());
//                readQuestIntent.putExtra(Puddle.reportsKey, puddle.getPuddleCredibilityReportsNumber());
//                readQuestIntent.putExtra(Puddle.detailsKey, puddle.getPuddleDetails());
//                readQuestIntent.putExtra(Puddle.dateKey,puddle.getPuddleDateCreated());
//
//                readQuestIntent.putExtra("key", keysList.get(i));
//
//                startActivity(readQuestIntent);
//            }
//        });
//        puddlesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                puddlesList.remove(i);
//                databaseReference.child(keysList.get(i)).removeValue();
//                keysList.remove(i);
//                puddleAdapter.notifyDataSetChanged();
//                return false;
//            }
//        });
//    }
//}
