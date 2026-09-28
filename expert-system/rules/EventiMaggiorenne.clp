;;;;;;EVENTI MAGGIORENNE;;;;;


;;QUANDOEVENTI
(defrule Domanda_quandoeventi
    (declare (salience 600))
    (not (quandoeventi ?))
    =>
    (printout out "YN::prova") ;si o no
    (assert (quandoeventi (read in)))
)

(defrule ko-quandoeventi
    (declare (salience 600))
    ?choice <- (quandoeventi ?quandoeventi&~ora&~futuro)
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati ad eventi che si stanno tenendo ora o che si terranno in futuro?")
    (assert (quandoeventi (read in)))   
)



;********************************************************************************
;* Domande  Possibilita' di muoversi                                            *
;********************************************************************************

;;AUTOMUNITO
(defrule Domanda_automunito
    (declare (salience 570))
    (not (automunito ?))   
    =>
    (printout out "YN::prova")
    (assert (automunito (read in)))
)

(defrule ko-automunito
    (declare (salience 570))  
    ?choice <- (automunito ?automunito&~si&~no)
    =>
    (retract ?choice)
    (printout out "YN::Si è automuniti?")
    (assert (automunito (read in)))
)  

;;COMPAGNIA
(defrule Domanda_compagnia
	(declare (salience 550))
    (not (compagnia ?))
    (automunito no)
	=>
    (printout out "YN::prova") ;solo o altre persone
    (assert (compagnia (read in)))
)

(defrule ko-compagnia
    (declare (salience 550))
    ?choice <- (compagnia ?compagnia&~solo&~altri)
    =>
    (retract ?choice)
    (printout out "YN::Con chi si viaggia?")
    (assert (compagnia (read in)))
)

;;AUTOMUNITO ALTRI
(defrule Domanda_automunitoaltri
    (declare (salience 400))
    (not (automunitoaltri ?))   
    (compagnia altri)
    =>
    (printout out "YN::prova")
    (assert (automunitoaltri (read in)))
)

(defrule ko-automunitoaltri
    (declare (salience 400))  
    ?choice <- (automunitoaltri ?automunitoaltri&~si&~no)
    =>
    (retract ?choice)
    (printout out "YN::Qualcuno che è con te è automunito?")
    (assert (automunitoaltri (read in)))
)        

(defrule a_piedi
    (declare (salience 300))
    (or (automunito no)(automunitoaltri no))
    =>
    (assert (apiedi si))
) ;CITTA' STESSA

;********************************************************************************
;* Domande  Distanza                                                            *
;********************************************************************************

;DISTANZA AUTO
(defrule Domanda_distanzaauto
    (declare (salience 350))
    (or (automunitoaltri si)(automunito si))
    (not (distanzaauto ?))
    =>
    (printout out "YN::prova")
    (assert (distanzaauto (read in)))
)

(defrule ko-distanzaauto 
    (declare (salience 350))
    ?choice <- (distanzaauto ?distanzaauto&~cinquantakm&~centokm)
    =>
    (retract ?choice)
    (printout out "YN::Quanto si è disposti a guidare?")
    (assert (distanzaauto (read in)))
) ;;50KM o 100KM.


;********************************************************************************
;* RISULTATI                                                                    *
;********************************************************************************

;************QUANDO*******************
(defrule quandoeventi_ora 
    (declare (salience 10))
    (quandoeventi ora)
    =>
    (printout out "ss::ora") ;control on first part of description (date)
)

(defrule quandoeventi_futuro
    (declare (salience 10))
    (quandoeventi futuro)
    =>
    (printout out "ss::futuro") ;control on first part of description (date)
)


;************RAGGIO*******************
(defrule apiedi
    (declare (salience 5))
    (apiedi si)
    =>
    (printout out "ss::apiedi") ;stessa città Address a = getFromLocation (lat,lon), a.toString() compare with string in description event
)

(defrule distanzaauto_50km
    (declare (salience 5))
    (distanzaauto cinquantakm)
    =>
    (printout out "ss::50km") ;50km Address a = getFromLocationName (String third_part_of_description) --- distancebetween (lanuser,lonuser,a.getLat,a.getLon, float result[]). result[0]<50000.
)

(defrule distanzaauto_100km
    (declare (salience 5))
    (distanzaauto centokm)
    =>
    (printout out "ss::100km") ;100km Address a = getFromLocationName (String third_part_of_description) --- distancebetween(lanuser,lonuser,a.getLat,a.getLon, float result[]). result[0]<100000.
)