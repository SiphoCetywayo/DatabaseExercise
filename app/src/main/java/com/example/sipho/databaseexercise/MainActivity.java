package com.example.sipho.databaseexercise;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler myDb;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    EditText Name, Speed, Tank, findID, editTankSize;
    Button btnAddCar, View_all, Btn_Update, Btn_fuel, Btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHandler(this);
        Name = findViewById(R.id.carName);
        Speed = findViewById(R.id.editSpeed);
        Tank = findViewById(R.id.editTank);
        btnAddCar = findViewById(R.id.add_car);
        Btn_Update = findViewById(R.id.btnUpt);
        View_all = findViewById(R.id.btnViewAll);
        Btn_fuel = findViewById(R.id.BtnFuel);
        Btn_delete = findViewById(R.id.BtnDlt);
        editTankSize = findViewById(R.id.editTankSize);
        findID = findViewById(R.id.EdtID);
        AddData();
        viewAll();
        updateData();
        ViewCarsWithMoreTanks();
        DeleteData();
    }

    public void AddData() {

        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(Name.getText().toString().isEmpty() && Speed.getText().toString().isEmpty() && Tank.getText().toString().isEmpty())) {
                    boolean isInserted = myDb.insertData(Name.getText().toString(), Speed.getText().toString(), Tank.getText().toString());


                    if (isInserted == true) {
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                    }

                    Name.getText().clear();
                    Speed.getText().clear();
                    Tank.getText().clear();


                } else {

                    Toast.makeText(MainActivity.this, "No empty entires permitted", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void viewAll() {

        View_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor results = myDb.getAllData();

                if (results.getCount() == 0) {
                    // Show message
                    showMessage("Error", " No Data found");
                    return;

                }

                StringBuffer buffer = new StringBuffer();
                while (results.moveToNext()) {
                    buffer.append("Id :" + results.getString(0) + "\n");
                    buffer.append("Name :" + results.getString(1) + "\n");
                    buffer.append("Speed :" + results.getString(2) + "\n");
                    buffer.append("Tank :" + results.getString(3) + "\n\n");

                    //Show all database entries
                    showMessage("Data", buffer.toString());


                }
            }
        });
    }


    public void updateData() {

        Btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(findID.getText().toString().isEmpty() && Name.getText().toString().isEmpty() && Speed.getText().toString().isEmpty() && Tank.getText().toString().isEmpty())) {
                    boolean isUpdated = myDb.updateData(findID.getText().toString(), Name.getText().toString(), Speed.getText().toString(), Tank.getText().toString());

                    Name.getText().clear();
                    Speed.getText().clear();
                    Tank.getText().clear();
                    findID.getText().clear();

                    if (isUpdated == true) {
                        Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data not updated", Toast.LENGTH_LONG).show();

                    }
                } else {

                    Toast.makeText(MainActivity.this, "No empty entires permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ViewCarsWithMoreTanks() {

        Btn_fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseHandler = new DatabaseHandler(getApplication());
                SQLiteDatabase sqLiteDatabase = databaseHandler.getReadableDatabase();
                Cursor cursor = databaseHandler.getCarFuelSizes(editTankSize.getText().toString(), sqLiteDatabase);

                if (cursor.getCount() == 0) {
                    // Show message
                    showMessage("Error", " No Data found");
                    return;

                }

                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()) {

                    buffer.append("Id :" + cursor.getString(0) + "\n");
                    buffer.append("Name :" + cursor.getString(1) + "\n");
                    buffer.append("Speed :" + cursor.getString(2) + "\n");
                    buffer.append("Tank :" + cursor.getString(3) + "\n\n");
                }

                editTankSize.getText().clear();


                showMessage("Data", buffer.toString());
            }
        });
    }

    public void DeleteData() {
        Btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRow = myDb.deleteData(findID.getText().toString());
                if (!findID.getText().toString().isEmpty()) {
                    if (deletedRow > 0) {

                        Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();

                    }

                } else {
                    Toast.makeText(MainActivity.this, "No empty entires permitted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void showMessage(String title, String Message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }

}
