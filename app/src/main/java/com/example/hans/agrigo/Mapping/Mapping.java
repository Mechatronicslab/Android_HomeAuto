package com.example.hans.agrigo.Mapping;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.hans.agrigo.R;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.geocoding.v5.GeocodingCriteria;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.Property.NONE;
import static com.mapbox.mapboxsdk.style.layers.Property.VISIBLE;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;

public class Mapping extends AppCompatActivity implements
        PermissionsListener, OnMapReadyCallback {

    private MapView mapView;
    private MapboxMap mapboxMap;
    private Button btnSelectLocation;
    private PermissionsManager permissionsManager;
    private ImageView hoveringMarker;
    private Layer droppedMarkerLayer;
    private static final String DROPPED_MARKER_LAYER_ID = "DROPPED_MARKER_LAYER_ID";
    private static final String SOURCE_ID = "SOURCE_ID";
    private static final String ICON_ID = "ICON_ID";
    private static final String LAYER_ID = "LAYER_ID";
    private static final String TAG = "SimplifyLineActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Mapbox.getInstance( this, "pk.eyJ1IjoiaGFuZG9rb2JpbWF3ZGoiLCJhIjoiY2s0aHczaHp3MG9sdzNta2R0NnMweG1sbiJ9.qz9a4nD-hyHUNQx1Qgdkew" );
        setContentView( R.layout.activity_mapping );
        setTheme( R.style.CustomInstructionView );

        mapView = findViewById( R.id.mapView );
        mapView.onCreate( savedInstanceState );
        mapView.getMapAsync( this );
    }

    @Override
    public void onMapReady(@NonNull final MapboxMap mapboxMap) {
        Mapping.this.mapboxMap = mapboxMap;
        mapboxMap.setStyle( Style.MAPBOX_STREETS,
                new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull final Style style) {
                        enableLocationPlugin( style );
                        Toast.makeText(
                                Mapping.this,
                                getString( R.string.move_map_instruction ), Toast.LENGTH_SHORT ).show();

                        hoveringMarker = new ImageView( Mapping.this );
                        hoveringMarker.setImageResource( R.drawable.red_marker );
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER );
                        hoveringMarker.setLayoutParams( params );
                        mapView.addView( hoveringMarker );
                        initDroppedMarker( style );
                        btnSelectLocation = findViewById( R.id.select_location_button );
                        btnSelectLocation.setOnClickListener( new View.OnClickListener() {
                            @SuppressLint("WrongConstant")
                            @Override
                            public void onClick(View view) {
                                if (hoveringMarker.getVisibility() == View.VISIBLE) {
                                    final LatLng mapTargetLatLng = mapboxMap.getCameraPosition().target;
                                    hoveringMarker.setVisibility( View.INVISIBLE );
                                    btnSelectLocation.setBackgroundColor(
                                            ContextCompat.getColor( Mapping.this, R.color.colorAccent ) );
                                    btnSelectLocation.setText( getString( R.string.location_picker_select_location_button_cancel ) );

// Show the SymbolLayer icon to represent the selected map location
                                    if (style.getLayer( DROPPED_MARKER_LAYER_ID ) != null) {
                                        GeoJsonSource source = style.getSourceAs( "dropped-marker-source-id" );
                                        if (source != null) {
                                            source.setGeoJson( Point.fromLngLat( mapTargetLatLng.getLongitude(), mapTargetLatLng.getLatitude() ) );
                                        }
                                        droppedMarkerLayer = style.getLayer( DROPPED_MARKER_LAYER_ID );
                                        if (droppedMarkerLayer != null) {
                                            droppedMarkerLayer.setProperties( visibility(  VISIBLE ) );
                                        }
                                    }
                                    reverseGeocode( Point.fromLngLat( mapTargetLatLng.getLongitude(),
                                            mapTargetLatLng.getLatitude() ) );
                                } else {
                                    btnSelectLocation.setBackgroundColor(
                                            ContextCompat.getColor( Mapping.this, R.color.colorPrimary ) );
                                    btnSelectLocation.setText( getString( R.string.location_picker_select_location_button_select ) );

// Show the red hovering ImageView marker
                                    hoveringMarker.setVisibility( View.VISIBLE );

// Hide the selected location SymbolLayer
                                    droppedMarkerLayer = style.getLayer( DROPPED_MARKER_LAYER_ID );
                                    if (droppedMarkerLayer != null) {
                                        droppedMarkerLayer.setProperties( visibility( NONE ) );
                                    }
                                }
                            }
                        } );
                    }
                } );
    }

    private void initDroppedMarker(@NonNull Style loadedMapStyle) {
// Add the marker image to map
        loadedMapStyle.addImage( "dropped-icon-image",getBitmap() );
        loadedMapStyle.addSource( new GeoJsonSource( "dropped-marker-source-id" ) );
        loadedMapStyle.addLayer( new SymbolLayer( DROPPED_MARKER_LAYER_ID,
                "dropped-marker-source-id" ).withProperties(
                iconImage( "dropped-icon-image" ),
                visibility( NONE  ),
                iconAllowOverlap( true ),
                iconIgnorePlacement( true )
        ) );
    }

    public Bitmap getBitmap() {
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.blue_marker, null);
        Bitmap mBitmap = BitmapUtils.getBitmapFromDrawable(drawable);
        return mBitmap;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        mapView.onSaveInstanceState( outState );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult( requestCode, permissions, grantResults );
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText( this, R.string.user_location_permission_explanation, Toast.LENGTH_LONG ).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted && mapboxMap != null) {
            Style style = mapboxMap.getStyle();
            if (style != null) {
                enableLocationPlugin( style );
            }
        } else {
            Toast.makeText( this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG ).show();
            finish();
        }
    }

    private void reverseGeocode(final Point point) {
        try {
            MapboxGeocoding client = MapboxGeocoding.builder()
                    .accessToken( getString( R.string.access_token ) )
                    .query( Point.fromLngLat( point.longitude(), point.latitude() ) )
                    .geocodingTypes( GeocodingCriteria.TYPE_ADDRESS )
                    .build();

            client.enqueueCall( new Callback<GeocodingResponse>() {
                @Override
                public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {

                    if (response.body() != null) {
                        List<CarmenFeature> results = response.body().features();
                        if (results.size() > 0) {
                            CarmenFeature feature = results.get( 0 );
                            mapboxMap.getStyle( new Style.OnStyleLoaded() {
                                @Override
                                public void onStyleLoaded(@NonNull Style style) {
                                    if (style.getLayer( DROPPED_MARKER_LAYER_ID ) != null) {
                                        Toast.makeText( Mapping.this,
                                                String.format( getString( R.string.location_picker_place_name_result ),
                                                        feature.placeName() ), Toast.LENGTH_SHORT ).show();
                                    }
                                }
                            } );

                        } else {
                            Toast.makeText( Mapping.this,
                                    getString( R.string.location_picker_dropped_marker_snippet_no_results ), Toast.LENGTH_SHORT ).show();
                        }
                    }
                }
                @Override
                public void onFailure(Call<GeocodingResponse> call, Throwable throwable) {
                    Timber.e("Geocoding Failure: %s", throwable.getMessage());
                }
            });
        } catch (ServicesException servicesException) {
            Timber.e("Error geocoding: %s", servicesException.toString());
            servicesException.printStackTrace();
        }
    }
    @SuppressWarnings( {"MissingPermission"})
    private void enableLocationPlugin(@NonNull Style loadedMapStyle) {
// Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component. Adding in LocationComponentOptions is also an optional
// parameter
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent( LocationComponentActivationOptions.builder(
                    this, loadedMapStyle).build());
            locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
            locationComponent.setCameraMode( CameraMode.TRACKING);
            locationComponent.setRenderMode( RenderMode.NORMAL);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }
}