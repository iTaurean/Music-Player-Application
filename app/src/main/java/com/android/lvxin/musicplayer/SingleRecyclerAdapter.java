package com.android.lvxin.musicplayer;

/**
 * @ClassName: SingleRecyclerAdapter
 * @Description: TODO
 * @Author: lvxin
 * @Date: 2017/7/13 08:35
 */
public abstract class SingleRecyclerAdapter<T, VM> extends BaseRecyclerAdapter<T, VM> {

    private final int layoutId;

    public SingleRecyclerAdapter(final int layoutId, VM viewModel) {
        super(viewModel);
        this.layoutId = layoutId;
    }

    @Override
    protected T getObjForPosition(int position) {
        if (null == data || data.size() <= position) {
            return null;
        }

        return data.get(position);
    }

    @Override
    protected int getLayoutIdForPosition(int position) {
        return layoutId;
    }

}
