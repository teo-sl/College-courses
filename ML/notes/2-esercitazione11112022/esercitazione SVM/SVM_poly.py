import numpy as np
from sklearn.svm import SVC
import matplotlib.pyplot as plt
import SVM_functions
from keras.datasets import mnist
from sklearn.decomposition import PCA

# Importazione e manipolazione dataset
(x_train, y_train), (x_valid, y_valid) = mnist.load_data()
digit0 = 0
digit1 = 1
I_train = np.where((y_train==digit0)+(y_train==digit1))
y_train = y_train[I_train]
x_train = x_train[I_train]
I_valid = np.where((y_valid==digit0)+(y_valid==digit1))
y_valid = y_valid[I_valid]
x_valid = x_valid[I_valid]

x_train = x_train.astype('float32')
x_valid = x_valid.astype('float32')
x_train /= 255      # normalizzazione
x_valid /= 255
n = x_train[0].size  
x_train = x_train.reshape(x_train.shape[0], n)  
x_valid = x_valid.reshape(x_valid.shape[0], n)
X = np.c_[x_train.T,x_valid.T]
X = X.T
y = np.concatenate((y_train,y_valid))

# Codice per visualizzare un'immagine

pixels = X[0].reshape((28, 28))
plt.figure()
plt.imshow(pixels,cmap='gray')
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')


# PCA per la riduzione della dimensionalità
pca = PCA(n_components=2) # dimensione spazio di arrivo
# quanto si perde è detta varianza spiegata, si può specificare in PCA

X_PCA = pca.fit_transform(X)
X_PCA = X_PCA-np.min(np.min(X_PCA))

# Divisione in training e validation set
training_size = 300
X_train = X_PCA[0:training_size]
X_valid = X_PCA[training_size:X_PCA.shape[0]]
Y_train = y[0:training_size]
Y_valid = y[training_size:X_PCA.shape[0]]

# Grafico training set
plt.figure()
plt.scatter(X_train[:,0], X_train[:,1], c=Y_train, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')


deg_max = 6
train_err = [0] * deg_max
valid_err = [0] * deg_max

# SVM con kernel polinomiale con grado crescente

for i in range(deg_max):
    clf = SVC(kernel = 'poly', degree = i+1, gamma = 'auto', C = 1)
    fit = clf.fit(X_train, Y_train)
    train_err[i] = 1 - clf.score(X_train, Y_train)
    valid_err[i] = 1 - clf.score(X_valid, Y_valid)
    plt.figure()
    SVM_functions.SVM_plot(X_train,Y_train,clf,'degree '+str(i+1))
    input('Premi [Invio] per continuare\n')

# Grafico degli errori al variare del grado del kernel

degs = np.cumsum([1]*deg_max)
plt.figure()
plt.plot(degs,train_err,degs,valid_err)
plt.legend(['training error','validation error'])
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

plt.figure()
SVM_functions.SVM_plot(X_valid,Y_valid,clf,'degree 6 VALIDATION')
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')
