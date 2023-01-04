
def create_board():
    # return an 8*8 matrix with 1 in (0,0) and (3,3) and 0 in (7,7) and (4,4)
    board = [[-1 for i in range(8)] for j in range(8)]
    board[0][0] = 1
    board[3][3] = 1
    board[7][7] = 0
    board[4][4] = 0
    return board

# pretty print the board
def print_board(board):
    for i in range(8):
        for j in range(8):
            if board[i][j] == -1:
                print(" ", end="")
            elif board[i][j] == 1:
                print("X", end="")
            else:
                print("O", end="")
        print()
    
# return the list of all the possible moves for a given player
def possible_moves(board, player):
    moves = []
    for i in range(8):
        for j in range(8):
            if board[i][j] == -1:
                if check_normal(board, player, i, j) or check_special(board, player, i, j)):
                    moves.append((i,j))
                
    return moves

# check if a move is valid
def check_normal(board, player, i, j):
    
    if i-1>=0 and j-1>=0 and board[i-1][j-1]==-1 and direction(board,i-1,j-1,"diag",-1) == player:
        return True
    if i-1>=0 and board[i-1][j]==-1 and direction(board,i-1,j,"vert",-1) == player:
        return True
    if i-1>=0 and j+1<8 and board[i-1][j+1]==-1 and direction(board,i-1,j+1,"diag",1) == player:
        return True
    if j-1>=0 and board[i][j-1]==-1 and direction(board,i,j-1,"oriz",-1) == player:
        return True
    if j+1<8 and board[i][j+1]==-1 and direction(board,i,j+1,"oriz",1) == player:
        return True
    if i+1<8 and j-1>=0 and board[i+1][j-1]==-1 and direction(board,i+1,j-1,"diag",1) == player:
        return True
    if i+1<8 and board[i+1][j]==-1 and direction(board,i+1,j,"vert",1) == player:
        return True
    if i+1<8 and j+1<8 and board[i+1][j+1]==-1  and direction(board,i+1,j+1,"diag",-1) == player:
        return True
    return False


def direction(board,i,j,dir,sign):
    if dir == "diag" and sign == 1:
        update = (1,1)
    elif dir == "diag" and sign == -1:
        update = (1,-1)
    elif dir == "vert" and sign == 1:
        update = (1,0)
    elif dir == "vert" and sign == -1:
        update = (-1,0)
    elif dir == "oriz" and sign == 1:
        update = (0,1)
    elif dir == "oriz" and sign == -1:
        update = (0,-1)
    
    while i>=0 and i<8 and j>=0 and j<8:
        if board[i][j] == -1:
            (i,j) = (i+update[0],j+update[1])
        else:
            return board[i][j]


def check_special(board, player, i, j):
    # iterate over the positions adjacent and diagonal to (i,j)
    if i-1>=0 and j-1>=0 and board[i-1][j-1]==player and check_couple_diagonal(board, player, i, j,-1):
        return True
        
    if i-1<8 and j+1<8 and board[i-1][j+1]==player and check_couple_diagonal(board, player, i, j,+1):
        return True

    if i+1<8 and j-1>=0 and board[i+1][j-1]==player and check_couple_diagonal(board, player, i, j,+1):
        return True

    if i+1<8 and j+1<8 and board[i+1][j+1]==player and check_couple_diagonal(board, player, i, j,-1):
        return True

    return False

    