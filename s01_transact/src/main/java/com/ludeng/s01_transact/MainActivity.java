package com.ludeng.s01_transact;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


    @BindView(R.id.buttonPanel1)
    Button buttonPanel1;
    @BindView(R.id.buttonPanel2)
    Button buttonPanel2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.buttonPanel1, R.id.buttonPanel2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonPanel1:
                Intent intent = new Intent(MainActivity.this,SencondService.class );
                bindService(intent,conn,BIND_AUTO_CREATE);
                break;
            case R.id.buttonPanel2:
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                data.writeString("data from activity");

                try {
                    binder.transact(0,data,reply,0);
                    String str = reply.readString();
                    Log.i("S10_Transact","--"+str);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }

                unbindService(conn);
                break;
        }
    }



    private Binder binder;

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MainActivity.this.binder = (Binder)iBinder;
            Log.i("S10_Transact","绑定成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
