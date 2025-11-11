[ --- 📚 Gestione Biblioteca --- ]   
Questo progetto è una semplice applicazione di gestione di una biblioteca, che consente di gestire utenti, libri e prestiti.

🧱 Tecnologie utilizzate    
PostgreSQL → per la gestione e il salvataggio dei dati.  
Spring Boot → come framework backend, con suddivisione in Model, Controller e View (qui chiamato erroneamente GUI).  
JWT (JSON Web Token) → per autenticazione e autorizzazione degli utenti.  

🚀 Avvio del programma  
Al primo avvio verrà chiesto di creare un utente.  
Consiglio di creare un utente Admin, inserendo come codice segreto: admin bello  
Non mettere niente crea un User semplice.  
In questo modo si può accedere a tutte le funzionalità del programma.  

💡 Note  
Le password sono hashate prima di essere inserite nel database.  
Alcune aree sono visibili solo se sei un Admin.  



[ --- 🧩 Struttura del progetto --- ]  
Di seguito vi è una breve descrizione delle principali classi e componenti del programma (scritta perché io altrimenti dopo mesi mi perdo).  

🖥 GUI  
MainFrame: Gestisce l’intera interfaccia grafica e il passaggio tra i vari pannelli dell’applicazione.  
  
   🏠 Account  
   HomePage:  
      1. Home: consente di scegliere tra Registrazione e Accesso.  
      2. Registrazione: permette di creare un nuovo utente (inserendo il codice "admin bello" si crea un Admin).  
      3. Accesso: dopo aver creato un utente, puoi effettuare il login e accedere alle altre aree del programma.  

   📊 Dashboard  
   Dopo l’accesso, l’interfaccia mostra:  
      1. Una barra superiore con le Opzioni.  
      2. Un menu laterale sinistro.  
      3. Una tabella principale a destra (inizialmente vuota).  

   ⚙️ Opzioni:  
      1. Account → visualizza le informazioni dell’account corrente e consente di disconnettersi.  
      2. Esporta → esporta la tabella visibile in un file.txt  
      3. Visualizza → cambia la tabella e il menu laterale mostrati (ogni cambio resetta i dati visualizzati).  
   📋 Menu laterali  
      MenuIstruzioni → mostra un testo introduttivo che guida l’utente nella navigazione del programma.  
      MenuUtente → permette di ricercare o eliminare utenti esistenti (accessibile solo agli Admin).  
      MenuLibro:  
         UtenteLibro (per tutti) → prenotare, restituire e cercare libri  
         AdminLibro (solo Admin) → creare, modificare o eliminare libri  
      MenuPrestito → consente di visualizzare prestiti, generare report sui libri più richiesti e sugli utenti più attivi (Accessibile solo agli Admin).  
   📑 Tabella  
   Contiene tutti i metodi e dati locali relativi alla tabella attualmente visualizzata.  
