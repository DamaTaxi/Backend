#!/usr/bin/env bash

REPOSITORY=/home/damataxi
cd $REPOSITORY

APP_NAME=action_codedeploy
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)
JAR_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "종료"
  sudo kill -9 $CURRENT_PID
  sudo kill -9 $JAR_PID
  sleep 5
fi

sudo kill $(sudo lsof -t -i:80)

source /home/ubuntu/codedeploy.sh

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > $REPOSITORY/nohup.out 2>&1 &
