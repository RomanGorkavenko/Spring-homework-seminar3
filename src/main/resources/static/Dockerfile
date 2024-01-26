FROM postgres:16.1-alpine
ENV POSTGRES_DB mydb
ENV POSTGRES_USER myuser
ENV POSTGRES_PASSWORD 6503

# Эта команда создаст новый образ Docker с именем “my-db”
# docker build -t my-db .

# После того, как образ будет создан, вы можете запустить контейнер с помощью следующей команды:
# docker run --name my-db -p 54322:5432 -d my-db

# Вход в консоль PSQL
# docker exec -it my-db psql -U myuser mydb