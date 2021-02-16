
ARG BUILD_IMAGE=gradle
ARG BUILD_IMAGE_VERSION=6.8.2-jdk15-hotspot
ARG PRODUCTION_IMAGE=adoptopenjdk/openjdk15
ARG PRODUCTION_IMAGE_VERSION=jdk-15.0.2_7-alpine
ARG GRADLE_OPTS="-Xms128m -Xmx256m"
ARG JAVA_OPTS="-Xms128m -Xmx256m"
#
# Stage to build and test service
FROM ${BUILD_IMAGE}:${BUILD_IMAGE_VERSION} as build

COPY . .

ENV GRADLE_OPTS ${GRADLE_OPTS}
RUN gradle build

#
# Slim down in production env
FROM ${PRODUCTION_IMAGE}:${PRODUCTION_IMAGE_VERSION} as production

RUN mkdir /opt/java/service
WORKDIR /opt/java/service

COPY --from=build /home/gradle/build/libs/*.jar service.jar

EXPOSE 9000

RUN addgroup -S java && adduser -S java -G java
# Don't run as root
USER java

CMD java ${JAVA_OPTS} -jar service.jar