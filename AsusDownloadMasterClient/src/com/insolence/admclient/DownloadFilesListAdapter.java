package com.insolence.admclient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.insolence.admclient.entity.DownloadFileInfo;

public class DownloadFilesListAdapter extends ArrayAdapter<DownloadFileInfo>{
	public DownloadFilesListAdapter(Context context, int resource, DownloadFileInfo[] objects) {
		super(context, resource, objects);
	}

	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
		
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.select_files_list_item, null);
        }
		
        final DownloadFileInfo current = getItem(position);
        
		((TextView) v.findViewById(R.id.file_name)).setText(current.getName() + " (" + current.getSize() + ")");
		
		final CheckBox selectedCheckBox = (CheckBox) v.findViewById(R.id.file_selected);
		
		selectedCheckBox.setOnCheckedChangeListener(null);
		selectedCheckBox.setChecked(current.isSelected());
		selectedCheckBox.setOnCheckedChangeListener(
				new OnCheckedChangeListener() {
			        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			        	current.setSelected(isChecked);
			        	uncheckedItemsCount += isChecked ? -1 : 1;
					}
				});
		
		return v;
	}
	
	public void massSelect(boolean selected){
		for (int i = 0; i < getCount(); i++){
			DownloadFileInfo c = getItem(i);
			c.setSelected(selected);
			uncheckedItemsCount = selected ? 0 : getCount();
		}
	}
	
	public String getDownloadItemsArgument(){
		if (uncheckedItemsCount == 0)
			return "All";
		String result = "";
		for (int i = 0; i < getCount(); i++){
			DownloadFileInfo c = getItem(i);
			if (c.isSelected())
				result = result + c.getId() + ",";
		}
		if (!result.equals("")){
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}
	
	private int uncheckedItemsCount = 0;
}