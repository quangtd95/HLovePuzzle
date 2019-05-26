package com.quangtd.hlovepuzzle.db;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.*;
import com.quangtd.hlovepuzzle.db.entity.ContentEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository handling the work with products and comments.
 */
public class DataRepository {

    private static DataRepository sInstance;

    private DataRepository() {
    }

    public static DataRepository getInstance() {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository();
                }
            }
        }
        sInstance.getListContents();
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */

    public LiveData<List<ContentEntity>> getListContents() {
        MutableLiveData<List<ContentEntity>> listMutableLiveData = new MutableLiveData<>();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("content");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ContentEntity> contentEntities = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    contentEntities.add(ds.getValue(ContentEntity.class));
                }
                listMutableLiveData.setValue(contentEntities);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAGG", databaseError.toString());
            }
        });
        return listMutableLiveData;
    }
}
