    ;;;;;;SVAGO <25 ANNI UOMO;;;;;

(deffacts knowledge
(budget low)
)


;********************************************************************************
;*                     Domande Svago                                            *
;********************************************************************************

;;TIPOSVAGO
(defrule Domanda_tiposvago
    (declare (salience 600))
    (not (tiposvago ?))
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

;;;;;;;;;;;;;;DIVERTIMENTO;;;;;;;;;;;;;;;;;;;
(defrule Domanda_sport
    (declare (salience 550))
    (not (sport ?))
    (tiposvago divertimento)
    =>
    (printout out "YN::prova") ;si o no
    (assert (sport (read in)))
)

(defrule ko-sport
    (declare (salience 550))
    ?choice <- (sport ?sport&~si&~no) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati ad eventi sportivi?")
    (assert (sport (read in)))   
)

;;;OUTPUT SI=stadium
;;;       NO=night_club

;;;;;;;;;;;;;;RELAX;;;;;;;;;;;;;;;;;;;


(defrule Domanda_tiponatura
    (declare (salience 550))
    (not (tiponatura ?))
    (tiposvago relax)
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

;;BUDGET MEZZI PUBBLICI
(defrule Domanda_budgetmezzipub 
    (declare (salience 400))
    (automunito no)
    =>
    (printout out "YN::prova")
    (assert (mezzipub (read in)))
)

(defrule ko-mezzipub 
    (declare (salience 400))
   ?choice <- (mezzipub ?mezzipub&~si&~no)
   =>
   (retract ?choice)
   (printout out "YN::Si è disposti a pagare il biglietto di un mezzo pubblico?")
   (assert (mezzipub (read in)))
)
;;;OUTPUT SI=RAGGIO 10000m


;********************************************************************************
;* Domande  Distanza                                                            *
;********************************************************************************


;;DISTANZA PIEDI
(defrule Domanda_distanzapiedi 
    (declare (salience 350))
    (mezzipub no)
    =>
    (printout out "YN::prova")
    (assert (distanzapiedi (read in)))
)

(defrule ko-distanzapiedi 
    (declare (salience 350))   
    ?choice <- (distanzapiedi ?distanzapiedi&~unkm&~duekm) ;perché giovane
   =>
   (retract ?choice)
   (printout out "YN::Fino a quanto si è disposti a camminare?")
   (assert (distanzapiedi (read in)))
)


;;;OUTPUT unkm= RAGGIO 1000m
;;;       duekm= RAGGIO 2000m


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
(defrule sport_si
    (declare (salience 10))
    (sport si)
    =>
    (printout out "ss::sport") ;stadium
)

(defrule sport_no
    (declare (salience 10))
    (sport no)
    =>
    (printout out "ss::nightclub") ;nightclub
)

;RELAX
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

(defrule distanzapiedi_1km
    (declare (salience 5))
    (distanzapiedi unkm)
    =>
    (printout out "ss::1km") ;50km auto
)

(defrule distanzapiedi_2km
    (declare (salience 5))
    (distanzapiedi duekm)
    =>
    (printout out "ss::2km") ;100km auto
)

(defrule distanzamezzipub_10km
    (declare (salience 5))
    (mezzipub si)
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

