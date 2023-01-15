D=5
WHITE = 1
BLACK = -1

MAXIMIZER = WHITE
DEPTH = 50

class Game():
    def __init__(self,board=None):
        if board is None:
            self.board = [[ 0 for i in range(D)] for j in range(D)]
        else:
            self.board=board

    def check_valid(self,i,j):
        if self.board[i][j]!=0:
            return False
        n = 0
        if i>0:
            n+=self.board[i-1][j]
        if i<D-1:
            n+=self.board[i+1][j]
        if j>0:
            n+=self.board[i][j-1]
        if j<D-1:
            n+=self.board[i][j+1]
        return n==0

    def get_moves(self):
        moves = []
        for i in range(D):
            for j in range(D):
                if self.check_valid(i,j):
                    moves.append((i,j))
        return moves

    def apply_move(self,player,move):
        i,j = move
        self.board[i][j] = player
    def revert_move(self,player,move):
        i,j = move
        self.board[i][j] = 0
    def is_terminal(self):
        return len(self.get_moves())==0

    def evaluate(self,player):
        if self.is_terminal():
            if player == MAXIMIZER:
                return -100_000
            else:
                return 100_000
        else:
            return 0
    # define a function to print the board, do a beatuiful print
    def print_board(self):
        for i in range(D):
            for j in range(D):
                if self.board[i][j] == 0:
                    print(" . ",end="")
                elif self.board[i][j] == 1:
                    print(" O ",end="")
                else:
                    print(" X ",end="")
            print()




def get_best_move(board,player):
    moves = board.get_moves()
    value = -float('inf')
    best_move = None
    for move in moves:
        board.apply_move(player,move)
        v = minimax(board,DEPTH,-player)
        if v>value:
            value = v
            best_move = move
        board.revert_move(player,move)
    print("Best move: ",best_move," with value: ",value)
    return best_move


def minimax(board,depth,player):
    if depth == 0 or board.is_terminal():
        return board.evaluate(player)
    moves = board.get_moves()
    if player == MAXIMIZER:
        value = -float('inf')
        for move in moves:
            board.apply_move(player,move)
            value = max(value,minimax(board,depth-1,-player))
            board.revert_move(player,move)
    else:
        value = float('inf')
        for move in moves:
            board.apply_move(player,move)
            value = min(value,minimax(board,depth-1,-player))
            board.revert_move(player,move)   
    return value


    