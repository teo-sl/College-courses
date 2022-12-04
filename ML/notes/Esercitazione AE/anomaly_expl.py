import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
import tensorflow.keras
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import numpy as np
import matplotlib.pylab as plt
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
from sklearn.metrics import roc_curve,roc_auc_score


(x_train, y_train), (x_test, y_test) = tensorflow.keras.datasets.mnist.load_data()
x_train = x_train.astype('float32') / 255.
x_test = x_test.astype('float32') / 255.

## TRAIN WITH CLASS 0
x_training = x_train[y_train==0]

## TEST WITH CLASS 0 AND CLASS 0+SQUARE
anomalies = 100
x_t = x_test[y_test==0]

y_t = np.zeros(x_t.shape[0])

h_mods = np.random.randint(2,x_t.shape[1]-2,size=anomalies)
v_mods = np.random.randint(2,x_t.shape[1]-2,size=anomalies)

for i in range(anomalies):
    y_t[i] = 1
    h = h_mods[i]
    v = v_mods[i]
    x_t[i,h-2:h+3,v-2:v+3] = 0.5


# SIZE OF LAYERS
n = 784
l2 = 128
l3 = 64

# MODEL'S STRUCTURE
input = tensorflow.keras.Input(shape=(n,))
x = tensorflow.keras.layers.Dense(l2, activation ='relu')(input)
x = tensorflow.keras.layers.Dense(l3,activation = 'relu')(x)
encoder = tensorflow.keras.Model(input, x)
x = tensorflow.keras.layers.Dense(l2, activation='relu')(x)
x = tensorflow.keras.layers.Dense(n, activation='sigmoid')(x)
autoencoder = tensorflow.keras.Model(input, x)

autoencoder.compile(optimizer='adam', loss='mse')

ep = 10
batches = 32
x_training_flat = x_training.reshape(x_training.shape[0],784)
autoencoder.fit(x_training_flat, x_training_flat, epochs=ep, batch_size=batches)


x_t_flat = x_t.reshape(x_t.shape[0],784)

x_t_flat_rec = autoencoder.predict(x_t_flat)
x_t_rec = x_t_flat_rec.reshape(x_t_flat_rec.shape[0],28,28)



plt.figure()
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_t[i], cmap='gray')
plt.suptitle('Immagini originali')



plt.figure()
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_t_rec[i], cmap='gray')
plt.suptitle('Ricostruzioni')


plt.figure()
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(np.abs(x_t[i]-x_t_rec[i]),cmap='gray')
plt.suptitle('Heatmaps')

