package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private String trim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        加载页面布局
        setContentView(R.layout.activity_main);
        init();
//        隐藏标题栏
        getSupportActionBar().hide();

//        2.点击按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// 检查是否获得了权限（Android6.0运行时权限）
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
// 没有获得授权，申请授权
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
// 返回值：
//如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
// 弹窗需要解释为何需要该权限，再次请求授权
                        Toast.makeText(MainActivity.this, "请授权！", Toast.LENGTH_LONG).show();
// 帮跳转到该应用的设置界面，让用户手动授权
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    } else {
// 不需要解释为何需要该权限，直接请求授权
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                } else {
// 已经获得授权，可以打电话
                    call();
                }

            }
        });
    }

    private void call() {
        trim = editText.getText().toString().trim();//trim去空格
        //判断输入是否为空
        if ("".equals(trim)){
            Toast.makeText(this, "请重新输入", Toast.LENGTH_SHORT).show();
            Log.e("TAG", "call: 请重新输入" );
            return;
        }
            Intent intent = new Intent();//创建一个意图类
            intent.setAction(Intent.ACTION_CALL);//实现意图的动作
//                必须写tel，小写必须"tel:"
            intent.setData(Uri.parse("tel:" + trim));//设置拨打电话的数据
            startActivity(intent);//启动

    }

    private void init() {
        button = findViewById(R.id.button);
        editText = findViewById(R.id.editTextTextPersonName);
    }
}