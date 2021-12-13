package com.grupoadec.pm013ptmvc.Views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.grupoadec.pm013ptmvc.Adapters.ListaEmpleadosAdapter;
import com.grupoadec.pm013ptmvc.Controllers.SQLiteHelper;
import com.grupoadec.pm013ptmvc.Controllers.Transactions;
import com.grupoadec.pm013ptmvc.MainActivity;
import com.grupoadec.pm013ptmvc.Models.Empleado;
import com.grupoadec.pm013ptmvc.R;

import java.util.ArrayList;

public class ViewEmpleados extends AppCompatActivity {

    SQLiteHelper objectSqLiteConexion;
    ListView objectListViewConsultaEmpleados;
    ArrayList<Empleado> objectArrayListTablaEmpleadosLista = new ArrayList<>();
    Empleado objectTablaEmpeladosListaEmpleados = null;
    EditText etbuscarce;
    ImageView ivbackce;
    ListaEmpleadosAdapter objectAdapter;

    String [] objectListItem;
    AlertDialog.Builder objectAlertDialogBuilderOpciones;

    String idEmpleado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_empleados);

        try{
            objectSqLiteConexion = new SQLiteHelper(this, Transactions.NameDatabase, null, 1);

            objectListViewConsultaEmpleados = findViewById(R.id.lvlistaempleados);
            etbuscarce = findViewById(R.id.etbuscarce);
            ivbackce = findViewById(R.id.ivbackce);

            objectAlertDialogBuilderOpciones = new AlertDialog.Builder(this);

            ObtenerListaEmpleados();

            objectAdapter = new ListaEmpleadosAdapter(this,objectArrayListTablaEmpleadosLista);
            objectListViewConsultaEmpleados.setAdapter(objectAdapter);

            etbuscarce.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    objectAdapter.filtrarEmpleadoNombreApellido(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            ivbackce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(objectIntent);
                    finish();
                }
            });

            objectListViewConsultaEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    try {
                        objectListItem = new String[]{"Editar","Eliminar"};
                        objectAlertDialogBuilderOpciones.setTitle("Seleccione un opcion");
                        objectAlertDialogBuilderOpciones.setSingleChoiceItems(objectListItem, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(objectListItem[i] == "Editar"){

                                    Empleado empleadoSelected = objectArrayListTablaEmpleadosLista.get(position);

                                    idEmpleado = empleadoSelected.getId().toString();

                                    Intent objectIntent = new Intent(getApplicationContext(),ActionsEmpleados.class);
                                    objectIntent.putExtra("iptEmpleadoId", idEmpleado);
                                    objectIntent.putExtra("iptNombre", empleadoSelected.getNombre());
                                    objectIntent.putExtra("iptApellido", empleadoSelected.getApellido());
                                    objectIntent.putExtra("iptEdad", empleadoSelected.getEdad().toString());
                                    objectIntent.putExtra("iptPuesto", empleadoSelected.getPuesto());
                                    objectIntent.putExtra("iptDireccion", empleadoSelected.getDireccion());

                                    startActivity(objectIntent);
                                    finish();

                                }
                                if(objectListItem[i] == "Eliminar"){
                                    Empleado empleadoSelected = objectArrayListTablaEmpleadosLista.get(position);
                                    idEmpleado = empleadoSelected.getId().toString();

                                    EliminarEmpleado(idEmpleado);
                                    dialogInterface.dismiss();

                                }
                            }
                        });
                        objectAlertDialogBuilderOpciones.setNeutralButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        objectAlertDialogBuilderOpciones.create();
                        objectAlertDialogBuilderOpciones.show();

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void ObtenerListaEmpleados() {
        try{

            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getReadableDatabase();

            Cursor objectCursor = objectSqLiteDatabase.rawQuery("SELECT * FROM " + Transactions.TablaEmpleados, null);

            while (objectCursor.moveToNext()){
                objectTablaEmpeladosListaEmpleados = new Empleado();
                objectTablaEmpeladosListaEmpleados.setId(objectCursor.getInt(0));
                objectTablaEmpeladosListaEmpleados.setNombre(objectCursor.getString(1));
                objectTablaEmpeladosListaEmpleados.setApellido(objectCursor.getString(2));
                objectTablaEmpeladosListaEmpleados.setEdad(objectCursor.getInt(3));
                objectTablaEmpeladosListaEmpleados.setDireccion(objectCursor.getString(4));
                objectTablaEmpeladosListaEmpleados.setPuesto(objectCursor.getString(5));

                objectArrayListTablaEmpleadosLista.add(objectTablaEmpeladosListaEmpleados);
            }

            objectCursor.close();
            objectSqLiteConexion.close();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void EliminarEmpleado(String parIdEmpleado) {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectSqLiteConexion.getWritableDatabase();

            String [] params = { parIdEmpleado };
            String wherecond = Transactions.IdEmpleado + "=?";

            objectSqLiteDatabase.delete(Transactions.TablaEmpleados, wherecond, params);

            objectSqLiteDatabase.close();

            Toast.makeText(getApplicationContext(), "Ha eliminado el empleado: " + parIdEmpleado, Toast.LENGTH_SHORT).show();

            objectArrayListTablaEmpleadosLista.clear();
            ObtenerListaEmpleados();
            objectAdapter = new ListaEmpleadosAdapter(this,objectArrayListTablaEmpleadosLista);
            objectListViewConsultaEmpleados.setAdapter(objectAdapter);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}