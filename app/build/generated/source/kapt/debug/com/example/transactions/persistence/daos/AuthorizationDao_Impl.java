package com.example.transactions.persistence.daos;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.transactions.persistence.entities.AuthorizationEntity;
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
public final class AuthorizationDao_Impl implements AuthorizationDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AuthorizationEntity> __insertionAdapterOfAuthorizationEntity;

  public AuthorizationDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAuthorizationEntity = new EntityInsertionAdapter<AuthorizationEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `authorization` (`id`,`commerceCode`,`terminalCode`,`amount`,`card`,`receiptId`,`rrn`,`statusCode`,`statusDescription`) VALUES (?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, AuthorizationEntity value) {
        if (value.getId() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getId());
        }
        if (value.getCommerceCode() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCommerceCode());
        }
        if (value.getTerminalCode() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTerminalCode());
        }
        if (value.getAmount() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAmount());
        }
        if (value.getCard() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getCard());
        }
        if (value.getReceiptId() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getReceiptId());
        }
        if (value.getRrn() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getRrn());
        }
        if (value.getStatusCode() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getStatusCode());
        }
        if (value.getStatusDescription() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getStatusDescription());
        }
      }
    };
  }

  @Override
  public Object insertAuthorization(final AuthorizationEntity authorization,
      final Continuation<? super Unit> $completion) {
    __db.assertNotSuspendingTransaction();
  }

  @Override
  public Object getAuthorizationById(final String authorizationId,
      final Continuation<? super AuthorizationEntity> $completion) {
    final String _sql = "SELECT * FROM authorization WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (authorizationId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, authorizationId);
    }
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

  @Override
  public Object getAllAuthorizations(
      final Continuation<? super List<AuthorizationEntity>> $completion) {
    final String _sql = "SELECT * FROM authorization";
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
