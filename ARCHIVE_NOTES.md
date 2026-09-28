# Archive notes

English | [Italiano](ARCHIVE_NOTES.it.md)

Smart Travel Assistant was developed in 2013–2014 as a Bachelor's thesis project using Java and Android Eclipse/ADT. The original manifest declares minimum SDK 8 and target SDK 19; `project.properties` targets `Google Inc.:Google APIs:19`.

## What was preserved

The application package structure, XML resources, and ten tourism CLP files are retained. The rules were copied byte for byte from the original installation package into `expert-system/rules/`. The application still loads them from a `clp` directory at the root of Android external storage, using `Environment.getExternalStorageDirectory()`. Moving them in this repository does not change that original runtime convention or bundle them as Android assets.

Source bytes and original encodings were preserved except for credential replacements and the two classpath paths described below. Some Java files contain legacy single-byte Italian text; the archive does not normalize their encoding. Eclipse `.project` and `.classpath` files retain useful ADT build information. Relative library references in `project.properties` deliberately point to dependencies that are no longer bundled.

## What was excluded or sanitized

- `bin/`, `gen/`, compiled classes, the installation APK, all 17 original JAR files, caches, `.settings/`, `local.properties`, `.DS_Store`, and `__MACOSX/` metadata were excluded.
- The four bundled library projects (`MainActivity`, `google-play-services_lib`, `appcompat_v7`, and `android-google-places-master`) were excluded. This includes Jess implementation source, its support rules and binaries, an example activity, OpenBeans, and nested Git metadata. See [THIRD_PARTY.md](THIRD_PARTY.md).
- All 30 PNG assets were excluded because their redistribution rights could not be verified. XML and Java references remain unchanged, so missing drawable resources are an additional build limitation. See [THIRD_PARTY.md](THIRD_PARTY.md).
- The Google Maps credential in `android-app/AndroidManifest.xml` was replaced with `YOUR_GOOGLE_MAPS_API_KEY`.
- Google Places credentials, including commented occurrences, in `android-app/src/com/example/ResultActivity.java` were replaced with `YOUR_GOOGLE_PLACES_API_KEY`. Its Forecast.IO credential was replaced with `YOUR_FORECAST_IO_API_KEY`. Generated and compiled copies containing credentials were excluded.
- Two absolute personal Windows paths in `.classpath` were replaced with relative `libs/` paths naming the original missing dependencies.
- The original Eclipse import instructions and packaged DOC/PDF user guide were not copied. They describe a bundled installation that this source-only archive does not supply. The canonical thesis is linked through its [Zenodo DOI](https://doi.org/10.5281/zenodo.23012254); no thesis PDF is bundled.

The original ZIP was left untouched. Cleanup was performed in a separate worktree, without importing the nested library Git history. No open-source LICENSE file was added.

## Build and runtime limitations

This is a historical reference archive, not a verified runnable release. No build or device test has been performed. Missing Jess and other libraries, omitted image resources, removed credentials, the Eclipse/ADT build environment, external-storage assumptions, and legacy service integrations prevent treating it as a self-contained modern Android project. Restoring the dependencies alone does not establish that the old APIs still function.

The original application logic remains unchanged, including its local password storage and logging, error handling, and age/gender assumptions in the rules. It has not been hardened for current use. No application architecture, algorithms, package names, or rules were rewritten.

## Preservation decision

The three application helpers `Logic.java`, `Question.java`, and `SessionManager.java` are retained unchanged. Their use of Jess or common Android patterns does not by itself establish third-party ownership. The thesis citations and the limits of the provenance review are recorded in [THIRD_PARTY.md](THIRD_PARTY.md). Known bundled libraries and unverified artwork are excluded; application source is not rewritten to compensate for those omissions.
