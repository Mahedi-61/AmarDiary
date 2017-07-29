package bd.org.fulkuri.amardiary;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import bd.org.fulkuri.adapter.NavigationDrawerCallbacks;
import bd.org.fulkuri.model.DatabaseHelper;


public class MainActivity extends AppCompatActivity implements NavigationDrawerCallbacks,
        View.OnClickListener, TextView.OnEditorActionListener {

    private EditText etStudyAcademicDD, etStudyQuranDD, etStudyHadithDD, etReligonalStudyLiteratureDD,
            etOtherStudyLiteratureDD, etJamaatNamazDD, etKajjaNamazDD, etCallForWorkFulkuriDD,
            etOtherWorkFulkuriDD, etMemberContactDD, etCoukosContactDD, etOgrropothikContactDD,
            etSuvasitoContactDD, etBikoshitoContactDD, etDhimanContactDD, etOvijatriContactDD,
            etTalentStudentContactDD, etTeacherContactDD, etGuardianContactDD, etAdviserContactDD,
            etWellWisherContactDD, etHourSleepDD;

    private CheckBox cbPresentClassDD, cbServiceBankOtherDD, cbCriticismOtherDD, cbGameOtherDD,
            cbNewspaperOtherDD;

    private TextView tvTitle;
    private ImageButton ibNext, ibPrevious;
    private Button bSave;
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private SharedPreferences profile;
    private Toolbar mToolbar;
    private DatabaseHelper dbHelper;

    private Calendar today, month;
    private String dayId = "", monthId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_daily_diary);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Daily Diary");

        profile = getSharedPreferences("profile", 0);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        mNavigationDrawerFragment.setUserData(profile.getString("user_name", ""), profile.getString("email_address", ""),
                BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));


        dbHelper = new DatabaseHelper(this);
        today = Calendar.getInstance();
        month = Calendar.getInstance();

        initialize();
        refreshCalendar();

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousDD);
        ibNext = (ImageButton) findViewById(R.id.ibNextDD);
        bSave = (Button) findViewById(R.id.bSaveDD);

        ibPrevious.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                setPreviousDay();
                refreshCalendar();
            }

        });
        ibNext.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                if (setNextDay()) {
                    refreshCalendar();
                }
            }

        });

        setClickListener();
    }


    private void initialize() {
        etStudyAcademicDD = (EditText) findViewById(R.id.etStudyAcademicDD);
        etStudyQuranDD = (EditText) findViewById(R.id.etStudyQuranDD);
        etStudyHadithDD = (EditText) findViewById(R.id.etStudyHadithDD);
        etReligonalStudyLiteratureDD = (EditText) findViewById(R.id.etReligonalStudyLiteratureDD);
        etOtherStudyLiteratureDD = (EditText) findViewById(R.id.etOtherStudyLiteratureDD);

        cbPresentClassDD = (CheckBox) findViewById(R.id.cbPresentClassDD);

        etJamaatNamazDD = (EditText) findViewById(R.id.etJamaatNamazDD);
        etKajjaNamazDD = (EditText) findViewById(R.id.etKajjaNamazDD);

        etCallForWorkFulkuriDD = ((EditText) findViewById(R.id.etCallForWorkFulkuriDD));
        etOtherWorkFulkuriDD = ((EditText) findViewById(R.id.etOtherWorkFulkuriDD));

        etMemberContactDD = (EditText) findViewById(R.id.etMemberContactDD);
        etCoukosContactDD = (EditText) findViewById(R.id.etCoukosContactDD);
        etOgrropothikContactDD = (EditText) findViewById(R.id.etOgrropothikContactDD);
        etSuvasitoContactDD = (EditText) findViewById(R.id.etSuvasitoContactDD);
        etBikoshitoContactDD = (EditText) findViewById(R.id.etBikoshitoContactDD);
        etDhimanContactDD = (EditText) findViewById(R.id.etDhimanContactDD);
        etOvijatriContactDD = (EditText) findViewById(R.id.etOvijatriContactDD);
        etTalentStudentContactDD = (EditText) findViewById(R.id.etTalentStudentContactDD);
        etTeacherContactDD = (EditText) findViewById(R.id.etTeacherContactDD);
        etGuardianContactDD = (EditText) findViewById(R.id.etGuardianContactDD);
        etAdviserContactDD = (EditText) findViewById(R.id.etAdviserContactDD);
        etWellWisherContactDD = (EditText) findViewById(R.id.etWellWisherContactDD);

        etHourSleepDD = (EditText) findViewById(R.id.etHourSleepDD);

        cbServiceBankOtherDD = ((CheckBox) findViewById(R.id.cbServiceBankOtherDD));
        cbCriticismOtherDD = ((CheckBox) findViewById(R.id.cbCriticismOtherDD));
        cbGameOtherDD = ((CheckBox) findViewById(R.id.cbGameOtherDD));
        cbNewspaperOtherDD = ((CheckBox) findViewById(R.id.cbNewspaperOtherDD));
    }


    private void setClickListener(){
        bSave.setOnClickListener(this);

        etOtherWorkFulkuriDD.setOnEditorActionListener(this);
        etOvijatriContactDD.setOnEditorActionListener(this);
        etDhimanContactDD.setOnEditorActionListener(this);
        etBikoshitoContactDD.setOnEditorActionListener(this);
        etSuvasitoContactDD.setOnEditorActionListener(this);

        etMemberContactDD.setOnEditorActionListener(this);
        etCoukosContactDD.setOnEditorActionListener(this);
        etOgrropothikContactDD.setOnEditorActionListener(this);
        etTalentStudentContactDD.setOnEditorActionListener(this);
        etAdviserContactDD.setOnEditorActionListener(this);
        etWellWisherContactDD.setOnEditorActionListener(this);
        etTeacherContactDD.setOnEditorActionListener(this);
        etGuardianContactDD.setOnEditorActionListener(this);
    }

    private void setValueInLayoutElement() {
        HashMap<String, String> hashmap = dbHelper.getDailyReportFromDatabase(dayId);
        int cbState = 0;

        etStudyAcademicDD.setText(hashmap.get("dd_study_academic"));
        etStudyQuranDD.setText(hashmap.get("dd_study_quran"));
        etStudyHadithDD.setText(hashmap.get("dd_study_hadith"));
        etReligonalStudyLiteratureDD.setText(hashmap.get("dd_study_literature_rel"));
        etOtherStudyLiteratureDD.setText(hashmap.get("dd_study_literature_other"));

        etJamaatNamazDD.setText(hashmap.get("dd_namaz_jamaat"));
        etKajjaNamazDD.setText(hashmap.get("dd_namaz_kajja"));

        etCallForWorkFulkuriDD.setText(hashmap.get("dd_fulkuri_call"));
        etOtherWorkFulkuriDD.setText(hashmap.get("dd_fulkuri_other"));

        etMemberContactDD.setText(hashmap.get("dd_contact_member"));
        etCoukosContactDD.setText(hashmap.get("dd_contact_coukos"));
        etOgrropothikContactDD.setText(hashmap.get("dd_contact_ogrropothik"));
        etSuvasitoContactDD.setText(hashmap.get("dd_contact_suvasito"));
        etBikoshitoContactDD.setText(hashmap.get("dd_contact_bikoshito"));
        etDhimanContactDD.setText(hashmap.get("dd_contact_dhiman"));
        etOvijatriContactDD.setText(hashmap.get("dd_contact_ovijatri"));
        etTalentStudentContactDD.setText(hashmap.get("dd_contact_talent_student"));
        etTeacherContactDD.setText(hashmap.get("dd_contact_teacher"));
        etGuardianContactDD.setText(hashmap.get("dd_contact_guardian"));
        etAdviserContactDD.setText(hashmap.get("dd_contact_adviser"));
        etWellWisherContactDD.setText(hashmap.get("dd_contact_wellwisher"));

        etHourSleepDD.setText(hashmap.get("dd_sleep"));

        if (hashmap.get("dd_class") != null) {
            cbState = Integer.parseInt(hashmap.get("dd_class"));
            if (cbState == 1) {
                cbPresentClassDD.setChecked(true);
            } else {
                cbPresentClassDD.setChecked(false);
            }
        } else {
            cbPresentClassDD.setChecked(false);
        }


        if (hashmap.get("dd_service_bank") != null) {
            cbState = Integer.parseInt(hashmap.get("dd_service_bank"));
            if (cbState == 1) {
                cbServiceBankOtherDD.setChecked(true);
            } else {
                cbServiceBankOtherDD.setChecked(false);
            }
        } else {
            cbServiceBankOtherDD.setChecked(false);
        }

        if (hashmap.get("dd_criticism") != null) {
            cbState = Integer.parseInt(hashmap.get("dd_criticism"));
            if (cbState == 1) {
                cbCriticismOtherDD.setChecked(true);
            } else {
                cbCriticismOtherDD.setChecked(false);
            }
        } else {
            cbCriticismOtherDD.setChecked(false);
        }

        if (hashmap.get("dd_game") != null) {
            cbState = Integer.parseInt(hashmap.get("dd_game"));
            if (cbState == 1) {
                cbGameOtherDD.setChecked(true);
            } else {
                cbGameOtherDD.setChecked(false);
            }
        } else {
            cbGameOtherDD.setChecked(false);
        }


        if (hashmap.get("dd_newspaper") != null) {
            cbState = Integer.parseInt(hashmap.get("dd_newspaper"));
            if (cbState == 1) {
                cbNewspaperOtherDD.setChecked(true);
            } else {
                cbNewspaperOtherDD.setChecked(false);
            }
        } else {
            cbNewspaperOtherDD.setChecked(false);
        }
    }



    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvTitleDD);
        dayId = DateFormat.format("EE, dd MMMM yyyy", month).toString();
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(dayId);
        setValueInLayoutElement();
        clearFocusFromAllEditext();
    }


    @Override
    public void onClick(View v) {
        HashMap<String, String> dailyDiary = new HashMap<>();
        dailyDiary.put("day_id", dayId);
        dailyDiary.put("month_id", monthId);

        dailyDiary.put("dd_study_academic", etStudyAcademicDD.getText().toString());
        dailyDiary.put("dd_study_quran", etStudyQuranDD.getText().toString());
        dailyDiary.put("dd_study_hadith", etStudyHadithDD.getText().toString());
        dailyDiary.put("dd_study_literature_rel", etReligonalStudyLiteratureDD.getText().toString());
        dailyDiary.put("dd_study_literature_other", etOtherStudyLiteratureDD.getText().toString());

        dailyDiary.put("dd_namaz_jamaat", etJamaatNamazDD.getText().toString());
        dailyDiary.put("dd_namaz_kajja", etKajjaNamazDD.getText().toString());

        dailyDiary.put("dd_fulkuri_call", etCallForWorkFulkuriDD.getText().toString());
        dailyDiary.put("dd_fulkuri_other", etOtherWorkFulkuriDD.getText().toString());

        dailyDiary.put("dd_sleep", etHourSleepDD.getText().toString());

        dailyDiary.put("dd_contact_member", etMemberContactDD.getText().toString());
        dailyDiary.put("dd_contact_coukos", etCoukosContactDD.getText().toString());
        dailyDiary.put("dd_contact_ogrropothik", etOgrropothikContactDD.getText().toString());
        dailyDiary.put("dd_contact_suvasito", etSuvasitoContactDD.getText().toString());
        dailyDiary.put("dd_contact_bikoshito", etBikoshitoContactDD.getText().toString());
        dailyDiary.put("dd_contact_dhiman", etDhimanContactDD.getText().toString());
        dailyDiary.put("dd_contact_ovijatri", etOvijatriContactDD.getText().toString());
        dailyDiary.put("dd_contact_talent_student", etTalentStudentContactDD.getText().toString());
        dailyDiary.put("dd_contact_teacher", etTeacherContactDD.getText().toString());
        dailyDiary.put("dd_contact_guardian", etGuardianContactDD.getText().toString());
        dailyDiary.put("dd_contact_adviser", etAdviserContactDD.getText().toString());
        dailyDiary.put("dd_contact_wellwisher", etWellWisherContactDD.getText().toString());

        if (cbPresentClassDD.isChecked()) {
            dailyDiary.put("dd_class", "1");
        } else {
            dailyDiary.put("dd_class", "0");
        }

        if (cbServiceBankOtherDD.isChecked()) {
            dailyDiary.put("dd_service_bank", "1");
        } else {
            dailyDiary.put("dd_service_bank", "0");
        }

        if (cbCriticismOtherDD.isChecked()) {
            dailyDiary.put("dd_criticism", "1");

        } else {
            dailyDiary.put("dd_criticism", "0");
        }

        if (cbGameOtherDD.isChecked()) {
            dailyDiary.put("dd_game", "1");

        } else {
            dailyDiary.put("dd_game", "0");
        }

        if (cbNewspaperOtherDD.isChecked()) {
            dailyDiary.put("dd_newspaper", "1");
        } else {
            dailyDiary.put("dd_newspaper", "0");
        }

        if (dbHelper.getIdFromDayId(dayId) == 0) {
            Toast.makeText(this, "Your diary is saved", Toast.LENGTH_SHORT).show();
            dbHelper.insertRowInTableDailyDairy(dailyDiary);
            return;

        } else {
            dailyDiary.put("_id", String.valueOf(dbHelper.getIdFromDayId(dayId)));
            Toast.makeText(this, "Your diary is updated", Toast.LENGTH_SHORT).show();
            dbHelper.updateRowInTableDailyDairy(dailyDiary);
            return;
        }
    }



    //*********** methods don't often change ****************
    private void clearFocusFromAllEditext(){

        etStudyQuranDD.clearFocus();
        etStudyHadithDD.clearFocus();
        etReligonalStudyLiteratureDD.clearFocus();
        etOtherStudyLiteratureDD.clearFocus();
        etJamaatNamazDD.clearFocus();
        etKajjaNamazDD.clearFocus();
        etCallForWorkFulkuriDD.clearFocus();
        etOtherWorkFulkuriDD.clearFocus();
        etMemberContactDD.clearFocus();
        etCoukosContactDD.clearFocus();
        etOgrropothikContactDD.clearFocus();
        etSuvasitoContactDD.clearFocus();
        etBikoshitoContactDD.clearFocus();
        etDhimanContactDD.clearFocus();
        etOvijatriContactDD.clearFocus();
        etTalentStudentContactDD.clearFocus();
        etTeacherContactDD.clearFocus();
        etGuardianContactDD.clearFocus();
        etAdviserContactDD.clearFocus();
        etWellWisherContactDD.clearFocus();
        etHourSleepDD.clearFocus();
    }



    private boolean setNextDay() {
        if (month.get(Calendar.YEAR) == today.get(Calendar.YEAR) &&
                month.get(Calendar.MONTH) == today.get(Calendar.MONTH) &&
                month.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {

            Toast.makeText(this, "You can't keep diary later than today !", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (month.get(Calendar.DAY_OF_MONTH) == month.getActualMaximum(Calendar.DAY_OF_MONTH)) {
            if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
                month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);

            } else {
                month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
            }
        } else {
            month.add(Calendar.DAY_OF_MONTH, 1);
        }
        return true;
    }



    private void setPreviousDay() {
        if (month.get(Calendar.DAY_OF_MONTH) == month.getActualMinimum(Calendar.DAY_OF_MONTH)) {
            if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
                month.set(month.get(Calendar.YEAR) - 1, month.getActualMaximum(Calendar.MONTH), 31);
                return;

            } else {
                month.add(Calendar.MONTH, -1);
                month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), month.getActualMaximum(Calendar.DAY_OF_MONTH));
                return;
            }
        } else {
            month.add(Calendar.DAY_OF_MONTH, -1);
            return;
        }
    }



    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0: {
                startActivity(new Intent(this, MonthlyDiary.class));
                break;
            }
            case 1: {
                startActivity(new Intent(this, MonthlyReport.class));
                break;
            }
            case 2: {
                startActivity(new Intent(this, MonthlyPlan.class));
                break;
            }
            case 3: {
                startActivity(new Intent(this, DiaryStatistics.class));
                break;
            }
            case 4: {
                startActivity(new Intent(this, Search.class));
                break;
            }
            case 5: {
                startActivity(new Intent(this, Settings.class));
                break;
            }

            case 6: {
                startActivity(new Intent(this, Help.class));
                break;
            }

            case 7: {
                startActivity(new Intent(this, About.class));
                break;
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else {

            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("Exit Amar Diary");
            builder.setMessage("Are you sure to exit ?");
            builder.setPositiveButton("Yes", new android.content.DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialoginterface, int i) {
                    dbHelper.close();
                    finish();
                }

            });

            builder.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialoginterface, int i) {
                }
            });
            builder.show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       /* if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }*/
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("day_id")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("EE, dd MMMM yyyy");
            try {
                Date date = dateFormat.parse(getIntent().getStringExtra("day_id"));
                month.setTime(date);
                refreshCalendar();

            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            switch(v.getId()){
                case R.id.etOtherWorkFulkuriDD:{
                        etOvijatriContactDD.requestFocus();
                    break;

                }case R.id.etOvijatriContactDD:{
                    etDhimanContactDD.requestFocus();
                    break;

                }case R.id.etDhimanContactDD:{
                    etBikoshitoContactDD.requestFocus();
                    break;

                }case R.id.etBikoshitoContactDD:{
                    etSuvasitoContactDD.requestFocus();
                    break;

                }case R.id.etSuvasitoContactDD:{
                    etMemberContactDD.requestFocus();
                    break;

                }case R.id.etMemberContactDD:{
                    etCoukosContactDD.requestFocus();
                    break;

                }case R.id.etCoukosContactDD:{
                    etOgrropothikContactDD.requestFocus();
                    break;

                }case R.id.etOgrropothikContactDD:{
                    etTalentStudentContactDD.requestFocus();
                    break;

                }case R.id.etTalentStudentContactDD:{
                    etAdviserContactDD.requestFocus();
                    break;

                }case R.id.etAdviserContactDD:{
                    etWellWisherContactDD.requestFocus();
                    break;

                }case R.id.etWellWisherContactDD:{
                    etTeacherContactDD.requestFocus();
                    break;

                }case R.id.etTeacherContactDD:{
                    etGuardianContactDD.requestFocus();
                    break;
                }case R.id.etGuardianContactDD:{
                    etHourSleepDD.requestFocus();
                    break;
                }
            }//end of switch
            return true;
        }
        return false;
    }
}

