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
    CONSTRAINT id_db_memory
        FOREIGN KEY (id_db_FK)
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
    CONSTRAINT id_db_cpu
        FOREIGN KEY (id_db_FK)
        REFERENCES db(id_db)
    );

CREATE TABLE usersDB(
    id_usersDB number(30) NOT NULL,
    username varchar(70) NOT NULL,
    account_status varchar(70) NOT NULL,
    default_ts varchar(70) NOT NULL,
    temp_ts varchar(70) NOT NULL,
    last_login timestamp NOT NULL, 
    roleDB varchar(30) NOT NULL,
    sessionDB varchar(30) NOT NULL,
    user_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_usersDB PRIMARY KEY (id_usersDB),
    CONSTRAINT id_db_users
        FOREIGN KEY (id_db_FK)
        REFERENCES db(id_db)
    );


CREATE TABLE tablespace(
    id_tablespace number(30) NOT NULL,
    name varchar(20) NOT NULL,
    block_size number(20) NOT NULL,
    max_size number(20) NOT NULL,
    status varchar(20) NOT NULL,
    contents varchar(30) NOT NULL,
    initial_extent number(20) NOT NULL,
    tablespace_timestamp timestamp NOT NULL,
    id_db_FK number(20) NOT NULL,
    CONSTRAINT id_tablespace PRIMARY KEY (id_tablespace),
    CONSTRAINT id_db_tablespace
        FOREIGN KEY (id_db_FK)
        REFERENCES db(id_db)
    );


CREATE TABLE datafile(
    id_datafile number(30) NOT NULL,
    name varchar(20) NOT NULL,
    bytes number(20) NOT NULL,
    id_tablespace_FK numeric(20) NOT NULL,
    datafile_timestamp timestamp NOT NULL,
    CONSTRAINT id_datafile PRIMARY KEY (id_datafile),
    CONSTRAINT id_tablespace_datafile
        FOREIGN KEY (id_tablespace_FK)
        REFERENCES tablespace(id_tablespace)
    );