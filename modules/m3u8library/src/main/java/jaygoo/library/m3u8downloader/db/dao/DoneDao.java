package jaygoo.library.m3u8downloader.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import jaygoo.library.m3u8downloader.db.table.M3u8DoneInfo;


/**
 * Created by HuangYong on 2018/6/27.
 */
@Dao
public interface DoneDao {

    @Insert
    void insert(M3u8DoneInfo doneInfo);

    @Delete
    void delete(M3u8DoneInfo doneInfo);

    @Update
    void update(M3u8DoneInfo doneInfo);

    @Query("SELECT * FROM T_M3U8_DONE")
    List<M3u8DoneInfo> getAll();

    @Query("SELECT * FROM T_M3U8_DONE WHERE task_id=:taskId ")
    List<M3u8DoneInfo> getById(String taskId);
}
