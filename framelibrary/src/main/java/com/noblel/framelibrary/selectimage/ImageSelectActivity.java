package com.noblel.framelibrary.selectimage;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.noblel.framelibrary.R;
import com.noblel.framelibrary.selectimage.bean.ImageEntity;
import com.noblel.framelibrary.activity.BaseSkinActivity;
import com.noblel.framelibrary.view.navigationbar.DefaultNavigationBar;
import com.noblel.baselibrary.utils.SystemStatusBarUtil;

import java.util.ArrayList;

/**
 * @author Noblel
 *         图片选择的Activity
 */
public class ImageSelectActivity extends BaseSkinActivity implements View.OnClickListener, ImageSelectListener {
    //多选
    public static final int MODE_MULTI = 0x0011;
    //单选
    public static final int MODE_SINGLE = 0x0012;
    //是否显示相机的EXTRA_KEY
    public static final String EXTRA_SHOW_CAMERA = "EXTRA_SHOW_CAMERA";
    //总共可以显示多少张图片的EXTRA_KEY
    public static final String EXTRA_SELECT_COUNT = "EXTRA_SELECT_COUNT";
    //选择模式的EXTRA_KEY
    public static final String EXTRA_SELECT_MODE = "EXTRA_SELECT_MODE";
    //原始的图片路径的EXTRA_KEY
    public static final String EXTRA_DEFAULT_SELECT_LIST = "EXTRA_DEFAULT_SELECT_LIST";
    //返回选择图片列表的EXTRA_KEY
    public static final String EXTRA_RESULT = "EXTRA_RESULT";
    //单选或者多选 int类型的type
    private int mMode = MODE_MULTI;
    //图片张数
    private int mMaxCount = 8;
    //是否显示拍照按钮
    private boolean mShowCamera = true;
    //已经选择好的图片集合
    private ArrayList<ImageEntity> mResultList;

    private RecyclerView mRecyclerView;

    private TextView mSelectNumTv;

    private TextView mSelectPreview;

    @Override
    protected void initData() {
        //获取上一个页面传过来的参数
        Intent intent = getIntent();
        mMode = intent.getIntExtra(EXTRA_SELECT_MODE, mMode);
        mMaxCount = intent.getIntExtra(EXTRA_SELECT_COUNT, mMaxCount);
        mShowCamera = intent.getBooleanExtra(EXTRA_SHOW_CAMERA, mShowCamera);
        mResultList = intent.getParcelableArrayListExtra(EXTRA_DEFAULT_SELECT_LIST);
        if (mResultList == null) {
            mResultList = new ArrayList<>();
        }
        //初始化本地图片数据
        initImageList();
        //改变视图显示
        exchangeViewShow();
    }

    //改变布局显示需要及时更新
    private void exchangeViewShow() {
        //预览是否可以点击,显示颜色
        if (mResultList.size() > 0) {
            //至少选择了一张
            mSelectPreview.setEnabled(true);
            mSelectPreview.setOnClickListener(this);
        } else {
            //没有选择
            mSelectPreview.setEnabled(false);
            mSelectPreview.setOnClickListener(null);
        }
        //中间图片的张数
        mSelectNumTv.setText(mResultList.size() + "/" + mMaxCount);
        //确定是否可以点击,显示颜色
    }

    /**
     * ContentProvider获取内存卡所有图片
     */
    private void initImageList() {
        //耗时操作,开线程 AsyncTask
        //int  id 查询全部
        getLoaderManager().initLoader(0, null, mLoaderCallback);
    }

    /**
     * 加载图片的CallBack
     */
    private LoaderManager.LoaderCallbacks<Cursor> mLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>() {
        private final String[] IMAGE_PROJECTION = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.MIME_TYPE,
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media._ID};

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            //查询数据库
            return new CursorLoader(ImageSelectActivity.this,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[4] + ">0 AND " + IMAGE_PROJECTION[3] + "=? OR " +
                            IMAGE_PROJECTION[3] + "=? ", new String[]{"image/jpeg", "image/png"},
                    IMAGE_PROJECTION[2] + " DESC");
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            //解析封装到集合
            if (data != null && data.getCount() > 0) {
                ArrayList<ImageEntity> images = new ArrayList<>();
                //如果需要显示拍照
                if (mShowCamera) {
                    images.add(new ImageEntity("", "", 0L));
                }
                while (data.moveToNext()) {
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                    ImageEntity image = new ImageEntity(path, name, dateTime);
                    images.add(image);
                }
                //显示列表数据
                showImageList(images);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    /**
     * 显示图片列表
     */
    private void showImageList(ArrayList<ImageEntity> images) {
        ImageSelectAdapter adapter = new ImageSelectAdapter(this, images, mResultList, mMaxCount);
        adapter.setSelectImageListener(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.image_list_rv);
        mSelectNumTv = (TextView) findViewById(R.id.select_num);
        mSelectPreview = (TextView) findViewById(R.id.select_preview);
    }

    @Override
    protected void initTitle() {
        DefaultNavigationBar navigationBar = new DefaultNavigationBar
                .Builder(this)
                .setBackgroundColor(Color.parseColor("#ff261f1f"))
                .setTitle("所有图片")
                .builder();
        SystemStatusBarUtil.statusBarTintColor(this, Color.parseColor("#ff261f1f"));
    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_image_selector);
    }

    @Override
    public void onClick(View v) {
        //TODO 图片预览
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //第一个图片加到集合
        //调用sureSelect()方法
        //通知系统本地有图片改变
//        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE), Uri.fromFile());
    }

    @Override
    public void select() {
        exchangeViewShow();
    }

    public void sureSelect(View view) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(EXTRA_RESULT, mResultList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
