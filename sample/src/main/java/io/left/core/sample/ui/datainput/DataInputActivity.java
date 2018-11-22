package io.left.core.sample.ui.datainput;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/5/2018 at 6:57 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/5/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import core.left.io.framework.application.ui.base.BaseActivity;
import core.left.io.framework.util.helper.Toaster;
import io.left.core.sample.R;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.data.service.ServiceLocator;
import io.left.core.sample.databinding.ActivityDataInputBinding;
import io.left.core.sample.ui.details.DetailsViewModelLocal;

public class DataInputActivity extends BaseActivity {

    private ActivityDataInputBinding dataInputBinding;
    private DetailsViewModelLocal mDetailsViewModel;
    private UserEntity mUserEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_data_input;
    }

    @Override
    protected void startUI() {
        dataInputBinding = (ActivityDataInputBinding) getViewDataBinding();
        mDetailsViewModel = getViewModel();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Observer for this particular sample this one not much necessary real strength is
        //enjoyed when multiple data sources are there for a same piece of data
        mDetailsViewModel.getUpdateOrInsertEvent().observe(this, forEvent -> {
            //retrieving forEvent object value only if not handled
            Long eventData = forEvent == null ? null : forEvent.getContentIfNotHandled();

            //Checking so that the update happens only for our intended id only. We ignore all
            //other id obviously. Although for this sample alteration of id not possible.
            //Still, the example given for developers particular requirement purpose.
            //Also the resultant entity is not from database it is only the requested object to match
            //or verify user requests
            if(eventData != null && forEvent.getForData() != null &&
                    forEvent.getForData().getId() == (mUserEntity == null ? -1 : mUserEntity.getId())) {
                Toaster.showLong(String.format(getString(R.string.user_inserted_confirmation),
                        mUserEntity.getUserName()));
                finish();
            }
        });
    }

    @Override
    protected void stopUI() {
    }

    public void onSaveData(View e) {

        String name = dataInputBinding.nameTv.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toaster.showLong(getString(R.string.user_inserted_empty_message));
            return;
        }

        mUserEntity = new UserEntity(name);
        mDetailsViewModel.updateOrInsert(mUserEntity);
    }

    @SuppressWarnings("unchecked")
    private DetailsViewModelLocal getViewModel() {

        return ViewModelProviders.of(this, new ViewModelProvider.Factory() {

            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {


                return (T) ServiceLocator.getInstance().getDetailsModelLocal(getApplicationContext());

            }

        }).get(DetailsViewModelLocal.class);

    }

}
