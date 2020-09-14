package com.emaza.checkhouse.entidades;

public class Usuario {

    public Usuario(int id, String nombres, String apellidos, String correo, String dni, int idvivienda, String password, int intentos, String tipo, String estado) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.dni = dni;
        this.idvivienda = idvivienda;
        this.password = password;
        this.intentos = intentos;
        this.tipo = tipo;
        this.estado = estado;
    }
    public Usuario(String nombres, String apellidos, String correo, String dni, String password) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.dni = dni;
        this.password = password;
    }
    public Usuario(int id, int idvivienda) {
        this.id = id;
        this.idvivienda = idvivienda;
    }

    private int id;
    private String nombres;
    private String apellidos;
    private String correo;
    private String dni;
    private int idvivienda;
    private String password;
    private int intentos;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIntentos() {
        return intentos;
    }

    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    private String estado;
}
