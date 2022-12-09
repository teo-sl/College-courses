# Gestione di dati non standard

1. strategia
2. data preparation 
3. data splitting
4. modeling 
5. testing


Sino a ora ci siamo occupati del modeling. Faremo una panoramica su data preparation su dati meno standard.

# Dati categorici

e.g. colore capelli, occhi. Le tecniche sono completamente diverse, le tecniche di learning viste non funzionano. Non si può definire un concetto di distanza, e quindi non si possono applicare le tecniche di base: KNN, SVM ecc... 

Potrei associare dei valori interi, tuttavia, sto ordinando così i valori, definendo implicitamente una distanza, che però non ha sementica rilevante. 

Anche se avessi attributi come il giudizio di un tema: scarso, sufficiente, distinto. Ho un ordinamento, non è detto che le distanze siano corrispondenti.

Servono tecniche basate su frequenze di occorrenza ecc... Non sempre sono significative, il codice fiscale occorre una volta sola, ma non è significativo! 

Nè reti, nè tecniche classiche funzionano.

# Testi

Esistono diverse tecniche per trasformare le parole in vettori. 

## Similarità sintattica 

Tra due parole: buonanotte, buoonantte; sulle parole possono applicare una distanza. Le note sono

- **distanza di Hamming**

Normalmente si basano su una famiglia di distanze dette: di **editing** (il numero di operazioni di modifica da applicare una stringa per trasformarla in un'altra). Nel caso dell'esempio di prima, basta eliminare una o e aggiungerne una, la edit distance è 2.

Nella distanza di _hamming_ l'unica operazione ammissibile è la sostituzione di caratteri. Non posso fare inserimenti e cancellazioni. Nel caso di prima non posso inserire, devo sostituire tutti i caratteri diversi. 

Se la distanza è zero coindicono (è misura di distanza per tuple)

- **distanza di Lenshtein** : vi sono anche inserimenti e cancellazioni.

Esistono anche altre varianti,

- **distanza di Damerau** : si considera anche lo scambio di caratteri adiacenti
  
La lenshtein e damerau hanno costo maggiore di quella di hamming. Il costo di hamming è lineare, per lenshten e damerau ha costo quadratico. Per parole lunghe il costo può essere oneroso. Esistono algoritmi che approssimano queste distanze.


Dal punto di vista di Python, vi è un pacchetto "nltk", un pacchetto per il linguaggio naturale. 

        nltk.metrics

Contiene queste distanze

        edit_distance # distanza di edit in accordo a levenstein
    
Si può porre transposition=True per tener conto di scambio caratteri contigui (_Damerau_)

Queste sono distanza di tipo sintattico. 

## Fonetico

In base al contesto possono esserci distanze diverse, come quelle fonetiche. Utile per dati compilati a mano. Solitamente, due parole sono simili anche perché suonano simili. Chi ha inserito i dati potrebbe aver sentito qualcosa di simile.

Esiste un pacchetto

        phonetics

Converte una parola in una stringa fonetica. Dovrebbe dare un'idea si quanto due stringhe si somigliono foneticamente. 

        ss = [word, word]
        phonetics.metaphone(ss[0]) # converte in phonetico la prima parola

## Sinonimi

In nltk vi è un'altra distanza, basata su sinonimi. Corpus dove le parole sono considerate in base al significato. 

        import nltk.corpus
        wn = nltk.corpus.wordnet

        s_lion = wn.synsets('lion')[0] # senza lo zero da diversi significati di leone
        s_lion.definition()

  
Si può calcolare la similarità. wordnet è una rete dove i nodi sono le parole e gli archi indicano la similarità.

        s_lion.path_similarity(s_tiger)

Il valore resituito è una similarità, più alto più simile.


Questo si basa su sinonimi determinati in base all'uso del dizionario, esistono invece tecniche che trasformano le parole in vettori numerici secondo delle reti neurali che trasformano parole in vettori cercando di rispettare la distanza trai significati delle parole

## Reti neurali

La distanza tra uomo e donna è la stessa che sta tra re e regina. Se ho vettori del genere posso fare valutazioni vettoriali vere e proprie.

Il pacchetto di riferimento è:

        gensim

        import gensim.downloader as api

Carica i risultati di una rete neurale scritta da Google. Esistono diverse versioni con livelli di accuratezza crescenti.

        wv = api.load('word2vec.google-news.300')

Sono reti preaddrestate che trasformano parole in vettori.

        wv.most_similar(positive=['king','woman'],negative=['man'])
        >> ['queen']

Togliendo al concetto di re uomo, e aggiungendo donna ottengo regina

        jaguar +  italian - inglese  === fiat, ferrari ecc...

## Gestione di interi testi

Anche i testi vanno trasformati in vetttori 

Vi sono diverse fasi da analizzare, dentro nltk:

        import nltk
        import nltk.corpus

        sv = nltk.corpus.stopwords # parole frequenti ma senza una semantica significativa

Per la lingua inglese "i me we our ....". Queste parole andranno tolte dal testo, per evitare che i due testi vengano considerati simili per la presenza di questi elementi.

Tecniche usate:

- **bag of words** : costruisco un vocabolario con parole presenti in entrambi i documenti. Per ogni parola vedo quante volte è presente. Avrò un vettore con tante coordinate quante parole e per ogni documento il numero di occorrenze della parola nel documento.

        import sklearn.feature_extraction
        import skelarn.feature_extraction.text as sfe_text

        vectorizer sfe_text.CountVectorizer()

        X = vectorizer.fit_transform([DocA,DocB])

        vectorizer.get_feature_names() # restituisce il vocabolario di parole

Si può normalizzare rispetto alla lunghezza per trasformare in numeri reali.

Tecnica abbastanza basic.

- TF-IF : si basa su concetto di frequenza tempo. quanto è frequente parola in un documento. Tale parola è tanto importante quanto più è frequente in un documento e meno frequente negli altri. e.g. il verbo essere è frequente in tutti i documenti. La parola fuorigioco sarà frequente in un documento e meno in altri. 

Si può anche dire di togliere stop_word='english'

        v = TfidVectorizer(stop_words='english)

        v.fit_transform([docA, docB])


Con word to vec si può calcolare la distanza tra documenti

        wv.wmdistance(docA,docB)

## Riassunto

- Le distanze sintattiche vengono usate su dati genomici 
- le fonetiche su database con data entry manuali
- reti neurali più sofisticate 


# Serie temporali

vi è un pacchetto 

        dtw

Con una serie temporale, un concetto base (confrontare due elettrocardiogrammi) si cercano di allineare le due forme d'onda e dopo aver discretizzato in finestre, si allineano una tecnica simile all'algoritmo di Levensthein. 

        import dtw

        # serie discretizzate
        y1= np.array([....])
        y2 = np.array([.....])
        # vengono allineate
        alignement = dtw.acclearated.dtw(y1,y2,dist='euclidian')

        # allineamento di y1
        alignement[3][0] 
        # per allineare a y2. Minimizza distanza euclidea
        y1[alignement[3][0]] 
        


# File audio

Un pacchetto è

        librosa
        librosa.display

        file = 'c-chromatic-scale....'

        # x1 è sequenza dati, ampiezza onda
        # sr è sample rate
        # se inizialmente vi erano più canali fa la media
        x1, sr = librosa.load(file, mono=True) 

        # mostra ampiezza file audio
        librosa.diplay.waveshow(x1,sr=sr)
        plt.show()

        x = x1

        # trasformata di Fourier
        X = librosa.stft(x)

        # possiamo mostrare la wave nella scala delle frequenze
        librosa.display.specshow(librosa.....)
        plt.show()


Vi sono tantissimi dati da estrarre. Trasformata di Fourier, ma anche altre caratteristiche vettoriali. Quando si lanciano gli algoritmi di ML, vogliamo ad esempio classificare le canzoni in base al genere. Le feature da usare dipendono dal task. Potrebbe bastare lo spettro, ma non sempre. Serve poter estrarre delle features specifiche. Sono sempre estratte dal file audio. Potremmo creare una rete neurale abbastanza complessa che usa solo la wave X. Il fatto è che se mi serve lo spettro, dovrò avere un'intera parte della rete che non fa altro che calcolare lo spettro. Questa feature potrebbe essere data direttamente in input. 

Pensiamo all'analisi dei testi di prima, per convertire una parola in un vettore serve una rete di 1.6 GB, tra l'altro, realizzata dagli esperti di Google! Ha senso usare una rete solo a valle di una feature extraction.

        librosa.feature

        # basati su frequenza ma sono di meno, più specifici
        # il range di feature principali basati su frequenza
        librosa.feature.mfcc()

        # prendere solo i centroidi dall'analisi spettrale
        spectral_centroid()

        # quante volte la funzione d'onda attraversa lo zero
        # è stato dimostrato che il genere influenza quante volte la funzione d'onda attraversa lo zero (per rock molte volte)
        zero_crossings()

        # energia collegata allo spettro
        spectral_rollof()

        # metrica basata sullo spettro
        rms()

        # per vederle tuttte
        librosa.feature;


Esiste anche, volendo lavorare sulla musica, trasformata di fuourier su note musicali

        chroma.stft(x)

        # effettua il display dello specshow

Vengono meglio rappresentate le note musicali. Lo spettrogramma è più generale, questo è più specifico per le note musicali. 


Vi è anche l'aspetto relativo alla riduzione del rumore.

Vi sono reti che si occupano solo di riduzione/eliminazione del rumore. 
Normalemente si prendono audio con solo il rumore, si allenano reti che imparano il rumore, e poi si usa la rete per eliminare il rumore. 


# Conclusioni

Con dati particolari, la parte difficile è come trasformare i dati in vettori. Con testi bow e ifdf, con **l'audio** vi è **più scelta per estrazione delle features**.

Ha impatto molto forte sulla fase di analisi. Le reti potrebbbero non essere in grado di imparare senza le feature giuste (a meno di avere delle reti immense che estraggono automaticamente le features). Sia per dati come testi che per dati audio.