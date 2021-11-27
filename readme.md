# Video game leader board
This code corresponds with Chapter 4 in the upcoming O'Reilly book: [Mastering Kafka Streams and ksqlDB][book] by Mitch Seymour. This tutorial covers **Stateful processing** in Kafka Streams. Here, we demonstrate many stateful operators in Kafka Streams' high-level DSL by building an application a video game leaderboard.


[book]: https://www.kafka-streams-book.com/

# Running Locally
The only dependency for running these examples is [Docker Compose][docker].

[docker]: https://docs.docker.com/compose/install/

Once Docker Compose is installed, you can start the local Kafka cluster using the following command:

```sh
$ docker-compose up
```

Now, to run the Kafka Streams application, simply run:

```
./gradlew run --info
```

# Producing Test Data
Once your application is running, you can produce some test data to see it in action. Since our video game leaderboard application reads from multiple topics (`players`, `products`, and `score-events`), we have saved example records for each topic in the `data/` directory. To produce data into each of these topics, open a new tab in your shell and run the following commands.

```sh
# log into the broker, which is where the kafka console scripts live
$ docker-compose exec kafka bash

# produce test data to players topic
$ kafka-console-producer --bootstrap-server kafka:9092 --topic test_gps_topic_apac --property 'parse.key=true' --property 'key.separator=|' < players.json

```