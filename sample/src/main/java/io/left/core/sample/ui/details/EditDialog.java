package io.left.core.sample.ui.details;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/10/2018 at 12:02 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/10/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.w3engineers.ext.strom.application.ui.widget.BaseDialog;
import com.w3engineers.ext.strom.application.ui.widget.BaseEditText;
import io.left.core.sample.R;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.service.ServiceLocator;
import io.left.core.sample.databinding.CustomDialogBinding;
import io.left.core.util.helper.Constants;

public class EditDialog extends BaseDialog {
    String TAG = getClass().getName();
    private BaseEditText nameTv, addressTv;
    private Button btnUpdate, btnCancel;
    private DetailsViewModelLocal mDetailsViewModel;
    private CustomDialogBinding mCustomDialogBinding;
    private UserEntity mUserEntity;

    public static EditDialog newInstance(UserEntity entity) {
        EditDialog fragment = new EditDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.User.ENTITY, entity);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.custom_dialog;
    }

    @Override
    protected void startUi() {
        //fetch user
        initData();

        btnUpdate = getView().findViewById(R.id.btn_update);
        btnCancel = getView().findViewById(R.id.btn_cancel);
        btnUpdate.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void initData() {
        if(getArguments() != null) {
            UserEntity userEntity = getArguments().getParcelable(Constants.User.ENTITY);

            if(userEntity != null) {

                mUserEntity = userEntity;

                //fetch user viewmodel
                mDetailsViewModel = getViewModel();

                //data binder setup
                mCustomDialogBinding = (CustomDialogBinding) getViewDataBinding();
                mCustomDialogBinding.setUserEntity(userEntity);

                //Observer for this particular sample this one not much necessary real strength is
                //enjoyed when multiple data sources are there for a same piece of data
                mDetailsViewModel.getUpdateOrInsertEvent().observe(this, forEvent -> {
                    //retrieving forEvent object value only if not handled
                    Long eventData = forEvent == null ? null : forEvent.getContentIfNotHandled();

                    //Checking so that the update happens only for our intended id only. We ignore all
                    //other id obviously. Although for this sample alteration of id not possible.
                    //Still, the example given for developers particular requirement purpose
                    if(eventData != null && forEvent.getForData() != null &&
                            forEvent.getForData().getId() == (mUserEntity == null ? -1 : mUserEntity.getId())) {
                        dismiss();
                    }
                });
            }
        }
    }

    public void show(FragmentManager fragmentManager) {
        super.show(fragmentManager, TAG);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_update:
                mDetailsViewModel.updateOrInsert(mCustomDialogBinding.getUserEntity());

                break;
            case R.id.btn_cancel:
                dismiss();
                break;
        }
    }

    @SuppressWarnings("unchecked")
    private DetailsViewModelLocal getViewModel() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

                if(getActivity() == null) {
                    return null;
                }

                return (T) ServiceLocator.getInstance().getDetailsModelLocal(getActivity().getApplicationContext());

            }

        }).get(DetailsViewModelLocal.class);

    }
}
