
ARG IMAGE=ruby
ARG VERSION=2.4.3-alpine
FROM ${IMAGE}:${VERSION}

RUN apk update && \
    apk add build-base && \
    apk add g++ gcc unixodbc-dev unixodbc freetds-dev freetds &&\
    apk add --update alpine-sdk \
               build-base

RUN mkdir cucumber
WORKDIR /cucumber
COPY . .

RUN addgroup -S ruby && adduser -S ruby -G ruby
# Don't run as root
USER ruby

RUN gem install bundler --source http://rubygems.org &&\
    bundle install

CMD cucumber --format pretty