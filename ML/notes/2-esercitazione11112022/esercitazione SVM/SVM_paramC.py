import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/SVM'])
import numpy as np
import matplotlib.pylab as plt
from sklearn.svm import SVC
import SVM_functions

# Esperimento 1

# Creazione dataset
a = 1
train_size = 100
X,Y = SVM_functions.twoclasses_dataset(a,train_size)

valid_size = 200
X_valid, Y_valid = SVM_functions.twoclasses_dataset(a,valid_size)


# Grafico dataset
plt.figure(1)
plt.scatter(X[:,0], X[:,1], c=Y, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# cs è il vettore che contiene i valori del parametro C considerati
cs = np.arange(0.001,1,0.01)
csize = cs.size
margins = np.zeros(csize)
score = np.zeros(csize)
best_score = 0
best_c = cs[0]
supp_numbers = np.zeros(csize)

# SVM con i vari valori di C
for i in range(csize):
    clf = SVC(kernel='linear', C=cs[i])
    fit = clf.fit(X, Y)
    margin = 1 / np.sqrt(np.sum(clf.coef_ ** 2))  # margin = 1/norm(w)
    margins[i] = margin
    score[i] = clf.score(X_valid,Y_valid)
    supp_numbers[i] = clf.support_vectors_.shape[0]
    if score[i] > best_score:
        best_score = score[i]
        best_clf = clf
        best_c = cs[i]

# Separazione ottenuta con il C che ottiene il minimo errore di validazione
plt.figure(2)
SVM_functions.SVM_plot(X,Y,best_clf,'Best value: C='+str(best_c))
input('Premi [Invio] per continuare\n')

# Grafico dell'ampiezza del margine al variare del parametro C
plt.figure(3)
plt.plot(cs,margins)
plt.xlabel('C')
plt.ylabel('margin length')
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# Grafico dell'errore di classificazione sul validation set del margine al variare del parametro C
plt.figure(4)
plt.plot(cs,1-score)
plt.xlabel('C')
plt.ylabel('validation error')
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# Grafico del numero di vettori di supporto del margine al variare del parametro C
plt.figure(5)
plt.plot(cs,supp_numbers)
plt.xlabel('C')
plt.ylabel('support vectors')
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# Esperimento 2

# Creazione dataset
train_size = 200
test_size = 500
length = 2 * np.pi
sep = 0.5
var = 0.5
X_train, Y_train = SVM_functions.spirals_dataset(train_size,length,sep,var)
X_valid, Y_valid = SVM_functions.spirals_dataset(train_size,length,sep,var)

plt.figure(6)
plt.scatter(X_train[:,0], X_train[:,1], c=Y_train, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')


# Iperparametri C e gamma con valori 2^-5,2^-3,...,2^13,2^15
esp = 2*np.arange(11)-5
Cs = 2.0**esp
gammas = 2.0**np.flip(-esp)

score = np.zeros((11,11))
best_score = 0
supp_numbers = np.zeros((11,11))
best_c_pos = 0
best_gamma_pos = 0

for i in range(11):
    for j in range(11):
        clf = SVC(kernel='rbf', gamma=gammas[i], C=Cs[j])
        fit = clf.fit(X_train, Y_train)
        score[i,j] = clf.score(X_valid, Y_valid)
        supp_numbers[i,j] = clf.support_vectors_.shape[0]
        if score[i,j] > best_score:
            best_score = score[i,j]
            best_clf = clf
            best_c_pos = j
            best_gamma_pos = i

# Grafico della separazione ottenuta con la combinazione di C e gamma che produce il minore errore di validazione

plt.figure(7)
SVM_functions.SVM_plot(X_train,Y_train,best_clf,'Best values: C=2^'+str(esp[best_c_pos])+' gamma=2^'+str(-np.flip(esp)[best_gamma_pos]))
input('Premi [Invio] per continuare\n')

