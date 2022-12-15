import extensions.File;
import extensions.CSVFile;
class Mathemon extends Program{
    void algorithm(){
        CSVFile sauvegarde;
        ////////////////// MENU DU JEU ///////////////////////
        boolean fini=false;
        Personnage joueur = new Personnage();
        afficherMenu();
        int charge;
        do{   
            charge=readInt();
        }while(charge!=1 && charge!=2);
        
        if(charge==1){
            joueur = newPerso(joueur);  /////// À FAIRE //////
        }else{
            sauvegarde=loadCSV("./Mathemon.CSV");
            joueur = chargerSaveGame(sauvegarde);  ///// À FAIRE /////
        }


        ///////////////// INITIALISÉ LE JEU ///////////////////
        int choix;
        Ennemie ennemie;
        while(!fini){
            choix=afficherInterface();
            if(choix==1){
                afficherStat(joueur);
            }else if(choix==2){
                choix=afficherMap(joueur.lieux);
                if(choix!=0){
                    //afficherLieu(choix); // faire une foction qui affiche le Lieu choisit grâce a choix (pour les tableau)
                    ennemie=newEnnemie(choix-1);
                    commencerCombat(joueur,ennemie);
                }
            }else if(choix==3){
                fini=true;
                Sauvegarder(joueur);
            }
        }  
    }




    /////////////////////////////////// FONCTION INITIALISATION//////////////////////////// FONCTION INITIALISATION/////////////////////////// FONCTION INITIALISATION//////////////////////////////////

    Personnage newPerso(Personnage p){
        println("Comment vous appelez vous? "+"\n");
        String nom=readString();
        p.nom=nom;
        p.vie=100;
        p.force=10;
        p.chance=1;
        p.niveau=1;
        p.experience=0;
        p.outils=newOutils();
        p.lieux=newLieux();
        return p;
    }

    Outils newOutils(){
        Outils o =new Outils();
        o.listeOutils=new boolean[]{true,false,false,false,false,false,false};
        /*o.addition = true;
        o.soustraction = false;
        o.multiplication = false;
        o.division = false;
        o.modulo = false;
        o.puissance = false;*/
        return o;
    }

    Lieux newLieux(){
        /*Lieux l=new Lieux();
        l.listeLieux=new boolean[]{true,false,false,false,false,false,false};
        l.SListeLieux= new String[]{"foret","ville","jungle","desert","ocean","montagne","prairie"};
        return l;*/
        Lieu l1=new Lieu();
        l1.visiter=true;
        l1.nom="forêt";
        Lieu l2=new Lieu();
        l2.visiter=false;
        l2.nom="ville";
        Lieu l3=new Lieu();
        l3.visiter=false;
        l3.nom="jungle";
        Lieu l4=new Lieu();
        l4.visiter=false;
        l4.nom="desert";
        Lieu l5=new Lieu();
        l5.visiter=false;
        l5.nom="ocean";
        Lieu l6=new Lieu();
        l6.visiter=false;
        l6.nom="montagne";
        Lieu l7=new Lieu();
        l7.visiter=false;
        l7.nom="prairie ";
        Lieux l=new Lieux();
        l.listeLieux=new Lieu[]{l1,l2,l3,l4,l5,l6,l7};
        return l;
    }

    Ennemie newEnnemie(int nLieu){
        Ennemie ennemie=new Ennemie();
        ennemie.nom = "rat";
        ennemie.vie = 50+20*nLieu;
        ennemie.force = 5+5*nLieu;
        ennemie.chance = 40+5*nLieu;
        ennemie.experience = 20+15*nLieu;
        return ennemie;
    }


    /////////////////////////////////// FONCTION AFFICHAGE//////////////////////////// FONCTION AFFICHAGE/////////////////////////// FONCTION AFFICHAGE//////////////////////////////////
    
    void afficherMenu(){
        File f=new File("File/titre_acceuil");
        while(ready(f)){
            println(readLine(f));
        }
    }

    int afficherInterface(){
        int choix;
        println("\n"+"1. Voir Stat \n"+
                "2. Voir map \n"+
                "3. Quitter"+"\n");
        do{
            choix=readInt();
        }while(choix<1 || choix>3);
        return choix;
    }

    void afficherStat(Personnage joueur){
        println("\n"+"nom : "+joueur.nom +"\n"+"vie : "+joueur.vie+"\n"+"force : "+joueur.force+"\n"+"chance : "+joueur.chance+"\n"+"niveau : "+joueur.niveau+"\n"+"exp : "+joueur.experience+"/"+expRequis(joueur)+"\n");
    }

    int afficherMap(Lieux lieu){
        println("\n"+"0. Retour"+"\n");
        int choix;
        for(int i=1;i<8;i++){
            if(lieu.listeLieux[i-1].visiter){
                println(i+". "+lieu.listeLieux[i-1].nom);
            }else{
                println(i+". ?????????");
            }
        }
        do{
            choix=readInt();
        }while(choix>8 || choix<0);
        return choix;
    }

    void afficherScene(Ennemie ennemie){
        println("\n"+"Vous aller affronter un "+ennemie.nom+"\n"+"Il lui reste "+ennemie.vie+" points de vie"+"\n");
    }

    

    
    /////////////////////////////////// FONCTION COMBAT//////////////////////////// FONCTION COMBAT/////////////////////////// FONCTION COMBAT//////////////////////////////////

    void commencerCombat(Personnage joueur, Ennemie ennemie){
        afficherScene(ennemie);
        boolean fini=false;
        boolean gagner=false;
        while(!fini){
            tourJoueur(joueur,ennemie);
            if(ennemie.vie<=0){
                gagner=true;
                gagnerExp(joueur, ennemie);
                fini=true;
            }else{
              tourEnnemie(ennemie,joueur);  
              if(joueur.vie<=0){
                fini=true;
              }
            }
        }
        // recupererButin(); (SECONDAIRE)
        afficherFinCombats(gagner);
    }

    boolean tourJoueur(Personnage joueur, Ennemie ennemie){
        double res = donnerEquation(joueur);  
        return verifierReponse(res,ennemie,joueur); 
    }

    void tourEnnemie(Ennemie ennemie, Personnage joueur){
        double frappe = random()*100+1;
        if(frappe<ennemie.chance){
            joueur.vie-=ennemie.force;
            println("\n"+"vous avez été touché");
        }else{
            println("\n"+"Il vous à rater");
        }
        println("\n"+"il vous reste "+joueur.vie+" points de vie");
    }

    void afficherFinCombats(boolean gagner){
        if(gagner){
            println("Bravo vous avez gagné");
        }else{
            println("Dommage vous avez perdu");
        }
    }
    
    double donnerEquation(Personnage joueur){
        int a = (int) (random() * 10);
        int b = (int) (random() * 10);
        char outils = outilsAlea(joueur.outils);
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
            res = puissance(a,b);
        }
        print(a + " " + outils + " " + b + " = ");
        return res;
    }


    boolean verifierReponse(double res, Ennemie ennemie, Personnage joueur){
        double x = readDouble();
        if (x == res){
            println("\n"+"Bravo vous avez réussi !");
            ennemie.vie-=joueur.force;
            println("\n"+"Il lui reste "+ennemie.vie+" points de vie"+"\n");
            return true;
        } else {
            println("\n"+"Dommage la répponse correcte était : " + res);
            return false;
        }
    }
    char outilsAlea(Outils outils){
        String outil= "+-*%^";
        int nbOutils =0;
        boolean trouveFalse=false;
        int cpt=0;
        while(!trouveFalse && cpt<5){
            if(outils.listeOutils[cpt]==true){
                nbOutils++;
            }else{
                trouveFalse=true;
            }
            cpt++;
        }
        return charAt(outil,((int)random()*nbOutils+1)-1);
    }

    /////////////////////////////////// FONCTION EXP//////////////////////////// FONCTION EXP/////////////////////////// FONCTION EXP//////////////////////////////////
        int expRequis(Personnage joueur){
        int nv = joueur.niveau; 
        double exp = 100;
        for (int cpt = 1 ; cpt < nv ; cpt = cpt + 1){
            exp = exp * 1.2;
        }
        return (int) exp;
    }
    Personnage gagnerExp(Personnage joueur, Ennemie ennemie){
        joueur.experience = joueur.experience + ennemie.experience;
        while (joueur.experience >= expRequis(joueur)){
            joueur.experience = joueur.experience - expRequis(joueur);
            joueur.niveau = joueur.niveau + 1;
            outilsDebloquer(joueur);
            lieuDebloquer(joueur);
        }
        return joueur;
    }

    void outilsDebloquer(Personnage joueur){
        int n;
        if(joueur.niveau%5==0){                 // Risque de bug si on passe 2 niveau d'un coup///  
            n=joueur.niveau/5;
            joueur.outils.listeOutils[n]=true;
        }
    }

    void lieuDebloquer(Personnage joueur){   // peut être fusionné avec celui au dessus//
        int n;
        if(joueur.niveau%5==0){   
            n=joueur.niveau/5;
            joueur.lieux.listeLieux[n].visiter=true;
        }
    }

    /////////////////////////////////// FONCTION SAUVEGARDE//////////////////////////// FONCTION SAUVEGARDE////////////////////////// FONCTION SAUVEGARDE//////////////////////////////////
    void Sauvegarder(Personnage joueur){
        String[][] content=new String[3][8];
        content[0][0]=""+joueur.nom;
        content[0][1]=""+joueur.vie;
        content[0][2]=""+joueur.force;
        content[0][3]=""+joueur.chance;
        content[0][4]=""+joueur.niveau;
        content[0][5]=""+joueur.experience;
        for(int i=0;i<6;i++){
            content[1][i]=""+joueur.outils.listeOutils[i];
        }
        for(int i=0;i<7;i++){
            content[2][i]=""+joueur.lieux.listeLieux[i];
        }
        saveCSV(content,"./Mathemon.CSV");
    }

    Personnage chargerSaveGame(CSVFile sauvegarde){
        Personnage p =new Personnage();
        p.nom=getCell(sauvegarde,0,0);
        p.vie=stringInt(getCell(sauvegarde,0,1));
        p.force=stringInt(getCell(sauvegarde,0,2));
        p.chance=stringInt(getCell(sauvegarde,0,3));
        p.niveau=stringInt(getCell(sauvegarde,0,4));
        p.experience=stringInt(getCell(sauvegarde,0,5));
        p.outils=newOutils();
        p.lieux=newLieux();
        return p;
    }

    int stringInt(String texte){
        int nb=0;
        for (int i=0;i<length(texte);i++){
            nb+=(charAt(texte,i)-'0')*puissance(10,length(texte)-i-1);
        }
        return nb;
    }

    void testStringInt(){
        assertEquals(85,stringInt("85"));
    }

    int puissance(int nb, int exposant){
        int reponse=nb;
        if(exposant==0){
            return 1;
        }
        for(int i=1;i<exposant;i++){
            reponse*=nb;
        }
        return reponse;
    }

    void testPuissance(){
        assertEquals(25,puissance(5,2));
        assertEquals(125,puissance(5,3));
        assertEquals(81,puissance(3,4));
    }
    
}
