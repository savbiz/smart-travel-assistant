# Componenti di terze parti

[English](THIRD_PARTY.md) | Italiano

L’archivio originale conteneva progetti di libreria separati e dipendenze binarie. Sono esclusi da questo repository; i riferimenti negli import dell’applicazione e nei file di progetto Eclipse sono conservati come informazioni storiche per la compilazione. Non si presume di avere il permesso di ridistribuire Jess.

| Componente presente nell’archivio originale | Ruolo nel progetto | Trattamento nell’archivio |
| --- | --- | --- |
| Implementazione Jess in `MainActivity/src/jess/`, relativo `scriptlib.clp`, classi compilate e `mainactivity.jar` | Motore inferenziale | Escluso l’intero progetto di libreria/esempio `MainActivity`, compresi i metadati Git annidati. Il codice applicativo continua a importare Jess. |
| OpenBeans `openbeans-1.0.jar` | Compatibilità JavaBeans per l’integrazione di Jess in Android | Escluso insieme a `MainActivity`. |
| `google-play-services_lib` e `google-play-services.jar` | Integrazione Google Maps | Esclusa l’intera libreria incorporata. |
| `appcompat_v7`, `android-support-v7-appcompat.jar` e `android-support-v4.jar` | Supporto di compatibilità Android | Esclusi il progetto di libreria e tutte le copie delle librerie di supporto. |
| `android-google-places-master` (`com.a2plab.googleplaces`) | Query Google Places e modelli dei risultati | Esclusi l’intero progetto sorgente e il JAR compilato. Gli import applicativi sono conservati. |
| JAR Google API/HTTP Client 1.14.1-beta e Gson 2.1 | Dipendenze della libreria Places | Esclusi insieme al progetto Places. |
| `forecastiolib-arcusweather-1.0.0.jar` | Client Forecast.IO (`com.arcusweather.forecastio`) | Escluso. |
| `java-json.jar` (`org.json`) | Dipendenza JSON inclusa nell’applicazione | Escluso. |

Google Maps, Google Places, Forecast.IO e PugliaEvents.it erano servizi o fonti di dati esterni. I loro nomi descrivono l’implementazione storica; questo archivio non ne attesta la disponibilità attuale e non concede diritti sul relativo software o sui dati. L’endpoint RSS originale di PugliaEvents resta nel sorgente come parte dell’integrazione storica.

I componenti di terze parti restano soggetti alle rispettive licenze e condizioni. Chi intende ricostruire l’ambiente storico deve ottenere separatamente le dipendenze e verificare le relative autorizzazioni. Questo documento non concede una licenza sull’applicazione o sui componenti esterni.

## Provenienza documentata nella tesi

La tesi fornisce i seguenti riferimenti di attribuzione. I numeri indicati sono quelli stampati sulle pagine; nel visualizzatore PDF corrispondono a sei pagine in più.

- **Icone del menu:** l’indice delle immagini a pagina 153, voce 29 relativa a `MenuActivity.java`, attribuisce le icone a VisualPharm (`www.visualpharm.com`) e Custom Icon Design (`www.customicondesign.com`). Identifica quindi fonti esterne, ma non i pacchetti esatti, la corrispondenza tra ciascun PNG e la sua fonte o i termini di ridistribuzione. Non chiarisce l’origine degli altri sfondi, delle immagini del launcher o delle schermate iniziali.
- **Integrazione Jess:** la sezione 3.3.3, pagina 74, cita il riferimento [80] per l’approccio basato sul router di input utilizzato per eseguire Jess su Android. Il riferimento [80], a pagina 150, rimanda a `kevin_thesis.pdf` nell’archivio del progetto `brenkel_makhubele` del 2012 dell’Università di Città del Capo. La [pagina universitaria del progetto Medical Advisor System](https://projects.cs.uct.ac.za/honsproj/cgi-bin/view/2012/brenkel_makhubele.zip/MAS_MKHKUL002_BRNKEV008/speech.html) indica Kevin Brenkel e Kulani Makhubele e descrive un porting di Jess su Android. È un indizio sulla provenienza: non dimostra se specifici file di supporto siano stati copiati o adattati, né ne chiarisce i termini di ridistribuzione.
- **Interfaccia tra applicazione e Jess:** la sezione 4.2.4.2, pagine 114–115, descrive il reindirizzamento di input/output e la classe `Question`. La tesi ne documenta il ruolo, ma non contiene dichiarazioni di paternità o licenza specifiche per `Logic.java` e `Question.java`.
- **Licenza Jess:** la sezione 3.3.2, pagina 73, dichiara che è stata ottenuta una licenza accademica. La tesi non fornisce un’autorizzazione a ridistribuire l’implementazione modificata di Jess, che resta esclusa.
- **Altre librerie:** i riferimenti bibliografici [78], [79] e [85], pagina 150, identificano `kataklisma/android-google-places`, `kushanj1/ForecastIOLibrary` e il progetto OpenBeans su Google Code. Il codice e i binari inclusi nell’archivio originale restano esclusi.

Fonte: [tesi Smart Travel Assistant](https://doi.org/10.5281/zenodo.23012254). Il PDF della tesi non è incluso nel repository.

## Codice applicativo e immagini

`expertsystem/Logic.java`, `expertsystem/Question.java` e `loginregister/SessionManager.java` sono conservati come parte dei sorgenti applicativi forniti. Collegano l’applicazione a Jess e gestiscono i dati delle domande e le sessioni locali; non sono l’implementazione del motore Jess esclusa dall’archivio. La verifica non ha stabilito una corrispondenza con codice originale esterno o un avviso di copyright di terzi per questi file. La citazione nella tesi documenta un approccio di integrazione, non la paternità dei singoli file. La loro provenienza completa non è stata verificata in modo indipendente; conservarli non equivale ad affermare che ogni riga sia stata scritta da zero.

Sono esclusi tutti i 30 PNG originali: `android-app/ic_launcher-web.png` e ogni PNG in `android-app/res/drawable-*/`. Comprendono icone del menu, icone Facebook, immagini del launcher, sfondi, schermate iniziali e altre immagini bitmap. La tesi attribuisce a terzi alcune icone del menu, ma non è stato possibile stabilire l’esatta corrispondenza tra immagini e fonti o i termini di ridistribuzione. Non è stato possibile distinguere con affidabilità la titolarità delle altre immagini. I riferimenti XML e Java restano invariati e non sono state introdotte immagini sostitutive. Le immagini originali restano nello ZIP sorgente intatto, esterno a questo repository.
