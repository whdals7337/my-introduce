#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=my-introduce

echo "> 기존 build 폴더 삭제"

rm -rf $REPOSITORY/front-end/build

echo "> zip 파일 복사 "

cp -r $REPOSITORY/front-end-zip/build $REPOSITORY/front-end
