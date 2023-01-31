## Complessità dei modelli nei programmi logici e delle estensioni nelle Abstract Argumentation $\Lambda=<A,\Sigma>$

---

- programma positivo senza ricorsione: modello minimo esiste ed è intersezione di tutti i minimali
- programma con negazione: il modello minimo non è assicurato, si considera il modello stratificato che esiste ed è unico
- programma con negazione non stratificato: si passa ai modelli stabili. Possono esistere 0, 1 o n modelli stabili. La loro definizione fa uso del concetto di $P^{Reduct}$, che trasforma il programma in uno positivo, dove il modello minimo è assicurato

La complessità per i modelli stabili è:
- verification: P (equivale a punto fisso)
- brave: NP
- skeptical: CO-NP


---

Per le estensioni nell'abstract argumentation.

|           | Verification | Brave reasoning | Skeptical reasoning | in logica        |
|:---------:|:------------:|:---------------:|:-------------------:|:----------------:|
| completi  | P            |  NP             | CO-NP               |  stable          |
| grounded  | P            | P               | P                   | well founded     |
| preferred | CO-NP        | NP              |  $\Pi_p^2$          | maximal stable   |
| semiStable| CO-NP        | $\Sigma_p^2$    |  $\Pi_p^2$          | least undefined  |
| stabile   | P            | NP              |  CO-NP              |  total stable    |
---

- completi: basta verificare la correttezza dell'etichettatura, quindi $poly(n)$
- grounded: applicare algoritmo di propagazione vincoli, $poly(n)$
- preferred: applicare definizione $\nexists S^{'} : S^{'} \subset S$, con $S$ l'estensione stabile. Quindi, di base è CO-NP. Dopo, per il brave si può usare la proprietà del semi-lattice
- semi-stabile: anche qui si deve applicare la definizione che non esista un preferred con un numero minore di undefined, CO-NP di base.
- stabile, basta verificare che non ci sono undefined

Altre proprietà per le abstract argumentation sono 

- $\sigma(S)\neq \empty \rightarrow \sigma(S) = \sigma(SS)$
- $|\sigma(S)|\ge 0$, per gli altri è $\ge 0$, per grounded e ideal corrisponde a un solo punto.
- se il grafo dlle dipendenze contiene un numero pari di cicli, preferred, semi stable e stable coincidono

