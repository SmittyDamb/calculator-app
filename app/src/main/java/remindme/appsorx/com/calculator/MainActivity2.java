package remindme.appsorx.com.calculator;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button btnsubmit, btncheck;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    Cursor cursor;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        radioGroup =(RadioGroup) findViewById(R.id.radioGroup);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        openHelper = new DatabaseHelper(this);
        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = radioButton.getText().toString();
                db=openHelper.getWritableDatabase();
                db=openHelper.getReadableDatabase();
                String qry = "SELECT " +DatabaseHelper.COL_ANS_2+ " FROM " +DatabaseHelper.TABLE_ANS+"";
                cursor=db.rawQuery(qry, null);
                if(cursor!=null){
                    if(cursor.getCount()>0){
                        cursor.moveToNext();
                    }
                }else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.COL_ANS_2, "LCD");
                    long i = db.insert(DatabaseHelper.TABLE_ANS, null, contentValues);
                }

                insertdata(text);
                cursor = db.rawQuery("SELECT " +DatabaseHelper.TABLE_ANS+ "."
                        +DatabaseHelper.COL_ANS_2+ "," +DatabaseHelper.TABLE_SA+ "."
                        +DatabaseHelper.COL_SA_2+ " FROM " +DatabaseHelper.TABLE_ANS+
                        " INNER JOIN (SELECT " +DatabaseHelper.COL_SA_2+ " FROM "
                        +DatabaseHelper.TABLE_SA+ " ORDER BY ID DESC LIMIT 1)"
                        +DatabaseHelper.TABLE_SA+ " ON " +DatabaseHelper.TABLE_SA+ "."
                        +DatabaseHelper.COL_SA_2+ "=" +DatabaseHelper.TABLE_ANS+ "."
                        +DatabaseHelper.COL_ANS_2+"", null);
                if(cursor!=null){
                    if(cursor.getCount()>0){
                        cursor.moveToNext();
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.COL_COM_2, "CORRECT");
                        long dd = db.insert(DatabaseHelper.TABLE_COM, null, contentValues);
                    }else{
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.COL_COM_2, "INCORRECT");
                        long dd = db.insert(DatabaseHelper.TABLE_COM, null, contentValues);
                    }
                }

                Toast.makeText(getApplicationContext(), "the answer is submitted successfully and the correct value is "+radioButton.getText(), Toast.LENGTH_LONG).show();

            }
        });

        btnNext = findViewById(R.id.btnnext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToActivity5();
            }

        });



    }

    private void moveToActivity5() {
        Intent intent = new Intent(MainActivity2.this, MainActivity5.class);
        startActivity(intent);

    }




    public void insertdata(String value){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_SA_2, value);
        long id = db.insert(DatabaseHelper.TABLE_SA, null, contentValues);
    }

}