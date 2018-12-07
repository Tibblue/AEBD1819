--TABLESPACE TP_AEBD
CREATE TABLESPACE TP_AEBD 
    DATAFILE 
        '\u01\app\oracle\oradata\orcl12\orcl\TP_AEBD_01.DBF' SIZE 104857600;
--TABLESPACE TP_TEMP   
CREATE TEMPORARY TABLESPACE TP_TEMP 
    TEMPFILE 
        '\u01\app\oracle\oradata\orcl12\orcl\TP_TEMPORARY_01.DBF' SIZE 52428800 AUTOEXTEND ON NEXT 104857600 MAXSIZE 34359721984 
    EXTENT MANAGEMENT LOCAL UNIFORM SIZE 1048576;

-- Ex. 4
CREATE USER grupo2 IDENTIFIED BY pass  
DEFAULT TABLESPACE TP_AEBD
TEMPORARY TABLESPACE TP_TEMP
PASSWORD EXPIRE ;

ALTER USER grupo2 QUOTA UNLIMITED ON TP_AEBD;

-- Ex.5
GRANT "DBA" TO grupo2 ;

GRANT CREATE SESSION TO grupo2 ;
GRANT CREATE TABLE TO grupo2 ;

-- Ex.7
connect grupo2/pass;

show user;


CREATE TABLE db(
    id_db number(30) NOT NULL,
    name varchar(200) NOT NULL,
    platform number(20) NOT NULL,
    data_storage number(20) NOT NULL,
    number_sessions number(20) NOT NULL,
    db_timestamp timestamp NOT NULL,
    CONSTRAINT id_db PRIMARY KEY (id_db)
    );

CREATE TABLE memory(
    id_memory number(30) NOT NULL,
    pool varchar(50) NOT NULL,
    allocBytes number(20) NOT NULL,
    usedBytes number(20) NOT NULL,
    populated_status varchar(90) NOT NULL,
    mem_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_memory PRIMARY KEY (id_memory),
    CONSTRAINT id_db_FK
        FOREIGN KEY (id_db)
        REFERENCES db(id_db)
    );

CREATE TABLE cpu(
    id_cpu number(30) NOT NULL,
    cpu_count number(20) NOT NULL,
    db_version varchar(70) NOT NULL,
    cpu_coreCount number(20) NOT NULL,
    cpu_socketCount number(20) NOT NULL,
    cpu_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_cpu PRIMARY KEY (id_cpu),
    CONSTRAINT id_db_FK
        FOREIGN KEY (id_db)
        REFERENCES db(id_db)
    
    );


CREATE TABLE user(
    id_user number(30) NOT NULL,
    username varchar(70) NOT NULL,
    account_status varchar(70) NOT NULL,
    default_ts varchar(70) NOT NULL,
    temp_ts varchar(70) NOT NULL,
    last_login timestamp NOT NULL, 
    role varchar(30) NOT NULL,
    session varchar(30) NOT NULL,
    user_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_user PRIMARY KEY (id_user),
    CONSTRAINT id_db_FK
        FOREIGN KEY (id_db)
        REFERENCES db(id_db)
    );


CREATE TABLE tablespace(
    id_tablespace number(30) NOT NULL,
    name varchar(20) NOT NULL,
    block_size number(70) NOT NULL,
    max_size number(20) NOT NULL,
    status varchar(20) NOT NULL,
    contents varchar(30) NOT NULL,
    initial_extent number(20) NOT NULL,
    tablespace_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_tablespace PRIMARY KEY (id_tablespace),
    CONSTRAINT id_db_FK
        FOREIGN KEY (id_db)
        REFERENCES db(id_db)
    );


CREATE TABLE datafile(
    id_datafile number(30) NOT NULL,
    name varchar(20) NOT NULL,
    bytes numeric(70) NOT NULL,
    id_tablespace_FK numeric(20) NOT NULL,
    datafile_timestamp timestamp NOT NULL,
    CONSTRAINT id_datafile PRIMARY KEY (id_datafile),
    CONSTRAINT id_tablespace_FK
        FOREIGN KEY (id_tablespace)
        REFERENCES tablespace(id_tablespace)
    );
    
