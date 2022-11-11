luca.ferragina@unical.it

$$min \lambda || w||^2 +\frac{1}{m}\sum\xi_i$$

$$\lambda_i (<w,x_i>+b) \ge 1- \xi_i $$

$$\xi_i \ge 0$$

Da dove deriva C di scikitlearn? Si moltiplica la loss per $1/\lambda$


$$||w||^2 +\frac{1}{\lambda}\frac{1}{m}\sum \xi_i$$

Quindi $C=\frac{1}{\lambda}$

se c->0 (lambda->infty) si va verso una hard svm
se c->infty (lambda->0) il termine con gli slcack contano di più, quindi ammettiamo molte più missclassificazioni


Il valore della decision function è 

$$<w,x_i>+b$$

non è esattamente la distanza, per ottenerla è necessario dividere per la norma di w

$$dist(w,x_i)=\frac{<w,x_i>+b}{||w||}$$

Coi coefficienti duali alpha, possiamo esprimere w ottimo come combinazione lineare dei vetttori di supporto

$$w = \sum \alpha_i v_i$$

dove $v_i$ sono i vettori di supporto


# Funzioni kernel

$$\varphi : \real^n \rightarrow \real^N$$

poiché N>>n vorremmo calcolare i prodotti scalari in N.

Esistono le funzioni kernel

$$K : \real^n \times \real^n \rightarrow \real$$

$$K(x,x^{'}) = <\phi(x),\phi(x^{'})>$$

## Kernel polinomiale

$$K_{d\gamma r}=(\gamma <x,x^{'}>_{\real^n}+r)^d = 0$$

Nel caso più semplice

$$\gamma = 1, r = 0, d=2$$

formula iperpiano 

$$w_1 x_1^2 + w_2 x_2^2 +\sqrt{2} w_3 x_1 x_2+ b $$

Si tratta dell'equazione di una conica, i.e. una circonferenza in questo caso.


Con un kernel polinomiale di grado 2 possiamo ottenere curve quadratiche, di grado al max 2, con una sola curva. con kernel di grado superiore potremmo fare più curve. 


# Mnist

Gli oggetti appartengono al $\real^{28\times28}$

Riduciamo la dimensionalità con PCA (principal common analysisi): 


$$\real^N \rightarrow \real^n$$

Dove N>>n.


ci si vuole arrivare con una proiezione lineare. Nello spazio più piccolo si mantiene *la massima varianza dei dati*. 

$$\real^N \rightarrow \real^n \rightarrow \real^N$$

L'errore che ottengo riproiettando è minimo


# Kernel gaussiano

La trasformazione è del tipo:


$$\exp(-\gamma||x-x^{'}||^2)$$
$$\varphi : \real^n \rightarrow \real^\infty [l^2]$$
 E la componente n-esima

 $$\varphi(x)_n = a_n x^n$$

 Ha tutta la potenza dei polinomi


# C parameter

L'svm è stabile rispetto al valore di C (cambia poco), si considerano valori, quindi, con passo esponenziale

Usare for è scomodo per il tuning degli iperparametri, si usa Grid search.

# Applicazione SVM

Sono usate per l'anomaly detection.

Solitamente l'AD si ha in caso non supervisionato. Dato X vogliamo trovare i dati anomali $\forall x\in X$ diamo uno score di anomalia.

Il setting che consideriamo adesso è **"one class classification"** . O semi supervised anomaly detection.

Tutte le x in X sono dati normali, il test set contiene invece delle anomalie. 

Una formulazione delle svm permette di risolvere questo problema (cambiano i vincoli):

$$min ||w||^2 + \frac{1}{\nu m} \sum_i \xi_i$$


$$s.t. <w,\varphi(x_i)>+b\ge 1-\xi_i$$


A differenza di prima, il prodotto scalare non ha $y_i$. Vogliamo un iperpiano che ha i dati tutti da una parte. 

E' interessante quando usiamo kernel polinomiali, quando a esempio i dati normali sono racchiusi in una sfera. Si spera che i dati anomali stiano fuori.


# Stringhe con SVM

$$\varphi : X \rightarrow \real^S$$

$$K : X \times X \rightarrow \real$$

$$K(x,x^i) = <\varphi(x),\varphi(x^i)>_{R^S}$$


Bastano queste due funzioni, consideriamo un problema specifico con stringhe. 

$$\sigma= \{\sigma_1, ...,\sigma_L\}$$
$$|\sigma| = L$$

 $\sum^k$ = $\{ \sigma ||\sigma |=k\}$

X = $\{ \sigma| |\sigma |\le d\}$
 = $\cup_{i = 0}^d \sum_i$

Def:
$x\in X$ indicizziamo $\varphi(x)$ con $v\in X$

**Che tipo di trasformazione $\varphi$ andremo a usare?**

Ogni elemento v del linguaggio corrisponde a una locazione del vettore $i_v$ e sarà tale che:

$\varphi(x)[i_v]=$ 1 se v è sottostringa di x e 0 altrrimenti


Poiché abbiamo indicizzato con v in X lo spazio di arrivo sarà pari a tutta la cardinalità di x


S=$|X|=$ | $\cup_{k=0}^{d} \sum^k|$ =$\sum_{k=0}^d L^k = O(L^d)$

Non è praticabile il prendere le stringhe e lavorare col prodotto scalare nello spazio di destinazione

$K(x,x^i) = <\varphi(x),\varphi(x^i)>_{R^S} = \sum_{v \in X} \varphi(x)_v \varphi(x^i)_v=$

=pari ad uno se entrambi x e $x^i$ sono sono tali che v è sottostringa= |{sottostringhe comuni a x e $x^i$}|

Tale problema di risolve risolvendo un altro problema, i.e., la lunghezza del massimo suffisso comune,

ho due stringhe x xi e voglio trovare la lunghezza del massimo suffisso comune:

cane e pane: ne è comune, ma il massimo suffisso comune è "ane"

Si risolve con programmazione dinamica

$A\in \real^{(n+1)\times (m+1)}$  |x| = n e |x^i| = m

$A_{ij}=$ lunghezza del max suffisso comune a [x1 xi] e [x1' e xj']


- passo base: $A_{0i}=A_{i0}=0$


$A_{33}$ = 2 tronco cane dopo 3 lettere e pane dopo tre lettere, can e pan

A_23=0 poiché ca e pan

Quindi
A_ij é zero se hanno lettere finali diverse, se x_i diverso da x_j, altrimenti, prendo $A_{i-1,j-1}$ e aggiungo 1 perché anche l'ultima lettera è uguale. 

Popolo tutta la matrice, l'ultimo elemento in basso a destra $A_{nm}$ sarà pari alla lunghezza del massimo suffisso comune. 

Se prendo il massimo su i e j di $A_{ij}$ = lunghezza massima sottostringa poiché è lunghezza massimo suffisso su stringhe troncate. 

Il numero di sottostringhe comuni sarà quindi

$$\sum_{ij} A_{ij} = K(x,x^{'})$$

il motivo per cui sommiamo gli elementi di A sta nel fatto che: trovare una sottostringa nuova comune ne introduce altre k (dove k è il valore nella matrice, ovvero, la lunghezza del suffisso), cioé, se aggiungo ane in cane, ho anche: ane, ne ed e.


Usando un kernel custom non si scrive la funzione kernel ma la matrice di Grahm. E' una matrice quadrata nxn, tale che

$$G_{ij}=K(x_i,x_j)$$

Calcolo k a priori e poi lo uso nel programma, precalcolo. 


