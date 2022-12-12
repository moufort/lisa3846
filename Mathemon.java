import extensions.File;
class Mathemon extends Program{
/*    void algorithm(){
        ////////////////// MENU DU JEU ///////////////////////
        boolean fini=false;
        afficherMenu();
        int charge;
        do{   
            charge=readInt();
        }while(charge!=1 && charge!=2);
        
        if(charge==1){
            chargerNewGame();    /////// À FAIRE //////
        }else{
            chargerSaveGame();  ///// À FAIRE /////
        }


        ///////////////// INITIALISÉ LE JEU ///////////////////

        while(!fini){
            afficherInterface();
            // SI COMBAT //
            commencerCombat();
            // SI GERER INVENTAIRE //
            gererInventaire();
            // SI FIN DE PARTIE //
            fermerPartie();
        }

*/
        ///////////////// DÉBUT DE JEUX ///////////////////////


        ///////////////// FIN DE JEUX (SAUVEGARDE) //////////////////////




        /*
        //////////MENU PRESENTATION //////////

        Presenter au joueur un menu pour qu'il ait le choix entre jeux principal/mini jeux, nouveau perso/chargé perso...


        Nous allons avoir besoin de crées un personnage joueur a initialiser donc : initialisationPersonnage()
        Savoir si il crée un personnage ou en charge un déja crée 
        gerer son niveau, son inventaire, sa vie...
        Presenter quete du personnage, ce qu'il doit faire pour avancer (un combat "exemple")


        Puis lui présenter un monde ou il va devoir faire des combats mathématique : afficherMonde()
        idée : lui présenter les mondes découvert en laissant les monde inconnu avec des ????????????

        permettre au joueur d'engager un combats (style donjon avec tableau à 2 dimension pour lui permettre de se déplacer et de tomber aléatoirement sur des ennemies ? ) : créeDonjon()
                                                 (ou il choisit l'adversaire lui meme dans une liste avec une force et des points de vie aléatoire ? ) 
        

        ////////GAMEPLAY COMBATS/////////

        Joueur doit répondre un une équation ( style a+b=?) pour touché, si il se trompe, il rate son coup    : réponseEquation()
        adversaire aura une certaine probabilité de le toucher selon son niveau (à jauger pour équilibrer)    : variable a changé au fur et a mesure :    stat()
        Si le joueur met trop de temps il rate

        Systeme stat joueur comme point dégat, vie... + prendre en compte si il y a équipement/malus           : combats()
        (crée un type stat)

        Crée un message et/ou image victoire/défaite + gérer expérience


        Si nous voulons rajouter une histoire alors une scéne == un fichier texte ( mais d'abord gameplay)


        */

    Personnage newPerso(String nom){
        Personnage p=new Personnage();
        p.nom=nom;
        p.vie=100;
        p.force=10;
        p.chance=1;
        p.niveau=1;
        p.experience=0;
        p.outils=newOutils();
        return p;
    }

    Outils newOutils(){
        Outils o =new Outils();
        o.addition = true;
        o.soustraction = false;
        o.multiplication = false;
        o.division = false;
        o.modulo = false;
        o.puissance = false;
        return o;
    }

    Lieux newLieux(){
        Lieux l=new Lieux();
        l.lieu1 = false;
        l.lieu2 = false;
        l.lieu3 = false;
        l.lieu4 = false;
        l.lieu5 = false;
        l.lieu6 = false;
        l.lieu7 = false;
        return l;
    }

    void afficherMenu(){
        File f=new File("File/titre_acceuil");
        while(ready(f)){
            println(readLine(f));
        }
    }

    char outilsAlea(Outils outils){
        String outil= "+-/%";
        int nbOutils =0;
        if(outils.soustraction==true){
            nbOutils+=1;
        }
        if(outils.multiplication==true){
            nbOutils+=1;
        }
        if(outils.division==true){
            nbOutils+=1;
        }
        if(outils.modulo==true){
            nbOutils+=1;
        }
        if(outils.puissance==true){
            nbOutils+=1;
        }
        return charAt(outil,(int)random()nbOutils+1);
    }

    double donnerEquation(){
        int a = (int) (random() * 10);
        int b = (int) (random() * 10);
        char outils = outilsAlea();
        double res = 0;
        if (outils == '+'){
            res = a + b;
        } else if (outils == '-'){
            res = a - b;
        } else if (outils == '*'){
            res = a * b;
        } else if (outils == '/'){
            res = a / b;
        } else if (outils == '%'){
            res = a % b;
        } else if (outils == '^'){
            res = a ^ b;
        }
        print(a + " " + outils + " " + b + " = ");
        return res;
    }

    boolean verifierReponse(double res){
        double x = readDouble();
        if (x == res){
            println("Bravo vous avez réussi !");
            return true;
        } else {
            println("Dommage la répponse correcte était : " + res);
            return false;
        }
    }

    void algorithm(){
        verifierReponse(donnerEquation());
    }

}
    /////////////////////////////////// FONCTION COMBAT//////////////////////////// FONCTION COMBAT/////////////////////////// FONCTION COMBAT//////////////////////////////////
/*
    void commencerCombat(Personnage joueur, Ennemie ennemie){
        afficherScene();
        boolean fini=false;
        boolean gagner;
        while(!fini){
            tourJoueur();
            tourEnnemie();
        }
        recupererButin(); // (SECONDAIRE)
        afficherFinCombats();
    }

    void tourJoueur(Personnage joueur){
        DonnerEquation();  
        verifierReponse(); // SUREMENT LA PARTIE LA PLUS DURE //
    }

    void tourEnnemie(Ennemie ennemie){

    }

    void afficherFinCombats(boolean gagner){
        if(gagner){
            afficherGagner();
        }else{
            afficherPerdu();
        }
    }
}
*/
