import numpy as np
import matplotlib.pyplot as plt
import random
import string

def SVM_plot(X,y,clf,title):
    plt.scatter(X[:, 0], X[:, 1], c=y, cmap=plt.cm.brg)
    plt.title(title)
    ax = plt.gca()
    xlim = ax.get_xlim()
    ylim = ax.get_ylim()
    xx = np.linspace(xlim[0], xlim[1], 200)
    yy = np.linspace(ylim[0], ylim[1], 200)
    YY, XX = np.meshgrid(yy, xx)
    xy = np.vstack([XX.ravel(), YY.ravel()]).T
    Z = clf.decision_function(xy).reshape(XX.shape)
    ax.contour(XX, YY, Z, colors='k', levels=[-1, 0, 1], linestyles=['--', '-', '--'])
    ax.scatter(clf.support_vectors_[:, 0], clf.support_vectors_[:, 1], s=100, linewidth=1, facecolors='none',edgecolors='k')
    plt.ion()
    plt.pause(1.00001)
    plt.show()

def twoclasses_dataset(a,train_size):
    # train_size is the size of each class
    x0 = np.random.multivariate_normal(np.zeros(2), np.eye(2), train_size)
    x1 = np.random.multivariate_normal(np.zeros(2), np.eye(2), train_size)
    X = np.r_[x0 - [a, a], x1 + [a, a]]
    y = [0] * train_size + [1] * train_size
    y = np.array(y)
    return X,y


def concentric_dataset(size,radius,var1,var2):
    # size is the size of each class
    x1 = var1*np.random.multivariate_normal([0, 0], np.eye(2), size)
    x2theta = 2 * np.pi * np.random.random(size)
    x2 = np.array((np.cos(x2theta).T, np.sin(x2theta).T))
    x2 = radius * x2.T
    x2 = x2 + var2 * np.random.multivariate_normal([0, 0], np.eye(2), size)
    X = np.c_[x1.T, x2.T]
    X = X.T
    y = [0] * size + [1] * size
    return X,y

def spirals_dataset(train_size,length,sep,var):
    # train_size is the size of each class
    tr = length * np.random.random(train_size) + sep
    x1 = tr * np.cos(tr)
    x2 = tr * np.sin(tr)
    spiral_tr = np.c_[x1.T, x2.T] + var * np.random.multivariate_normal(np.zeros(2), np.eye(2), train_size)

    sr = length * np.random.random(train_size) + sep
    x1 = sr * np.cos(sr)
    x2 = sr * np.sin(sr)
    spiral_sr = np.c_[x1.T, x2.T] + var * np.random.multivariate_normal(np.zeros(2), np.eye(2), train_size)
    spiral_sr = -spiral_sr

    X = np.c_[spiral_sr.T, spiral_tr.T].T
    X = X + [10, 10]
    Y = [0] * train_size + [1] * train_size
    return X,Y


def RandomString():
    stringLength = 1 + np.random.poisson(10)
    letters = string.ascii_lowercase
    ret = ''.join(random.choice(letters) for i in range(stringLength))
    return ret


def StringsArray(n):
    ret = [0]*n
    for i in range(n):
        ret[i] = RandomString()
    return ret
