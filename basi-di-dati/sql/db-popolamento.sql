/**
SCRIPT PER IL POPOLAMENTO CON DATI DI PROVA, ASSICURARSI DI ESEGUIRE PRIMA LO SCRIPT DB-STRUTTURA
*/

--SEZIONE LABORATORIO
INSERT INTO LABORATORIO (NOME, DESCRIZIONE, CAMPO, ANNOFONDAZIONE) VALUES ('Laboratorio Newton', 'Il Laboratorio Newton progetta, sviluppa, produce e distribuisce dispositivi medici certificati, con particolare riferimento ad accessi vascolari e dispositivi medici monouso.', 'Bioingegneria', 1850);
INSERT INTO LABORATORIO (NOME, DESCRIZIONE, CAMPO, ANNOFONDAZIONE) VALUES ('Laboratorio Tesla', 'Nel Laboratorio Nazionale Tesla, moltissimi scienziati utilizzano l''esclusivo acceleratore di particelle, per sondare gli elementi costitutivi piu'' basilari della materia, aiutandoci a comprendere meglio queste particelle e le forze che le legano.', 'Chimica', 1923);

--SEZIONE SEDE
INSERT INTO SEDE (INDIRIZZO, ID_LAB) 
VALUES ('Via Duomo 23', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%NEWTON%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO SEDE (INDIRIZZO, ID_LAB) 
VALUES ('Via Scarlatti 77', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%TESLA%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO SEDE (INDIRIZZO, ID_LAB) 
VALUES ('Via dei mille 43', (SELECT ID_LAB FROM LABORATORIO WHERE UPPER(LABORATORIO.NOME) LIKE ('%NEWTON%') FETCH NEXT 1 ROWS ONLY));

--SEZIONE POSTAZIONE
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A3', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B3', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B2', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('A1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));
INSERT INTO POSTAZIONE (NOME, ID_SEDE) 
VALUES ('B1', (SELECT ID_SEDE FROM SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') FETCH NEXT 1 ROWS ONLY));

--SEZIONE STRUMENTO
--SCHEDA TECNICA -> 'Marca: , Dimensioni: cm x cm x cm, Voltaggio: V, Potenza: W'
--SOTTOSEZIONE SEDE 'VIA DUOMO'
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Microscopio Elettronico', 'Marca: ArgonTech, Dimensioni: 60cm x 50cm x 20cm, Peso: 3.0Kg, Voltaggio: 100V, Potenza: 10W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND NOME='A1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Centrifuga', 'Marca: KITSeft, Dimensioni: 30cm x 30m x 50cm, Peso: 2.5Kg, Voltaggio: 220V, Potenza: 25W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='A1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Miscelatore', 'Marca: KITSeft, Dimensioni: 20cm x 20cm x 15cm, Peso: 3.7Kg, Voltaggio: 12V, Potenza: 3W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='A2'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Bilancia di precisione', 'Marca: ArgonTech, Dimensioni: 20cm x 20cm x 5cm, Peso: 6.0Kg, Voltaggio: 12V, Potenza: 3W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='A2'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Agitatore magnetico', 'Marca: ArgonTech, Dimensioni: 23cm x 23cm x 10cm, Peso: 4.7Kg, Voltaggio: 120V, Potenza: 20W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='A2'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Cappa chimica', 'Marca: LabProto, Dimensioni: 60cm x 180cm x 1cm, Peso: 0.5Kg', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='A3'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Glove box', 'Marca: LabProto, Dimensioni: 20cm x 10cm x 10cm, Peso: 0.5Kg', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='B1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Pompa per vuoto', 'Marca: KITSeft, Dimensioni: 37cm x 17cm x 28cm, Peso: 12.0Kg, Voltaggio: 220V, Potenza: 50W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DUOMO%') AND  NOME='B2'));
--SOTTOSEZIONE SEDE 'VIA SCARLATTI'
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Oscilloscopio', 'Marca: KITSeft, Dimensioni: 15cm x 10cm x 5cm, Peso: 1.0Kg, Voltaggio: 30V, Potenza: 5W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND  NOME='A1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Alimentatore da banco', 'Marca: LabProto, Dimensioni: 30cm x 20cm x 5cm, Peso: 1.3Kg, Voltaggio: 120V, Potenza: 12W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND  NOME='A1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Generatore di forme d''onda', 'Marca: LabProto, Dimensioni: 20cm x 10cm x 5cm, Peso: 0.8Kg, Voltaggio: 12V, Potenza: 3W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND  NOME='A2'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Saldatore di precisione', 'Marca: ArgonTech, Dimensioni: 10cm x 1cm x 1cm, Peso: 0.7Kg, Voltaggio: 12V, Potenza: 3W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA SCARLATTI%') AND  NOME='A2'));
--SOTTOSEZIONE SEDE 'VIA DEI MILLE'
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Microscopio Elettronico', 'Marca: ArgonTech, Dimensioni: 40cm x 30cm x 70cm, Peso: 3.0Kg, Voltaggio: 220V, Potenza: 12W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND  NOME='A1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Piastra riscaldante', 'Marca: KITSeft, Dimensioni: 30cm x 30cm x 5cm, Peso: 2.0Kg, Voltaggio: 120V, Potenza: 5W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND  NOME='B1'));
INSERT INTO STRUMENTO (DESCRIZIONE, SCHEDATECNICA, ID_POSTAZIONE) 
VALUES ('Incubatore', 'Marca: ArgonTech, Dimensioni: 50cm x 50cm x 50cm, Peso: 6.0Kg, Voltaggio: 220V, Potenza: 20W', (SELECT ID_POSTAZIONE FROM POSTAZIONE JOIN SEDE ON POSTAZIONE.ID_SEDE=SEDE.ID_SEDE WHERE UPPER(SEDE.INDIRIZZO) LIKE ('%VIA DEI MILLE%') AND  NOME='B1'));

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
INSERT INTO UTENTE(USERNAME, USR_PASSWORD, EMAIL)
VALUES ('cicciogamer89', (SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM('paguro89')), 'AL32UTF8') FROM DUAL), 'cicciogamer89@paguro.it');
INSERT INTO UTENTE(USERNAME, USR_PASSWORD, EMAIL)
VALUES ('MadScientist', (SELECT UTL_I18N.STRING_TO_RAW(LTRIM(RTRIM('Einstein111')), 'AL32UTF8') FROM DUAL), 'albertEinstein22@mail.it');

--SEZIONE PRENOTAZIONE
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/05/10 17:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%OSCILLOSCOPIO%') FETCH NEXT 1 ROWS ONLY), 'cicciogamer89', 3);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/07/10 08:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%MICROSCOPIO%') FETCH NEXT 1 ROWS ONLY), 'cicciogamer89', 4);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/10/10 23:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%PIASTRA%') FETCH NEXT 1 ROWS ONLY), 'cicciogamer89', 5);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/03/10 13:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%SALDATORE%') FETCH NEXT 1 ROWS ONLY), 'cicciogamer89', 6);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/03/10 02:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%CAPPA%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 7);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/01/10 12:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%AGITATORE%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 8);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/12/10 11:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%BILANCIA%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 9);
INSERT INTO PRENOTAZIONE(DATAINIZIO, DURATA, USERNAME, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/08/10 09:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%OSCILLOSCOPIO%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 10);
INSERT INTO PRENOTAZIONE(DataInizio, Durata, Username, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/09/10 07:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%PIASTRA%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 11);
INSERT INTO PRENOTAZIONE(DataInizio, Durata, Username, ID_STRUMENTO)
VALUES ((SELECT TO_DATE('2025/07/10 07:30', 'YYYY/MM/DD HH24:MI') FROM DUAL), (SELECT ID_STRUMENTO FROM STRUMENTO WHERE UPPER(STRUMENTO.DESCRIZIONE) LIKE ('%MICROSCOPIO%') FETCH NEXT 1 ROWS ONLY), 'MadScientist', 12);










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