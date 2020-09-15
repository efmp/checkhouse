package com.emaza.checkhouse.entidades;

public class Solicitud {

    public Solicitud(int id, int idusuario, String nombres, String apellidos, String dni, int idvivienda, String banco, String estado) {
        this.id = id;
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idvivienda = idvivienda;
        this.banco = banco;
        this.estado = estado;
    }

    public Solicitud(int id, int idusuario, String nombres, String apellidos, String dni, String banco, String estado) {
        this.id = id;
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idvivienda = idvivienda;
        this.banco = banco;
        this.estado = estado;
    }
    public Solicitud(int idusuario, String nombres, String apellidos, String dni, int idvivienda, String banco, String estado) {
        this.idusuario = idusuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dni = dni;
        this.idvivienda = idvivienda;
        this.banco = banco;
        this.estado = estado;
    }

    private int id;
    private int idusuario;
    private String nombres;
    private String apellidos;
    private String dni;
    private int idvivienda;
    private String banco;
    private String estado;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdvivienda() {
        return idvivienda;
    }

    public void setIdvivienda(int idvivienda) {
        this.idvivienda = idvivienda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
}
