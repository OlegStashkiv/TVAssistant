package team.cont.tvassistant.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import team.cont.tvassistant.GlobalActivity;
import team.cont.tvassistant.LoginActivity;
import team.cont.tvassistant.R;
import team.cont.tvassistant.VideoActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LookedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LookedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LookedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Создаем список вьюх которые будут создаваться
    private List<View> allEds;
    //счетчик чисто декоративный для визуального отображения edittext'ov
    private int counter = 0;
    private SharedPreferences  sPref;
    public static final String LOGIN_SETTING = "loginfile"; // Ім'я файла
    public static final String SAVED_EMAIL = "saved_email";   //Ключі
    public static final String SAVED_LOGIN = "saved_login";  //До даних
    public static final String SAVED_ID = "saved_id";


    private static final String url = "jdbc:mysql://78.46.96.244:3306/controller_tvassistant";
    private static final String user = "controller_user";
    private static final String password = "user123";


    private OnFragmentInteractionListener mListener;

    String userEmail;
    String userLogin;
    int userID;

    ProgressBar progressBar;


    public LookedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LookedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LookedFragment newInstance(String param1, String param2) {
        LookedFragment fragment = new LookedFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_looked, container, false);

        SharedPreferences myPrefs = this.getActivity().getSharedPreferences(LOGIN_SETTING, Context.MODE_PRIVATE);
        userID=myPrefs.getInt(SAVED_ID,1);



        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar3);

        new LoadLookTask().execute();

        return rootView;
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



    private class LoadLookTask extends AsyncTask<String,String,String> {
        ArrayList<String> FNames = new ArrayList<>();
        ArrayList<String> FURLs = new ArrayList<>();
        ArrayList<Integer> IDs = new ArrayList<>();
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

                String sql = "SELECT video.id, video.img_url, video.name FROM video,looked WHERE looked.user_id="+userID+" and video.id=looked.film_id";

                final ResultSet rs= st.executeQuery(sql);


                while(rs.next()){
                    FNames.add(rs.getString(3));
                    FURLs.add(rs.getString(2));
                    IDs.add(rs.getInt(1));
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
            final LinearLayout linear = (LinearLayout) getView().findViewById(R.id.linear1);
            //берем наш кастомный лейаут находим через него все наши кнопки и едит тексты, задаем нужные данные

            displayMyView(linear,FNames,ind,FURLs,IDs);
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



    private void displayMyView(LinearLayout liner,ArrayList<String> array,int numbers,ArrayList<String> urls,ArrayList<Integer> ids){
        for(int i=0;i<numbers;i++) {
            Log.w("CIRKLE",String.valueOf(i));
            final View view = getActivity().getLayoutInflater().inflate(R.layout.looked_list, null);
            TextView tv4 = (TextView) view.findViewById(R.id.textView4);
            ImageView imageView2m = (ImageView) view.findViewById(R.id.imageView2m);
            tv4.setText(String.valueOf(numbers)+array.get(i));
            Log.w("NAMEVIDEO",array.get(i));
            new DownloadImageTask((ImageView) view.findViewById(R.id.imageView2m)).execute(urls.get(i));
            //добавляем все что создаем в массив
            allEds.add(view);
            allEds.add(view);
            final int id=i;
            final String name=array.get(i);
            //добавляем елементы в linearlayout
            tv4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    getActivity().finish();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

            liner.addView(view);
            imageView2m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), VideoActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("f_id", ids.get(id));
                    bundle.putString("f_name",name);
                    getActivity().finish();
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


        }


}

}
