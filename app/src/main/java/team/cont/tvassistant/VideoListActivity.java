package team.cont.tvassistant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    int ID,userID;
    String ListName;
    TextView Tname;
    ProgressBar progressBar;
    private List<View> allEds;

    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("List_ID", 1);
            ListName = extras.getString("List_Name",null);
            userID = extras.getInt("userID",1);

        }
        Tname = (TextView) findViewById(R.id.textView19);
        Tname.setText(ListName);

        new LoadVideo().execute();

    }

    private class LoadVideo extends AsyncTask<String,String,String> {

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

                String sql = "SELECT video.id, video.name, video.img_url, video.rating_value FROM video,list_logs WHERE list_logs.list_id="+ID+" AND list_logs.video_id=video.id";

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(2));
                    FURLs.add(rs.getString(3));
                    IDs.add(rs.getInt(1));
                    FRating.add(rs.getFloat(4));
                    //   Log.w("FNAMES== ",FNames.get(ind));
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
            allEds = new ArrayList<View>();
            //находим наш linear который у нас под кнопкой add edittext в activity_main.xml
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear11);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные
            ShowList(linear,FNames,ind,FURLs,IDs,FRating);

        }
    }

    private void ShowList(LinearLayout liner,ArrayList<String> names,int numbers,ArrayList<String> urls,ArrayList<Integer> ids, ArrayList<Float> FR){
        for(int i=0;i<numbers;i++) {
            final View view = getLayoutInflater().inflate(R.layout.list_video_layout, null);
            RatingBar RB = (RatingBar) view.findViewById(R.id.ratingBar2);
            RB.setRating(FR.get(i));
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            ImageView imageView2m = (ImageView) view.findViewById(R.id.imageView2m);
            tv4.setText(names.get(i));
            new DownloadImageTask((ImageView) view.findViewById(R.id.imageView2m)).execute(urls.get(i));
            //добавляем все что создаем в массив
            allEds.add(view);
            final int id=i;
            final String name=names.get(i);
            //добавляем елементы в linearlayout
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    finish();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            liner.addView(view);

            imageView2m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getIntent();
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    finish();
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



