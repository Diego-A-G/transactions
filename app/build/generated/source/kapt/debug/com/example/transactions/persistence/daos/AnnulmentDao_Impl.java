package com.example.transactions.persistence.daos;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.transactions.persistence.entities.AnnulmentEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
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
  public Object insertAnnulment(final AnnulmentEntity annul,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAnnulmentEntity.insert(annul);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAnnulments(final Continuation<? super List<AnnulmentEntity>> continuation) {
    final String _sql = "SELECT * FROM annulment";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AnnulmentEntity>>() {
      @Override
      public List<AnnulmentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfReceiptId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptId");
          final int _cursorIndexOfRrn = CursorUtil.getColumnIndexOrThrow(_cursor, "rrn");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "statusDescription");
          final List<AnnulmentEntity> _result = new ArrayList<AnnulmentEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AnnulmentEntity _item;
            final String _tmpReceiptId;
            if (_cursor.isNull(_cursorIndexOfReceiptId)) {
              _tmpReceiptId = null;
            } else {
              _tmpReceiptId = _cursor.getString(_cursorIndexOfReceiptId);
            }
            final String _tmpRrn;
            if (_cursor.isNull(_cursorIndexOfRrn)) {
              _tmpRrn = null;
            } else {
              _tmpRrn = _cursor.getString(_cursorIndexOfRrn);
            }
            final String _tmpStatusCode;
            if (_cursor.isNull(_cursorIndexOfStatusCode)) {
              _tmpStatusCode = null;
            } else {
              _tmpStatusCode = _cursor.getString(_cursorIndexOfStatusCode);
            }
            final String _tmpStatusDescription;
            if (_cursor.isNull(_cursorIndexOfStatusDescription)) {
              _tmpStatusDescription = null;
            } else {
              _tmpStatusDescription = _cursor.getString(_cursorIndexOfStatusDescription);
            }
            _item = new AnnulmentEntity(_tmpReceiptId,_tmpRrn,_tmpStatusCode,_tmpStatusDescription);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
