package org.iut.mastermind.domain;

import com.vtence.molecule.Response;
import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.MotSecret;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;

import java.util.Optional;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si il y a une partie en cours, on renvoie false (pas de nouvelle partie)
    // sinon on utilise le service de tirage aléatoire pour obtenir un mot
    // et on initialise une nouvelle partie et on la stocke
    public boolean nouvellePartie(Joueur joueur) {
        Optional<Partie> optionalPartie=partieRepository.getPartieEnregistree(joueur);

        boolean enCours=false;
        if(optionalPartie.isPresent()) {
            Partie partie=optionalPartie.get();
            enCours=!partie.isTerminee();
        }

        if(!enCours){
            String motSecret=serviceTirageMot.tirageMotAleatoire();
            Partie partie=new Partie(joueur, motSecret, 0, false);

            partieRepository.create(partie);
        }

        return !enCours;
    }

    // on récupère éventuellement la partie enregistrée pour le joueur
    // si la partie n'est pas une partie en cours, on renvoie une erreur
    // sinon on retourne le resultat du mot proposé
    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        Optional<Partie> optionalPartie=partieRepository.getPartieEnregistree(joueur);
        Partie partie;

        ResultatPartie res;

        if(optionalPartie.isPresent() && !(partie=optionalPartie.get()).isTerminee()) {
            Reponse reponse=partie.tourDeJeu(motPropose);
            res=ResultatPartie.create(reponse, partie.isTerminee());
            partieRepository.update(partie);
        }
        else{
            res=ResultatPartie.ERROR;
        }

        return  res;
    }

    // on évalue le résultat du mot proposé pour le tour de jeu
    // on met à jour la bd pour la partie
    // on retourne le résulat de la partie
    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        return null;
    }

    // si la partie en cours est vide, on renvoie false
    // sinon, on évalue si la partie est terminée
    private boolean isJeuEnCours(Optional<Partie> partieEnCours) {
        return false;
    }
}
