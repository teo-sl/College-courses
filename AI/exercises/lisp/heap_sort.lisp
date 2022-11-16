(defun binary-list (n)
  (cond ((= n 3) (list 1))
        ((= n 2) (list 0))
        ((= n 1) ()) 
        (t 
             (append 
                  (binary-list (truncate n 2)) 
                  (list (rem n 2))
             )
         )
   )
)

(defun insert-heap-0 (L X BS)
    
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

(defun get-nth (H BL)
    (cond
        ((not BL)
            (car H)
        )
        ((= (car BL) 1)
            (get-nth (caddr H) (cdr BL))
        ) 
        (t
            (get-nth (cadr H) (cdr BL))
        )
    )
)

(defun delete-nth (H BL)
    (cond
        ((not BL)
            '()
        )
        ((= (car BL) 1)
            (list
                (car H)
                (cadr H)
                (delete-nth (caddr H) (cdr BL))
            )
            
        ) 
        (t
            (list
                (car H)
                (delete-nth (cadr H) (cdr BL))
                (caddr H)
            )
        )
    )

)
(defun set-root (H V)

    (list
        V
        (cadr H)
        (caddr H)
    )
)
(defun min-son (H) 
    (cond
        ((and (not (cadr H)) (not (caddr H)))
            '()
        )
        ((not (cadr H))
            (list  1 (caaddr H))
        )
        ((not (caddr H))
            (list 0 (caadr H))
        )
        ((< (caadr H) (caaddr H))
            (list  0 (caadr H))
        )
        (t
            (list 1 (caaddr H))
        )
    )
    
)

(defun fix-heap-down (H son_value)
    (setq min_son (min-son H))
    (cond
        ((not H)
            '()
        )
        ((not min_son)

            (list son_value '() '())
        )
        ((< (car H) (cadr min_son)) 
            (list
                son_value
                (cadr H)
                (caddr H)
            )
        )
        ( (= (car min_son ) 0) 
            
            (setq son_son_value (max (car H) son_value))
            (setq my_value (min (caadr H) son_value))

            (list
                my_value
                (fix-heap-down (cadr H) son_son_value )
                (caddr H)
            )
        )
        ((= (car min_son ) 1)
            
            (setq son_son_value (max (car H) son_value))
            (setq my_value (min (caaddr H) (car H)))
            (list
                my_value
                (cadr H)
                (fix-heap-down (caddr H)  son_son_value ) 
            )
        )
    )

)

(defun fix-heap-down (H son_value)
    
    (cond
        (; caso nodo vuoto
            (not H) '()
        )

        (;caso nodo foglia
            (and (not (cadr H)) 
              (not (caddr H))
            )
            (list son_value () ())
        )
        (;caso figlio sinistro
            (or (not (caddr H)) 
                (and (cadr H) (caddr H) (< (caadr H) (caaddr H)))
            )

            (setq my_value (min son_value (caadr H)))
            (cond 
                ((= my_value son_value)
                    (setq son_son_value (caadr H))
                )
                ((= my_value (caadr H))
                    (setq son_son_value son_value)
                )
            )
            (list
                my_value
                (fix-heap-down (cadr H) son_son_value)
                (caddr H)
            )
        )
        (;cado figlio destro
            (or (not (cadr H)) 
                (and (cadr H) (caddr H) (< (caaddr H) (caadr H)))
            )

            (setq my_value (min son_value (caaddr H)))
            (cond 
                ((= my_value son_value)
                    (setq son_son_value (caaddr H))
                )
                ((= my_value (caaddr H))
                    (setq son_son_value son_value)
                )
            )
            (list
                my_value
                (cadr H)
                (fix-heap-down (caddr H) son_son_value)
            )
        )
    )
)


(defun delete-root (H SH)
    (setq BL (binary-list SH))
    (setq new_root (get-nth H BL))
    (fix-heap-down 
        (set-root (delete-nth H BL) new_root)
        new_root
    )
    
)

(defun empty-heap (H SH)
    
    (cond
        ((= SH 1)
            (list (car H))
        )
        ((> SH 0)
            (append
                (list (car H))
                (empty-heap (delete-root H SH) (- SH 1))
            )

            
        )

    )
    

)

(defun heap_sort (L)
    (setq full_heap (feed-heap L (length L) () 0 ))
    
    (empty-heap full_heap (length L))
)


(setq h (heap_sort '(5 1 3 9 4 12 78 32 0 21 43 -1 45 10 )))
(print h)







