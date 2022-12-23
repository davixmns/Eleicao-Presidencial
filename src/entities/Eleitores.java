package entities;

import dao.EleitorDaoJDBC;

import java.util.List;

public class Eleitores {
    private static List<Eleitor> eleitores;

    public Eleitores(){
        EleitorDaoJDBC eleitorDaoJDBC = new EleitorDaoJDBC();
        eleitores = eleitorDaoJDBC.findAll();
    }

    public static void addEleitor(Eleitor e){
        eleitores.add(e);
    }

    public static Eleitor getEleitor(int i){
        return eleitores.get(i);
    }
}
