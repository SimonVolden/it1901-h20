# kildekode for brukergrensesnittet

Brukergrensesnittet er laget med JavaFX og FXML.

Brukergrensesnittet består av flere scener som er koblet til hverandre ved hjelp av diverse knapper, og hver scene har en egen kontroller-klasse som håndterer logikk og funksjoner.
Kontrollerne har mulighet til å sende informasjon til andre kontrollere, ved hjelp av initData()-metoder. Flere kontrollere har også mulighet til å laste inn og skrive til fil.

Flere av scenene har teksfelt man kan interagere med, enten ved å skrive inn tekst, eller ved å trykke på tekstfelt som inneholder informasjon (f.eks. en tidligere treningsøkt).

I workout-history-scenen, kan man trykke på tidligere treningsøkter for å se ytterligere detaljer, samt muligheten til å lagre treningsøkten til "Saved workouts".
I saved-workouts-scenen kan man se igjennom lagrede treningsøkter, og man har mulighet til å trykke på enkelt-økter for å vise mer informasjon om treningsøkten.