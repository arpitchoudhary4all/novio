package amigo.atom.team.amigo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

import amigo.atom.team.amigo.R;
import amigo.atom.team.amigo.modal.User;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Context context;
   private final View view;
   private ArrayList<User> list;
   boolean notFirstTime;
 
 
   public CustomInfoWindowAdapter(Context context, ArrayList list) {
       this.context = context;
       this.list = list;
       this.view = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_map_info_window, null);
   }

   @Override
   public View getInfoContents(final Marker marker) {
//       int position = Integer.parseInt(marker.getSnippet());

       //Todo your code here

       return view;
   }

   @Override
   public View getInfoWindow(Marker marker) {
       // TODO Auto-generated method stub
       return null;
   }
 
   }