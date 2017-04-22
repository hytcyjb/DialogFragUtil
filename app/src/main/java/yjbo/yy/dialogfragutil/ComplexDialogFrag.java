package yjbo.yy.dialogfragutil;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

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
