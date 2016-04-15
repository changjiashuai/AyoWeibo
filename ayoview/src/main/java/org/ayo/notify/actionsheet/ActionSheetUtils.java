package org.ayo.notify.actionsheet;

import android.content.Context;


/**
 * eg:
 * String[] items = {"photo0 ", "catemra 1", "item2", "item3"};
	private OnSheetItemClickListener actionSheetCallback = new OnSheetItemClickListener() {
		
		@Override
		public void onClick(int which) {
			if(which == 0){
				
			}else if(which == 1){
				
			}else if(which == 2){
				
			}else if(which == 3){
				
			}
		}
	};
	ActionSheetUtils.showActionSheet(agent.getActivity(), items, actionSheetCallback);
 * @author cowthan
 *
 */
public class ActionSheetUtils {

	
	public static void showActionSheet(Context c, String[] items, ActionSheetDialog.OnSheetItemClickListener callback){
		ActionSheetDialog a = new ActionSheetDialog(c)
		.builder()
		.setCancelable(true)
		.setCanceledOnTouchOutside(true);
		for(String item: items){
			a.addSheetItem(item, ActionSheetDialog.SheetItemColor.Blue, callback);
		}
		a.show();
	}
}
