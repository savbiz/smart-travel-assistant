;;;;;;SVAGO DONNA >=25 ANNI;;;;;

(defrule x
(declare (salience 3000))    
    =>
(assert (budget high))
(assert (forse bambini))
(assert (preference shopping))
)
;(deffacts knowledge
;(budget high)
;(forse bambini)
;(preference shopping)
;)

;********************************************************************************
;*                     Domande Svago                                            *
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

;;TIPOSVAGO
(defrule Domanda_tiposvago
    (declare (salience 595))
    (not (tiposvago ?))
    =>
    (printout out "YN::prova") ;relax o divertimento
    (assert (tiposvago (read in)))
)

(defrule ko-tiposvago
    (declare (salience 595))
    ?choice <- (tiposvago ?tiposvago&~relax&~divertimento) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::A cosa si è più propensi?")
    (assert (tiposvago (read in)))   
)

;;;;;;;;;;;;;;RELAX;;;;;;;;;;;;;;;;;;;

(defrule Domanda_tiponatura
    (declare (salience 550))
    (not (tiponatura ?))
    (tiposvago relax)
    (bambini si)
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
    (bambini si)
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


;;;;;;;;;;;;;;DIVERTIMENTO;;;;;;;;;;;;;;;;;;;
(defrule Domanda_tipodivertimento
    (declare (salience 550))
    (not (tipodivertimento ?))
    (tiposvago divertimento)
    (bambini si)
    =>
    (printout out "YN::prova") ;spiaggia o spazioverde
    (assert (tipodivertimento (read in)))
)

(defrule ko-tipodivertimento
    (declare (salience 550))
    ?choice <- (tipodivertimento ?tipodivertimento&~giochi&~animali) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Cosa si pensa potrebbe divertire di più i bambini?")
    (assert (tipodivertimento (read in)))   
)
;;;OUTPUT giochi=amusement_park (keyword)
;;;       animali=zoo|aquarium


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

;SUPPORT

(defrule shoppingcenter 
    (declare (salience 100))
    (bambini no)
    (tiposvago divertimento)
   =>
    (assert (shopping si))
)

(defrule spa 
    (declare (salience 100))
    (bambini no)
    (tiposvago relax)
   =>
    (assert (spa si))
) 

;********************************************************************************
;* RISULTATI                                                                    *
;********************************************************************************


;************TIPO POI*******************

;DIVERTIMENTO NO BAMBINI
(defrule tiposvago_divertimento_bambini_no
    (declare (salience 10))
    (shopping si)
    =>
    (printout out "ss::shopping") ;shopping mall
)

;DIVERTIMENTI SI BAMBINI
(defrule tipo_divertimento_giochi
    (declare (salience 10))
    (tipodivertimento giochi)
    =>
    (printout out "ss::giochi") ;amusement_park
)

(defrule tipo_divertimento_animali
    (declare (salience 10))
    (tipodivertimento animali)
    =>
    (printout out "ss::animali") ;zoo e aquarium
)

;RELAX NO BAMBINI
(defrule luoghiaffollati_sispa
    (declare (salience 10))
    (spa si)
    =>
    (printout out "ss::spa") ;relax spa
)

;RELAX SI BAMBINI
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
