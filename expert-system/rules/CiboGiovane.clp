;;;;;;CIBO <25 ANNI;;;;;

(deffacts knowledge
(budget low)
)


;********************************************************************************
;*                     Domande Cibo                                             *
;********************************************************************************

;;TIPOSVAGO
(defrule Domanda_dolcesalato
    (declare (salience 600))
    (budget low)
    (not (dolcesalato ?))
    =>
    (printout out "YN::prova") ;dolce o salato
    (assert (dolcesalato (read in)))
)

(defrule ko-dolcesalato
    (declare (salience 600))
    ?choice <- (dolcesalato ?dolcesalato&~dolce&~salato) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Cosa si preferisce?")
    (assert (dolcesalato (read in)))   
)
;;;OUTPUT dolce=bakery
;;;       salato=pizza/cafè

;********************************************************************************
;* RISULTATI                                                                    *
;********************************************************************************


;************TIPO POI*******************

;CIBO
(defrule salato
    (declare (salience 10))
    (dolcesalato salato)
    =>
    (printout out "ss::salato") ;pizza e cafè
)

(defrule dolce
    (declare (salience 10))
    (dolcesalato dolce)
    =>
    (printout out "ss::dolce") ;bakery
)