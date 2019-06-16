package com.example.priya.riyaproject;




import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class Fetchitem extends AsyncTask<Void,Void,List<String>> {
    String data ="";
    private List_of_booksActivity activity;
    Context mc;
    List<String> list;
    Fetchitem(List_of_booksActivity context,Context mc){
        activity=context;
        this.mc=mc;
    }
    @Override
    protected List<String> doInBackground(Void... voids) {
        try {
            URL url = new URL("http://useruserfinal-env.xwik2y2dnd.us-east-1.elasticbeanstalk.com/api/1239");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            list=new ArrayList<>();


            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            //    line = bufferedReader.readLine();
            //  i++;
            int j=0;
            int i=0;
           /*while(j<6){
                if(data.charAt(i)=='<' ){
                    i=i+3;
                    list.add(f);
                     f="";
                     j++;
                }
                else
                   f=f+Character.toString(data.charAt(i));
                i++;
            }

*/
            String f="";
            while(j<6){
                f="";

                while(data.charAt(i)!='<'){
                    f=f.concat(Character.toString(data.charAt(i)));
                    i++;
                }
                if(data.charAt(i-3)!='n'){
                    list.add(f);
                    j++;}
                i=i+4;}




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    protected void onPostExecute(List<String> str) {
        super.onPostExecute(str);
        str=list;
        activity.setList(str);

    }

}

