package com.ytinrete.multiproccess;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.AbstractCursor;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ytinrete.tools.l;

/**
 *
 */

public class MtpMasterProvider extends ContentProvider {


  private final static String AUTHORITY = "com.ytinrete.content";
  private final static String MP_INFO_1 = "info1";
  private final static int CODE_MP_INFO_1 = 1;
  private final static String MP_INFO_2 = "info2";
  private final static int CODE_MP_INFO_2 = 2;
  private UriMatcher uriMatcher;

  @Override
  public boolean onCreate() {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(AUTHORITY, MP_INFO_1, CODE_MP_INFO_1);
    uriMatcher.addURI(AUTHORITY, MP_INFO_2, CODE_MP_INFO_2);
    return false;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    if (uriMatcher.match(uri) == CODE_MP_INFO_1) {
      return new InfoCursor();
    } else if (uriMatcher.match(uri) == CODE_MP_INFO_2) {
      return new InfoCursor();
    }
    return null;
  }

  private class InfoCursor extends AbstractCursor {

    private String colNames[] = new String[]{"int", "str"};

    @Override
    public int getCount() {
      l.md("getCount:");
      return 1;
    }

    @Override
    public String[] getColumnNames() {
      l.md("getColumnNames:");
      return colNames;
    }

    @Override
    public String getString(int column) {
      l.md("getString:" + column);
      if (column >= 0 && column < colNames.length) {
        if ("str".equals(colNames[column])) {
          return SingletonTest.getInstance().getStr();
        }
      }
      return null;
    }

    @Override
    public short getShort(int column) {
      l.md("getShort:");
      return 0;
    }

    @Override
    public int getInt(int column) {
      l.md("getInt:" + column);
      if (column >= 0 && column < colNames.length) {
        if ("int".equals(colNames[column])) {
          return SingletonTest.getInstance().getAnInt();
        }
      }
      return 0;
    }

    @Override
    public long getLong(int column) {
      l.md("getLong:");
      return 0;
    }

    @Override
    public float getFloat(int column) {
      l.md("getFloat:");
      return 0;
    }

    @Override
    public double getDouble(int column) {
      l.md("getDouble:");
      return 0;
    }

    @Override
    public boolean isNull(int column) {
      l.md("isNull:");
      return false;
    }
  }


  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    return null;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    return null;
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    return 0;
  }
}
