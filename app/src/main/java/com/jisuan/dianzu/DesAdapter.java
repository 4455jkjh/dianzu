package com.jisuan.dianzu;
import java.util.List;
import android.widget.TextView;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Adapter;

public class DesAdapter extends ItemAdapter
{

	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method
		Name.DESCRIBE=list.get(p3).name;
		m.toolbar.setSubtitle(Name.DESCRIBE);
		m.nam.notifyDataSetChanged();
		m.edit.putInt(MainActivity.sp_name1,0);
		m.edit.putInt(MainActivity.sp_describe,p3);
		m.edit.commit();
	}

	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		// TODO: Implement this method
	}


	@Override
	public void setText(TextView tv, int index)
	{
		// TODO: Implement this method
		tv.setText(list.get(index).name);
	}
	@Override
	public void put(String des,String name, float value,int area)
	{
		for(Value va:list){
			if(va.name.equals(des))
				return;
		}
		Value va=new Value();
		va.name = des;
		list.add(va);
	}
	List<Value> list;

	@Override
	public void reset()
	{
		list.removeAll(list);
		Value v=new Value();
		v.name="选择线体";
		list.add(v);
	}

	
	@Override
	public List<Value> getList()
	{
		return list;
	}
	public DesAdapter(MainActivity m)
	{
		super(m);
		list = new ArrayList<Value>();
	}
	
}
