#!/bin/bash

REPOSITORY=/home/ec2-user/app/step2
PROJECT_NAME=my-introduce

mkdir $REPOSITORY/front-end
cp $REPOSITORY/zip/* $REPOSITORY/front-end
cd $REPOSITORY/front-end
npm start