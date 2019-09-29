package com.dragontelnet.isstracker.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dragontelnet.isstracker.R;
import com.dragontelnet.isstracker.adapter.PeopleListAdapter;
import com.dragontelnet.isstracker.models.AstroPeople;
import com.dragontelnet.isstracker.models.AstrosInSpace;
import com.dragontelnet.isstracker.models.IssInfo;
import com.dragontelnet.isstracker.models.IssPosition;
import com.dragontelnet.isstracker.service.GetIssInfo;
import com.dragontelnet.isstracker.service.RetrofitInstance;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private Handler handler;
    private Marker marker = null;
    private Runnable runnable;
    private ProgressDialog progressDialog;
    private GoogleMap mMap;
    private LatLng issLocLatLng;
    private int delay = 1000; //1 sec
    private List<AstroPeople> astroPeopleList;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.stop)
    Button stop;
    @BindView(R.id.peoples_space_btn)
    Button peoplesSpaceBtn;
    private static final String TAG = "MapsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        handler = new Handler();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Getting location,please wait....");
        progressDialog.show();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        start.setEnabled(false);
        start.setTextColor(getResources().getColor(android.R.color.darker_gray));
        mMap = googleMap;
        GetIssInfo getIssInfo = RetrofitInstance.getRetrofitInstance();

        //refreshing per second
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //getting location of ISS,per second
                getLocation(getIssInfo);
            }

        }, delay);

    }

    private void getAstrosName(PeopleListAdapter listAdapter) {
        GetIssInfo getIssInfo = RetrofitInstance.getRetrofitInstance();
        Call<AstrosInSpace> call = getIssInfo.getAstrosInSpace();
        call.enqueue(new Callback<AstrosInSpace>() {
            @Override
            public void onResponse(Call<AstrosInSpace> call, Response<AstrosInSpace> response) {
                if (response.isSuccessful() && response.body()!=null) {

                    astroPeopleList = response.body().getAstroPeopleList();
                    listAdapter.onListLoaded(astroPeopleList);

                }
            }

            @Override
            public void onFailure(Call<AstrosInSpace> call, Throwable t) {

            }
        });

    }


    private void getLocation(GetIssInfo getIssInfo) {

        Call<IssInfo> call = getIssInfo.getIssInfo();
        call.enqueue(new Callback<IssInfo>() {
            @Override
            public void onResponse(Call<IssInfo> call, Response<IssInfo> response) {

                IssPosition issPosition = response.body().getIssPosition();
                String latitude = issPosition.getLatitude();
                String longitude = issPosition.getLongitude();

                issLocLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                if (marker != null) {
                    marker.remove();
                    marker.hideInfoWindow();
                }
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(issLocLatLng)
                        .draggable(true)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.sat))
                        .title("ISS");
                marker = mMap.addMarker(markerOptions);
                marker.setPosition(issLocLatLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(issLocLatLng));
                marker.showInfoWindow();

            }

            @Override
            public void onFailure(Call<IssInfo> call, Throwable t) {

            }
        });

        handler.postDelayed(runnable, delay);

    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @OnClick(R.id.start)
    public void onStartClicked() {
        handler.postDelayed(runnable, delay);
        Toast.makeText(MapsActivity.this, "Tracking STARTED..", Toast.LENGTH_SHORT).show();
        start.setEnabled(false);
        stop.setEnabled(true);
        start.setTextColor(getResources().getColor(android.R.color.darker_gray));
        stop.setTextColor(getResources().getColor(android.R.color.holo_red_light));

    }

    @OnClick(R.id.stop)
    public void onStopClicked() {
        handler.removeCallbacks(runnable);
        Toast.makeText(MapsActivity.this, "Tracking STOPPED", Toast.LENGTH_SHORT).show();
        stop.setEnabled(false);
        start.setEnabled(true);
        stop.setTextColor(getResources().getColor(android.R.color.darker_gray));
        start.setTextColor(getResources().getColor(android.R.color.white));

    }


    @OnClick(R.id.peoples_space_btn)
    public void onShowPeopleBtn() {
        View view=getLayoutInflater()
                .inflate(R.layout.show_peoples_dialog,null,false);

        AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        RecyclerView recyclerView=view.findViewById(R.id.people_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
        PeopleListAdapter peopleListAdapter=new PeopleListAdapter();
        recyclerView.setAdapter(peopleListAdapter);
        getAstrosName(peopleListAdapter);
        builder.setView(view);
        builder.create().show();

    }
}
