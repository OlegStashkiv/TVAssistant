package team.cont.tvassistant;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SavedActivity extends AppCompatActivity {

    // /Создаем список вьюх которые будут создаваться
    private List<View> allEds;
    //счетчик чисто декоративный для визуального отображения edittext'ov
    private int counter = 0;
    private SharedPreferences sPref;
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";


    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    String userEmail;
    String userLogin;
    int userID;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);

        SharedPreferences myPrefs =getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        userID=myPrefs.getInt(SAVED_ID,1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        new LoadSaveTask().execute();

    }


    private class LoadSaveTask extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<String> FURLs = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Integer> LIDS= new ArrayList<>();
        ArrayList<Float> FRating = new ArrayList<>();
        String res="";
        int ind=0;

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

                String sql = "SELECT video.id, video.img_url, video.name, saved.id,video.rating_value FROM video,saved WHERE saved.user_id="+userID+" and video.id=saved.film_id";

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(3));
                    FURLs.add(rs.getString(2));
                    IDs.add(rs.getInt(1));
                    LIDS.add(rs.getInt(4));
                    FRating.add(rs.getFloat(5));
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
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear5);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные

            displayMyView(linear,FNames,ind,FURLs,IDs,LIDS,FRating);
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

    private void displayMyView(LinearLayout liner,ArrayList<String> array,int numbers,ArrayList<String> urls,ArrayList<Integer> ids,ArrayList<Integer> lds,ArrayList<Float> FR){
        for(int i=0;i<numbers;i++) {

            final View view = getLayoutInflater().inflate(R.layout.saved_list, null);
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            ImageView imageView2m = (ImageView) view.findViewById(R.id.imageView2m);
            tv4.setText(array.get(i));
            Button remove = (Button) view.findViewById(R.id.button5);
            Button looked = (Button) view.findViewById(R.id.button2);
            RatingBar RB = (RatingBar) view.findViewById(R.id.ratingBar2);
            RB.setRating(FR.get(i));

            int s=i;

            looked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("IDDD==",String.valueOf(s));
                    new DelTask(lds.get(s)).execute();
                    new LookTask(ids.get(s)).execute();
                    looked.setText("Переглянуто");
                    //looked.setTextColor(Color.parseColor("#0DFC4B"));
                    looked.setEnabled(false);
                    remove.setEnabled(false);
                    Toast.makeText(SavedActivity.this, "Додано в переглянуті", Toast.LENGTH_SHORT).show();
                }
            });

            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.w("IDDD==",String.valueOf(s));
                    new DelTask(lds.get(s)).execute();
                    //remove.setText("Видалено");
                    //remove.setTextColor(Color.parseColor("#ED0000"));
                    Toast.makeText(SavedActivity.this, "Видалено", Toast.LENGTH_SHORT).show();
                    remove.setEnabled(false);
                    looked.setEnabled(false);
                }
            });
            new DownloadImageTask((ImageView) view.findViewById(R.id.imageView2m)).execute(urls.get(i));
            //добавляем все что создаем в массив
            allEds.add(view);
            allEds.add(view);
            int id=i;
            final String name=array.get(i);
            //добавляем елементы в linearlayout
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(SavedActivity.this, VideoActivity.class);
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
                    Intent intent = new Intent(SavedActivity.this, VideoActivity.class);
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

    private class DelTask extends AsyncTask<String,String,String> {
        int z;
        public DelTask(Integer q) {
            z=q;
        }

        String res="";
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String ... params){
            try {
                Log.w("IDDDz==",String.valueOf(z));
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();

                String sql = "DELETE FROM saved WHERE id="+z;
                st.execute(sql);



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

        }
    }


    private class LookTask extends AsyncTask<String,String,String> {
        int z;
        public LookTask(Integer q) {
            z=q;
        }

        String res="";
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String ... params){
            try {
                Log.w("IDDDz==",String.valueOf(z));
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();

                String sql ="INSERT INTO looked ( film_id, user_id) VALUES ("+z+","+userID+")";
                st.execute(sql);
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

        }
    }

}
