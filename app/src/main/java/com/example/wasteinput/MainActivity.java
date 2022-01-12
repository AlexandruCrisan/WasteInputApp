package com.example.wasteinput;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.wasteinput.GarbageData;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    EditText txt_lat, txt_log, txt_index, txt_prate;
    Button btn;
    ListView listView;
    TextView text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();


        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.list_item, list) ;
        listView.setAdapter(adapter);

        DatabaseReference reference = FirebaseDatabase.getInstance("https://wasteinput-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("GarbageData");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    GarbageInformation garbageInformation = null;
                    try {
                        garbageInformation = snapshot.getValue(GarbageInformation.class);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        Log.d("DEBG", "GARBAGE INFO FAILED :" + String.valueOf(e));
                    }
                    String txt = "Garbage " + String.valueOf(garbageInformation.getIndex()) + " | Pollution Rate : " +String.valueOf(garbageInformation.getPollution_rate()) + " | " +  String.valueOf(garbageInformation.getLat())  + ", " + String.valueOf(garbageInformation.getLog()) ;

                    list.add(txt);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("DEBG", "ERROR " + String.valueOf(error));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView name = (TextView) view.findViewById((R.id.label));
                String index =  name.getText().toString() ;
                String[] words = index.split(" ");

                ManageGarbage manageGarbage = new ManageGarbage();
                manageGarbage.remove(words[1]);
            }
        });

    }

    public void button_pressed (View view) {

        if ( txt_lat.getText().toString().matches("") || txt_log.getText().toString().matches("")) {
            Toast.makeText(this, "You need to provide the full coordinates", Toast.LENGTH_SHORT).show();
        }
        else if (txt_prate.getText().toString().matches("")){
            Toast.makeText(this, "You need to provide the pollution rate", Toast.LENGTH_SHORT).show();
        }
        else if (txt_index.getText().toString().matches("")) {
            Toast.makeText(this, "You need to enter an index", Toast.LENGTH_SHORT).show();
        } else {
            ManageGarbage manageGarbage = new ManageGarbage();
            double db_lat = Double.parseDouble(txt_lat.getText().toString());
            double db_log = Double.parseDouble(txt_log.getText().toString());

            GarbageData garbageData = new GarbageData(db_lat, db_log, Integer.parseInt(txt_index.getText().toString()), Double.parseDouble(txt_prate.getText().toString()));

            manageGarbage.add(garbageData, txt_index.getText().toString()).addOnSuccessListener(suc->{
                Log.d("DEBG", "SUCCESS");
            }).addOnFailureListener(er->{
                Log.d("DEBG", ""+er.getMessage());
            });

        }

    }

    private void setup () {
        txt_lat = findViewById(R.id.latitude_txt);
        txt_log = findViewById(R.id.longitude_txt);
        txt_index = findViewById(R.id.index_txt);
        txt_prate = findViewById(R.id.pollution_rate_txt);

        listView = findViewById(R.id.listview);
        btn = findViewById(R.id.add_button);
    }
}