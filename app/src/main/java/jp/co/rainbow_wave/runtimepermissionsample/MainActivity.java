package jp.co.rainbow_wave.runtimepermissionsample;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_MAP_ACCESS = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_map).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // マップを表示するのに必要な権限が付与されているか確認する
                int permLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
                int permStorage  = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (permLocation != PackageManager.PERMISSION_GRANTED &&
                        permStorage != PackageManager.PERMISSION_GRANTED) {
                    // 2つまとめて指定
                    requestPermissions(
                            new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_MAP_ACCESS
                    );
                } else if (permLocation != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_MAP_ACCESS
                    );
                } else if (permStorage != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_MAP_ACCESS
                    );
                } else {
                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MAP_ACCESS:
                boolean grantedResult = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        grantedResult = false;
                        break;
                    }
                }
                if (grantedResult) {
                    // OK : マップ表示
                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                } else {
                    // NG : エラー表示などを行う
                    Toast.makeText(this, "NG : 権限不足のためアクセスできません", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
}
