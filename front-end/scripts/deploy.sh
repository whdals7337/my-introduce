#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=my-introduce

echo "> zip 파일 복사 "

cp -r $REPOSITORY/front-end-zip/build $REPOSITORY/front-end
