package com.quangtd.hlovepuzzle.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import com.quangtd.hlovepuzzle.common.HLovePuzzleApp;
import com.quangtd.hlovepuzzle.db.DataRepository;
import com.quangtd.hlovepuzzle.db.entity.ContentEntity;

import java.util.List;

public class PuzzleViewModel extends AndroidViewModel {

    private final DataRepository mDataRepository;

    private final MediatorLiveData<List<ContentEntity>> mContentList;

    public PuzzleViewModel(@NonNull Application application) {
        super(application);
        HLovePuzzleApp app = (HLovePuzzleApp) application;
        mDataRepository = app.getRepository();
        LiveData<List<ContentEntity>> listLiveData = mDataRepository.getListContents();
        mContentList = new MediatorLiveData<>();
        mContentList.addSource(listLiveData, mContentList::setValue);
    }

    public LiveData<List<ContentEntity>> getListContent() {
        return mContentList;
    }
}
