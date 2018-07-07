package com.camerarecorder;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.camerarecorder.fragment.VideoRecorderFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_VIDEO_PERMISSIONS = 1;
    private static final String[] VIDEO_PERMISSION = {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (hasPermission(getApplicationContext(), VIDEO_PERMISSION)) {

        } else {
            Log.d(TAG, "onCreate: requestPermissions");
            ActivityCompat.requestPermissions(this, VIDEO_PERMISSION, REQUEST_VIDEO_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_VIDEO_PERMISSIONS && permissions.length == VIDEO_PERMISSION.length) {
            boolean getPermission = true;
            for (int code : grantResults) {
                getPermission = getPermission && (PackageManager.PERMISSION_GRANTED == code);
            }
            if (getPermission) {
                Log.d(TAG, "onRequestPermissionsResult: 获得所需全部权限");
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_fl_container, VideoRecorderFragment.newInstance()).commitAllowingStateLoss();
            } else {
                Toast.makeText(getApplicationContext(), "Please grant the permissions", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /***
     * 权限判断
     * @param mContext
     * @param permissions
     * @return
     */
    private boolean hasPermission(Context mContext, String[] permissions) {
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED !=
                    ActivityCompat.checkSelfPermission(mContext, permission)) {
                Log.e(TAG, "no permission:" + permission);
                return false;
            }
        }
        return true;
    }
}
