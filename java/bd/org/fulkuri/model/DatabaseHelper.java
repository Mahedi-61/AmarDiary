package bd.org.fulkuri.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "amar_dairy";
    public static String DB_PATH = "/data/data/bd.org.fulkuri.amardairy/databases/";
    public static final int DB_VERSION = 1;
    public static final String TABLE_1 = "tb_daily_dairy";
    public static final String TABLE_2 = "tb_et_md";
    public static final String TABLE_3 = "tb_monthly_plan";

    public static SQLiteDatabase db;
    private final Context context;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }


    private boolean checkDataBase() {
        return (new File((new StringBuilder(String.valueOf(DB_PATH))).append(DB_NAME).toString())).exists();
    }


    private void copyDataBase() throws IOException {
        InputStream inputstream = context.getAssets().open(DB_NAME);
        FileOutputStream fileoutputstream = new FileOutputStream((new StringBuilder(String.valueOf(DB_PATH)))
                .append(DB_NAME).toString());

        byte abyte0[] = new byte[1024];
        do {
            int i = inputstream.read(abyte0);
            if (i <= 0) {
                fileoutputstream.flush();
                fileoutputstream.close();
                inputstream.close();
                return;
            }
            fileoutputstream.write(abyte0, 0, i);
        } while (true);
    }


    public void openDataBase() throws SQLException {
        db = SQLiteDatabase.openDatabase((new StringBuilder(String.valueOf(DB_PATH)))
                .append(DB_NAME).toString(), null, 0);
    }

    
    public void close() {
        db.close();
    }


    public void onCreate(SQLiteDatabase sqlitedatabase) {
    }


    public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
    }
    

    public void createDataBase() throws IOException {
        if (!checkDataBase()) {
            getReadableDatabase().close();
            try {
                copyDataBase();
                return;

            } catch (IOException ioexception) {
                throw new Error("Error copying database");
            }
        } else {
            Log.e("allah help me", "database is already exits");
            getReadableDatabase().close();
            return;
        }
    }


    //table 1
    public int checkThisMonthExistOrNot(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT _id FROM " +
                TABLE_2 + " WHERE md_month = '")).append(monthId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = 61;
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }


    public ContentValues setAllContentValuesForTableDailyDairy(HashMap<String, String> dailyDiary) {
        ContentValues contentvalues = new ContentValues();
        
        contentvalues.put("day_id", dailyDiary.get("day_id"));
        contentvalues.put("month_id", dailyDiary.get("month_id"));

        contentvalues.put("dd_study_academic",         dailyDiary.get("dd_study_academic"));
        contentvalues.put("dd_study_quran",            dailyDiary.get("dd_study_quran"));
        contentvalues.put("dd_study_hadith",           dailyDiary.get("dd_study_hadith"));
        contentvalues.put("dd_study_literature_rel",   dailyDiary.get("dd_study_literature_rel"));
        contentvalues.put("dd_study_literature_other", dailyDiary.get("dd_study_literature_other"));

        contentvalues.put("dd_namaz_jamaat",           dailyDiary.get("dd_namaz_jamaat"));
        contentvalues.put("dd_namaz_kajja",            dailyDiary.get("dd_namaz_kajja"));

        contentvalues.put("dd_fulkuri_call",           dailyDiary.get("dd_fulkuri_call"));
        contentvalues.put("dd_fulkuri_other",          dailyDiary.get("dd_fulkuri_other"));
        contentvalues.put("dd_sleep",                  dailyDiary.get("dd_sleep"));

        contentvalues.put("dd_contact_member",         dailyDiary.get("dd_contact_member"));
        contentvalues.put("dd_contact_coukos",         dailyDiary.get("dd_contact_coukos"));
        contentvalues.put("dd_contact_ogrropothik",    dailyDiary.get("dd_contact_ogrropothik"));
        contentvalues.put("dd_contact_suvasito",       dailyDiary.get("dd_contact_suvasito"));
        contentvalues.put("dd_contact_bikoshito",      dailyDiary.get("dd_contact_bikoshito"));
        contentvalues.put("dd_contact_dhiman",         dailyDiary.get("dd_contact_dhiman"));
        contentvalues.put("dd_contact_ovijatri",       dailyDiary.get("dd_contact_ovijatri"));
        contentvalues.put("dd_contact_talent_student", dailyDiary.get("dd_contact_talent_student"));
        contentvalues.put("dd_contact_teacher",        dailyDiary.get("dd_contact_teacher"));
        contentvalues.put("dd_contact_guardian",       dailyDiary.get("dd_contact_guardian"));
        contentvalues.put("dd_contact_adviser",        dailyDiary.get("dd_contact_adviser"));
        contentvalues.put("dd_contact_wellwisher",     dailyDiary.get("dd_contact_wellwisher"));

        contentvalues.put("dd_class",                  dailyDiary.get("dd_class"));
        contentvalues.put("dd_service_bank",           dailyDiary.get("dd_service_bank"));
        contentvalues.put("dd_criticism",              dailyDiary.get("dd_criticism"));
        contentvalues.put("dd_game",                   dailyDiary.get("dd_game"));
        contentvalues.put("dd_newspaper",              dailyDiary.get("dd_newspaper"));
        
        return contentvalues;
    }


    public HashMap<String, String> getDailyReportFromDatabase(String dayId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("' ").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();

            hashmap.put("dd_study_academic",          myCursor.getString(3));
            hashmap.put("dd_study_quran",             myCursor.getString(4));
            hashmap.put("dd_study_hadith",            myCursor.getString(5));
            hashmap.put("dd_study_literature_rel",    myCursor.getString(6));
            hashmap.put("dd_study_literature_other",  myCursor.getString(7));

            hashmap.put("dd_namaz_jamaat",            myCursor.getString(8));
            hashmap.put("dd_namaz_kajja",             myCursor.getString(9));

            hashmap.put("dd_fulkuri_call",            myCursor.getString(10));
            hashmap.put("dd_fulkuri_other",           myCursor.getString(11));
            hashmap.put("dd_sleep",                   myCursor.getString(12));

            hashmap.put("dd_contact_member",          myCursor.getString(13));
            hashmap.put("dd_contact_coukos",          myCursor.getString(14));
            hashmap.put("dd_contact_ogrropothik",     myCursor.getString(15));
            hashmap.put("dd_contact_suvasito",        myCursor.getString(16));
            hashmap.put("dd_contact_bikoshito",       myCursor.getString(17));
            hashmap.put("dd_contact_dhiman",          myCursor.getString(18));
            hashmap.put("dd_contact_ovijatri",        myCursor.getString(19));
            hashmap.put("dd_contact_talent_student",  myCursor.getString(20));
            hashmap.put("dd_contact_teacher",         myCursor.getString(21));
            hashmap.put("dd_contact_guardian",        myCursor.getString(22));
            hashmap.put("dd_contact_adviser",         myCursor.getString(23));
            hashmap.put("dd_contact_wellwisher",      myCursor.getString(24));

            hashmap.put("dd_class",                   myCursor.getString(25));
            hashmap.put("dd_service_bank",            myCursor.getString(26));
            hashmap.put("dd_criticism",               myCursor.getString(27));
            hashmap.put("dd_game",                    myCursor.getString(28));
            hashmap.put("dd_newspaper",               myCursor.getString(29));
            
        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }


    public String getEachColumnForDayFromDatabase(String dayId, String column) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        String data = "";
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT ")).append(column).append(" FROM ")
                .append(TABLE_1).append(" WHERE ").append("day_id").append(" = '").append(dayId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                data = myCursor.getString(0);

            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return data;
    }


    public ArrayList<String> getEachColumnForMonthFromDatabase(String monthId, String column) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        ArrayList<String> arraylist = new ArrayList();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT ")).append(column).append(" FROM ").append(TABLE_1)
                .append(" WHERE ").append("month_id").append(" = '").append(monthId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                arraylist.add(myCursor.getString(0));
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return arraylist;
    }


    public int getIdFromDayId(String dayId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT _id FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = myCursor.getInt(0);
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }


    public ArrayList<Integer> getTotalNumberOfDairyKeepingInfoForMonth(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        ArrayList<Integer> arraylist = new ArrayList();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT keeping_dairy FROM " + TABLE_1 +
                " WHERE month_id = '")).append(monthId).append("'").toString(), null);

        if (myCursor.moveToFirst()) {
            do {
                arraylist.add(myCursor.getInt(0));
            } while (myCursor.moveToNext());
        }
        myCursor.close();
        sqlitedatabase.close();
        return arraylist;
    }


    public int getWthereDairyKeepsOrNotFromDayId(String dayId) {

        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor cursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT DISTINCT keeping_dairy FROM " + TABLE_1 + " WHERE day_id = '"))
                .append(dayId).append("'").toString(), null);

        int i = 0;
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                i = cursor.getInt(0);
            }
        }
        cursor.close();
        sqlitedatabase.close();
        return i;
    }


    public void insertRowInTableDailyDairy (HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_1, null, setAllContentValuesForTableDailyDairy(hashmap));
        sqlitedatabase.close();
    }
    

    public void updateRowInTableDailyDairy(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_1, setAllContentValuesForTableDailyDairy(hashmap),
                "_id = ?", new String[]{ hashmap.get("_id")});
        sqlitedatabase.close();
    }


    //table 2
    public ContentValues settAllContentValuesForTableEtMR(HashMap<String, String> monthlyDiary) {
        ContentValues contentvalues = new ContentValues();

        contentvalues.put("md_month",                          monthlyDiary.get("md_month"));
        contentvalues.put("md_quran_sura_name",                monthlyDiary.get("md_quran_sura_name"));
        contentvalues.put("md_quran_memorize_ayat",            monthlyDiary.get("md_quran_memorize_ayat"));
        contentvalues.put("md_quran_alocona",                  monthlyDiary.get("md_quran_alocona"));
        contentvalues.put("md_hadith_book_name",               monthlyDiary.get("md_hadith_book_name"));
        contentvalues.put("md_hadith_memorize_hadis",          monthlyDiary.get("md_hadith_memorize_hadis"));
        contentvalues.put("md_hadith_alocona",                 monthlyDiary.get("md_hadith_alocona"));
        contentvalues.put("md_literature_book_name",           monthlyDiary.get("md_literature_book_name"));
        contentvalues.put("md_literature_book_note",            monthlyDiary.get("md_literature_book_note"));
        contentvalues.put("md_namaz_nofol",                     monthlyDiary.get("md_namaz_nofol"));
        contentvalues.put("md_contact_ongkurito",               monthlyDiary.get("md_contact_ongkurito"));
        contentvalues.put("md_contact_friend",                  monthlyDiary.get("md_contact_friend"));
        contentvalues.put("md_service_work_total_day",          monthlyDiary.get("md_service_work_total_day"));
        contentvalues.put("md_service_work_average_time",       monthlyDiary.get("md_service_work_average_time"));
        contentvalues.put("md_distribution_fulkuri",            monthlyDiary.get("md_distribution_fulkuri"));
        contentvalues.put("md_distribution_poriciti",           monthlyDiary.get("md_distribution_poriciti"));
        contentvalues.put("md_distribution_stikar",             monthlyDiary.get("md_distribution_stikar"));
        contentvalues.put("md_distribution_literature",         monthlyDiary.get("md_distribution_literature"));
        contentvalues.put("md_distribution_amar_proitdin",      monthlyDiary.get("md_distribution_amar_proitdin"));
        contentvalues.put("md_distribution_gift",               monthlyDiary.get("md_distribution_gift"));
        contentvalues.put("md_distribution_cd_dvd",             monthlyDiary.get("md_distribution_cd_dvd"));
        contentvalues.put("md_distribution_other",              monthlyDiary.get("md_distribution_other"));
        contentvalues.put("md_increment_member",                 monthlyDiary.get("md_increment_member"));
        contentvalues.put("md_increment_coukos",                 monthlyDiary.get("md_increment_coukos"));
        contentvalues.put("md_increment_ogrropothik",            monthlyDiary.get("md_increment_ogrropothik"));
        contentvalues.put("md_increment_suvasito",               monthlyDiary.get("md_increment_suvasito"));
        contentvalues.put("md_increment_bikoshito",              monthlyDiary.get("md_increment_bikoshito"));
        contentvalues.put("md_increment_dhiman",                 monthlyDiary.get("md_increment_dhiman"));
        contentvalues.put("md_increment_ovijatri",               monthlyDiary.get("md_increment_ovijatri"));
        contentvalues.put("md_increment_wellwisher",             monthlyDiary.get("md_increment_wellwisher"));
        contentvalues.put("md_increment_teacher_wellwisher",     monthlyDiary.get("md_increment_teacher_wellwisher"));
        contentvalues.put("md_increment_friend",                 monthlyDiary.get("md_increment_friend"));
        contentvalues.put("md_increment_talent_student",         monthlyDiary.get("md_increment_talent_student"));
        contentvalues.put("md_contribution_amount",              monthlyDiary.get("md_contribution_amount"));
        contentvalues.put("md_contribution_payment_date",        monthlyDiary.get("md_contribution_payment_date"));
        contentvalues.put("md_service_bank_amount",              monthlyDiary.get("md_service_bank_amount"));
        contentvalues.put("md_other_computer",                   monthlyDiary.get("md_other_computer"));
        contentvalues.put("md_other_language",                   monthlyDiary.get("md_other_language"));
        contentvalues.put("md_other_children_organization",      monthlyDiary.get("md_other_children_organization"));
        contentvalues.put("md_suggestion",                       monthlyDiary.get("md_suggestion"));

        return contentvalues;
    }


    public HashMap<String, String> getAllContentFromTableEtMR(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_2 + " WHERE md_month='"))
                .append(monthId).append("'").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();

            hashmap.put("md_quran_sura_name",             myCursor.getString(2));
            hashmap.put("md_quran_memorize_ayat",         myCursor.getString(3));
            hashmap.put("md_quran_alocona",               myCursor.getString(4));
            hashmap.put("md_hadith_book_name",            myCursor.getString(5));
            hashmap.put("md_hadith_memorize_hadis",       myCursor.getString(6));
            hashmap.put("md_hadith_alocona",              myCursor.getString(7));

            hashmap.put("md_literature_book_name",        myCursor.getString(8));
            hashmap.put("md_literature_book_note",        myCursor.getString(9));
            hashmap.put("md_namaz_nofol",                 myCursor.getString(10));
            hashmap.put("md_contact_ongkurito",           myCursor.getString(11));
            hashmap.put("md_contact_friend",              myCursor.getString(12));
            hashmap.put("md_service_work_total_day",      myCursor.getString(13));
            hashmap.put("md_service_work_average_time",   myCursor.getString(14));

            hashmap.put("md_distribution_fulkuri",        myCursor.getString(15));
            hashmap.put("md_distribution_poriciti",       myCursor.getString(16));
            hashmap.put("md_distribution_stikar",         myCursor.getString(17));
            hashmap.put("md_distribution_literature",     myCursor.getString(18));
            hashmap.put("md_distribution_amar_proitdin",  myCursor.getString(19));
            hashmap.put("md_distribution_gift",           myCursor.getString(20));
            hashmap.put("md_distribution_cd_dvd",         myCursor.getString(21));
            hashmap.put("md_distribution_other",          myCursor.getString(22));
            hashmap.put("md_increment_member",            myCursor.getString(23));
            hashmap.put("md_increment_coukos",            myCursor.getString(24));
            hashmap.put("md_increment_ogrropothik",       myCursor.getString(25));
            hashmap.put("md_increment_suvasito",          myCursor.getString(26));
            hashmap.put("md_increment_bikoshito",         myCursor.getString(27));
            hashmap.put("md_increment_dhiman",            myCursor.getString(28));
            hashmap.put("md_increment_ovijatri",          myCursor.getString(29));
            hashmap.put("md_increment_wellwisher",        myCursor.getString(30));
            hashmap.put("md_increment_teacher_wellwisher",   myCursor.getString(31));
            hashmap.put("md_increment_friend",            myCursor.getString(32));
            hashmap.put("md_increment_talent_student",    myCursor.getString(33));

            hashmap.put("md_contribution_amount",         myCursor.getString(34));
            hashmap.put("md_contribution_payment_date",   myCursor.getString(35));
            hashmap.put("md_service_bank_amount",         myCursor.getString(36));
            hashmap.put("md_other_computer",              myCursor.getString(37));
            hashmap.put("md_other_language",              myCursor.getString(38));
            hashmap.put("md_other_children_organization", myCursor.getString(39));
            hashmap.put("md_suggestion", myCursor.getString(40));

        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }


    public void updateRowInTableEtMR(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_2, settAllContentValuesForTableEtMR(hashmap),
                "md_month = ?", new String[]{ hashmap.get("md_month")});
        sqlitedatabase.close();
    }


    public void insertRowInTableEtOfMR(HashMap<String, String> hashmap) {

        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_2, null, settAllContentValuesForTableEtMR(hashmap));
        sqlitedatabase.close();
    }

    
    //table 3
    public ContentValues setAllContentValuesForTableMonthlyPlan(HashMap<String, String> monthlyPlan) {
        ContentValues contentvalues = new ContentValues();

        contentvalues.put(AllData.MP_Month_Id,                   monthlyPlan.get(AllData.MP_Month_Id));
        contentvalues.put(AllData.MP_Q_Sura_Name,                monthlyPlan.get(AllData.MP_Q_Sura_Name));
        contentvalues.put(AllData.MP_Q_Total_day,                monthlyPlan.get(AllData.MP_Q_Total_day));
        contentvalues.put(AllData.MP_Q_Average_Ayat,             monthlyPlan.get(AllData.MP_Q_Average_Ayat));
        contentvalues.put(AllData.MP_Q_Alocona,                  monthlyPlan.get(AllData.MP_Q_Alocona));
        contentvalues.put(AllData.MP_Q_Memorize_Ayat,            monthlyPlan.get(AllData.MP_Q_Memorize_Ayat));

        contentvalues.put(AllData.MP_H_Book_Name,                monthlyPlan.get(AllData.MP_H_Book_Name));
        contentvalues.put(AllData.MP_H_Total_day,                monthlyPlan.get(AllData.MP_H_Total_day));
        contentvalues.put(AllData.MP_H_Average_Hadis,            monthlyPlan.get(AllData.MP_H_Average_Hadis));
        contentvalues.put(AllData.MP_H_Alocona,                  monthlyPlan.get(AllData.MP_H_Alocona));
        contentvalues.put(AllData.MP_H_Memorize_Hadis,           monthlyPlan.get(AllData.MP_H_Memorize_Hadis));

        contentvalues.put(AllData.MP_L_Book_Name,                monthlyPlan.get(AllData.MP_L_Book_Name));
        contentvalues.put(AllData.MP_L_Total_page,               monthlyPlan.get(AllData.MP_L_Total_page));
        contentvalues.put(AllData.MP_L_Relioganl_Page,           monthlyPlan.get(AllData.MP_L_Relioganl_Page));
        contentvalues.put(AllData.MP_L_Other_Page,               monthlyPlan.get(AllData.MP_L_Other_Page));
        contentvalues.put(AllData.MP_L_Book_Note,                monthlyPlan.get(AllData.MP_L_Book_Note));

        contentvalues.put(AllData.MP_A_Total_Day,                monthlyPlan.get(AllData.MP_A_Total_Day));
        contentvalues.put(AllData.MP_A_Average_Hour,             monthlyPlan.get(AllData.MP_A_Average_Hour));
        contentvalues.put(AllData.MP_A_Total_Day_Class,          monthlyPlan.get(AllData.MP_A_Total_Day_Class));

        contentvalues.put(AllData.MP_N_Jamaat,                   monthlyPlan.get(AllData.MP_N_Jamaat));
        contentvalues.put(AllData.MP_N_Nofol,                    monthlyPlan.get(AllData.MP_N_Nofol));

        contentvalues.put(AllData.MP_F_C_Total_Day,              monthlyPlan.get(AllData.MP_F_C_Total_Day));
        contentvalues.put(AllData.MP_F_C_Total_Hour,             monthlyPlan.get(AllData.MP_F_C_Total_Hour));
        contentvalues.put(AllData.MP_F_O_Total_Day,              monthlyPlan.get(AllData.MP_F_O_Total_Day));
        contentvalues.put(AllData.MP_F_O_Average_Hour,           monthlyPlan.get(AllData.MP_F_O_Average_Hour));

        contentvalues.put(AllData.MP_C_Member,                   monthlyPlan.get(AllData.MP_C_Member));
        contentvalues.put(AllData.MP_C_Coukos,                   monthlyPlan.get(AllData.MP_C_Coukos));
        contentvalues.put(AllData.MP_C_Ogrropothik,              monthlyPlan.get(AllData.MP_C_Ogrropothik));
        contentvalues.put(AllData.MP_C_Ongkurito,                monthlyPlan.get(AllData.MP_C_Ongkurito));
        contentvalues.put(AllData.MP_C_Suvasito,                 monthlyPlan.get(AllData.MP_C_Suvasito));
        contentvalues.put(AllData.MP_C_Bikosito,                 monthlyPlan.get(AllData.MP_C_Bikosito));
        contentvalues.put(AllData.MP_C_Dhiman,                   monthlyPlan.get(AllData.MP_C_Dhiman));
        contentvalues.put(AllData.MP_C_Ovijatri,                 monthlyPlan.get(AllData.MP_C_Ovijatri));
        contentvalues.put(AllData.MP_C_Talent_Student,           monthlyPlan.get(AllData.MP_C_Talent_Student));
        contentvalues.put(AllData.MP_C_Friend,                   monthlyPlan.get(AllData.MP_C_Friend));
        contentvalues.put(AllData.MP_C_Teacher,                  monthlyPlan.get(AllData.MP_C_Teacher));
        contentvalues.put(AllData.MP_C_Guardian,                 monthlyPlan.get(AllData.MP_C_Guardian));
        contentvalues.put(AllData.MP_C_Adviser,                  monthlyPlan.get(AllData.MP_C_Adviser));
        contentvalues.put(AllData.MP_C_WellWisher,               monthlyPlan.get(AllData.MP_C_WellWisher));

        contentvalues.put(AllData.MP_SW_Total_Day,               monthlyPlan.get(AllData.MP_SW_Total_Day));
        contentvalues.put(AllData.MP_SW_Description,             monthlyPlan.get(AllData.MP_SW_Description));

        contentvalues.put(AllData.MP_D_Fulkuri,                  monthlyPlan.get(AllData.MP_D_Fulkuri));
        contentvalues.put(AllData.MP_D_Poriciti,                 monthlyPlan.get(AllData.MP_D_Poriciti));
        contentvalues.put(AllData.MP_D_Stikar,                   monthlyPlan.get(AllData.MP_D_Stikar));
        contentvalues.put(AllData.MP_D_Literature,               monthlyPlan.get(AllData.MP_D_Literature));
        contentvalues.put(AllData.MP_D_AmarProtidin,             monthlyPlan.get(AllData.MP_D_AmarProtidin));
        contentvalues.put(AllData.MP_D_Gift,                     monthlyPlan.get(AllData.MP_D_Gift));
        contentvalues.put(AllData.MP_D_Cd_Dvd,                   monthlyPlan.get(AllData.MP_D_Cd_Dvd));
        contentvalues.put(AllData.MP_D_Other,                    monthlyPlan.get(AllData.MP_D_Other));

        contentvalues.put(AllData.MP_I_Member,                   monthlyPlan.get(AllData.MP_I_Member));
        contentvalues.put(AllData.MP_I_Coukos,                   monthlyPlan.get(AllData.MP_I_Coukos));
        contentvalues.put(AllData.MP_I_Ogrropothik,              monthlyPlan.get(AllData.MP_I_Ogrropothik));
        contentvalues.put(AllData.MP_I_Suvasito,                 monthlyPlan.get(AllData.MP_I_Suvasito));
        contentvalues.put(AllData.MP_I_Bikosito,                 monthlyPlan.get(AllData.MP_I_Bikosito));
        contentvalues.put(AllData.MP_I_Dhiman,                   monthlyPlan.get(AllData.MP_I_Dhiman));
        contentvalues.put(AllData.MP_I_Ovijatri,                 monthlyPlan.get(AllData.MP_I_Ovijatri));
        contentvalues.put(AllData.MP_I_Wellwisher,               monthlyPlan.get(AllData.MP_I_Wellwisher));
        contentvalues.put(AllData.MP_I_Teacher_Wellwisher,       monthlyPlan.get(AllData.MP_I_Teacher_Wellwisher));
        contentvalues.put(AllData.MP_I_Friend,                   monthlyPlan.get(AllData.MP_I_Friend));
        contentvalues.put(AllData.MP_I_Talent_Student,           monthlyPlan.get(AllData.MP_I_Talent_Student));

        contentvalues.put(AllData.MP_CB_Amount,                  monthlyPlan.get(AllData.MP_CB_Amount));
        contentvalues.put(AllData.MP_CB_Increment,               monthlyPlan.get(AllData.MP_CB_Increment));
        contentvalues.put(AllData.MP_SB_Amount,                  monthlyPlan.get(AllData.MP_SB_Amount));

        contentvalues.put(AllData.MP_O_Criticism,                monthlyPlan.get(AllData.MP_O_Criticism));
        contentvalues.put(AllData.MP_O_Game,                     monthlyPlan.get(AllData.MP_O_Game));
        contentvalues.put(AllData.MP_O_Newspaper,                monthlyPlan.get(AllData.MP_O_Newspaper));
        contentvalues.put(AllData.MP_O_Computer,                 monthlyPlan.get(AllData.MP_O_Computer));
        contentvalues.put(AllData.MP_O_Language,                 monthlyPlan.get(AllData.MP_O_Language));
        contentvalues.put(AllData.MP_O_Children_Organization,    monthlyPlan.get(AllData.MP_O_Children_Organization));
        return contentvalues;
    }



    public HashMap<String, String> getAllContentFromTableMonthlyPlan(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        HashMap<String, String> hashmap = new HashMap();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT * FROM " + TABLE_3 + " WHERE month_id ='"))
                .append(monthId).append("'").toString(), null);

        if (myCursor != null && myCursor.getCount() > 0) {
            myCursor.moveToFirst();

            hashmap.put(AllData.MP_Month_Id,                    myCursor.getString(2));
            hashmap.put(AllData.MP_Q_Sura_Name,                 myCursor.getString(3));
            hashmap.put(AllData.MP_Q_Total_day,                 myCursor.getString(4));
            hashmap.put(AllData.MP_Q_Average_Ayat,              myCursor.getString(5));
            hashmap.put(AllData.MP_Q_Alocona,                   myCursor.getString(6));
            hashmap.put(AllData.MP_Q_Memorize_Ayat,             myCursor.getString(7));

            hashmap.put(AllData.MP_H_Book_Name,                 myCursor.getString(8));
            hashmap.put(AllData.MP_H_Total_day,                 myCursor.getString(9));
            hashmap.put(AllData.MP_H_Average_Hadis,             myCursor.getString(10));
            hashmap.put(AllData.MP_H_Alocona,                   myCursor.getString(11));
            hashmap.put(AllData.MP_H_Memorize_Hadis,            myCursor.getString(12));

            hashmap.put(AllData.MP_L_Book_Name,                 myCursor.getString(13));
            hashmap.put(AllData.MP_L_Total_page,                myCursor.getString(14));
            hashmap.put(AllData.MP_L_Relioganl_Page,            myCursor.getString(15));
            hashmap.put(AllData.MP_L_Other_Page,                myCursor.getString(16));
            hashmap.put(AllData.MP_L_Book_Note,                 myCursor.getString(17));

            hashmap.put(AllData.MP_A_Total_Day,                 myCursor.getString(18));
            hashmap.put(AllData.MP_A_Average_Hour,              myCursor.getString(19));
            hashmap.put(AllData.MP_A_Total_Day_Class,           myCursor.getString(20));

            hashmap.put(AllData.MP_N_Jamaat,                    myCursor.getString(21));
            hashmap.put(AllData.MP_N_Nofol,                     myCursor.getString(22));

            hashmap.put(AllData.MP_F_C_Total_Day,               myCursor.getString(23));
            hashmap.put(AllData.MP_F_C_Total_Hour,              myCursor.getString(24));
            hashmap.put(AllData.MP_F_O_Total_Day,               myCursor.getString(25));
            hashmap.put(AllData.MP_F_O_Average_Hour,            myCursor.getString(26));

            hashmap.put(AllData.MP_C_Member,                    myCursor.getString(27));
            hashmap.put(AllData.MP_C_Coukos,                    myCursor.getString(28));
            hashmap.put(AllData.MP_C_Ogrropothik,               myCursor.getString(29));
            hashmap.put(AllData.MP_C_Ongkurito,                 myCursor.getString(30));
            hashmap.put(AllData.MP_C_Suvasito,                  myCursor.getString(31));
            hashmap.put(AllData.MP_C_Bikosito,                  myCursor.getString(32));
            hashmap.put(AllData.MP_C_Dhiman,                    myCursor.getString(33));
            hashmap.put(AllData.MP_C_Ovijatri,                  myCursor.getString(34));
            hashmap.put(AllData.MP_C_Talent_Student,            myCursor.getString(35));
            hashmap.put(AllData.MP_C_Friend,                    myCursor.getString(36));
            hashmap.put(AllData.MP_C_Teacher,                   myCursor.getString(37));
            hashmap.put(AllData.MP_C_Guardian,                  myCursor.getString(38));
            hashmap.put(AllData.MP_C_Adviser,                   myCursor.getString(39));
            hashmap.put(AllData.MP_C_WellWisher,                myCursor.getString(40));

            hashmap.put(AllData.MP_SW_Total_Day,                myCursor.getString(41));
            hashmap.put(AllData.MP_SW_Description,              myCursor.getString(42));

            hashmap.put(AllData.MP_D_Fulkuri,                   myCursor.getString(43));
            hashmap.put(AllData.MP_D_Poriciti,                  myCursor.getString(44));
            hashmap.put(AllData.MP_D_Stikar,                    myCursor.getString(45));
            hashmap.put(AllData.MP_D_Literature,                myCursor.getString(46));
            hashmap.put(AllData.MP_D_AmarProtidin,              myCursor.getString(47));
            hashmap.put(AllData.MP_D_Gift,                      myCursor.getString(48));
            hashmap.put(AllData.MP_D_Cd_Dvd,                    myCursor.getString(49));
            hashmap.put(AllData.MP_D_Other,                     myCursor.getString(50));

            hashmap.put(AllData.MP_I_Member,                    myCursor.getString(51));
            hashmap.put(AllData.MP_I_Coukos,                    myCursor.getString(52));
            hashmap.put(AllData.MP_I_Ogrropothik,               myCursor.getString(53));
            hashmap.put(AllData.MP_I_Suvasito,                  myCursor.getString(54));
            hashmap.put(AllData.MP_I_Bikosito,                  myCursor.getString(55));
            hashmap.put(AllData.MP_I_Dhiman,                    myCursor.getString(56));
            hashmap.put(AllData.MP_I_Ovijatri,                  myCursor.getString(57));
            hashmap.put(AllData.MP_I_Wellwisher,                myCursor.getString(58));
            hashmap.put(AllData.MP_I_Teacher_Wellwisher,        myCursor.getString(59));
            hashmap.put(AllData.MP_I_Friend,                    myCursor.getString(60));
            hashmap.put(AllData.MP_I_Talent_Student,            myCursor.getString(61));

            hashmap.put(AllData.MP_CB_Amount,                   myCursor.getString(62));
            hashmap.put(AllData.MP_CB_Increment,                myCursor.getString(63));
            hashmap.put(AllData.MP_SB_Amount,                   myCursor.getString(64));

            hashmap.put(AllData.MP_O_Criticism,                 myCursor.getString(65));
            hashmap.put(AllData.MP_O_Game,                      myCursor.getString(66));
            hashmap.put(AllData.MP_O_Newspaper,                 myCursor.getString(67));
            hashmap.put(AllData.MP_O_Computer,                  myCursor.getString(68));
            hashmap.put(AllData.MP_O_Language,                  myCursor.getString(69));
            hashmap.put(AllData.MP_O_Children_Organization,     myCursor.getString(70));

        }
        myCursor.close();
        sqlitedatabase.close();
        return hashmap;
    }
    


    public void insertRowInTableMonthlyPlan(HashMap<String, String> hashmap) {

        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.insert(TABLE_3, null, setAllContentValuesForTableMonthlyPlan(hashmap));
        sqlitedatabase.close();
    }



    public void updateRowInTableMonthlyPlan(HashMap<String, String> hashmap) {
        SQLiteDatabase sqlitedatabase = getWritableDatabase();
        sqlitedatabase.update(TABLE_3, setAllContentValuesForTableMonthlyPlan(hashmap),
                "month_id = ?", new String[]{ hashmap.get(AllData.MP_Month_Id)});
        sqlitedatabase.close();
    }


    public int checkMonthlyPlanExistOrNot(String monthId) {
        SQLiteDatabase sqlitedatabase = getReadableDatabase();
        Cursor myCursor = sqlitedatabase.rawQuery((new StringBuilder("SELECT  keeping_plan FROM " +
                TABLE_3 + " WHERE month_id = '")).append(monthId).append("'").toString(), null);

        int i = 0;
        if (myCursor != null) {
            if (myCursor.getCount() > 0) {
                myCursor.moveToFirst();
                i = myCursor.getInt(0);
            }
        }
        myCursor.close();
        sqlitedatabase.close();
        return i;
    }
}
