package com.gestaodetarefas.tasks;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tasks {
    private String name;
    private String description;
    public static int idCounter = 1;
    private int id;
    private Date currentDateHour = new Date();
    private String date = new SimpleDateFormat("dd/MM/yyyy").format(currentDateHour);
    private String hour = new SimpleDateFormat("HH:mm:ss").format(currentDateHour);
    private String dthr = date + " " + hour;
    private String prazo;


    public Tasks() {
        this.id = idCounter++;
    }

    public Tasks(int id, String name, String description, String dthr, String prazo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dthr = dthr;
        this.prazo = prazo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrazo (String prazo) {
        this.prazo = prazo;
    }


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getDthr() {
        return dthr;
    }

    public String getPrazo() {
        return prazo;
    }
}
