package yjbo.yy.dialogfragutil;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

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
