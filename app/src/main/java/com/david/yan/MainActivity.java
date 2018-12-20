package com.david.yan;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    DatabaseReference mydatabase;

    EditText inputName, inputAge, inputGender;
    TextView Name, Age, Gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mydatabase = FirebaseDatabase.getInstance().getReference("person");


                inputName = findViewById(R.id.inputName);
        inputAge = findViewById(R.id.inputAge);
        inputGender = findViewById(R.id.inputGender);

        Name = findViewById(R.id.aName);
        Age = findViewById(R.id.aAge);
        Gender = findViewById(R.id.aGender);
    }



    public void search(View v) {
        final String name = convertText(inputName).toLowerCase();

        ValueEventListener listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ss : dataSnapshot.getChildren()) {
                    Do p = ss.getValue(Do.class);
                    String person_name = p.getName().toLowerCase();

                    if (!person_name.equals(name))
                        continue;

                    else {
                        Name.setText(p.getName());
                        Age.setText(p.getAge());
                        Gender.setText(p.getGender());
                        return;
                    }
                }

                Name.setText("");
                Age.setText("");
                Gender.setText("");
                return;
            }

            @Override
            public void onCancelled(DatabaseError error) {}

        };
        mydatabase.addValueEventListener(listener);
    }


    public void store(View v){
        String name, age, gender, key;

        name = convertText(inputName);
        age = convertText(inputAge);
        gender = convertText(inputGender);

        key = mydatabase.push().getKey();


        mydatabase.child(key).setValue(new Do(name, age, gender));
    }

    protected String convertText(EditText tx){
        return tx.getText().toString();
    }


}
