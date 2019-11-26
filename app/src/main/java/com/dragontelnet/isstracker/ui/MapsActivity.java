package com.dragontelnet.isstracker.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dragontelnet.isstracker.R;
import com.dragontelnet.isstracker.adapter.PeopleListAdapter;
import com.dragontelnet.isstracker.models.AstroPeople;
import com.dragontelnet.isstracker.models.IssPosition;
import com.dragontelnet.isstracker.viewmodel.MapsActivityViewModel;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private Handler handler;
    private Marker marker = null;
    private Runnable runnable;
    private ProgressDialog progressDialog;
    private GoogleMap mMap;
    private LatLng issLocLatLng;
    private int delay = 1000; //1 sec

    private Observer<IssPosition> positionObserver;

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

        setProgressDialog();

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
        //refreshing per second
        handler.postDelayed(runnable = new Runnable() {
            public void run() {
                getViewMode().getLocationUpdates().removeObservers(MapsActivity.this);
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                //getting location of ISS,per second
                getLocationUpdates();
            }

        }, delay);

    }

    private void getLocationUpdates()
    {
        positionObserver=new Observer<IssPosition>() {
            @Override
            public void onChanged(IssPosition issPosition) {
                String latitude = issPosition.getLatitude();
                String longitude = issPosition.getLongitude();

                issLocLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));

                //removing previous marker
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
        };
        handler.postDelayed(runnable, delay);

        getViewMode().getLocationUpdates().observe(this,positionObserver);
    }

    private void getAstrosName(PeopleListAdapter listAdapter) {

        getViewMode().getAstrosName().observe(this, new Observer<List<AstroPeople>>() {
            @Override
            public void onChanged(List<AstroPeople> astroPeopleList) {
                listAdapter.onListLoaded(astroPeopleList);
            }
        });

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

        View view=getPeoplesDialogView();

        AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        PeopleListAdapter peopleListAdapter=new PeopleListAdapter();
        getRecyclerView(view).setAdapter(peopleListAdapter);

        getAstrosName(peopleListAdapter);//setting adapter

        builder.setView(view);
        builder.create().show();

    }

    private RecyclerView getRecyclerView(View view) {
        RecyclerView recyclerView=view.findViewById(R.id.people_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
        return recyclerView;
    }

    private View getPeoplesDialogView()
    {
        return getLayoutInflater()
                .inflate(R.layout.show_peoples_dialog,null,false);
    }


    private MapsActivityViewModel getViewMode()
    {
        return ViewModelProviders.of(this).get(MapsActivityViewModel.class);
    }


    private void setProgressDialog()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Getting location,please wait....");
        progressDialog.show();
    }

}
