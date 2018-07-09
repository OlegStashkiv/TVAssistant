package team.cont.tvassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {


    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    private List<View> allEds;
    Button bSearch;
    Spinner Sp;
    EditText sL;
    ProgressBar progressBar;

    String base="SELECT * FROM video WHERE ";
    String value="";
    String full_string="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bSearch = (Button) findViewById(R.id.button14);
        Sp = (Spinner) findViewById(R.id.spinner20);
        sL= (EditText) findViewById(R.id.searchLine);
        Sp.setSelection(0);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);


        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearch();
            }
        });

    }

    public void startSearch(){

        String textOfSpinner = Sp.getSelectedItem().toString();
        switch(textOfSpinner) {
                case "Назва":
                    value=" name Like '%"+sL.getText().toString()+"%'";
                    break;
                case "Актор":
                    value=" actor Like '%"+sL.getText().toString()+"%'";
                    break;
                case "Режисер":
                    value=" producer Like '%"+sL.getText().toString()+"%'";
                    break;
                case "Тег":
                    value=" tags Like '%"+sL.getText().toString()+"%'";
                    break;
                case "Опис":
                    value=" description Like '%"+sL.getText().toString()+"%'";
                    break;
        }


        full_string=base+value;
        Log.w("string",full_string);
        new LoadRes().execute();
    }

    private class LoadRes extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<String> FURLs = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Float> FRating = new ArrayList<>();
        String res="";
        int FID;
        int ind=0;

        Boolean isSuccess = false;
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String ... params){
            try {

                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();

                String sql = full_string;

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(2));
                    FURLs.add(rs.getString(8));
                    IDs.add(rs.getInt(1));
                    FRating.add(rs.getFloat(13));
                    ind++;
                }
                con.close();

            }
            catch(Exception e) {
                e.printStackTrace();
                res=e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //    super.onPostExecute(aVoid);
            Log.w("res",res);
            allEds = new ArrayList<View>();
            //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear40);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
            if(ind>0) {

                testq(linear, FNames, ind, FURLs, IDs, FRating);
            }else{
                linear.removeAllViews();
                TextView tv = (TextView) findViewById(R.id.textView);
                tv.setVisibility(View.VISIBLE);
            }

            //   new DownloadImageTask((ImageView) findViewById(R.id.imageLogo1)).execute(FURL);
        }
    }

    private void testq(LinearLayout liner,ArrayList<String> array,int numbers,ArrayList<String> urls,ArrayList<Integer> ids, ArrayList<Float> FR){
        liner.removeAllViews();
        for(int i=0;i<numbers;i++) {
            TextView tv = (TextView) findViewById(R.id.textView);
            tv.setVisibility(View.GONE);
            final View view = getLayoutInflater().inflate(R.layout.search_list, null);
            RatingBar RB = (RatingBar) view.findViewById(R.id.ratingBar2);
            RB.setRating(FR.get(i));
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            ImageView imageView2m = (ImageView) view.findViewById(R.id.imageView2m);
            tv4.setText(array.get(i));
            Log.w("qwerty",array.get(i));
            new DownloadImageTask((ImageView) view.findViewById(R.id.imageView2m)).execute(urls.get(i));
            //добавляем все что создаем в массив
            allEds.add(view);
            final int id=i;
            final String name=array.get(i);
            //добавляем елементы в linearlayout
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            liner.addView(view);

            imageView2m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SearchActivity.this, VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}