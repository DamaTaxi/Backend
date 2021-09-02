#!/usr/bin/env bash

REPOSITORY=/home/damataxi
cd $REPOSITORY

APP_NAME=action_codedeploy
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)
JAR_PID=$(pgrep -f $JAR_PATH)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -15 $CURRENT_PID"
  sudo kill -15 $CURRENT_PID
  echo "> kill -15 $JAR_PID"
  sudo kill -15 $JAR_PID
  sleep 5
fi

source /home/ubuntu/codedeploy.sh

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > $REPOSITORY/nohup.out 2>&1 &
