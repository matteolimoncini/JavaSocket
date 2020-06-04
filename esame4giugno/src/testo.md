Tema d'esame:
Si realizzi uno sistema di telelavoro e sharing di contenuti tra utenti come segue: il sistema
comprende un server, che coordina la collaborazione tra utenti, e da 3 coppie di utenti, tali
che A lavora con B; C lavora con D; E lavora con F. Il comportamento dei processi è definito
dalle seguenti specifiche:
SERVER
* comunica con i processi utente utilizzando i servizi di UDP
* se riceve una registrazione da un processo utente:
- nel messaggio di registrazione riceve il nome del processo utente
- se il nome è unico (nessun utente è registrato con quel nome al momento, e il nome è
nell'insieme ammesso descritto sopra), il server rende ACK all'utente e ne ricorda nome e
indirizzo
- altrimenti il server rende NACK all'utente
* quando il server riceve da un utente X in {A,B,C,D,E,F} registrato l'intenzione di modificare il
contenuto, opera come segue:
- controlla se esiste già un contenuto per la coppia di utenti di cui X fa parte
- in caso affermativo invia a X il valore corrente del contenuto e l'identità dell'ultimo utente che
lo ha modificato, altrimenti invia "NIL"
- ricorda il contenuto come in stato di modifica
* quando il server riceve da X - per il contenuto della coppia di cui X fa parte, e in modifica -
un nuovo valore, aggiorna il contenuto, ricorda l'identità di X come ultimo modificatore, e invia
a X un riscontro del successo dell'operazione. Il contenuto esce dallo stato di modifica.
* il server non termina mai.
UTENTE:
* comunica con il server utilizzando i servizi di UDP
* alla partenza riceve da tastiera il nome da utilizzare per la registrazione con il server
* manda al server un messaggio di registrazione con il proprio nome e ne attende risposta
- se la risposta è ACK, l'utente continua con il funzionamento descritto ai punti successivi
- altrimenti l'utente notifica un messaggio di errore a video e torna a chiedere l'inserimento da
tastiera di un nome
* un utente registrato attende l'indicazione da tastiera di voler modificare il contenuto
condiviso (*)
- invia al server la richiesta di modifica
- riceve la risposta dal server e la notifica a video
- chiede da tastiera l'inserimento del contenuto modificato e lo invia al server
- attende la risposta dal server e torna al punto (*) sopra
* nessun utente termina mai.
I contenuti sono stringhe di al massimo 20 B.
La definizione del formato dei messaggi, così come i dettagli implementativi non specificati
sopra, sono a discrezione dello studente. Tutti i messaggi ricevuti da ogni processo devono
essere mostrati a video.