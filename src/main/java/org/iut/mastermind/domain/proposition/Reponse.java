package org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private int position;

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // on récupère la lettre à la position dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        for(position=0; position<essai.length(); position++){
            resultat.add(evaluationCaractere(essai.charAt(position)));
        }
    }

    // si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        boolean toutesPlacees = true;
        if(resultat.size()==motSecret.length()){
            int i=0;
            while (i<resultat.size() && toutesPlacees){
                if(!resultat.get(i).equals(Lettre.PLACEE)){
                    toutesPlacees=false;
                }
                else{
                    i++;
                }
            }
        }
        else{
            toutesPlacees=false;
        }

        return  toutesPlacees;
    }

    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    // renvoie le statut du caractère
    private Lettre evaluationCaractere(char carCourant) {
        Lettre res;
        if(estBienPlace((carCourant))){
            res=Lettre.PLACEE;
        }
        else if (estPresent(carCourant)) {
            res=Lettre.NON_PLACEE;
        }
        else{
            res=Lettre.INCORRECTE;
        }
        return res;
    }

    // le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        return  motSecret.indexOf(carCourant)!=-1;
    }

    // le caractère est placé dans le mot secret
    private boolean estBienPlace(char carCourant) {
        return motSecret.charAt(position)==carCourant;
    }
}
