package bd.org.fulkuri.amardiary;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

import bd.org.fulkuri.model.AllData;
import bd.org.fulkuri.model.DatabaseHelper;
import bd.org.fulkuri.model.MonthlyDiaryCalculation;

public class MonthlyReport extends AppCompatActivity implements View.OnClickListener, 
        TextView.OnEditorActionListener {

    private Button bSave, bSendDiary;

    private EditText etSuraNameQuranMR, etBookNameHadithMR, etBookNameLiteratureMR,
            etMemberIncrementMR, etCoukosIncrementMR, etOgrropothikIncrementMR, etSuvasitoIncrementMR,
            etBikoshitoIncrementMR, etDhimanIncrementMR, etOvijatriIncrementMR, etWellWisherIncrementMR,
            etTeacherWellWisherIncrementMR, etFriendIncrementMR, etTalentStudentIncrementMR,
            etMonthlyFulkuriDistributionMR, etPoricitiDistributionMR, etStikarDistributionMR, etLiteratureDistributionMR,
            etAmarProtidinDistributionMR, etGiftDistributionMR, etCdVcdDistributionMR, etOtherDistributionMR,
            etComputerOtherMR, etLanguageOhterMR, etOtherChildrenOtherMR, etAmountContributionMR,
            etPaymentDateContributionMR, etAmountServiceBankMR, etOngkuritoContactMR, etFriendContactMR,
            etTotalDayServiceWorkMR, etAverageTimeServiceWorkMR, etNofolNamazMR, etMemorizeQuranMR,
            etMemorizeHadithMR, etAloconaQuranMR, etAloconaHadithMR, etBookNoteLiteratureMR,
            etSuggestionMR;

    private TextView tvTotalDayQuranMR, tvAverageAyatQuranMR, tvTotalDayHadithMR,
            tvAverageHadisHadithMR, tvTotalPageLiteratureMR, tvReligonalPageLiteratureMR,
            tvOtherPageLiteratureMR, tvTotalDayAcademicMR, tvAverageHourAcademicMR, tvTotalDayInClassAcademicMR,
            tvJamaatNamazMR, tvAverageJamaatNamazMR, tvKajjaNamazMR, tvTotalDayCallFulkuriWorkMR,
            tvTotalHourCallFulkuriWorkMR, tvTotalDayOtherFulkuriWorkMR, tvAverageHourOtherFulkuriWorkMR, tvMemberContactMR,
            tvCoukosContactMR, tvOgrropothikContactMR, tvSuvasitoContactMR, tvBikoshitoContactMR,
            tvDhimanContactMR, tvOvijatriContactMR, tvTalentStudentContactMR, tvTeacherContactMR,
            tvGuardianContactMR, tvAdviserContactMR, tvWellWisherContactMR, tvCriticismOtherMR,
            tvGameOtherMR, tvNewspaperOtherMR, tvAverageHourSleepMR;

    private DatabaseHelper dbHelper;
    private CheckBox cbPlanExistMR;
    private Toolbar mToolbar;
    private MonthlyDiaryCalculation calculation;
    private ImageButton ibNext, ibPrevious;
    private Calendar currentMonth, month;
    private String monthId = "";
    private HashMap<String, String> monthlyDiary;

    private TextView tvTitle, tvDairyKeepingDays;

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_monthly_report);
        dbHelper = new DatabaseHelper(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Monthly Report");

        month = Calendar.getInstance();
        currentMonth = Calendar.getInstance();
        calculation = new MonthlyDiaryCalculation(this);

        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousMR);
        ibNext = (ImageButton) findViewById(R.id.ibNextMR);

        ibPrevious.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });

        ibNext.setOnClickListener(new android.view.View.OnClickListener() {
            public void onClick(View view) {
                if (setNextMonth()) {
                    refreshCalendar();
                }
            }
        });

        initializeEditText();
        initializeTextView();
        refreshCalendar();

        bSave = (Button) findViewById(R.id.bSaveMR);
        bSendDiary = (Button) findViewById(R.id.bSendMR);
        setClickListener();
    }


    private void setValueInLayoutElement() {

        //EditText -- 39
        etSuraNameQuranMR.setText(monthlyDiary.get("md_quran_sura_name"));
        etMemorizeQuranMR.setText(monthlyDiary.get("md_quran_memorize_ayat"));
        etAloconaQuranMR.setText(monthlyDiary.get("md_quran_alocona"));

        etBookNameHadithMR.setText(monthlyDiary.get("md_hadith_book_name"));
        etMemorizeHadithMR.setText(monthlyDiary.get("md_hadith_memorize_hadis"));
        etAloconaHadithMR.setText(monthlyDiary.get("md_hadith_alocona"));

        etBookNameLiteratureMR.setText(monthlyDiary.get("md_literature_book_name"));
        etBookNoteLiteratureMR.setText(monthlyDiary.get("md_literature_book_note"));

        etNofolNamazMR.setText(monthlyDiary.get("md_namaz_nofol"));

        etOngkuritoContactMR.setText(monthlyDiary.get("md_contact_ongkurito"));
        etFriendContactMR.setText(monthlyDiary.get("md_contact_friend"));

        etTotalDayServiceWorkMR.setText(monthlyDiary.get("md_service_work_total_day"));
        etAverageTimeServiceWorkMR.setText(monthlyDiary.get("md_service_work_average_time"));

        etMonthlyFulkuriDistributionMR.setText(monthlyDiary.get("md_distribution_fulkuri"));
        etPoricitiDistributionMR.setText(monthlyDiary.get("md_distribution_poriciti"));
        etStikarDistributionMR.setText(monthlyDiary.get("md_distribution_stikar"));
        etLiteratureDistributionMR.setText(monthlyDiary.get("md_distribution_literature"));
        etAmarProtidinDistributionMR.setText(monthlyDiary.get("md_distribution_amar_proitdin"));
        etGiftDistributionMR.setText(monthlyDiary.get("md_distribution_gift"));
        etCdVcdDistributionMR.setText(monthlyDiary.get("md_distribution_cd_dvd"));
        etOtherDistributionMR.setText(monthlyDiary.get("md_distribution_other"));

        etMemberIncrementMR.setText(monthlyDiary.get("md_increment_member"));
        etCoukosIncrementMR.setText(monthlyDiary.get("md_increment_coukos"));
        etOgrropothikIncrementMR.setText(monthlyDiary.get("md_increment_ogrropothik"));
        etSuvasitoIncrementMR.setText(monthlyDiary.get("md_increment_suvasito"));
        etBikoshitoIncrementMR.setText(monthlyDiary.get("md_increment_bikoshito"));
        etDhimanIncrementMR.setText(monthlyDiary.get("md_increment_dhiman"));
        etOvijatriIncrementMR.setText(monthlyDiary.get("md_increment_ovijatri"));
        etWellWisherIncrementMR.setText(monthlyDiary.get("md_increment_wellwisher"));
        etTeacherWellWisherIncrementMR.setText(monthlyDiary.get("md_increment_teacher_wellwisher"));
        etFriendIncrementMR.setText(monthlyDiary.get("md_increment_friend"));
        etTalentStudentIncrementMR.setText(monthlyDiary.get("md_increment_talent_student"));


        etAmountContributionMR.setText(monthlyDiary.get("md_contribution_amount"));
        etPaymentDateContributionMR.setText(monthlyDiary.get("md_contribution_payment_date"));
        etAmountServiceBankMR.setText(monthlyDiary.get("md_service_bank_amount"));

        etComputerOtherMR.setText(monthlyDiary.get("md_other_computer"));
        etLanguageOhterMR.setText(monthlyDiary.get("md_other_language"));
        etOtherChildrenOtherMR.setText(monthlyDiary.get("md_other_children_organization"));

        etSuggestionMR.setText(monthlyDiary.get("md_suggestion"));

        //TextView -- 33
        tvTotalDayQuranMR.setText(calculation.getTotalTypeDay(monthId, "dd_study_quran"));
        tvTotalDayHadithMR.setText(calculation.getTotalTypeDay(monthId, "dd_study_hadith"));
        tvTotalDayAcademicMR.setText(calculation.getTotalTypeDay(monthId, "dd_study_academic"));
        tvTotalDayInClassAcademicMR.setText(calculation.getTotalTypeDay(monthId, "dd_class"));
        tvTotalDayCallFulkuriWorkMR.setText(calculation.getTotalTypeDay(monthId, "dd_fulkuri_call"));
        tvTotalDayOtherFulkuriWorkMR.setText(calculation.getTotalTypeDay(monthId, "dd_fulkuri_other"));

        tvJamaatNamazMR.setText(calculation.getTotalType(monthId, "dd_namaz_jamaat"));
        tvKajjaNamazMR.setText(calculation.getTotalType(monthId, "dd_namaz_kajja"));
        tvReligonalPageLiteratureMR.setText(calculation.getTotalType(monthId, "dd_study_literature_rel"));
        tvOtherPageLiteratureMR.setText(calculation.getTotalType(monthId, "dd_study_literature_other"));
        tvTotalHourCallFulkuriWorkMR.setText(calculation.getTotalType(monthId, "dd_fulkuri_call"));

        tvAverageAyatQuranMR.setText(calculation.getAverageType(monthId, "dd_study_quran"));
        tvAverageHadisHadithMR.setText(calculation.getAverageType(monthId, "dd_study_hadith"));
        tvTotalPageLiteratureMR.setText(calculation.getTotalPage(monthId, "dd_study_literature_rel", "dd_study_literature_other"));
        tvAverageHourAcademicMR.setText(calculation.getAverageType(monthId, "dd_study_academic"));
        tvAverageJamaatNamazMR.setText(calculation.getAverageType(monthId, "dd_namaz_jamaat"));
        tvAverageHourOtherFulkuriWorkMR.setText(calculation.getAverageType(monthId, "dd_fulkuri_other"));
        tvAverageHourSleepMR.setText(calculation.getAverageType(monthId, "dd_sleep"));

        tvMemberContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_member"));
        tvCoukosContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_coukos"));
        tvOgrropothikContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_ogrropothik"));
        tvSuvasitoContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_suvasito"));
        tvBikoshitoContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_bikoshito"));
        tvDhimanContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_dhiman"));
        tvOvijatriContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_ovijatri"));
        tvTalentStudentContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_talent_student"));
        tvTeacherContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_teacher"));
        tvGuardianContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_guardian"));
        tvAdviserContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_adviser"));
        tvWellWisherContactMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_contact_wellwisher"));

        tvCriticismOtherMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_criticism"));
        tvGameOtherMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_game"));
        tvNewspaperOtherMR.setText(calculation.getSumForAMonthDiary(monthId, "dd_newspaper"));
    }


    private void refreshCalendar() {
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        monthlyDiary = dbHelper.getAllContentFromTableEtMR(monthId);

        tvTitle = (TextView) findViewById(R.id.tvMonthOfDairyMR);
        tvDairyKeepingDays = (TextView) findViewById(R.id.tvDairyKeepingDaysMR);
        cbPlanExistMR = (CheckBox) findViewById(R.id.cbPlanExistMR);

        tvTitle.setText(monthId);
        int cbState = dbHelper.checkMonthlyPlanExistOrNot(monthId);
        if (cbState == 1) {
            cbPlanExistMR.setChecked(true);
        } else {
            cbPlanExistMR.setChecked(false);
        }
        tvDairyKeepingDays.setText("Total Diary Keeping Days:  " + getMonthlyDairyKeepingDays(monthId));

        clearFocusFromEditText();
        setValueInLayoutElement();
    }



    public void onClick(View view) {

        switch(view.getId()){
            case R.id.bSendMR:{
                sendDataIntoEmail();
                break;
            }

            //save monthly dairy
            case R.id.bSaveMR:{
                monthlyDiary = new HashMap();

                monthlyDiary.put("md_month", monthId);
                monthlyDiary.put("md_quran_sura_name", etSuraNameQuranMR.getText().toString());
                monthlyDiary.put("md_quran_memorize_ayat", etMemorizeQuranMR.getText().toString());
                monthlyDiary.put("md_quran_alocona", etAloconaQuranMR.getText().toString());

                monthlyDiary.put("md_hadith_book_name", etBookNameHadithMR.getText().toString());
                monthlyDiary.put("md_hadith_memorize_hadis", etMemorizeHadithMR.getText().toString());
                monthlyDiary.put("md_hadith_alocona", etAloconaHadithMR.getText().toString());

                monthlyDiary.put("md_literature_book_name", etBookNameLiteratureMR.getText().toString());
                monthlyDiary.put("md_literature_book_note", etBookNoteLiteratureMR.getText().toString());

                monthlyDiary.put("md_namaz_nofol", etNofolNamazMR.getText().toString());

                monthlyDiary.put("md_contact_ongkurito", etOngkuritoContactMR.getText().toString());
                monthlyDiary.put("md_contact_friend", etFriendContactMR.getText().toString());

                monthlyDiary.put("md_service_work_total_day", etTotalDayServiceWorkMR.getText().toString());
                monthlyDiary.put("md_service_work_average_time", etAverageTimeServiceWorkMR.getText().toString());

                monthlyDiary.put("md_distribution_fulkuri", etMonthlyFulkuriDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_poriciti", etPoricitiDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_stikar", etStikarDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_literature", etLiteratureDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_amar_proitdin", etAmarProtidinDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_gift", etGiftDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_cd_dvd", etCdVcdDistributionMR.getText().toString());
                monthlyDiary.put("md_distribution_other", etOtherDistributionMR.getText().toString());

                monthlyDiary.put("md_increment_member", etMemberIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_coukos", etCoukosIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_ogrropothik", etOgrropothikIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_suvasito", etSuvasitoIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_bikoshito", etBikoshitoIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_dhiman", etDhimanIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_ovijatri", etOvijatriIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_wellwisher", etWellWisherIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_teacher_wellwisher", etTeacherWellWisherIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_friend", etFriendIncrementMR.getText().toString());
                monthlyDiary.put("md_increment_talent_student", etTalentStudentIncrementMR.getText().toString());

                monthlyDiary.put("md_contribution_amount", etAmountContributionMR.getText().toString());
                monthlyDiary.put("md_contribution_payment_date", etPaymentDateContributionMR.getText().toString());
                monthlyDiary.put("md_service_bank_amount", etAmountServiceBankMR.getText().toString());

                monthlyDiary.put("md_other_computer", etComputerOtherMR.getText().toString());
                monthlyDiary.put("md_other_language", etLanguageOhterMR.getText().toString());
                monthlyDiary.put("md_other_children_organization", etOtherChildrenOtherMR.getText().toString());
                monthlyDiary.put("md_suggestion", etSuggestionMR.getText().toString());

                if (dbHelper.checkThisMonthExistOrNot(monthId) == 0) {
                    Toast.makeText(this, "Your diary is saved successfully !!", Toast.LENGTH_SHORT).show();
                    dbHelper.insertRowInTableEtOfMR(monthlyDiary);
                    return;

                } else {
                    dbHelper.updateRowInTableEtMR(monthlyDiary);
                    Toast.makeText(this, "Your diary is updated successfully !!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }//end of case
        }//end of switch
    }



    public String getMonthlyDairyKeepingDays(String monthId) {
        ArrayList<Integer> list = dbHelper.getTotalNumberOfDairyKeepingInfoForMonth(monthId);
        return (list.size() + "");
    }


    //methods that often don't change
    private boolean setNextMonth() {
        if (month.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) &&
                month.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)) {

            Toast.makeText(getApplicationContext(), "You can't see diary later than current month !",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (month.get(Calendar.MONTH) == month.getActualMaximum(Calendar.MONTH)) {
            month.set(month.get(Calendar.YEAR) + 1, month.getActualMinimum(Calendar.MONTH), 1);

        } else {
            month.set(month.get(Calendar.YEAR), month.get(Calendar.MONTH) + 1, 1);
        }
        return true;
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {
                this.finish();
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (getIntent().hasExtra("month_id")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
            try {
                Date date = dateFormat.parse(getIntent().getStringExtra("month_id"));
                month.setTime(date);
                refreshCalendar();

            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }

    }



    private void clearFocusFromEditText() {

        etBookNameHadithMR.clearFocus();
        etBookNameLiteratureMR.clearFocus();
        etMemberIncrementMR.clearFocus();
        etCoukosIncrementMR.clearFocus();
        etOgrropothikIncrementMR.clearFocus();
        etSuvasitoIncrementMR.clearFocus();
        etBikoshitoIncrementMR.clearFocus();
        etDhimanIncrementMR.clearFocus();
        etOvijatriIncrementMR.clearFocus();
        etWellWisherIncrementMR.clearFocus();
        etTeacherWellWisherIncrementMR.clearFocus();
        etFriendIncrementMR.clearFocus();
        etTalentStudentIncrementMR.clearFocus();
        etMonthlyFulkuriDistributionMR.clearFocus();
        etPoricitiDistributionMR.clearFocus();
        etStikarDistributionMR.clearFocus();
        etLiteratureDistributionMR.clearFocus();
        etAmarProtidinDistributionMR.clearFocus();
        etGiftDistributionMR.clearFocus();
        etCdVcdDistributionMR.clearFocus();
        etOtherDistributionMR.clearFocus();
        etComputerOtherMR.clearFocus();
        etLanguageOhterMR.clearFocus();
        etOtherChildrenOtherMR.clearFocus();
        etAmountContributionMR.clearFocus();
        etPaymentDateContributionMR.clearFocus();
        etAmountServiceBankMR.clearFocus();
        etOngkuritoContactMR.clearFocus();
        etFriendContactMR.clearFocus();
        etTotalDayServiceWorkMR.clearFocus();
        etAverageTimeServiceWorkMR.clearFocus();
        etNofolNamazMR.clearFocus();
        etMemorizeQuranMR.clearFocus();
        etMemorizeHadithMR.clearFocus();
        etAloconaQuranMR.clearFocus();
        etAloconaHadithMR.clearFocus();
        etBookNoteLiteratureMR.clearFocus();
        etSuggestionMR.clearFocus();
    }



    private void initializeTextView() {

        tvTotalDayQuranMR = ((TextView) findViewById(R.id.tvTotalDayQuranMR));
        tvAverageAyatQuranMR = ((TextView) findViewById(R.id.tvAverageAyatQuranMR));

        tvTotalDayHadithMR = ((TextView) findViewById(R.id.tvTotalDayHadithMR));
        tvAverageHadisHadithMR = ((TextView) findViewById(R.id.tvAverageHadisHadithMR));

        tvTotalPageLiteratureMR = ((TextView) findViewById(R.id.tvTotalPageLiteratureMR));
        tvReligonalPageLiteratureMR = ((TextView) findViewById(R.id.tvReligonalPageLiteratureMR));
        tvOtherPageLiteratureMR = ((TextView) findViewById(R.id.tvOtherPageLiteratureMR));

        tvTotalDayAcademicMR = ((TextView) findViewById(R.id.tvTotalDayAcademicMR));
        tvAverageHourAcademicMR = ((TextView) findViewById(R.id.tvAverageHourAcademicMR));
        tvTotalDayInClassAcademicMR = ((TextView) findViewById(R.id.tvTotalDayInClassAcademicMR));

        tvJamaatNamazMR = ((TextView) findViewById(R.id.tvJamaatNamazMR));
        tvAverageJamaatNamazMR = ((TextView) findViewById(R.id.tvAverageJamaatNamazMR));
        tvKajjaNamazMR = ((TextView) findViewById(R.id.tvKajjaNamazMR));

        tvAverageHourSleepMR = (TextView) findViewById(R.id.tvAverageHourSleepMR);
        tvTotalDayCallFulkuriWorkMR = ((TextView) findViewById(R.id.tvTotalDayCallFulkuriWorkMR));
        tvTotalHourCallFulkuriWorkMR = ((TextView) findViewById(R.id.tvTotalHourCallFulkuriWorkMR));
        tvTotalDayOtherFulkuriWorkMR = ((TextView) findViewById(R.id.tvTotalDayOtherFulkuriWorkMR));
        tvAverageHourOtherFulkuriWorkMR = ((TextView) findViewById(R.id.tvAverageHourOtherFulkuriWorkMR));

        tvMemberContactMR = ((TextView) findViewById(R.id.tvMemberContactMR));
        tvCoukosContactMR = ((TextView) findViewById(R.id.tvCoukosContactMR));
        tvOgrropothikContactMR = ((TextView) findViewById(R.id.tvOgrropothikContactMR));
        tvSuvasitoContactMR = ((TextView) findViewById(R.id.tvSuvasitoContactMR));
        tvBikoshitoContactMR = ((TextView) findViewById(R.id.tvBikoshitoContactMR));
        tvDhimanContactMR = ((TextView) findViewById(R.id.tvDhimanContactMR));
        tvOvijatriContactMR = ((TextView) findViewById(R.id.tvOvijatriContactMR));
        tvTalentStudentContactMR = ((TextView) findViewById(R.id.tvTalentStudentContactMR));
        tvTeacherContactMR = ((TextView) findViewById(R.id.tvTeacherContactMR));
        tvGuardianContactMR = ((TextView) findViewById(R.id.tvGuardianContactMR));
        tvAdviserContactMR = ((TextView) findViewById(R.id.tvAdviserContactMR));
        tvWellWisherContactMR = ((TextView) findViewById(R.id.tvWellWisherContactMR));

        tvCriticismOtherMR = ((TextView) findViewById(R.id.tvCriticismOtherMR));
        tvGameOtherMR = ((TextView) findViewById(R.id.tvGameOtherMR));
        tvNewspaperOtherMR = ((TextView) findViewById(R.id.tvNewspaperOtherMR));
    }



    private void initializeEditText() {

        etSuraNameQuranMR = ((EditText) findViewById(R.id.etSuraNameQuranMR));
        etMemorizeQuranMR = ((EditText) findViewById(R.id.etMemorizeQuranMR));
        etAloconaQuranMR = (EditText) findViewById(R.id.etAloconaQuranMR);

        etBookNameHadithMR = ((EditText) findViewById(R.id.etBookNameHadithMR));
        etMemorizeHadithMR = ((EditText) findViewById(R.id.etMemorizeHadithMR));
        etAloconaHadithMR = (EditText) findViewById(R.id.etAloconaHadithMR);

        etBookNameLiteratureMR = ((EditText) findViewById(R.id.etBookNameLiteratureMR));
        etBookNoteLiteratureMR = (EditText) findViewById(R.id.etBookNoteLiteratureMR);

        etNofolNamazMR = ((EditText) findViewById(R.id.etNofolNamazMR));

        etOngkuritoContactMR = ((EditText) findViewById(R.id.etOngkuritoContactMR));
        etFriendContactMR = ((EditText) findViewById(R.id.etFriendContactMR));

        etTotalDayServiceWorkMR = ((EditText) findViewById(R.id.etTotalDayServiceWorkMR));
        etAverageTimeServiceWorkMR = ((EditText) findViewById(R.id.etAverageTimeServiceWorkMR));

        etMonthlyFulkuriDistributionMR = ((EditText) findViewById(R.id.etMonthlyFulkuriDistributionMR));
        etPoricitiDistributionMR = ((EditText) findViewById(R.id.etPoricitiDistributionMR));
        etStikarDistributionMR = ((EditText) findViewById(R.id.etStikarDistributionMR));
        etLiteratureDistributionMR = ((EditText) findViewById(R.id.etLiteratureDistributionMR));
        etAmarProtidinDistributionMR = ((EditText) findViewById(R.id.etAmarProtidinDistributionMR));
        etGiftDistributionMR = ((EditText) findViewById(R.id.etGiftDistributionMR));
        etCdVcdDistributionMR = ((EditText) findViewById(R.id.etCdVcdDistributionMR));
        etOtherDistributionMR = ((EditText) findViewById(R.id.etOtherDistributionMR));

        etMemberIncrementMR = ((EditText) findViewById(R.id.etMemberIncrementMR));
        etCoukosIncrementMR = ((EditText) findViewById(R.id.etCoukosIncrementMR));
        etOgrropothikIncrementMR = ((EditText) findViewById(R.id.etOgrropothikIncrementMR));
        etSuvasitoIncrementMR = ((EditText) findViewById(R.id.etSuvasitoIncrementMR));
        etBikoshitoIncrementMR = ((EditText) findViewById(R.id.etBikoshitoIncrementMR));
        etDhimanIncrementMR = ((EditText) findViewById(R.id.etDhimanIncrementMR));
        etOvijatriIncrementMR = ((EditText) findViewById(R.id.etOvijatriIncrementMR));
        etWellWisherIncrementMR = ((EditText) findViewById(R.id.etWellWisherIncrementMR));
        etTeacherWellWisherIncrementMR = ((EditText) findViewById(R.id.etTeacherWellWisherIncrementMR));
        etFriendIncrementMR = ((EditText) findViewById(R.id.etFriendIncrementMR));
        etTalentStudentIncrementMR = ((EditText) findViewById(R.id.etTalentStudentIncrementMR));

        etAmountContributionMR = ((EditText) findViewById(R.id.etAmountContributionMR));
        etPaymentDateContributionMR = ((EditText) findViewById(R.id.etPaymentDateContributionMR));
        etAmountServiceBankMR = ((EditText) findViewById(R.id.etAmountServiceBankMR));

        etComputerOtherMR = ((EditText) findViewById(R.id.etComputerOtherMR));
        etLanguageOhterMR = ((EditText) findViewById(R.id.etLanguageOhterMR));
        etOtherChildrenOtherMR = ((EditText) findViewById(R.id.etOtherChildrenOtherMR));

        etSuggestionMR = (EditText) findViewById(R.id.etSuggestionMR);
    }

    

    private void setClickListener(){
        bSave.setOnClickListener(this);
        bSendDiary.setOnClickListener(this);

        etAloconaQuranMR.setOnEditorActionListener(this);
        etAloconaHadithMR.setOnEditorActionListener(this);
        etAverageTimeServiceWorkMR.setOnEditorActionListener(this);
        
        etMonthlyFulkuriDistributionMR.setOnEditorActionListener(this);
        etPoricitiDistributionMR.setOnEditorActionListener(this);
        etStikarDistributionMR.setOnEditorActionListener(this);
        etLiteratureDistributionMR.setOnEditorActionListener(this);
        etAmarProtidinDistributionMR.setOnEditorActionListener(this);
        etGiftDistributionMR.setOnEditorActionListener(this);
        etCdVcdDistributionMR.setOnEditorActionListener(this);
        etOtherDistributionMR.setOnEditorActionListener(this);

        etMemberIncrementMR.setOnEditorActionListener(this);
        etCoukosIncrementMR.setOnEditorActionListener(this);
        etOgrropothikIncrementMR.setOnEditorActionListener(this);
        etSuvasitoIncrementMR.setOnEditorActionListener(this);
        etBikoshitoIncrementMR.setOnEditorActionListener(this);
        etDhimanIncrementMR.setOnEditorActionListener(this);
        etOvijatriIncrementMR.setOnEditorActionListener(this);
        etWellWisherIncrementMR.setOnEditorActionListener(this);
        etFriendIncrementMR.setOnEditorActionListener(this);
        etTalentStudentIncrementMR.setOnEditorActionListener(this);
    }


    
    private void sendDataIntoEmail(){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{""});
        intent.putExtra("android.intent.extra.SUBJECT", (new StringBuilder("Amar Diary For ")).append(monthId).toString());
        intent.putExtra("android.intent.extra.TEXT", getReadyToSendData());

        try {
            startActivity(createEmailOnlyChooserIntent(intent, "Send via email"));
            return;
        }
        catch (Exception ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }



    public Intent createEmailOnlyChooserIntent(Intent intent, CharSequence charsequence) {
        Stack stack = new Stack();
        Object obj = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", "info@domain.com", null));
        obj = getPackageManager().queryIntentActivities(((Intent) (obj)), 0).iterator();
        do {
            ResolveInfo resolveinfo;
            Intent intent1;
            if (!((Iterator) (obj)).hasNext()) {
                if (!stack.isEmpty()) {
                    intent = Intent.createChooser((Intent) stack.remove(0), charsequence);
                    intent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) stack.toArray(new Parcelable[stack.size()]));
                    return intent;
                } else {
                    return Intent.createChooser(intent, charsequence);
                }
            }
            resolveinfo = (ResolveInfo) ((Iterator) (obj)).next();
            intent1 = new Intent(intent);
            intent1.setPackage(resolveinfo.activityInfo.packageName);
            stack.add(intent1);
        } while (true);
    }



    private String getReadyToSendData(){
        StringBuilder diray = new StringBuilder("");

        diray.append("মোট ডাইয়েরী রাখা হয়েছেঃ ").append(getMonthlyDairyKeepingDays(monthId)).append(" দিন").
              append("\nকুরআন অধ্যয়নঃ").
              append("\nমোট দিনঃ   ").append(calculation.getTotalTypeDay(monthId, AllData.DD_S_QURAN)).
              append("\nগড় আয়াতঃ   ").append(calculation.getAverageType(monthId, AllData.DD_S_QURAN)).

              append("\n\nহাদীস অধ্যয়নঃ ").
              append("\nমোট দিনঃ   ").append(calculation.getTotalTypeDay(monthId, AllData.DD_S_HADITH)).
              append("\nগড় হাদীসঃ   ").append(calculation.getAverageType(monthId, AllData.DD_S_HADITH)).

              append("\n\nসাহিত্য অধ্যয়নঃ").
              append("\nমোট পৃষ্ঠাঃ   ").append(calculation.getTotalPage(monthId, AllData.DD_S_LITERATURE_REL, AllData.DD_S_LITERATURE_OTHER)).
              append("\nধর্মীয় পৃষ্ঠাঃ  ").append(calculation.getTotalType(monthId, AllData.DD_S_LITERATURE_REL)).
              append("\nঅন্যান্য পৃষ্ঠাঃ  ").append(calculation.getTotalType(monthId, AllData.DD_S_LITERATURE_OTHER)).

              append("\n\nঅ্যাকাডেমিক অধ্যয়নঃ").
              append("\nমোট দিনঃ   ").append(calculation.getTotalTypeDay(monthId, AllData.DD_S_ACADEMIC)).
              append("\nগড় ঘন্টাঃ  ").append(calculation.getAverageType(monthId, AllData.DD_S_ACADEMIC)).
              append("\nক্লাসে উপস্থিতিঃ  ").append(calculation.getTotalTypeDay(monthId, AllData.DD_CLASS)).

              append("\n\nনামাজঃ").
              append("\nমোট জামায়াতঃ   ").append(calculation.getTotalType(monthId, AllData.DD_N_JAMAAT)).
              append("\nগড় ওয়াক্তঃ  ").append(calculation.getAverageType(monthId, AllData.DD_N_JAMAAT)).
              append("\nমোট কাজ্বাঃ ").append(calculation.getTotalTypeDay(monthId, AllData.DD_N_KAJJA)).


              append("\n\nফুলকুঁড়ির কাজঃ").
              append("\nআহ্বানমূলক কাজঃ").
              append("\nমোট দিনঃ   ").append(calculation.getTotalTypeDay(monthId, AllData.DD_F_CALL)).
              append("\nমোট ঘন্টাঃ  ").append(calculation.getTotalType(monthId, AllData.DD_F_CALL)).
              append("\n\nঅন্যান্য কাজঃ").
              append("\nমোট দিনঃ   ").append(calculation.getTotalTypeDay(monthId, AllData.DD_F_OTHER)).
              append("\nগড় ঘন্টাঃ  ").append(calculation.getAverageType(monthId, AllData.DD_F_OTHER)).

              append("\n\nযোগাযোগঃ").
              append("\nসদস্যঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_MEMBER)).
              append("\nচৌকসঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_COUKOS)).
              append("\nঅগ্রপথিকঃ  ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_OGRROPOTHIK)).
              append("\nসুবাসিতঃ    ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_SUVASITO)).
              append("\nবিকশিতঃ    ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_BIKOSHITO)).
              append("\nধীমানঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_DHIMAN)).
              append("\nঅভিযাত্রীঃ    ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_OVIJATRI)).


              append("\n\nবৃদ্ধি").
              append("\nসদস্যঃ   ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_member"))).
              append("\nচৌকসঃ   ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_coukos"))).
              append("\nঅগ্রপথিকঃ  ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_ogrropothik"))).
              append("\nসুবাসিতঃ    ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_suvasito"))).
              append("\nবিকশিতঃ    ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_bikoshito"))).
              append("\nধীমানঃ   ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_dhiman"))).
              append("\nঅভিযাত্রীঃ    ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_increment_ovijatri"))).


              append("\n\nব্যক্তিগত চাঁদাঃ ").
              append("\nপরিমাণঃ   ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_contribution_amount"))).
              append("\nপরিশোধের তারিখঃ   ").append(getZeroForNullValueInMonthlyDiaryET(monthlyDiary.get("md_contribution_payment_date"))).

              append("\n\nঅন্যান্যঃ").
              append("\nআত্নপর্যালোচনাঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_CRITICISM)).
              append("\nখেলাধুলাঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_GAME)).
              append("\nপত্রপত্রিকা পাঠঃ   ").append(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_NEWSPAPER));

        return new String(diray);
    }


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_NEXT) {
            switch (v.getId()) {
                case R.id.etAverageTimeServiceWorkMR: {etMonthlyFulkuriDistributionMR.requestFocus(); break;}

                case R.id.etAloconaQuranMR:{etMemorizeQuranMR.requestFocus();break;}
                case R.id.etAloconaHadithMR:{etMemorizeHadithMR.requestFocus();break;}

                case R.id.etMonthlyFulkuriDistributionMR: {etPoricitiDistributionMR.requestFocus(); break;}
                case R.id.etPoricitiDistributionMR: {etStikarDistributionMR.requestFocus(); break;}
                case R.id.etStikarDistributionMR: {etLiteratureDistributionMR.requestFocus(); break;}
                case R.id.etLiteratureDistributionMR: {etAmarProtidinDistributionMR.requestFocus(); break;}
                case R.id.etAmarProtidinDistributionMR: {etGiftDistributionMR.requestFocus(); break;}
                case R.id.etGiftDistributionMR: {etCdVcdDistributionMR.requestFocus(); break;}
                case R.id.etCdVcdDistributionMR: {etOtherDistributionMR.requestFocus(); break;}
                case R.id.etOtherDistributionMR: {etMemberIncrementMR.requestFocus(); break;}

                case R.id.etMemberIncrementMR: {etCoukosIncrementMR.requestFocus(); break;}
                case R.id.etCoukosIncrementMR: {etOgrropothikIncrementMR.requestFocus(); break;}
                case R.id.etOgrropothikIncrementMR: {etSuvasitoIncrementMR.requestFocus(); break;}
                case R.id.etSuvasitoIncrementMR: {etBikoshitoIncrementMR.requestFocus(); break;}
                case R.id.etBikoshitoIncrementMR: {etDhimanIncrementMR.requestFocus(); break;}
                case R.id.etDhimanIncrementMR: {etOvijatriIncrementMR.requestFocus(); break;}
                case R.id.etOvijatriIncrementMR: {etWellWisherIncrementMR.requestFocus(); break;}
                case R.id.etWellWisherIncrementMR: {etFriendIncrementMR.requestFocus(); break;}
                case R.id.etFriendIncrementMR: {etTalentStudentIncrementMR.requestFocus(); break;}
                case R.id.etTalentStudentIncrementMR: {etTeacherWellWisherIncrementMR.requestFocus(); break;}
            }//end of switch
            return true;
        }//end of if
        return false;
    }



    private String getZeroForNullValueInMonthlyDiaryET(String value){
        if(value == null) return "0";
        return value;
    }
}

