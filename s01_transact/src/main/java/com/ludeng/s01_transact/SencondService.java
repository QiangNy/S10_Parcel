package com.ludeng.s01_transact;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by chen on 16-10-13.
 */
public class SencondService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

         return new MyBinder();

    }

    class MyBinder extends Binder {
        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            String str = data.readString();
            Log.i("S10_Transact", "--" +str);

            reply.writeString("data from service reply");
            return super.onTransact(code, data, reply, flags);
        }
    }
}
