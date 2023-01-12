/**
SCRIPT PER IL POPOLAMENTO CON DATI DI PROVA, ASSICURARSI DI ESEGUIRE PRIMA LO SCRIPT DB-STRUTTURA
*/

--SEZIONE LABORATORIO
INSERT INTO LABORATORIO (NOME) VALUES ('Laboratorio Newton');
INSERT INTO LABORATORIO (NOME) VALUES ('Laboratorio Tesla');



--SEZIONE SEDE
INSERT INTO SEDE (INDIRIZZO, ANNOFONDAZIONE, ID_LAB) VALUES ('Via Duomo 23', '1986', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%NEWTON%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO SEDE (INDIRIZZO, ANNOFONDAZIONE, ID_LAB) VALUES ('Via Scarlatti 77', '2007', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%TESLA%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO SEDE (INDIRIZZO, ANNOFONDAZIONE, ID_LAB) VALUES ('Via dei mille 43', '2011', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%NEWTON%') FETCH NEXT 1 ROWS ONLY));

--SEZIONE POSTAZIONE
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('3', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('4', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NUMERO, ID_SEDE) VALUES ('2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));



--SEZIONE STRUMENTO
--SOTTOSEZIONE SEDE 'VIA DUOMO'
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Microscopio Elettronico', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Centrifuga', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Miscelatore', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='2'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Bilancia di precisione', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='2'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Agitatore magnetico', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='2'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Cappa chimica', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='3'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Glove box', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='4'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Pompa per vuoto', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NUMERO='4'));
--SOTTOSEZIONE SEDE 'VIA SCARLATTI'
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Oscilloscopio', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Alimentatore da banco', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Generatore di forme d''onda', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Saldatore di precisione', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Camera bianca', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND NUMERO='2'));
--SOTTOSEZIONE SEDE 'VIA DEI MILLE'
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Microscopio Elettronico', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Piastra riscaldante', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND NUMERO='1'));
INSERT INTO STRUMENTO (DESCRIZIONE, ID_POSTAZIONE) 
VALUES ('Incubatore', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND NUMERO='2'));

--SEZIONE RESPONSABILE
INSERT INTO RESPONSABILE (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('R123456789', 'Roberto', 'Rossi', DATE '1969-11-24', 'RSSRRT69E01G234I', 'Via Roma 11', '3341234567', 'rob.rossi@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO RESPONSABILE (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('R987654321', 'Michele', 'Verdi', DATE '1982-06-11', 'VRDMHL82E01G455X', 'Vico nuovo 33', '3345678901', 'mitch.green@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO RESPONSABILE (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('R010156789', 'Massimo', 'De Angelis', DATE '1974-02-15', 'DNGMSM72D01H581V', 'Piazza san Marco 11', '3678967890', 'max.ang@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));


--SEZIONE TECNICO
INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,TELEFONO2,EMAIL,ID_SEDE)
VALUES ('T000000001', 'Nicola', 'Esposito', DATE '1994-01-15', 'SPSNCL94D10H581A', 'Via Marina 10', '3448567810','0812345678', 'nick.exp@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('T000000002', 'Maria', 'Gentile', DATE '1992-04-18', 'GNTMRA92D58D947F', 'Via Armando Diaz 3', '3348261110', 'mary.kind@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('T000000003', 'Lucia', 'Cecchi', DATE '1988-12-15', 'CCCLCU88T55M185K', 'Via Medina 17', '3311564010', 'lucy.lu@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('T000000004', 'Marco', 'De Luca', DATE '1987-11-15', 'DLCMRC87S15I082P', 'Via Giulio Cesare 2', '3771824344', 'mark.gp@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO TECNICO (MATRICOLA,NOME,COGNOME,DATANASCITA,CODICEFISCALE,INDIRIZZO,TELEFONO1,EMAIL,ID_SEDE)
VALUES ('T000000005', 'Giulio', 'Mattei', DATE '1990-05-02', 'MTTGLI90E02I082W', 'Piazza medaglie d''oro 22', '3452276344', 'jul.best@email.it', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));

--SEZIONE UTENTE
INSERT INTO UTENTE VALUES ('cicciogamer89@paguro.it', 'cicciogamer89', (SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM('paguro89')), 'AL32UTF8') FROM DUAL));
INSERT INTO UTENTE VALUES ('albertEinstein22@mail.it', 'MadScientist', (SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM('Einstein111')), 'AL32UTF8') FROM DUAL));


--SEZIONE PRENOTAZIONE
INSERT INTO PRENOTAZIONE(DURATA,DATAINIZIO,ID_STRUMENTO,USERNAME) 
VALUES ('15', (SELECT TO_DATE('2023/02/02 16:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%OSCILLOSCOPIO%') FETCH NEXT 1 ROWS ONLY), (SELECT USERNAME FROM UTENTE WHERE UPPER(UTENTE.USERNAME) LIKE('%CICCIO%') FETCH NEXT 1 ROWS ONLY));                      






































































/*

--SEZIONE PER IL DEBUG 
(SELECT UTL_I18N.STRING_TO_RAW('paguro23', 'AL32UTF8') FROM DUAL);
SELECT UTL_RAW.CAST_TO_VARCHAR2('70616775726F3233') FROM DUAL;

SELECT * FROM UTENTE;

SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM('paguro23')), 'AL32UTF8') FROM DUAL;

--DELETE PRENOTAZIONE WHERE ID_PRENOTAZIONE='2';

DECLARE 
pwd VARCHAR2(1000);
encrpt RAW(1000);
prov INTEGER;
BEGIN
encrpt := encrypt_pwd('paguro23');
DBMS_OUTPUT.PUT_LINE(encrpt);
pwd := decrypt_pwd(encrpt);
DBMS_OUTPUT.PUT_LINE('  paguro23 ');
DBMS_OUTPUT.PUT_LINE(LTRIM(RTRIM('  paguro23 ')));
SELECT LENGTH('paguro23')INTO prov FROM DUAL;
DBMS_OUTPUT.PUT_LINE(prov);
END prova;
/

SELECT REGEXP_INSTR('paguro23', '[[:digit:]]'), REGEXP_INSTR('paguro23', '[a-zA-z]') FROM DUAL;

**/