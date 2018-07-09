package team.cont.tvassistant;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import team.cont.tvassistant.fragments.GlobalFragment;

public class VideoActivity extends AppCompatActivity {

    ProgressBar progressBar;
    int ID;
    TextView Year,textType,textCountry,textGenre,textDesc,textView4,textTegs,testView100,testView200,textView14,textView16,Reguser;
    String ActivityName="321";
    String Genres;
    ImageButton imageButton;
    Button bCreate,bEdit,BLook,BSave,BList;
    int VideoType=1;
    int userID;
    RatingBar ratingBar;
    String YouMark="";
    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";

    private String rstatus="false";
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";
    float youM;
    int CountR;
    float LRating,NRating;


    //Создаем список вьюх которые будут создаваться
    private List<View> allEds;
    //счетчик чисто декоративный для визуального отображения edittext'ov
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        progressBar = (ProgressBar) findViewById(R.id.progressBar5);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Year=(TextView) findViewById(R.id.textYear);
        textType=(TextView) findViewById(R.id.textType);
        textCountry=(TextView) findViewById(R.id.textCountry);
        textGenre=(TextView) findViewById(R.id.textGenre);
        textDesc=(TextView) findViewById(R.id.textDesc);
        Reguser= (TextView) findViewById(R.id.textReguser);
        BLook = (Button) findViewById(R.id.button11);
        BSave = (Button) findViewById(R.id.button10);
        BList = (Button) findViewById(R.id.button9);

     //   textView4=(TextView) findViewById(R.id.textView4);
       // textView4.setMovementMethod(new ScrollingMovementMethod());
        textTegs=(TextView) findViewById(R.id.textTegs);
        bEdit = (Button) findViewById(R.id.button4);
        bCreate = (Button) findViewById(R.id.button3);
        textView14 = (TextView) findViewById(R.id.textView14);
        textView16 = (TextView) findViewById(R.id.textView16);

        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        imageButton=(ImageButton) findViewById(R.id.imageButton);

      //
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            ID = extras.getInt("f_id", 1);

        //    mode = extras.getInt("mode", COUNTRY_MODE);
            ActivityName=extras.getString("f_name");
        }
        SharedPreferences myPrefs = getSharedPreferences(LOGIN_SETTING, MODE_PRIVATE);
        String userEmail = myPrefs.getString(SAVED_EMAIL,null);
        String userLogin = myPrefs.getString(SAVED_LOGIN,null);
         userID=myPrefs.getInt(SAVED_ID,1);

    //    Intent intent = getIntent();
    //    String ActivityName=intent.getStringExtra("f_name");
    //    ID = intent.getIntExtra("f_id",1);

        this.setTitle(ActivityName);
        TabHost tabHost = (TabHost) findViewById(R.id.tabsVideo);
        tabHost.setup();
        TabHost.TabSpec tabSpec;

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("Деталі");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab1G);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);


        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag2");
        // название вкладки
        tabSpec.setIndicator("Рецензії");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab2G);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);


        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag3");
        // название вкладки
        tabSpec.setIndicator("Схожі");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab3G);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getApplicationContext(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });

////

   // testq(linear);
   //     testq(linear);

////
        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity1 = new Intent(VideoActivity.this, EditCommentActivity.class);
                Bundle bundle = new Bundle();

                bundle.putInt("f_id", ID);
                bundle.putInt("u_id",userID);
                bundle.putString("f_name",ActivityName);
                finish();
                activity1.putExtras(bundle);
                startActivity(activity1);

            }
        });
        bCreate.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent activity1 = new Intent(VideoActivity.this, CreateCommentActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("f_id", ID);
            bundle.putInt("u_id",userID);
            bundle.putString("f_name",ActivityName);
            finish();
            activity1.putExtras(bundle);
            startActivity(activity1);

        }
    });

        BLook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddToLook(ID).execute();
            }
        });
        BSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AddToSave(ID).execute();
            }
        });

        BList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent activity1 = new Intent(VideoActivity.this, ListsInVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("f_id", ID);
                bundle.putInt("f_userID",userID);
                activity1.putExtras(bundle);
                startActivity(activity1);
            }
        });
        new LoadTask().execute();
        new isLoadRating().execute();
        new LoadComments().execute();

    }
    private class AddToLook extends AsyncTask<String,String,String>{
        int filmID;
        public AddToLook(Integer q) {
            filmID=q;
        }
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
                Log.w("filmID=",String.valueOf(filmID));
                Log.w("userID=", String.valueOf(userID));
                String sql = "INSERT INTO looked ( film_id, user_id) VALUES ("+filmID+","+userID+")";
                st.execute(sql);
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Button buttonlooked = (Button) findViewById(R.id.button11);
            Button buttonSaved = (Button) findViewById(R.id.button10);
            buttonSaved.setEnabled(false);
            buttonlooked.setText("Додано");
            buttonlooked.setEnabled(false);


        }
    }
    private class isLoadRating extends AsyncTask<String,String,String>{
        Float mark;
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
                String sql = "SELECT * FROM rating WHERE user_id="+userID+" AND video_id="+ID;
                final ResultSet rs=st.executeQuery(sql);
                if(rs.next())
                {rstatus="false";
                    mark=rs.getFloat(4);
                }
                else{rstatus="true";

                    con.close();}
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if(rstatus=="true"){
               ratingBar.setIsIndicator(false);

                ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {

                        youM=rating;
                        new AddRating().execute();

                        Toast.makeText(VideoActivity.this, "рейтинг: " + String.valueOf(rating),
                                Toast.LENGTH_LONG).show();
                    }
                });
            } else{
                ratingBar.setIsIndicator(true);
                textView16.setVisibility(View.VISIBLE);
                textView16.setText("Ваша оцінка - "+String.valueOf(mark));
            }
        }
    }

    private class AddRating extends AsyncTask<String,String,String>{
        @Override
        protected void onPreExecute()
        {
            ratingBar.setIsIndicator(true);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String ... params){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                String sql = "INSERT INTO rating (video_id, user_id, mark) VALUES ("+ID+" , "+ userID + " , "+youM +" ) ";
                st.execute(sql);
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);

            new ChangeRatingValue().execute();

        }
    }

    private class ChangeRatingValue extends AsyncTask<String,String,String>{
        String z;
        @Override
        protected void onPreExecute()
        {   CountR++;
            NRating=(LRating+youM)/(CountR);
            Log.w("RATING=",String.valueOf(NRating));
            Log.w("COUNT=",String.valueOf(CountR));
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        protected String doInBackground(String ... params){
            try {
                Class.forName("com.mysql.jdbc.Driver");
                java.sql.Connection con = DriverManager.getConnection(url, user, password);
                Statement st = con.createStatement();
                String sql = "UPDATE video Set rating_value="+NRating+" , rating_count="+CountR+" WHERE  id="+ID;
                st.execute(sql);
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Intent activity1 = new Intent(VideoActivity.this, VideoActivity.class);
            Bundle bundle = new Bundle();

            bundle.putInt("f_id", ID);
            bundle.putString("f_name", ActivityName);
            bundle.putString("f_genre",  Genres);
            activity1.putExtras(bundle);
            finish();
            startActivity(activity1);


        }
    }

    private class LoadTask extends AsyncTask<String,String,String> {
        private String FName="",FURL="",FGenre="",FDesc="",FTegs="",FCountry="",FLink="",FProd;
        int Ftype,FYear;
        Float FRating;
        int FCount;


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

                String sql = "SELECT * FROM video WHERE id="+ID;

                final ResultSet rs= st.executeQuery(sql);
                rs.next();
                FName=rs.getString(2);
                FURL=rs.getString(8);
                FGenre=rs.getString(5);
                Ftype=rs.getInt(3);
                FTegs=rs.getString(6);
                FDesc=rs.getString(7);
                FCountry=rs.getString(10);
                FLink=rs.getString(9);
                FYear=rs.getInt(4);
                FRating=rs.getFloat(13);
                FCount=rs.getInt(14);
                FProd=rs.getString(12);


                con.close();

            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            //    super.onPostExecute(aVoid);

            Year.setText( String.valueOf(FYear));
            switch(Ftype){
                case 1:
                    textType.setText("Фільм");
                    break;
                case 2:
                    textType.setText("Серіал");
                    break;
                case 3:
                    textType.setText("Мультфільм");
                    break;
                case 4:
                    textType.setText("Аніме");
                    break;
            }

            Reguser.setText(FProd);
            textCountry.setText(FCountry);
            textGenre.setText(FGenre);
            Genres=FGenre;
            if(FDesc.length()>250){
                textDesc.setText(FDesc.substring(0, 250)+"...");
                textDesc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent activity1 = new Intent(VideoActivity.this, DescInfoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("f_id", ID);
                        activity1.putExtras(bundle);
                        startActivity(activity1);
                    }
                });

            }else{
                textDesc.setText(FDesc);
            }

            textTegs.setText(FTegs);
            VideoType=Ftype;
            LRating=FRating;
            CountR=FCount;
            ratingBar.setRating(FRating);
            textView14.setText("Оцінка "+String.valueOf(FRating)+" Голосів "+String.valueOf(FCount));
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(FLink)));
                }
            });


            new DownloadImageTask((ImageView) findViewById(R.id.imageLogo1)).execute(FURL);
            new CheckTask(ID).execute();
            new CheckSave(ID).execute();
            new LoadSimilarVideo().execute();
           // testDB();
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

    private class LoadSimilarVideo extends AsyncTask<String,String,String> {
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

                String sql = "SELECT * FROM video WHERE type="+VideoType+" ORDER BY RAND() LIMIT 6";

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(2));
                    FURLs.add(rs.getString(8));
                    IDs.add(rs.getInt(1));
                    FRating.add(rs.getFloat(13));
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
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные

            testq(linear,FNames,ind,FURLs,IDs,FRating);


            //   new DownloadImageTask((ImageView) findViewById(R.id.imageLogo1)).execute(FURL);

        }
    }

    private void testq(LinearLayout liner,ArrayList<String> array,int numbers,ArrayList<String> urls,ArrayList<Integer> ids, ArrayList<Float> FR){
        for(int i=0;i<numbers;i++) {
            final View view = getLayoutInflater().inflate(R.layout.similar_video__layout, null);
            RatingBar RB = (RatingBar) view.findViewById(R.id.ratingBar2);
            RB.setRating(FR.get(i));
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            ImageView imageView2m = (ImageView) view.findViewById(R.id.imageView2m);
            tv4.setText(array.get(i));
            new DownloadImageTask((ImageView) view.findViewById(R.id.imageView2m)).execute(urls.get(i));
            //добавляем все что создаем в массив
            allEds.add(view);
            final int id=i;
            final String name=array.get(i);
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

    private class LoadComments extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<String> FTexts = new ArrayList<>();
        ArrayList<Integer> FPosition = new ArrayList<>();

        ArrayList<Integer> IDs = new ArrayList<>();
        String res="";
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

                    String sql = "SELECT comment.text, comment.position, user.login FROM comment,user WHERE video_id="+ID+" AND comment.user_id=user.id";

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(3));
                    FTexts.add(rs.getString(1));
                    FPosition.add(rs.getInt(2));

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
            final LinearLayout linear = (LinearLayout) findViewById(R.id.linear7);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные

            show_comments(linear,FNames,FTexts,FPosition,ind);

             new AddOrRead().execute();

            //   new DownloadImageTask((ImageView) findViewById(R.id.imageLogo1)).execute(FURL);

        }
    }

    private void show_comments(LinearLayout liner,ArrayList<String> names,ArrayList<String> texts,ArrayList<Integer> positions, int count){
        for(int i=0;i<count;i++) {
            final View view = getLayoutInflater().inflate(R.layout.comment_list, null);
            TextView tvNick = (TextView) view.findViewById(R.id.textViewLogin);
            tvNick.setText(names.get(i));
            TextView tvText = (TextView) view.findViewById(R.id.textViewPosition);
            tvText.setText(texts.get(i));
            ImageView imageViewStatus = (ImageView) view.findViewById(R.id.imageViewStatus);
            switch (positions.get(i)) {
                case 0:
                    imageViewStatus.setImageResource(R.drawable.surprised);
                    break;
                case 1:
                    imageViewStatus.setImageResource(R.drawable.happy);
                    break;
                case 2:
                    imageViewStatus.setImageResource(R.drawable.sad);
                    break;
            }
            //добавляем все что создаем в массив
            allEds.add(view);
            //добавляем елементы в linearlayout
            liner.addView(view);
        }
    }

    private class AddOrRead extends AsyncTask<String,String,String>{
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
                String sql = "SELECT * FROM comment WHERE user_id= "+userID+" AND video_id="+ID;
                final ResultSet rs=st.executeQuery(sql);
                if(rs.next())
                {rstatus="false";}
                else{rstatus="true";
                    con.close();}
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if(rstatus=="true"){
                bCreate.setVisibility(View.VISIBLE);
                bEdit.setVisibility(View.GONE);
            } else{
                bEdit.setVisibility(View.VISIBLE);
                bCreate.setVisibility(View.GONE);
            }
        }
    }

    private class CheckTask extends AsyncTask<String,String,String>{
        int filmID;
        public CheckTask(Integer q) {
            filmID=q;
        }
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
                String sql = "SELECT * FROM  looked WHERE looked.user_id="+userID+" AND looked.film_id="+filmID;
                final ResultSet rs=st.executeQuery(sql);
                if(rs.next())
                {rstatus="false";}
                else{rstatus="true";
                    con.close();}
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if(rstatus=="true"){

            } else{
                BLook.setEnabled(false);
                BLook.setText("Бачив");
            }
        }
    }
    private class CheckSave extends AsyncTask<String,String,String>{
        int filmID;
        public CheckSave(Integer q) {
            filmID=q;
        }
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
                String sql = "SELECT * FROM  saved WHERE saved.user_id="+userID+" AND saved.film_id="+filmID;
                final ResultSet rs=st.executeQuery(sql);
                if(rs.next())
                {rstatus="false";}
                else{rstatus="true";
                    con.close();}
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if(rstatus=="true"){

            } else{
                Button buttonSaved = (Button) findViewById(R.id.button10);
                Button buttonlooked = (Button) findViewById(R.id.button11);
                //  buttonlooked.setEnabled(false);
                buttonSaved.setText("Збережено");
                buttonSaved.setEnabled(false);
            }
        }
    }
    private class AddToSave extends AsyncTask<String,String,String>{
        int filmID;
        public AddToSave(Integer q) {
            filmID=q;
        }
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
                Log.w("filmID=",String.valueOf(filmID));
                Log.w("userID=", String.valueOf(userID));
                String sql = "INSERT INTO saved ( film_id, user_id) VALUES ("+filmID+","+userID+")";
                st.execute(sql);
                con.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            return rstatus;
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            Button buttonSaved = (Button) findViewById(R.id.button10);
            Button buttonlooked = (Button) findViewById(R.id.button11);
            buttonlooked.setEnabled(false);
            buttonSaved.setText("Збережено");
            buttonSaved.setEnabled(false);
        }
    }
}
