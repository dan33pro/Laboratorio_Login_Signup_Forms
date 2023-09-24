package unipiloto.edu.co;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import android.Manifest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class SignupFormActivity extends AppCompatActivity implements View.OnClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private DatabaseHelper myDB;
    private TextInputLayout editFullName, editUserName, editEmail, editPassword, editConfirmPassword;
    private EditText tvLatitud, tvLongitud, tvAltura, tvPrecision, editBornDate;
    RadioGroup radioGroupGender;
    private int gender;
    Spinner spinnerRol;
    private String rol;

    private Button btnAddData;
    private Button btnViewAll;

    private LocationManager locManager;
    private Location loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_form);

        EditText inputDate = findViewById(R.id.bornDate);
        inputDate.setOnClickListener(this);

        myDB = new DatabaseHelper(this);

        tvLatitud = (EditText) findViewById(R.id.tvLatitud);
        tvLongitud = (EditText) findViewById(R.id.tvLongitud);
        tvAltura = (EditText) findViewById(R.id.tvAltura);
        tvPrecision = (EditText) findViewById(R.id.tvPrecision);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            tvLatitud.setText("No se han definido los permisos necesarios.");
            tvLongitud.setText("");
            tvAltura.setText("");
            tvPrecision.setText("");

            return;
        } else {
            locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            tvLatitud.setText(String.valueOf(loc.getLatitude()));
            tvLongitud.setText(String.valueOf(loc.getLongitude()));
            tvAltura.setText(String.valueOf(loc.getAltitude()));
            tvPrecision.setText(String.valueOf(loc.getAccuracy()));

            editFullName = (TextInputLayout) findViewById(R.id.editText_full_name);
            editUserName = (TextInputLayout) findViewById(R.id.editText_username);
            editEmail = (TextInputLayout) findViewById(R.id.editText_email);
            editPassword = (TextInputLayout) findViewById(R.id.editText_password);
            editConfirmPassword = (TextInputLayout) findViewById(R.id.editText_password_check);
            editBornDate = (EditText) findViewById(R.id.bornDate);
            spinnerRol = (Spinner) findViewById(R.id.spinner_rol);
            radioGroupGender = (RadioGroup) findViewById(R.id.radioGroup_gender);


            btnAddData = (Button) findViewById(R.id.button_register);
            btnViewAll = (Button) findViewById(R.id.button_show_register);
            addData();
            viewAll();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bornDate) {
            showDatePickerDialog();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because january is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                EditText date = findViewById(R.id.bornDate);
                date.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public static class DatePickerFragment extends DialogFragment {

        private DatePickerDialog.OnDateSetListener listener;

        public static DatePickerFragment newInstance(DatePickerDialog.OnDateSetListener listener) {
            DatePickerFragment fragment = new DatePickerFragment();
            fragment.setListener(listener);
            return fragment;
        }

        public void setListener(DatePickerDialog.OnDateSetListener listener) {
            this.listener = listener;
        }

        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), listener, year, month, day);
        }

    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rol = String.valueOf(spinnerRol.getSelectedItem());

                        int radioButtonId = radioGroupGender.getCheckedRadioButtonId();
                        View radioButton = radioGroupGender.findViewById(radioButtonId);
                        int index = radioGroupGender.indexOfChild(radioButton);
                        RadioButton selectRadioButton = (RadioButton) radioGroupGender.getChildAt(index);
                        switch (selectRadioButton.getText().toString()) {
                            case "M":
                                gender = 1;
                                break;
                            case "F":
                                gender = 2;
                                break;
                            case "No B":
                                gender = 3;
                                break;
                            default:
                                gender = 0;
                                break;
                        }

                        String password = editPassword.getEditText().getText().toString();
                        String cPassword = editConfirmPassword.getEditText().getText().toString();

                        if (password.equals(cPassword)) {
                            String fName = editFullName.getEditText().getText().toString();
                            String uName = editUserName.getEditText().getText().toString();
                            String email = editEmail.getEditText().getText().toString();
                            String bornDate = editBornDate.getText().toString();
                            String latitude = tvLatitud.getText().toString();
                            String length = tvAltura.getText().toString();
                            String altitude = tvAltura.getText().toString();
                            String precision = tvPrecision.getText().toString();

                            User user = new User(fName, uName, email, password, bornDate, latitude, length, altitude, precision, rol, gender);
                            boolean isInserted = myDB.insertData(user);
                            if (isInserted)
                                Toast.makeText(SignupFormActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(SignupFormActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SignupFormActivity.this, "Your passwords aren't same", Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    public void viewAll() {
        btnViewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error", "Nothing found");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("ID: "+res.getString(0)+"\n");
                            buffer.append("Full Name: "+res.getString(1)+"\n");
                            buffer.append("User Name: "+res.getString(2)+"\n");
                            buffer.append("Email: "+res.getString(3)+"\n");
                            buffer.append("Password: "+res.getString(4)+"\n");
                            buffer.append("Born Date: "+res.getString(5)+"\n");
                            buffer.append("Latitude: "+res.getString(6)+"\n");
                            buffer.append("Length: "+res.getString(7)+"\n");
                            buffer.append("Altitude: "+res.getString(8)+"\n");
                            buffer.append("Precision: "+res.getString(9)+"\n");
                            buffer.append("Rol: "+res.getString(10)+"\n");
                            buffer.append("Gender: "+res.getString(11)+"\n\n");
                        }
                        showMessage("Data", buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}