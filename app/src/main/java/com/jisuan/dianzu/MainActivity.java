package com.jisuan.dianzu;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.BaseAdapter;
import android.text.TextWatcher;
import android.text.Editable;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.os.Vibrator;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener
{


	public Toolbar toolbar;
	public EditText dianzu,wendu,jizhun;
	private TextView bijiaozhi,wucha,hint;
	private Spinner describe,name;


	private static final String fmt_bjz="比较值：%.5f ";
	private static final String fmt_wc= "误  差：%.5f%%";
	private static final String sp_name="com.jisuan.dianzu";
	private static final String sp_dianzu="dianzu";
	private static final String sp_wendu="wendu";
	private static final String sp_jizhun="jizhun";
	public static final String sp_describe="describe";
	public static final String sp_name1="name";
	private SharedPreferences spr;
	public SharedPreferences.Editor edit;

	Button qkqb,qkdz,qkwd,qkjz;
	Vibrator vib;
	Sqlite sqlite;
	Cursor cursor;
	ItemAdapter des,nam;
	AddDialog add;
	public void vibrate(long l)
	{
		if (vib == null)
			vib = (Vibrator)getSystemService(VIBRATOR_SERVICE);
		vib.vibrate(l);
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if (Build.VERSION.SDK_INT == 19)
		{
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			findViewById(R.id.status).setVisibility(View.VISIBLE);
		}
		spr = getSharedPreferences(sp_name, MODE_PRIVATE);
		edit = spr.edit();
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		Name.DESCRIBE = "变频";
		initView();
		sqlite = new Sqlite(this);
		add = new AddDialog(this, sqlite);
		cursor = sqlite.Query();
		Value.d = cursor.getColumnIndex(Sqlite.DESCRIBE);
		Value.n = cursor.getColumnIndex(Sqlite.NAME);
		Value.v = cursor.getColumnIndex(Sqlite.VALUE);
		Value.a = cursor.getColumnIndex(Sqlite.AREA);
		refresh();
		describe.setAdapter(des);
		name.setAdapter(nam);
		dianzu.setText(spr.getString(sp_dianzu, ""));
		wendu.setText(spr.getString(sp_wendu, ""));
		jizhun.setText(spr.getString(sp_jizhun, jizhun.getText().toString()));
		describe.setSelection(spr.getInt(sp_describe,0));
		name.setSelection(spr.getInt(sp_name1,0));
	}
	public void refresh()
	{
		des.reset();
		nam.reset();
		add.adapter.reset();
		for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
		{
			String des=cursor.getString(Value.d);
			String na=cursor.getString(Value.n);
			float val=cursor.getFloat(Value.v);
			int area=cursor.getInt(Value.a);
			this.des.put(des, na, val, area);
			nam.put(des, na, val, area);
			add.adapter.put(des, na, val, area);
		}
		nam.notifyDataSetChanged();
		des.notifyDataSetChanged();
		add.adapter.notifyDataSetChanged();
		//new AddDialog(this).show();
    }
	private  void initView()
	{
		dianzu = fev(R.id.dianzu);
		wendu  = fev(R.id.wendu);
		jizhun = fev(R.id.jizhun);
		bijiaozhi = ftv(R.id.bijiaozhi);
		wucha = ftv(R.id.wucha);
		hint = ftv(R.id.hint);
		describe = fsv(R.id.describe);
		name = fsv(R.id.name);
		dianzu.addTextChangedListener(this);
		wendu.addTextChangedListener(this);
		jizhun.addTextChangedListener(this);
		des = new DesAdapter(this);
		describe.setOnItemSelectedListener(des);
		nam = new NamAdapter(this);
		name.setOnItemSelectedListener(nam);
	}

	private EditText fev(int id)
	{
		return (EditText)findViewById(id);
	}
	private TextView ftv(int id)
	{
		return (TextView)findViewById(id);
	}
	private Spinner fsv(int id)
	{
		return (Spinner)findViewById(id);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// TODO: Implement this method
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	public void calculate()
	{
		float dz,wd,jz;
		try
		{
			dz = Float.parseFloat(dianzu.getText().toString());
			wd = Float.parseFloat(wendu. getText().toString());
			jz = Float.parseFloat(jizhun.getText().toString());
		}
		catch (NumberFormatException e)
		{
			return;
		}
		float bjz=dz * 309.5f / (234.5f + wd);
		float wc=(bjz / jz - 1) * 100;
		bijiaozhi.setText(String.format(fmt_bjz, bjz));
		wucha.setText(String.format(fmt_wc, wc));
		String hi;
		if (wc > Name.max)
			hi = "电阻大了";
		else if (wc < Name.min)
			hi = "电阻小了";
		else
			hi = "";
		hint.setText("");
		hint.setText(hi);
		put(sp_dianzu, dz);
		put(sp_wendu, wd);
		put(sp_jizhun, jz);
		edit.commit();
	}
	private void put(String n, float f)
	{
		edit.putString(n, f + "");
	}
	@Override
	public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
	}

	@Override
	public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
	{
		// TODO: Implement this method
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		switch (p1.getId())
		{
			case R.id.qkdz:
				qkdz();
				break;
			case R.id.qkjz:
				qkjz();
				break;
			case R.id.qkwd:
				qkwd();
				break;
			case R.id.qkqb:
				qkdz();
				qkwd();
				qkjz();
				bijiaozhi.setText("");
				wucha.setText("");
				hint.setText("");
				break;
		}
	}
	public void qkdz()
	{
		dianzu.setText("");
	}
	public void qkwd()
	{
		wendu.setText("");
	}
	public void qkjz()
	{
		jizhun.setText("");
	}
	@Override
	public void afterTextChanged(Editable p1)
	{
		// TODO: Implement this method
		calculate();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
			case R.id.add:
				add.show();
				return true;
			case R.id.exit:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
