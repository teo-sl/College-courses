# in questo file è presente il codice per la denoising dell'immagine.
# L'autoencoder riceve in ingresso le immagini rumorose e usa come
# target le immagini originali. In tal modo, la rete impara a rimuovere
# il rumore dalle immagini. Per valutare le prestazioni del modello,
# si esegue il predict sul test set noisy.
import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
import tensorflow.keras
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Conv2D, MaxPooling2D, UpSampling2D
import numpy as np
import tensorflow.keras.backend as K
import matplotlib.pylab as plt
from tensorflow.keras.datasets import mnist
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'


# SIZE OF LAYERS
n = 784
l2 = 128
l3 = 64

img_rows=28
img_cols=28
# MNIST DATASET
(x_train, y_train), (x_test, y_test) = mnist.load_data()
if K.image_data_format() == 'channels_first':
    x_train = x_train.reshape(x_train.shape[0], 1, img_rows, img_cols)
    x_test = x_test.reshape(x_test.shape[0], 1, img_rows, img_cols)
    input_shape = (1, img_rows, img_cols)
else:
    x_train = x_train.reshape(x_train.shape[0], img_rows, img_cols, 1)
    x_test = x_test.reshape(x_test.shape[0], img_rows, img_cols, 1)
    input_shape = (img_rows, img_cols, 1)
x_train = x_train.astype('float32')
x_test = x_test.astype('float32')
x_train /= 255
x_test /= 255

# NOISE
noise_factor = 0.5
x_train_noisy = x_train + noise_factor * np.random.normal(loc=0.0, scale=1.0, size=x_train.shape)
x_test_noisy = x_test + noise_factor * np.random.normal(loc=0.0, scale=1.0, size=x_test.shape)
x_train_noisy = np.clip(x_train_noisy, 0., 1.)
x_test_noisy = np.clip(x_test_noisy, 0., 1.)


# CONVOLUTIONAL MODEL
model = Sequential()
model.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same',activation='relu',input_shape=input_shape))
model.add(MaxPooling2D(pool_size=(2, 2)))

model.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same', activation='relu'))
model.add(UpSampling2D(size=(2, 2)))

model.add(Conv2D(filters = 1, kernel_size = 3, padding = 'same', activation='sigmoid'))

model.compile(optimizer='adadelta',loss='binary_crossentropy',metrics=['binary_crossentropy'])

# MODEL TRAINING
ep = 3
batches = 128
stop = tensorflow.keras.callbacks.EarlyStopping(monitor='loss', min_delta=0.1, patience=0)

exists = os.path.isfile('denoise.hist');
if exists:
    with open('denoise.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        model.load_weights('denoise')
else:
    history = model.fit(x_train_noisy, x_train, epochs = ep, batch_size=batches, callbacks=[stop])
    history_dict = history.history;
    with open('denoise.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    model.save_weights('denoise');


# TEST
x_new = model.predict(x_test_noisy)

model2 = Sequential()
model2.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same',activation='relu',input_shape=input_shape))
model2.add(MaxPooling2D(pool_size=(2, 2)))

model2.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same', activation='relu'))
model2.add(UpSampling2D(size=(2, 2)))

model2.add(Conv2D(filters = 1, kernel_size = 3, padding = 'same', activation='sigmoid'))

model2.compile(optimizer='adadelta',loss='binary_crossentropy',metrics=['binary_crossentropy'])

exists = os.path.isfile('denoise2.hist');
if exists:
    with open('denoise2.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        model2.load_weights('denoise2')
else:
    history = model2.fit(x_train, x_train, epochs = ep, batch_size=batches, callbacks=[stop])
    history_dict = history.history;
    with open('denoise2.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    model2.save_weights('denoise2');

x_new2 = model2.predict(x_test_noisy)

plt.figure(1)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_test[i][:,:,0], cmap=plt.get_cmap('gray'))
plt.suptitle('Image')

plt.figure(2)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_test_noisy[i][:,:,0], cmap=plt.get_cmap('gray'))
plt.suptitle('Noisy Image')

plt.figure(3)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_new2[i][:,:,0], cmap=plt.get_cmap('gray'))
plt.suptitle('Standard AE')

plt.figure(4)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_new[i][:,:,0], cmap=plt.get_cmap('gray'))
plt.suptitle('Denoising AE')

plt.show()
