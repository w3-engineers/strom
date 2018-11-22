package io.left.core.rmsample.ui.users;

import android.Manifest;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import core.left.io.framework.application.data.BaseServiceLocator;
import core.left.io.framework.application.data.helper.BaseResponse;
import core.left.io.framework.application.ui.base.ItemClickListener;
import core.left.io.framework.application.ui.base.rm.RmBaseActivity;
import core.left.io.framework.util.Text;
import core.left.io.framework.util.collections.CollectionUtil;
import core.left.io.framework.util.helper.PermissionUtil;
import core.left.io.framework.util.helper.Toaster;
import io.left.core.rmsample.R;
import io.left.core.rmsample.data.local.MyInfoProvider;
import io.left.core.rmsample.data.provider.ServiceLocator;
import io.left.core.rmsample.data.remote.UserEntity;
import io.left.core.rmsample.data.remote.model.UsableData;
import io.left.core.rmsample.data.remote.model.UserEntityResponse;
import io.left.core.rmsample.databinding.ActivityUsersBinding;

public class UsersActivity extends RmBaseActivity implements ItemClickListener<UserEntity> {

    private UsersViewModel mUsersViewModel;
    private ActivityUsersBinding mActivityUsersBinding;
    private UsersAdapter mUsersAdapter;
    private ServiceLocator mServiceLocator;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_users;
    }

    @Override
    protected void startUI() {

        if (PermissionUtil.on(this).request(Manifest.permission.GET_ACCOUNTS)) {
            initData();
        }

    }

    private void initData() {
        mActivityUsersBinding = (ActivityUsersBinding) getViewDataBinding();
        mActivityUsersBinding.setUserEntity(new MyInfoProvider(getApplicationContext()).getMyProfileInfo());
        mUsersViewModel = getViewModel();

        initRecyclerView();

        mUsersViewModel.getUsersLiveData().observe(this, this::onChanged);

        mUsersViewModel.getPingData().observe(this, userEntityEvent ->
        {
            if(userEntityEvent != null) {

                Toaster.showLong("Ping response from: "+userEntityEvent.getContentIfNotHandled().mUserName);

            }
        });

        mUsersViewModel.getData().observe(this, usableDataEvent -> {

            if(usableDataEvent != null) {
                UsableData usableData = usableDataEvent.getContentIfNotHandled();
                if(usableData != null && Text.isNotEmpty(usableData.mData) && usableData.mPeerUserEntity != null) {
                    String st = String.format("%s from %s(%s)", usableData.mData,
                            usableData.mPeerUserEntity.mUserName, usableData.mPeerUserEntity.mUserId);

                    Toaster.showLong(st);
                }
            }
        });
    }

    @Override
    protected <T extends BaseResponse> void onSuccessResponse(@NonNull T baseResponse) {
        super.onSuccessResponse(baseResponse);

        UserEntityResponse userEntityResponse = (UserEntityResponse) baseResponse;

        if(userEntityResponse.state == UserEntityResponse.ADDED) {

            mUsersAdapter.addItem(userEntityResponse.mUserEntity);

        } else if(userEntityResponse.state == UserEntityResponse.GONE) {

            mUsersAdapter.removeItem(userEntityResponse.mUserEntity);

        }
    }

    private void initRecyclerView() {
        mUsersAdapter = new UsersAdapter();
        mUsersAdapter.setItemClickListener(this);
        mActivityUsersBinding.rv.setAdapter(mUsersAdapter);
        mActivityUsersBinding.rv.setLayoutManager(new LinearLayoutManager(this));
    }


    @SuppressWarnings("unchecked")
    private UsersViewModel getViewModel() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

                mServiceLocator = ServiceLocator.getInstance();
                return (T) mServiceLocator.getUsersModel(getApplicationContext());

            }

        }).get(UsersViewModel.class);

    }

    @Override
    protected BaseServiceLocator getServiceLocator() {
        return mServiceLocator;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(CollectionUtil.hasItem(permissions) && permissions[0].equals(Manifest.permission.GET_ACCOUNTS)) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initData();
            } else {
                Toaster.showLong(getString(R.string.user_activity_permission_required));
            }
        }
    }

    @Override
    public void onItemClick(View view, UserEntity userEntity) {

        if(view != null && userEntity != null) {
            mUsersViewModel.ping(userEntity);
        }

    }

    private void onChanged(UserEntityResponse userEntityResponse) {
        onResponse(userEntityResponse, getString(R.string.users_unable_to_process));
    }
}
