package com.grupoadec.pm013ptmvc.Controllers;

public class Transactions {
    public static final String NameDatabase = "EmpleadosDB";

    public static final String TablaEmpleados = "TblEmpleados";

    public static final String IdEmpleado = "IdEmpleado";
    public static final String NombreEmpleado = "NombreEmpleado";
    public static final String ApellidoEmpleado = "ApellidoEmpleado";
    public static final String EdadEmpleado = "EdadEmpleado";
    public static final String DireccionEmpleado = "DireccionEmpleado";
    public static final String PuestoEmpleado = "PuestoEmpleado";

    public static final String CreateTableEmpleados = "CREATE TABLE TblEmpleados (IdEmpleado INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "NombreEmpleado VARCHAR(80), ApellidoEmpleado VARCHAR(80), EdadEmpleado INTEGER, DireccionEmpleado VARCHAR(200), PuestoEmpleado VARCHAR(120))";

    public static final String DropTableEmpleados = "DROP TABLE IF EXISTS TblEmpleados";
}
