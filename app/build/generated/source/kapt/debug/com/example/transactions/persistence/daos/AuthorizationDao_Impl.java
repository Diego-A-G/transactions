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
import com.example.transactions.persistence.entities.AuthorizationEntity;
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
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfAuthorizationEntity.insert(authorization);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAuthorizationById(final String authorizationId,
      final Continuation<? super AuthorizationEntity> continuation) {
    final String _sql = "SELECT * FROM authorization WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (authorizationId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, authorizationId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AuthorizationEntity>() {
      @Override
      public AuthorizationEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCommerceCode = CursorUtil.getColumnIndexOrThrow(_cursor, "commerceCode");
          final int _cursorIndexOfTerminalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "terminalCode");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCard = CursorUtil.getColumnIndexOrThrow(_cursor, "card");
          final int _cursorIndexOfReceiptId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptId");
          final int _cursorIndexOfRrn = CursorUtil.getColumnIndexOrThrow(_cursor, "rrn");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "statusDescription");
          final AuthorizationEntity _result;
          if(_cursor.moveToFirst()) {
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCommerceCode;
            if (_cursor.isNull(_cursorIndexOfCommerceCode)) {
              _tmpCommerceCode = null;
            } else {
              _tmpCommerceCode = _cursor.getString(_cursorIndexOfCommerceCode);
            }
            final String _tmpTerminalCode;
            if (_cursor.isNull(_cursorIndexOfTerminalCode)) {
              _tmpTerminalCode = null;
            } else {
              _tmpTerminalCode = _cursor.getString(_cursorIndexOfTerminalCode);
            }
            final String _tmpAmount;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmpAmount = null;
            } else {
              _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            }
            final String _tmpCard;
            if (_cursor.isNull(_cursorIndexOfCard)) {
              _tmpCard = null;
            } else {
              _tmpCard = _cursor.getString(_cursorIndexOfCard);
            }
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
            _result = new AuthorizationEntity(_tmpId,_tmpCommerceCode,_tmpTerminalCode,_tmpAmount,_tmpCard,_tmpReceiptId,_tmpRrn,_tmpStatusCode,_tmpStatusDescription);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, continuation);
  }

  @Override
  public Object getAllAuthorizations(
      final Continuation<? super List<AuthorizationEntity>> continuation) {
    final String _sql = "SELECT * FROM authorization";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AuthorizationEntity>>() {
      @Override
      public List<AuthorizationEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCommerceCode = CursorUtil.getColumnIndexOrThrow(_cursor, "commerceCode");
          final int _cursorIndexOfTerminalCode = CursorUtil.getColumnIndexOrThrow(_cursor, "terminalCode");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCard = CursorUtil.getColumnIndexOrThrow(_cursor, "card");
          final int _cursorIndexOfReceiptId = CursorUtil.getColumnIndexOrThrow(_cursor, "receiptId");
          final int _cursorIndexOfRrn = CursorUtil.getColumnIndexOrThrow(_cursor, "rrn");
          final int _cursorIndexOfStatusCode = CursorUtil.getColumnIndexOrThrow(_cursor, "statusCode");
          final int _cursorIndexOfStatusDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "statusDescription");
          final List<AuthorizationEntity> _result = new ArrayList<AuthorizationEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final AuthorizationEntity _item;
            final String _tmpId;
            if (_cursor.isNull(_cursorIndexOfId)) {
              _tmpId = null;
            } else {
              _tmpId = _cursor.getString(_cursorIndexOfId);
            }
            final String _tmpCommerceCode;
            if (_cursor.isNull(_cursorIndexOfCommerceCode)) {
              _tmpCommerceCode = null;
            } else {
              _tmpCommerceCode = _cursor.getString(_cursorIndexOfCommerceCode);
            }
            final String _tmpTerminalCode;
            if (_cursor.isNull(_cursorIndexOfTerminalCode)) {
              _tmpTerminalCode = null;
            } else {
              _tmpTerminalCode = _cursor.getString(_cursorIndexOfTerminalCode);
            }
            final String _tmpAmount;
            if (_cursor.isNull(_cursorIndexOfAmount)) {
              _tmpAmount = null;
            } else {
              _tmpAmount = _cursor.getString(_cursorIndexOfAmount);
            }
            final String _tmpCard;
            if (_cursor.isNull(_cursorIndexOfCard)) {
              _tmpCard = null;
            } else {
              _tmpCard = _cursor.getString(_cursorIndexOfCard);
            }
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
            _item = new AuthorizationEntity(_tmpId,_tmpCommerceCode,_tmpTerminalCode,_tmpAmount,_tmpCard,_tmpReceiptId,_tmpRrn,_tmpStatusCode,_tmpStatusDescription);
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
