package com.android.lvxin.musicplayer;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * The type Base recycler adapter.
 *
 * @param <T> the type parameter
 * @ClassName: BaseRecyclerAdapter
 * @Description: TODO
 * @Author: lvxin
 * @Date: 12 /14/15 15:48
 */
public abstract class BaseRecyclerAdapter<T, VM> extends RecyclerView.Adapter<BaseRecyclerAdapter.ItemViewHolder> {

    protected VM mViewModel;
    protected List<T> data;

    public BaseRecyclerAdapter(VM viewModel) {
        this.mViewModel = viewModel;
    }

    public void updateData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        binding.setVariable(BR.viewModel, mViewModel);
        return new ItemViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        T obj = getObjForPosition(position);
        holder.bind(obj);
        setItemValue(obj);
    }

    @Override
    public int getItemCount() {
        return null != data ? data.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIdForPosition(position);
    }

    protected abstract void setItemValue(T obj);

    protected abstract T getObjForPosition(int position);

    protected abstract int getLayoutIdForPosition(int position);

    static class ItemViewHolder<T> extends RecyclerView.ViewHolder {

        private final ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(T obj) {
            binding.setVariable(BR.obj, obj);
            binding.executePendingBindings();
        }
    }
}
