package bd.org.fulkuri.model;

import android.content.Context;

import java.util.ArrayList;

public class MonthlyDiaryCalculation {

    Context context;
    DatabaseHelper dbHelper;

    public MonthlyDiaryCalculation(Context context) {
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }


    public String getAverageType(String monthId, String type) {

        Double totalDay = Double.parseDouble(getTotalTypeDay(monthId, type));
        Double totalData = Double.parseDouble(getTotalType(monthId, type));

        if (totalData == 0.0) {
            return "0.0";
        } else {
            return String.valueOf(totalData / totalDay);
        }
    }


    public String getTotalType(String monthId, String type) {
        ArrayList<String> typeTotal = dbHelper.getEachColumnForMonthFromDatabase(monthId, type);
        double sum = 0.0D;

        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) &&  (!(typeTotal.get(i)).equals("."))){

                try{
                     Double.parseDouble(typeTotal.get(i));
                }catch (Throwable e){
                    continue;
                }
                sum = sum + Double.parseDouble(typeTotal.get(i));
            }
        }
        return String.valueOf((int) sum);
    }


    public String getTotalTypeDay(String monthId, String type) {
        ArrayList<String> typeTotal = dbHelper.getEachColumnForMonthFromDatabase(monthId, type);
        int sum = 0;
        Double value;
        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) &&  (!(typeTotal.get(i)).equals("."))) {
                try{
                    value = Double.parseDouble(typeTotal.get(i));
                }catch (Throwable e){
                    continue;
                }

                if(!(value == 0.0D)){
                    sum++;
                }
            }
        }
        return String.valueOf(sum);
    }


    public String getSumForAMonthDiary(String monthId, String appendixType) {
        double sum = 0.0D;
        ArrayList<String> allEntry = dbHelper.getEachColumnForMonthFromDatabase(monthId, appendixType);

        for (int i = 0; i < allEntry.size(); i++) {
            if ((allEntry.get(i) != null) && (!(allEntry.get(i)).equals("")) && (!(allEntry.get(i)).equals("."))) {
                sum = sum + Double.parseDouble(allEntry.get(i));
            }
        }

        return String.valueOf((int) sum);
    }

    public String getTotalPage(String monthId, String islamicPage, String otherPage) {
        return String.valueOf((Integer.parseInt(getSumForAMonthDiary(monthId, islamicPage)) +
                Integer.parseInt(getSumForAMonthDiary(monthId, otherPage))));
    }

}
