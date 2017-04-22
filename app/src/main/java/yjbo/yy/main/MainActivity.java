package yjbo.yy.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import yjbo.yy.dialogfragutil.ComplexDialogFrag;
import yjbo.yy.dialogfragutil.R;
import yjbo.yy.dialogfragutil.SimpleDialogFrag;
import yjbo.yy.dialogfragutil.ViewHolderUtil;

/**
 * 为了打造一款DialogFragment的统一封装类
 * 参考：1.封装adapter内使用的保存layout的id方法：http://www.cniao5.com/forum/thread/2ac69d820f0611e790dc00163e0230fa
 * 2.https://github.com/shaohui10086/BottomDialog.git
 *
 * @author yjbo
 * @qq 1457521527
 * @time 2017/4/22 13:21
 */
public class MainActivity extends AppCompatActivity {
    private Activity mContext;
    private SimpleDialogFrag simpleDialogFrag;
    private ComplexDialogFrag complexDialogFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().hide();
        setTitle("*一个简单方法类让app统一样式*");
        mContext = this;
        setOnclick();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showSimpDialog();
            }
        }, 2 * 1000);
    }

    //点击事件
    private void setOnclick() {
        //按钮1
        findViewById(R.id.alert_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(mContext)
                        .setTitle("提示")
                        .setMessage("我是alertdialog系统对话框")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setCancelable(false).show();
            }
        });

        //按钮2
        findViewById(R.id.diafr_util_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSimpDialog();
            }
        });

        //按钮3
        findViewById(R.id.diafr_complexe_id).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                complexDialogFrag = new ComplexDialogFrag() {
                    @Override
                    protected View bindLayout(LayoutInflater inflater, ViewGroup container) {
                        View view = inflater.inflate(R.layout.fragment_dialog_text, container);
                        ViewHolderUtil viewHUtil = new ViewHolderUtil(view);
                        viewHUtil.setText(R.id.title, "提示")
                                .setText(R.id.message, "这是一个复杂的弹出框")
                                .setOnClickListener(R.id.pos, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Toast.makeText(MainActivity.this, "你已经确定了", Toast.LENGTH_SHORT).show();
                                        complexDialogFrag.hideDialog();
                                    }
                                })
                                .setVisible(R.id.neu, false)
                                .setVisible(R.id.neg, false);
                        return view;
                    }
                };
                complexDialogFrag.show(getSupportFragmentManager(), null);
            }
        });
    }

    private void showSimpDialog() {
        //在初始化下一个弹出框的时候把上个弹出框先去掉
        if (simpleDialogFrag != null && simpleDialogFrag.isVisible()) {
            simpleDialogFrag.dismiss();
        }
        simpleDialogFrag = new SimpleDialogFrag() {
            @Override
            protected View bindLayout(LayoutInflater inflater, ViewGroup container) {
                View view = inflater.inflate(R.layout.fragment_dialog_text, container);
                final ViewHolderUtil viewHUtil = new ViewHolderUtil(view);
                viewHUtil.setText(R.id.title, "我是title")
                        .setText(R.id.message, "我是message")
                        .setOnClickListener(R.id.neg, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView negTx = viewHUtil.getView(R.id.neg);
                                Toast.makeText(MainActivity.this, "你点击了我==" + negTx.getText(), Toast.LENGTH_SHORT).show();
                                simpleDialogFrag.hideDialog();
                            }
                        })
                        .setOnClickListener(R.id.neu, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView neuTx = viewHUtil.getView(R.id.neu);
                                Toast.makeText(MainActivity.this, "你点击了我==" + neuTx.getText(), Toast.LENGTH_SHORT).show();
                                simpleDialogFrag.hideDialog();
                            }
                        })
                        .setOnClickListener(R.id.pos, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView posTx = viewHUtil.getView(R.id.pos);
                                Toast.makeText(MainActivity.this, "你点击了我==" + posTx.getText(), Toast.LENGTH_SHORT).show();
                                simpleDialogFrag.hideDialog();
                            }
                        });
                return view;
            }
        };

        simpleDialogFrag.show(getSupportFragmentManager(), null);
    }

    Handler mHandler = new Handler();

}
