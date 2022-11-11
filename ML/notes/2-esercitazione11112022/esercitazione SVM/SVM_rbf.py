import numpy as np
from sklearn.svm import SVC
import matplotlib.pyplot as plt
import SVM_functions

# Creazione dataset spirali
train_size = 200
length = 4 * np.pi
sep = 1
var = 0.5
X,Y = SVM_functions.spirals_dataset(train_size,length,sep,var)

# Grafico dataset
plt.figure(1)
plt.scatter(X[:,0], X[:,1], c=Y, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

deg_max = 3

# Kernel polinomiali

for i in range(deg_max):
    clf = SVC(kernel = 'poly', degree = i+1, gamma = 'auto', C = 1)
    fit = clf.fit(X, Y)
    plt.figure(i+2)
    SVM_functions.SVM_plot(X,Y,clf,'degree '+str(i+1))
    input('Premi [Invio] per continuare\n')

# Kernel Gaussiano

clf = SVC(kernel = 'rbf', gamma = 'auto', C = 1)
fit = clf.fit(X, Y)
plt.figure(deg_max+2)
SVM_functions.SVM_plot(X, Y, clf,'rbf')
input('Premi [Invio] per continuare\n')
