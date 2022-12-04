import sys
sys.path.extend(['/home/luca/Scrivania/PyDir/CNN'])
import tensorflow.keras
from tensorflow.keras import layers
from tensorflow.keras import backend as K
from tensorflow.keras.models import Model
import numpy as np
import pickle
import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'

import tensorflow as tf
tf.config.experimental_run_functions_eagerly(True)

img_shape = (28, 28, 1)
batch_size = 16
latent_dim = 2


# Encoder network mapping images to the parameters of a probability distribution over the latent space.
# It’s a simple convnet that maps the input image x to two vectors, z_mean and z_log_var

input_img = tensorflow.keras.Input(shape=img_shape)
x = layers.Conv2D(32, 3, padding='same', activation='relu')(input_img)
x = layers.Conv2D(64, 3, padding='same', activation='relu',strides=(2, 2))(x)
x = layers.Conv2D(64, 3, padding='same', activation='relu')(x)
x = layers.Conv2D(64, 3, padding='same', activation='relu')(x)
shape_before_flattening = K.int_shape(x)
x = layers.Flatten()(x)
x = layers.Dense(32, activation='relu')(x)
z_mean = layers.Dense(latent_dim)(x)
z_log_var = layers.Dense(latent_dim)(x)

# Here, you wrap some arbitrary code (built on top of Keras backend primitives) into a
# Lambda layer. In Keras, everything needs to be a layer, so code that isn’t part of a built-in
# layer should be wrapped in a Lambda (or in a custom layer).

def sampling(args):
    z_mean, z_log_var = args
    epsilon = K.random_normal(shape=(K.shape(z_mean)[0], latent_dim), mean=0., stddev=1.)
    return z_mean + K.exp(z_log_var) * epsilon
enc_mean = Model(input_img,z_mean)
enc_log_var = Model(input_img,z_log_var)
z = layers.Lambda(sampling)([z_mean, z_log_var])
enc = Model(input_img,z)

# decoder implementation. You reshape the vector z to the dimensions of an image
# and then use a few convolution layers to obtain a final
# image output that has the same dimensions as the original input_img .

decoder_input = layers.Input(K.int_shape(z)[1:])
x = layers.Dense(np.prod(shape_before_flattening[1:]), activation='relu')(decoder_input)
x = layers.Reshape(shape_before_flattening[1:])(x)
x = layers.Conv2DTranspose(32, 3, padding='same', activation='relu', strides=(2, 2))(x)
x = layers.Conv2D(1, 3, padding='same', activation='sigmoid')(x)
decoder = Model(decoder_input, x)
z_decoded = decoder(z)

# The dual loss of a VAE doesn’t fit the traditional expectation of a sample-wise function
# of the form loss(input, target) . Thus, you’ll set up the loss by writing a custom
# layer that internally uses the built-in add_loss layer method to create an arbitrary loss.

class CustomVariationalLayer(tensorflow.keras.layers.Layer):
    def vae_loss(self, x, z_decoded):
        x = K.flatten(x)
        z_decoded = K.flatten(z_decoded)
        xent_loss = tensorflow.keras.metrics.binary_crossentropy(x, z_decoded)
        kl_loss = -5e-4 * K.mean(1 + z_log_var - K.square(z_mean) - K.exp(z_log_var), axis=-1)
        return K.mean(xent_loss + kl_loss)

    def call(self, inputs):
        x = inputs[0]
        z_decoded = inputs[1]
        loss = self.vae_loss(x, z_decoded)
        self.add_loss(loss, inputs=inputs)
        return x

y = CustomVariationalLayer()([input_img, z_decoded])

# Because the loss is taken care of in the custom layer, you don’t specify an external loss
# at compile time ( loss=None ), which in turn means you won’t pass target data during training
# (as you can see, you only pass x_train to the model in fit ).

from tensorflow.keras.datasets import mnist
vae = Model(input_img, y)
vae.compile(optimizer='rmsprop', loss=None, experimental_run_tf_function=False)
vae.summary()
(x_train, y_train), (x_test, y_test) = mnist.load_data()
x_train = x_train.astype('float32') / 255.
x_train = x_train.reshape(x_train.shape + (1,))
x_test = x_test.astype('float32') / 255.
x_test = x_test.reshape(x_test.shape + (1,))


exists = os.path.isfile('vae.hist');
if exists:
    with open('vae.hist','rb') as hist_file:
        history_dict = pickle.load(hist_file);
        vae.load_weights('vae')
else:
    history = vae.fit(x=x_train, y=None, shuffle=True, epochs=5, batch_size=batch_size, validation_data=(x_test, None))
    history_dict = history.history;
    with open('vae.hist','wb') as hist_file:
        pickle.dump(history_dict, hist_file);
    vae.save_weights('vae');

# Sampling a grid of points from the 2D latent space and decoding them to images

import matplotlib.pyplot as plt
from scipy.stats import norm

n = 15
digit_size = 28
figure = np.zeros((digit_size * n, digit_size * n))
grid_x = norm.ppf(np.linspace(0.05, 0.95, n))
grid_y = norm.ppf(np.linspace(0.05, 0.95, n))



for i, yi in enumerate(grid_x):
    for j, xi in enumerate(grid_y):
        z_sample = np.array([[xi, yi]])
        z_sample = np.tile(z_sample, batch_size).reshape(batch_size, 2)
        x_decoded = decoder.predict(z_sample, batch_size=batch_size)
        digit = x_decoded[0].reshape(digit_size, digit_size)
        figure[i * digit_size: (i + 1) * digit_size,
        j * digit_size: (j + 1) * digit_size] = digit

plt.figure(1,figsize=(10, 10))
plt.imshow(figure, cmap='Greys_r')
plt.show()

zeta = enc.predict(x_train)
plt.figure()
plt.scatter(zeta[:,0],zeta[:,1],c=y_train,cmap=plt.cm.jet_r)
plt.title('Latent Space')

