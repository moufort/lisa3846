import extensions.File;
class Mathemon extends Program{
    void algorithm(){
        ////////////////// MENU DU JEU ///////////////////////
        boolean fini=false;
        Personnage joueur = new Personnage();
        afficherMenu();
        int charge;
        do{   
            charge=readInt();
        }while(charge!=1 /*&& charge!=2*/);
        
        if(charge==1){
            joueur = newPerso(joueur);  /////// À FAIRE //////
        }/*else{
            chargerSaveGame();  ///// À FAIRE /////
        }*/


        ///////////////// INITIALISÉ LE JEU ///////////////////
        int choix;
        Ennemie ennemie =new Ennemie();
        ennemie.nom="rat";
        ennemie.vie=100;
        ennemie.force=10;
        ennemie.chance=50;
        while(!fini){
            choix=afficherInterface();
            if(choix==1){
                afficherStat(joueur);
            }else if(choix==2){
                choix=afficherMap(joueur.lieux);
                if(choix!=0){
                    //afficherLieu(choix); // faire une foction qui affiche le Lieu choisit grâce a choix (pour les tableau)
                    commencerCombat(joueur,ennemie);
                }
            }else if(choix==3){
                fini=true;
                //Sauvegarder(joueur);
            }
            // SI COMBAT //
            
            // SI GERER INVENTAIRE //
            
            // SI FIN DE PARTIE //    
        }
        
    }

/*
    Personnage chargerNewGame(){
        Personnage p=newPerso(p);
        return p;
    }
*/
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
        l.listeLieux=new boolean[]{true,false,false,false,false,false,false};
        l.SListeLieux= new String[]{"foret","ville","jungle","desert","ocean","montagne","prairie"};
        l.lieu1 = true;
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
        println("\n"+"nom : "+joueur.nom +"\n"+"vie : "+joueur.vie+"\n"+"force : "+joueur.force+"\n"+"chance : "+joueur.chance+"\n"+"niveau : "+joueur.niveau+"\n"+"exp : "+joueur.experience+"\n");
    }

    int afficherMap(Lieux lieu){
        println("\n"+"0. Retour"+"\n");
        int choix;
        for(int i=1;i<8;i++){
            if(lieu.listeLieux[i-1]){
                println(i+". "+lieu.SListeLieux[i-1]);
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

    /*void Sauvegarder(Personnage joueur){
        String[][] content=new String[3][8];
        content[0][0]=""+joueur.nom;
        content[0][1]=""+joueur.vie;
        content[0][2]=""+joueur.force;
        content[0][3]=""+joueur.chance;
        content[0][4]=""+joueur.niveau;
        content[0][5]=""+joueur.experience;
        content[1][0]=""+joueur.outil;
        content[2][0]=""+joueur.lieu;
        saveCSV(content,"Mathemon.CSV");
    }*/

    /////////////////////////////////// FONCTION COMBAT//////////////////////////// FONCTION COMBAT/////////////////////////// FONCTION COMBAT//////////////////////////////////

    void commencerCombat(Personnage joueur, Ennemie ennemie){
        afficherScene(ennemie);
        boolean fini=false;
        boolean gagner=false;
        while(!fini){
            tourJoueur(joueur,ennemie);
            if(ennemie.vie<=0){
                gagner=true;
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
            res = a ^ b;
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
        return charAt(outil,((int)random()*nbOutils+1)-1);
    }
}
