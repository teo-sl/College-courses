import plotly.express as px
import pandas as pd

def reformat_file():
    phrases = []
    with open('divina.txt','r') as f:
        lines = f.readlines()
        l = ""
        for line in lines:
            line = line.strip()
            # get last char
            if line == "":
                continue
            splitted = line.split()
            if splitted[0] == "Inferno:" or splitted[0] == "Purgatorio:" or splitted[0] == "Paradiso:":
                continue
            if line[-1] == '.':
                l += line + "\n"
                phrases.append(l)
                l=""
            else:
                l += line + " "
    with open('divina_out.txt','w') as f:
        for phrase in phrases:
            f.write(phrase)


def count_phrase_length_frequency():
    dict = {}
    with open('divina_out.txt','r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            splitted = line.split()
            if len(splitted) in dict:
                dict[len(splitted)] += 1
            else:
                dict[len(splitted)] = 1
    return dict

# plot the frequency of phrase length with plotly
def plot_frequency(dict):
    df = pd.DataFrame.from_dict(dict, orient='index')
    df = df.reset_index()
    df = df.rename(columns={'index':'phrase_length', 0:'frequency'})
    fig = px.bar(df, x='phrase_length', y='frequency')
    fig.show()
plot_frequency(count_phrase_length_frequency())


# count the unique words in the file
def count_unique_words():
    words = []
    with open('divina_out.txt','r') as f:
        lines = f.readlines()
        for line in lines:
            line = line.strip()
            splitted = line.split()
            for word in splitted:
                words.append(word)
    return len(set(words))

print("Unique words: ",count_unique_words())