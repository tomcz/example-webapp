CREATE TABLE changelog (
  change_number BIGINT NOT NULL,
  complete_dt TIMESTAMP NOT NULL,
  applied_by VARCHAR(100) NOT NULL,
  description VARCHAR(500) NOT NULL,
  primary key (change_number)
)