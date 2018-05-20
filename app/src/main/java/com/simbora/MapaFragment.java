package com.simbora;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AndreLucas on 19/05/2018.
 */

public class MapaFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener {
    private View rootView;
    private LocationListener mLocationListener;
    private GoogleMap googleMap;
    private LocationManager locationManager;
    private Location location;
    private CardView btnViajar;
    private CardView btnPara;
    private CardView btnQuando;
    private TextView paraEdit;
    private TextView quandoEdit;
    private LatLng latlng;
    private Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener date;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanteState) {
        this.rootView = inflater.inflate(R.layout.mapa_fragment, container, false);

        this.btnViajar = this.rootView.findViewById(R.id.btnViajar);
        this.btnPara = this.rootView.findViewById(R.id.btnPara);
        this.btnQuando = this.rootView.findViewById(R.id.btnQuando);
        this.paraEdit = this.rootView.findViewById(R.id.paraEdit);
        this.quandoEdit = this.rootView.findViewById(R.id.quandoEdit);

        this.btnViajar.setOnClickListener(this);
        this.btnPara.setOnClickListener(this);
        this.btnQuando.setOnClickListener(this);

        this.date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        this.locationManager = (LocationManager)
                getActivity().getSystemService(Context.LOCATION_SERVICE);

        return this.rootView;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);

        TimePickerDialog mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                quandoEdit.setText(sdf.format(myCalendar.getTime())+" "+i + ":" + i1);
            }
        }, hour, minute, true);
        mTimePicker.setTitle("Selecionar Hora");
        mTimePicker.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap1) {
        this.googleMap = googleMap1;
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(40.7143528, -74.0059731), 7));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data.getStringExtra("address")!=null) {
            this.paraEdit.setText(data.getStringExtra("address"));
            this.latlng = new LatLng(Float.parseFloat(data.getStringExtra("lat")),Float.parseFloat(data.getStringExtra("lng")));
            this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(this.latlng, 7));
        }
    }

    @Override
    public void onClick(View view) {
        if(R.id.btnViajar==view.getId()){
            if(this.latlng==null || this.quandoEdit.getText().equals("")){
                Toast.makeText(getActivity(),"Informe os outros campos antes",Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getActivity(), AdicionarViagem.class);
                Bundle b = new Bundle();
                b.putSerializable("lat",this.latlng.latitude);
                b.putSerializable("lng",this.latlng.longitude);
                b.putSerializable("data",this.quandoEdit.getText()+"");
                intent.putExtras(b);
                startActivity(intent);
            }
        } else if(R.id.btnPara==view.getId()){
            Intent intent = new Intent(getActivity(), AutoComplete.class);
            startActivityForResult(intent, 1);
        } else if(R.id.btnQuando==view.getId()){
            new DatePickerDialog(getActivity(), date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }
}