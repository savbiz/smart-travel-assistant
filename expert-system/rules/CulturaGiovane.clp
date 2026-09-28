;;;;;;CULTURA <25 ANNI;;;;;

(deffacts knowledge
(budget low)
(affollato si)
)

;********************************************************************************
;*                     Domande Culturale                                        *
;********************************************************************************

;;TIPOCULTURA
(defrule Domanda_tipocultura
    (declare (salience 600))
    (not (tipocultura ?))
    (budget low)
    =>
    (printout out "YN::prova") ;si o no
    (assert (tipocultura (read in)))
)

(defrule ko-tipocultura
    (declare (salience 600))
    ?choice <- (tipocultura ?tipocultura&~storica&~religiosa) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::A quale tipo di cultura si è interessati?")
    (assert (tipocultura (read in)))   
)


;;;;;;;;;;;;;;RELIGIOSO;;;;;;;;;;;;;;;;;;;


;;TIPORELIGIONE (INTERESSE IN ALTRE RELIGIONI O NO)
(defrule Domanda_tiporeligione
    (declare (salience 540))
    (not (tiporeligione ?))
    (tipocultura religiosa)
    =>
    (printout out "YN::prova") ;si o no
    (assert (tiporeligione (read in)))
)

(defrule ko-tiporeligione
    (declare (salience 540))
    ?choice <- (tiporeligione ?tiporeligione&~si&~no) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati ad altre religioni oltre a quella cristiana?")
    (assert (tiporeligione (read in)))   
)


;;TIPORELIGIONEOROCC
(defrule Domanda_tiporeligioneorocc
    (declare (salience 500))
    (not (tiporeligioneorocc ?))
    (tipocultura religiosa)
    (tiporeligione si) ;interessato ad altre religioni
    =>
    (printout out "YN::prova") ;si o no
    (assert (tiporeligioneorocc (read in)))
)

(defrule ko-tiporeligioneorocc
    (declare (salience 500))
    ?choice <- (tiporeligioneorocc ?tiporeligioneorocc&~occidentali&~orientali) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati a religioni occidentali o orientali?") 
    (assert (tiporeligioneorocc (read in)))   
)   ;;;OUTPUT ORIENTALI=MOSCHEE E TEMPLI HINDI
    ;;        OCCIDENTALI=SINAGOGHE


;;TIPORELIGIONECRISTIANA
(defrule Domanda_tiporeligionecristiana
    (declare (salience 500))
    (not (tiporeligionecristiana ?))
    (tiporeligione no) ;non interessato ad altre religioni
    =>
    (printout out "YN::prova") ;si o no
    (assert (tiporeligionecristiana (read in)))
)

(defrule ko-tiporeligionecristiana
    (declare (salience 500))
    ?choice <- (tiporeligionecristiana ?tiporeligionecristiana&~si&~no) ;interessato a san nicola
    =>
    (retract ?choice)
    (printout out "YN::Noto che si è a Bari, si è interessati alla storia di San Nicola?") 
    (assert (tiporeligionecristiana (read in)))   
)   ;;;OUTPUT SI=CHIESE (SAN NICOLA)
    ;;        NO=CHIESE


;;;;;;;;;;;;;;STORICO;;;;;;;;;;;;;;;

;;TIPOSTORIA (ARCHITETTURE MILITARI O MONUMENTI)
(defrule Domanda_tipostorico
    (declare (salience 550))
    (not (tipostorico ?))
    (tipocultura storica)
    =>
    (printout out "YN::prova") ;si o no
    (assert (tipostorico (read in)))
)

(defrule ko-tipostorico
    (declare (salience 550))
    ?choice <- (tipostorico ?tipostorico&~castelli&~monumenti) ;RISPOSTE
    =>
    (retract ?choice)
    (printout out "YN::Si è interessati a castelli o monumenti?")
    (assert (tipostorico (read in)))   
)

;;;OUTPUT CASTELLI=CASTELLI
;;;       MONUMENTI=PIAZZE (SUPPORTATO DA GPLACES)


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

;TIPO ALTRE RELIGIONI
(defrule tiporeligioneorocc_occidentali 
    (declare (salience 10))
    (tiporeligioneorocc occidentali)
    =>
    (printout out "ss::occidentali") ;religioni occidentali
)

(defrule tiporeligioneorocc_orientali
    (declare (salience 10))
    (tiporeligioneorocc orientali)
    =>
    (printout out "ss::orientali") ;religioni orientali
)

;;RELIGIONE CRISTIANA
(defrule tiporeligionecristiana_si ;sannicola
    (declare (salience 10))
    (tiporeligionecristiana si)
    =>
    (printout out "ss::sannicola") ;interesse san nicola
)

(defrule tiporeligionecristiana_no ;chiese generiche
    (declare (salience 10))
    (tiporeligionecristiana no)
    =>
    (printout out "ss::chiese") ;chiese generiche (non interesse san nicola)
)

;;TIPO CULTURA STORICA
(defrule tipostorico_castelli
    (declare (salience 10))
    (tipostorico castelli)
    =>
    (printout out "ss::castelli")
)

(defrule tipostorico_monumenti
    (declare (salience 10))
    (tipostorico monumenti)
    =>
    (printout out "ss::monumenti") ;piazze
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