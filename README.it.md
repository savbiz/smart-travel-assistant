# Smart Travel Assistant

[English](README.md) | Italiano

Smart Travel Assistant è un sistema esperto su Android per raccomandazioni turistiche personalizzate. L’ho sviluppato nel 2013–2014 come progetto di tesi di laurea triennale in Informatica presso l’Università degli Studi di Bari Aldo Moro, nell’ambito del progetto Puglia@Service.

L’applicazione combina il profilo dell’utente con domande su preferenze ed esigenze per suggerire luoghi in cui mangiare, attrazioni culturali, attività di svago ed eventi. Jess (Java Expert System Shell) è il motore inferenziale; dieci file CLP contengono le regole del dominio turistico. Il codice Android collega i risultati delle regole alle ricerche e ai filtri basati sul contesto: posizione, distanza, meteo, temperatura e apertura attuale dei luoghi. Gli eventi provengono dal feed RSS di PugliaEvents.it.

L’implementazione utilizza Java, risorse Android in XML, SQLite per i profili locali e la cronologia dei suggerimenti, le API Google Maps e Places e i dati meteo di Forecast.IO. I dati esterni sono gestiti in formato JSON e RSS/XML.

## Contenuto del repository

- `android-app/` — codice dell’applicazione, risorse XML, manifest e file di progetto Eclipse/ADT.
- `expert-system/rules/` — regole CLP originali del dominio turistico, conservate senza modifiche.
- [ARCHIVE_NOTES.it.md](ARCHIVE_NOTES.it.md) — scelte di conservazione e limiti di compilazione.
- [THIRD_PARTY.it.md](THIRD_PARTY.it.md) — dipendenze esterne escluse dall’archivio.

## Stato del progetto

Questo repository conserva un progetto di tesi a scopo storico e di consultazione. È stato sviluppato nell’ambiente Android Eclipse/ADT e ha come destinazione la piattaforma Google APIs Android 19. Dipende da servizi e librerie dell’epoca, non inclusi qui. Le credenziali API sono state sostituite con segnaposto. Le immagini PNG originali sono escluse perché non è stato possibile verificarne i diritti di ridistribuzione; i riferimenti nei layout e nel manifest sono conservati.

L’archivio non è autosufficiente per la compilazione e non è stato sottoposto a prove di compilazione. Non si prevede che possa essere compilato con gli strumenti Android moderni senza un sostanziale lavoro di migrazione. I nomi dei package, gli algoritmi e il comportamento delle regole sono stati mantenuti; non è stato effettuato alcun aggiornamento tecnologico. Le note dell’archivio descrivono il posizionamento originale dei file delle regole sul dispositivo.

## Tesi

*Smart Travel Assistant: un sistema esperto di supporto al turismo su Android*  
[Copia di riferimento della tesi su Zenodo](https://doi.org/10.5281/zenodo.23012254)

Questo repository non concede una licenza open source. I componenti esterni restano soggetti alle rispettive licenze.
