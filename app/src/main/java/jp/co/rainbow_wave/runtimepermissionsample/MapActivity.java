package jp.co.rainbow_wave.runtimepermissionsample;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapActivity extends Activity
        implements LocationListener {

    private GoogleMap mMap;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        try {
            mMap = mapFragment.getMap();
            if (savedInstanceState == null) {

                mapFragment.setRetainInstance(true);

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);

                // 東京駅中心
                CameraPosition cameraPosition =
                        new CameraPosition.Builder()
                                .target(new LatLng(35.681382, 139.766084))
                                .zoom(15)
                                .build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                mLocationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        2000,
                        1,
                        this);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        CameraPosition cameraPosition =
                new CameraPosition.Builder()
                        .target(new LatLng(
                                location.getLatitude(),
                                location.getLongitude()
                        ))
                        .zoom(15)
                        .build();
        // カメラ移動
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mLocationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
