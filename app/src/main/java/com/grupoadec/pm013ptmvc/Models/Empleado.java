package com.grupoadec.pm013ptmvc.Models;

public class Empleado {
    Integer id;
    String nombre;
    String apellido;
    Integer edad;
    String Direccion;
    String Puesto;

    public Empleado(Integer id, String nombre, String apellido, Integer edad, String direccion, String puesto) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        Direccion = direccion;
        Puesto = puesto;
    }

    public Empleado(){}

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}

    public Integer getEdad() {return edad;}

    public void setEdad(Integer edad) {this.edad = edad;}

    public String getDireccion() {return Direccion;}

    public void setDireccion(String direccion) {Direccion = direccion;}

    public String getPuesto() {return Puesto;}

    public void setPuesto(String puesto) {Puesto = puesto;}
}
