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


(defun insert-heap-0(L X BS)
    
    (cond
        ((not BS) 
            (list X () () )
        )
        (( = (car BS) 0)
            (setq son (insert-heap-0 (cadr L) X (cdr BS)))
            (list 
                (car L)
                son
                (caddr L)
            )
        )
        ((= (car BS) 1)
            (setq son (insert-heap-0 (caddr L) X (cdr BS)))
            (list 
                (car L)
                (cadr L)
                son
            )
        )
    )
)

(defun fix-heap (L BS)
    (cond
        ( (not BS) L)
        ( (= (car BS) 0) 
            (setq son (fix-heap (cadr L) (cdr BS) ))
            
            (list
                (min (car L) (car son))
                (list
                    (max (car L) (car son))
                    (cadr son)
                    (caddr son)
                )
                (caddr L)
            )
        )
        ( ( = (car BS) 1)
            (setq son (fix-heap (caddr L) (cdr BS) ))
            (list
                (min (car L) (car son))
                (cadr L)
                (list
                    (max (car L) (car son))
                    (cadr son)
                    (caddr son)
                )
            )
        )
    )
)



(defun insert-heap (L X S)
    (cond 
        ((= S 0) (list X () ()))
        (t
            (fix-heap (insert-heap-0 L X (binary-list (+ S 1))) (binary-list (+ S 1)))
        )
    )
)






(defun feed-heap (L VS H HS)
    (cond
        ((= VS 0)
            H
        )
        (t
            (feed-heap 
                (cdr L)
                (- VS 1)
                (insert-heap H (car L) HS)
                (+ HS 1)
            )
        )
    )
)

(defun heap_sort (L)
    (feed-heap L (length L) () 0 )
)





