#!bin/sh

docker run                                  \
  -d                                        \
  -p 5432:5432                              \
  -e POSTGRES_USER="admin"                  \
  -e POSTGRES_PASSWORD="123456"             \
  postgres

