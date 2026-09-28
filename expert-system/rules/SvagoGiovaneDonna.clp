;;;;;;SVAGO DONNA <25 ANNI;;;;;

(deffacts knowledge
(budget medium)
(preference shopping)
)

;********************************************************************************
;*                     Domande Svago                                            *
;********************************************************************************

;;TIPOSVAGO
(defrule Domanda_tiposvago
    (declare (salience 600))
    (not (tiposvago ?))
    (budget medium)
    =>
    (printout out "YN::prova") ;relax o divertimento
    (assert (tiposvago (read in)))
)

(defrule ko-tiposvago
    (declare (salience 600))
    ?choice <- (tiposvago ?tiposvago&~relax&~divertimento) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::A cosa si è più propensi?")
    (assert (tiposvago (read in)))   
)
;;OUTPUT divertimento=shopping mall


;;;;;;;;;;;;;;RELAX;;;;;;;;;;;;;;;;;;;

(defrule Domanda_luoghiaffollati
    (declare (salience 590))
    (not (luoghiaffollati ?))
    (tiposvago relax)
    =>
    (printout out "YN::prova") ;si o no
    (assert (luoghiaffollati (read in)))
)

(defrule ko-luoghiaffollati
    (declare (salience 590))
    ?choice <- (luoghiaffollati ?luoghiaffollati&~si&~no) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Ti infastidiscono i luoghi affollati?")
    (assert (luoghiaffollati (read in)))   
)
;;;OUTPUT si=  SPA


(defrule Domanda_tiponatura
    (declare (salience 550))
    (not (tiponatura ?))
    (tiposvago relax)
    (luoghiaffollati no)
    =>
    (printout out "YN::prova") ;spiaggia o spazioverde
    (assert (tiponatura (read in)))
)

(defrule ko-tiponatura
    (declare (salience 550))
    ?choice <- (tiponatura ?tiponatura&~spazioverde&~spiaggia) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::A cosa si è interessati?")
    (assert (tiponatura (read in)))   
)
;;;OUTPUT spiaggia=SPIAGGIA (keyword)

(defrule Domanda_tipospazioverde
    (declare (salience 540))
    (not (tipospazioverde ?))
    (tiposvago relax)
    (luoghiaffollati no)
    (tiponatura spazioverde)
    =>
    (printout out "YN::prova") ;si o no
    (assert (tipospazioverde (read in)))
)

(defrule ko-tipospazioverde
    (declare (salience 540))
    ?choice <- (tipospazioverde ?tipospazioverde&~giardini&~parchi) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati a giardini comunali o ad grandi parchi?")
    (assert (tipospazioverde (read in)))   
)
;;;OUTPUT giardini=giardini (keyword)
;;;       parchi=parchi

;********************************************************************************
;* Domande  Raggio azione                                                       *
;********************************************************************************


;;AUTOMUNITO
(defrule Domanda_automunito
    (declare (salience 450))
    (not (automunito ?))
    =>
    (printout out "YN::prova") ;si o no
    (assert (automunito (read in)))
)

(defrule ko-automunito 
    (declare (salience 450))
    ?choice <- (automunito ?automunito&~si&~no)
    =>
    (retract ?choice)
    (printout out "YN::Si è automuniti o si è con qualcuno automunito?") ;si o no
    (assert (automunito (read in)))
)  

;;;OUTPUT NO=RAGGIO 10000m (grazie a mezzi pubblici)

;********************************************************************************
;* Domande  Distanza Auto                                                       *
;********************************************************************************

;DISTANZA AUTO
(defrule Domanda_distanzaauto
    (declare (salience 350))
    (automunito si)
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
   (printout out "YN::Fino a quanto si è disposti a guidare?")
   (assert (distanzaauto (read in)))
)

;;;OUTPUT cinquantakm= RAGGIO 50000m
;;;       centokm= RAGGIO 100000m




;********************************************************************************
;* RISULTATI                                                                    *
;********************************************************************************


;************TIPO POI*******************

;DIVERTIMENTO
(defrule tiposvago_divertimento 
    (declare (salience 10))
    (tiposvago divertimento)
    =>
    (printout out "ss::shopping") ;shopping mall
)

;RELAX
(defrule luoghiaffollati_sispa
    (declare (salience 10))
    (luoghiaffollati si)
    =>
    (printout out "ss::spa") ;relax spa
)

(defrule tiponatura_spiaggia
    (declare (salience 10))
    (tiponatura spiaggia)
    =>
    (printout out "ss::spiaggia")
)

(defrule tipospazioverde_giardini 
    (declare (salience 10))
    (tipospazioverde giardini)
    =>
    (printout out "ss::giardini")
)

(defrule tipospazioverde_parchi
    (declare (salience 10))
    (tipospazioverde parchi)
    =>
    (printout out "ss::parchi")
)

;************RAGGIO*******************

(defrule distanzamezzipub_10km
    (declare (salience 5))
    (automunito no)
    =>
    (printout out "ss::10km") ;mezzi pubblici
)

(defrule distanzaauto_50km
    (declare (salience 5))
    (distanzaauto cinquantakm)
    =>
    (printout out "ss::50km") ;50km auto
)

(defrule distanzaauto_100km
    (declare (salience 5))
    (distanzaauto centokm)
    =>
    (printout out "ss::100km") ;100km auto
)
