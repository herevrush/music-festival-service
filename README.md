This is a repository for Music Festival Backend Service, which calls http://eacodingtest.digital.energyaustralia.com.au/api/v1/festivals API and get all the data.

Try it

git clone https://github.com/herevrush/music-festival-service.git

cd music-festival-service

mvn install

Runing in dev profile: mvn spring-boot:run

Running in production profile: 

java -jar -Xmx300m -Dspring.profiles.active=prod -Dspring.config.location=./config/prod/ music-festival-service-1.0.jar
