(defun binary-list (n)
  (cond ((= n 3) (list 1))
        ((= n 2) (list 0))
        ((= n 1) (list 1)) 
        (t 
             (append 
                  (binary-list (truncate n 2)) 
                  (list (rem n 2))
             )
         )
   )
)


(defun insert-heap-0(L X H BS)
    (cond
        ((not BS) 
            (list (max X H) () () )
        )
        (( = (car BS) 0)
            (setq son (insert-heap-0 (cadr L) X (car L) (cdr BS)))
            (cond
                ((= (car son) X) (setq V (car L)))
                ((not (numberp H) ) (setq V (car L)))
                (t (setq V (max H X)))
            )
            (list 
                V
                son
                (caddr L)
            )
        )
        ((= (car BS) 1)

            (setq son (insert-heap-0 (caddr L) X (car L) (cdr BS)))
            
            (cond
                ((= (car son) X) (setq V (car L)))
                ((not (numberp H) ) (setq V (car L)))
                (t (setq V (max H X)))
            )
            (list 
                V
                (cadr L)
                son
            )
        
        )
    )
)



(defun insert-heap (L X S)
    (cond 
        ((= S 0) (list X () ()))
        (t
            (insert-heap-0 L X () (binary-list (+ S 1)))
        )
    )
)




(setq l (insert-heap () 1 0 ))


(setq ll 
    (insert-heap
        '( 3 ( 5 ( 8 () () ) ( 10 () () ) ) ( 4 ( 11 () () ) () ) ) 9 6
    )
)

(print
    (insert-heap
        ll 4 7
    )
)


