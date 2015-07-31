package com.insolence.admclient;

import com.insolence.admclient.entity.DownloadFileInfo;
import com.insolence.admclient.entity.DownloadInfo;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

public class SelectFilesAlertDialogBuilder {

	public static void build(Activity context, DownloadInfo downloadInfo, final IDialogResultProcessor processor){
		final AlertDialog.Builder alert = new AlertDialog.Builder(context);
	    alert.setTitle(context.getResources().getString(R.string.dialog_select_files_to_download_title));
	    
	    
	    View dialogView = context.getLayoutInflater().inflate( R.layout.select_files_dialog, null); 
	    
	    ListView filesListView = (ListView) dialogView.findViewById(R.id.files_list);
	    
	    final DownloadFilesListAdapter adapter = new DownloadFilesListAdapter(context, R.layout.select_files_list_item, downloadInfo.getFiles().toArray(new DownloadFileInfo[0]));
	    
	    View headerView = context.getLayoutInflater().inflate(R.layout.select_files_list_header, null, false);
	    
	    headerView.findViewById(R.id.select_all_hyperlink).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.massSelect(true);
				adapter.notifyDataSetChanged();
			}
		});
	    
	    headerView.findViewById(R.id.select_none_hyperlink).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.massSelect(false);
				adapter.notifyDataSetChanged();
			}
		});
	    
		filesListView.addHeaderView(headerView);
		
		filesListView.setAdapter(adapter);
	    
	    alert.setView(dialogView);
	    
	    alert.setPositiveButton(context.getResources().getString(R.string.basic_yes), new DialogInterface.OnClickListener() {
    	    public void onClick(DialogInterface dialog, int whichButton) {
				String arg = adapter.getDownloadItemsArgument();
				if (processor != null && !arg.equals(""))
					processor.process(arg);     	      
    	    }
    	  });  
    	  
	    alert.setNegativeButton(context.getResources().getString(R.string.basic_cancel), null);

    	alert.show();
	}
	
	public interface IDialogResultProcessor{
		void process(String argument);
	}
	
}
