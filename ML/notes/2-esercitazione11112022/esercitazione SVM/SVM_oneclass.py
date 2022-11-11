import numpy as np
from sklearn.svm import OneClassSVM
import matplotlib.pyplot as plt
import SVM_functions
from tensorflow.keras.datasets import mnist
from sklearn.decomposition import PCA

# DATASET ONE-CLASS
normals_train = 100
normals_test = 100
anomalies = 10
a = 3
x_train,x_test,y_test = SVM_functions.one_class_dataset(a,normals_train,normals_test,anomalies)

clf = OneClassSVM(kernel='poly',degree=2,nu=0.99,gamma=0.1)
clf.fit(x_train)

plt.figure()
plt.scatter(x_train[:, 0], x_train[:, 1],c=np.array(normals_train*[0]), cmap=plt.cm.brg)
plt.title('TRAINING SET')
ax = plt.gca()
xlim = ax.get_xlim()
ylim = ax.get_ylim()
xx = np.linspace(xlim[0], xlim[1], 200)
yy = np.linspace(ylim[0], ylim[1], 200)
YY, XX = np.meshgrid(yy, xx)
xy = np.vstack([XX.ravel(), YY.ravel()]).T
Z = clf.decision_function(xy).reshape(XX.shape)
ax.contour(XX, YY, Z, colors='k', levels=[-1, 0, 1], linestyles=['--', '-', '--'])
plt.ion()
plt.pause(1.00001)
plt.show()
input('Premi [Invio] per continuare\n')




plt.figure()
plt.scatter(x_test[:, 0], x_test[:, 1],c=y_test, cmap=plt.cm.brg)
plt.title('TRAINING SET')
ax = plt.gca()
xlim = ax.get_xlim()
ylim = ax.get_ylim()
xx = np.linspace(xlim[0], xlim[1], 200)
yy = np.linspace(ylim[0], ylim[1], 200)
YY, XX = np.meshgrid(yy, xx)
xy = np.vstack([XX.ravel(), YY.ravel()]).T
Z = clf.decision_function(xy).reshape(XX.shape)
ax.contour(XX, YY, Z, colors='k', levels=[-1, 0, 1], linestyles=['--', '-', '--'])