package ludeng.com.s10_parcel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ludeng.com.s10_parcel.R;

public class MainActivity extends AppCompatActivity {

  /*  private Button button1;
    private Button button2;*/

    Parcel parcel = null;
    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        parcel = Parcel.obtain();

    }

    @OnClick({R.id.button1, R.id.button2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                Log.i("S10_Parcel", "button1");
                parcel.writeString("abc");
                parcel.writeInt(123);
                parcel.writeFloat(1.0f);

                startActivity(new Intent(this,SecondActivity.class));

                break;
            case R.id.button2:
                Log.i("S10_Parcel", "button2");
                parcel.setDataPosition(0);

                String str = parcel.readString();
                int i = parcel.readInt();
                float f = parcel.readFloat();

                Log.i("S10_Parcel", "str = " + str + "  i = " + i + "   f = " + f);
                break;
        }
    }
}
