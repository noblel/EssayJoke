package com.noblel.framelibrary.selectimage;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.noblel.baselibrary.adapter.BaseRecyclerAdapter;
import com.noblel.baselibrary.adapter.BaseViewHolder;
import com.noblel.framelibrary.R;
import com.noblel.framelibrary.selectimage.bean.ImageEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noblel
 */
public class ImageSelectAdapter extends BaseRecyclerAdapter<ImageEntity> {
    private ArrayList<ImageEntity> mImageListResult;
    private int mMaxCount;

    public ImageSelectAdapter(Context context, List<ImageEntity> data,
                              ArrayList<ImageEntity> images, int maxCount) {
        super(context, data, R.layout.media_chooser_item);
        mImageListResult = images;
        mMaxCount = maxCount;
    }

    @Override
    public void convert(BaseViewHolder holder, final ImageEntity item, int position) {
        if (TextUtils.isEmpty(item.getName())) {
            //显示拍照
            holder.setVisibility(R.id.camera_ll, View.VISIBLE);
            holder.setVisibility(R.id.media_selected_indicator, View.INVISIBLE);
            holder.setVisibility(R.id.image, View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    PermissionHelper.with((Activity) mContext)
//                            .requestPermission(Manifest.permission.CAMERA)
//                            .requestCode()
                }
            });
        } else {
            //显示图片
            holder.setVisibility(R.id.camera_ll, View.INVISIBLE);
            holder.setVisibility(R.id.media_selected_indicator, View.VISIBLE);
            holder.setVisibility(R.id.image, View.VISIBLE);
            //利用Glide显示图片
            ImageView imageView = holder.getView(R.id.image);

            Glide.with(mContext).load(item.getPath()).centerCrop().into(imageView);

            ImageView selectedImage = holder.getView(R.id.media_selected_indicator);

            if (mImageListResult.contains(item)) {
                selectedImage.setSelected(true);
            } else {
                selectedImage.setSelected(false);
            }

            //增加点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mImageListResult.contains(item)) {
                        //不能大于最大张数
                        if (mImageListResult.size() >= mMaxCount) {
                            String toast = String.format(mContext.getString(R.string.toast_select_image)
                                    , mMaxCount);
                            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mImageListResult.add(item);
                    } else {
                        mImageListResult.remove(item);
                    }
                    notifyDataSetChanged();
                    //通知显示布局
                    if (mListener !=null) {
                        mListener.select();
                    }
                }
            });
        }
    }


    private ImageSelectListener mListener;

    public ImageSelectListener getSelectImageListener() {
        return mListener;
    }

    public void setSelectImageListener(ImageSelectListener listener) {
        mListener = listener;
    }
}
