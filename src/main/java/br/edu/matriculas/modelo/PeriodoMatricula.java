package br.edu.matriculas.modelo;

import java.time.LocalDate;

public class PeriodoMatricula {

    private String semestre;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private boolean aberto;

    public PeriodoMatricula(String semestre, LocalDate dataInicio, LocalDate dataFim) {
        this.semestre = semestre;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
    }

    public String getSemestre() {
        return semestre;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public boolean isAberto() {
        return aberto;
    }

    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }
}
