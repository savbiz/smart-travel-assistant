# Third-party components

English | [Italiano](THIRD_PARTY.it.md)

The source archive included separate library projects and binary dependencies. They are excluded from this repository; references in application imports and Eclipse project files are retained as historical build information. No redistribution permission for Jess is assumed.

| Component found in the original archive | Historical role | Archive treatment |
| --- | --- | --- |
| Jess implementation under `MainActivity/src/jess/`, its `scriptlib.clp`, compiled classes, and `mainactivity.jar` | Inference engine | Entire `MainActivity` library/example project excluded, including its nested Git metadata. Application code still imports Jess. |
| OpenBeans `openbeans-1.0.jar` | JavaBeans compatibility for the Android Jess integration | Excluded with `MainActivity`. |
| `google-play-services_lib` and `google-play-services.jar` | Google Maps integration | Entire vendored library excluded. |
| `appcompat_v7`, `android-support-v7-appcompat.jar`, and `android-support-v4.jar` | Android compatibility support | Library project and all support-library copies excluded. |
| `android-google-places-master` (`com.a2plab.googleplaces`) | Google Places queries and result models | Entire bundled source project and compiled JAR excluded. Application imports are preserved. |
| Google API/HTTP Client 1.14.1-beta and Gson 2.1 JARs | Dependencies of the Places library | Excluded with the Places project. |
| `forecastiolib-arcusweather-1.0.0.jar` | Forecast.IO client (`com.arcusweather.forecastio`) | Excluded. |
| `java-json.jar` (`org.json`) | JSON dependency bundled with the application | Excluded. |

Google Maps, Google Places, Forecast.IO, and PugliaEvents.it were external services or data sources. Their names describe the historical implementation; this archive does not establish their present availability or grant rights to their software or data. The original PugliaEvents RSS endpoint remains in application source as historical integration code.

Third-party components remain subject to their respective licenses and terms. Anyone reconstructing the historical environment must obtain the dependencies separately and establish the relevant permissions. This document does not grant a license to the application or to any external component.

## Provenance evidence from the thesis

The thesis provides the following attribution evidence (page numbers below are the printed page numbers; PDF viewer page numbers are six higher):

- **Menu icons:** the image index on page 153, entry 29 for `MenuActivity.java`, credits VisualPharm (`www.visualpharm.com`) and Custom Icon Design (`www.customicondesign.com`). This identifies external artwork sources, but does not identify the exact icon packs, map each PNG to a source, or provide redistribution terms. It does not establish the origin of the other backgrounds, launcher images, or splash screens.
- **Jess integration:** section 3.3.3, page 74, cites reference [80] for the input-router approach used to run Jess on Android. Reference [80], page 150, points to `kevin_thesis.pdf` in the University of Cape Town's 2012 `brenkel_makhubele` project archive. The university's [Medical Advisor System project page](https://projects.cs.uct.ac.za/honsproj/cgi-bin/view/2012/brenkel_makhubele.zip/MAS_MKHKUL002_BRNKEV008/speech.html) identifies Kevin Brenkel and Kulani Makhubele and describes a Jess Android port. This is a provenance lead; it does not establish whether specific application helper files were copied or adapted, or their redistribution terms.
- **Application/Jess interface:** section 4.2.4.2, pages 114–115, describes input/output routing and the `Question` class. The thesis documents their role but supplies no file-specific authorship or license statement for `Logic.java` or `Question.java`.
- **Jess license:** section 3.3.2, page 73, says an academic license was obtained. The thesis supplies no permission to redistribute the modified Jess implementation. That implementation remains excluded.
- **Other libraries:** bibliography references [78], [79], and [85], page 150, identify `kataklisma/android-google-places`, `kushanj1/ForecastIOLibrary`, and Google Code's OpenBeans project. Their bundled code and binaries remain excluded.

Source: [Smart Travel Assistant thesis](https://doi.org/10.5281/zenodo.23012254). The thesis PDF itself is not included in this repository.

## Application code and artwork

`expertsystem/Logic.java`, `expertsystem/Question.java`, and `loginregister/SessionManager.java` are retained as part of the supplied application source. They connect the application to Jess and manage question data and local sessions; they are not the excluded Jess engine implementation. The review did not establish an upstream code match or third-party copyright notice for these files. The thesis citation documents an integration approach, not file-level authorship. Their complete provenance has not been independently verified; retaining them is not a claim that every line was written from scratch.

All 30 original PNG files are excluded: `android-app/ic_launcher-web.png` and every PNG under `android-app/res/drawable-*/`. This covers menu icons, Facebook icons, launcher images, backgrounds, splash screens, and other bitmap artwork. The thesis credits third-party menu icons, but the exact asset-to-source mapping and redistribution terms could not be established. The other images could not be reliably separated by ownership. Their XML and Java references remain unchanged, and no replacement artwork has been introduced. The original artwork remains in the untouched source ZIP, outside this repository.
