package com.camerarecorder.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.camerarecorder.R;
import com.camerarecorder.view.CameraPreview;

/**
 * @author changhaismile
 * @name VideoRecorderFragment
 * @comment 视频录制
 * @date 2018/7/7
 */
public class VideoRecorderFragment extends Fragment{
    public static final String TAG = VideoRecorderFragment.class.getSimpleName();
    private View mRootView;
    private Button mCaptureBtn;
    private CameraPreview mCameraPreview;

    public static VideoRecorderFragment newInstance() {
        VideoRecorderFragment fragment = new VideoRecorderFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "frag onViewCreated");
        mRootView = view;
        mCaptureBtn = view.findViewById(R.id.capture_btn);
        mCaptureBtn.setOnClickListener(listener);
        initCameraPreview();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "frag onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "frag onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_video_recorder, container, false);
        return mRootView;
    }

    private void initCameraPreview() {
        mCameraPreview = new CameraPreview(getContext());
        FrameLayout frameLayout = mRootView.findViewById(R.id.video_fl_perview);
        frameLayout.addView(mCameraPreview);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 销毁预览");
        mCameraPreview = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 回到前台");
        if (mCameraPreview == null) {
            initCameraPreview();
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.capture_btn:
                    if (mCameraPreview.isRecording()) {
                        mCameraPreview.stopRecording();
                        mCaptureBtn.setText("录像");
                    } else {
                        if (mCameraPreview.startRecording()) {
                            mCaptureBtn.setText("停止");
                        }
                    }
                    break;
            }
        }
    };
}
