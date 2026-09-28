# Note sull’archivio

[English](ARCHIVE_NOTES.md) | Italiano

Smart Travel Assistant è stato sviluppato nel 2013–2014 come progetto di tesi di laurea triennale, utilizzando Java e Android Eclipse/ADT. Il manifest originale dichiara SDK minimo 8 e SDK di destinazione 19; `project.properties` indica `Google Inc.:Google APIs:19`.

## Contenuti conservati

Sono conservati la struttura dei package, le risorse XML dell’applicazione e i dieci file CLP del dominio turistico. Le regole sono state copiate byte per byte dal pacchetto di installazione originale in `expert-system/rules/`. L’applicazione continua a caricarle da una directory `clp` nella radice della memoria esterna Android, tramite `Environment.getExternalStorageDirectory()`. Il loro spostamento nel repository non modifica questa convenzione originale e non le incorpora come risorse Android di tipo asset.

I byte e le codifiche originali dei sorgenti sono stati mantenuti, salvo le sostituzioni delle credenziali e dei due percorsi nel classpath descritte sotto. Alcuni file Java contengono testo italiano in una codifica storica a singolo byte; l’archivio non ne normalizza la codifica. I file Eclipse `.project` e `.classpath` conservano informazioni utili sull’ambiente ADT. I riferimenti relativi alle librerie in `project.properties` rimandano intenzionalmente a dipendenze non più incluse.

## Contenuti esclusi o ripuliti

- Sono stati esclusi `bin/`, `gen/`, classi compilate, APK di installazione, tutti i 17 JAR originali, cache, `.settings/`, `local.properties`, `.DS_Store` e metadati `__MACOSX/`.
- Sono stati esclusi i quattro progetti di libreria inclusi nell’archivio originale: `MainActivity`, `google-play-services_lib`, `appcompat_v7` e `android-google-places-master`. Comprendevano l’implementazione di Jess, le sue regole di supporto e i binari, un’activity di esempio, OpenBeans e metadati Git annidati. Si veda [THIRD_PARTY.it.md](THIRD_PARTY.it.md).
- Sono stati esclusi tutti i 30 PNG perché non è stato possibile verificarne i diritti di ridistribuzione. I riferimenti XML e Java restano invariati: le risorse grafiche mancanti costituiscono quindi un ulteriore limite alla compilazione. Si veda [THIRD_PARTY.it.md](THIRD_PARTY.it.md).
- La credenziale Google Maps in `android-app/AndroidManifest.xml` è stata sostituita con `YOUR_GOOGLE_MAPS_API_KEY`.
- Le credenziali Google Places in `android-app/src/com/example/ResultActivity.java`, comprese le occorrenze nei commenti, sono state sostituite con `YOUR_GOOGLE_PLACES_API_KEY`. La credenziale Forecast.IO nello stesso file è stata sostituita con `YOUR_FORECAST_IO_API_KEY`. Le copie generate e compilate contenenti credenziali sono state escluse.
- Due percorsi assoluti personali di Windows in `.classpath` sono stati sostituiti con percorsi relativi in `libs/`, mantenendo i nomi delle dipendenze originali mancanti.
- Le istruzioni originali per l’importazione in Eclipse e la guida all’uso in formato DOC/PDF non sono state copiate. Descrivono un’installazione completa che questo archivio dei sorgenti non fornisce. La copia di riferimento della tesi è raggiungibile tramite il [DOI Zenodo](https://doi.org/10.5281/zenodo.23012254); il PDF della tesi non è incluso.

Lo ZIP originale è rimasto intatto. La pulizia è stata eseguita in una directory di lavoro separata, senza importare la cronologia Git annidata della libreria. Non è stato aggiunto un file LICENSE con licenza open source.

## Limiti di compilazione ed esecuzione

Questo è un archivio storico di consultazione, non una versione eseguibile verificata. Non sono state eseguite prove di compilazione o su dispositivo. L’assenza di Jess e delle altre librerie, l’omissione delle immagini, la rimozione delle credenziali, l’ambiente Eclipse/ADT, le assunzioni sulla memoria esterna e le integrazioni con servizi dell’epoca impediscono di considerarlo un progetto Android moderno autosufficiente. Il solo ripristino delle dipendenze non dimostra che le vecchie API siano ancora funzionanti.

La logica originale dell’applicazione è rimasta invariata, inclusi la memorizzazione locale delle password e i messaggi di log, la gestione degli errori e le assunzioni su età e genere nelle regole. Non sono stati effettuati interventi per adeguarne la sicurezza all’uso attuale. Architettura, algoritmi, nomi dei package e regole non sono stati riscritti.

## Scelta di conservazione

I tre file di supporto applicativo `Logic.java`, `Question.java` e `SessionManager.java` sono conservati senza modifiche. L’uso di Jess o di comuni schemi di programmazione Android non dimostra di per sé una titolarità di terzi. I riferimenti alla tesi e i limiti della verifica della provenienza sono documentati in [THIRD_PARTY.it.md](THIRD_PARTY.it.md). Le librerie esterne note e le immagini non verificate sono escluse; i sorgenti applicativi non sono riscritti per compensare queste omissioni.
