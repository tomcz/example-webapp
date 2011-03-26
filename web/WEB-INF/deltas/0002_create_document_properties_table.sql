create table document_properties (
  document_id varchar(255) not null,
  field_key varchar(255) not null,
  field_value varchar(255),
  field_message varchar(255),
  primary key (document_id, field_key)
);

alter table document_properties add constraint FK3C8EE2D7817A9160 foreign key (document_id) references document;
