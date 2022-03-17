# Kildekode for persistentlaget

Persistentlaget bruker [Jackson-biblioteket](http://github.com/FasterXML/Jackson) for å serialisere objektene i domenelaget til [JSON](https://www.json.org/json-en.html). 

For hver klasse i [domenelaget](../core/) finnes tilsvarende serialisering og deserialiseringsklasser. Disse blir tatt i bruk i [brukergrensesnittet](../ui).