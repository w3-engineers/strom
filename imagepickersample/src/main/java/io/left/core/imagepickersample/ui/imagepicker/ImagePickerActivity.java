package io.left.core.imagepickersample.ui.imagepicker;

import android.Manifest;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.w3engineers.ext.strom.application.ui.base.BaseActivity;
import com.w3engineers.ext.strom.util.helper.PermissionUtil;
import com.w3engineers.ext.strom.util.helper.Toaster;
import com.w3engineers.ext.strom.util.helper.image.imgpicker.ImagePicker;
import com.w3engineers.ext.strom.util.helper.image.imgpicker.ImagePickerDataModel;

import io.left.core.imagepickersample.R;
import io.left.core.imagepickersample.databinding.ActivityImagePickerBinding;


public class ImagePickerActivity extends BaseActivity {

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
    protected void onImage(Uri imageUri) {
        super.onImage(imageUri);
        if(imageUri != null) {
            binding.getImageModel().setImageUri(imageUri);
        } else {
            Toaster.showLong("Unable to pick image");
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view != null) {
            switch (view.getId()) {
                case R.id.pick_all_images:
                    if(PermissionUtil.on(this).request(ImagePicker.REQUEST_CODE_PICK_IMAGE, Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        this.startActivityForResult(ImagePicker.getInstance().getImagePickerIntent(getApplicationContext()),
                                ImagePicker.REQUEST_CODE_PICK_IMAGE);
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == ImagePicker.REQUEST_CODE_PICK_IMAGE) {

            if(PermissionUtil.on(getApplicationContext()).isAllowed(permissions)) {
                this.startActivityForResult(ImagePicker.getInstance().getImagePickerIntent(getApplicationContext()),
                        ImagePicker.REQUEST_CODE_PICK_IMAGE);
            } else {
                Toaster.showLong("Permission required");
            }

        }
    }
}