package entities;

import dao.CandidatoDaoJDBC;

import java.util.List;

public class Candidatos {
    private static List<Candidato> candidatos;

    public Candidatos(){
        CandidatoDaoJDBC candidatoDaoJDBC = new CandidatoDaoJDBC();
        candidatos = candidatoDaoJDBC.findAll();
    }

    public static void addCandidato(Candidato c){
        candidatos.add(c);
    }

    public static Candidato getCandidato(int i){
        return candidatos.get(i);
    }
}
