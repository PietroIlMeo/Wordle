
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

public class WordleV extends JFrame implements ActionListener {

    // Dichiarazione Etichette
    JLabel lettere[][], titolo, vite, vittorie, messaggio, titoloPIniz, sceltaDiff, sconfitta, messaggioD, messaggioV;
    // Dichiarazione array tasti
    JButton[] tasti;
    // Dichiarazione Bottoni
    JButton cancella, invio, giocoNuovo, gioca, easy, medium, hard, aiuto;
    // Oggetto della classe WordleM per il gioco
    WordleM gioco;
    // Dichiarazione Pannelli
    JPanel spazioLettere, tastiera, finestra, spazioTitolo, spazioMessaggio, spazioPIniz, panelDiff, panelBottoniDifficolta, panelMessaggioD, panelMessaggioS;
    // Dichiarazione contaVite e contaVittorie
    int cVite, cVittorie;

    //Metodo privato per generare parola nel gioco
    private void nuovaParola() {
        gioco.parolaDaIndovinare(); // creazione parola nascosta
        // controllo che le parole già indovinate non siano uguali
        while(gioco.controlloNParola(gioco.getParola()) == false){
            gioco.parolaDaIndovinare();
        }
        riga = 0; // Reimposta la riga delle lettere
        vittoria = false; // Reimposta il flag di vittoria
        spazioLettere.removeAll(); // Rimuovi tutte le lettere
        spazioLettere.revalidate(); // Rendi valida la rimozione delle componenti
        spazioLettere.repaint(); // Ridisegna il pannello
        for (int r = 0; r < 6; r++) { // Rigenera le label delle lettere
            for (int c = 0; c < 5; c++) {
                lettere[r][c] = new JLabel();
                lettere[r][c].setHorizontalAlignment(SwingConstants.CENTER); // setto scritta centrale
                lettere[r][c].setFont(new Font("Comic Sans MS", Font.BOLD, 13)); // setto font e dimensione scritta
                lettere[r][c].setPreferredSize(new Dimension(50, 50)); // setto dimensione 
                lettere[r][c].setBorder(new EmptyBorder(10, 10, 10, 10)); // setto spazi tra un tasto della tastiera e l'altro 10 pixel
                lettere[r][c].setOpaque(true); // setto sfondo opaco
                lettere[r][c].setBackground(new Color(245, 245, 245)); // setto colore dello sfondo
                lettere[r][c].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED)); // setto bordo con stile rilievo abbassato
                spazioLettere.add(lettere[r][c]); // Aggiungi la label al pannello

            }
        }
        spazioLettere.setBorder(new EmptyBorder(10, 10, 10, 10)); // setto spazio spazioLettere 10 pixel
        spazioLettere.revalidate(); // Rendi valida la rimozione e l'aggiunta dei componenti
        spazioLettere.repaint(); // Ridisegna il pannello
        // Rimuovi il colore dai bottoni della tastiera
        for (int i = 0; i < 26; i++) {
            tasti[i].setBackground(new Color(220, 220, 220));
            tasti[i].setForeground(Color.BLACK);
            tasti[i].setSize(50,50);
        }

        if (tastiera.getComponentCount() == 32) { // 26 tasti + 4 righe + 2 pulsanti
            tastiera.revalidate(); // Rendi valida la rimozione e l'aggiunta dei componenti
            tastiera.repaint(); // Ridisegna il pannello
            nuovaParola(); // Richiamo il metodo nuovaParola
        }
        // Aggiorna le vittorie mantenendo le vite
        cVittorie = gioco.vittorie;
        makeGestioneVittorie();
        messaggio.setText("");
    }
          
    private void makeLettere() {
        lettere = new JLabel[6][5]; // creo matrice di etichetta 6 x 5 
        for (int r = 0; r < 6; r++) { //righe
            for (int c = 0; c < 5; c++) { //colonne
                lettere[r][c] = new JLabel();
                lettere[r][c].setFont(new Font("Comic Sans MS", Font.BOLD, 13));
                lettere[r][c].setHorizontalAlignment(SwingConstants.CENTER); // setto scritta centrale
                lettere[r][c].setPreferredSize(new Dimension(50, 50)); 
                lettere[r][c].setBorder(new EmptyBorder(10, 10, 10, 10));
                lettere[r][c].setOpaque(true);
                lettere[r][c].setBackground(new Color(245, 245, 245));
                lettere[r][c].setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
            }
        }
    }

    private void makePanelLettere() {
        spazioLettere = new JPanel();
        spazioLettere.setLayout(new GridLayout(6, 5, 10, 10)); // setto layout come GridLayout con dimensione 6 righe 5 colonne delimitata da 10 pixel di spazio tra righe e colonne
        for (int r = 0; r < 6; r++) { //righe
            for (int c = 0; c < 5; c++) { //colonne
                spazioLettere.add(lettere[r][c]);
            }
        }
        spazioLettere.setBorder(new EmptyBorder(10, 10, 10, 10));
        spazioLettere.setBackground(Color.LIGHT_GRAY);
        spazioLettere.revalidate(); // Rendi valida la rimozione e l'aggiunta dei componenti
        spazioLettere.repaint(); // Ridisegna il JPanel con i nuovi componenti
    }

    private void makeTasti() {
        tasti = new JButton[26];
        for (int i = 0; i < 26; i++) {
            // ('A' + i) calcola il valore ASCII della lettera 'A' sommando l'indice i
            //(char) esegue un cast esplicito a char, in modo da ottenere la lettera corrispondente al valore ASCII calcolato
            char c = (char) ('A' + i); 
            tasti[i] = new JButton(Character.toString(c)); // Character.toString(c) converte la lettera c in una stringa
            tasti[i].setFont(new Font("Comic Sans MS", Font.BOLD, 13));
            tasti[i].setSize(50,50);
            tasti[i].setOpaque(false);
            tasti[i].setBorderPainted(false); // setto colore bordo in falso
            tasti[i].setUI(new BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                    ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                    Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                    if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                        color = color.darker();
                    }
                    g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                    g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                    super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
                }
            });
            tasti[i].setBackground(new Color(220, 220, 220));
            tasti[i].addActionListener(this); // aggiungo azione quando il tasto viene premuto
        }

        cancella = new JButton("X");
        cancella.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        cancella.setOpaque(false);
        cancella.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        cancella.setBackground(new Color(220, 220, 220));
        cancella.setForeground(Color.RED);
        cancella.setBorderPainted(false);
        cancella.addActionListener(this);

        invio = new JButton("✔");
        invio.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        invio.setOpaque(false);
        invio.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        invio.setBackground(new Color(220, 220, 220));
        invio.setForeground(new Color(50, 205, 50));
        invio.setBorderPainted(false);
        invio.addActionListener(this);

        giocoNuovo = new JButton(">>");
        giocoNuovo.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        giocoNuovo.setOpaque(false);
        giocoNuovo.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        giocoNuovo.setBackground(new Color(220, 220, 220));
        giocoNuovo.setForeground(Color.RED);
        giocoNuovo.setBorderPainted(false);
        giocoNuovo.addActionListener(this);
        
        aiuto = new JButton(gioco.aiuti + "🛟"); // gioco.aiuti --> rappresenta il numero di aiuti disponibili nel gioco, memorizzato come una variabile nella classe WordleM
        aiuto.setOpaque(false);
        aiuto.addActionListener(this);
        aiuto.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        aiuto.setBackground(new Color(220, 220, 220));
    }

    private void makeTastiera() {
        tastiera = new JPanel();
        tastiera.setBorder(new EmptyBorder(10, 10, 10, 10));
        tastiera.setLayout(new GridLayout(6, 5));

        for (int i = 0; i < 26; i++) {
            tastiera.add(tasti[i]);
        }
        tastiera.add(cancella);
        tastiera.add(invio);
        tastiera.setBackground(Color.LIGHT_GRAY);
    }

    private void makeSpazioTitolo() {
        spazioTitolo = new JPanel();
        spazioTitolo.add(vite);
        spazioTitolo.add(titolo);
        spazioTitolo.add(vittorie);
        spazioTitolo.setLayout(new GridLayout(1, 2));
        spazioTitolo.setPreferredSize(new Dimension(50, 50));
        spazioTitolo.setBackground(Color.LIGHT_GRAY);
    }

    private void makeFinestra() {
        finestra = new JPanel();
        finestra.add(spazioLettere);
        finestra.add(tastiera);
        finestra.setLayout(new GridLayout(2, 1)); // righe, colonne
        finestra.setBackground(Color.LIGHT_GRAY);
    }

    private void makeGestioneVite() {
        vite.setText("♥: " + cVite);
        vite.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        vite.setForeground(Color.red);
        vite.setHorizontalAlignment(SwingConstants.CENTER);
        
        if(cVite == 0){
            panelMessaggioS = new JPanel();
            panelMessaggioS.setLayout(new BoxLayout(panelMessaggioS, BoxLayout.Y_AXIS));
            messaggioV = new JLabel("🏆:" + cVittorie);
            messaggioV.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
            messaggioV.setHorizontalAlignment(SwingConstants.CENTER);
            sconfitta = new JLabel("HAI PERSO");
            sconfitta.setBackground(Color.LIGHT_GRAY);
            sconfitta.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
            remove(finestra);
            remove(spazioTitolo);
            remove(spazioMessaggio);
            // Aggiungi uno spazio verticale elastico per centrare i componenti
            
            // Crea un Component vuoto con altezza specifica
            Component rigidArea = Box.createRigidArea(new Dimension(0, 350)); // Altezza di 50 pixel

            panelMessaggioS.add(rigidArea);
            panelMessaggioS.add(sconfitta);
            panelMessaggioS.add(messaggioV);
            
            sconfitta.setAlignmentX(Component.CENTER_ALIGNMENT);
            messaggioV.setAlignmentX(Component.CENTER_ALIGNMENT);

            panelMessaggioS.setBackground(Color.LIGHT_GRAY);
            sconfitta.setForeground(Color.RED);
            add(panelMessaggioS, BorderLayout.CENTER);
            revalidate(); // Rendi valida la rimozione dei componenti
            repaint();
        }
    }

    private void makeGestioneVittorie() {
        vittorie.setText("🏆: " + cVittorie);
        vittorie.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        vittorie.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void makePanelMessaggio() {
        spazioMessaggio = new JPanel();
        spazioMessaggio.add(messaggio);
        spazioMessaggio.setPreferredSize(new Dimension(50, 50));
        spazioMessaggio.setBackground(Color.LIGHT_GRAY);
    }

    private boolean paginaIniz() {
        panelMessaggioD = new JPanel();
        panelMessaggioD.setLayout(new BorderLayout());
        gioca = new JButton("GIOCA");
        gioca.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        
        gioca.setAlignmentY(Component.CENTER_ALIGNMENT);
        messaggioD = new JLabel("");
        messaggioD.setFont(new Font("Cominc Sans MS", Font.BOLD, 15));
        
        panelMessaggioD.add(messaggioD, BorderLayout.CENTER);
        titoloPIniz = new JLabel("Welcome to WORDLE");
        titoloPIniz.setForeground(new Color(0,0,128));
        titoloPIniz.setHorizontalAlignment(SwingConstants.CENTER);
        titoloPIniz.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        
        sceltaDiff = new JLabel("Scegli la difficoltà");
        sceltaDiff.setHorizontalAlignment(SwingConstants.CENTER);
        sceltaDiff.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        sceltaDiff.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelDiff = new JPanel();

        panelBottoniDifficolta = new JPanel(new FlowLayout()); // Layout per allineare i bottoni
        easy = new JButton("EASY");
        easy.setOpaque(false);
        easy.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        easy.setPreferredSize(new Dimension(100, 50));  // Imposta le dimensioni desiderate
        easy.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        easy.setBackground(Color.GREEN);
        easy.setForeground(Color.WHITE);
        easy.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        easy.addActionListener(this);

        medium = new JButton("MEDIUM");
        medium.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        medium.setPreferredSize(new Dimension(100, 50));  // Imposta le dimensioni desiderate
        medium.setOpaque(false);
        medium.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        medium.setBackground(Color.ORANGE);
        medium.setForeground(Color.WHITE);
        medium.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        medium.addActionListener(this);

        hard = new JButton("HARD");
        hard.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        hard.setPreferredSize(new Dimension(100, 50));  // Imposta le dimensioni desiderate
        hard.setOpaque(false);
        hard.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                    color = color.darker();
                }
                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
            }
        });
        hard.setBackground(Color.RED);
        hard.setForeground(Color.WHITE);
        hard.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        hard.addActionListener(this);

        panelBottoniDifficolta.add(easy);
        panelBottoniDifficolta.add(medium);
        panelBottoniDifficolta.add(hard);
        
        panelBottoniDifficolta.setBackground(Color.LIGHT_GRAY);
        
        panelDiff.add(sceltaDiff, BorderLayout.NORTH);
        panelDiff.add(panelBottoniDifficolta, BorderLayout.CENTER);
        panelDiff.add(gioca, BorderLayout.SOUTH);

        spazioPIniz = new JPanel();
        spazioPIniz.setLayout(new BorderLayout());
        spazioPIniz.add(titoloPIniz, BorderLayout.NORTH);
        titoloPIniz.setPreferredSize(new Dimension(300, 300));
        spazioPIniz.add(panelDiff, BorderLayout.CENTER);
        spazioPIniz.add(panelMessaggioD, BorderLayout.SOUTH);
        panelMessaggioD.setVisible(true);
        panelMessaggioD.setPreferredSize(new Dimension(200, 200));
        panelDiff.setPreferredSize(new Dimension(200, 200));
        
        spazioPIniz.setBackground(Color.LIGHT_GRAY);
        titoloPIniz.setBackground(Color.LIGHT_GRAY);
        panelDiff.setBackground(Color.LIGHT_GRAY);
        panelMessaggioD.setBackground(Color.LIGHT_GRAY);
        
        add(spazioPIniz);
        gioca.addActionListener(this);
        
        return true;
    }

    public WordleV() {
        gioco = new WordleM();
        gioco.parolaDaIndovinare();
        while(gioco.controlloNParola(gioco.getParola()) == false){
            gioco.parolaDaIndovinare();
        }

        setTitle("Wordle"); // imposto titolo della schermata
        setBounds(500, 500, 370, 900); // imposto posizione e grandezza della schermata
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // specifica che l'applicazione deve uscire e terminare completamente quando l'utente chiude la finestra principale
        setLayout(new BorderLayout());

        paginaIniz();

        makeTasti();
        makeTastiera();

        makeLettere();
        makePanelLettere();

        vite = new JLabel("");

        vittorie = new JLabel("Vittorie: ");
        cVittorie = gioco.vittorie; // gioco.vittorie --> rappresenta il numero di vittorie disponibili nel gioco, memorizzato come una variabile nella classe WordleM
        makeGestioneVittorie();

        titolo = new JLabel("WORDLE");
        titolo.setHorizontalAlignment(SwingConstants.CENTER);
        titolo.setFont(new Font("Comic Sans MS", Font.BOLD, 24)); //Comic Sans MS Impact Segoe UI
        titolo.setForeground(new Color(0,0,128));
        makeSpazioTitolo();

        messaggio = new JLabel("");
        makePanelMessaggio();

        makeFinestra();
        setVisible(true);
        setResizable(false);
    }

    int[] pos = new int[5];
    int riga = 0;
    boolean vittoria = false;
    String parolaUtentePrec = "";
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String parolaUtente = "";

        for (int i = 0; i < 26; i++) {
            if (e.getSource() == tasti[i]) {
                for (int c = 0; c < 5; c++) { //colonne
                    if (lettere[riga][c].getText().equals("")) {
                        pos[c] = i;
                        lettere[riga][c].setText(tasti[i].getText());
                        break;
                    }
                }
            }
        }

        if (e.getSource() == cancella) {
            for (int c = 4; c >= 0; c--) {
                if (!lettere[riga][c].getText().equals("") && vittoria == false) {
                    pos[c] = -1;
                    lettere[riga][c].setText("");
                    break;
                }
            }
        }

        boolean totLet = false;

        if (e.getSource() == invio) {
            for (int t = 0; t < 5; t++) {
                totLet = true;
                parolaUtente = parolaUtente + lettere[riga][t].getText();
            }
            if (parolaUtente.length() < 5) {
                // messaggio di errore
                JOptionPane.showMessageDialog(null, "ERRORE MANCANO DELLE LETTERE!!", "Errore", JOptionPane.ERROR_MESSAGE); 
            } else {
                messaggio.setText("");
                parolaUtentePrec = parolaUtente.toLowerCase(); // salva la parola dell'utente precedente
                parolaUtente = parolaUtente.toLowerCase();
                if (!vittoria) {
                    int[] esito = gioco.tentativo(parolaUtente); // richiamo metodo tentativo di WordleM che mi restituisce l'esito (1 --> non esiste ,2 --> esiste ma nella posizione sbagliata, 3 --> posizione corretta) e lo salva in un array di esito
                    for (int r = 0; r < 5; r++) {
                        lettere[riga][r].setOpaque(true);
                        tasti[pos[r]].setOpaque(false);
                        tasti[pos[r]].setUI(new BasicButtonUI() {
                            @Override
                            public void paint(Graphics g, JComponent c) {
                                AbstractButton b = (AbstractButton) c; // Effettua un cast dell'elemento grafico JComponent in un bottone astratto AbstractButton
                                ButtonModel bm = b.getModel(); // Ottiene il modello del bottone, che contiene lo stato attuale del bottone (premuto, rilasciato, ecc.)
                                Color color = b.getBackground(); // Ottiene il colore di sfondo del bottone
                                if (bm.isArmed()) { // Verifica se il bottone è "armato", ovvero se è stato premuto. In tal caso, scurisce leggermente il colore di sfondo del bottone
                                    color = color.darker();
                                }
                                g.setColor(color); // Imposta il colore corrente per il disegno del bottone
                                g.fillRoundRect(2, 2, c.getWidth() - 4, c.getHeight() - 4, 20, 20); // Disegna un rettangolo arrotondato all'interno del bottone con il colore corrente
                                super.paint(g, c); // Chiama il metodo paint della superclasse BasicButtonUI per completare il disegno del bottone con le impostazioni predefinite
                            }
                        });
                        if (esito[r] == 1) {
                            if (tasti[pos[r]].getBackground() == Color.GREEN) {
                                lettere[riga][r].setBackground(Color.RED); // sfondo
                                lettere[riga][r].setForeground(Color.BLACK); // colore testo
                            } else {
                                lettere[riga][r].setBackground(Color.RED);
                                lettere[riga][r].setForeground(Color.BLACK);
                                tasti[pos[r]].setBackground(Color.RED);
                                tasti[pos[r]].setForeground(Color.BLACK);
                            }
                        } else if (esito[r] == 2) {
                            if (tasti[pos[r]].getBackground() == Color.GREEN) {
                                lettere[riga][r].setBackground(Color.YELLOW);
                                lettere[riga][r].setForeground(Color.BLACK);
                            } else {
                                lettere[riga][r].setBackground(Color.YELLOW);
                                lettere[riga][r].setForeground(Color.BLACK);
                                tasti[pos[r]].setBackground(Color.YELLOW);
                                tasti[pos[r]].setForeground(Color.BLACK);
                            }
                        } else {
                            lettere[riga][r].setBackground(Color.GREEN);
                            lettere[riga][r].setForeground(Color.BLACK);
                            tasti[pos[r]].setBackground(Color.GREEN);
                            tasti[pos[r]].setForeground(Color.BLACK);
                        }
                    }
                    if (!parolaUtente.equals(gioco.getParola())) {
                        riga++;
                        if (cVite != 0) {
                            if(!gioco.getMode().equals("HARD")){
                                cVite = gioco.vite(parolaUtente); // richiama metodo vite (max.6) e restituisce le vite aggiornate        
                            }else{
                                cVite = gioco.viteH(parolaUtente); // richiama metodo viteH (max.10) e restituisce le vite aggiornate                            
                                if(riga == 6){
                                    for (int r = 0; r < 5; r++) {
                                        if(!(lettere[riga-1][r].getBackground()).equals(Color.GREEN)){
                                            cVite = 0; // se arrivo all'ultima riga e la sbaglio metto le vite a 0
                                        }
                                    }
                                }
                            }
                            makeGestioneVite();
                            
                        }
                    } else {
                        messaggio.setText("Vittoria");
                        cVittorie = gioco.vittorie(parolaUtente); // richiama metodo vittorie ritornando le vittorie aggiornate
                        if(!gioco.getMode().equals("HARD")){
                            cVite = gioco.vite(parolaUtente);
                        }else{
                            cVite = gioco.viteH(parolaUtente);
                        }
                        makeGestioneVite();
                        makeGestioneVittorie();
                        vittoria = true;
                        tastiera.add(giocoNuovo); // aggiunge il tasto giocoNuovo per proseguire con un altro round
                        tastiera.revalidate(); // Rendi valida l'aggiunta dei componenti
                        tastiera.repaint(); // Ridisegna il pannello
                    }
                }
            }
        }
        
        if (e.getSource() == gioca) {
            if(!gioco.getMode().equals("EASY") && !gioco.getMode().equals("MEDIUM") && !gioco.getMode().equals("HARD")){
                // crea avviso se non hai impostato la difficoltà
                JOptionPane.showMessageDialog(null, "Devi scegliere la difficoltà", "Avviso", JOptionPane.WARNING_MESSAGE);
            }else{
                if(gioco.getMode().equals("EASY")){
                    tastiera.add(aiuto);
                    tastiera.revalidate();
                    tastiera.repaint();
                }else if(gioco.getMode().equals("HARD")){
                    cVite = gioco.viteH;
                }
                remove(spazioPIniz); // rimuovo dal JFrame 

                add(spazioTitolo, BorderLayout.NORTH);
                add(finestra, BorderLayout.CENTER);
                add(spazioMessaggio, BorderLayout.SOUTH);
                revalidate(); // Rendi valida la rimozione dei componenti
                repaint(); // Ridisegna il pannello

                if(!gioco.getMode().equals("HARD")){
                    cVite = gioco.vite;
                }else{
                    cVite = gioco.viteH;
                }
                makeGestioneVite();
            }
        }
        
        if(e.getSource() == easy){
            gioco.setMode(easy.getText());
            messaggioD.setText("<html> <div style='text-align:center;'> <font face='Comic Sans MS'> - Hai scelto la difficoltà EASY <br> - Avrai 3 aiuti che potrai usare quando vuoi <br> "
                            + "- Avrai 6 vite che si ripristineranno ogni volta che indovinerai la parola nascosta <br>"
                            + "- Attenzione se arriverai a 0 vite hai perso </font> </div> </html>");
        }
        
        if(e.getSource() == medium){
            gioco.setMode(medium.getText());
            messaggioD.setText("<html> <div style='text-align:center;'> <font face='Comic Sans MS'> - Hai scelto la difficoltà MEDIUM <br>"
                            + "- Avrai 6 vite che si ripristineranno ogni volta che indovinerai la parola nascosta <br>"
                            + "- Attenzione se arriverai a 0 vite hai perso </font> </div> </html>");
        }
        
        if(e.getSource() == hard){
            gioco.setMode(hard.getText());
            messaggioD.setText("<html> <div style='text-align:center;'> <font face='Comic Sans MS'> - Hai scelto la difficoltà HARD <br>"
                            + "- Avrai 10 vite totali <br>"
                            + "- Attenzione se arriverai a 0 vite hai perso </font> </div> </html>");
        }
        
        if(e.getSource() == aiuto){
            if(!aiuto.getText().equals("0🛟")){
                String parolaG = gioco.getParola();
                int cnt = 0;
                if(!parolaG.equals(parolaUtentePrec)){
                    for(int i = 0; i < 5; i++){      
                        if((parolaUtentePrec.charAt(i)) != parolaG.charAt(i)){
                            cnt = i+1;
                            messaggio.setText("La " + cnt + " lettera contiene il carattere " + parolaG.charAt(i));
                            aiuto.setText(gioco.contaAiuti() + "🛟");
                            aiuto.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
                            break;
                            
                        }
                    }
                }
            }else{
                messaggio.setText("Aiuti finiti");
            }
        }
        
        if (e.getSource() == giocoNuovo) {
            tastiera.remove(giocoNuovo);
            tastiera.revalidate(); // Rendi valida la rimozione dei componenti
            tastiera.repaint(); // Ridisegna il pannello
            nuovaParola(); // Chiamata al metodo per generare una nuova parola
        }
    }
}