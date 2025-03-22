CREATE TABLE IF NOT EXISTS subscriber(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    msisdn VARCHAR(13) NOT NULL
);

CREATE TABLE IF NOT EXISTS cdr(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    call_type VARCHAR(2),
    caller_number VARCHAR(255),
    receiver_cumber VARCHAR(255),
    beginning_call VARCHAR(255),
    termination_call VARCHAR(255)
);
