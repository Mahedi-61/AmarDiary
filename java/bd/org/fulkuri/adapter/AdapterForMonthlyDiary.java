package bd.org.fulkuri.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import bd.org.fulkuri.amardiary.R;
import bd.org.fulkuri.model.AllData;
import bd.org.fulkuri.model.DatabaseHelper;
import bd.org.fulkuri.model.MonthlyDiaryCalculation;

public class AdapterForMonthlyDiary extends BaseAdapter {

    private ArrayList<String> allDayIdList;
    private String monthId;
    DatabaseHelper dbHelper;
    private Context context;
    private MonthlyDiaryCalculation calculation;
    private HashMap<String, String> myMap;

    public AdapterForMonthlyDiary(Context context, ArrayList<String> allDayIdList, String monthId) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        calculation = new MonthlyDiaryCalculation(context);
        this.allDayIdList = allDayIdList;
        this.monthId = monthId;
    }


    public int getCount() {
        return (allDayIdList.size() + 1);
    }


    public Object getItem(int i) {
        return null;
    }


    public long getItemId(int i) {
        return 0L;
    }


    public View getView(int i, View convertView, ViewGroup viewgroup) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            row = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).
                    inflate(R.layout.li_monthly_diary, null);
            holder = new ViewHolder(row);
            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        //allocating monthly diary: last column for total
        if(i == allDayIdList.size()){
            holder.tvDateMD.setText(context.getResources().getString(R.string.total));
            //study -- 5
            holder.tvAcademicMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_S_ACADEMIC));
            holder.tvQuranMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_S_QURAN));
            holder.tvHadithMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_S_HADITH));
            holder.tvLiteratureRelegionMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_S_LITERATURE_REL));
            holder.tvLiteratureOtherMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_S_LITERATURE_OTHER));

            //namaj --2
            holder.tvJamaatMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_N_JAMAAT));
            holder.tvKajaMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_N_KAJJA));

            //fulkuri -- 2
            holder.tvCallMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_F_CALL));
            holder.tvOtherMD.setText(calculation.getSumForAMonthDiary(monthId,AllData.DD_F_OTHER));

            //contacts -- 12
            holder.tvOvijatriMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_OVIJATRI));
            holder.tvDhimanMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_DHIMAN));
            holder.tvBikoshitoMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_BIKOSHITO));
            holder.tvSuvasitoMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_SUVASITO));
            holder.tvMemberMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_MEMBER));
            holder.tvCoukosMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_COUKOS));

            holder.tvOgrropothikMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_OGRROPOTHIK));
            holder.tvTalentStudentMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_TALENT_STUDENT));
            holder.tvAdviserMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_ADVISER));
            holder.tvWellWisherMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_WELLWISHER));
            holder.tvTeacherMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_TEACHER));
            holder.tvGuardianMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_C_GUARDIAN));

            //miscellaneous -- 5 plus class
            holder.cbClassMD.setVisibility(View.GONE);
            holder.cbServiceBankMD.setVisibility(View.GONE);
            holder.cbNewspaperMD.setVisibility(View.GONE);
            holder.cbGameMD.setVisibility(View.GONE);
            holder.cbCriticismMD.setVisibility(View.GONE);

            holder.tvTotalClassMD.setVisibility(View.VISIBLE);
            holder.tvTotalSeviceBankMD.setVisibility(View.VISIBLE);
            holder.tvTotalNewspaperMD.setVisibility(View.VISIBLE);
            holder.tvTotalGameMD.setVisibility(View.VISIBLE);
            holder.tvTotalCriticismMD.setVisibility(View.VISIBLE);

            holder.tvSleepMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_SLEEP));
            holder.tvTotalClassMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_CLASS));
            holder.tvTotalSeviceBankMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_SERVICE_BANK));
            holder.tvTotalNewspaperMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_NEWSPAPER));
            holder.tvTotalGameMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_GAME));
            holder.tvTotalCriticismMD.setText(calculation.getSumForAMonthDiary(monthId, AllData.DD_M_CRITICISM));

        } else{
            myMap = dbHelper.getDailyReportFromDatabase(allDayIdList.get(i));
            holder.tvDateMD.setText((i + 1)+  "");
            holder.tvAcademicMD.setText(myMap.get(AllData.DD_S_ACADEMIC));
            holder.tvQuranMD.setText(myMap.get(AllData.DD_S_QURAN));
            holder.tvHadithMD.setText(myMap.get(AllData.DD_S_HADITH));
            holder.tvLiteratureRelegionMD.setText(myMap.get(AllData.DD_S_LITERATURE_REL));
            holder.tvLiteratureOtherMD.setText( myMap.get(AllData.DD_S_LITERATURE_OTHER));

            holder.tvJamaatMD.setText(myMap.get(AllData.DD_N_JAMAAT));
            holder.tvKajaMD.setText(myMap.get(AllData.DD_N_KAJJA));

            //fulkuri 2
            holder.tvCallMD.setText(myMap.get(AllData.DD_F_CALL));
            holder.tvOtherMD.setText(myMap.get(AllData.DD_F_OTHER));

            //contact 12
            holder.tvOvijatriMD.setText(myMap.get(AllData.DD_C_OVIJATRI));
            holder.tvDhimanMD.setText(myMap.get(AllData.DD_C_DHIMAN));
            holder.tvBikoshitoMD.setText(myMap.get(AllData.DD_C_BIKOSHITO));
            holder.tvSuvasitoMD.setText(myMap.get(AllData.DD_C_SUVASITO));
            holder.tvMemberMD.setText(myMap.get(AllData.DD_C_MEMBER));
            holder.tvCoukosMD.setText(myMap.get(AllData.DD_C_COUKOS));
            holder.tvOgrropothikMD.setText(myMap.get(AllData.DD_C_OGRROPOTHIK));
            holder.tvTalentStudentMD.setText(myMap.get(AllData.DD_C_TALENT_STUDENT));
            holder.tvAdviserMD.setText(myMap.get(AllData.DD_C_ADVISER));
            holder.tvWellWisherMD.setText(myMap.get(AllData.DD_C_WELLWISHER));
            holder.tvTeacherMD.setText(myMap.get(AllData.DD_C_TEACHER));
            holder.tvGuardianMD.setText(myMap.get(AllData.DD_C_GUARDIAN));

            //miscellaneous without class
            holder.tvSleepMD.setText(myMap.get(AllData.DD_M_SLEEP));

            holder.cbClassMD.setVisibility(View.VISIBLE);
            holder.cbServiceBankMD.setVisibility(View.VISIBLE);
            holder.cbNewspaperMD.setVisibility(View.VISIBLE);
            holder.cbGameMD.setVisibility(View.VISIBLE);
            holder.cbCriticismMD.setVisibility(View.VISIBLE);

            holder.tvTotalClassMD.setVisibility(View.GONE);
            holder.tvTotalSeviceBankMD.setVisibility(View.GONE);
            holder.tvTotalNewspaperMD.setVisibility(View.GONE);
            holder.tvTotalGameMD.setVisibility(View.GONE);
            holder.tvTotalCriticismMD.setVisibility(View.GONE);

            if (myMap.get(AllData.DD_M_SERVICE_BANK) != null) {
                if (Integer.parseInt(myMap.get(AllData.DD_M_SERVICE_BANK)) == 1) {
                    holder.cbServiceBankMD.setChecked(true);
                } else {
                    holder.cbServiceBankMD.setChecked(false);
                }
            } else {
                holder.cbServiceBankMD.setChecked(false);
            }

            if (myMap.get(AllData.DD_CLASS) != null) {
                if (Integer.parseInt(myMap.get(AllData.DD_CLASS)) == 1) {
                    holder.cbClassMD.setChecked(true);
                } else {
                    holder.cbClassMD.setChecked(false);
                }
            } else {
                holder.cbClassMD.setChecked(false);
            }

            if (myMap.get(AllData.DD_M_NEWSPAPER) != null) {
                if (Integer.parseInt(myMap.get(AllData.DD_M_NEWSPAPER)) == 1) {
                    holder.cbNewspaperMD.setChecked(true);
                } else {
                    holder.cbNewspaperMD.setChecked(false);
                }
            } else {
                holder.cbNewspaperMD.setChecked(false);
            }

            if (myMap.get(AllData.DD_M_GAME) != null) {
                if (Integer.parseInt(myMap.get(AllData.DD_M_GAME)) == 1) {
                    holder.cbGameMD.setChecked(true);
                } else {
                    holder.cbGameMD.setChecked(false);
                }
            } else {
                holder.cbGameMD.setChecked(false);
            }

            if (myMap.get(AllData.DD_M_CRITICISM) != null) {
                if (Integer.parseInt(myMap.get(AllData.DD_M_CRITICISM)) == 1) {
                    holder.cbCriticismMD.setChecked(true);
                } else {
                    holder.cbCriticismMD.setChecked(false);
                }
            } else {
                holder.cbCriticismMD.setChecked(false);
            }
        }
        return row;
    }




    private class ViewHolder {

        public CheckBox cbClassMD, cbServiceBankMD, cbNewspaperMD, cbGameMD, cbCriticismMD;
        private TextView tvTotalClassMD, tvTotalSeviceBankMD, tvTotalNewspaperMD, tvTotalGameMD,tvTotalCriticismMD;
        public TextView tvDateMD, tvAcademicMD, tvQuranMD, tvHadithMD, tvLiteratureRelegionMD, tvLiteratureOtherMD,
                tvJamaatMD, tvKajaMD, tvCallMD, tvOtherMD,
                tvOvijatriMD, tvDhimanMD, tvBikoshitoMD, tvSuvasitoMD, tvMemberMD, tvCoukosMD,
                tvOgrropothikMD, tvTalentStudentMD, tvAdviserMD, tvWellWisherMD, tvTeacherMD, tvGuardianMD,
                tvSleepMD;


        public ViewHolder(View view) {
            tvDateMD = (TextView) view.findViewById(R.id.tvDateMD);
            tvAcademicMD = (TextView) view.findViewById(R.id.tvAcademicMD);
            tvQuranMD = (TextView) view.findViewById(R.id.tvQuranMD);
            tvHadithMD = (TextView) view.findViewById(R.id.tvHadithMD);
            tvLiteratureRelegionMD = (TextView) view.findViewById(R.id.tvLiteratureRelegionMD);
            tvLiteratureOtherMD = (TextView) view.findViewById(R.id.tvLiteratureOtherMD);

            cbClassMD = (CheckBox) view.findViewById(R.id.cbclassMD);
            cbServiceBankMD = (CheckBox) view.findViewById(R.id.cbServiceBankMD);
            cbNewspaperMD = (CheckBox) view.findViewById(R.id.cbNewspaperMD);
            cbGameMD = (CheckBox) view.findViewById(R.id.cbGameMD);
            cbCriticismMD = (CheckBox) view.findViewById(R.id.cbCriticismMD);

            tvTotalClassMD = (TextView) view.findViewById(R.id.tvTotalClassMD);
            tvTotalSeviceBankMD = (TextView) view.findViewById(R.id.tvTotalSeviceBankMD);
            tvTotalNewspaperMD = (TextView) view.findViewById(R.id.tvTotalNewspaperMD);
            tvTotalGameMD = (TextView) view.findViewById(R.id.tvTotalGameMD);
            tvTotalCriticismMD = (TextView) view.findViewById(R.id.tvTotalCriticismMD);

            tvJamaatMD = (TextView) view.findViewById(R.id.tvJamaatMD);
            tvKajaMD = (TextView) view.findViewById(R.id.tvKajjaMD);

            tvCallMD = (TextView) view.findViewById(R.id.tvCallMD);
            tvOtherMD = (TextView) view.findViewById(R.id.tvOtherMD);

            tvOvijatriMD = (TextView) view.findViewById(R.id.tvOvijatriMD);
            tvDhimanMD = (TextView) view.findViewById(R.id.tvDhimanMD);
            tvBikoshitoMD = (TextView) view.findViewById(R.id.tvBikoshitoMD);
            tvSuvasitoMD = (TextView) view.findViewById(R.id.tvSuvasitoMD);
            tvMemberMD = (TextView) view.findViewById(R.id.tvMemberMD);
            tvCoukosMD = (TextView) view.findViewById(R.id.tvCoukosMD);
            tvOgrropothikMD = (TextView) view.findViewById(R.id.tvOgrropothikMD);
            tvTalentStudentMD = (TextView) view.findViewById(R.id.tvTalentStudentMD);
            tvAdviserMD = (TextView) view.findViewById(R.id.tvAdviserMD);
            tvWellWisherMD = (TextView) view.findViewById(R.id.tvWellWisherMD);
            tvTeacherMD = (TextView) view.findViewById(R.id.tvTeacherMD);
            tvGuardianMD = (TextView) view.findViewById(R.id.tvGuardianMD);

            tvSleepMD = (TextView) view.findViewById(R.id.tvSleepMD);
        }
    }
}

