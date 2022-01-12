package com.example.wasteinput;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageGarbage {
    private DatabaseReference databaseReference;
    private String index_str;
    private String final_index;

    public ManageGarbage() {
        FirebaseDatabase db = FirebaseDatabase.getInstance("https://wasteinput-default-rtdb.europe-west1.firebasedatabase.app/");
        databaseReference = db.getReference(GarbageData.class.getSimpleName());

        index_str = "Garbage";

        Log.d("DEBG", String.valueOf(databaseReference));
    }

    public Task<Void> add (GarbageData gbd, String index) {
        if(gbd == null) try {
            throw new Exception("GarbageData is null");
        } catch (Exception e) {
            e.printStackTrace();
        }
        final_index = index_str + index;
        return databaseReference.child(final_index).setValue(gbd);
    }

    public void remove (String index) {
        final_index = index_str + index;
        DatabaseReference drGarbage = FirebaseDatabase.getInstance("https://wasteinput-default-rtdb.europe-west1.firebasedatabase.app/").getReference("GarbageData").child(final_index);

        drGarbage.removeValue();
    }
}
