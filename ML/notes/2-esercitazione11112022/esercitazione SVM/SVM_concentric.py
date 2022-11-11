import numpy as np
import matplotlib.pylab as plt
import SVM_functions
from sklearn.svm import SVC
from mpl_toolkits.mplot3d import Axes3D

# Creazione dataset classi concentriche
size = 100
radius = 10
var1 = 1
var2 = 0.5
X,y = SVM_functions.concentric_dataset(size,radius,var1,var2)

# Grafico dataset
plt.figure(1)
plt.scatter(X[:,0], X[:,1], c=y, cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')

# Separazione lineare
clf = SVC(kernel='linear', C=1)
clf_fit = clf.fit(X, y)
plt.figure(2)
SVM_functions.SVM_plot(X,y,clf,'linear separation')
input('Premi [Invio] per continuare\n')

# Kernel polinomiale grado 2
clf = SVC(kernel='poly', degree=2 , C=1, gamma=1, coef0=0)
clf_fit = clf.fit(X, y)
plt.figure(3)
SVM_functions.SVM_plot(X,y,clf,'quadratic separation')
input('Premi [Invio] per continuare\n')

# Trasformazione kernel esplicita
# caso gamma = 1, r=0 d=2 da R^2 in R^3
x3d = X[:,0]**2
y3d = np.sqrt(2)*X[:,0]*X[:,1]
z3d = X[:,1]**2

# Grafico 3d
fig = plt.figure(4)
ax = fig.add_subplot(111, projection='3d')
ax.scatter(x3d,y3d,z3d,c=y,cmap = plt.cm.brg)
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')
