package com.example.whiletrue.ui.dumpster;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.whiletrue.R;
import com.example.whiletrue.ui.dumpster.adapter.CloseAdapter;
import com.example.whiletrue.ui.dumpster.adapter.LocationRecyclerViewAdapter;
import com.example.whiletrue.ui.dumpster.model.Cart;
import com.example.whiletrue.ui.dumpster.model.Place;
import com.example.whiletrue.ui.dumpster.model.Top;
import com.example.whiletrue.ui.home.model.TopModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.localization.LocalizationPlugin;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncher;
import com.mapbox.services.android.navigation.ui.v5.NavigationLauncherOptions;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.NavigationRoute;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;


public class DumpsterFragment extends Fragment implements MapboxMap.OnMapClickListener {
    private static final String TAG = "DirectionsActivity";
    View root;
    public static LinearLayout top;
    public static FrameLayout carta;
    public final List<DirectionsRoute> directionsRouteList = new ArrayList<>();

    public static MapView mapView;
    public static MapboxMap mbMap;

    public static FloatingActionButton buttonNavigation;
    public static Dialog dialog;

    public PermissionsManager permissionsManager;
    public static DirectionsRoute currentRoute;
    private static NavigationMapRoute navigationMapRoute;
    RecyclerView recyclerView;

    public static List<Point> points = new ArrayList<>();
    public List<Place> places = new ArrayList<>();
    public List<Cart> carts = new ArrayList<>();

    RecyclerView recyclerView2;
    List<Top> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Mapbox.getInstance(getContext(), getString(R.string.mapbox_access_token));
        root = inflater.inflate(R.layout.fragment_dumpster, container, false);

        top = root.findViewById(R.id.top);
        carta = root.findViewById(R.id.carta);

        recyclerView = root.findViewById(R.id.rv_on_top_of_map);
        mapView = root.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        dialog = new Dialog(getContext());
        dialog.setTitle("Начать путешесвие?");
        dialog.setContentView(R.layout.dialog_view);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onMapReady(@NonNull final MapboxMap mapboxMap) {
                mbMap = mapboxMap;
                addMarkers();

                carts.add(new Cart(0, 10));
                carts.add(new Cart(1, 20));
                carts.add(new Cart(2, 23));
                carts.add(new Cart(3, 5));
                carts.add(new Cart(4, 11));

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
                recyclerView.setLayoutManager(layoutManager);
                LocationRecyclerViewAdapter adapter = new LocationRecyclerViewAdapter(carts, getContext());
                recyclerView.setAdapter(adapter);


                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        localization(mbMap, style);

                        addDestinationIconSymbolLayer(style);
                        enableLocationComponent(style);
                        initMapStuff(style, root);

//                        mapboxMap.addOnMapClickListener(MainActivity.this);

                        startWay(root, getActivity());
                    }
                });
            }
        });

        list = new ArrayList<>();
        list.add(new Top("Ростовская область, Таганрог, Азовская улица", "https://hozmarket24.ru/uploads/all/59/aa/f6/59aaf6175cf2ed5a3255e170251be780/sx-filter__common-thumbnails-MediumWatermark/emkost-dlya-sbora-batareek.jpg", 5, 47.208584, 38.936626));
        list.add(new Top("Ростовская область, Таганрог, Пионерский переулок", "http://lavkaurna.ru/uploads/all/77/41/19/7741193b08a014293b5befb5a1fe3325/sx-filter__common-thumbnails-MediumWatermark/emkost-dlya-sbora-batareek.jpg", 4, 47.24446, 38.914547));
        list.add(new Top("Ростовская область, Таганрог, Кольцовская улица", "https://kddm.kzn.ru/upload/iblock/bdc/MMB_0884.jpg", 5, 47.206584, 38.936626));

        recyclerView2 = root.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView2.setLayoutManager(layoutManager);
        CloseAdapter adapter = new CloseAdapter(list, getContext(), getActivity());
        recyclerView2.setAdapter(adapter);

        ImageView end = root.findViewById(R.id.end);
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DumpsterFragment.carta.setVisibility(View.VISIBLE);
                DumpsterFragment.top.setVisibility(View.GONE);
            }
        });

        buttonNavigation = root.findViewById(R.id.startButton);

        Button button = dialog.findViewById(R.id.b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationLauncherOptions options = NavigationLauncherOptions.builder()
                        .directionsRoute(currentRoute)
                        .shouldSimulateRoute(true)
                        .build();

                NavigationLauncher.startNavigation(getActivity(), options);
            }
        });

        return root;
    }


    //Добавление стилей для маркера по касанию
    private void addDestinationIconSymbolLayer(@NonNull Style loadedMapStyle) {
        loadedMapStyle.addImage("destination-icon-id",
                BitmapFactory.decodeResource(this.getResources(), R.drawable.mapbox_marker_icon_default));
        GeoJsonSource geoJsonSource = new GeoJsonSource("destination-source-id");
        loadedMapStyle.addSource(geoJsonSource);
        SymbolLayer destinationSymbolLayer = new SymbolLayer("destination-symbol-layer-id", "destination-source-id");
        destinationSymbolLayer.withProperties(
                iconImage("destination-icon-id"),
                iconAllowOverlap(true),
                iconIgnorePlacement(true)
        );
        loadedMapStyle.addLayer(destinationSymbolLayer);
    }

    //Обработка кнопки для возврата к местонахождению пользователя
    void initMapStuff(Style style, View root) {
        FloatingActionButton FAB = root.findViewById(R.id.myLocationButton);
        FAB.setColorFilter(Color.argb(255, 255, 255, 255));
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (mbMap.getLocationComponent().getLastKnownLocation() != null) { // Check to ensure coordinates aren't null, probably a better way of doing this...
                    mbMap.animateCamera(com.mapbox.mapboxsdk.camera.CameraUpdateFactory.newLatLngZoom(new LatLng(mbMap.getLocationComponent().getLastKnownLocation().getLatitude(), mbMap.getLocationComponent().getLastKnownLocation().getLongitude()), 16));
                }
            }
        });
    }

    //Показывать точку местопроложения пользователя
    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(@NonNull Style loadedMapStyle) {
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {
            LocationComponent locationComponent = mbMap.getLocationComponent();
            locationComponent.activateLocationComponent(getContext(), loadedMapStyle);
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
        } else {
            permissionsManager = new PermissionsManager(new PermissionsListener() {
                @Override
                public void onExplanationNeeded(List<String> permissionsToExplain) {
                    Toast.makeText(getActivity(), "location not enabled", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionResult(boolean granted) {
                    if (granted) {
                        mbMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                initMapStuff(style, root);
                            }
                        });
                    } else {
                        Toast.makeText(getActivity(), "Location services not allowed", Toast.LENGTH_LONG).show();
                    }
                }
            });
            permissionsManager.requestLocationPermissions(getActivity());
        }
    }

    //Обработка нажатия на карту (добавление маркера+пути)
    @SuppressWarnings({"MissingPermission"})
    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        LocationComponent locationComponent = mbMap.getLocationComponent();

        Point destinationPoint = Point.fromLngLat(point.getLongitude(), point.getLatitude());
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        GeoJsonSource source = mbMap.getStyle().getSourceAs("destination-source-id");
        if (source != null) {
            source.setGeoJson(Feature.fromGeometry(destinationPoint));
        }

        getRoute(originPoint, destinationPoint, getContext());

        buttonNavigation.setEnabled(true);
        buttonNavigation.setBackgroundResource(R.color.purple_200);
        return true;
    }

    public static void way(Point point, Context context) {
        LocationComponent locationComponent = mbMap.getLocationComponent();
        Point originPoint = Point.fromLngLat(locationComponent.getLastKnownLocation().getLongitude(),
                locationComponent.getLastKnownLocation().getLatitude());

        getRoute(originPoint, point, context);

        buttonNavigation.setEnabled(true);
        buttonNavigation.setBackgroundResource(R.color.purple_200);
    }

    //Построение пути от пользователя до маркера
    public static void getRoute(Point origin, Point destination, Context context) {
        NavigationRoute.Builder builder = NavigationRoute.builder(context)
                .accessToken(Mapbox.getAccessToken())
                .origin(origin)
                .destination(destination)
                .profile(DirectionsCriteria.PROFILE_DRIVING);
        for (int i = 0; i < points.size(); i++) {
            builder.addWaypoint(points.get(i));
        }
        builder.build()
                .getRoute(new Callback<DirectionsResponse>() {
                    @Override
                    public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                        Log.d(TAG, "Response code: " + response.code());
                        if (response.body() == null) {
                            Log.e(TAG, "No routes found, make sure you set the right user and access token.");
                            return;
                        } else if (response.body().routes().size() < 1) {
                            Log.e(TAG, "No routes found");
                            return;
                        }

                        currentRoute = response.body().routes().get(0);

                        // Draw the route on the map
                        if (navigationMapRoute != null) {
                            navigationMapRoute.removeRoute();
                        } else {
                            navigationMapRoute = new NavigationMapRoute(null, mapView, mbMap, R.style.NavigationMapRoute);
                        }
                        navigationMapRoute.addRoute(currentRoute);
                    }

                    @Override
                    public void onFailure(Call<DirectionsResponse> call, Throwable throwable) {
                        Log.e(TAG, "Error: " + throwable.getMessage());
                    }
                });

    }

    //Добавление статичных маркеров на карту
    @SuppressLint("ResourceAsColor")
    public void addMarkers() {
//        points.add(Point.fromLngLat(52.596357, 38.924705));
        places.add(new Place(47.208584, 38.936626, "Бумага", "Ростовская область, Таганрог, Азовская улица", R.drawable.ic_combo_g, getContext()));
        places.add(new Place(47.24446, 38.914547, "Батарейки", "Ростовская область, Таганрог, Пионерский переулок", R.drawable.ic_combo_p, getContext()));
        places.add(new Place(47.205055, 38.914890, "Пластик", "Ростовская область, Таганрог, Кольцовская улица", R.drawable.ic_combo_y, getContext()));
    }

    //Локализация
    public void localization(MapboxMap mapboxMap, Style style) {
        LocalizationPlugin localizationPlugin = new LocalizationPlugin(mapView, mapboxMap, style);
        try {
            localizationPlugin.matchMapLanguageWithDeviceDefault();
        } catch (RuntimeException ignored) {
        }
    }

    //Начала пути по навигатору
    public static void startWay(View root, Activity activity) {
        buttonNavigation.setColorFilter(Color.argb(255, 255, 255, 255));
//        buttonNavigation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                boolean simulateRoute = true;
//                Button button = dialog.findViewById(R.id.b);
//                button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        NavigationLauncherOptions options = NavigationLauncherOptions.builder()
//                                .directionsRoute(currentRoute)
//                                .shouldSimulateRoute(simulateRoute)
//                                .build();
//
//                        NavigationLauncher.startNavigation(activity, options);
//                    }
//                });
//                dialog.show();
//            }
//        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    @SuppressWarnings({"MissingPermission"})
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}