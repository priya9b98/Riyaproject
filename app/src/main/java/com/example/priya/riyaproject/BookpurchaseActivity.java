package com.example.priya.riyaproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Calendar;

public class BookpurchaseActivity extends AppCompatActivity {
    TextView bookna,rate1,descr,borrowdate,returndae;
    ImageView bookimag;
    Double bid1,userid;
    Button btnborrow,btncart,Btnbdate,btnredate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookpurchase);
        final Bundle b = getIntent().getBundleExtra("bund");

        bid1=b.getDouble("bid");
        userid=b.getDouble("userid");
        final String bookn = b.getString("na");
        final String bimage = b.getString("image");
        final String brate = b.getString("rate");

        bookna = findViewById(R.id.textView2);
        borrowdate = findViewById(R.id.borroedate);
        returndae = findViewById(R.id.returndate);
        Btnbdate = findViewById(R.id.btnbdate);
        bookna.setText(bookn);
        rate1 = findViewById(R.id.textView4);
        rate1.setText(brate);
        descr = findViewById(R.id.description);
        bookimag = findViewById(R.id.bimage);
        RequestOptions p = new RequestOptions();
        p.placeholder(R.mipmap.ic_launcher);
        Glide.with(getApplicationContext()).applyDefaultRequestOptions(p).load(bimage).into(bookimag);
        btnborrow = findViewById(R.id.borrow);
        btncart = findViewById(R.id.btncart);
        btnborrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(BookpurchaseActivity.this, BorrowActivity.class);
                b.putString("dateborrow",borrowdate.getText().toString());
                b.putString("returndate",returndae.getText().toString());
                intent.putExtra("bund", b);
                startActivity(intent);

            }
        });

        this.showDatepickerDialog();
    }
            private void showDatepickerDialog() {
                Btnbdate = findViewById(R.id.btnbdate);
                btnredate = findViewById(R.id.btnreturn);

                Btnbdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuffer str=new StringBuffer();
                                str.append(dayOfMonth);
                                str.append("-");
                                str.append(month+1);
                                str.append("-");
                                str.append(year);

                                borrowdate.setText(str.toString());
                            }
                        };
                        Calendar now = Calendar.getInstance();
                        int year = now.get(java.util.Calendar.YEAR);
                        int month = now.get(java.util.Calendar.MONTH);
                        int day = now.get(java.util.Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerDialog = new DatePickerDialog(   BookpurchaseActivity.this,dateSetListener, year, month, day);
                        datePickerDialog.show();

                    }
                });


                btnredate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatePickerDialog.OnDateSetListener onDateSetListener=new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                StringBuffer buff=new StringBuffer();
                                buff.append(dayOfMonth);
                                buff.append("-");
                                buff.append(month+1);
                                buff.append("-");
                                buff.append(year);

                                returndae.setText(buff.toString());
                            }
                        };
                        Calendar now = Calendar.getInstance();
                        int year = now.get(java.util.Calendar.YEAR);
                        int month = now.get(java.util.Calendar.MONTH);
                        int day = now.get(java.util.Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePickerd = new DatePickerDialog(   BookpurchaseActivity.this,onDateSetListener, year, month, day);
                        datePickerd.show();


                    }
                });



            }
        }
