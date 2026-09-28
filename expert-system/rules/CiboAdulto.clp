;;;;;;CIBO >=25 ANNI;;;;;

(deffacts knowledge
(budget medium)
(forse bambini)
)


;********************************************************************************
;*                     Domande Cibo                                             *
;********************************************************************************

(defrule Domanda_bambini
    (declare (salience 600))
    (not (bambini ?))
    (forse bambini)
    =>
    (printout out "YN::prova") ;si o no
    (assert (bambini (read in)))
)

(defrule ko-bambini
    (declare (salience 600))
    ?choice <- (bambini ?bambini&~si&~no) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Hai bambini con te?")
    (assert (bambini (read in)))   
)

;TIPO PASTO PASTO o LEGGERO
(defrule Domanda_tipopasto
    (declare (salience 550))
    (not (tipopasto ?))
    =>
    (printout out "YN::prova") ;si o no
    (assert (tipopasto (read in)))
)

(defrule ko-tipopasto
    (declare (salience 550))
    ?choice <- (tipopasto ?tipopasto&~pasto&~leggero) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si desidera un pasto completo o qualcosa di leggero?")
    (assert (tipopasto (read in)))   
)
;OUTPUT pasto e bambini=pizza

;SUPPORT PIZZA PER BAMBINI

(defrule pizza
    (declare (salience 100))
    (bambini si)
    (tipopasto pasto)
   =>
    (assert (pizza si))
)

;;TIPOLEGGERO
(defrule Domanda_dolcesalato
    (declare (salience 500))
    (not (dolcesalato ?))
    (tipopasto leggero)    
    =>
    (printout out "YN::prova") ;dolce o salato
    (assert (dolcesalato (read in)))
)

(defrule ko-dolcesalato
    (declare (salience 500))
    ?choice <- (dolcesalato ?dolcesalato&~dolce&~salato) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Cosa si preferisce?")
    (assert (dolcesalato (read in)))   
)
;;;OUTPUT dolce=bakery
;;;       salato=pizza/cafè

;;TIPOCUCINA
(defrule Domanda_cucinaetnica
    (declare (salience 450))
    (not (cucinaetnica?))
    (tipopasto pasto)   
    (bambini no) 
    =>
    (printout out "YN::prova") ;si o no
    (assert (cucinaetnica (read in)))
)

(defrule ko-cucinaetnica
    (declare (salience 450))
    ?choice <- (cucinaetnica ?cucinaetnica&~si&~no) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati alla cucina etnica?")
    (assert (cucinaetnica (read in)))   
)

(defrule pizza_noetnica
    (declare (salience 100))
    (tipopasto pasto)
    (cucinaetnica no)
   =>
    (assert (italiano si))
)


;TIPO ETNIA
(defrule Domanda_tipoetnia
    (declare (salience 400))
    (not (tipoetnia ?))
    (cucinaetnica si)
    =>
    (printout out "YN::prova") ;si o no
    (assert (tipoetnia (read in)))
)

(defrule ko-tipoetnia
    (declare (salience 400))
    ?choice <- (tipoetnia ?tipoetnia&~occidentale&~orientale) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati alla cucina occidentale od orientale?")
    (assert (tipoetnia (read in)))   
)

;************TIPO CIBO*******************

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

;PASTO SI BAMBINI
(defrule pasto_pizza
    (declare (salience 10))
    (pizza si)
    =>
    (printout out "ss::pizza") ;pizza
)

;PASTO NO BAMBINI NO ETNICA
(defrule cucina_italiana
    (declare (salience 10))
    (italiano si)
    =>
    (printout out "ss::italiano") ;cucina italiana
)

;PASTO NO BAMBINI SI ETNICA
(defrule cucina_orientale
    (declare (salience 10))
    (tipoetnia orientale)
    =>
    (printout out "ss::orientale") ;cucina orientale
)

;PASTO NO BAMBINI SI ETNICA
(defrule cucina_occidentale
    (declare (salience 10))
    (tipoetnia occidentale)
    =>
    (printout out "ss::occidentale") ;cucina occidentale
)