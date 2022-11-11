import numpy as np
import matplotlib.pylab as plt
from sklearn.svm import SVC
import SVM_functions


# Creazione dataset
a = 1.5 # più lo diminuisco più vi è intersezione
train_size = 100
X,y = SVM_functions.twoclasses_dataset(a,train_size)

# Grafico dataset
plt.figure(1)
plt.scatter(X[:,0], X[:,1], c=y, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# Creazione modello
clf = SVC(kernel = 'linear',C=10)
# Training modello
clf_fit = clf.fit(X, y)

# Grafico separazione
plt.figure(2)
SVM_functions.SVM_plot(X, y, clf, 'linear separation')
input('Premi [Invio] per continuare\n')


#print(clf.coef_[0]) # coordinate w
#print(clf.intercept_) # intercetta

#print(clf.decision_function )
# si invoca su dei dati da una serie di valori, il cui segno
# indica se si trovano da una parte o l'altra dell'iperpiano
# sono legati alla distanza ma non esattamente


# clf.predict(X) predizione per la classe per ogni punto

# clf.score(X,label) percentuale di punti di X classificati
# correttamente


# y = clf.support_vectors_ lista di questi vettori

# y = clf.support_ lista indici dei vettori

# clf.n_support_  numero vettori di supporto per ogni classe


# clf.dual_coef_[0] sono i coefficienti del problema duale dell'svm

# np.dot(alpha,support_vectors)




