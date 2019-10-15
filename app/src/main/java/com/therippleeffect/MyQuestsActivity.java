package com.therippleeffect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MyQuestsActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quests);
        setTitle(getString(R.string.my_activity));
        ViewPager vp = findViewById(R.id.viewPager);
        PuddlesFragmentPagerAdapter puddlesFragmentPagerAdapter = new PuddlesFragmentPagerAdapter(this, getSupportFragmentManager());
        vp.setAdapter(puddlesFragmentPagerAdapter);
        TabLayout tab = findViewById(R.id.tableLayout);
        tab.setupWithViewPager(vp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create_new_puddle)
        {startActivity(new Intent(MyQuestsActivity.this, CreateActivity.class ));}
       else if (item.getItemId() == R.id.logout)
        {
            mAuth.signOut();
            startActivity(new Intent(MyQuestsActivity.this,SingInActivity.class ));}
        return super.onOptionsItemSelected(item);
    }

}
