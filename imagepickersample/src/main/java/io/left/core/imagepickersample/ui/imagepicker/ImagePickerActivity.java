package io.left.core.imagepickersample.ui.imagepicker;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import core.left.io.framework.application.ui.base.BaseActivity;
import core.left.io.framework.util.helper.imgpicker.ImagePicker;
import core.left.io.framework.util.helper.imgpicker.ImagePickerDataModel;
import io.left.core.imagepickersample.R;
import io.left.core.imagepickersample.databinding.ActivityImagePickerBinding;


public class ImagePickerActivity extends BaseActivity {

    private final int REQUEST_CODE_PICK_IMAGE = 101;

    private ActivityImagePickerBinding binding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_picker;
    }

    @Override
    protected void startUI() {
        setClickListener(findViewById(R.id.pick_all_images));
        binding = (ActivityImagePickerBinding) getViewDataBinding();
        binding.setImageModel(new ImagePickerDataModel());
    }

    @Override
    protected void stopUI() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQUEST_CODE_PICK_IMAGE:

                Uri uri = ImagePicker.getImageUri(getApplicationContext(), resultCode, data);
                binding.getImageModel().setImageUri(uri);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view != null) {
            switch (view.getId()) {
                case R.id.pick_all_images:

                    startActivityForResult(ImagePicker.getPickImageIntent(getApplicationContext()), REQUEST_CODE_PICK_IMAGE);

                    break;
            }
        }
    }
}