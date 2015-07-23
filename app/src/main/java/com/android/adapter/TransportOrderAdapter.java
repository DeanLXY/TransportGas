package com.android.adapter;

import android.content.Context;
import android.view.View;

import com.android.annotation.ContentView;
import com.android.annotation.Viewholder;
import com.android.module.Information;
import com.example.transportgas.R;

@ContentView(R.layout.distace_list_item)
@Viewholder(TransportOrderAdapter.ViewHolder.class)
public class TransportOrderAdapter extends AbsHolderAdapter<Information> {

	public TransportOrderAdapter(Context context) {
		super(context);
	}

	public static class ViewHolder {

	}

	@Override
	public void bindValue(int position, View convertView, Information t) {

	}

}
