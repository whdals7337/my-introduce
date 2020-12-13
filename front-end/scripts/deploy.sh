#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=my-introduce

echo "> 디렉토리 생성"

mkdir $REPOSITORY/front-end

echo "> zip 파일 복사 "

cp $REPOSITORY/zip/* $REPOSITORY/front-end

echo "> Build 파일 복사"

cd $REPOSITORY/front-end

echo "> Build 파일 복사"

npm start