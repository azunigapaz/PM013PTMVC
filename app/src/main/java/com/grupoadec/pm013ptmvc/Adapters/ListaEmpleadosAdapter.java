package com.grupoadec.pm013ptmvc.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.grupoadec.pm013ptmvc.Models.Empleado;
import com.grupoadec.pm013ptmvc.R;
import java.util.ArrayList;

public class ListaEmpleadosAdapter extends BaseAdapter {
    private static LayoutInflater objectInflater = null;
    Context objectContext;
    ArrayList<Empleado> objectArrayListTablaEmpleados;
    ArrayList<Empleado> objectArrayListTablaEmpleadosOriginal;

    public ListaEmpleadosAdapter(Context objectContext, ArrayList<Empleado> objectArrayListTablaEmpleados) {
        try{
            this.objectContext = objectContext;
            this.objectArrayListTablaEmpleados = objectArrayListTablaEmpleados;

            objectArrayListTablaEmpleadosOriginal = new ArrayList<>();
            objectArrayListTablaEmpleadosOriginal.addAll(objectArrayListTablaEmpleados);

            objectInflater = (LayoutInflater)objectContext.getSystemService(objectContext.LAYOUT_INFLATER_SERVICE);
        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final View objectView = objectInflater.inflate(R.layout.custom_view_consulta_empleados,null);

        try{
            TextView tvNombreCVCE = objectView.findViewById(R.id.tvNombreCVCE);
            TextView tvPuestoCVCE = objectView.findViewById(R.id.tvPuestoCVCE);
            TextView tvEdadCVCE = objectView.findViewById(R.id.tvEdadCVCE);
            TextView tvDireccionCVCE = objectView.findViewById(R.id.tvDireccionCVCE);

            tvNombreCVCE.setText(objectArrayListTablaEmpleados.get(i).getNombre() + " " + objectArrayListTablaEmpleados.get(i).getApellido());
            tvPuestoCVCE.setText(objectArrayListTablaEmpleados.get(i).getPuesto());
            tvEdadCVCE.setText(objectArrayListTablaEmpleados.get(i).getEdad().toString());
            tvDireccionCVCE.setText(objectArrayListTablaEmpleados.get(i).getDireccion());

        }catch (Exception e){
            Toast.makeText(objectContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return objectView;
    }

    @Override
    public int getCount() {

        return objectArrayListTablaEmpleados.size();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public void filtrarEmpleadoNombreApellido(final CharSequence txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud > 0) {
            objectArrayListTablaEmpleados.clear();
            for(int i = 0; i < objectArrayListTablaEmpleadosOriginal.size(); i++){
                if(objectArrayListTablaEmpleadosOriginal.get(i).getNombre().toLowerCase().contains(txtBuscar.toString().toLowerCase()) || objectArrayListTablaEmpleadosOriginal.get(i).getApellido().toLowerCase().contains(txtBuscar.toString().toLowerCase())){
                    objectArrayListTablaEmpleados.add(objectArrayListTablaEmpleadosOriginal.get(i));
                }
            }
            notifyDataSetChanged();
        }else{
            objectArrayListTablaEmpleados.clear();
            objectArrayListTablaEmpleados.addAll(objectArrayListTablaEmpleadosOriginal);
            notifyDataSetChanged();
        }
    }

}
