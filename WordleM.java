public class WordleM {

    String[] parole = {"alato", "miele", "bolla", "gomma", "ruota", "serra", "matto", "tardi", "cuore", "fermo", "gusto", "occhi", "penna", "quota", "scuro", "trama", "valle", "botta", "zaino", "aereo", "pollo", "carie", "crudo", "vuoto", "dolce", "reale", "morte", "pazzo", "trova", "asilo", "losco", "vizio", "duomo", "pasto", "limbo", "torre", "sedia", "zitto", "mazzo", "burro", "mangi", "sfiga", "molle", "corte", "fallo", "bacio", "calza", "fatto", "video", "callo", "rosso", "cella", "esule", "tempo", "tende", "rospo", "asino", "cisti", "derby", "somma", "lucro", "balla", "gatto", "picco", "palmo", "burro", "malta", "notti", "parco", "rullo", "sacco", "stile", "buono", "venne", "ricci", "sidro", "salto", "gamba", "basso", "vasto", "casco", "guida", "frana", "frate", "pezzo", "sopra", "santo", "volta", "picco", "acuto", "vinci", "rotto", "vento", "turbo", "rocca", "veste", "entro", "sosta", "pasta", "calma", "ossia", "salta", "ampio", "sfora", "stira", "bacco", "manda", "serve", "canta", "vanta", "parla", "valga", "solco","attua", "ricco", "crudo", "vesto", "servo", "barca", "veste", "sacro", "sacra", "lampa", "bravo", "mazzo", "pollo", "tempo", "pasto", "casca", "sette", "zuppa", "magro", "palla", "sento", "notti", "denso", "renne", "casco", "festa", "molto", "molle", "stufa", "rotto", "vacca", "talpa", "salto", "calza", "astio", "visto", "metro", "sesto", "manto", "nulla", "firma", "vieta", "limbo", "bordo", "campo", "torta", "radio", "sopra", "capri", "ruota", "tonno", "vasco", "mente", "vanno", "menta", "venne", "pelle", "capra", "veste", "metto", "tende", "sparo", "bando", "calce", "lento", "pazzo", "fermo", "prega", "morto","porta", "frena", "rambo", "metro", "gioco", "scala", "miele", "pesca", "fogli"

};
    int vite, vittorie, aiuti, viteH;
    String parola = "", difficolta = "";
    String parolaUtente = "";
    String[] paroleIndovinate;
    int[] esito = {0, 0, 0, 0, 0};

    public WordleM() {
        vite = 6;
        vittorie = 0;
        aiuti = 3;
        viteH = 10;
        paroleIndovinate = new String[parole.length];
        for(int i = 0; i < parole.length; i++){
            paroleIndovinate[i] = "";
        }
    }

    public WordleM(String[] parole, int vite, int vittorie, int[] esito, int aiuti, int viteH, String[] paroleIndovinate) {
        this.parole = parole;
        this.vite = vite;
        this.vittorie = vittorie;
        this.esito = esito;
        this.aiuti = aiuti;
        this.viteH = viteH;
        this.paroleIndovinate = paroleIndovinate;
    }

    public void parolaDaIndovinare() {
        int i = (int) (Math.random() * parole.length);
        parola = parole[i];
        System.out.println("La parola scelta: " + parola);
    }

    public String getParola() {
        return parola;
    }

    public int[] tentativo(String t) {
        parolaUtente = t;
        boolean[] lettereTrovate = {false, false, false, false, false};
        boolean trovato = false;
        for (int i = 0; i < 5; i++) {
            System.out.println("La parola nel tentativo: " + parola);
            if (parola.charAt(i) == t.charAt(i)) {
                esito[i] = 3; // correct position
                System.out.println(t.charAt(i) + " è nella posizione corretta");
                lettereTrovate[i] = true;
            }else{
                trovato = false;
                for(int l = 0; l < 5; l++){
                    if (t.charAt(i) == parola.charAt(l)){
                        trovato = true;
                    }    
                }
                if(trovato){
                    esito[i] = 2;//exist but wrong position
                    System.out.println(t.charAt(i) + " è presente ma non è nella posizione corretta");
                }else{
                    esito[i] = 1;// not exist
                    System.out.println(t.charAt(i) + " non è presente nella parola");
                }
                
            }
        }
        int count = 0;
        for (int i = 0; i < 5; i++) {
            count = 0;
            for (int l = 0; l < 5; l++){
                if (t.charAt(i) == parola.charAt(l)){
                        trovato = true;
                        count+=1;
                    }
            }
            if(!lettereTrovate[i] && count>1){
                int cLetterePosCorr = 0;
                for (int a = 0; a < 5; a++) {
                    if(parola.charAt(a) == t.charAt(i) && lettereTrovate[a]){
                        cLetterePosCorr+=1;
                    }
                }
                if(cLetterePosCorr == count){
                   esito[i] = 1;
                }
            }else if(!lettereTrovate[i] && count==1){
                for (int a = 0; a < 5; a++) {
                    if(parola.charAt(a) == t.charAt(i) && lettereTrovate[a]){
                        esito[i] = 1;
                    }
                }
            }
        }
        return esito;
    }
    
    public int vite(String t){
        if (!t.equals(parola)) {
            vite = vite - 1;
        }else{
            vite = 6;
        }
        return vite;
    }

    public int vittorie(String t) {
        if (t.equals(parola)) {
            vittorie = vittorie + 1;
        }
        return vittorie;
    }
    
    public String getParolaUtente(){
        return parolaUtente;
    }
    
    public void setMode(String mode){
        difficolta = mode;
    }
    
    public String getMode(){
        return difficolta;
    }
    
    public int contaAiuti(){
        aiuti--;
        return aiuti;
    }
    
    public int viteH(String t){
        if (!t.equals(parola)) {
            viteH = viteH - 1;
        }
        return viteH;
    }
    
    public boolean controlloNParola(String p){
        for(int i = 0; i < parole.length; i++){
            if(!paroleIndovinate[i].equals(p) && paroleIndovinate[i].equals("")){
                paroleIndovinate[i] = p;
                return true;
            }
            if(paroleIndovinate[i].equals(p)){
                return false;
            }
        }
        return false;
    }
}