package com.noblel.baselibrary.version;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import com.noblel.baselibrary.R;


/**
 * @author Noblel
 */
public class DownLoadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
            DownloadManager.Query query = new DownloadManager.Query();
            // 在广播中取出下载任务的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            query.setFilterById(id);
            Cursor cursor = manager.query(query);
            if (cursor.moveToFirst()) {
                String url = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                // 如果文件名不为空，说明已经存在了，拿到文件名想干嘛都好
                if (url != null) {
                    VersionManager.installAPK(context, url);
                }
            }
        } else if (DownloadManager.ACTION_NOTIFICATION_CLICKED.equals(intent.getAction())) {
            Toast.makeText(context, context.getString(R.string.downloading), Toast.LENGTH_SHORT).show();
        }
    }
}
