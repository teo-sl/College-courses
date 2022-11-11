;; to execute the file, use
;;
;; > clisp <filename>
;;
;; you first need to install clisp
;;
;; to show the results, use the print function

(setq x 5)
(setq y 6)

(print (+ x y)) 


(defun mod_vett(x y)
    (sqrt (+ (* x x) (* y y ) ) )
)

(print (mod_vett 2 3 ) )

(defun fatt(n)
    (cond 
        ((= n 1) 1)
        (T (* n (fatt(- n 1))))

    )
)

(print (fatt 5))

(print
    (car '(a b c))
)

(print
    (cdr '(a b c))
)

(print
    (cadaar '(((a b)) c d)
    )
)


(print
    (append '(1 2) '(3 4))
)

(print
    (reverse '(1 2 3 4))
)


(print
    (length '(1 2 3 4))
)

(print
    (list 'a 'b 'c)
)

(print
    (subst '1 '2 '(1 2 3 4))
)

(print
    (last '(1 2 3 4))
)

(defun rotate_sx(L)
    (append (cdr L) (list (car L)))
)

(setq L '(1 2 3))

(print
    (rotate_sx '(1 2 3) )

)

(defun rotate_dx(L)
    (reverse ( rotate_sx ( reverse L) ) )
)

(print
    (rotate_dx '(1 2 3) )

)

(defun fib(n)

    (cond 
        (( or (= n 0) (= n 1) ) 1)
        (T (+ (fib (- n 1 ))
              (fib (- n 2 ))
            )
        )
    )
)

(print
    (fib 10)
)

;; Other functions are
;;
;; ATOM/1               true if element is atom, nil if is a list
;; LISTP/1              true if element is list
;; NUMBERP/1            true if element is number
;; ZEROP/1              true if element is number and == zero
;; EVENP/1              true if element is number and even
;; MINUSP/1             true if element is number and < 0
;; MEMBER/2             <element> <list> true if element \in list
;;
