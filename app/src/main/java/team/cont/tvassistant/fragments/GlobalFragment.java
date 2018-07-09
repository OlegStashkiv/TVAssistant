package team.cont.tvassistant.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import team.cont.tvassistant.GlobalActivity;
import team.cont.tvassistant.ListActivity;
import team.cont.tvassistant.LoginActivity;
import team.cont.tvassistant.R;
import team.cont.tvassistant.VideoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GlobalFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GlobalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GlobalFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";


    private String rstatus="false";

    private String aUrl="123";
    private String aFname="321";
    private String year1="1990",year2="2017",years;

    Switch Sw1,Sw2,Sw3,Sw4;

    Button button_next;
    Button buttonlooked;
    TextView TestV,tv_,r_tv;
    TabHost tabHost;
    TextView nameView;
    ProgressBar progressBar;
    ImageView filmImg;
    int userID;
    Drawable draw; //Дефолтний колір кнопки
    Spinner spinner;
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,cb10,cb11,cb12,cb13;
    EditText ed1,ed2;
    RatingBar r1,r5;
    SeekBar sk1;
    Button bFL;
    Button bFL_off;
    int lastIdD=-1;
    Boolean isLast;
    TextView RatingTextView;

    String full_string="";
    String base="SELECT * FROM video WHERE";
    String f_type="";
    String f_rating="";
    String end_string=" ORDER BY RAND() LIMIT 1";

    String genres="";
    String genre[];


    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GlobalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GlobalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GlobalFragment newInstance(String param1, String param2) {
        GlobalFragment fragment = new GlobalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_global, container, false);
        tabHost = (TabHost) rootView.findViewById(R.id.tabsGlob);
        tabHost.setup();
        TabHost.TabSpec tabSpec;
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        Sw1 = (Switch) rootView.findViewById(R.id.switchtype);
        Sw2 = (Switch) rootView.findViewById(R.id.switch2);
        Sw3 = (Switch) rootView.findViewById(R.id.switch3);
        Sw4 = (Switch) rootView.findViewById(R.id.switch4);

        cb1 = (CheckBox) rootView.findViewById(R.id.checkBox);
        cb2 = (CheckBox) rootView.findViewById(R.id.checkBox2);
        cb3 = (CheckBox) rootView.findViewById(R.id.checkBox3);
        cb4 = (CheckBox) rootView.findViewById(R.id.checkBox4);
        cb5 = (CheckBox) rootView.findViewById(R.id.checkBox5);
        cb6 = (CheckBox) rootView.findViewById(R.id.checkBox6);
        cb7 = (CheckBox) rootView.findViewById(R.id.checkBox7);
        cb8 = (CheckBox) rootView.findViewById(R.id.checkBox8);
        cb9 = (CheckBox) rootView.findViewById(R.id.checkBox9);
        cb10 = (CheckBox) rootView.findViewById(R.id.checkBox10);
        cb11 = (CheckBox) rootView.findViewById(R.id.checkBox11);
        cb12 = (CheckBox) rootView.findViewById(R.id.checkBox12);
        cb13 = (CheckBox) rootView.findViewById(R.id.checkBox13);

        ed1 = (EditText) rootView.findViewById(R.id.editText5);
        ed2 = (EditText) rootView.findViewById(R.id.editText6);
        tv_ = (TextView) rootView.findViewById(R.id.textView9);
        r_tv = (TextView) rootView.findViewById(R.id.textView11);
        r1 = (RatingBar) rootView.findViewById(R.id.rating1);
        r5 = (RatingBar) rootView.findViewById(R.id.rating2);
        TestV = (TextView)rootView.findViewById(R.id.textView15);
        bFL = (Button) rootView.findViewById(R.id.button7);
        sk1 = (SeekBar) rootView.findViewById(R.id.seekBar);
        RatingTextView = (TextView) rootView.findViewById(R.id.textView13);

        Sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    spinner.setVisibility(View.VISIBLE);

                }else{
                    spinner.setVisibility(View.GONE);
                }
            }
        });

        Sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cb1.setVisibility(View.VISIBLE);
                    cb2.setVisibility(View.VISIBLE);
                    cb3.setVisibility(View.VISIBLE);
                    cb4.setVisibility(View.VISIBLE);
                    cb5.setVisibility(View.VISIBLE);
                    cb6.setVisibility(View.VISIBLE);
                    cb7.setVisibility(View.VISIBLE);
                    cb8.setVisibility(View.VISIBLE);
                    cb9.setVisibility(View.VISIBLE);
                    cb10.setVisibility(View.VISIBLE);
                    cb11.setVisibility(View.VISIBLE);
                    cb12.setVisibility(View.VISIBLE);
                    cb13.setVisibility(View.VISIBLE);


                }else{
                    cb1.setVisibility(View.GONE);
                    cb2.setVisibility(View.GONE);
                    cb3.setVisibility(View.GONE);
                    cb4.setVisibility(View.GONE);
                    cb5.setVisibility(View.GONE);
                    cb6.setVisibility(View.GONE);
                    cb7.setVisibility(View.GONE);
                    cb8.setVisibility(View.GONE);
                    cb9.setVisibility(View.GONE);
                    cb10.setVisibility(View.GONE);
                    cb11.setVisibility(View.GONE);
                    cb12.setVisibility(View.GONE);
                    cb13.setVisibility(View.GONE);

                }
            }
        });

        Sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ed1.setVisibility(View.VISIBLE);
                    ed2.setVisibility(View.VISIBLE);
                    tv_.setVisibility(View.VISIBLE);

                }else{
                    ed1.setVisibility(View.GONE);
                    ed2.setVisibility(View.GONE);
                    tv_.setVisibility(View.GONE);



                }
            }
        });

        Sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    r_tv.setVisibility(View.VISIBLE);
                    r1.setVisibility(View.VISIBLE);
                    r5.setVisibility(View.VISIBLE);
                    sk1.setVisibility(View.VISIBLE);

                }else{
                    r_tv.setVisibility(View.GONE);
                    r1.setVisibility(View.GONE);
                    r5.setVisibility(View.GONE);
                    sk1.setVisibility(View.GONE);

                }
            }
        });

        bFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startFilter();
            }
        });

        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag1");
        // название вкладки
        tabSpec.setIndicator("Результат");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab1G);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);


        // создаем вкладку и указываем тег
        tabSpec = tabHost.newTabSpec("tag2");
        // название вкладки
        tabSpec.setIndicator("Фільтри");
        // указываем id компонента из FrameLayout, он и станет содержимым
        tabSpec.setContent(R.id.tab2G);
        // добавляем в корневой элемент
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag("tag1");

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            public void onTabChanged(String tabId) {
                Toast.makeText(getActivity(), "tabId = " + tabId, Toast.LENGTH_SHORT).show();
            }
        });

        TextView TestV =  (TextView) rootView.findViewById(R.id.testshowtext);
        TextView nameView = (TextView) rootView.findViewById(R.id.nameView);
        Button button_next = (Button) rootView.findViewById(R.id.button_next);
        Button buttonSaved = (Button) rootView.findViewById(R.id.buttonsave);
        Button buttonlooked = (Button) rootView.findViewById(R.id.buttonlooked);
        bFL_off = (Button) rootView.findViewById(R.id.button8);
        draw= button_next.getBackground();
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadNextFilm().execute();
                buttonlooked.setText("Бачив");
                buttonlooked.setBackground(draw);
                buttonSaved.setText("Зберегти");
                buttonSaved.setBackground(draw);
            }
        });
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar4);

        SharedPreferences myPrefs = this.getActivity().getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        userID=myPrefs.getInt(SAVED_ID,1);

        ImageView filmImg = (ImageView) rootView.findViewById(R.id.filmImg);

        genre = new String[20];
        for (int z=0;z<20;z++){
            genre[z]="";
        }

        sk1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                // TODO Auto-generated method stub

              r_tv.setText(String.valueOf(progress));
                f_rating=" rating_value>="+String.valueOf(progress)+" AND";

            }
        });

        cb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb1.isChecked()) {
                    genre[0]=" genre LIKE '%Комеді%' AND";
                } else {
                    genre[0]="";
                }
            }
        });
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb2.isChecked()) {
                    genre[1]=" genre LIKE '%Фентезі%' AND";
                } else {
                    genre[1]="";
                }
            }
        });
        cb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb3.isChecked()) {
                    genre[2]=" genre LIKE '%Пригоди%' AND";
                } else {
                    genre[2]="";
                }
            }
        });
        cb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb4.isChecked()) {
                    genre[3]=" genre LIKE '%Сімейний%' AND";
                } else {
                    genre[3]="";
                }
            }
        });
        cb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb5.isChecked()) {
                    genre[4]=" genre LIKE '%Драма%' AND";
                } else {
                    genre[4]="";
                }
            }
        });
        cb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb6.isChecked()) {
                    genre[5]=" genre LIKE '%Триллер%' AND";
                } else {
                    genre[5]="";
                }
            }
        });
        cb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb7.isChecked()) {
                    genre[6]=" genre LIKE '%Кримінал%' AND";
                } else {
                    genre[6]="";
                }
            }
        });
        cb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb8.isChecked()) {
                    genre[7]=" genre LIKE '%Фантастика%' AND";
                } else {
                    genre[7]="";
                }
            }
        });
        cb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb9.isChecked()) {
                    genre[8]=" genre LIKE '%Бойовик%' AND";
                } else {
                    genre[8]="";
                }
            }
        });
        cb10.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb10.isChecked()) {
                    genre[9]=" genre LIKE '%Детектив%' AND";
                } else {
                    genre[9]="";
                }
            }
        });
        cb11.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb11.isChecked()) {
                    genre[10]=" genre LIKE '%Жахи%' AND";
                } else {
                    genre[10]="";
                }
            }
        });
        cb12.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb12.isChecked()) {
                    genre[11]=" genre LIKE '%Містика%' AND";
                } else {
                    genre[11]="";
                }
            }
        });
        cb13.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (cb13.isChecked()) {
                    genre[12]=" genre LIKE '%Кіберпанк%' AND";
                } else {
                    genre[12]="";
                }
            }
        });


        startFilter();
        bFL_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb1.setChecked(false);
                cb2.setChecked(false);
                cb3.setChecked(false);
                cb4.setChecked(false);
                cb5.setChecked(false);
                cb6.setChecked(false);
                cb7.setChecked(false);
                cb8.setChecked(false);
                cb9.setChecked(false);
                cb10.setChecked(false);
                cb11.setChecked(false);
                cb12.setChecked(false);
                cb13.setChecked(false);

                Sw1.setChecked(false);
                Sw2.setChecked(false);
                Sw3.setChecked(false);
                Sw4.setChecked(false);
                spinner.setSelection(0);
                ed1.setText("1990");
                ed2.setText("2017");
                sk1.refreshDrawableState();
                f_rating="";

                full_string=base+years+end_string;

            }
        });

        return rootView;
    }

    public void startFilter(){


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Любий"))
                {
                    f_type="";
                //    TestV.setText("Любий");
                }else if(selectedItem.equals("Фільм")){
                    f_type=" type=1 AND";
                //    TestV.setText("Фільм");
                }else if(selectedItem.equals("Серіал")){
                    f_type=" type=2 AND";
                //    TestV.setText("Серіал");
                }else if(selectedItem.equals("Мультфільм")){
                    f_type=" type=3 AND";
                //    TestV.setText("Мультфільм");
                }else if(selectedItem.equals("Аніме")){
                    f_type=" type=4 AND";
                 //   TestV.setText("Аніме");
                }
            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {
                //    TestV.setText("Нічого не вибрано")
            }
        });
        genres=genre[0]+genre[1]+genre[2]+genre[3]+genre[4]+genre[5]+genre[6]+genre[7]+genre[8]+genre[9]+genre[10]+genre[11]+genre[12];

        year1=ed1.getText().toString();
        if(year1.length()<4 | year2.length()>4){
            year1="1990";
            ed1.setText(year1);
        }
        year2=ed2.getText().toString();

        if(year2.length()<4 |year2.length()>4 ){
            year2="2017";
            ed2.setText(year2);
        }

        years=" year>="+year1+" AND year<="+year2+" ";
        full_string=base+f_type+genres+f_rating+years+end_string;
        TestV.setText(full_string);
        tabHost.setCurrentTab(0);
        new LoadNextFilm().execute();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

private class LoadNextFilm extends AsyncTask<String,String,String> {
    private String FName="",FURL="",FGenre="";
    float Frating;
    private int ID;
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
            rs.next();
            FName=rs.getString(2);
            FURL=rs.getString(8);
            ID=rs.getInt(1);
            FGenre=rs.getString(5);
            Frating=rs.getFloat(13);
            con.close();


        }
        catch(Exception e) {
            e.printStackTrace();
        }
        rstatus="true";
        return rstatus;
    }

    @Override
    protected void onPostExecute(String r) {
        progressBar.setVisibility(View.GONE);
        //    super.onPostExecute(aVoid);

        ImageView imv = (ImageView) getView().findViewById(R.id.filmImg);
        RatingBar ratingbar = (RatingBar) getView().findViewById(R.id.ratingBar);
        Button buttonlooked = (Button) getView().findViewById(R.id.buttonlooked);
        Button buttonSaved = (Button) getView().findViewById(R.id.buttonsave);
        TextView Test2 = (TextView) getView().findViewById(R.id.nameView);
        Log.w("IDTEST=",String.valueOf(ID));
        if(lastIdD==ID && isLast){
            isLast=false;
            nextFilm();

        }else if(ID==0 ){
            isLast=true;
            ratingbar.setRating(Frating);
            buttonlooked.setEnabled(false);
            buttonSaved.setEnabled(false);
            Test2.setText("Немає результатів, змініть фільтри");
            imv.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_menu_gallery));

        } else {
                isLast=true;
                lastIdD = ID;
                TestV.setText(String.valueOf(ID));
                TextView Test = (TextView) getView().findViewById(R.id.testshowtext);
                buttonlooked.setEnabled(true);
                buttonSaved.setEnabled(true);
                ratingbar.setRating(Frating);
                RatingTextView.setText(String.valueOf(Frating));
                Test.setText(FURL);
                Test2.setText(FName);
                //  Log.w("ID==");
                buttonlooked.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AddToLook(ID).execute();
                    }
                });

                buttonSaved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new AddToSave(ID).execute();
                    }
                });

                Test2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                imv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent activity1 = new Intent(getActivity(), VideoActivity.class);
                        Bundle bundle = new Bundle();

                        bundle.putInt("f_id", ID);
                        bundle.putString("f_name", FName);
                        bundle.putString("f_genre", FGenre);
                        activity1.putExtras(bundle);
                        startActivity(activity1);
                    }
                });
                new DownloadImageTask((ImageView) getView().findViewById(R.id.filmImg)).execute(FURL);
                new CheckSave(ID).execute();
                new CheckTask(ID).execute();
            }
    }
}

    private void nextFilm(){
    new LoadNextFilm().execute();
}
    private class CheckSave extends AsyncTask<String,String,String>{
        int filmID;
        public CheckSave(Integer q) {
            filmID=q;
        }
        private String fLogin="",FEmail="",ftext="";
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
                Button buttonSaved = (Button) getView().findViewById(R.id.buttonsave);
                Button buttonlooked = (Button) getView().findViewById(R.id.buttonlooked);
              //  buttonlooked.setEnabled(false);
                buttonSaved.setText("Збережено");
                buttonSaved.setEnabled(false);
            }
        }
    }
    private class CheckTask extends AsyncTask<String,String,String>{
        int filmID;
        public CheckTask(Integer q) {
            filmID=q;
        }
        private String fLogin="",FEmail="",ftext="";
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
                new LoadNextFilm().execute();
            }
        }
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
            Button buttonlooked = (Button) getView().findViewById(R.id.buttonlooked);
            Button buttonSaved = (Button) getView().findViewById(R.id.buttonsave);
            buttonSaved.setEnabled(false);
            buttonlooked.setText("Додано");
            buttonlooked.setEnabled(false);


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
            Button buttonSaved = (Button) getView().findViewById(R.id.buttonsave);
            Button buttonlooked = (Button) getView().findViewById(R.id.buttonlooked);
            buttonlooked.setEnabled(false);
            buttonSaved.setText("Збережено");
            buttonSaved.setEnabled(false);
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
