 **SERVER**
 Si implementi un server multiprocesso in C (multithread in java) che fornisca indicazioni sul distributore di benzina più conveniente.
 Nel dettaglio, il server principale, attivo sulla porta 55555, si occuperà esclusivamente di ricevere nuove richieste di connessione;
 demanderà invece ai processi figli l'erogazione del servizio ai client connessi.
 
 Il server principale disporrà di due variabili intere: distr {1,…,7} e prezzo inizializzate nell'ordine a 1 e 9999,
 che si riferiscono rispettivamente all'id del distributore di benzina finora risultato più conveniente
 e al prezzo al litro praticato dal benzinaio espresso in millesimi di euro
 (questo significa che se la benzina costa ad esempio 1 euro e 65 centesimi al litro, prezzo assumerà valore 1650).
 
 Ricevuta una nuova richiesta di connessione, verrà istanziato un nuovo processo figlio.
 Questi invierà dapprima al client connesso la coppia (distr, prezzo) secondo la sintassi seguente:
 - distr prezzo
 
 per poi ricevere da questi coppie (newdistr, newprezzo) avvalendosi della stessa sintassi.
 
 La singola coppia si riferisce al gestore e relativo prezzo praticato che l'utente, tramite il client,
 comunica al server così da mantenerlo aggiornato.
 
 Per tutta la durata della connessione, il client è libero di inviare al server un numero indeterminato di coppie;
 sarà compito del processo figlio selezionare solo quella coppia che risulta più conveniente (o egualmente conveniente ma
 ricevuta successivamente), sovrascrivendo conseguentemente le due variabili (distr, prezzo).
 
 Nel caso nessuna delle coppie inviate dal client risulti più conveniente di quella originariamente memorizzata nel processo figlio,
 nessuna modifica sarà effettuata.
 
 Al termine del servizio, ovvero quando la connessione verrà rilasciata, il processo figlio restituirà al genitore la coppia (distr, prezzo),
 opportunamente codificata nel valore di ritorno1.
 
 La codifica prevede di usare i 3 bit più significativi degli 8 a disposizione per distr (per intenderci, sono i bit più a sinistra),
 e i 5 bit meno significativi per approssimare la differenza prezzo – 1550 all'intero più vicino divisibile per 8
 (assumendo che prezzo non possa essere né inferiore a 1550 – assunzione realistica – né maggiore di 1798 millesimi di euro – assunzione un
 po' azzardata, di questi tempi...).
 
 Ad esempio se la differenza fosse pari a 30, il valore approssimato all'intero più vicino divisibile per 8 sarebbe pari a 32;
 se fosse 34 diverrebbe 32, convenendo di associare a 36 il valore 40.
 
 Il server principale si occuperà così di recuperare il valore di ritorno di ogni processo figlio che abbia terminato la propria esecuzione e,
 previa opportuna decodifica1, provvederà ad aggiornare la propria copia di variabili distr e prezzo, qualora il prezzo attualmente decodificato sia
 inferiore o uguale al valore memorizzato.