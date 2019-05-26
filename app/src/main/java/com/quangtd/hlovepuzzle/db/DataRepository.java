package com.quangtd.hlovepuzzle.db;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
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

    private final AppDatabase mDatabase;
    private MediatorLiveData<List<ContentEntity>> mObservableProducts;

    private DataRepository(final AppDatabase database) {
        mDatabase = database;
    }

    public static DataRepository getInstance(final AppDatabase database) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database);
                }
            }
        }
        sInstance.getListContents();
        return sInstance;
    }

    /**
     * Get the list of products from the database and get notified when the data changes.
     */
//    public LiveData<List<ContentEntity>> getProducts() {
//        return mObservableProducts;
//    }
//
//    public LiveData<ContentEntity> loadProduct(final int productId) {
//        return mDatabase.productDao().loadProduct(productId);
//    }
//
//    public LiveData<List<CommentEntity>> loadComments(final int productId) {
//        return mDatabase.commentDao().loadComments(productId);
//    }
//
//    public LiveData<List<ProductEntity>> searchProducts(String query) {
//        return mDatabase.productDao().searchAllProducts(query);
//    }
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
