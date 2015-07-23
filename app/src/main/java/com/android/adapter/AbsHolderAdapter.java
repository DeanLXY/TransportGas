package com.android.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.annotation.ContentView;
import com.android.annotation.Viewholder;
import com.android.annotation.util.ViewFinder;
import com.android.annotation.util.ViewFinderUtils;

//@ContentView(resourseid)
//@Viewholder(clsss)
public abstract class AbsHolderAdapter<T> extends BaseAdapter {
	private Context context;
	private List<T> dataList;
	private LayoutInflater mInflater;

	public AbsHolderAdapter(Context context) {
		this.context = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<T> dataList) {
		if (this.dataList == null) {
			this.dataList = new ArrayList<T>();
			if(dataList != null){
				this.dataList.addAll(dataList);
			}
		} else {
			this.dataList = dataList;
		}
		notifyDataSetChanged();
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return dataList == null ? null : dataList.size();
	}

	@Override
	public T getItem(int position) {
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		try {
			Viewholder viewholder = getClass().getAnnotation(Viewholder.class);
			Class holderClazz = viewholder.value();
			Object viewHolder = null;
			if (convertView == null) {
				ContentView contentView = getClass().getAnnotation(ContentView.class);
				if (contentView != null) {
					int viewId = contentView.value();
					convertView = mInflater.inflate(viewId, null);
					viewHolder = holderClazz.newInstance(); 
					convertView.setTag(viewHolder);
				}
			} else {
				viewHolder = convertView.getTag();
			}
			viewHolderFindView(viewHolder, convertView);
			T t = getItem(position);
			bindValue(position, convertView, t);
			return convertView;
		} catch (Exception e) {
		}
		return null;
	}

	public abstract void bindValue(int position, View convertView, T t);

	private void viewHolderFindView(Object viewHolder, View convertView) {
		ViewFinderUtils.findElements(viewHolder, new ViewFinder(convertView));
	}

}
