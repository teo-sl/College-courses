# I transformer

Utilizzati in NLP, conversational chatbot, ecc...

Introdotto nel paper Attention is all you need.

## Text generation transformers

Riceve ingresso una stringa iniziale.

Durante la produzione è come se utilizzasse quanto già prodotto come input, riuscendo a individuare le relazioni tra le parti del testo, quelle più importanti e quelle meno importanti.

Anche le RNN fa qualcosa di simile, la le RNN hanno una finestra molto più piccola (LSTM, GRU ecc...). Il meccanismo dell'attention ha potenzialmente una finestra illimitata.

# Architettura (alto livello)

La struttura è quella di un encoder decoder.

L'output dell'encoder è un vettore continuo che rappresenta l'input, il decoder genera, step by step un output che, inoltre, viene dato nuovamente in ingresso al decoder.

![Architettura transformer](https://machinelearningmastery.com/wp-content/uploads/2021/08/attention_research_1.png)


## Encoder

- Input embedding: si ottiene una rappresentazione vettoriale di ogni parola
- positional encoding: aggiunge informazioni sulla posizione della parola all'interno del testo. Se abbiamo una posizione dispari usiamo il coseno, altrimenti il seno. Si sommano i due vettori (quello embedding e il positional). Si usa il seno e coseno per le proprietà lineari.

$$PE(pos,2i+1)=cos(\frac{pos}{100000^{2i/dmodel}})$$

$$PE(pos,2i)=sin(\frac{pos}{100000^{2i/dmodel}})$$

- encode layer: si mappano tutte le input sequence in rappresentazioni astratte continue che contengono le informazioni di apprendimento per l'intera sequenza. Vi sono due componeti principali:
  - multi-haeded attention 
  - una rete completamente connessa
  
per ognuno di questi viene aggiunto un layer di normalizzazione.

### Multi haeded attention module

![Architettura](imgs/Schermata%202022-12-11%20alle%2016.06.49.png)

Questo modulo usa una parte detta **self attention**, ciò permette alla rete di definire l'importanza di una parola rispetto alle altre all'interno del'input. 

L'input è dato a tre distinti layer completamente connessi (linear).

Questi vengono usati per generare i vettori di query, key e value.
 
Il significato di questi vettori è analogo al caso dei motori di ricerca:
- query, è il termine che ricechiamo
- keys, la ricerca viene mappata a una serie di chiavi (titoli di video, descrizioni ecc...) associati agli elementi candidati del database
- values, le best match ottenute (i.e. video, pagine web ecc...)


I vettori query sono moltiplicati per i vettori key, da questa moltiplicazione si ottiene una matrice di scores. Gli elementi di questa matrice ci dicono quanto è importante una parola rispetto alle altre. Maggiore è il valore, più importante è la parola.

La matrice degli score è poi normalizzata per $\sqrt{d_k}$. Si ottiene da questa la scaled version.

Tali valori vengono poi inviati alla softmax che li converte in probabilità.

$$softmax(x)_i = \frac{exp(x_i)}{\sum_j exp(x_j)}$$


Ciè che otteniamo sono gli attention weights, questi vengono poi moltiplicati per i vettori value. Si ottiene così vettori di output.

Naturalmente, i valori con maggiore score softmax avranno maggiore importanza.

Questo output vector viene dato in pasto a un livello lineare per essere processati. 

![](imgs/Schermata%202022-12-11%20alle%2018.01.47.png)


I vettori di query key e value sono precedentemente divisi in vettori n-dimensionali che, singolarmente, andranno a essere processati dalla self-attention head. Alla fine, i vettori di output vengono concatenati e dati in pasto ad un livello lineare. 



* **Sommario**: Quindi, l'output di un di un multi headed attention sono vettori che ci dicono quanto la data parola è importante rispetto alle altre (l'input è un positional input embedding).



### Residual Connection, Layer Normalization & Pointwise Feed-Forward

L'output del multi headed attention è sommato all'input originale (positional input encoding), questa è deta residual connection. Questo risultato è normalizzato e poi dato in pasto a un layer di feed forward (linear layer con ReLu).

![](imgs/Schermata%202022-12-11%20alle%2018.08.05.png)

L'output è poi sommato all'input e normalizzato. 

### Sommario encoder

I livelli di encoding possono essere posti in cascata per permettere al decoder di estrarre feature interessanti.

## Decoder

Generare text sequences. 

Struttura:

- 2 multi headed attention layers
- point wise feed forward layer con residual connection e layer normalization dopo ogni sublayer. Ogni sublayer ha un compito diverso


Il decoder è auto-regressivo il suo output viene dato in pasto al decoder stesso, oltre a ricevere input dall'encoder. Il decoder termina il suo lavoro quando genera un end token. 


### Output Embedding & Positional Encoding

L'input viene dato ad un output embedding e poi un positional encoding per avere informazioni sulla posizione per positiona embeddings. 

### Decoder Multi Headed Attention 1

L'output viene dato al primo decoder multi headed, che calcola l'attention score per gli input del decoder. 

Questo MHAL è diverso dagli altri, dal momento che il decoder è autoregressiv, gestisce i token parola per parola; il che vuol dire che tale livello ha accesso solo alla parola corrente e a quelle precedenti, non quelle future, che verranno generate solo successivamente.

Per evitare questo si usa un meccanismo detto masking, posto tra il layer di scale e quello di softmax. Viene quindi usata una lookhead mask, che viene applicata prima della softmax e dopo lo scaling.

![](imgs/Schermata%202022-12-11%20alle%2018.21.58.png)


La maschera è una matrice della stessa dimensione dello scaled scores e con 0 e $-\infty$. Una volta sommata, la triangolare alta è messa a - infinito. Una volta processati dalla softmax questi valori diventano 0 e non influenzano il risultato finale poiché corrispondono a valori di probabilità nulli. 


L'output di questo primo MHAL è un masked output vector che dice come il modello dovrà comportarsi sugli input del decoder.  


### Decoder Multi Headed Attention 2 e pointwise feed forward layer

Questo secondo MHAL riceve input dall'encoder e dal primo MHAL del decoder. Dall'encoder prende considera le query e le key, mentre dal decoder i values. Questo permette al decoder di capire quali encoder input sono rilevanti da porre il proprio focus. L'output di questo livello è dato in pasto a un point wise feed forward layer. 

### Linear classifier

L'output dell'ultmo pointwise feed forward layer viene dato a un linear layer che agisce da classificatore, tanto grande quante sono le classi a disposizione. e.g. se ci sono 10.000 classi per 10.000 parole l'output sarà un vettore di 10.000 elementi. Questo viene dato a un softmax, che ci da un valore di probabilità, l'indice del valore massimo sarà la parola predetta. L'output viene aggiunto poi alla lista dei decoder input.

Il decoder continua così fin quando un end token è prodotto (potrebbe essere l'ultimo indice del vettore). 


Il decodere può essere replicato, ricevendo, per ognuno, ingressi dall'encoder. 
















