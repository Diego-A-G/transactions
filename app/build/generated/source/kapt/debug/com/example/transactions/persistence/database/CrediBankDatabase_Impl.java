package com.example.transactions.persistence.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.transactions.persistence.daos.AnnulmentDao;
import com.example.transactions.persistence.daos.AnnulmentDao_Impl;
import com.example.transactions.persistence.daos.AuthorizationDao;
import com.example.transactions.persistence.daos.AuthorizationDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CrediBankDatabase_Impl extends CrediBankDatabase {
  private volatile AuthorizationDao _authorizationDao;

  private volatile AnnulmentDao _annulmentDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `authorization` (`id` TEXT NOT NULL, `commerceCode` TEXT NOT NULL, `terminalCode` TEXT NOT NULL, `amount` TEXT NOT NULL, `card` TEXT NOT NULL, `receiptId` TEXT NOT NULL, `rrn` TEXT NOT NULL, `statusCode` TEXT NOT NULL, `statusDescription` TEXT NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `annulment` (`receiptId` TEXT NOT NULL, `rrn` TEXT NOT NULL, `statusCode` TEXT NOT NULL, `statusDescription` TEXT NOT NULL, PRIMARY KEY(`receiptId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '2f16f4983262f7729f58b2b5a295404c')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `authorization`");
        _db.execSQL("DROP TABLE IF EXISTS `annulment`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAuthorization = new HashMap<String, TableInfo.Column>(9);
        _columnsAuthorization.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("commerceCode", new TableInfo.Column("commerceCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("terminalCode", new TableInfo.Column("terminalCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("amount", new TableInfo.Column("amount", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("card", new TableInfo.Column("card", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("receiptId", new TableInfo.Column("receiptId", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("rrn", new TableInfo.Column("rrn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("statusCode", new TableInfo.Column("statusCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAuthorization.put("statusDescription", new TableInfo.Column("statusDescription", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAuthorization = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAuthorization = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAuthorization = new TableInfo("authorization", _columnsAuthorization, _foreignKeysAuthorization, _indicesAuthorization);
        final TableInfo _existingAuthorization = TableInfo.read(_db, "authorization");
        if (! _infoAuthorization.equals(_existingAuthorization)) {
          return new RoomOpenHelper.ValidationResult(false, "authorization(com.example.transactions.persistence.entities.AuthorizationEntity).\n"
                  + " Expected:\n" + _infoAuthorization + "\n"
                  + " Found:\n" + _existingAuthorization);
        }
        final HashMap<String, TableInfo.Column> _columnsAnnulment = new HashMap<String, TableInfo.Column>(4);
        _columnsAnnulment.put("receiptId", new TableInfo.Column("receiptId", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnulment.put("rrn", new TableInfo.Column("rrn", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnulment.put("statusCode", new TableInfo.Column("statusCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnnulment.put("statusDescription", new TableInfo.Column("statusDescription", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnnulment = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAnnulment = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAnnulment = new TableInfo("annulment", _columnsAnnulment, _foreignKeysAnnulment, _indicesAnnulment);
        final TableInfo _existingAnnulment = TableInfo.read(_db, "annulment");
        if (! _infoAnnulment.equals(_existingAnnulment)) {
          return new RoomOpenHelper.ValidationResult(false, "annulment(com.example.transactions.persistence.entities.AnnulmentEntity).\n"
                  + " Expected:\n" + _infoAnnulment + "\n"
                  + " Found:\n" + _existingAnnulment);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "2f16f4983262f7729f58b2b5a295404c", "aa9e8e919d3725213980e9216c4d7fc2");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "authorization","annulment");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `authorization`");
      _db.execSQL("DELETE FROM `annulment`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(AuthorizationDao.class, AuthorizationDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AnnulmentDao.class, AnnulmentDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public AuthorizationDao getAuthorizationDao() {
    if (_authorizationDao != null) {
      return _authorizationDao;
    } else {
      synchronized(this) {
        if(_authorizationDao == null) {
          _authorizationDao = new AuthorizationDao_Impl(this);
        }
        return _authorizationDao;
      }
    }
  }

  @Override
  public AnnulmentDao getAnnulmentDao() {
    if (_annulmentDao != null) {
      return _annulmentDao;
    } else {
      synchronized(this) {
        if(_annulmentDao == null) {
          _annulmentDao = new AnnulmentDao_Impl(this);
        }
        return _annulmentDao;
      }
    }
  }
}
