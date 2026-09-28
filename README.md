# Smart Travel Assistant

English | [Italiano](README.it.md)

Smart Travel Assistant is an Android knowledge-based expert system for personalized tourism recommendations. I developed it in 2013–2014 as my Bachelor's thesis in Computer Science at the University of Bari Aldo Moro, within the Puglia@Service project.

The application combines a user profile with questions about preferences and needs to suggest food, cultural attractions, leisure activities, and events. Jess (Java Expert System Shell) runs the inference engine; ten CLP files hold the tourism rules. The Android code connects the rule results to searches and context-aware filtering, including location, distance, weather, temperature, and whether places are currently open. Events come from the PugliaEvents.it RSS feed.

The implementation uses Java, Android XML resources, SQLite for local profiles and recommendation history, Google Maps and Places APIs, and Forecast.IO weather data. JSON and RSS/XML are used for external data.

## Repository

- `android-app/` — application source, XML resources, manifest, and Eclipse/ADT project files.
- `expert-system/rules/` — the original tourism CLP rules, preserved without changes.
- [ARCHIVE_NOTES.md](ARCHIVE_NOTES.md) — preservation choices and build limitations.
- [THIRD_PARTY.md](THIRD_PARTY.md) — external dependencies excluded from this archive.

## Historical status

This repository preserves a thesis project for historical and reference purposes. It was developed with the Android Eclipse/ADT ecosystem and targets the Google APIs Android 19 platform. It depends on legacy services and libraries that are not bundled here. API credentials have been replaced with placeholders. The original PNG artwork is omitted because its redistribution rights could not be established; layout and manifest references are preserved.

The archive is not a self-contained build and has not been build-tested. It is not expected to build on a modern Android toolchain without substantial migration work. The original package names, algorithms, and rule behavior have been retained; no modernization has been attempted. See the archive notes for the original rule-file deployment convention.

## Thesis

*Smart Travel Assistant: un sistema esperto di supporto al turismo su Android*  
[Canonical thesis on Zenodo](https://doi.org/10.5281/zenodo.23012254)

No open-source license is granted by this repository. External components remain subject to their respective licenses.
