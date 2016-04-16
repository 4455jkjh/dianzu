package com.jisuan.dianzu;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.TextView;
import java.util.List;
import android.widget.AdapterView;
import android.widget.Adapter;
import java.util.ArrayList;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.view.KeyEvent;

public class AddDialog extends AppCompatDialog implements View.OnClickListener,CompoundButton. OnCheckedChangeListener
{

	@Override
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		describe.setEnabled(p2);
	}

	Sqlite sqlite;
	ad da;
	public AddDialog(MainActivity c, Sqlite sqlite)
	{
		super(c, R.style.Dialog);
		this.sqlite = sqlite;
		da = new ad(c);
		m=c;
	}
	private static final List<Value> a;
	Button cancle,ok;
	Toolbar toolbar;
	EditText describe,name,value;
	CheckBox cb;
	int area_v;
	MainActivity m;
	Spinner des,area;
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
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		//setTitle("对话框");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);
		toolbar = (Toolbar) findViewById(R.id.toolbar1);
		cancle = (Button) findViewById(R.id.cancle);
		ok = (Button)findViewById(R.id.ok);
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
				String des=cb.isChecked() ? describe.getText().toString() : "";
				String name=this.name.getText().toString();
				String value=this.value.getText().toString();
				put(des,name,value);
				break;
		}
	}
	public void put(String de,String na,String va){
		if(de.equals("")||na.equals("")||va.equals("")){
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
}
