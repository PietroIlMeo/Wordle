import java.util.Scanner;

public class WordleMain {
    public static void main(String[] args) {
        String tentativo = "";
        Boolean error = false;
        WordleM gioco = new WordleM();
        gioco.parolaDaIndovinare();
        Scanner scanner = new Scanner(System.in);
        while (gioco.vite > 0) {
            do{
                // get Tentativo
                error = false;
                System.out.println("Indovina la parola (" + gioco.vite + " tentativi rimasti):");
                System.out.println('(' + gioco.getParola() + ')');
                tentativo = scanner.nextLine();
                
                // check if Tentativo != 5
                if (tentativo.length() !=5){
                    error = true;
                    System.out.println("ERRORE! La parola deve contenere 5 caratteri");
                }else{
                    // check if there is a number in Tentativo
                    for(char letter:tentativo.toCharArray()){
                        if (Character.isDigit(letter)){
                            error = true;
                        }
                    }
                    if(error == true){
                        System.out.println("ERRORE! La parola deve contenere solo caratteri non numeri");
                    }
                }
            // if the check not reported error, the code continue...
            }while(error);
            
            if (tentativo.equals(gioco.parola)) {
                System.out.println("Hai indovinato la parola!");
                break;
            } else {
                gioco.tentativo(tentativo);
            }
            if (gioco.vite == 0) {
                System.out.println("Hai esaurito i tentativi. La parola era: " + gioco.parola);
            }
        }
        scanner.close();
    }
}
