package com.quangtd.hlovepuzzle.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import com.quangtd.hlovepuzzle.db.entity.ContentEntity;

import java.util.List;

@Dao
public interface ContentDao {

    @Query("select * from content")
    LiveData<List<ContentEntity>> getAll();
}
