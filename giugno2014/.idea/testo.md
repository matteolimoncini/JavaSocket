
 **ESAME DI LABORATORIO DEL 10/06/14**

 **SERVER**
 Si implementi un server concorrente in C (multithread in java) che fornisca due tipologie di servizio:

 il primo di raccolta previsioni elaborate da svariati istituti meteorologici (attivo sulla porta 22222) e il
 secondo di diffusione delle previsioni aggiornate e di raccolta feedback sulla attendibilità delle stesse (attivo sulla porta 33333).

 Servizio raccolta previsioni (22222)
 Siano 'A', 'B', 'C' i tre istituti meteorologici deputati alla elaborazione delle previsioni del tempo
 e sia {'s', 'v', 'c', 'p', 'n', '-'} l'insieme delle possibili situazioni climatiche.
 
 Il server disporrà di una opportuna struttura dati (a cui si farà riferimento con il nome di main_record) in grado di mantenere,
 per ognuno dei tre istituti meteorologici (tipo char), la relativa previsione aggiornata (tipo char), inizialmente posta uguale a '-',
 nonché un giudizio di preferenza (tipo int), introdotto più avanti nel testo, inizialmente posto uguale a 0.
 
 Ricevuta una nuova richiesta di connessione, dopo averla instaurata, il server riceverà dal client una stringa contenente il proprio identificativo,/ secondo la seguente sintassi:
 - id id
 dove id ∈ {'A', 'B', 'C'}.
 
 Successivamente, il client invierà una sequenza di previsioni, ognuna secondo il formato:
 - forecast fc
 con fc ∈ {'s', 'v', 'c', 'p', 'n', '-'}, fino a quando deciderà di abbandonare il servizio.
 
 Il server, ricevute le singole previsioni, provvederà ad aggiornare main_record sovrascrivendo la vecchia previsione.
 
 La disconnessione non prevede alcun preavviso da parte del client: sarà il server a dover gestire correttamente eventuali errori causati da tale evento.

 Servizio diffusione previsioni e raccolta feedback (33333)
 Ricevuta una nuova richiesta di connessione, dopo averla instaurata,
 il server invierà al client una stringa contenente le previsioni dei tre istituti e il relativo giudizio di preferenza,
 opportunamente ordinati in ordine decrescente di giudizio, secondo la seguente sintassi:
 - A fcA rateA B fcB rateB C fcC rateC
 dove fcX ∈ {'s', 'v', 'c', 'p', 'n', '-'}, rateX ∈ ℕ, X ∈ {'A', 'B', 'C'} e dove si è assunto, a titolo esemplificativo rateA ≥ rateB ≥ rateC.
