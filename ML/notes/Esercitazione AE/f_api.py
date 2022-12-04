from tensorflow.keras.models import Sequential, Model
from tensorflow.keras import layers
from tensorflow.keras import Input

input_tensor = Input(shape=(32,))     # tensore
dense = layers.Dense(32, activation='relu')   # un layer è una funzione
output_tensor = dense(input_tensor)          # un layer è chiamato su un tensore e restiutuisce un tensore


# Modello sequenziale
seq_model = Sequential()
seq_model.add(layers.Dense(32, activation='relu', input_shape=(64,)))
seq_model.add(layers.Dense(32, activation='relu'))
seq_model.add(layers.Dense(10, activation='softmax'))
seq_model.summary()

# Modello funzionale
input_tensor = Input(shape=(64,))
x = layers.Dense(32, activation='relu')(input_tensor)
x = layers.Dense(32, activation='relu')(x)
output_tensor = layers.Dense(10, activation='softmax')(x)
model = Model(input_tensor, output_tensor)
model.summary()