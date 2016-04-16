package com.jisuan.dianzu;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Adapter;

public class NamAdapter extends ItemAdapter
{

	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method
		Value value=getList().get(p3);
		Name.NAME=value.name;
		Name.setArea(value.area);
		Name.value=value.value;
		m.jizhun.setText(Name.value+"");
		m.setTitle(Name.NAME);
	}

	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		// TODO: Implement this method
	}

	Map<String,List<Value>> map_all;
	public NamAdapter(MainActivity m){
		super(m);
		map_all = new HashMap<String,List<Value>>();
	}
	@Override
	public List<Value> getList()
	{
		// TODO: Implement this method
		return map_all.get(Name.DESCRIBE);
	}

	@Override
	public void setText(TextView tv, int index)
	{
		tv.setText(getList().get(index).name);
	}

	@Override
	public void reset()
	{
		map_all.clear();
		List<Value>vl=new ArrayList<Value>();
		Value vv=new Value();
		vv.name="选择机种";
		vv.area=0;
		vv.value=0;
		vl.add(vv);
		map_all.put("选择线体",vl);
	}

	
	@Override
	public void put(String des,String name, float value,int area)
	{
		boolean b=map_all.containsKey(des);
		Value v=new Value();
		v.name=name;
		v.value=value;
		if(b)
			map_all.get(des).add(v);
		else{
			List<Value> vl=new ArrayList<Value>();
			Value vv=new Value();
			vv.name="选择机种";
			vv.area=0;
			vv.value=0;
			vl.add(vv);
			vl.add(v);
			map_all.put(des,vl);
		}
	}
	
}
