package com.example.priya.riyaproject;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class SearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] genre={"comedy","action","drama","thriller"};
    String[] year={"0","1900>","1900-1920","1920-1940","1940-1960","1960-1980","1980-2000","2000-2005","2005-2010","2010-2015"};
    SearchView searchView;
    Double userid;
    DynamoDBMapper dynamoDBMapper;
    ImageView rat;
    int k=0;
    String cust_email;
    public List<Books> list;
    Bundle bundle;
    TextView user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AWSMobileClient.getInstance().initialize(this).execute();
        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();




        Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        Spinner spin1 = (Spinner) findViewById(R.id.spinner1);
        spin1.setOnItemSelectedListener(this);
        searchView=findViewById(R.id.searchvi);
        rat=findViewById(R.id.rating);
        user=findViewById(R.id.testing2);
        Intent r=getIntent();
        list=new ArrayList<>();
        list=null;
        cust_email=r.getStringExtra("email");
         userid=r.getDoubleExtra("userid",0);
         user.setText(userid.toString());
        searchView.setQueryHint("find books");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
               searchforbooks(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,genre);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setPrompt("GENRE");
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

        ArrayAdapter aa1 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,year);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin1.setAdapter(aa1);
        spin1.setPrompt("Release Year");

        rat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it= new Intent(SearchActivity.this,List_of_booksActivity.class);
                it.putExtra("number",2);
                it.putExtra("userid",userid);
                it.putExtra("email",cust_email);

                startActivity(it);
            }
        });
    }

   private void searchforbooks(String query) {
        Map<String, AttributeValue> ev=new HashMap<>();
        ev.put(":val1", new AttributeValue().withS(query));

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("BookName = :val1").withExpressionAttributeValues(ev);
        new Thread(new Runnable() {
            @Override
            public void run() {

                 List<Books> lis1=dynamoDBMapper.scan(Books.class,scanExpression);
                 final List<Books> list1=lis1;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(list1.get(0) == null){
                            Toast.makeText(SearchActivity.this,"not available",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent intent1 =new Intent(SearchActivity.this,BookpurchaseActivity.class);
                            Bundle b=new Bundle();
                            b.putString("na",list1.get(0).getBookName());
                            b.putString("image",list1.get(0).getImageUrl());
                            b.putString("rate",list1.get(0).getRating());
                            b.putString("price",list1.get(0).getPrice());
                            b.putDouble("userid",userid);
                            b.putString("email",cust_email);
                            b.putDouble("bid",list1.get(0).getISBN());
                            intent1.putExtra("bund",b);


                            startActivity(intent1);
                        }

                    }
                });


            }
        }).start();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Intent i=new Intent(SearchActivity.this,List_of_booksActivity.class);
        if(year[position]=="1900>"){
            i.putExtra("number",1);
            i.putExtra("year","1900");
            i.putExtra("year2","1800");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);


        }
        else if (year[position]=="1900-1920"){
            i.putExtra("number",1);
            i.putExtra("year","1920");
            i.putExtra("year2","1900");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);


        }
        else if (year[position]=="1920-1940"){
            i.putExtra("number",1);
            i.putExtra("year","1940");
            i.putExtra("year2","1920");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

           /* try {
                FindBookwithSpecifiedattribute(dynamoDBMapper,"1940","1920");
              while(list==null) {k++;}
                    Bundle args = new Bundle();
                    args.putSerializable("ARRAYLIST", (Serializable) list);
                    i.putExtra("BUNDLE", args);
                    startActivity(i);


            } catch (Exception e) {
                e.printStackTrace();
            }*/


           startActivity(i);


        }
        else if (year[position]=="1940-1960"){
            i.putExtra("number",1);
            i.putExtra("year","1960");
            i.putExtra("year2","1940");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);


        }
        else if (year[position]=="1960-1980"){
            i.putExtra("number",1);
            i.putExtra("year","1980");
            i.putExtra("year2","1960");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);

        }
        else if (year[position]=="1980-2000"){
            i.putExtra("number",1);
            i.putExtra("year","2000");
            i.putExtra("year2","1980");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);
        }
        else if (year[position]=="2000-2005"){
            i.putExtra("number",1);
            i.putExtra("year","2005");
            i.putExtra("year2","2000");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);

        }
        else if (year[position]=="2005-2010"){
            i.putExtra("number",1);
            i.putExtra("year","2010");
            i.putExtra("year2","2005");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);

        }
        else if (year[position]=="2010-2015"){
            i.putExtra("number",1);
            i.putExtra("year","2015");
            i.putExtra("year2","2010");
            i.putExtra("userid",userid);
            i.putExtra("email",cust_email);

            startActivity(i);

        }
        else{}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /*private void FindBookwithSpecifiedattribute(final DynamoDBMapper dynamoDBMapper, String year1,String year2) throws  Exception {
        Map<String, AttributeValue> ev=new HashMap<>();
        ev.put(":val1", new AttributeValue().withS(year1));
        ev.put(":val2",new AttributeValue().withS(year2));

        final DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("PublicationYear between :val2 and :val1").withExpressionAttributeValues(ev);

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Books> list1=dynamoDBMapper.scan(Books.class,scanExpression);
                list=list1;



            }
        }).start();





    }*/
}

