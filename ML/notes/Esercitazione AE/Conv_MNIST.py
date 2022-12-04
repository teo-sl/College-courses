import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, Conv2D, MaxPooling2D, UpSampling2D
import tensorflow.keras.backend as K
import matplotlib.pylab as plt
from tensorflow.keras.datasets import mnist
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'
import numpy as np
import tensorflow.keras


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

input = tensorflow.keras.Input(shape=(28,28,1))
x = tensorflow.keras.layers.Conv2D(filters = 32, kernel_size = 3, padding = 'same',activation='relu')(input)
x = tensorflow.keras.layers.MaxPooling2D(pool_size=(2, 2))(x)
x = tensorflow.keras.layers.Conv2D(filters = 32, kernel_size = 3, padding = 'same',activation='relu')(x)
encoder = tensorflow.keras.Model(input, x)
x = tensorflow.keras.layers.UpSampling2D(size=(2, 2))(x)
x = tensorflow.keras.layers.Conv2D(filters = 1, kernel_size = 3, padding = 'same', activation='sigmoid')(x)
autoencoder = tensorflow.keras.Model(input, x)
"""
model = Sequential()
model.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same',activation='relu',input_shape=input_shape))
model.add(MaxPooling2D(pool_size=(2, 2)))
model.add(Conv2D(filters = 32, kernel_size = 3, padding = 'same', activation='relu'))
model.add(UpSampling2D(size=(2, 2)))
model.add(Conv2D(filters = 1, kernel_size = 3, padding = 'same', activation='sigmoid'))
"""
autoencoder.compile(optimizer='adam',loss='mse')

# MODEL TRAINING
ep = 10
batches = 32
exists = os.path.isfile('conv.hist');
if exists:
    with open('conv.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        autoencoder.load_weights('conv')
else:
    history = autoencoder.fit(x_train, x_train, epochs=ep, batch_size=batches,
                             validation_data = (x_test, x_test));
    history_dict = history.history;
    with open('conv.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    autoencoder.save_weights('conv');


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

hist_plot(history_dict, 'Convolutional AE')

# TEST
x_new = autoencoder.predict(x_train)


plt.figure(2)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_train[i][:,:,0], cmap='gray')
plt.suptitle('Immagini originali')

plt.figure(3)
for i in range(9):
    plt.subplot(330 + 1 + i)
    plt.imshow(x_new[i][:,:,0], cmap='gray')
plt.suptitle('Immagini ricostruite')


proj = encoder.predict(x_train)
