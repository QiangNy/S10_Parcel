package ludeng.com.s10_parcel.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ludeng.com.s10_parcel.R;
import ludeng.com.s10_parcel.utils.DbManager;
import ludeng.com.s10_parcel.utils.LoginInfo;

/**
 * Created by chen on 16-10-22.
 */
public class SecondActivity extends Activity {

    @BindView(R.id.write_db)
    Button writeDb;
    @BindView(R.id.read_db)
    Button readDb;
    @BindView(R.id.write_content)
    TextView writeContent;
    @BindView(R.id.read_context)
    TextView readContext;
    @BindView(R.id.update_db)
    Button updateDb;
    @BindView(R.id.delet_db)
    Button deletDb;

    private DbManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        dbManager = new DbManager(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @OnClick({R.id.write_db, R.id.read_db, R.id.update_db,R.id.delet_db})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.write_db:
                writeContentToDataBase();
                break;
            case R.id.read_db:
                showContentDataBase();
                break;
            case R.id.update_db:
                updateDataBase();
                break;
            case R.id.delet_db:
                deleteDatabase();
                break;
        }
    }

    private void deleteDatabase() {
        dbManager.deletOldLogin(new LoginInfo("chen", "1z2x3c4v5b"));
    }

    private void showContentDataBase() {
        List<LoginInfo> mList = dbManager.query();
        StringBuffer sb = new StringBuffer();
        for (LoginInfo loginInfo : mList) {
            sb.append(loginInfo.name + "," + loginInfo.passWd);
        }
        readContext.setText(sb.toString());
    }

    private void writeContentToDataBase() {
        dbManager.add(new LoginInfo("test", "1234560"));
        dbManager.add(new LoginInfo("chen", "1z2x3c4v5b"));
    }

    private void updateDataBase() {
        dbManager.updatePasswd(new LoginInfo("test", "qqqwwweee"));
    }

}
