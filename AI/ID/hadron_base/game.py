from hadron import *

game = Game()

HUMAN = -MAXIMIZER
def read_move():
    while True:
        (k,l) = get_best_move(game,MAXIMIZER)
        game.apply_move(MAXIMIZER,(k,l))
        game.print_board()
        while True:
            prompt = input("Inserisci la tua mossa\n")
            (i,j) = prompt.split(",")
            i = int(i)
            j = int(j)
            if game.check_valid(i,j):
                break
        
        game.apply_move(HUMAN,(i,j))
        print()
        game.print_board()

read_move()
        
