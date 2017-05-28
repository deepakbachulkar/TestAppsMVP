package com.deepak.testapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.deepak.testapp.model.User;
import com.deepak.testapp.recyclerview.Intermediary;
import com.deepak.testapp.recyclerview.RecyclerViewHeaderFooterAdapter;
import com.deepak.testapp.utils.AppPreferences;
import com.deepak.testapp.utils.Storage;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView
{
    private ProgressBar progressBar;
    private MainPresenter presenter;
    private TextView txtError;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private List<User> mData= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        presenter = new MainPresenterImpl(this);
        progressBar= (ProgressBar) findViewById(R.id.progress);
        txtError = (TextView) findViewById(R.id.txtError);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        presenter.requestCall(getApplicationContext());
        presenter.versionCheck(getApplicationContext());
    }

    @Override
    public void requestPermission(boolean isPermission)
    {
        if(!isPermission){
            updatedDataToUI();
        }else{
            accessPermission();
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void error(String reason) {
        txtError.setVisibility(View.VISIBLE);
        txtError.setText(reason);
        Toast.makeText(getApplicationContext(), reason, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updatedToUI(List<User> data)
    {
        txtError.setVisibility(View.GONE);
        Log.i("", "Data: "+data);
        updatedList(data);
    }

    @Override
    public void versionCheck(boolean isVersion) {
        if(isVersion)
        {
            // If isVersion is true then next Updated version of App.
            // Here To implement SQLIte Code.. Get the data for local stored and add to db.
            //  Sqlite or Realm related  class and interface add in packages.
                             //---&---
            // Another solve for updated code by server API.
        }
        Log.d("","Version Code: "+AppPreferences.getVersion(getApplicationContext())+ " : isVersion: "+isVersion);
//        Toast.makeText(getApplicationContext(), "Version:"+ AppPreferences.getVersion(getApplicationContext()), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int position = (int) view.getTag();

            Toast.makeText(getApplicationContext(), "CLICK ON :=> "
                    + mData.get(position).getUserName() + " : "+mData.get(position).getMobileNo() ,
                    Toast.LENGTH_SHORT).show();
        }
    };

    private void updatedDataToUI()
    {
            List<User> data = null;
            if (Storage.readObject(getApplicationContext()) != null)
                data = (ArrayList<User>) Storage.readObject(getApplicationContext());
            if (data == null || data.size() <= 0)
                presenter.server(getApplicationContext(), "");
            else {
                updatedList(data);
            }
    }

    private void updatedList(List<User> data)
    {
        mData = data;
        Intermediary mIntermediary = new Intermediary(data, onClickListener);
        StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerViewHeaderFooterAdapter
                mAdapter = new RecyclerViewHeaderFooterAdapter(mLayoutManager, mIntermediary);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSIONS_WRITE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        updatedDataToUI();
                } else {
                    accessPermission();
                }
                break;
        }
    }

    final private int PERMISSIONS_WRITE = 4;
    private void accessPermission()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED)
        {
            Activity activity = (Activity) this;
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE);
            } else
            {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_WRITE);
            }
        }
    }
}
