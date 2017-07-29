package bd.org.fulkuri.model;

import android.content.Context;
import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class PreviousDaysReportCalculation {

    private Context context;
    private Calendar today;
    private int dayNo;
    private DatabaseHelper dbHelper;

    public PreviousDaysReportCalculation(Context context, int dayNo) {

        today = Calendar.getInstance();
        this.context = context;
        dbHelper = new DatabaseHelper(context);
        this.dayNo = dayNo;
    }


    public String getFromAndTo() {

        Calendar calendar = Calendar.getInstance();
        String beginningDay = "";
        String endingDay = "";
        int i = 1;
        do {
            if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
                if (calendar.get(Calendar.MONTH) == calendar.getActualMinimum(Calendar.MONTH)) {
                    calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.getActualMaximum(Calendar.MONTH), 31);

                } else {
                    calendar.add(Calendar.MONTH, -1);
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
            }
            if (i == 1) {
                endingDay = DateFormat.format("dd, MMMM yyyy", calendar).toString();
            }
            if (i == dayNo) {
                beginningDay = DateFormat.format("dd, MMMM yyyy", calendar).toString();
                return (beginningDay + "  To  " + endingDay);
            }
            i++;
        } while (true);
    }


    public ArrayList<String> getLastDaysReportForEachField(String column) {
        Calendar calendar = Calendar.getInstance();
        ArrayList<String> arraylist = new ArrayList<>();
        int k = dayNo;
        int count = 0;

        do {

            boolean flag = false;

            if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
                if (calendar.get(Calendar.MONTH) == calendar.getActualMinimum(Calendar.MONTH)) {
                    calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.getActualMaximum(Calendar.MONTH), 31);

                } else {
                    calendar.add(Calendar.MONTH, -1);
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }

                if (k > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    k = k - calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    count = count + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    String monthId = DateFormat.format("MMMM yyyy", calendar).toString();
                    arraylist.addAll(dbHelper.getEachColumnForMonthFromDatabase(monthId, column));

                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    flag = true;
                }

            } else {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);

            }
            if (!flag) {
                k--;
                count++;
                String dayId = DateFormat.format("EE, dd MMMM yyyy", calendar).toString();
                arraylist.add(dbHelper.getEachColumnForDayFromDatabase(dayId, column));
            }

            if (count == dayNo) {
                return arraylist;
            }
        } while (true);
    }


    public String getAverageType(String type) {

        Double totalDay = Double.parseDouble(getTotalTypeDay(type));
        Double totalData = Double.parseDouble(getTotalType(type));

        if (totalData == 0.0) {
            return "0.0";
        } else {
            return String.valueOf(totalData / totalDay);
        }
    }


    public String getTotalType(String type) {
        ArrayList<String> typeTotal = getLastDaysReportForEachField(type);
        double sum = 0.0D;

        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) && !(typeTotal.get(i)).equals("0")) {
                sum = sum + Double.parseDouble(typeTotal.get(i));
            }
        }
        return String.valueOf((int) sum);
    }


    public String getTotalTypeDay(String type) {
        ArrayList<String> typeTotal = getLastDaysReportForEachField(type);
        int sum = 0;

        for (int i = 0; i < typeTotal.size(); i++) {
            if ((typeTotal.get(i) != null) && (!(typeTotal.get(i)).equals("")) && !(typeTotal.get(i)).equals("0")) {
                sum++;
            }
        }
        return String.valueOf(sum);
    }


    public String getAllContact(String contactType) {
        double sum = 0.0D;
        ArrayList<String> allContact = getLastDaysReportForEachField(contactType);

        for (int i = 0; i < allContact.size(); i++) {
            if ((allContact.get(i) != null) && (!(allContact.get(i)).equals(""))) {
                sum = sum + Double.parseDouble(allContact.get(i));
            }
        }

        return String.valueOf((int) sum);
    }


    public String getAllOthers(String appendixType) {
        int sum = 0;

        ArrayList<String> allOthers = getLastDaysReportForEachField(appendixType);
        for (int i = 0; i < allOthers.size(); i++) {
            if ((allOthers.get(i) != null) && (!(allOthers.get(i)).equals(""))) {
                sum = sum + Integer.parseInt(allOthers.get(i));
            }
        }
        return String.valueOf(sum);
    }


    public String getTotalPage(String islamicPage, String otherPage) {
        return String.valueOf((Integer.parseInt(getAllOthers(islamicPage)) +
                Integer.parseInt(getAllOthers(otherPage))));
    }


    public String getPreviousDairyKeepingDays() {

        Calendar calendar = Calendar.getInstance();
        ArrayList<Integer> arraylist = new ArrayList<>();
        int k = dayNo;
        int count = 0;

        do {
            boolean flag = false;

            if (calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMinimum(Calendar.DAY_OF_MONTH)) {
                if (calendar.get(Calendar.MONTH) == calendar.getActualMinimum(Calendar.MONTH)) {
                    calendar.set(calendar.get(Calendar.YEAR) - 1, calendar.getActualMaximum(Calendar.MONTH), 31);

                } else {
                    calendar.add(Calendar.MONTH, -1);
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                }

                if (k > calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) {
                    k = k - calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                    count = count + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

                    String monthId = DateFormat.format("MMMM yyyy", calendar).toString();
                    arraylist.addAll(dbHelper.getTotalNumberOfDairyKeepingInfoForMonth(monthId));

                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                            calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
                    flag = true;
                }

            } else {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);

            }
            if (!flag) {
                k--;
                count++;
                String dayId = DateFormat.format("EE, dd MMMM yyyy", calendar).toString();
                arraylist.add(dbHelper.getWthereDairyKeepsOrNotFromDayId(dayId));
            }

            if (count == dayNo) {
                int sum = 0;
                for(int i: arraylist){
                    sum = sum + i;
                }

                return (sum + "");
            }
        } while (true);
    }

}

