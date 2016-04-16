package com.jisuan.dianzu;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import android.database.Cursor;

public class Sqlite extends SQLiteOpenHelper
{
	public static final String FILENAME = "data.db";
	public static final int VERSION = 2;
	public static final String TABLE = "data";
	public static final String DESCRIBE ="describe";
	public static final String NAME = "name";
	public static final String VALUE = "value"; 
	public static final String AREA = "area";
	public SQLiteDatabase db;
	public Sqlite(Context c)
	{
		super(c, FILENAME, null, VERSION);
		if (db == null)
			db = getWritableDatabase();
	}
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		// TODO: Implement this method
		String CREATE = 
			String.format("create table %s(%s varchar(10),%s varcahr(10),%s integer(4,3),%s integet(1))",
						  TABLE, DESCRIBE, NAME, VALUE, AREA);
		p1.execSQL(CREATE);
		db = p1;
		for (Object[] o:Data.INV)
		{
			put(o);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
		p1.execSQL("drop table " + TABLE);
		onCreate(p1);
	}
	public void put(Object[] args)
	{
		String cmd=String.format("insert into %s  (%s,%s,%s,%s) values (?,?,?,?)",
								 TABLE, DESCRIBE, NAME, VALUE, AREA);
		db.execSQL(cmd , args);
	}
	public Cursor Query()
	{
		return getReadableDatabase().rawQuery("select * from " + TABLE, null);
	}
}
