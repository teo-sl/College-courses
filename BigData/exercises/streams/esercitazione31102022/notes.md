# Laziness di java stream
Il sistema cerca di fondere tutti i passi, limitando lo spostamento di dati. Non esamina tutto l'input se non necessario

Ciò non accade nella programmazione imperativa, coi classici for loop. 

- limit -> selezionare solo parte di input

Principio => nature is thrifty in all of its aspects.

In natura, molte leggi sono basate sul fatto che la natura cerca di trovare la strada più semplice, al fine di risparmiare energia. e.g. la bolla di sapone è qella forma poiché è il metodo di raggruppare un certo volume con la minima quantità di energia.

Inserendo .parallel nel codice, lo stesso codice può essere eseguito in parallelo. 

-Xmx12g per aumentare la memoria che java può usare 
