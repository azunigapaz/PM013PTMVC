package com.grupoadec.pm013ptmvc.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
import com.grupoadec.pm013ptmvc.R;

public class ActionsEmpleados extends AppCompatActivity {
    String parPeIdEmpleado,parPeNombreEmpleado,parPeApellidoEmpleado,parPeEdadEmpleado,parPePuestoEmpleado,parPeDireccionEmpleado;
    EditText etnombresEdicion,etapellidosEdicion,etedadEdicion,etpuestoEdicion,etdireccionEdicion;

    Button btneditarempleado;

    ImageView ivbackle;

    SQLiteHelper objectConexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions_empleados);

        try{
            etnombresEdicion = findViewById(R.id.etnombresEdicion);
            etapellidosEdicion = findViewById(R.id.etapellidosEdicion);
            etedadEdicion = findViewById(R.id.etedadEdicion);
            etpuestoEdicion = findViewById(R.id.etpuestoEdicion);
            etdireccionEdicion = findViewById(R.id.etdireccionEdicion);
            btneditarempleado = findViewById(R.id.btneditarempleado);
            ivbackle = findViewById(R.id.ivbackle);

            objectConexion = new SQLiteHelper(this, Transactions.NameDatabase,null,1);

            parPeIdEmpleado = getIntent().getStringExtra("iptEmpleadoId");
            parPeNombreEmpleado = getIntent().getStringExtra("iptNombre");
            parPeApellidoEmpleado = getIntent().getStringExtra("iptApellido");
            parPeEdadEmpleado = getIntent().getStringExtra("iptEdad");
            parPePuestoEmpleado = getIntent().getStringExtra("iptPuesto");
            parPeDireccionEmpleado = getIntent().getStringExtra("iptDireccion");

            etnombresEdicion.setText(parPeNombreEmpleado);
            etapellidosEdicion.setText(parPeApellidoEmpleado);
            etedadEdicion.setText(parPeEdadEmpleado);
            etpuestoEdicion.setText(parPePuestoEmpleado);
            etdireccionEdicion.setText(parPeDireccionEmpleado);

            btneditarempleado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActualizarEmpleado();
                }
            });

            ivbackle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent objectIntent = new Intent(getApplicationContext(),ViewEmpleados.class);
                    startActivity(objectIntent);
                    finish();
                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void ActualizarEmpleado() {
        try{
            SQLiteDatabase objectSqLiteDatabase = objectConexion.getWritableDatabase();

            String [] params = { parPeIdEmpleado };

            ContentValues objectContentValuesEditEmpleado = new ContentValues();
            objectContentValuesEditEmpleado.put(Transactions.NombreEmpleado, etnombresEdicion.getText().toString());
            objectContentValuesEditEmpleado.put(Transactions.ApellidoEmpleado, etapellidosEdicion.getText().toString());
            objectContentValuesEditEmpleado.put(Transactions.EdadEmpleado, etedadEdicion.getText().toString());
            objectContentValuesEditEmpleado.put(Transactions.PuestoEmpleado, etpuestoEdicion.getText().toString());
            objectContentValuesEditEmpleado.put(Transactions.DireccionEmpleado, etdireccionEdicion.getText().toString());

            objectSqLiteDatabase.update(Transactions.TablaEmpleados, objectContentValuesEditEmpleado, Transactions.IdEmpleado + "=?", params);

            objectSqLiteDatabase.close();

            Intent intent = new Intent(getApplicationContext(),ViewEmpleados.class);
            startActivity(intent);
            finish();

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}