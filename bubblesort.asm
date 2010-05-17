;Länge initialisieren
ADDM listeEnde
SUBM listeAnfang
ADDM 1
STORE n

:whilestart
LOAD null
STORE vertauscht
;WHILESTART
LOAD null
STORE i
:FORSTART LOAD i
SUB n
ADDM 1
JMPGEM FORENDE
;SCHLEIFE
LOAD null
ADDM listeAnfang
ADD i
STORE pointerA
ADDM 1
STORE pointerB
LOADI pointerA
SUBI pointerB
JMPLTM ENDIF
LOAD NULL
ADDM 1
STORE vertauscht
LOADI pointerA
STORE zwischenspeicher
LOADI pointerB
STOREI pointerA
LOAD zwischenspeicher
STOREI pointerB
:ENDIF
;SCHLEIFE ENDE
LOAD i
ADDM 1
STORE i
JMPM FORSTART
:FORENDE
;WHILEENDE
LOAD n
SUBM 1
STORE n
LOAD vertauscht
JMPEQM whileende
LOAD n
SUBM 1
JMPGEM whilestart
:whileende
HALT

;Variablen
:vertauscht
:n 0
:null 0
:i 0
:pointerA
:pointerB
:zwischenspeicher


:listeAnfang 19
18
23
53
12
:listeEnde 15