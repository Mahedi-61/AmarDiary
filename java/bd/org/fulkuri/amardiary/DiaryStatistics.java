package bd.org.fulkuri.amardiary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import bd.org.fulkuri.model.AllData;
import bd.org.fulkuri.model.DatabaseHelper;
import bd.org.fulkuri.model.MonthlyDiaryCalculation;

public class DiaryStatistics extends AppCompatActivity {

    private TextView tvPlanTotalDayAcademicDS, tvPlanAverageHourAcademicDS, tvPlanTotalDayQuranDS,
            tvPlanAverageAyatQuranDS, tvPlanTotalDayHadithDS, tvPlanAverageHadisHadithDS,
            tvPlanTotalPageLiteratureDS, tvPlanReligonalPageLiteratureDS, tvPlanOtherPageLiteratureDS,
            tvPlanPresentClassDS, tvPlanTotalJamaatNamazDS, tvPlanTotalDayCallFulkuriWorkDS,
            tvPlanTotalHourCallFulkuriWorkDS, tvPlanTotalDayOtherFulkuriWorkDS, tvPlanAverageHourOtherFulkuriWorkDS,
            tvPlanCriticismOtherDS, tvPlanGameOtherDS, tvPlanNewspaperOtherDS;


    private TextView tvDairyTotalDayAcademicDS, tvDairyAverageHourAcademicDS, tvDairyTotalDayQuranDS,
            tvDairyAverageAyatQuranDS, tvDairyTotalDayHadithDS, tvDairyAverageHadisHadithDS,
            tvDairyTotalPageLiteratureDS, tvDairyReligonalPageLiteratureDS, tvDairyOtherPageLiteratureDS,
            tvDairyPresentClassDS, tvDairyTotalJamaatNamazDS, tvDairyTotalDayCallFulkuriWorkDS,
            tvDairyTotalHourCallFulkuriWorkDS, tvDairyTotalDayOtherFulkuriWorkDS, tvDairyAverageHourOtherFulkuriWorkDS,
            tvDairyCriticismOtherDS, tvDairyGameOtherDS, tvDairyNewspaperOtherDS;

    private TextView tvPercentageTotalDayAcademicDS, tvPercentageAverageHourAcademicDS, tvPercentageTotalDayQuranDS,
            tvPercentageAverageAyatQuranDS, tvPercentageTotalDayHadithDS, tvPercentageAverageHadisHadithDS,
            tvPercentageTotalPageLiteratureDS, tvPercentageReligonalPageLiteratureDS, tvPercentageOtherPageLiteratureDS,
            tvPercentagePresentClassDS, tvPercentageTotalJamaatNamazDS, tvPercentageTotalDayCallFulkuriWorkDS,
            tvPercentageTotalHourCallFulkuriWorkDS, tvPercentageTotalDayOtherFulkuriWorkDS, tvPercentageAverageHourOtherFulkuriWorkDS,
            tvPercentageCriticismOtherDS, tvPercentageGameOtherDS, tvPercentageNewspaperOtherDS;

    private Toolbar mToolbar;
    private ImageButton ibNext, ibPrevious;
    private Calendar month, currentMonth;
    private String monthId = "";
    private HashMap<String, String> monthlyPlan;
    private DatabaseHelper dbHelper;
    private MonthlyDiaryCalculation calculation;
    private ArrayList<String> planValueList, diaryValueList;
    private TextView tvTitle;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_diary_statistics);
        dbHelper = new DatabaseHelper(this);
        month = Calendar.getInstance();
        currentMonth = Calendar.getInstance();

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Diary Statistics");


        initialize();
        refreshCalendar();
        ibPrevious = (ImageButton) findViewById(R.id.ibPreviousDS);
        ibNext = (ImageButton) findViewById(R.id.ibNextDS);

        ibPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setPreviousMonth();
                refreshCalendar();
            }
        });


        ibNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (setNextMonth()) refreshCalendar();
            }
        });

    }


    public String getPercentage(String plan, String diary) {
        double dDairy = 0.0D;
        double dPlan = 0.0D;

        if (plan != null && !plan.equals("")) {
            dPlan = Double.valueOf(plan);
        } else dPlan = 0.0D;


        if (diary != null && !diary.equals("")) {
            dDairy = Double.valueOf(diary);
        } else dDairy = 0.0D;

        if (dPlan <= 0.0D) {
            return "--";
        }

        if(dDairy == 0.0D){
            return  "0%";
        }
        if (dDairy >= dPlan) {
            return "100%";

        } else {
            //return (new StringBuilder(String.valueOf((100D * dDairy) / dPlan).substring(0, 2))).append("%").toString();
            return (new StringBuilder(String.valueOf((int)((100D * dDairy) / dPlan)))).append("%").toString();
        }
    }


    private void initialize() {
        //plan 18
        tvPlanTotalDayAcademicDS = ((TextView) findViewById(R.id.tvPlanTotalDayAcademicDS));
        tvPlanAverageHourAcademicDS = ((TextView) findViewById(R.id.tvPlanAverageHourAcademicDS));

        tvPlanTotalDayQuranDS = ((TextView) findViewById(R.id.tvPlanTotalDayQuranDS));
        tvPlanAverageAyatQuranDS = ((TextView) findViewById(R.id.tvPlanAverageAyatQuranDS));

        tvPlanTotalDayHadithDS = ((TextView) findViewById(R.id.tvPlanTotalDayHadithDS));
        tvPlanAverageHadisHadithDS = ((TextView) findViewById(R.id.tvPlanAverageHadisHadithDS));

        tvPlanTotalPageLiteratureDS = ((TextView) findViewById(R.id.tvPlanTotalPageLiteratureDS));
        tvPlanReligonalPageLiteratureDS = ((TextView) findViewById(R.id.tvPlanReligonalPageLiteratureDS));
        tvPlanOtherPageLiteratureDS = ((TextView) findViewById(R.id.tvPlanOtherPageLiteratureDS));

        tvPlanPresentClassDS = ((TextView) findViewById(R.id.tvPlanPresentClassDS));
        tvPlanTotalJamaatNamazDS = ((TextView) findViewById(R.id.tvPlanTotalJamaatNamazDS));

        tvPlanTotalDayCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvPlanTotalDayCallFulkuriWorkDS));
        tvPlanTotalHourCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvPlanTotalHourCallFulkuriWorkDS));
        tvPlanTotalDayOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvPlanTotalDayOtherFulkuriWorkDS));
        tvPlanAverageHourOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvPlanAverageHourOtherFulkuriWorkDS));

        tvPlanCriticismOtherDS = ((TextView) findViewById(R.id.tvPlanCriticismOtherDS));
        tvPlanGameOtherDS = ((TextView) findViewById(R.id.tvPlanGameOtherDS));
        tvPlanNewspaperOtherDS = ((TextView) findViewById(R.id.tvPlanNewspaperOtherDS));


        //dairy 18
        tvDairyTotalDayAcademicDS = ((TextView) findViewById(R.id.tvDairyTotalDayAcademicDS));
        tvDairyAverageHourAcademicDS = ((TextView) findViewById(R.id.tvDairyAverageHourAcademicDS));

        tvDairyTotalDayQuranDS = ((TextView) findViewById(R.id.tvDairyTotalDayQuranDS));
        tvDairyAverageAyatQuranDS = ((TextView) findViewById(R.id.tvDairyAverageAyatQuranDS));

        tvDairyTotalDayHadithDS = ((TextView) findViewById(R.id.tvDairyTotalDayHadithDS));
        tvDairyAverageHadisHadithDS = ((TextView) findViewById(R.id.tvDairyAverageHadisHadithDS));

        tvDairyTotalPageLiteratureDS = ((TextView) findViewById(R.id.tvDairyTotalPageLiteratureDS));
        tvDairyReligonalPageLiteratureDS = ((TextView) findViewById(R.id.tvDairyReligonalPageLiteratureDS));
        tvDairyOtherPageLiteratureDS = ((TextView) findViewById(R.id.tvDairyOtherPageLiteratureDS));

        tvDairyPresentClassDS = ((TextView) findViewById(R.id.tvDairyPresentClassDS));
        tvDairyTotalJamaatNamazDS = ((TextView) findViewById(R.id.tvDairyTotalJamaatNamazDS));

        tvDairyTotalDayCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvDairyTotalDayCallFulkuriWorkDS));
        tvDairyTotalHourCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvDairyTotalHourCallFulkuriWorkDS));
        tvDairyTotalDayOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvDairyTotalDayOtherFulkuriWorkDS));
        tvDairyAverageHourOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvDairyAverageHourOtherFulkuriWorkDS));

        tvDairyCriticismOtherDS = ((TextView) findViewById(R.id.tvDairyCriticismOtherDS));
        tvDairyGameOtherDS = ((TextView) findViewById(R.id.tvDairyGameOtherDS));
        tvDairyNewspaperOtherDS = ((TextView) findViewById(R.id.tvDairyNewspaperOtherDS));


        //percentage 18
        tvPercentageTotalDayAcademicDS = ((TextView) findViewById(R.id.tvPercentageTotalDayAcademicDS));
        tvPercentageAverageHourAcademicDS = ((TextView) findViewById(R.id.tvPercentageAverageHourAcademicDS));

        tvPercentageTotalDayQuranDS = ((TextView) findViewById(R.id.tvPercentageTotalDayQuranDS));
        tvPercentageAverageAyatQuranDS = ((TextView) findViewById(R.id.tvPercentageAverageAyatQuranDS));

        tvPercentageTotalDayHadithDS = ((TextView) findViewById(R.id.tvPercentageTotalDayHadithDS));
        tvPercentageAverageHadisHadithDS = ((TextView) findViewById(R.id.tvPercentageAverageHadisHadithDS));

        tvPercentageTotalPageLiteratureDS = ((TextView) findViewById(R.id.tvPercentageTotalPageLiteratureDS));
        tvPercentageReligonalPageLiteratureDS = ((TextView) findViewById(R.id.tvPercentageReligonalPageLiteratureDS));
        tvPercentageOtherPageLiteratureDS = ((TextView) findViewById(R.id.tvPercentageOtherPageLiteratureDS));

        tvPercentagePresentClassDS = ((TextView) findViewById(R.id.tvPercentagePresentClassDS));
        tvPercentageTotalJamaatNamazDS = ((TextView) findViewById(R.id.tvPercentageTotalJamaatNamazDS));

        tvPercentageTotalDayCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvPercentageTotalDayCallFulkuriWorkDS));
        tvPercentageTotalHourCallFulkuriWorkDS = ((TextView) findViewById(R.id.tvPercentageTotalHourCallFulkuriWorkDS));
        tvPercentageTotalDayOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvPercentageTotalDayOtherFulkuriWorkDS));
        tvPercentageAverageHourOtherFulkuriWorkDS = ((TextView) findViewById(R.id.tvPercentageAverageHourOtherFulkuriWorkDS));

        tvPercentageCriticismOtherDS = ((TextView) findViewById(R.id.tvPercentageCriticismOtherDS));
        tvPercentageGameOtherDS = ((TextView) findViewById(R.id.tvPercentageGameOtherDS));
        tvPercentageNewspaperOtherDS = ((TextView) findViewById(R.id.tvPercentageNewspaperOtherDS));
    }


    private void setValueInTextView() {
        //plan 18
        planValueList = new ArrayList<>();

        planValueList.add(monthlyPlan.get(AllData.MP_A_Total_Day));
        tvPlanTotalDayAcademicDS.setText(planValueList.get(0));
        planValueList.add(monthlyPlan.get(AllData.MP_A_Average_Hour));
        tvPlanAverageHourAcademicDS.setText(planValueList.get(1));

        planValueList.add(monthlyPlan.get(AllData.MP_Q_Total_day));
        tvPlanTotalDayQuranDS.setText(planValueList.get(2));
        planValueList.add(monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        tvPlanAverageAyatQuranDS.setText(planValueList.get(3));

        planValueList.add(monthlyPlan.get(AllData.MP_H_Total_day));
        tvPlanTotalDayHadithDS.setText(planValueList.get(4));
        planValueList.add(monthlyPlan.get(AllData.MP_H_Average_Hadis));
        tvPlanAverageHadisHadithDS.setText(planValueList.get(5));

        planValueList.add(monthlyPlan.get(AllData.MP_L_Total_page));
        tvPlanTotalPageLiteratureDS.setText(planValueList.get(6));
        planValueList.add(monthlyPlan.get(AllData.MP_L_Relioganl_Page));
        tvPlanReligonalPageLiteratureDS.setText(planValueList.get(7));
        planValueList.add(monthlyPlan.get(AllData.MP_L_Other_Page));
        tvPlanOtherPageLiteratureDS.setText(planValueList.get(8));

        planValueList.add(monthlyPlan.get(AllData.MP_A_Total_Day_Class));
        tvPlanPresentClassDS.setText(planValueList.get(9));


        if (monthlyPlan.get(AllData.MP_N_Jamaat) != null) {
            int cbState = Integer.parseInt(monthlyPlan.get(AllData.MP_N_Jamaat));
            if (cbState == 1) {
                planValueList.add((month.getActualMaximum(Calendar.DAY_OF_MONTH) * 5) + "");
                tvPlanTotalJamaatNamazDS.setText(planValueList.get(10));

            } else {
                planValueList.add("");
                tvPlanTotalJamaatNamazDS.setText(planValueList.get(10));
            }
        } else {
            planValueList.add("");
            tvPlanTotalJamaatNamazDS.setText(planValueList.get(10));
        }

        planValueList.add(monthlyPlan.get(AllData.MP_F_C_Total_Day));
        tvPlanTotalDayCallFulkuriWorkDS.setText(planValueList.get(11));
        planValueList.add(monthlyPlan.get(AllData.MP_F_C_Total_Hour));
        tvPlanTotalHourCallFulkuriWorkDS.setText(planValueList.get(12));
        planValueList.add(monthlyPlan.get(AllData.MP_F_O_Total_Day));
        tvPlanTotalDayOtherFulkuriWorkDS.setText(planValueList.get(13));
        planValueList.add(monthlyPlan.get(AllData.MP_F_O_Average_Hour));
        tvPlanAverageHourOtherFulkuriWorkDS.setText(planValueList.get(14));

        planValueList.add(monthlyPlan.get(AllData.MP_O_Criticism));
        tvPlanCriticismOtherDS.setText(planValueList.get(15));
        planValueList.add(monthlyPlan.get(AllData.MP_O_Game));
        tvPlanGameOtherDS.setText(planValueList.get(16));
        planValueList.add(monthlyPlan.get(AllData.MP_O_Newspaper));
        tvPlanNewspaperOtherDS.setText(planValueList.get(17));


        //dairy 18
        diaryValueList = new ArrayList<>();

        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_study_academic"));
        tvDairyTotalDayAcademicDS.setText(diaryValueList.get(0));
        diaryValueList.add(calculation.getAverageType(monthId, "dd_study_academic"));
        tvDairyAverageHourAcademicDS.setText(diaryValueList.get(1));

        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_study_quran"));
        tvDairyTotalDayQuranDS.setText(diaryValueList.get(2));
        diaryValueList.add(calculation.getAverageType(monthId, "dd_study_quran"));
        tvDairyAverageAyatQuranDS.setText(diaryValueList.get(3));

        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_study_hadith"));
        tvDairyTotalDayHadithDS.setText(diaryValueList.get(4));
        diaryValueList.add(calculation.getAverageType(monthId, "dd_study_hadith"));
        tvDairyAverageHadisHadithDS.setText(diaryValueList.get(5));

        diaryValueList.add(calculation.getTotalPage(monthId, "dd_study_literature_rel", "dd_study_literature_other"));
        tvDairyTotalPageLiteratureDS.setText(diaryValueList.get(6));
        diaryValueList.add(calculation.getTotalType(monthId, "dd_study_literature_rel"));
        tvDairyReligonalPageLiteratureDS.setText(diaryValueList.get(7));
        diaryValueList.add(calculation.getTotalType(monthId, "dd_study_literature_other"));
        tvDairyOtherPageLiteratureDS.setText(diaryValueList.get(8));

        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_class"));
        tvDairyPresentClassDS.setText(diaryValueList.get(9));
        diaryValueList.add(calculation.getTotalType(monthId, "dd_namaz_jamaat"));
        tvDairyTotalJamaatNamazDS.setText(diaryValueList.get(10));

        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_fulkuri_call"));
        tvDairyTotalDayCallFulkuriWorkDS.setText(diaryValueList.get(11));
        diaryValueList.add(calculation.getTotalType(monthId, "dd_fulkuri_call"));
        tvDairyTotalHourCallFulkuriWorkDS.setText(diaryValueList.get(12));
        diaryValueList.add(calculation.getTotalTypeDay(monthId, "dd_fulkuri_other"));
        tvDairyTotalDayOtherFulkuriWorkDS.setText(diaryValueList.get(13));
        diaryValueList.add(calculation.getAverageType(monthId, "dd_fulkuri_other"));
        tvDairyAverageHourOtherFulkuriWorkDS.setText(diaryValueList.get(14));

        diaryValueList.add(calculation.getSumForAMonthDiary(monthId, "dd_criticism"));
        tvDairyCriticismOtherDS.setText(diaryValueList.get(15));
        diaryValueList.add(calculation.getSumForAMonthDiary(monthId, "dd_game"));
        tvDairyGameOtherDS.setText(diaryValueList.get(16));
        diaryValueList.add(calculation.getSumForAMonthDiary(monthId, "dd_newspaper"));
        tvDairyNewspaperOtherDS.setText(diaryValueList.get(17));

        //percentage 18
        tvPercentageTotalDayAcademicDS.setText(getPercentage(planValueList.get(0),  diaryValueList.get(0)));
        tvPercentageAverageHourAcademicDS.setText(getPercentage(planValueList.get(1),  diaryValueList.get(1)));

        tvPercentageTotalDayQuranDS.setText(getPercentage(planValueList.get(2),  diaryValueList.get(2)));
        tvPercentageAverageAyatQuranDS.setText(getPercentage(planValueList.get(3),  diaryValueList.get(3)));

        tvPercentageTotalDayHadithDS.setText(getPercentage(planValueList.get(4),  diaryValueList.get(4)));
        tvPercentageAverageHadisHadithDS.setText(getPercentage(planValueList.get(5),  diaryValueList.get(5)));

        tvPercentageTotalPageLiteratureDS.setText(getPercentage(planValueList.get(6),  diaryValueList.get(6)));
        tvPercentageReligonalPageLiteratureDS.setText(getPercentage(planValueList.get(7),  diaryValueList.get(7)));
        tvPercentageOtherPageLiteratureDS.setText(getPercentage(planValueList.get(8),  diaryValueList.get(8)));

        tvPercentagePresentClassDS.setText(getPercentage(planValueList.get(9),  diaryValueList.get(9)));
        tvPercentageTotalJamaatNamazDS.setText(getPercentage(planValueList.get(10),  diaryValueList.get(10)));

        tvPercentageTotalDayCallFulkuriWorkDS.setText(getPercentage(planValueList.get(11),  diaryValueList.get(11)));
        tvPercentageTotalHourCallFulkuriWorkDS.setText(getPercentage(planValueList.get(12),  diaryValueList.get(12)));
        tvPercentageTotalDayOtherFulkuriWorkDS.setText(getPercentage(planValueList.get(13),  diaryValueList.get(13)));
        tvPercentageAverageHourOtherFulkuriWorkDS.setText(getPercentage(planValueList.get(14),  diaryValueList.get(14)));
        
        tvPercentageCriticismOtherDS.setText(getPercentage(planValueList.get(15),  diaryValueList.get(15)));
        tvPercentageGameOtherDS.setText(getPercentage(planValueList.get(16),  diaryValueList.get(16)));
        tvPercentageNewspaperOtherDS.setText(getPercentage(planValueList.get(17),  diaryValueList.get(17)));

    }


    private void refreshCalendar() {
        tvTitle = (TextView) findViewById(R.id.tvDateDS);
        monthId = DateFormat.format("MMMM yyyy", month).toString();
        tvTitle.setText(monthId);

        calculation = new MonthlyDiaryCalculation(this);
        monthlyPlan = dbHelper.getAllContentFromTableMonthlyPlan(monthId);
        setValueInTextView();
    }


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
}
