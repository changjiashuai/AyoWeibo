package com.iwomedia.statictis;

import android.content.Context;

import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

public class AppUpdateUtils {
	
	public interface OnUpdateCheckComplete{
		void hasNewVersion(UpdateResponse updateInfo);
		void hasNoNewVersion();
	}
	
	public static void checkNewVersionAvailable(Context context, final OnUpdateCheckComplete onUpdateCheckComplete){
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		    @Override
		    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		        switch (updateStatus) {
		        case UpdateStatus.Yes: // has update
//		            UmengUpdateAgent.showUpdateDialog(MainListActivity.this, updateInfo);
//		            App.setIfNeedUpdate(true);
		        	onUpdateCheckComplete.hasNewVersion(updateInfo);
		            break;
		        case UpdateStatus.No: // has no update
		        	onUpdateCheckComplete.hasNoNewVersion();
		        	break;
		        case UpdateStatus.NoneWifi: // none wifi
		        	onUpdateCheckComplete.hasNoNewVersion();
		            break;
		        case UpdateStatus.Timeout: // time out
		        	onUpdateCheckComplete.hasNoNewVersion();
		            break;
		        }
		    }
		});
		UmengUpdateAgent.update(context);
	}
	
	public static void checkNewVersionAvailableAndNotify(final Context context){
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
		    @Override
		    public void onUpdateReturned(int updateStatus,UpdateResponse updateInfo) {
		        switch (updateStatus) {
		        case UpdateStatus.Yes: // has update
		            UmengUpdateAgent.showUpdateDialog(context, updateInfo);
		            break;
		        case UpdateStatus.No: // has no update
		        	break;
		        case UpdateStatus.NoneWifi: // none wifi
		            break;
		        case UpdateStatus.Timeout: // time out
		            break;
		        }
		    }
		});
		UmengUpdateAgent.update(context);
	}
}
