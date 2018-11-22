package io.left.core.sample.ui.details;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/5/2018 at 6:58 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/5/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import core.left.io.framework.application.data.helper.BaseResponse;
import core.left.io.framework.application.ui.base.BaseActivity;
import core.left.io.framework.application.ui.base.ItemClickListener;
import core.left.io.framework.application.ui.util.DialogUtil;
import core.left.io.framework.application.ui.util.DialogUtil.DialogListener;
import io.left.core.sample.R;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.remote.user.UserEntityResponse;
import io.left.core.sample.data.service.ServiceLocator;
import io.left.core.sample.databinding.ActivityUserDetailsBinding;
import io.left.core.sample.ui.datainput.DataInputActivity;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class DetailsActivity extends BaseActivity implements ItemClickListener<UserEntity> {

    private ActivityUserDetailsBinding binding;
    private DetailsAdapter detailsAdapter;
    private DetailsViewModelLocal mDetailsViewModelLocal;
    private DetailsViewModelRemote mDetailsViewModelRemote;
    private AlertDialog mDisplayingAlertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_details;
    }

    @Override
    protected int getMenuId() {
        return R.menu.menu_detail;
    }

    @Override
    protected int statusBarColor() {
        return R.color.accent;
    }

    @Override
    protected void startUI() {
        binding = (ActivityUserDetailsBinding) getViewDataBinding();
        detailsAdapter = new DetailsAdapter();

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator));
        binding.rv.addItemDecoration(dividerItemDecoration);

        binding.rv.setAdapter(detailsAdapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        detailsAdapter.setItemClickListener(this);

        mDetailsViewModelLocal = getViewModelLocal();
        mDetailsViewModelLocal.getAllUsers().observe(this, userEntityResponse -> {
            //We have omitted this version to invisible progress view.
            // In real case we will be storing data
            // data to db and it should be re-actively consumed by UI or other source (We are not closing
            // the LiveData option that is also a reactive component).
            // In Our next version we have plan to update this approach one more level so that we will have
            // one more level abstraction/commonality to handle pieces of data
//            binding.loadingProgress.setVisibility(View.GONE);

            onResponse(userEntityResponse, getString(R.string.details_error_message));
        });

        mDetailsViewModelLocal.getDeleteEvent().observe(this, userEntityIntegerForEvent -> {
            //retrieving forEvent object value only if not handled
            Integer eventData = userEntityIntegerForEvent == null ? null : userEntityIntegerForEvent.getContentIfNotHandled();

            if(eventData != null && eventData > 0 && userEntityIntegerForEvent.getForData() != null ) {

                detailsAdapter.removeItem(userEntityIntegerForEvent.getForData());
                
                if(mDisplayingAlertDialog != null) {
                    mDisplayingAlertDialog.dismiss();
                }
            }
        });

        mDetailsViewModelRemote = getViewModelRemote();
        mDetailsViewModelRemote.getAllUsers().observe(this, userEntityResponse -> {
            binding.loadingProgress.setVisibility(View.GONE);

            onResponse(userEntityResponse, getString(R.string.details_error_message));
        });
    }

    @Override
    protected <T extends BaseResponse> void onSuccessResponse(@NonNull T baseResponse) {
        super.onSuccessResponse(baseResponse);
        UserEntityResponse userEntityResponse = (UserEntityResponse) baseResponse;
        detailsAdapter.addItem(userEntityResponse.mUserEntities);
    }

    @Override
    protected void stopUI() {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                openDataInputActivity();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addItem(View view) {
        openDataInputActivity();
    }

    @SuppressWarnings("unchecked")
    private DetailsViewModelLocal getViewModelLocal() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) ServiceLocator.getInstance().getDetailsModelLocal(getApplicationContext());
            }
        }).get(DetailsViewModelLocal.class);

    }

    @SuppressWarnings("unchecked")
    private DetailsViewModelRemote getViewModelRemote() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T) ServiceLocator.getInstance().getDetailsModelRemote();
            }
        }).get(DetailsViewModelRemote.class);

    }

    @Override
    public void onItemClick(View view, UserEntity item) {

        if (view != null) {
            switch (view.getId()) {
                case R.id.btn_delete:
                    showDeleteConfirmationDialog(item);
                    break;
                case R.id.btn_edit:
                    EditDialog.newInstance(item.copy()).show(getSupportFragmentManager());
                    break;

            }
        }
    }

    private void showDeleteConfirmationDialog(UserEntity item) {
        DialogUtil.showDialog(this, getString(R.string.delete_confirmation), new DialogListener() {
            @Override
            public void onClickPositive() {
                mDetailsViewModelLocal.delete(item);
            }

            @Override
            public void onClickNegative() {

            }
        });
    }

    private void openDataInputActivity() {
        Intent intent = new Intent(this, DataInputActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        startActivity(intent);
    }
}
