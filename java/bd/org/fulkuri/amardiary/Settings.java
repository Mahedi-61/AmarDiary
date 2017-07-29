package bd.org.fulkuri.amardiary;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Calendar;

import bd.org.fulkuri.model.AlarmReceiver;
import bd.org.fulkuri.model.DatabaseHelper;

public class Settings extends ActionBarActivity implements View.OnClickListener {

    private LinearLayout lvProfile, lvReminder, lvPassword, lvMobileNo,
            lvExportDatabase, lvImportDatabase;

    private TextView tvMyName, tvReminderTime, tvExportDatabae, tvImportDatabase;
    private Toolbar mToolbar;
    private  AlarmReceiver alarm = new AlarmReceiver();
    private SharedPreferences profile;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.ac_settings);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");

        initialize();
    }


    private void initialize() {
        profile = getSharedPreferences("profile", 0);
        alarm = new AlarmReceiver();

        lvProfile = (LinearLayout) findViewById(R.id.lvMyProfileSettings);
        lvReminder = (LinearLayout) findViewById(R.id.lvReminderSettings);
        lvPassword = (LinearLayout) findViewById(R.id.lvChangePasswordSettings);
        lvMobileNo = (LinearLayout) findViewById(R.id.lvChangeMobileNoSettings);
        lvExportDatabase = (LinearLayout) findViewById(R.id.lvExportDatabaseSettings);
        lvImportDatabase = (LinearLayout) findViewById(R.id.lvImportDatabaseSettings);

        tvMyName = (TextView) findViewById(R.id.tvMyNameSettings);
        tvReminderTime = (TextView) findViewById(R.id.tvReminderStateSettings);
        tvExportDatabae = (TextView) findViewById(R.id.tvExportDatabaseStateSettings);
        tvImportDatabase = (TextView) findViewById(R.id.tvImportDatabaseStateSettings);

        tvMyName.setText(profile.getString("user_name", "your name"));
        tvReminderTime.setText(profile.getString("reminder_time", "no reminder is set"));
        tvExportDatabae.setText(profile.getString("export_database", "no database file is exported"));
        tvImportDatabase.setText(profile.getString("import_database", "no database file is imported"));


        lvProfile.setOnClickListener(this);
        lvReminder.setOnClickListener(this);
        lvPassword.setOnClickListener(this);
        lvMobileNo.setOnClickListener(this);
        lvExportDatabase.setOnClickListener(this);
        lvImportDatabase.setOnClickListener(this);
    }


    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.lvMyProfileSettings: {

                final Dialog dialog = new Dialog(this);
                dialog.setTitle("User Profile Settings");
                dialog.setContentView(R.layout.dl_profile_settings);

                final EditText userName = (EditText) dialog.findViewById(R.id.dl_etUserNameSettings);
                userName.setText(profile.getString("user_name", ""));
                final EditText userInstitute = (EditText) dialog.findViewById(R.id.dl_etAcademicInstituteSettings);
                userInstitute.setText(profile.getString("user_institute", ""));
                final EditText user_class = (EditText) dialog.findViewById(R.id.dl_etClassSettings);
                user_class.setText(profile.getString("user_class", ""));

                final EditText user_department = (EditText) dialog.findViewById(R.id.dl_etDepartmentSettings);
                user_department.setText(profile.getString("user_department", ""));
                final EditText user_year = (EditText) dialog.findViewById(R.id.dl_etYearSettings);
                user_year.setText(profile.getString("user_year", ""));
                final EditText user_responsibility = (EditText) dialog.findViewById(R.id.dl_etResponsibilitySettings);
                user_responsibility.setText(profile.getString("user_responsibility", ""));

                Button bCancel = (Button) dialog.findViewById(R.id.dl_bCancelProfileSettings);
                Button bSave = (Button) dialog.findViewById(R.id.dl_bSaveProfileSettings);

                bSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        SharedPreferences.Editor edit = profile.edit();
                        edit.putString("user_name", userName.getText().toString());
                        edit.putString("user_institute", userInstitute.getText().toString());
                        edit.putString("user_class", user_class.getText().toString());
                        edit.putString("user_department", user_department.getText().toString());
                        edit.putString("user_year", user_year.getText().toString());
                        edit.putString("user_responsibility", user_responsibility.getText().toString());

                        edit.commit();

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Your profile is saved successfully !",
                                Toast.LENGTH_SHORT).show();
                        tvMyName.setText(profile.getString("user_name", ""));
                    }
                });

                bCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }

            case R.id.lvReminderSettings: {
                int state = profile.getInt("reminder_state", 0);
                SharedPreferences.Editor edit = profile.edit();

                if (state == 0) {
                    openTimePickerDialog(false);

                } else if (state == 1) {
                    tvReminderTime.setText("no reminder is set");
                    edit.putInt("reminder_state", 0);
                    edit.putString("reminder_time", "no reminder is set");
                    alarm.cancelAlarm(this);
                }

                edit.commit();
                break;
            }

            case R.id.lvChangePasswordSettings: {

                final Dialog dialog = new Dialog(this);
                dialog.setTitle("Password Settings");
                dialog.setContentView(R.layout.dl_password_settings);
                final EditText etOldPassword = (EditText) dialog.findViewById(R.id.dl_etOldPasswordSettings);
                final EditText etNewPassword = (EditText) dialog.findViewById(R.id.dl_etNewPasswordSettings);
                final EditText etRetypePassword = (EditText) dialog.findViewById(R.id.dl_etRetypePasswordSettings);
                Button bCancel = (Button) dialog.findViewById(R.id.dl_bCancelPasswordSettings);
                Button bSave = (Button) dialog.findViewById(R.id.dl_bSavePasswordSettings);

                bSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        String old_login_password = profile.getString("login_password", "");

                        if (!etOldPassword.getText().toString().trim().equals(old_login_password)) {
                            Toast.makeText(getApplicationContext(), "Please enter your correct password", Toast.LENGTH_SHORT).show();

                        } else if (etNewPassword.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(), "Please enter your new password", Toast.LENGTH_SHORT).show();

                        } else if (etRetypePassword.getText().toString().trim().equals("")) {
                            Toast.makeText(getApplicationContext(), "Please re-enter your new password", Toast.LENGTH_SHORT).show();

                        } else if (!etNewPassword.getText().toString().equals(etRetypePassword.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "New Password doesn't match. Try again !!", Toast.LENGTH_SHORT).show();

                        } else {
                            SharedPreferences.Editor edit = profile.edit();
                            edit.putString("login_password", etNewPassword.getText().toString());
                            edit.commit();

                            dialog.dismiss();
                            Toast.makeText(getApplicationContext(), "New password is saved successfully !", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                bCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }

            case R.id.lvChangeMobileNoSettings: {
                final Dialog dialog = new Dialog(this);
                dialog.setTitle("Mobile No Settings");
                dialog.setContentView(R.layout.dl_mobile_no_settings);
                final EditText mobileNumber = (EditText) dialog.findViewById(R.id.dl_etmobileNumberSettings);
                mobileNumber.setText(profile.getString("mobile_number", ""));

                Button bCancel = (Button) dialog.findViewById(R.id.dl_bCancelMobileNoSettings);
                Button bSave = (Button) dialog.findViewById(R.id.dl_bSaveMobileNoSettings);

                bSave.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        SharedPreferences.Editor edit = profile.edit();
                        edit.putString("mobile_number", mobileNumber.getText().toString());
                        edit.commit();

                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Your Mobile Number is changed successfully !", Toast.LENGTH_SHORT).show();
                    }
                });

                bCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            }

            case R.id.lvExportDatabaseSettings:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Export Databas file");
                builder.setMessage("Are you sure to export your private databse file into local sd card ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int j) {
                        SharedPreferences.Editor editor = profile.edit();
                        editor.putString("export_database", (new StringBuilder("last export date:  "))
                                .append(DateFormat.format("EE, dd MMMM yyyy", Calendar.getInstance())).toString());

                        editor.commit();
                        if (exportDatabase()) {
                            tvExportDatabae.setText(profile.getString("export_database", "No database file is exported"));
                        }
                        dialoginterface.dismiss();
                    }
                });

                builder.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                });
                builder.show();
                break;
            }

            case R.id.lvImportDatabaseSettings:{
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Import Databas file");
                builder.setMessage("Are you sure to import your private databse file from local sd card ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialoginterface, int j) {
                        SharedPreferences.Editor editor = profile.edit();
                        editor.putString("import_database", (new StringBuilder("last export date:  "))
                                .append(DateFormat.format("EE, dd MMMM yyyy", Calendar.getInstance())).toString());

                        editor.commit();
                        if (importDatabase()) {
                            tvExportDatabae.setText(profile.getString("import_database", "No database file is imported"));
                        }
                        dialoginterface.dismiss();
                    }
                });

                builder.setNegativeButton("No", new android.content.DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {
                    }
                });
                builder.show();
                break;
            }
        } //end of switch case
    } //end of onClick method


    private void openTimePickerDialog(boolean flag) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), flag);

        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.setMessage("This alarm works only when you forget to keep your diary before your alarm time !!");
        timePickerDialog.show();
        timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            public void onCancel(DialogInterface dialoginterface) {
                dialoginterface.dismiss();
            }

        });
    }

    
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minitue) {
            Calendar thisTime = Calendar.getInstance();
            Calendar setTime = (Calendar) thisTime.clone();

            setTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            setTime.set(Calendar.MINUTE, minitue);
            setTime.set(Calendar.SECOND, 0);
            setTime.set(Calendar.MILLISECOND, 0);

            if (setTime.compareTo(thisTime) <= 0) {
                setTime.add(Calendar.DAY_OF_MONTH, 1);
            }

            setMyAlarm(setTime);
        }
    };

    
    private void setMyAlarm(Calendar calendar) {
        String reminderTime = (new StringBuilder("everyday :  "))
                .append(DateFormat.format("hh:mm a", calendar).toString()).toString();
        SharedPreferences.Editor editor = profile.edit();
        editor.putString("reminder_time", reminderTime);
        editor.putInt("hour", calendar.get(Calendar.HOUR_OF_DAY));
        editor.putInt("minute", calendar.get(Calendar.MINUTE));

        editor.putInt("reminder_state", 1);
        editor.commit();

        tvReminderTime.setText(profile.getString("reminder_time", ""));
        alarm.setAlarm(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public boolean exportDatabase() {

        try {
            File sdState = Environment.getExternalStorageDirectory();

            if (sdState.canWrite()) {

                File currentDb = new File(getDatabasePath(DatabaseHelper.DB_NAME).toString());
                File backDB = new File(sdState, DatabaseHelper.DB_NAME);

                FileChannel src = (new FileInputStream(currentDb)).getChannel();
                FileChannel dst = (new FileOutputStream(backDB)).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();

                Toast.makeText(this, "Database file exported successfully", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {
            Toast.makeText(this, "No sdcard is mounted on your phone", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public boolean importDatabase() {

        try {
            File sdState = Environment.getExternalStorageDirectory();

            if (sdState.canWrite()) {

                File oldDb = new File(getDatabasePath(DatabaseHelper.DB_NAME).toString());
                File newDB = new File(sdState, DatabaseHelper.DB_NAME);

                FileChannel src = (new FileInputStream(newDB)).getChannel();
                FileChannel dst = (new FileOutputStream(oldDb)).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                (new DatabaseHelper(this)).getWritableDatabase().close();

                Toast.makeText(this, "Database file imported successfully", Toast.LENGTH_SHORT).show();
                return true;
            }

        } catch (Exception e) {
            Toast.makeText(this, "No database file is found", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}

