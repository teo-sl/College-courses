import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense
import matplotlib.pylab as plt
from tensorflow.keras.datasets import mnist
import tensorflow.keras
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
import numpy as np

# SIZE OF LAYERS
n = 784
l2 = 64


# MNIST DATASET
(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train = x_train.astype('float32')
x_test = x_test.astype('float32')
x_train /= 255
x_test /= 255
X_train = x_train.reshape(x_train.shape[0], n)
X_test = x_test.reshape(x_test.shape[0], n)


# MODEL'S STRUCTURE
input = tensorflow.keras.Input(shape=(n,))
x = tensorflow.keras.layers.Dense(l2, activation='relu')(input)
encoder = tensorflow.keras.Model(input, x)

x = tensorflow.keras.layers.Dense(n, activation='sigmoid')(x)
autoencoder = tensorflow.keras.Model(input, x)




# MODEL'S OPTIMIZER
autoencoder.compile(optimizer='sgd', loss='mse')

# MODEL TRAINING
ep = 10
batches = 32
exists = os.path.isfile('shallow.hist');
if exists:
    with open('shallow.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        autoencoder.load_weights('shallow')
else:
    history = autoencoder.fit(X_train, X_train, epochs=ep, batch_size=batches,
                             validation_data = (X_test, X_test));
    history_dict = history.history;
    with open('shallow.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    autoencoder.save_weights('shallow');


f=1

def hist_plot(history_dict, title):
    global f;
    loss = history_dict['loss'];
    val_loss = history_dict['val_loss'];
    epochs = range(1, len(loss) + 1); # 2 3
    plt.figure(f);
    f = f + 1;
    plt.plot(epochs, loss, 'b', label='Training Loss')
    plt.plot(epochs, val_loss, 'bo', label='Validation Loss')
    plt.title(title)
    plt.xlabel('Epochs')
    plt.ylabel('Loss')
    plt.legend(loc='lower left')
    plt.ion();
    plt.show();
    plt.pause(3.0001);

hist_plot(history_dict, 'Shallow AE')

# TEST
X_new = autoencoder.predict(X_train)
x_new = X_new.reshape(60000,28,28)
x_new = x_new*255

plt.figure(2)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_new[i], cmap='gray')
plt.suptitle('Immagini ricostruite')

plt.figure(3)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_train[i], cmap='gray')
plt.suptitle('Immagini originali')

proj = encoder.predict(X_train)
plt.figure(4)
plt.scatter(proj[:,0],proj[:,1],c=y_train, cmap='jet_r')
plt.title('Latent Space')

plt.show()