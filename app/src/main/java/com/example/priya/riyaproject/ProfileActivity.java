package com.example.priya.riyaproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class ProfileActivity extends AppCompatActivity {
    Button books,store;
    TextView name;
    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setTitle("HungryMinds");
        Intent inty=getIntent();
        String name1=inty.getStringExtra("name");
        final Double userid=inty.getDoubleExtra("userid",0);
        name=(TextView)findViewById(R.id.profile_name);
        name.setText(name1);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
                dynamoDBMapper = DynamoDBMapper.builder()
                        .dynamoDBClient(dynamoDBClient)
                        .awsConfiguration(
                                AWSMobileClient.getInstance().getConfiguration())
                        .build();
            }
        }).execute();

        books=findViewById(R.id.srchbook);
        store=findViewById(R.id.srchstore);

        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent iy=new Intent(ProfileActivity.this,SearchActivity.class);
              iy.putExtra("userid",userid);
                startActivity(iy);
            }
        });




        LinearLayout gallary=findViewById(R.id.gallary);
        LinearLayout gallary1=findViewById(R.id.gallary1);
        LayoutInflater inflater=LayoutInflater.from(this);
        for(int i=0;i<6;i++){
            View view=inflater.inflate(R.layout.item_list1,gallary,false);
            TextView textView=view.findViewById(R.id.textView);
            textView.setText("item"+i);
            ImageView imageView=view.findViewById(R.id.images);
            imageView.setImageResource(R.mipmap.ic_launcher);
            gallary.addView(view);

        }

        LayoutInflater inflater1=LayoutInflater.from(this);
        for(int i=0;i<6;i++){
            View view=inflater.inflate(R.layout.item_list1,gallary1,false);
            TextView textView=view.findViewById(R.id.textView);
            textView.setText("item"+i);
            ImageView imageView1=view.findViewById(R.id.images);
            imageView1.setImageResource(R.mipmap.ic_launcher);
            gallary1.addView(view);

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_logout:
                Intent intent=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(intent);
            case R.id.action_setting:
                Toast.makeText(getApplicationContext(),"setting",Toast.LENGTH_SHORT).show();
            default:
                return false;

        }
    }




}
