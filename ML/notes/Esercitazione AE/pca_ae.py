import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import matplotlib.pylab as plt
from tensorflow.keras.datasets import mnist
import numpy as np
from sklearn.decomposition import PCA
from sklearn.preprocessing import StandardScaler
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'


# SIZE OF LAYERS
n = 784
l2 = 2


# MNIST DATASET
(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train = x_train.astype('float32')
x_test = x_test.astype('float32')
x_train /= 255
x_test /= 255
X_train = x_train.reshape(x_train.shape[0], n)
X_test = x_test.reshape(x_test.shape[0], n)

sc = StandardScaler(with_std=False)
sc.fit(X_train)
X_train = sc.transform(X_train)


# MODEL'S STRUCTURE
model = Sequential()
model.add(Dense(units=l2, activation='linear',input_dim=n, use_bias=False))
model.add(Dense(units=n, activation='linear',use_bias=False))


# MODEL'S OPTIMIZER
model.compile(optimizer='adam', loss='mse')

# MODEL TRAINING
ep = 30
batches = 32
exists = os.path.isfile('pca.hist');
if exists:
    with open('pca.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        model.load_weights('pca')
else:
    history = model.fit(X_train, X_train, epochs=ep, batch_size=batches,
                             validation_data = (X_test, X_test));
    history_dict = history.history;
    with open('pca.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    model.save_weights('pca');

w = model.get_weights()

proj = np.dot(X_train,w[0])
plt.figure(1)
plt.scatter(proj[:,0],proj[:,1],c=y_train, cmap='jet_r')
plt.title('Linear Autoencoder')


pca = PCA(n_components=2)
X_PCA = pca.fit_transform(X_train)
plt.figure(2)
plt.scatter(X_PCA[:,0],X_PCA[:,1],c=y_train, cmap='jet_r')
plt.title('PCA')

plt.show()