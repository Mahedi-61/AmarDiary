package bd.org.fulkuri.amardiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;

import bd.org.fulkuri.model.AllData;
import bd.org.fulkuri.model.DatabaseHelper;

public class MonthlyPlan extends AppCompatActivity implements View.OnClickListener, TextView.OnEditorActionListener {

    private LinearLayout lvButtonMP;

    private EditText
            etSuraNameQuranMP, etTotalDayQuranMP, etAverageAyatQuranMP, etAloconaQuranMP, etMemorizeQuranMP,
            etBookNameHadithMP, etTotalDayHadithMP, etAverageHadisHadithMP, etAloconaHadithMP, etMemorizeHadithMP,
            etBookNameLiteratureMP, etTotalPageLiteratureMP, etReligonalPageLiteratureMP, etOtherPageLiteratureMP, etBookNoteLiteratureMP,
            etTotalDayAcademicMP, etAverageHourAcademicMP,etTotalDayInClassAcademicMP,
            etTotalDayCallFulkuriWorkMP, etTotalHourCallFulkuriWorkMP, etTotalDayOtherFulkuriWorkMP,etAverageHourOtherFulkuriWorkMP,

            etMemberContactMP, etCoukosContactMP, etOgrropothikContactMP, etOngkuritoContactMP,
            etSuvasitoContactMP, etBikoshitoContactMP, etDhimanContactMP, etOvijatriContactMP,
            etTalentStudentContactMP, etFriendContactMP, etTeacherContactMP, etGuardianContactMP,
            etAdviserContactMP, etWellWisherContactMP,

            etTotalDayServiceWorkMP, etDescriptionServiceWorkMP,
            etMonthlyFulkuriDistributionMP, etPoricitiDistributionMP, etStikarDistributionMP, etLiteratureDistributionMP,
            etAmarProtidinDistributionMP,   etGiftDistributionMP,  etCdVcdDistributionMP, etOtherDistributionMP,

            etMemberIncrementMP, etCoukosIncrementMP, etOgrropothikIncrementMP, etSuvasitoIncrementMP,
            etBikoshitoIncrementMP, etDhimanIncrementMP, etOvijatriIncrementMP, etWellWisherIncrementMP,
            etTeacherWellWisherIncrementMP, etFriendIncrementMP, etTalentStudentIncrementMP,

            etAmountContributionMP, etIncrementContributionMP, etAmountServiceBankMP,
            etCriticismOtherMP, etGameOtherMP, etNewspaperOtherMP,etComputerOtherMP, etLanguageOhterMP, etOtherChildrenOtherMP;

    private CheckBox cbNamazJamaatMP, cbNofolIbadotMP;
    private ImageButton ibNext, ibPrevious;
    private Button bSave;
    private TextView tvTitle;
    private Toolbar mToolbar;

    private int cbState = 0;
    private Calendar month, currentMonth;
    private String monthId = "";
    private DatabaseHelper dbHelper;
    private HashMap<String, String> monthlyPlan;



    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_monthly_plan);

        month = Calendar.getInstance();
        currentMonth = Calendar.getInstance();
        dbHelper = new DatabaseHelper(this);
        initialize();
        refreshCalendar();


        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monthly Plan");

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousMP);
        ibPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ibNext = (ImageButton) findViewById(R.id.ibNextMP);
        ibNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setNextMonth();
                refreshCalendar();
            }
        });

        bSave = (Button) findViewById(R.id.bSaveMP);
        bSave.setOnClickListener(this);
        setClickListener();
    }


    
    private void setValueInLayoutElement() {

        etSuraNameQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Sura_Name));
        etTotalDayQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Total_day));
        etAverageAyatQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        etAloconaQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Alocona));
        etMemorizeQuranMP.setText(monthlyPlan.get(AllData.MP_Q_Memorize_Ayat));

        etBookNameHadithMP.setText(monthlyPlan.get(AllData.MP_H_Book_Name));
        etTotalDayHadithMP.setText(monthlyPlan.get(AllData.MP_H_Total_day));
        etAverageHadisHadithMP.setText(monthlyPlan.get(AllData.MP_H_Average_Hadis));
        etAloconaHadithMP.setText(monthlyPlan.get(AllData.MP_H_Alocona));
        etMemorizeHadithMP.setText(monthlyPlan.get(AllData.MP_H_Memorize_Hadis));

        etBookNameLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Book_Name));
        etTotalPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Total_page));
        etReligonalPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Relioganl_Page));
        etOtherPageLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Other_Page));
        etBookNoteLiteratureMP.setText(monthlyPlan.get(AllData.MP_L_Book_Note));

        etTotalDayAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Total_Day));
        etAverageHourAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Average_Hour));
        etTotalDayInClassAcademicMP.setText(monthlyPlan.get(AllData.MP_A_Total_Day_Class));


        if (monthlyPlan.get(AllData.MP_N_Jamaat) != null) {
            cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Jamaat));
            if (cbState == 1) {
                cbNamazJamaatMP.setChecked(true);
            } else {
                cbNamazJamaatMP.setChecked(false);
            }
        }else{
            cbNamazJamaatMP.setChecked(false);
        }

        if (monthlyPlan.get(AllData.MP_N_Nofol) != null) {
            cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Nofol));
            if (cbState == 1) {
                cbNofolIbadotMP.setChecked(true);
            } else {
                cbNofolIbadotMP.setChecked(false);
            }
        }else{
            cbNofolIbadotMP.setChecked(false);
        }

        etTotalDayCallFulkuriWorkMP.setText(monthlyPlan.get(AllData.MP_F_C_Total_Day));
        etTotalHourCallFulkuriWorkMP.setText(monthlyPlan.get(AllData.MP_F_C_Total_Hour));
        etTotalDayOtherFulkuriWorkMP.setText(monthlyPlan.get(AllData.MP_F_O_Total_Day));
        etAverageHourOtherFulkuriWorkMP.setText(monthlyPlan.get(AllData.MP_F_O_Average_Hour));

        etMemberContactMP.setText(monthlyPlan.get(AllData.MP_C_Member));
        etCoukosContactMP.setText(monthlyPlan.get(AllData.MP_C_Coukos));
        etOgrropothikContactMP.setText(monthlyPlan.get(AllData.MP_C_Ogrropothik));
        etOngkuritoContactMP.setText(monthlyPlan.get(AllData.MP_C_Ongkurito));
        etSuvasitoContactMP.setText(monthlyPlan.get(AllData.MP_C_Suvasito));
        etBikoshitoContactMP.setText(monthlyPlan.get(AllData.MP_C_Bikosito));
        etDhimanContactMP.setText(monthlyPlan.get(AllData.MP_C_Dhiman));
        etOvijatriContactMP.setText(monthlyPlan.get(AllData.MP_C_Ovijatri));
        etTalentStudentContactMP.setText(monthlyPlan.get(AllData.MP_C_Talent_Student));
        etFriendContactMP.setText(monthlyPlan.get(AllData.MP_C_Friend));
        etTeacherContactMP.setText(monthlyPlan.get(AllData.MP_C_Teacher));
        etGuardianContactMP.setText(monthlyPlan.get(AllData.MP_C_Guardian));
        etAdviserContactMP.setText(monthlyPlan.get(AllData.MP_C_Adviser));
        etWellWisherContactMP.setText(monthlyPlan.get(AllData.MP_C_WellWisher));

        etTotalDayServiceWorkMP.setText(monthlyPlan.get(AllData.MP_SW_Total_Day));
        etDescriptionServiceWorkMP.setText(monthlyPlan.get(AllData.MP_SW_Description));

        etMonthlyFulkuriDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Fulkuri));
        etPoricitiDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Poriciti));
        etStikarDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Stikar));
        etLiteratureDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Literature));
        etAmarProtidinDistributionMP.setText(monthlyPlan.get(AllData.MP_D_AmarProtidin));
        etGiftDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Gift));
        etCdVcdDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Cd_Dvd));
        etOtherDistributionMP.setText(monthlyPlan.get(AllData.MP_D_Other));

        etMemberIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Member));
        etCoukosIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Coukos));
        etOgrropothikIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Ogrropothik));
        etSuvasitoIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Suvasito));
        etBikoshitoIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Bikosito));
        etDhimanIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Dhiman));
        etOvijatriIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Ovijatri));
        etWellWisherIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Wellwisher));
        etTeacherWellWisherIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Teacher_Wellwisher));
        etFriendIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Friend));
        etTalentStudentIncrementMP.setText(monthlyPlan.get(AllData.MP_I_Talent_Student));

        etAmountContributionMP.setText(monthlyPlan.get(AllData.MP_CB_Amount));
        etIncrementContributionMP.setText(monthlyPlan.get(AllData.MP_CB_Increment));
        etAmountServiceBankMP.setText(monthlyPlan.get(AllData.MP_SB_Amount));

        etCriticismOtherMP.setText(monthlyPlan.get(AllData.MP_O_Criticism));
        etGameOtherMP.setText(monthlyPlan.get(AllData.MP_O_Game));
        etNewspaperOtherMP.setText(monthlyPlan.get(AllData.MP_O_Newspaper));
        etComputerOtherMP.setText(monthlyPlan.get(AllData.MP_O_Computer));
        etLanguageOhterMP.setText(monthlyPlan.get(AllData.MP_O_Language));
        etOtherChildrenOtherMP.setText(monthlyPlan.get(AllData.MP_O_Children_Organization));
    }



    //each time month changed whole plan is re-setup
    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvMonthOfPlanMP);
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(monthId);
        monthlyPlan = dbHelper.getAllContentFromTableMonthlyPlan(monthId);

        makeAllElementClear();
        setValueInLayoutElement();
        bSaveOrUpdate();

        //for previous month edit and save option is disabled
        if (month.get(Calendar.YEAR) >= currentMonth.get(Calendar.YEAR)){
            if( month.get(Calendar.MONTH)  >= currentMonth.get(Calendar.MONTH)){
                makeAllElementsEnabledOrDisabled(true);

            }else{
                makeAllElementsEnabledOrDisabled(false);
            }
        }else{
            makeAllElementsEnabledOrDisabled(false);
        }
    }


    public void onClick(View view) {
                    HashMap<String , String> hashmap = new HashMap();

                    hashmap.put(AllData.MP_Month_Id,              monthId);
                    hashmap.put(AllData.MP_Q_Sura_Name,           etSuraNameQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Total_day,           etTotalDayQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Average_Ayat,        etAverageAyatQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Alocona,             etAloconaQuranMP.getText().toString());
                    hashmap.put(AllData.MP_Q_Memorize_Ayat,       etMemorizeQuranMP.getText().toString());

                    hashmap.put(AllData.MP_H_Book_Name,           etBookNameHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Total_day,           etTotalDayHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Average_Hadis,       etAverageHadisHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Alocona,             etAloconaHadithMP.getText().toString());
                    hashmap.put(AllData.MP_H_Memorize_Hadis,      etMemorizeHadithMP.getText().toString());

                    hashmap.put(AllData.MP_L_Book_Name,           etBookNameLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Total_page,          etTotalPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Relioganl_Page,      etReligonalPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Other_Page,          etOtherPageLiteratureMP.getText().toString());
                    hashmap.put(AllData.MP_L_Book_Note,           etBookNoteLiteratureMP.getText().toString());

                    hashmap.put(AllData.MP_A_Total_Day,           etTotalDayAcademicMP.getText().toString());
                    hashmap.put(AllData.MP_A_Average_Hour,        etAverageHourAcademicMP.getText().toString());
                    hashmap.put(AllData.MP_A_Total_Day_Class,     etTotalDayInClassAcademicMP.getText().toString());

                    if (cbNamazJamaatMP.isChecked()) {
                        hashmap.put(AllData.MP_N_Jamaat, "1");
                    } else {
                        hashmap.put(AllData.MP_N_Jamaat, "0");
                    }

                    if (cbNofolIbadotMP.isChecked()) {
                        hashmap.put(AllData.MP_N_Nofol, "1");
                    } else {
                        hashmap.put(AllData.MP_N_Nofol, "0");
                    }

                    hashmap.put(AllData.MP_F_C_Total_Day,         etTotalDayCallFulkuriWorkMP.getText().toString());
                    hashmap.put(AllData.MP_F_C_Total_Hour,        etTotalHourCallFulkuriWorkMP.getText().toString());
                    hashmap.put(AllData.MP_F_O_Total_Day,         etTotalDayOtherFulkuriWorkMP.getText().toString());
                    hashmap.put(AllData.MP_F_O_Average_Hour,      etAverageHourOtherFulkuriWorkMP.getText().toString());

                    hashmap.put(AllData.MP_C_Member,              etMemberContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Coukos,              etCoukosContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Ogrropothik,         etOgrropothikContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Ongkurito,           etOngkuritoContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Suvasito,            etSuvasitoContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Bikosito,            etBikoshitoContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Dhiman,              etDhimanContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Ovijatri,            etOvijatriContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Talent_Student,      etTalentStudentContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Friend,              etFriendContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Teacher,             etTeacherContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Guardian,            etGuardianContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_Adviser,             etAdviserContactMP.getText().toString());
                    hashmap.put(AllData.MP_C_WellWisher,          etWellWisherContactMP.getText().toString());

                    hashmap.put(AllData.MP_SW_Total_Day,          etTotalDayServiceWorkMP.getText().toString());
                    hashmap.put(AllData.MP_SW_Description,        etDescriptionServiceWorkMP.getText().toString());

                    hashmap.put(AllData.MP_D_Fulkuri,             etMonthlyFulkuriDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Poriciti,            etPoricitiDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Stikar,              etStikarDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Literature,          etLiteratureDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_AmarProtidin,        etAmarProtidinDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Gift,                etGiftDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Cd_Dvd,              etCdVcdDistributionMP.getText().toString());
                    hashmap.put(AllData.MP_D_Other,               etOtherDistributionMP.getText().toString());

                    hashmap.put(AllData.MP_I_Member,              etMemberIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Coukos,              etCoukosIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Ogrropothik,         etOgrropothikIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Suvasito,            etSuvasitoIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Bikosito,            etBikoshitoIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Dhiman,              etDhimanIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Ovijatri,            etOvijatriIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Wellwisher,          etWellWisherIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Teacher_Wellwisher,  etTeacherWellWisherIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Friend,              etFriendIncrementMP.getText().toString());
                    hashmap.put(AllData.MP_I_Talent_Student,      etTalentStudentIncrementMP.getText().toString());

                    hashmap.put(AllData.MP_CB_Amount,             etAmountContributionMP.getText().toString());
                    hashmap.put(AllData.MP_CB_Increment,          etIncrementContributionMP.getText().toString());
                    hashmap.put(AllData.MP_SB_Amount,             etAmountServiceBankMP.getText().toString());

                    hashmap.put(AllData.MP_O_Criticism,           etCriticismOtherMP.getText().toString());
                    hashmap.put(AllData.MP_O_Game,                etGameOtherMP.getText().toString());
                    hashmap.put(AllData.MP_O_Newspaper,           etNewspaperOtherMP.getText().toString());
                    hashmap.put(AllData.MP_O_Computer,            etComputerOtherMP.getText().toString());
                    hashmap.put(AllData.MP_O_Language,            etLanguageOhterMP.getText().toString());
                    hashmap.put(AllData.MP_O_Children_Organization,  etOtherChildrenOtherMP.getText().toString());

                    if (dbHelper.checkMonthlyPlanExistOrNot(monthId) == 0) {
                        Toast.makeText(this, "Your plan is saved successfully !!", Toast.LENGTH_SHORT).show();
                        dbHelper.insertRowInTableMonthlyPlan(hashmap);
                        return;

                    } else {
                        dbHelper.updateRowInTableMonthlyPlan(hashmap);
                        Toast.makeText(this, "Your plan is updated successfully !!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


    //methods that don't often change
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    public void makeAllElementsEnabledOrDisabled(boolean condition) {

        etSuraNameQuranMP.setEnabled(condition);
        etTotalDayQuranMP.setEnabled(condition);
        etAverageAyatQuranMP.setEnabled(condition);
        etAloconaQuranMP.setEnabled(condition);
        etMemorizeQuranMP.setEnabled(condition);
        etBookNameHadithMP.setEnabled(condition);
        etTotalDayHadithMP.setEnabled(condition);
        etAverageHadisHadithMP.setEnabled(condition);
        etAloconaHadithMP.setEnabled(condition);
        etMemorizeHadithMP.setEnabled(condition);
        etBookNameLiteratureMP.setEnabled(condition);
        etTotalPageLiteratureMP.setEnabled(condition);
        etReligonalPageLiteratureMP.setEnabled(condition);
        etOtherPageLiteratureMP.setEnabled(condition);
        etBookNoteLiteratureMP.setEnabled(condition);
        etTotalDayAcademicMP.setEnabled(condition);
        etAverageHourAcademicMP.setEnabled(condition);
        etTotalDayInClassAcademicMP.setEnabled(condition);
        etTotalDayCallFulkuriWorkMP.setEnabled(condition);
        etTotalHourCallFulkuriWorkMP.setEnabled(condition);
        etTotalDayOtherFulkuriWorkMP.setEnabled(condition);
        etAverageHourOtherFulkuriWorkMP.setEnabled(condition);
        etMemberContactMP.setEnabled(condition);
        etCoukosContactMP.setEnabled(condition);
        etOgrropothikContactMP.setEnabled(condition);
        etOngkuritoContactMP.setEnabled(condition);
        etSuvasitoContactMP.setEnabled(condition);
        etBikoshitoContactMP.setEnabled(condition);
        etDhimanContactMP.setEnabled(condition);
        etOvijatriContactMP.setEnabled(condition);
        etTalentStudentContactMP.setEnabled(condition);
        etFriendContactMP.setEnabled(condition);
        etTeacherContactMP.setEnabled(condition);
        etGuardianContactMP.setEnabled(condition);
        etAdviserContactMP.setEnabled(condition);
        etWellWisherContactMP.setEnabled(condition);

        etTotalDayServiceWorkMP.setEnabled(condition);
        etDescriptionServiceWorkMP.setEnabled(condition);
        etMonthlyFulkuriDistributionMP.setEnabled(condition);
        etPoricitiDistributionMP.setEnabled(condition);
        etStikarDistributionMP.setEnabled(condition);
        etLiteratureDistributionMP.setEnabled(condition);
        etAmarProtidinDistributionMP.setEnabled(condition);
        etGiftDistributionMP.setEnabled(condition);
        etCdVcdDistributionMP.setEnabled(condition);
        etOtherDistributionMP.setEnabled(condition);
        etMemberIncrementMP.setEnabled(condition);
        etCoukosIncrementMP.setEnabled(condition);
        etOgrropothikIncrementMP.setEnabled(condition);
        etSuvasitoIncrementMP.setEnabled(condition);
        etBikoshitoIncrementMP.setEnabled(condition);
        etDhimanIncrementMP.setEnabled(condition);
        etOvijatriIncrementMP.setEnabled(condition);
        etWellWisherIncrementMP.setEnabled(condition);
        etTeacherWellWisherIncrementMP.setEnabled(condition);
        etFriendIncrementMP.setEnabled(condition);
        etTalentStudentIncrementMP.setEnabled(condition);

        etAmountContributionMP.setEnabled(condition);
        etIncrementContributionMP.setEnabled(condition);
        etAmountServiceBankMP.setEnabled(condition);
        etCriticismOtherMP.setEnabled(condition);
        etGameOtherMP.setEnabled(condition);
        etNewspaperOtherMP.setEnabled(condition);
        etComputerOtherMP.setEnabled(condition);
        etLanguageOhterMP.setEnabled(condition);
        etOtherChildrenOtherMP.setEnabled(condition);

        cbNamazJamaatMP.setEnabled(condition);
        cbNofolIbadotMP.setEnabled(condition);

        if(condition == true){
            lvButtonMP.setVisibility(View.VISIBLE);

        }else if(condition == false){
            lvButtonMP.setVisibility(View.GONE);
        }
    }



    private void initialize(){
        etSuraNameQuranMP = ((EditText) findViewById(R.id.etSuraNameQuranMP));
        etTotalDayQuranMP = ((EditText) findViewById(R.id.etTotalDayQuranMP));
        etAverageAyatQuranMP = ((EditText) findViewById(R.id.etAverageAyatQuranMP));
        etAloconaQuranMP = ((EditText) findViewById(R.id.etAloconaQuranMP));
        etMemorizeQuranMP = ((EditText) findViewById(R.id.etMemorizeQuranMP));


        etBookNameHadithMP = ((EditText) findViewById(R.id.etBookNameHadithMP));
        etTotalDayHadithMP = ((EditText) findViewById(R.id.etTotalDayHadithMP));
        etAverageHadisHadithMP = ((EditText) findViewById(R.id.etAverageHadisHadithMP));
        etAloconaHadithMP = ((EditText) findViewById(R.id.etAloconaHadithMP));
        etMemorizeHadithMP = ((EditText) findViewById(R.id.etMemorizeHadithMP));


        etBookNameLiteratureMP = ((EditText) findViewById(R.id.etBookNameLiteratureMP));
        etTotalPageLiteratureMP = ((EditText) findViewById(R.id.etTotalPageLiteratureMP));
        etReligonalPageLiteratureMP = ((EditText) findViewById(R.id.etReligonalPageLiteratureMP));
        etOtherPageLiteratureMP = ((EditText) findViewById(R.id.etOtherPageLiteratureMP));
        etBookNoteLiteratureMP = ((EditText) findViewById(R.id.etBookNoteLiteratureMP));

        etTotalDayAcademicMP = ((EditText) findViewById(R.id.etTotalDayAcademicMP));
        etAverageHourAcademicMP = ((EditText) findViewById(R.id.etAverageHourAcademicMP));
        etTotalDayInClassAcademicMP = ((EditText) findViewById(R.id.etTotalDayInClassAcademicMP));

        cbNamazJamaatMP = (CheckBox) findViewById(R.id.cbNamazJamaatMP);
        cbNofolIbadotMP = (CheckBox) findViewById(R.id.cbNofolIbadotMP);

        etTotalDayServiceWorkMP = ((EditText) findViewById(R.id.etTotalDayServiceWorkMP));
        etDescriptionServiceWorkMP = ((EditText) findViewById(R.id.etDescriptionServiceWorkMP));

        etTotalDayCallFulkuriWorkMP = ((EditText) findViewById(R.id.etTotalDayCallFulkuriWorkMP));
        etTotalHourCallFulkuriWorkMP = ((EditText) findViewById(R.id.etTotalHourCallFulkuriWorkMP));
        etTotalDayOtherFulkuriWorkMP = ((EditText) findViewById(R.id.etTotalDayOtherFulkuriWorkMP));
        etAverageHourOtherFulkuriWorkMP = ((EditText) findViewById(R.id.etAverageHourOtherFulkuriWorkMP));

        etMemberContactMP = ((EditText) findViewById(R.id.etMemberContactMP));
        etCoukosContactMP = ((EditText) findViewById(R.id.etCoukosContactMP));
        etOgrropothikContactMP = ((EditText) findViewById(R.id.etOgrropothikContactMP));
        etOngkuritoContactMP = ((EditText) findViewById(R.id.etOngkuritoContactMP));
        etSuvasitoContactMP = ((EditText) findViewById(R.id.etSuvasitoContactMP));
        etBikoshitoContactMP = ((EditText) findViewById(R.id.etBikoshitoContactMP));
        etDhimanContactMP = ((EditText) findViewById(R.id.etDhimanContactMP));
        etOvijatriContactMP = ((EditText) findViewById(R.id.etOvijatriContactMP));
        etTalentStudentContactMP = ((EditText) findViewById(R.id.etTalentStudentContactMP));
        etFriendContactMP = ((EditText) findViewById(R.id.etFriendContactMP));
        etTeacherContactMP = ((EditText) findViewById(R.id.etTeacherContactMP));
        etGuardianContactMP = ((EditText) findViewById(R.id.etGuardianContactMP));
        etAdviserContactMP = ((EditText) findViewById(R.id.etAdviserContactMP));
        etWellWisherContactMP = ((EditText) findViewById(R.id.etWellWisherContactMP));

        etMonthlyFulkuriDistributionMP = ((EditText) findViewById(R.id.etMonthlyFulkuriDistributionMP));
        etPoricitiDistributionMP = ((EditText) findViewById(R.id.etPoricitiDistributionMP));
        etStikarDistributionMP = ((EditText) findViewById(R.id.etStikarDistributionMP));
        etLiteratureDistributionMP = ((EditText) findViewById(R.id.etLiteratureDistributionMP));
        etAmarProtidinDistributionMP = ((EditText) findViewById(R.id.etAmarProtidinDistributionMP));
        etGiftDistributionMP = ((EditText) findViewById(R.id.etGiftDistributionMP));
        etCdVcdDistributionMP = ((EditText) findViewById(R.id.etCdVcdDistributionMP));
        etOtherDistributionMP = ((EditText) findViewById(R.id.etOtherDistributionMP));


        etMemberIncrementMP = ((EditText) findViewById(R.id.etMemberIncrementMP));
        etCoukosIncrementMP = ((EditText) findViewById(R.id.etCoukosIncrementMP));
        etOgrropothikIncrementMP = ((EditText) findViewById(R.id.etOgrropothikIncrementMP));
        etSuvasitoIncrementMP = ((EditText) findViewById(R.id.etSuvasitoIncrementMP));
        etBikoshitoIncrementMP = ((EditText) findViewById(R.id.etBikoshitoIncrementMP));
        etDhimanIncrementMP = ((EditText) findViewById(R.id.etDhimanIncrementMP));
        etOvijatriIncrementMP = ((EditText) findViewById(R.id.etOvijatriIncrementMP));
        etWellWisherIncrementMP = ((EditText) findViewById(R.id.etWellWisherIncrementMP));
        etTeacherWellWisherIncrementMP = ((EditText) findViewById(R.id.etTeacherWellWisherIncrementMP));
        etFriendIncrementMP = ((EditText) findViewById(R.id.etFriendIncrementMP));
        etTalentStudentIncrementMP = ((EditText) findViewById(R.id.etTalentStudentIncrementMP));

        etAmountContributionMP = ((EditText) findViewById(R.id.etAmountContributionMP));
        etIncrementContributionMP = ((EditText) findViewById(R.id.etIncrementContributionMP));
        etAmountServiceBankMP = ((EditText) findViewById(R.id.etAmountSereviceBankMP));

        etCriticismOtherMP = ((EditText) findViewById(R.id.etCriticismOtherMP));
        etGameOtherMP = ((EditText) findViewById(R.id.etGameOtherMP));
        etNewspaperOtherMP = ((EditText) findViewById(R.id.etNewspaperOtherMP));
        etComputerOtherMP = ((EditText) findViewById(R.id.etComputerOtherMP));
        etLanguageOhterMP = ((EditText) findViewById(R.id.etLanguageOhterMP));
        etOtherChildrenOtherMP = ((EditText) findViewById(R.id.etOtherChildrenOtherMP));

        bSave = (Button) findViewById(R.id.bSaveMP);
        lvButtonMP = (LinearLayout) findViewById(R.id.lvButtonMP);
    }



    private void setNextMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);
            return;

        } else {
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
            return;
        }
    }



    private void setPreviousMonth() {
        if (month.get(Calendar.MONTH) == month.getActualMinimum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) - 1, month.getActualMaximum(Calendar.MONTH), 1);
            return;

        } else {
            month.add(Calendar.MONTH, -1);
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH), 1);
            return;
        }
    }


    private void setClickListener(){

        etSuraNameQuranMP.setOnEditorActionListener(this);
        etTotalDayQuranMP.setOnEditorActionListener(this);
        etAverageAyatQuranMP.setOnEditorActionListener(this);
        etAloconaQuranMP.setOnEditorActionListener(this);

        etBookNameHadithMP.setOnEditorActionListener(this);
        etTotalDayHadithMP.setOnEditorActionListener(this);
        etAverageHadisHadithMP.setOnEditorActionListener(this);
        etAloconaHadithMP.setOnEditorActionListener(this);

        etBookNameLiteratureMP.setOnEditorActionListener(this);
        etTotalPageLiteratureMP.setOnEditorActionListener(this);
        etReligonalPageLiteratureMP.setOnEditorActionListener(this);
        etOtherPageLiteratureMP.setOnEditorActionListener(this);
        etBookNoteLiteratureMP.setOnEditorActionListener(this);

        etTotalDayAcademicMP.setOnEditorActionListener(this);
        etAverageHourAcademicMP.setOnEditorActionListener(this);
        etTotalDayInClassAcademicMP.setOnEditorActionListener(this);

        etTotalDayCallFulkuriWorkMP.setOnEditorActionListener(this);
        etTotalHourCallFulkuriWorkMP.setOnEditorActionListener(this);
        etTotalDayOtherFulkuriWorkMP.setOnEditorActionListener(this);
        etAverageHourOtherFulkuriWorkMP.setOnEditorActionListener(this);

        //contact
        etMemberContactMP.setOnEditorActionListener(this);
        etCoukosContactMP.setOnEditorActionListener(this);
        etOgrropothikContactMP.setOnEditorActionListener(this);
        etOngkuritoContactMP.setOnEditorActionListener(this);
        etSuvasitoContactMP.setOnEditorActionListener(this);
        etBikoshitoContactMP.setOnEditorActionListener(this);
        etDhimanContactMP.setOnEditorActionListener(this);
        etOvijatriContactMP.setOnEditorActionListener(this);
        etTalentStudentContactMP.setOnEditorActionListener(this);
        etFriendContactMP.setOnEditorActionListener(this);
        etTeacherContactMP.setOnEditorActionListener(this);
        etGuardianContactMP.setOnEditorActionListener(this);
        etAdviserContactMP.setOnEditorActionListener(this);

        etMonthlyFulkuriDistributionMP.setOnEditorActionListener(this);
        etPoricitiDistributionMP.setOnEditorActionListener(this);
        etStikarDistributionMP.setOnEditorActionListener(this);
        etLiteratureDistributionMP.setOnEditorActionListener(this);
        etAmarProtidinDistributionMP.setOnEditorActionListener(this);
        etGiftDistributionMP.setOnEditorActionListener(this);
        etCdVcdDistributionMP.setOnEditorActionListener(this);
        etOtherDistributionMP.setOnEditorActionListener(this);

        etMemberIncrementMP.setOnEditorActionListener(this);
        etCoukosIncrementMP.setOnEditorActionListener(this);
        etOgrropothikIncrementMP.setOnEditorActionListener(this);
        etSuvasitoIncrementMP.setOnEditorActionListener(this);
        etBikoshitoIncrementMP.setOnEditorActionListener(this);
        etDhimanIncrementMP.setOnEditorActionListener(this);
        etOvijatriIncrementMP.setOnEditorActionListener(this);
        etWellWisherIncrementMP.setOnEditorActionListener(this);
        etFriendIncrementMP.setOnEditorActionListener(this);
        etTalentStudentIncrementMP.setOnEditorActionListener(this);
    }

    

    public void bSaveOrUpdate(){
        if (dbHelper.checkMonthlyPlanExistOrNot(monthId) == 0) {
            bSave.setText("Save");

        }else{
            bSave.setText("Update");
        }
    }



    public void makeAllElementClear() {

        etTotalDayQuranMP.clearFocus();
        etAverageAyatQuranMP.clearFocus();
        etAloconaQuranMP.clearFocus();
        etMemorizeQuranMP.clearFocus();
        etBookNameHadithMP.clearFocus();
        etTotalDayHadithMP.clearFocus();
        etAverageHadisHadithMP.clearFocus();
        etAloconaHadithMP.clearFocus();
        etMemorizeHadithMP.clearFocus();
        etBookNameLiteratureMP.clearFocus();
        etTotalPageLiteratureMP.clearFocus();
        etReligonalPageLiteratureMP.clearFocus();
        etOtherPageLiteratureMP.clearFocus();
        etBookNoteLiteratureMP.clearFocus();
        etTotalDayAcademicMP.clearFocus();
        etAverageHourAcademicMP.clearFocus();
        etTotalDayInClassAcademicMP.clearFocus();
        etTotalDayCallFulkuriWorkMP.clearFocus();
        etTotalHourCallFulkuriWorkMP.clearFocus();
        etTotalDayOtherFulkuriWorkMP.clearFocus();
        etAverageHourOtherFulkuriWorkMP.clearFocus();
        etMemberContactMP.clearFocus();
        etCoukosContactMP.clearFocus();
        etOgrropothikContactMP.clearFocus();
        etOngkuritoContactMP.clearFocus();
        etSuvasitoContactMP.clearFocus();
        etBikoshitoContactMP.clearFocus();
        etDhimanContactMP.clearFocus();
        etOvijatriContactMP.clearFocus();
        etTalentStudentContactMP.clearFocus();
        etFriendContactMP.clearFocus();
        etTeacherContactMP.clearFocus();
        etGuardianContactMP.clearFocus();
        etAdviserContactMP.clearFocus();
        etWellWisherContactMP.clearFocus();

        etTotalDayServiceWorkMP.clearFocus();
        etDescriptionServiceWorkMP.clearFocus();
        etMonthlyFulkuriDistributionMP.clearFocus();
        etPoricitiDistributionMP.clearFocus();
        etStikarDistributionMP.clearFocus();
        etLiteratureDistributionMP.clearFocus();
        etAmarProtidinDistributionMP.clearFocus();
        etGiftDistributionMP.clearFocus();
        etCdVcdDistributionMP.clearFocus();
        etOtherDistributionMP.clearFocus();
        etMemberIncrementMP.clearFocus();
        etCoukosIncrementMP.clearFocus();
        etOgrropothikIncrementMP.clearFocus();
        etSuvasitoIncrementMP.clearFocus();
        etBikoshitoIncrementMP.clearFocus();
        etDhimanIncrementMP.clearFocus();
        etOvijatriIncrementMP.clearFocus();
        etWellWisherIncrementMP.clearFocus();
        etTeacherWellWisherIncrementMP.clearFocus();
        etFriendIncrementMP.clearFocus();
        etTalentStudentIncrementMP.clearFocus();

        etAmountContributionMP.clearFocus();
        etIncrementContributionMP.clearFocus();
        etAmountServiceBankMP.clearFocus();
        etCriticismOtherMP.clearFocus();
        etGameOtherMP.clearFocus();
        etNewspaperOtherMP.clearFocus();
        etComputerOtherMP.clearFocus();
        etLanguageOhterMP.clearFocus();
        etOtherChildrenOtherMP.clearFocus();

        cbNamazJamaatMP.clearFocus();
        cbNofolIbadotMP.clearFocus();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            switch (v.getId()) {

                case R.id.etSuraNameQuranMP:{etTotalDayQuranMP.requestFocus();break;}
                case R.id.etTotalDayQuranMP:{etAverageAyatQuranMP.requestFocus();break;}
                case R.id.etAverageAyatQuranMP:{etAloconaQuranMP.requestFocus();break;}
                case R.id.etAloconaQuranMP:{etMemorizeQuranMP.requestFocus();break;}

                case R.id.etBookNameHadithMP:{etTotalDayHadithMP.requestFocus();break;}
                case R.id.etTotalDayHadithMP:{etAverageHadisHadithMP.requestFocus();break;}
                case R.id.etAverageHadisHadithMP:{etAloconaHadithMP.requestFocus();break;}
                case R.id.etAloconaHadithMP:{etMemorizeHadithMP.requestFocus();break;}

                case R.id.etBookNameLiteratureMP:{etTotalPageLiteratureMP.requestFocus();break;}
                case R.id.etTotalPageLiteratureMP:{etReligonalPageLiteratureMP.requestFocus();break;}
                case R.id.etReligonalPageLiteratureMP:{etOtherPageLiteratureMP.requestFocus();break;}
                case R.id.etOtherPageLiteratureMP:{etBookNoteLiteratureMP.requestFocus();break;}
                case R.id.etBookNoteLiteratureMP:{etTotalDayAcademicMP.requestFocus();break;}

                case R.id.etTotalDayAcademicMP:{etAverageHourAcademicMP.requestFocus();break;}
                case R.id.etAverageHourAcademicMP:{etTotalDayInClassAcademicMP.requestFocus();break;}
                case R.id.etTotalDayInClassAcademicMP:{etTotalDayCallFulkuriWorkMP.requestFocus();break;}

                case R.id.etTotalDayCallFulkuriWorkMP:{etTotalHourCallFulkuriWorkMP.requestFocus();break;}
                case R.id.etTotalHourCallFulkuriWorkMP:{etTotalDayOtherFulkuriWorkMP.requestFocus();break;}
                case R.id.etTotalDayOtherFulkuriWorkMP:{etAverageHourOtherFulkuriWorkMP.requestFocus();break;}
                case R.id.etAverageHourOtherFulkuriWorkMP:{etMemberContactMP.requestFocus();break;}

                case R.id.etMemberContactMP: {etCoukosContactMP.requestFocus(); break;}
                case R.id.etCoukosContactMP: {etOgrropothikContactMP.requestFocus(); break;}
                case R.id.etOgrropothikContactMP: {etOngkuritoContactMP.requestFocus(); break;}
                case R.id.etOngkuritoContactMP: {etSuvasitoContactMP.requestFocus(); break;}
                case R.id.etSuvasitoContactMP: {etBikoshitoContactMP.requestFocus(); break;}
                case R.id.etBikoshitoContactMP: {etDhimanContactMP.requestFocus(); break;}
                case R.id.etDhimanContactMP: {etOvijatriContactMP.requestFocus(); break;}
                case R.id.etOvijatriContactMP: {etTalentStudentContactMP.requestFocus(); break;}
                case R.id.etTalentStudentContactMP: {etFriendContactMP.requestFocus(); break;}
                case R.id.etFriendContactMP: {etTeacherContactMP.requestFocus(); break;}
                case R.id.etTeacherContactMP: {etGuardianContactMP.requestFocus(); break;}
                case R.id.etGuardianContactMP: {etAdviserContactMP.requestFocus(); break;}
                case R.id.etAdviserContactMP: {etWellWisherContactMP.requestFocus(); break;}


                case R.id.etMonthlyFulkuriDistributionMP: {etPoricitiDistributionMP.requestFocus(); break;}
                case R.id.etPoricitiDistributionMP: {etStikarDistributionMP.requestFocus(); break;}
                case R.id.etStikarDistributionMP: {etLiteratureDistributionMP.requestFocus(); break;}
                case R.id.etLiteratureDistributionMP: {etAmarProtidinDistributionMP.requestFocus(); break;}
                case R.id.etAmarProtidinDistributionMP: {etGiftDistributionMP.requestFocus(); break;}
                case R.id.etGiftDistributionMP: {etCdVcdDistributionMP.requestFocus(); break;}
                case R.id.etCdVcdDistributionMP: {etOtherDistributionMP.requestFocus(); break;}
                case R.id.etOtherDistributionMP: {etMemberIncrementMP.requestFocus(); break;}

                case R.id.etMemberIncrementMP: {etCoukosIncrementMP.requestFocus(); break;}
                case R.id.etCoukosIncrementMP: {etOgrropothikIncrementMP.requestFocus(); break;}
                case R.id.etOgrropothikIncrementMP: {etSuvasitoIncrementMP.requestFocus(); break;}
                case R.id.etSuvasitoIncrementMP: {etBikoshitoIncrementMP.requestFocus(); break;}
                case R.id.etBikoshitoIncrementMP: {etDhimanIncrementMP.requestFocus(); break;}
                case R.id.etDhimanIncrementMP: {etOvijatriIncrementMP.requestFocus(); break;}
                case R.id.etOvijatriIncrementMP: {etWellWisherIncrementMP.requestFocus(); break;}
                case R.id.etWellWisherIncrementMP: {etFriendIncrementMP.requestFocus(); break;}
                case R.id.etFriendIncrementMP: {etTalentStudentIncrementMP.requestFocus(); break;}
                case R.id.etTalentStudentIncrementMP: {etTeacherWellWisherIncrementMP.requestFocus(); break;}
            }//end of switch
            return true;
        }//end of if
        return false;
    }
}
