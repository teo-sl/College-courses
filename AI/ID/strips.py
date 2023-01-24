

GOAL_STATE = None

class State():
    def __init__(self,fluents):
        self.fluents = fluents
    def get_fluents(self):
        return self.fluents
    def apply_move()
class Move():
    def __init__(self,type,blocks):
        self.type = type   # BLOCK or TABLE
        self.blocks = blocks
class Fluent():
    def __init__(self,type,blocks):
        self.type = type    # ON or CLEAR
        self.blocks = blocks #list of blocks
    def get_type(self):
        return self.type
    def get_blocks(self):
        return self.blocks
    def __eq__(self,other):
        return self.type == other.type and self.blocks == other.blocks
    def __hash__(self):
        return hash((self.type,self.blocks))
    def __str__(self):
        return str(self.type) + str(self.blocks)
    def __repr__(self):
        return str(self.type) + str(self.blocks)

    
class StripsNode():
    def __init__(self,moves,current_state,father_depth):
        self.moves = moves
        self.fluents = current_state
        self.depth = father_depth+1
    
    def heuristic(self):
        match = 0
        unmatched = 0
        total = len(GOAL_STATE)
        for fluent in self.fluents:
            if fluent in GOAL_STATE:
                match += 1
            else:
                unmatched += 1
        return total - match + unmatched
    def get_sons(self):
        moving_blocks = []
        for fluent in self.fluents:
            if fluent.type == 'CLEAR':
                moving_blocks.append(fluent.blocks[0])