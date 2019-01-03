package io.left.core.sample.ui.details;

/*
 *  ****************************************************************************
 *  * Created by : Md. Azizul Islam on 7/6/2018 at 2:13 PM.
 *  * Email : azizul@w3engineers.com
 *  *
 *  * Purpose:
 *  *
 *  * Last edited by : Md. Azizul Islam on 7/6/2018.
 *  *
 *  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>
 *  ****************************************************************************
 */

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.w3engineers.ext.strom.application.ui.base.BaseAdapter;
import io.left.core.sample.R;
import io.left.core.sample.data.database.user.UserEntity;
import io.left.core.sample.databinding.ItemUserInfoLocalBinding;
import io.left.core.sample.databinding.ItemUserInfoRemoteBinding;

public class DetailsAdapter extends BaseAdapter<UserEntity> {

    private final int USER_TYPE_LOCAL = 1, USER_TYPE_REMOTE = 0;

    @Override
    public boolean isEqual(UserEntity left, UserEntity right) {
        return !left.mIsFromNetwork && left.getId() == right.getId();
    }

    @Override
    public int getItemViewType(int position) {
        UserEntity userEntity = getItem(position);

        return userEntity.mIsFromNetwork ? USER_TYPE_REMOTE : USER_TYPE_LOCAL;
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseAdapterViewHolder newViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case USER_TYPE_LOCAL:
                return new UserHolderLocal(inflate(parent, R.layout.item_user_info_local));

            case USER_TYPE_REMOTE:
                return new UserHolderRemote(inflate(parent, R.layout.item_user_info_remote));
        }

        return null;
    }

    private class UserHolderLocal extends BaseAdapterViewHolder<UserEntity>{

        private ItemUserInfoLocalBinding mItemUserInfoLocalBinding;

        UserHolderLocal(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            mItemUserInfoLocalBinding = (ItemUserInfoLocalBinding) viewDataBinding;
        }

        @Override
        public void bind(UserEntity item) {
            mItemUserInfoLocalBinding.setUserEntity(item);
            mItemUserInfoLocalBinding.executePendingBindings();
//            binding.nameTv.setText(String.format("Name : %s",item.getUserName()));
            mItemUserInfoLocalBinding.btnDelete.setOnClickListener(this);
            mItemUserInfoLocalBinding.btnEdit.setOnClickListener(this);
        }
    }

    private class UserHolderRemote extends BaseAdapterViewHolder<UserEntity>{

        private ItemUserInfoRemoteBinding mItemUserInfoRemoteBinding;

        UserHolderRemote(ViewDataBinding viewDataBinding) {
            super(viewDataBinding);
            mItemUserInfoRemoteBinding = (ItemUserInfoRemoteBinding) viewDataBinding;
        }

        @Override
        public void bind(UserEntity item) {
            mItemUserInfoRemoteBinding.setUserEntity(item);
            mItemUserInfoRemoteBinding.executePendingBindings();
        }
    }
}
