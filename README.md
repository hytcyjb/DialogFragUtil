<h2>DialogFragUtil 打造一款DialogFragment的统一封装类</h2>
```
/***
 * DialogFragUtil 打造一款DialogFragment的统一封装类
 * 我的qq号：1457521527；欢迎互相学习~ 
 * ****欢迎star****
 *  @author yjbo
 *  @create 2017.03.29  23:00
 */
```
<h3>一.参考：</h3>
   1.（封装布局内的id）封装adapter内使用的保存layout的id方法：
           http://www.cniao5.com/forum/thread/2ac69d820f0611e790dc00163e0230fa
           
   2.（自定义DialogFragment的高度使用）https://github.com/shaohui10086/BottomDialog.git

<h3>二.源码：</h3>
[点击下载](https://github.com/hytcyjb/DialogFragUtil.git)
    
    

<h3>三.效果图：</h3>

<p><img src="https://github.com/hytcyjb/DialogFragUtil/blob/master/screenshot/jdfw.gif?raw=true" width="230" height="427"></p>

<p><img src="https://github.com/hytcyjb/DialogFragUtil/blob/master/screenshot/app_pic_1.png?raw=true" width="230" height="427">    <img src="https://github.com/hytcyjb/DialogFragUtil/blob/master/screenshot/app_pic_2.png?raw=true" width="230" height="427">   <img src="https://github.com/hytcyjb/DialogFragUtil/blob/master/screenshot/app_pic_3.png?raw=true" width="230" height="427"></p>


<h3>四.代码分析：</h3>

```
主要代码：
/** 
 * 参考：封装adapter内使用的保存layout的id方法：http://www.cniao5.com/forum/thread/2ac69d820f0611e790dc00163e0230fa
 * @author yjbo
 * @time 2017/4/22 13:55
 */
public class ViewHolderUtil {

    private SparseArray<View> mViews;
    private View mItemView;

    public ViewHolderUtil(View itemView) {

        mItemView = itemView;
        this.mViews = new SparseArray<>();
    }

    /**
     * 从ItemView获取View
     *
     * @param id  ItemView里包含的ViewId
     * @param <V> 返回View
     * @return
     */
    public <V extends View> V getView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = mItemView.findViewById(id);
            mViews.put(id, view);
        }
        return (V) view;
    }

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolderUtil setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 设置ImageView的值
     *
     * @param viewId
     * @param resId
     * @return
     */
    public ViewHolderUtil setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 设置ImageView的值
     * 第三方  ImageLoder Glide Picasso
     * 不能直接写死第三方图片加载
     * 使用自己的一套规范  ImageLoder
     *
     * @param viewId
     * @return
     */
    public ViewHolderUtil setImagePath(int viewId, ImageLoder imageLoder) {
        ImageView view = getView(viewId);
        imageLoder.loadImage(view, imageLoder.getPath());
        return this;
    }

    //图片加载 （对第三方库加载图片等封装）
    public abstract static class ImageLoder {
        private String path;

        public ImageLoder(String path) {
            this.path = path;
        }

        //需要复写该方法加载图片
        public abstract void loadImage(ImageView imageView, String path);

        public String getPath() {
            return path;
        }
    }

    /**
     * 设置是否可见
     *
     * @param viewId
     * @param visible
     * @return
     */
    public ViewHolderUtil setVisible(int viewId, boolean visible) {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    /**
     * 设置tag
     *
     * @param viewId
     * @param tag
     * @return
     */
    public ViewHolderUtil setTag(int viewId, Object tag) {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolderUtil setTag(int viewId, int key, Object tag) {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    /**
     * 设置Checkable
     *
     * @param viewId
     * @param checked
     * @return
     */
    public ViewHolderUtil setChecked(int viewId, boolean checked) {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    //点击事件
    public ViewHolderUtil setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    //触摸事件
    public ViewHolderUtil setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    //长按事件
    public ViewHolderUtil setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }
}
```

```
 在初始化下一个弹出框的时候把上个弹出框先去掉
        if (simpleDialogFrag != null && simpleDialogFrag.isVisible()) {
            simpleDialogFrag.dismiss();
        }
```

```
/**
 * 最简单的DialogFragment样式
 *
 * @author yjbo
 * @qq 1457521527
 * @time 2017/4/22 14:18
 */
public abstract class SimpleDialogFrag extends DialogFragment {

    private boolean mIsKeyCanback = true;
    private boolean mIsOutCanback = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public SimpleDialogFrag() {
    }

    /**
     * isKeyCanback 点击物理返回键可以取消
     * isOutCanback 点击除了弹出框其他地方可以取消
     *
     * @author yjbo  @time 2017/4/22 16:26
     */
    public SimpleDialogFrag(boolean isOutCanback, boolean isKeyCanback) {
        mIsKeyCanback = isKeyCanback;
        mIsOutCanback = isOutCanback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        getDialog().setCanceledOnTouchOutside(mIsOutCanback);//弹出框外面是否可取消

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    getDialog().setCanceledOnTouchOutside(mIsKeyCanback);//键盘点击时是否可以取消--不需要设置了
                    return !mIsKeyCanback;//return true 不往上传递则关闭不了，默认是可以取消，即return false
                } else {
                    return false;
                }
            }
        });
        View view = bindLayout(inflater, container);
        return view;
    }


    protected abstract View bindLayout(LayoutInflater inflater, ViewGroup container);

    //关闭弹出框
    public void hideDialog() {
        try {
            Dialog dialog = getDialog();//没初始化就会出现问题
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
```

```
/**
 * 复杂的DialogFragment样式
 * 可以设置弹出框的宽和高
 *
 * @author yjbo
 * @qq 1457521527
 * @time 2017/4/22 14:18
 */
public abstract class ComplexDialogFrag extends DialogFragment {

    private boolean mIsKeyCanback = true;
    private boolean mIsOutCanback = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //参考 2.https://github.com/shaohui10086/BottomDialog.git；放其他地方就没有这个效果
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);

    }

    public ComplexDialogFrag() {
    }

    /**
     * isKeyCanback 点击物理返回键可以取消
     * isOutCanback 点击除了弹出框其他地方可以取消
     *
     * @author yjbo  @time 2017/4/22 16:26
     */
    public ComplexDialogFrag(boolean isOutCanback, boolean isKeyCanback) {
        mIsKeyCanback = isKeyCanback;
        mIsOutCanback = isOutCanback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(mIsOutCanback);//弹出框外面是否可取消

        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    getDialog().setCanceledOnTouchOutside(mIsKeyCanback);//键盘点击时是否可以取消--不需要设置了
                    return !mIsKeyCanback;//return true 不往上传递则关闭不了，默认是可以取消，即return false
                } else {
                    return false;
                }
            }
        });
        View view = bindLayout(inflater, container);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //获取手机屏幕的长和宽
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        //这个设置宽高的必须放在onstart方法里，不能放oncreateview里面
        Window dialogWindow = getDialog().getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);// 布局文件居中
//        dialogWindow.setLayout(lp.MATCH_PARENT, lp.WRAP_CONTENT);// 为了让对话框宽度铺满
        //alpha在0.0f到1.0f之间。1.0完全不透明，0.0f完全透明，自身不可见。
        //设置弹窗的宽度，
        lp.width = width - getResources().getDimensionPixelSize(R.dimen.dialogfrag_margin);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.alpha=0.5f;
        dialogWindow.setAttributes(lp);
    }

    protected abstract View bindLayout(LayoutInflater inflater, ViewGroup container);

    //关闭弹出框
    public void hideDialog() {
        try {
            Dialog dialog = getDialog();//没初始化就会出现问题
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
```



****欢迎star****

源码：[点击下载](https://github.com/hytcyjb/DialogFragUtil.git)
