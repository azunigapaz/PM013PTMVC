package com.grupoadec.pm013ptmvc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.grupoadec.pm013ptmvc.Controllers.SQLiteHelper;
import com.grupoadec.pm013ptmvc.Controllers.Transactions;
import com.grupoadec.pm013ptmvc.Views.ViewEmpleados;

public class MainActivity extends AppCompatActivity {
    ImageView ivsalir;
    EditText etnombres,etapellidos,etedad,etpuesto,etdireccion;
    Button btnagregarpersona,btnlistapersonas;

    SQLiteHelper conexion;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            ivsalir = findViewById(R.id.ivsalir);
            etnombres = findViewById(R.id.etnombres);
            etapellidos = findViewById(R.id.etapellidos);
            etedad = findViewById(R.id.etedad);
            etpuesto = findViewById(R.id.etpuesto);
            etdireccion = findViewById(R.id.etdireccion);
            btnagregarpersona = findViewById(R.id.btnagregarpersona);
            btnlistapersonas = findViewById(R.id.btnlistapersonas);

            conexion = new SQLiteHelper(this, Transactions.NameDatabase,null,1);
            builder = new AlertDialog.Builder(this);

            ivsalir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finishAndRemoveTask();
                }
            });

            btnagregarpersona.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(etnombres.length()>0 && etapellidos.length()>0 && etedad.length()>0 && etpuesto.length()>0 && etdireccion.length()>0){
                            builder.setMessage("Desea registrar el empleado: " + etnombres.getText() + " " + etapellidos.getText() + " ?")
                                    .setCancelable(false)
                                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            GuardarEmpleado();
                                            Toast.makeText(getApplicationContext(),"Empleado registrado",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //  Action for 'NO' Button
                                            dialog.cancel();
                                            Toast.makeText(getApplicationContext(),"No se registro el empleado",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    });
                            //Creating dialog box
                            AlertDialog alert = builder.create();
                            //Setting the title manually
                            alert.setTitle("Alerta");
                            alert.show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Todos los campos son obligatorios ",Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

            btnlistapersonas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ViewEmpleados.class);
                    startActivity(intent);
                    finish();
                }
            });

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void GuardarEmpleado() {

        try{
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues objectContentValuesGuardarPersona = new ContentValues();
            objectContentValuesGuardarPersona.put(Transactions.NombreEmpleado, etnombres.getText().toString());
            objectContentValuesGuardarPersona.put(Transactions.ApellidoEmpleado, etapellidos.getText().toString());
            objectContentValuesGuardarPersona.put(Transactions.EdadEmpleado, etedad.getText().toString());
            objectContentValuesGuardarPersona.put(Transactions.PuestoEmpleado, etpuesto.getText().toString());
            objectContentValuesGuardarPersona.put(Transactions.DireccionEmpleado, etdireccion.getText().toString());

            Long resultado = db.insert(Transactions.TablaEmpleados,Transactions.IdEmpleado, objectContentValuesGuardarPersona);

            if(resultado != -1){
                Toast.makeText(getApplicationContext(),"Empleado registrado, id: " + resultado.toString(),Toast.LENGTH_SHORT).show();
                db.close();
                LimpiarPantalla();
            }else{
                Toast.makeText(getApplicationContext(),"El empleado no pudo ser registrado en la DB ",Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void LimpiarPantalla() {
        try{
            etnombres.setText("");
            etapellidos.setText("");
            etedad.setText("");
            etpuesto.setText("");
            etdireccion.setText("");
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
}