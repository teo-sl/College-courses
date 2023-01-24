
def read_maze():
    with open('maze.txt') as f:
        lines = f.readlines()
        lines = [line.strip() for line in lines]
        lines = [line.split(' ') for line in lines]
        lines = [[int(x) for x in line] for line in lines]
        return lines
class Maze():
    def __init__(self,position,matrix,father):
        self.father = father
        if father != None:
            self.depth = father.get_depth()+1
        else:
            self.depth = 0
        self.position = position
        self.old_position = []
        self.matrix = matrix

    def __str__(self):
        return str(self.matrix)

    def get_x(self):
        for i in range(len(self.matrix)):
            for j in range(len(self.matrix[i])):
                if self.matrix[i][j] == 2:
                    return (i,j)
    def get_depth(self):
        return self.depth
    def get_position(self):
        return self.position

    def get_distance_to_goal(self):
        (x,y) = self.get_x()
        #return abs(x - self.position[0]) + abs(y - self.position[1])
        # use euclidean distance
        return ((x - self.position[0])**2 + (y - self.position[1])**2)**0.5

    def heuristic(self):
        return self.get_distance_to_goal()
    
    def is_terminal(self):
        (x,y) = self.position
        if x-1>=0 and self.matrix[x-1][y] == 2:
            return True
        if x+1<len(self.matrix) and self.matrix[x+1][y] == 2:
            return True
        if y-1>=0 and self.matrix[x][y-1] == 2:
            return True
        if y+1<len(self.matrix[0]) and self.matrix[x][y+1] == 2:
            return True
        return False


    def apply_move(self,move):
        self.old_position.append(self.position)
        self.position=(self.position[0]+move[0],self.position[1]+move[1])
    def revert_move(self):
        self.position = self.old_position.pop()
    def get_moves(self):
        moves = []
        (x,y) = self.position
        if x-1>= 0 and self.matrix[x-1][y] == 0:
            moves.append((-1,0))
        if x+1<len(self.matrix) and self.matrix[x+1][y] == 0:
            moves.append((1,0))
        if y-1>= 0 and self.matrix[x][y-1] == 0:
            moves.append((0,-1))
        if y+1<len(self.matrix[0]) and self.matrix[x][y+1] == 0:
            moves.append((0,1))
        return moves
    
visited = 0
def A_star(root : Maze):
    path = []
    return path,A_star_rec(root,[],0,path)

def A_star_rec(n,vis,depth,path):
    global visited
    if n is None:
        return None
    if n.is_terminal():
        print("visited nodes: ",visited)
        print("depth: ",depth)
        path.append(n.get_position())
        return n
    visited+=1
    vis.append(n.get_position())
    moves = n.get_moves()
    sons = []
    for move in moves:
        n.apply_move(move)
        if n.get_position() not in vis:
            sons.append((move,n.heuristic()+depth))
        n.revert_move()
    sons = sorted(sons,key=lambda x:x[1])
    for son in sons:
        n.apply_move(son[0])
        old_pos = n.get_position()
        res = A_star_rec(n,vis,depth+1,path)
        if res != None:
            path.append(old_pos)
            return res
        n.revert_move()

    return None
        
        

# define a function to write the maze on file
def write_maze(maze):
    with open('maze.txt','w') as f:
        for line in maze:
            for x in line:
                f.write(str(x)+" ")
            f.write("\n")

