f1 = open("./ITCompany_10000.csv")
f2 = open("./redo.csv","w")
for l in f1:
    parts = l.split("[")
    nl = parts[1].replace(","," ").replace("]","")
    new_line = parts[0]+nl
    new_line=new_line.strip()
    new_new_line=""
    for x in new_line.split(" "):
        new_new_line+=x.strip()+" "
    new_new_line=new_new_line.strip()+'\n'
    f2.write(new_line)
    f2.write('\n')