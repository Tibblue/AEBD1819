--Criar tablespace default e temporario
CREATE TABLESPACE TP_AEBD 
    DATAFILE 
        '\u01\app\oracle\oradata\orcl12\orcl\TP_AEBD_01.DBF' SIZE 104857600;

CREATE TEMPORARY TABLESPACE TP_TEMP 
    TEMPFILE 
        '\u01\app\oracle\oradata\orcl12\orcl\TP_TEMPORARY_01.DBF' SIZE 52428800 AUTOEXTEND ON NEXT 104857600 MAXSIZE 34359721984 
    EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1048576;

-- Criar utilizador, que refere aos tablespaces criados anteriormente
CREATE USER grupo2 IDENTIFIED BY pass  
DEFAULT TABLESPACE TP_AEBD
TEMPORARY TABLESPACE TP_TEMP
PASSWORD EXPIRE ;

-- Conceder acesso e controlo de bases de dados ao utilizador criado
GRANT "DBA" TO grupo2 ;

-- Conceder privilegios de inico de sessao e criação de tabelas ao utilizador
GRANT CREATE SESSION TO grupo2 ;
GRANT CREATE TABLE TO grupo2 ;

-- Testar se a coneção foi criada
connect grupo2/pass;

show user;
