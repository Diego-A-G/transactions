package com.example.transactions.persistence.daos;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.transactions.persistence.entities.AnnulmentEntity;
import java.lang.Class;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AnnulmentDao_Impl implements AnnulmentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AnnulmentEntity> __insertionAdapterOfAnnulmentEntity;

  public AnnulmentDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAnnulmentEntity = new EntityInsertionAdapter<AnnulmentEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `annulment` (`receiptId`,`rrn`,`statusCode`,`statusDescription`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AnnulmentEntity value) {
        if (value.getReceiptId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getReceiptId());
        }
        if (value.getRrn() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getRrn());
        }
        if (value.getStatusCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getStatusCode());
        }
        if (value.getStatusDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatusDescription());
        }
      }
    };
  }

  @Override
  public Object insertAnnulment(final AnnulmentEntity annulment,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAnnulments(final Continuation<? super List<AnnulmentEntity>> $completion) {
    final String _sql = "SELECT * FROM annulment";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    int _argIndex = 1;
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Object _result;
      if(_cursor.moveToFirst()) {
        _result = new Object();
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
