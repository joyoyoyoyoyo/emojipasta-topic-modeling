CREATE TABLE post (
  anonymized_ID text
  subreddit text,
  posted_time uuid,
  headline text,
  body text,
  PRIMARY KEY (anonymized_ID, subreddit, posted_time)
);
