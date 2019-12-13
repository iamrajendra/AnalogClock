package com.iamrajendra.analogclock.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.iamrajendra.analogclock.model.PeriodTableV2;
import com.iamrajendra.analogclock.utlis.Date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DatabaseManager{
    private FirebaseFirestore db;
    private String uid;
    private String TAG = DatabaseManager.class.getSimpleName();
    private List<PeriodTableV2> periodTableV2s=new ArrayList<>();
    public static  long starTime,endTime;
private MutableLiveData<List<PeriodTableV2>> pMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<PeriodTableV2>> getpMutableLiveData() {
        return pMutableLiveData;
    }

    public DatabaseManager(String uid) {
        this.uid = uid;
        db = FirebaseFirestore.getInstance();
        db.collection(uid).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                periodTableV2s.clear();
                for (QueryDocumentSnapshot doc : value) {
                    Log.e(TAG, "onEvent: "+value );
                    PeriodTableV2 periodTableV2=    doc.toObject(PeriodTableV2.class);
                    periodTableV2.setUid(doc.getId());
                    periodTableV2s.add(periodTableV2);
                }
                Collections.sort(periodTableV2s);
                pMutableLiveData.setValue(periodTableV2s);
                if (periodTableV2s.isEmpty()){
                    initTime();
                }else {
                    changeTime(periodTableV2s.get(periodTableV2s.size() - 1));
                    Log.e(TAG, "onEvent: "+periodTableV2s.size() );
                }
            }
        });



    }

    private void changeTime(PeriodTableV2 tableV2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(tableV2.getEndDate());
        calendar.add(Calendar.MINUTE,30);
        endTime = calendar.getTimeInMillis();
        starTime = tableV2.getEndDate();
        Log.e(TAG, "changeTime: start time"+ Date.getTime(Date.TIME,starTime));
        Log.e(TAG, "changeTime: end time"+ Date.getTime(Date.TIME,endTime));
    }

    private void initTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,10);
        calendar.set(Calendar.MINUTE,00);
       starTime = calendar.getTimeInMillis();
        calendar.set(Calendar.MINUTE,30);
        endTime = calendar.getTimeInMillis();

    }

    public void insert(PeriodTableV2 tableV2, OnSuccessListener<DocumentReference> success,OnFailureListener failure){

        db.collection(uid)
                .add(tableV2).addOnSuccessListener(success)
                .addOnFailureListener(failure);


    }


    public void populateSingleValue(String key,OnCompleteListener<DocumentSnapshot> onCompleteListener) {

        DocumentReference docRef = db.collection(uid).document(key);
        docRef.get().addOnCompleteListener(onCompleteListener);

    }

    public void update(PeriodTableV2 periodTableV2, EventListener<DocumentSnapshot> listener ) {
        DocumentReference docRef = db.collection(uid).document(periodTableV2.getUid());
        docRef.update(convertIntoHashMap(periodTableV2));

        docRef.addSnapshotListener(MetadataChanges.INCLUDE,listener);
    }

    private Map<String,Object> convertIntoHashMap(PeriodTableV2 periodTableV2) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(periodTableV2);// obj is your object
        Map<String,Object> result = new Gson().fromJson(json, Map.class);
        return result;
    }

    public void delete(List<PeriodTableV2> selectedList) {
        for (int i = 0; i < selectedList.size(); i++) {
            deleteSingle(selectedList.get(i).getUid());
        }
    }

    public void deleteSingle(String key) {
        db.collection(uid).document(key).delete();
    }
}
