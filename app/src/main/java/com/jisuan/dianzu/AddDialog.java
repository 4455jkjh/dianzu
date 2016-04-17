package com.jisuan.dianzu;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import java.util.*;

import android.support.v7.widget.Toolbar;

public class AddDialog extends AppCompatDialog implements View.OnClickListener,CompoundButton. OnCheckedChangeListener
{
	Sqlite sqlite;
	ad da;
	private static final List<Value> a;
	AppCompatButton cancle,ok;
	Toolbar toolbar;
	EditText describe,name,value;
	CheckBox cb;
	int area_v;
	MainActivity m;
	Spinner des,area;
	public dd adapter;

	static{
		a = new ArrayList<Value>();
		Value v1,v2;
		v1 = new Value();
		v2 = new Value();
		v1.name = "-7%~7";
		v2.name = "-4%~10%";
		a.add(v1);
		a.add(v2);
	}
	@Override
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		describe.setEnabled(p2);
		des.setEnabled(!p2);
	}

	public AddDialog(MainActivity c, Sqlite sqlite)
	{
		super(c, R.style.Dialog);
		this.sqlite = sqlite;
		da = new ad(c);
		m = c;
		adapter = new dd(c);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//setTitle("对话框");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);
		toolbar = (Toolbar) findViewById(R.id.toolbar1);
		cancle = (AppCompatButton)  findViewById(R.id.cancle);
		ok = (AppCompatButton)findViewById(R.id.ok);
		describe = (EditText) findViewById(R.id.describe);
		name = (EditText) findViewById(R.id.name);
		value = (EditText) findViewById(R.id.value);
		cb = (CheckBox)findViewById(R.id.custom);
		des = (Spinner) findViewById(R.id.custom1);
		area = (Spinner) findViewById(R.id.area1);
		toolbar.setTitle("增加机种");
		cancle.setOnClickListener(this);
		ok.setOnClickListener(this);
		cb.setOnCheckedChangeListener(this);
		area.setAdapter(da);
		area.setOnItemSelectedListener(da);
		des.setAdapter(adapter);
		des.setOnItemSelectedListener(adapter);
	}

	@Override
	public void onClick(View p1)
	{
		switch (p1.getId())
		{
			case R.id.cancle:
				dismiss();
				break;
			case R.id.ok:
				String des=describe.getText().toString();
				String name=this.name.getText().toString();
				String value=this.value.getText().toString();
				put(des, name, value);
				break;
		}
	}
	public void put(String de, String na, String va)
	{
		if (de.equals("") || na.equals("") || va.equals(""))
		{
			m.vibrate(100);
			return;
		}
		Object[] objs={de,na,va,area_v};
		sqlite.put(objs);
		m.refresh();
		dismiss();
	}
	@Override
	public void show()
	{
		super.show();
		cb.setChecked(false);
		describe.setText("");
		describe.setEnabled(false);
		name.setText("");
		value.setText("");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		return super.onKeyDown(keyCode, event);
	}

	class ad extends ItemAdapter
	{
		public ad(MainActivity c)
		{
			super(c);
		}
		@Override
		public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
		{
			area_v = p3;
		}

		@Override
		public void onNothingSelected(AdapterView<?> p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void reset()
		{
			// TODO: Implement this method
		}

		@Override
		public List<Value> getList()
		{
			return a;
		}

		@Override
		public void setText(TextView tv, int index)
		{
			tv.setText(a.get(index).name);
		}

		@Override
		public void put(String des, String name, float value, int area)
		{
			// TODO: Implement this method
		}
	}
	class dd extends ItemAdapter
	{
		List<Value> vl;
		@Override
		public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
		{
			// TODO: Implement this method
			describe.setText(vl.get(p3).name);
		}

		@Override
		public void onNothingSelected(AdapterView<?> p1)
		{
			// TODO: Implement this method
		}

		@Override
		public void reset()
		{
			// TODO: Implement this method
			vl.removeAll(vl);
		}

		@Override
		public List<Value> getList()
		{
			// TODO: Implement this method
			return vl;
		}

		@Override
		public void setText(TextView tv, int index)
		{
			// TODO: Implement this method
			tv.setText(vl.get(index).name);
		}

		@Override
		public void put(String des, String name, float value, int area)
		{
			// TODO: Implement this method
			for (Value va:vl)
			{
				if (va.name.equals(des))
					return;
			}
			Value v=new Value();
			v.name = des;
			vl.add(v);
		}

		public dd(MainActivity m)
		{
			super(m);
			vl = new ArrayList<Value>();
		}
	}
}
