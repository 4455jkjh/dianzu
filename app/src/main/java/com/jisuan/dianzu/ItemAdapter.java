package com.jisuan.dianzu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

public abstract class ItemAdapter extends BaseAdapter implements AdapterView.OnItemSelectedListener
{
	public abstract void reset();
	public abstract List<Value> getList();
	public abstract void setText(TextView tv, int index);
	public abstract  void put(String des, String name, float value,int area);
	public MainActivity m;
	private String des;
	public ItemAdapter(MainActivity m)
	{
		this.m = m;
		des = "";
	}
	@Override
	public int getCount()
	{
		// TODO: Implement this method
		return getList().size();
	}

	@Override
	public Object getItem(int p1)
	{
		// TODO: Implement this method
		return getList().get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		// TODO: Implement this method
		return getItem(p1).hashCode();
	}

	@Override
	public View getView(int p1, View v, ViewGroup p3)
	{
		// TODO: Implement this method
		if (v == null)
			v = m.getLayoutInflater().inflate(R.layout.adapter, p3, false);
		setText((TextView)v.findViewById(R.id.name), p1);
		return v;
	}

}
