package bd.org.fulkuri.amardiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

import bd.org.fulkuri.model.AllData;
import bd.org.fulkuri.model.PreviousDaysReportCalculation;

public class SearchDiaryByPreviousDays extends AppCompatActivity implements View.OnClickListener{

    private TextView tvFromAndToPD, tvDairyKeepingDaysPD,
            tvTotalDayAcademicPD, tvAverageHourAcademicPD, tvTotalDayQuranPD,
            tvAverageAyatQuranPD, tvTotalDayHadithPD, tvAverageHadisHadithPD,
            tvTotalPageLiteraturePD, tvReligonalPageLiteraturePD, tvOtherPageLiteraturePD,
            tvPresentClassPD, tvTotalJamaatNamazPD, tvTotalKajjaNamazPD, tvTotalDayCallFulkuriWorkPD,
            tvOvijatriContactPD, tvDhimanContactPD, tvBikoshitoContactPD, tvSuvasitoContactPD,
            tvMemberContactPD, tvCoukosContactPD, tvOgrropothikContactPD, tvTalentStudentContactPD,
            tvAdviserContactPD, tvWellWisherContactPD, tvTeacherContactPD, tvGuardianContactPD,
            tvTotalHourCallFulkuriWorkPD, tvTotalDayOtherFulkuriWorkPD, tvAverageHourOtherFulkuriWorkPD,
            tvCriticismOtherPD, tvGameOtherPD, tvNewspaperOtherPD;


    private Button bSendPD;
    private Toolbar mToolbar;
    private  Handler handler;
    private ProgressDialog pd;
    private String sDayNo;
    private ArrayList<String> list;
    private PreviousDaysReportCalculation calculation;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_previous_diary);
        sDayNo = getIntent().getStringExtra("last_days");

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Previous " + sDayNo + " Days Diary");

        initialize();
        pd = ProgressDialog.show(this, "", "Calculating Diary. Please wait...");
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                setValueInTextView();
                pd.dismiss();
            }
        };

        backgroundWork.start();
        bSendPD.setOnClickListener(this);
    }



    private void initialize() {

        tvFromAndToPD = ((TextView) findViewById(R.id.tvFromAndToPD));
        tvDairyKeepingDaysPD = ((TextView) findViewById(R.id.tvDairyKeepingDaysPD));


        tvTotalDayAcademicPD = ((TextView) findViewById(R.id.tvTotalDayAcademicPD));
        tvAverageHourAcademicPD = ((TextView) findViewById(R.id.tvAverageHourAcademicPD));

        tvTotalDayQuranPD = ((TextView) findViewById(R.id.tvTotalDayQuranPD));
        tvAverageAyatQuranPD = ((TextView) findViewById(R.id.tvAverageAyatQuranPD));

        tvTotalDayHadithPD = ((TextView) findViewById(R.id.tvTotalDayHadithPD));
        tvAverageHadisHadithPD = ((TextView) findViewById(R.id.tvAverageHadisHadithPD));

        tvTotalPageLiteraturePD = ((TextView) findViewById(R.id.tvTotalPageLiteraturePD));
        tvReligonalPageLiteraturePD = ((TextView) findViewById(R.id.tvReligonalPageLiteraturePD));
        tvOtherPageLiteraturePD = ((TextView) findViewById(R.id.tvOtherPageLiteraturePD));

        tvPresentClassPD = ((TextView) findViewById(R.id.tvPresentClassPD));
        tvTotalJamaatNamazPD = ((TextView) findViewById(R.id.tvTotalJamaatNamazPD));
        tvTotalKajjaNamazPD = ((TextView) findViewById(R.id.tvTotalKajjaNamazPD));

        tvOvijatriContactPD = ((TextView) findViewById(R.id.tvOvijatriContactPD));
        tvDhimanContactPD = ((TextView) findViewById(R.id.tvDhimanContactPD));
        tvBikoshitoContactPD = ((TextView) findViewById(R.id.tvBikoshitoContactPD));
        tvSuvasitoContactPD = ((TextView) findViewById(R.id.tvSuvasitoContactPD));
        tvMemberContactPD = ((TextView) findViewById(R.id.tvMemberContactPD));
        tvCoukosContactPD = ((TextView) findViewById(R.id.tvCoukosContactPD));
        tvOgrropothikContactPD = ((TextView) findViewById(R.id.tvOgrropothikContactPD));
        tvTalentStudentContactPD = ((TextView) findViewById(R.id.tvTalentStudentContactPD));
        tvAdviserContactPD = ((TextView) findViewById(R.id.tvAdviserContactPD));
        tvWellWisherContactPD = ((TextView) findViewById(R.id.tvWellWisherContactPD));
        tvTeacherContactPD = ((TextView) findViewById(R.id.tvTeacherContactPD));
        tvGuardianContactPD = ((TextView) findViewById(R.id.tvGuardianContactPD));

        tvCriticismOtherPD = ((TextView) findViewById(R.id.tvPresentClassPD));

        tvTotalDayCallFulkuriWorkPD = ((TextView) findViewById(R.id.tvTotalDayCallFulkuriWorkPD));
        tvTotalHourCallFulkuriWorkPD = ((TextView) findViewById(R.id.tvTotalHourCallFulkuriWorkPD));
        tvTotalDayOtherFulkuriWorkPD = ((TextView) findViewById(R.id.tvTotalDayOtherFulkuriWorkPD));
        tvAverageHourOtherFulkuriWorkPD = ((TextView) findViewById(R.id.tvAverageHourOtherFulkuriWorkPD));

        tvCriticismOtherPD = ((TextView) findViewById(R.id.tvCriticismOtherPD));
        tvGameOtherPD = ((TextView) findViewById(R.id.tvGameOtherPD));
        tvNewspaperOtherPD = ((TextView) findViewById(R.id.tvNewspaperOtherPD));

        bSendPD = (Button) findViewById(R.id.bSendPD);
    }


    private void setValueInTextView() {
        
        tvFromAndToPD.setText(list.get(0));
        tvDairyKeepingDaysPD.setText(list.get(1));

        tvTotalDayAcademicPD.setText(list.get(2));
        tvAverageHourAcademicPD.setText(list.get(3));

        tvTotalDayQuranPD.setText(list.get(4));
        tvAverageAyatQuranPD.setText(list.get(5));

        tvTotalDayHadithPD.setText(list.get(6));
        tvAverageHadisHadithPD.setText(list.get(7));

        tvTotalPageLiteraturePD.setText(list.get(8));
        tvReligonalPageLiteraturePD.setText(list.get(9));
        tvOtherPageLiteraturePD.setText(list.get(10));


        tvPresentClassPD.setText(list.get(11));
        tvTotalJamaatNamazPD.setText(list.get(12));
        tvTotalKajjaNamazPD.setText(list.get(13));

        tvOvijatriContactPD.setText(list.get(14));
        tvDhimanContactPD.setText(list.get(15));
        tvBikoshitoContactPD.setText(list.get(16));
        tvSuvasitoContactPD.setText(list.get(17));
        tvMemberContactPD.setText(list.get(18));
        tvCoukosContactPD.setText(list.get(19));
        tvOgrropothikContactPD.setText(list.get(20));
        tvTalentStudentContactPD.setText(list.get(21));
        tvAdviserContactPD.setText(list.get(22));
        tvWellWisherContactPD.setText(list.get(23));
        tvTeacherContactPD.setText(list.get(24));
        tvGuardianContactPD.setText(list.get(25));

        tvTotalDayCallFulkuriWorkPD.setText(list.get(26));
        tvTotalHourCallFulkuriWorkPD.setText(list.get(27));
        tvTotalDayOtherFulkuriWorkPD.setText(list.get(28));
        tvAverageHourOtherFulkuriWorkPD.setText(list.get(29));


        tvCriticismOtherPD.setText(list.get(30));
        tvGameOtherPD.setText(list.get(31));
        tvNewspaperOtherPD.setText(list.get(32));
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


    Thread backgroundWork = new Thread(new Runnable() {
        @Override
        public void run() {

            try {
                calculation = new PreviousDaysReportCalculation(SearchDiaryByPreviousDays.this, Integer.parseInt(sDayNo));
                list = new ArrayList<>();
                list.add(calculation.getFromAndTo());
                list.add(calculation.getPreviousDairyKeepingDays());
                list.add(calculation.getTotalTypeDay(AllData.DD_S_ACADEMIC));
                list.add(calculation.getAverageType(AllData.DD_S_ACADEMIC));

                list.add(calculation.getTotalTypeDay(AllData.DD_S_QURAN));
                list.add(calculation.getAverageType(AllData.DD_S_QURAN));
                list.add(calculation.getTotalTypeDay(AllData.DD_S_HADITH));
                list.add(calculation.getAverageType(AllData.DD_S_HADITH));

                list.add(calculation.getTotalPage(AllData.DD_S_LITERATURE_REL, AllData.DD_S_LITERATURE_OTHER));
                list.add(calculation.getTotalType(AllData.DD_S_LITERATURE_REL));
                list.add(calculation.getTotalType(AllData.DD_S_LITERATURE_OTHER));

                list.add(calculation.getTotalTypeDay(AllData.DD_CLASS));
                list.add(calculation.getTotalType(AllData.DD_N_JAMAAT));
                list.add(calculation.getTotalType(AllData.DD_N_KAJJA));

                list.add(calculation.getAllContact(AllData.DD_C_OVIJATRI));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_DHIMAN));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_BIKOSHITO));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_SUVASITO));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_MEMBER));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_COUKOS));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_OGRROPOTHIK));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_TALENT_STUDENT));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_ADVISER));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_WELLWISHER));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_TEACHER));
                list.add(calculation.getTotalTypeDay(AllData.DD_C_GUARDIAN));

                list.add(calculation.getTotalTypeDay(AllData.DD_F_CALL));
                list.add(calculation.getTotalType(AllData.DD_F_CALL));
                list.add(calculation.getTotalTypeDay(AllData.DD_F_OTHER));
                list.add(calculation.getAverageType(AllData.DD_F_OTHER));

                list.add(calculation.getAllOthers(AllData.DD_M_CRITICISM));
                list.add(calculation.getAllOthers(AllData.DD_M_GAME));
                list.add(calculation.getAllOthers(AllData.DD_M_NEWSPAPER));

                threadMessage(list.size());
            }catch (Throwable t){
                Log.e("Allah help me" , "waiting for finish");
            }
        }

        private void threadMessage(int size){
            if(list.size() == size){
                Message msg = handler.obtainMessage();
                handler.sendMessage(msg);
            }
        }
    });



    /////// code for sending email /////////////////
    public void onClick(View view) {
        sendDataIntoEmail();
    }


    private void sendDataIntoEmail(){
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("message/rfc822");
        intent.putExtra("android.intent.extra.EMAIL", new String[]{""});
        intent.putExtra("android.intent.extra.SUBJECT", (new StringBuilder("Amar Diary For Last ")).
                append(sDayNo).append(" Days").toString());

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
                    intent.putExtra("android.intent.extra.INITIAL_INTENTS",
                            (Parcelable[]) stack.toArray(new Parcelable[stack.size()]));

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
        StringBuilder diary = new StringBuilder("");

        diary.
                append("বিগত ").append(sDayNo).append(" দিনের ডায়রীঃ ").
                append("\nতারিখঃ ").append(list.get(0)).
                append("\n\nমোট ডাইয়েরী রাখা হয়েছেঃ ").append(list.get(1)).append(" দিন").

                append("\n\nঅ্যাকাডেমিক অধ্যয়নঃ").
                append("\nমোট দিনঃ   ").append(list.get(2)).
                append("\nগড় ঘন্টাঃ  ").append(list.get(3)).
                append("\nক্লাসে উপস্থিতিঃ  ").append(list.get(11)).

                append("\nকুরআন অধ্যয়নঃ").
                append("\nমোট দিনঃ   ").append(list.get(4)).
                append("\nগড় আয়াতঃ   ").append(list.get(5)).

                append("\n\nহাদীস অধ্যয়নঃ ").
                append("\nমোট দিনঃ   ").append(list.get(6)).
                append("\nগড় হাদীসঃ   ").append(list.get(7)).

                append("\n\nসাহিত্য অধ্যয়নঃ").
                append("\nমোট পৃষ্ঠাঃ   ").append(list.get(8)).
                append("\nধর্মীয় পৃষ্ঠাঃ  ").append(list.get(9)).
                append("\nঅন্যান্য পৃষ্ঠাঃ  ").append(list.get(10)).

                append("\n\nনামাজঃ").
                append("\nমোট জামায়াতঃ   ").append(list.get(12)).
                append("\nমোট কাজ্বাঃ ").append(list.get(13)).


                append("\n\nফুলকুঁড়ির কাজঃ").
                append("\nআহ্বানমূলক কাজঃ").
                append("\nমোট দিনঃ   ").append(list.get(26)).
                append("\nমোট ঘন্টাঃ  ").append(list.get(27)).

                append("\n\nঅন্যান্য কাজঃ").
                append("\nমোট দিনঃ   ").append(list.get(28)).
                append("\nগড় ঘন্টাঃ  ").append(list.get(29)).

                append("\n\nঅন্যান্যঃ").
                append("\nআত্নপর্যালোচনাঃ   ").append(list.get(30)).
                append("\nখেলাধুলাঃ   ").append(list.get(31)).
                append("\nপত্রপত্রিকা পাঠঃ   ").append(list.get(32));

        return new String(diary);
    }
}
